package cn.edu.service;

import cn.edu.entity.DsBean;
import cn.edu.entity.StudentImageBean;
import cn.edu.entity.StudentInfoBean;

/**
 * @author lbc
 * @description: TODO
 * @date 2019/3/31 20:07
 */
public interface StuImgDataService {

    //通过id和year得到学生画像
    public StudentImageBean getStuImgById(String id, String year);

    //通过id和year得到学生ds字段情况
    public DsBean getDsById(String id, String year);

    //得到学生的基本信息
    public StudentInfoBean getStudentInfoById(String id);
}
