# coding=utf-8
import pymysql
import numpy
import sys

db = pymysql.connect(host="localhost", user="root", password="root", db="pecrp")
cur = db.cursor()

classifies = []
labels = []
videos = []
classifyVideo = {}
videoLabel = {}

videoPlay = [0]

resLabel = [[0]]
resVideo = [[0]]

videoPlayList = [(0, 0)]

def read():
    # 获取所有分类编号
    sql_select = "SELECT * FROM classify"
    try:
        cur.execute(sql_select)
        res = cur.fetchall()
        # print(res)
        for row in res:
            classifies.append(row[0])
    except Exception as e:
        print(e)
        db.rollback()

    # 获取所有标签编号
    sql_select = "SELECT * FROM label"
    try:
        cur.execute(sql_select)
        res = cur.fetchall()
        # print(res)
        for row in res:
            labels.append(row[0])
    except Exception as e:
        print(e)
        db.rollback()

    # 获取所有视频编号
    sql_select = "SELECT * FROM video"
    try:
        cur.execute(sql_select)
        res = cur.fetchall()
        # print(res)
        for row in res:
            videos.append(row[0])
    except Exception as e:
        print(e)
        db.rollback()

    # 获取视频的标签编号 videoLabel[i] => 视频i的所有标签编号
    sql_select = "SELECT * FROM video_label"
    try:
        cur.execute(sql_select)
        res = cur.fetchall()
        # print(res)
        tlist = []
        tvideo = res[0][0]

        # print(labels)
        for video in videos:
            videoLabel[video] = [0]  # 避免之后建模过程中没有标签的视频造成下标为0的情况
        # print(res)
        for row in res:
            if (row[0] == tvideo):
                tlist.append(row[1])
            else:
                videoLabel[tvideo] = tlist[:]
                tlist.clear()
                tlist.append(row[1])
            tvideo = row[0]

        if (tlist != None):  # 最后一个list不为空的情况
            videoLabel[tvideo] = tlist[:]
            tlist.clear()

    except Exception as e:
        print(e)
        db.rollback()

    # 获取分类下的视频编号 classifyVideo[i] => 分类i的所有视频编号
    sql_select = "SELECT * FROM classify_video ORDER BY cid"
    try:
        cur.execute(sql_select)
        res = cur.fetchall()
        # print(res)
        tlist = []
        tclass = res[0][0]

        # print(labels)
        for c in classifies:
            classifyVideo[c] = [0]  # 避免之后建模过程中没有视频的分类造成下标为0的情况
        # print(res)
        for row in res:
            if (row[0] == tclass):
                tlist.append(row[1])
            else:
                classifyVideo[tclass] = tlist[:]
                tlist.clear()
                tlist.append(row[1])
            tclass = row[0]

        if (tlist != None):  # 最后一个list不为空的情况
            classifyVideo[tclass] = tlist[:]
            tlist.clear()

            # print(classifyVideo)

    except Exception as e:
        print(e)
        db.rollback()


    sql = "SELECT * FROM video"
    try:
        cur.execute(sql)
        res = cur.fetchall()
        # print("vid", 'playNum')
        # 获取video中所有视频的播放数，添加到list中
        for row in res:
            playNum = row[5]
            # print(vid, playNum)
            videoPlayList.append(playNum)
    except Exception as e:
        print(e)
        db.rollback()

    for c in classifies:
        resLabel.append([0])
        resVideo.append([0])


# 分类下中的视频中出现最多的标签
def topKLabel(cid):
    ansList = []
    labelList = [[0, 0]]
    for l in labels:
        labelList.append([0, l])

    for v in classifyVideo[cid]:
        if (v == 0):
            return
        for l in labels:
            if (l in videoLabel[v]):
                labelList[l][0] += 1
    labelList.sort(reverse=True)
    for l in labelList:
        if (l[1] != 0):
            ansList.append(l[1])
    print("cid:", cid, "label:", ansList)
    return ansList[:]


# 将推荐分类的标签写入数据库
def writeLabel(cid):
    if (resLabel[cid] == None):
        return
    try:
        sql_insert = "REPLACE INTO rec_classify_label(cid, lid, hotDegree) VALUES ('%d', '%d', '%d')"
        index = 1
        for item in resLabel[cid]:
            cur.execute(sql_insert % (cid, item, index))
            index += 1
            db.commit()
    except Exception as e:
        print(e)
        db.rollback()


# 分类下出现最多标签所对应的视频
def topKVideo(cid):
    ansList = []
    cntList = [[0, 0]]
    for v in videos:
        cntList.append([0, v])
    for v in classifyVideo[cid]:
        if (resLabel[cid] == None):
            break
        for l in resLabel[cid]:
            if (l in videoLabel[v]):
                cntList[v][0] += 1
    cntList.sort(reverse=True)
    for item in cntList:
        if ((item[1] != 0) and (item[1] in classifyVideo[cid])):
            ansList.append(item[1])
    print("cid:", cid, "video:", ansList)
    return ansList[:]


# 将推荐的视频写入数据库
def writeVideo(cid):
    if (resVideo[cid] == None):
        return
    try:
        sql_insert = "REPLACE INTO rec_classify_video(cid, vid, hotDegree) VALUES ('%d', '%d', '%d')"
        index = 1
        for item in resVideo[cid]:
            cur.execute(sql_insert % (cid, item, index))
            index += 1
            db.commit()
    except Exception as e:
        print(e)
        db.rollback()


# 将分类热门推荐视频写入数据库
def writeHot(cid):

    playList = [(0, 0)]
    for v in classifyVideo[cid]:
        if(v == 0):
            return
        # print(v, videoPlayList[v])
        playList.append((videoPlayList[v], v))

    playList.sort(reverse=True)

    print("cid:", cid, "hot:", playList)

    try:
        # 将已有记录更新，没有的插入
        sql_replace = "REPLACE INTO rec_classify_hot(cid, vid, hotDegree) VALUES ('%d', '%d', '%d')"
        index = 1
        for item in playList:
            if(item[1] != 0):
                cur.execute(sql_replace % (cid, item[1], index))
                db.commit()
                index += 1
    except Exception as e:
        print(e)
    # db.rollback()


if __name__ == "__main__":
    print("=====start get classified=====")
    read()
    for c in classifies:
        resLabel[c] = topKLabel(c)
        resVideo[c] = topKVideo(c)
        writeLabel(c)
        writeVideo(c)
        writeHot(c)

    print("=====end get classified=====")
