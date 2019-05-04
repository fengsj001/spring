package com.spring.formework.webmvc.servlet;

import java.io.File;
import java.util.Locale;

/**
 * 创建人Jack
 * 涉及这个类的主要目的
 * 1、将一个静态文件变为一个动态文件
 * 2、根据用户传送参数不同，产生不同的结果
 * 最终输出字符串，交给Response
 */

public class GPViewResolver {

    private final String DEFAULT_TEMPLATE_SUFFIX = ".html";

    private File templateRootDir;
    private String viewName;

    public GPViewResolver(String templateRoot){
        String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();
        this.templateRootDir = new File(templateRootPath);
    }

    public GPview resolveViewName(String viewName, Locale locale){
        this.viewName = viewName;
        if(null == viewName || "".equals(viewName.trim())){return null;}
        viewName = viewName.endsWith(DEFAULT_TEMPLATE_SUFFIX) ? viewName : (viewName + DEFAULT_TEMPLATE_SUFFIX);
        File templaterFile = new File((templateRootDir.getPath() + "/" + viewName).replaceAll("/+",""));
        return new GPview(templaterFile);
    }

    public String getViewName(){
        return viewName;
    }

}
