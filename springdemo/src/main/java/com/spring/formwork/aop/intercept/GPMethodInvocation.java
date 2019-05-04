package com.spring.formwork.aop.intercept;

import com.spring.formwork.aop.aspect.GPJoinPint;
import com.spring.formwork.aop.aspect.GPMethodInterceptor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建人Jack
 */
public class GPMethodInvocation implements GPJoinPint {

    private Object proxy;
    private Method method;
    private Object target;
    private Object[] arguments;
    private List<Object> interceptorsAndDynamicMatchers;
    private Class<?> targetClass;

    private Map<String,Object> userAttributes;

    //定义一个索引，从-1开始来记录当前拦截器执行的位置
    private int currentInterceptorIndex = -1;


    public GPMethodInvocation(
            Object proxy, Object target, Method method, Object[] arguments,
            Class<Object> targetClass, List<Object> interceptorsAndDynamicMethodMatchers) {
        this.proxy = proxy;
        this.target = target;
        this.targetClass = targetClass;
        this.method = method;
        this.arguments = arguments;
        this.interceptorsAndDynamicMatchers = interceptorsAndDynamicMatchers;
    }

    public Object procced() throws Exception {
        //如果Interceptor执行完了，则执行joinPoint
        if(this.currentInterceptorIndex == this.interceptorsAndDynamicMatchers.size() - 1){
            return this.method.invoke(this.target,this.arguments);
        }

        Object interceptorInterceptionAdvice =
                this.interceptorsAndDynamicMatchers.get(++this.currentInterceptorIndex);
        //如果要动态匹配joinPoint
        if(interceptorInterceptionAdvice instanceof GPMethodInterceptor){
            GPMethodInterceptor mi = (GPMethodInterceptor) interceptorInterceptionAdvice;
            return mi.invoke(this);
        }else{
            //动态匹配失败时，略过当前Interceptor，调用下一个Interceptor
            return procced();
        }
    }
    @Override
    public Object getThis(){return this.target;}

    @Override
    public Object[] getArguments() {
        return new Object[0];
    }

    @Override
    public Method getMethod() {
        return null;
    }

  public void  setUserAttribute(String key,Object value){
        if (null != value){
            if (null == this.userAttributes){
                this.userAttributes = new HashMap<String,Object>();
            }
            this.userAttributes.put(key,value);
        }else{
            if ( null != this.userAttributes){
                this.userAttributes.remove(key);
            }
        }
  }

   public Object getUserAttribute(String key) {
        return (this.userAttributes != null ? this.userAttributes.get(key) : null);
   }
}
