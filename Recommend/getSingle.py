# coding=utf-8
import pymysql
import numpy
import sys

db = pymysql.connect(host="localhost", user="root", password="root", db="pecrp")
cur = db.cursor()

users = []
labels = []
videos = []
videoLabel = {}
userLabel = {}
userModel = {}
cosList = []
resLabel = []

resVideo = []
vid = 0
uid = 0


def read():
    # 获取所有的用户编号
    sql_select = "SELECT * FROM user"
    try:
        cur.execute(sql_select)
        res = cur.fetchall()
        # print(res)
        for row in res:
            users.append(row[0])
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

    # 获取所有用户的标签编号 userLabel[i] => 用户i的所有标签编号
    sql_select = "SELECT * FROM user_label"
    try:
        cur.execute(sql_select)
        res = cur.fetchall()
        # print(res)
        tlist = []
        tuser = res[0][0]

        for per in users:
            userLabel[per] = [0]  # 避免之后建模过程中没有标签的用户造成下标为0的情况

        for row in res:
            if (row[0] == tuser):
                tlist.append(row[1])
            else:
                userLabel[tuser] = tlist[:]
                tlist.clear()
                tlist.append(row[1])
            tuser = row[0]

        if (tlist != None):  # 最后一个list不为空的情况
            userLabel[tuser] = tlist[:]
            tlist.clear()

            # print(userLabel)
    except Exception as e:
        print(e)
        db.rollback()


# 某个用户的评分特征向量 对所有标签的评分
def user_tag_model(u):
    w = []
    index = 0
    # print(userLabel)

    for item in labels:
        # print("item:", item)
        if (item == userLabel[u][index]):
            w.append(1)
            index += 1
        else:
            w.append(0)

        if (index == len(userLabel[u])):
            index -= 1  # 防止下标溢出

    userModel[u] = w[:]


# 计算两个用户u,v的相似度
def cosineUser(u, v):
    array1 = numpy.array(userModel[u])
    array2 = numpy.array(userModel[v])

    normu = numpy.linalg.norm(array1)
    normv = numpy.linalg.norm(array2)

    if ((normu == 0) or (normv == 0)):
        return 0

    ans = numpy.dot(array1.T, array2)
    ans = float(ans / (normu * normv))
    # 归一化
    ans = 0.5 + 0.5 * ans
    return ans


# 计算前k个邻居
def topKNeighbor(u):
    for v in users:
        if (v != u):
            cosList.append((cosineUser(u, v), v))
    cosList.sort(reverse=True)
    # print(cosList)


# 前k个邻居标签中当前用户没有的
def topKLabel(u, k):
    for item in cosList:
        v = item[1]
        for l in userLabel[v]:
            if ((l not in userLabel[u]) and (l not in resLabel) and (l != 0)):
                resLabel.append(l)
                if (len(resLabel) >= k):
                    return
                    # print(resLabel)


# 将推荐的标签写入数据库
def writeLabel():
    try:
        sql_insert = "REPLACE INTO rec_single_label(vid, uid, lid, hotDegree) VALUES ('%d', '%d', '%d', '%d')"
        index = 1
        for item in resLabel:
            cur.execute(sql_insert % (vid, uid, item, index))
            index += 1
            db.commit()
    except Exception as e:
        print(e)
        db.rollback()


# 被topk中的标签标记最多的资源
def topKLabelVideo(k):
    videoCnt = [[0, 0]]
    for v in videos:
        videoCnt.append([0, v])

    for l in resLabel:
        for v in videos:
            if (l in videoLabel[v]):
                videoCnt[v][0] += 1

    videoCnt.sort(reverse=True)  # 按被标记的数量排序
    # print(videoCnt)

    for item in videoCnt:
        if ((item[1] != vid) and (item[1] != 0)):
            resVideo.append(item[1])


# 某个视频的评分特征向量 对所有标签的评分
def video_tag_model(u):
    w = []
    index = 0
    # print(userLabel)
    for item in labels:
        if (item in videoLabel[u]):
            w.append(1)
            index += 1
        else:
            w.append(0)

        if (index == len(userLabel[u])):
            index -= 1  # 防止下标溢出

    return w[:]


# 计算用户u和物品v的相似度
def cosineUserVideo(u, v):
    array1 = numpy.array(userModel[u])
    array2 = numpy.array(video_tag_model(v))
    # print(v, array2)
    normu = numpy.linalg.norm(array1)
    normv = numpy.linalg.norm(array2)

    if ((normu == 0) or (normv == 0)):
        return 0

    ans = numpy.dot(array1.T, array2)
    ans = float(ans / (normu * normv))
    # 归一化
    ans = 0.5 + 0.5 * ans
    return ans

# 过滤资源，视频和用户模型进行余弦相似度计算
def filterVideo():
    cosUVList =[]
    # print(videoLabel)
    # print(resVideo)
    for v in resVideo:
        cosUVList.append((cosineUserVideo(uid, v), v))

    cosUVList.sort(reverse=True)
    resVideo.clear()
    for v in cosUVList:
        resVideo.append(v[1])

    print("coslist:", cosUVList)
    print("resVideo", resVideo)




def writeVideo():
    try:
        sql_insert = "REPLACE INTO rec_single_video(vid, uid, recVid, hotDegree) VALUES ('%d', '%d', '%d', '%d')"
        index = 1
        for item in resVideo:
            cur.execute(sql_insert % (vid, uid, item, index))
            index += 1
            db.commit()
    except Exception as e:
        print(e)
        db.rollback()


if __name__ == "__main__":
    print("=====start get single=====")
    # vid = int(sys.argv[1])
    vid = 1
    uid = 1
    read()
    for u in users:
        user_tag_model(u)
    topKNeighbor(uid)
    topKLabel(uid, 10)
    writeLabel()
    print("=======single video labels finished!")
    topKLabelVideo(10)
    filterVideo()
    # print(resVideo)
    writeVideo()
    print("=======single video recVideos finished!")
    print("=====end get single=====")
