#coding=utf-8
import pymysql
import sys
db = pymysql.connect(host="localhost", user="root", password="root", db="pecrp")
cur = db.cursor()
oriLabel = []
resLabel = []
vid = 1
def read():

    # 获取当前vid的所有标签编号
    sql_select = "SELECT * FROM video_label WHERE vid = '%d'"
    try:
        cur.execute(sql_select % vid)
        res = cur.fetchall()
        for row in res:
            oriLabel.append(row[1])
    except Exception as e:
        print(e)
        db.rollback()

def writeLabel():
    try:
        sql_insert = "REPLACE INTO rec_single_label(vid, lid) VALUES ('%d', '%d')"
        for item in resLabel:
            cur.execute(sql_insert % (vid, item))
            db.commit()
    except Exception as e:
        print(e)
        db.rollback()

def writeVideo():
    try:
        a = 1
    except Exception as e:
        print(e)
        db.rollback()
if __name__ == "__main__":
    print("=====start get single=====")
    vid = int(sys.argv[1])
    read()
    writeLabel()
    # writeVideo()
    print(vid)
    print("=====end get single=====")
