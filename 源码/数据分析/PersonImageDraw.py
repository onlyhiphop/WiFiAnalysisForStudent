# -*- encoding: utf-8 -*-
"""
@Description :
    对整个学期的数据分析：
    对人物的行为轨迹进行画像
    输入：一个学期的数据   输入格式： mac, addr, total_time, go_times
@Author : liao
@Version : 1.0
@Time : 2019/2/11 20:21
@Contact :   760003259@qq.com
"""
import pandas as pd
import pymysql
import sys


def getHobbies(all_addr_list, dataframe):
    hobbies = []
    for h in all_addr_list:
        spend_time = dataframe.loc[mac].loc[h]['total_time']
        # 对比标准数据
        if h == '图书馆' and spend_time > 950:
            hobbies.append('图书馆学习')
        elif h == '篮球场' and spend_time > 300:
            hobbies.append('篮球')
        elif h == '足球场' and spend_time > 600:
            hobbies.append('足球')
    return hobbies


def calculateConsum(all_addr_list, dataframe):
    ability = 0
    market_times = 0
    canteen_times = 0
    for c in all_addr_list:
        if c == '超市':
            # 去超市的次数
            market_times = int(dataframe.loc[mac].loc[c]['go_times'])
        elif c == '食堂':
            # 去食堂的次数
            canteen_times = int(dataframe.loc[mac].loc[c]['go_times'])

    # 根据次数判断消费能力（根据标准）
    if canteen_times < 6 and market_times > 4:
        ability = ability + 2
    return ability


inputpath = sys.argv[1]
df = pd.read_csv(inputpath, names=['mac', 'addr', 'total_time', 'go_times'], encoding='utf-8')
grouped = df.groupby(by=['mac', 'addr'])
df_sum = grouped.sum()  # 对 total_time 和 go_times 列进行求和
grouped2 = df_sum.groupby(by='mac')  # 对不同Mac地址分类，后便于分组进行操作

# 打开数据库连接
# db = pymysql.connect(host='localhost', user='root', password='123456', db='wifianalysis', port=3306)
# cur = db.cursor()

for i, j in grouped2:
    mac = i
    all_addr = list(j.loc[i].index.values)  # 二级索引是地址，列出所有二级索引转成list

    consumption_ability = calculateConsum(all_addr, j)

    hobbies = getHobbies(all_addr, j)  # 将对应的地址与爱好转换

    if len(hobbies) == 0:
        hobbies = '长时间在寝室,无运动爱好'
        favorite_stay_addr = '寝室'
        favorite_go_addr = '寝室'
    else:
        # 算待的总时间和去的总次数时，先把寝室去掉
        j.loc[i].loc['寝室']['total_time'] = 0
        j.loc[i].loc['寝室']['go_times'] = 0
        favorite_stay_addr = j.loc[i]['total_time'].idxmax()
        favorite_go_addr = j.loc[i]['go_times'].idxmax()
    print(mac)
    print('爱好：', hobbies)
    print('待的时间最长的地方：', favorite_stay_addr)
    print('去的次数最多的地方：', favorite_go_addr)
    print('消费能力', consumption_ability)


