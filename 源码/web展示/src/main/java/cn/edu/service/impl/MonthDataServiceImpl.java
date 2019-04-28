package cn.edu.service.impl;

import cn.edu.dao.AddrTimeDao;
import cn.edu.dao.DsDao;
import cn.edu.dao.StudentInfoDao;
import cn.edu.entity.AddrTimeBean;
import cn.edu.entity.DsBean;
import cn.edu.service.MonthDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lbc
 * @description: TODO
 * @date 2019/3/3115:41
 */
@Service
@Transactional
public class MonthDataServiceImpl implements MonthDataService {

    @Autowired
    private AddrTimeDao addrTimeDao;
    @Autowired
    private StudentInfoDao studentInfoDao;
    @Autowired
    private DsDao dsDao;

    @Override
    public List<AddrTimeBean> getMonthAddrDataById(String id, String year, String month) {
        String mac = studentInfoDao.findMacById(id);
        List<AddrTimeBean> addrTimeBeans = addrTimeDao.getAddrMonthDataByIdMonth(mac, year, month);
        return addrTimeBeans;
    }

    @Override
    public DsBean getMonthDsById(String id, String year, String month) {
        String mac = studentInfoDao.findMacById(id);
        DsBean dsBean = dsDao.getDsBeanByMacMonth(mac, year, month);
        return dsBean;
    }


}
