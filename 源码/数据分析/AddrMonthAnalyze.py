# -*- encoding: utf-8 -*-
"""
@Description :
    对一个月内一个学生在各个地点停留时间数据的分析，存入数据库
    对应：AddrStayTimeMR 产生的数据
@Author : liao
@Version : 1.0
@Time : 2019/2/09 15:36
@Contact :   760003259@qq.com
"""
import os
import pandas as pd
import pymysql
import sys

input_data_path = sys.argv[1]
all_file = os.listdir(input_data_path)


def file_analyze(input_file):
    df = pd.read_csv(input_file, names=['mac', 'addr', 'start_time', 'end_time', 'differ_time'], encoding='utf-8')

    mac = df['mac'][0]
    # 以addr分组，可以得到所有的addr
    grouped = df.groupby(by='addr')

    # 得到了所有addr出现的对应次数，字典类型
    addr_time_dict = dict(grouped['addr'].count())

    # 求所有addr对应的总时间
    addr_totaltime_dict = dict(grouped['differ_time'].sum())

    # 插入操作
    for index, key in enumerate(addr_time_dict.keys()):
        if index == 0:
            sql_insert = 'insert into addr_Jan (mac,addr,total_time,go_times) values (\'{}\',\'{}\',{},{})'.format(mac, key, addr_totaltime_dict[key], addr_time_dict[key])
        else:
            sql_insert = sql_insert + ',(\'{}\',\'{}\',{},{})'.format(mac, key, addr_totaltime_dict[key], addr_time_dict[key])
    print(sql_insert)

    try:
        cur.execute(sql_insert)
        # 提交
        db.commit()
    except Exception as e:
        # 错误回滚
        db.rollback()
        print(e)


# 打开数据库连接
db = pymysql.connect(host='localhost', user='root', password='123456', db='wifianalysis', port=3306)

# 使用cursor() 方法获取操作游标
cur = db.cursor()

for file in all_file:
    input_file_path = input_data_path + '\\' + file
    file_analyze(input_file_path)

db.close()
