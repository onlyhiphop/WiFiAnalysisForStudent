# -*- encoding: utf-8 -*-
"""
@Description :
    auto_id,mac,N_time,active_time,laterN_time
    对一个学期的手机亮屏数据进行处理。
@Author : liao
@Version : 1.0
@Time : 2019/2/28 21:20
@Contact :   760003259@qq.com
"""
import pandas as pd
import sys

input_path = sys.argv[1]

df = pd.read_csv(input_path, sep='\t', names=['mac', 'N_time', 'Y_time', 'active_time', 'laterN_time'], encoding='utf-8')

grouped_mac = df.groupby(by='mac')

for mac, df_iterm in grouped_mac:
    N_time = df_iterm['N_time'].sum()
    Y_time = df_iterm['Y_time'].sum()
    print(N_time)
    print(Y_time)
