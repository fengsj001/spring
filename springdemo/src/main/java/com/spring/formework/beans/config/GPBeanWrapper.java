package com.spring.formework.beans.config;

/**
 * 2019/4/16
 * 创建人￥Jack
 */
public class GPBeanWrapper {

    private Object wrappedInstance;
    private Class<?> wrappedClass;

    public GPBeanWrapper(Object wrappedInstance){
        this.wrappedInstance = wrappedInstance;
    }

    //返回代理以后的Class
    //可能会是这个$Proxy0
    public Class<?> getWrappedClass(){
        return this.wrappedInstance.getClass();
    }
}
