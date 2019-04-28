package cn.edu.service;

import cn.edu.entity.AddrTimeBean;
import cn.edu.entity.DsBean;

import java.util.List;

/**
 * @author lbc
 * @description: TODO
 * @date 2019/3/3115:39
 */
public interface MonthDataService {

    //得到地点的月行为轨迹
    public List<AddrTimeBean> getMonthAddrDataById(String id, String year, String month);

    //得到手机Ds字段情况
    public DsBean getMonthDsById(String id, String year, String month);
}
