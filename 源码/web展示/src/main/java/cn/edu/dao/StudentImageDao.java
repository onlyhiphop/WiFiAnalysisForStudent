package cn.edu.dao;

import cn.edu.entity.StudentImageBean;
import org.apache.ibatis.annotations.Param;

/**
 * @author lbc
 * @description: TODO
 * @date 2019/3/31 16:54
 */
public interface StudentImageDao {

    //通过mac地址获取student_imge
    public StudentImageBean getStuImgByMac(@Param("mac") String mac, @Param("year") String year);
}
