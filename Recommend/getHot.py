#coding=utf-8
import pymysql
db = pymysql.connect(host="localhost", user="root", password="root", db="pecrp")
cur = db.cursor()
list = []
def takeSecond(elem):   # list的排序规则order by playNum
    return elem[1]
def read():
    sql = "SELECT * FROM video"
    try:
        cur.execute(sql)
        res = cur.fetchall()
        # print("vid", 'playNum')
        # 获取video中所有视频的播放数，添加到list中
        for row in res:
            vid = row[0]
            playNum = row[5]
            # print(vid, playNum)
            list.append((vid, playNum))
        list.sort(key=takeSecond,reverse=True)
        # print(list)
        # 将已有记录更新，没有的插入
        sql_replace = "REPLACE INTO rec_hot_video(vid, hotDegree) VALUES ('%d', '%d')"
        for i in range(len(list)):
            cur.execute(sql_replace % (list[i][0], list[i][1]))
            db.commit()
            # print(i,sql)
    except Exception as e:
        print(e)
        db.rollback()
    finally:
        db.close()

if __name__ == "__main__":
    print("=====start get hot=====")
    read()
    print("=====end get hot=====")