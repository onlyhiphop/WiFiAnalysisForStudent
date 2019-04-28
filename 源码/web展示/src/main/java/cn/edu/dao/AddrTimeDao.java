package cn.edu.dao;

import cn.edu.entity.AddrTimeBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lbc
 * @description: 对addr相关表进行查询
 * @date 2019/3/30 15:57
 */
public interface AddrTimeDao {

    //通过mac和month获取某月的地点相关信息
    public List<AddrTimeBean> getAddrMonthDataByIdMonth(@Param("mac") String mac,@Param("year") String year, @Param("month") String month);

}
