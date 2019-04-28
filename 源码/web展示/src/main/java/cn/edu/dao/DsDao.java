package cn.edu.dao;

import cn.edu.entity.DsBean;
import org.apache.ibatis.annotations.Param;

/**
 * @author lbc
 * @description: TODO
 * @date 2019/3/31 16:48
 */
public interface DsDao {

    //通过mac地址和year month求某月Ds表情况
    public DsBean getDsBeanByMacMonth(@Param("mac") String mac, @Param("year") String year, @Param("month") String month);

    //通过mac地址和year求某年的Ds表情况
    public DsBean getDsBeanByMacYear(@Param("mac") String mac, @Param("year") String year);
}
