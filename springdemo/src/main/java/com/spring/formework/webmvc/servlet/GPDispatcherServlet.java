package com.spring.formework.webmvc.servlet;

import com.spring.formework.annotation.Controller;
import com.spring.formework.annotation.RequestMapping;
import com.spring.formework.context.GPApplicationContext;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 *
 * 创建人Jack
 */
@Slf4j
public class GPDispatcherServlet extends HttpServlet {

    private final String CONTEXT_CONFIG_LOCATION = "contextConfigLocation";

    private GPApplicationContext context;

    private List<GPHandlerMapping> handlerMappings = new ArrayList<GPHandlerMapping>();

    private Map<GPHandlerMapping,GPHandlerAdapter> handlerAdapters = new HashMap<GPHandlerMapping, GPHandlerAdapter>();

    private List<GPViewResolver> viewResolvers = new ArrayList<GPViewResolver>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doDispatch(req, resp);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        //相当于把IOC容器初始化了
        context = new GPApplicationContext(config.getInitParameter(CONTEXT_CONFIG_LOCATION));
        initStrategies(context);
    }

    private void initStrategies(GPApplicationContext context) {

        //有九种侧罗
        //针对于每个用户请求，都会经过一些处理的侧罗之后，最终才能有结果输出
        //每种侧罗可以自定义干预，但是最终的结果都是一致

        //============= 这里就是传说中的九大组件 =================

        //文件上传解析，如果请求类型是multipart将通过MultipartResolver进行文件上传解析
        initMultipartResolver(context);
        //本地化解析
        initLocaleResolver(context);
        //主题解析
        initThemeRsolver(context);

        /**自己实现*/
        //GPHandlerMapping 用来保存Controller中配置的RequestMapping和Method的一个对应关系
        //通过HandlerMapping，将请求映射到处理器
        initHandlerMappings(context);
        /**自己实现*/
        //HandlerAdapters用来动态匹配Method参数，包括类转换，动态赋值
        initHandlerAdapters(context);
        //如果执行中有异常则交给HandlerExceptionResolver来解析
        initHandlerExceptionResolvers(context);
        //直接解析请求到的视图名
        initRequestToViewNameTranslator(context);
        /**自己实现*/
        //通过viewResolver来实现动态模板解析
        //自己解析一套模板语言
        //通过viewResolver解析逻辑视图到具体视图实现
        initViewResolvers(context);
        //flash映射管理
        initFlashMapManager(context);
    }

    private void initViewResolvers(GPApplicationContext context) {}

    private void initFlashMapManager(GPApplicationContext context) {}

    private void initRequestToViewNameTranslator(GPApplicationContext context) {}

    private void initHandlerExceptionResolvers(GPApplicationContext context) {}

    private void initHandlerAdapters(GPApplicationContext context) {}
//将Controller中配置的RequestMapping和method一一对应
    private void initHandlerMappings(GPApplicationContext context) {
        //按照我们通常的理解应该是一个map
        //Map<String,Method> map;
        //method.put(url,Method);

        //首先从容器中取到所有的实例
        String [] beanNames = context.getBeanDefinitionNames();
        for(String beanName : beanNames){
            //到了MVC层，对外提供的方法只有一个getBean（）方法
            //返回的对象不是beanWrapper，怎么办
            Object controller = context.getBean(beanName);
            Class<?> clazz = controller.getClass();

            if(!clazz.isAnnotationPresent(Controller.class)){
                continue;
            }
            String baseUrl = "";

            if(clazz.isAnnotationPresent(RequestMapping.class)){
                RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
                baseUrl = requestMapping.value();
            }

            //获取Method的url配置
            Method [] methods = clazz.getMethods();
            for (Method method : methods) {
                //没有加RequestMapping注解的直接忽略
                if(!method.isAnnotationPresent(RequestMapping.class)){continue;}
                //映射URL
                RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);


                String regex = ("/" + baseUrl + "/" + requestMapping.value().replaceAll("\\*",".*")).replaceAll("/+","/");
                Pattern pattern = Pattern.compile(regex);

                this.handlerMappings.add(new GPHandlerMapping(pattern,controller,method));
                log.info("Mapped" + regex + "," + method);
            }
        }

    }

    private void initThemeRsolver(GPApplicationContext context) {}

    private void initLocaleResolver(GPApplicationContext context) {}

    private void initMultipartResolver(GPApplicationContext context) {}

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) {}
}
