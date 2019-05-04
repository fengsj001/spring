package com.spring.demo.action;

import com.spring.demo.service.IModifyService;
import com.spring.demo.service.IQueryService;
import com.spring.formework.annotation.Autowired;
import com.spring.formework.annotation.Controller;
import com.spring.formework.annotation.RequestMapping;
import com.spring.formework.annotation.RequestParam;
import com.spring.formework.webmvc.servlet.GPModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 公布接口url
 * 创建人Jack
 */
@Controller
@RequestMapping("/web")
public class MyAction {

    @Autowired
    IQueryService queryService;
    @Autowired
    IModifyService modifyService;

    @RequestMapping("/query.json")
    public GPModelAndView query(HttpServletRequest request, HttpServletResponse response, @RequestParam("name")String name){
        String result = queryService.query(name);
        return out(response,request,result);
    }

    @RequestMapping("/add.json")
    public GPModelAndView add(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") Integer id){
        String result = modifyService.remove(id);
        return out(response,request,result);
    }
    @RequestMapping("/remove.json")
    public GPModelAndView remove(HttpServletResponse response, HttpServletRequest request, @RequestParam("id") Integer id){
        String result = modifyService.remove(id);
        return out(response,request,result);
    }
    @RequestMapping("/edit.json")
    public GPModelAndView edit(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") Integer id,
                               @RequestParam("name") String name){
        String result = modifyService.edit(id,name);
        return out(response,request,result);
    }



    private GPModelAndView out(HttpServletResponse response, HttpServletRequest request, String str) {
        try {
            response.getWriter().write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
