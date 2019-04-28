package cn.edu.controller;

import cn.edu.entity.AddrTimeBean;
import cn.edu.entity.DsBean;
import cn.edu.service.MonthDataService;
import cn.edu.service.StuImgDataService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lbc
 * @description: 学生月数据分析模块
 * @date 2019/3/31 15:45
 */
@Controller
@RequestMapping(value = "/wifi-web")
public class StudentMonthConstroller {

    @Autowired
    private MonthDataService monthDataService;


    @RequestMapping(value = "/month.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> monthDataPage(HttpServletRequest request){
        String id = request.getParameter("id");
        String year = request.getParameter("year");
        String month = request.getParameter("month");
        Map<String, Object> map = new HashMap<>();
        List<AddrTimeBean> addrTimeBeans = monthDataService.getMonthAddrDataById(id, year, month);
        DsBean dsBean = monthDataService.getMonthDsById(id, year, month);

        Map<String, List> maps = formatList(addrTimeBeans);

        map.put("addr_data", maps);
        map.put("dsBean", dsBean);

        return map;
    }

    private Map<String, List> formatList(List<AddrTimeBean> atbs) {
        List<String> addrs = new ArrayList<>();
        List<Integer> totals = new ArrayList<>();
        List<Integer> goes = new ArrayList<>();

        for (AddrTimeBean atb:atbs) {
            String addr = atb.getAddr();
            Integer total_time = atb.getTotal_time();
            Integer go_times = atb.getGo_times();
            addrs.add(addr);
            totals.add(total_time);
            goes.add(go_times);
        }

        Map<String, List> maps = new HashMap<>();
        maps.put("addrs", addrs);
        maps.put("totals", totals);
        maps.put("goes", goes);

        return maps;
    }

}
