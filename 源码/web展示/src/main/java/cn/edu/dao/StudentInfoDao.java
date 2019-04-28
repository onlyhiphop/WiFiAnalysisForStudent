package cn.edu.dao;

import cn.edu.entity.StudentInfoBean;

/**
 * @author lbc
 * @description: TODO
 * @date 2019/3/31 11:29
 */
public interface StudentInfoDao {

    //通过学号查询出学生信息
    public StudentInfoBean findStudentInfoById(String id);

    //通过学号查询mac地址
    public String findMacById(String id);

}
