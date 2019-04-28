# -*- encoding: utf-8 -*-
"""
@Description :
    对ds字段的分析
    对应：GetDsTimeMR 程序的输出  mac,startTime,endTime,differMinute,ds
        可以用于一个月数据的分析，也可以用于一个学期
    1. 统计学生手机亮屏总时间 （ds = N）
    2. 分析出学生玩手机活跃时间点
    3. 分析出晚上 11点之后 学生玩手机的情况
@Author : liao
@Version : 1.0
@Time : 2019/2/23 14:46
@Contact :   760003259@qq.com
"""
import pandas as pd
import numpy as np
import sys

input_path = sys.argv[1]

df = pd.read_csv(input_path, names=['mac', 'ds', 'startTime', 'endTime', 'differMinute'], encoding='utf-8')

# 让控制台不限制输出
pd.set_option('display.width', None)

mac = df['mac'][0]

grouped = df.groupby(by='ds')

# 创建一个24小时的df
time = [0, 0.5, 1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5, 5, 5.5, 6, 6.5, 7, 7.5, 8, 8.5, 9, 9.5, 10, 10.5, 11, 11.5, 12, 12.5,
        13,
        13.5, 14, 14.5, 15, 15.5, 16, 16.5, 17, 17.5, 18, 18.5, 19, 19.5, 20, 20.5, 21, 21.5, 22, 22.5, 23, 23.5]
oneDay_df = pd.DataFrame(np.zeros((df.shape[0], len(time))), columns=time)


# 将具体时间根据需求格式化成整点或半点
def timeFormat(time_str):  # Wed Dec 19 16:51:07 2018
    the_time = time_str.split(' ')[3]
    hours = int(the_time.split(':')[0])
    minute = int(the_time.split(':')[1])
    # 没有24点，是 0点
    if hours == 23 and minute >= 30:
        return 0
    if minute >= 30:
        return hours + 0.5
    else:
        return hours


# 得到中间的间隔时间
def getMiddleTime(startTime, endTime):
    middle_list = [startTime, endTime]

    if endTime - startTime < 0:
        e = np.arange(0, endTime, 0.5)
        s = np.arange(startTime, 24, 0.5)
        middle_list.extend(e)
        middle_list.extend(s)
        return middle_list

    m = np.arange(startTime + 0.5, endTime, 0.5)
    middle_list.extend(m)
    return middle_list


# 遍历DataFrame，将时间对应进oneDay_df中
def assign_oneDay_df(df):
    for index, row in df.iterrows():
        start = timeFormat(row['startTime'])
        end = timeFormat(row['endTime'])
        all_index = getMiddleTime(start, end)
        oneDay_df.loc[index, all_index] = 1


# 不息屏总时间
N_sum = 0
# 息屏总时间
Y_sum = 0

for i, j in grouped:
    if i == 'N':
        N_sum = j['differMinute'].sum()
        assign_oneDay_df(j)
    if i == 'Y':
        Y_sum = j['differMinute'].sum()

# 降序排列，得到最活跃时间点
sortValues_series = oneDay_df.sum().sort_values(ascending=False)
sortValues_list = list(sortValues_series.items())  # .items() 返回索引和值 (元组)

# 得到前3的时间点
top3_list = sortValues_list[:5]

# 里面存着元组(时间点, 次数)
laterTime = []
for key, value in sortValues_list:
    if value != 0:
        if key >= 23.5 or key <= 6:
            laterTime.append((key, value))

print(mac)
print("息屏总时间", Y_sum)
print("不息屏总时间", N_sum)
for t, tt in top3_list:
    print(tt, "次在", t, "点玩手机")

print("在23.5点和6点之前的玩手机情况:")
if laterTime:
    for t, tt in laterTime:
        print(tt, "次在", t, "点玩手机")
else:
    print("在23.5点和6点之前没有玩手机情况")
