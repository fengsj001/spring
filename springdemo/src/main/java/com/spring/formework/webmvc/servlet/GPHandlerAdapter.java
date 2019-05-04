package com.spring.formework.webmvc.servlet;

import com.spring.formework.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 创建人Jack
 */
//专人干专事
public class GPHandlerAdapter {

    public boolean supports(Object handler){
        return (handler instanceof  GPHandlerMapping);
    }

    public  GPModelAndView handle(HttpServletRequest req, HttpServletResponse resp,Object handler) throws InvocationTargetException, IllegalAccessException {
        GPHandlerMapping handlerMapping = (GPHandlerMapping) handler;

        //每一个方法有一个参数列表，那么这里保存的是形参列表
        Map<String,Integer> paramMapping = new HashMap<String,Integer>();

        //这里只是出来了命名参数
        Annotation[][] pa = handlerMapping.getMethod().getParameterAnnotations();

        for (int i = 0; i < pa.length ; i++) {
            for(Annotation a : pa[i]){
                if(a instanceof RequestParam){
                    String paramName = ((RequestParam)a).value();
                    if(!"".equals(paramName.trim())){
                        paramMapping.put(paramName,i);
                    }
                }
            }
        }

        //根据用户请求的参数信息，跟Method中的参数信息进行动态匹配
        //resq传进来的目的只有一个：只是为了将其赋值给方法参数，仅此而已

        //只有当用户传过来的modelandview为空的时候，才会new一个默认的

        //1、要准备好这个方法的形参列表
        //方法重载；形参的决定因素：参数的个数、参数的类型、参数顺序、方法的名字
        //只处理Request和ResPonse
        Class<?>[] paramTypes = handlerMapping.getMethod().getParameterTypes();
        for(int i =0;i < paramTypes.length; i++){
            Class<?> type = paramTypes[i];
            if(type == HttpServletRequest.class ||
                type == HttpServletResponse.class){
                paramMapping.put(type.getName(),i);
            }
            }

            //2、拿到自定义命名参数所占的位置
            //用户通过url传过来的参数列表
            Map<String,String[]> reqParameterMap = req.getParameterMap();

            //3、构造实参列表
            Object [] paramValues = new Object[paramTypes.length];

            for(Map.Entry<String,String[]> param : reqParameterMap.entrySet()){
                String value =
                        Arrays.toString(param.getValue()).replaceAll("\\[|\\]","").replaceAll("\\s","");

                if(!paramMapping.containsKey(param.getKey())){continue;}

                int index = paramMapping.get(param.getKey());

                //因为页面上传过来的值都是String类型的，而在方法中定义的类型是千变万化的
                //要针对我们传过来的参数进行类型转换
                paramValues[index] = caseStringValue(value,paramTypes[index]);
            }

            if(paramMapping.containsKey(HttpServletRequest.class.getName())) {
                int reqIndex = paramMapping.get(HttpServletRequest.class.getName());
                paramValues[reqIndex] = req;
            }

            if(paramMapping.containsKey(HttpServletResponse.class.getName())) {
                int respIndex = paramMapping.get(HttpServletResponse.class.getName());
                paramValues[respIndex] = resp;
            }

            //4、从handler中取出Controller、method，然后利用反射机制调用
            Object result = handlerMapping.getMethod().invoke(handlerMapping.getController(),paramValues);
            if(null == result) {return null;}
            boolean isModelAndView = handlerMapping.getMethod().getReturnType() == GPModelAndView.class;
            if(isModelAndView){
                return (GPModelAndView)result;
            }else{
                return null;
            }
        }

    private Object caseStringValue(String value, Class<?> paramType) {
        if(paramType == String.class){
            return value;
        }
        //如果是int
        if(Integer.class == paramType){
            return  Integer.valueOf(value);
        }else if(Double.class == paramType){
            return Double.valueOf(value);
        }else{
            if(value != null){
                return value;
            }
            return null;
        }
        //如果还有double或者其他类型，继续加if，策略模式
    }
}

