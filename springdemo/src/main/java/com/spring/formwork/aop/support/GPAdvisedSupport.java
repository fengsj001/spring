package com.spring.formwork.aop.support;

import com.spring.formwork.aop.GPAopConfig;
import com.spring.formwork.aop.aspect.GPAfterReturningAdviceInterceptor;
import com.spring.formwork.aop.aspect.GPMethodBeforeAdviceInterceptor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 2019/4/19
 * 创建人￥Jack
 */
public class GPAdvisedSupport {

    private Class<?> targetClass;

    private Object target;

    private GPAopConfig config;

    private Pattern pointCutClassPattern;

    private transient Map<Method,List<Object>>methodCache;

    public GPAdvisedSupport(GPAopConfig config){this.config=config;}

    public Class<?> getTargetClass(){return this.targetClass;}



    public List<Object>getInterseptorAndDynamicInterceptionAdvice(Method method,Class<?> targetClass) throws NoSuchMethodException {
        List<Object> cached = methodCache.get(method);
        if(null == cached){
            Method m = targetClass.getMethod(method.getName(),method.getParameterTypes());
            cached = methodCache.get(m);

            //底层逻辑，对代理方法进行兼容
            this.methodCache.put(m,cached);
        }
        return cached;
    }


    public void setTargetClass(Class<?> targetClass){
        this.targetClass = targetClass;
        parse();
    }

    private void parse() {
        String pointCut = config.getPointCut().replaceAll("\\.","\\\\.")
                .replaceAll("\\\\.\\*",".*")
                .replaceAll("\\(","\\\\(")
                .replaceAll("\\)","\\\\)");
        //pointCut =public.*com.spring.demo.service..*Service..*(.*)
        //玩正则
        String pointCutForRegex = pointCut.substring(0,pointCut.lastIndexOf("\\(")-4);
        pointCutClassPattern = Pattern.compile("class" + pointCutForRegex.substring(
                pointCutForRegex.lastIndexOf(" ") + 1));
        try {
            methodCache = new HashMap<Method, List<Object>>();
            Pattern pattern = Pattern.compile(pointCut);

            Class aspectClass = Class.forName(this.config.getAspectClass());
            Map<String,Method> aspectMethods = new HashMap<String, Method>();
            for (Method m : aspectClass.getMethods()){
                String methodString = m.toString();
                if (methodString.contains("throws")){
                    methodString = methodString.substring(0,methodString.lastIndexOf("throws")).trim();
                }

                Matcher matcher = pattern.matcher(methodString);
                if(matcher.matches()){
                    //执行器链
                    List<Object> advices = new LinkedList<>();
                    //把每一个方法包装成MethodInerceptor
                    //before
                    if(!(null == config.getAspectBefore() || "".equals(config.getAspectBefore()))){
                        //创建一个advice
                        advices.add(new GPMethodBeforeAdviceInterceptor(aspectMethods.get(config.getAspectBefore()),aspectClass.newInstance()));
                    }

                    //after
                    if(!(null == config.getAspectAfter() || "".equals(config.getAspectAfter()))){
                        //创建一个Advice
                        advices.add(new GPAfterReturningAdviceInterceptor(aspectMethods.get(config.getAspectAfter()),aspectClass.newInstance()));
                    }
                    //afterThrowing
                    if(!(null == config.getAspectAfterThrow() || "".equals(config.getAspectAfter()))){
                        //创建一个Advice
                        advices.add(new GPAfterReturningAdviceInterceptor(aspectMethods.get(config.getAspectAfter()),aspectClass.newInstance()));
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }


    }

    public void setTarget(Object target) { this.target = target;}
}
