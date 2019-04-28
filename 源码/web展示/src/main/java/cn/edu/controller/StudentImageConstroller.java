package cn.edu.controller;

import cn.edu.entity.DsBean;
import cn.edu.entity.StudentImageBean;
import cn.edu.entity.StudentInfoBean;
import cn.edu.service.StuImgDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lbc
 * @description: 学生画像功能模块
 * @date 2019/3/31 15:45
 */
@Controller
@RequestMapping(value = "/wifi-web")
public class StudentImageConstroller {

    @Autowired
    private StuImgDataService stuImgDataService;

    @RequestMapping(value = "/stuimg.action")
    @ResponseBody
    public Map<String, Object> stuImgData(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        String id = request.getParameter("id");
        String year = request.getParameter("year");
        StudentImageBean stuimgBean = stuImgDataService.getStuImgById(id, year);
        DsBean dsBean = stuImgDataService.getDsById(id, year);
        StudentInfoBean studentInfo = stuImgDataService.getStudentInfoById(id);
        map.put("studentInfo", studentInfo);
        map.put("studentImage", stuimgBean);
        map.put("dsBean", dsBean);
        return map;
    }

}
