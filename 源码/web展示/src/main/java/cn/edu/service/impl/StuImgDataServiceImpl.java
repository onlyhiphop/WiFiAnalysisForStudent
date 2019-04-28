package cn.edu.service.impl;

import cn.edu.dao.DsDao;
import cn.edu.dao.StudentImageDao;
import cn.edu.dao.StudentInfoDao;
import cn.edu.entity.DsBean;
import cn.edu.entity.StudentImageBean;
import cn.edu.entity.StudentInfoBean;
import cn.edu.service.StuImgDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author lbc
 * @description: TODO
 * @date 2019/3/31 20:08
 */
@Service
@Transactional
public class StuImgDataServiceImpl implements StuImgDataService{

    @Autowired
    private StudentImageDao studentImageDao;
    @Autowired
    private StudentInfoDao studentInfoDao;
    @Autowired
    private DsDao dsDao;


    @Override
    public StudentImageBean getStuImgById(String id, String year) {
        String mac = studentInfoDao.findMacById(id);
        StudentImageBean stuImgBean = studentImageDao.getStuImgByMac(mac, year);
        return stuImgBean;
    }

    @Override
    public DsBean getDsById(String id, String year) {
        String mac = studentInfoDao.findMacById(id);
        DsBean dsBean = dsDao.getDsBeanByMacYear(mac, year);
        return dsBean;
    }

    @Override
    public StudentInfoBean getStudentInfoById(String id) {
        StudentInfoBean studentInfoBean = studentInfoDao.findStudentInfoById(id);
        return studentInfoBean;
    }
}
