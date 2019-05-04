package com.spring.demo.action;

import com.spring.demo.service.IQueryService;
import com.spring.formework.annotation.Autowired;
import com.spring.formework.annotation.Controller;
import com.spring.formework.annotation.RequestMapping;
import com.spring.formework.annotation.RequestParam;
import com.spring.formework.webmvc.servlet.GPModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * 2019/4/18
 * 创建人￥Jack
 */
@Controller
@RequestMapping("/")
public class PageAction {

    @Autowired
    IQueryService queryService;

    @RequestMapping("/first.html")
    public GPModelAndView query(@RequestParam("teacher") String teacher){
        String result = queryService.query(teacher);
        Map<String,Object> model = new HashMap<String,Object>();
        model.put("teacher",teacher);
        model.put("data",result);
        model.put("token","123456");
        return new GPModelAndView("first.html",model);
    }
}
