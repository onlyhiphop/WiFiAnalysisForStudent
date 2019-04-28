# -*- encoding: utf-8 -*-
"""
@Description :
    对初始的数据进行清洗，取出需要的字段
@Author : liao
@Version : 1.0
@Time : 2019/1/16 16:32
@Contact :   760003259@qq.com

    补充：
    eval() 存在很大的安全漏洞，所以用 ast.literal_eval() 代替
"""
import re
import os
import ast
import sys


def clean_data(file_input_name, file_output_name):
    file_w = open(file_output_name, 'w', encoding='utf-8')
    file_w.write("id,time,ds,mac,range\n")

    file_r = open(file_input_name, 'r', encoding='utf-8')
    text = file_r.read()

    ret = re.findall('data=({.*})', text)
    i = 0
    for r in ret:
        i += 1
        try:
            data_dict = ast.literal_eval(r)
        except:
            print(file_input_name)
            print("第", i, "项数据出现异常")
        else:
            # id 为WiFi探针的id
            # 利用字典的 get方法 如果字段不存在，则会返回None
            id = data_dict.get('id')
            if id == "00f92a6b":
                addr = "寝室"
            time = data_dict.get('time')
            # lat = data_dict.get('lat')
            # lon = data_dict.get('lon')
            data_list = data_dict.get('data')  # list中每一项又是一个字典
            for data in data_list:
                mac = data.get('mac')
                range = data.get('range')
                ds = data.get('ds')
                print(ds)
                if ds is None:
                    ds = 'N'
                data_line = "{},{},{},{},{},{}\n".format(id, time, ds, mac, range, addr)
                if not 'None' in data_line:
                    file_w.write(data_line)
    file_r.close()
    file_w.close()


data_collect_path = sys.argv[1]
data_clean_path = sys.argv[2]


file_list = os.listdir(data_collect_path)
for file_item in file_list:
    file_input_name = data_collect_path + '\\' + file_item
    file_ouput_name = data_clean_path + '\\' +file_item
    clean_data(file_input_name, file_ouput_name)


