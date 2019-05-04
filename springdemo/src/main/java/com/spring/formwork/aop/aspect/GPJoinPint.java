package com.spring.formwork.aop.aspect;

import java.lang.reflect.Method;

/**
 * 创建人Jack
 */
public interface GPJoinPint {

    Object getThis();
    Object[] getArguments();

    Method getMethod();
    void setUserAttribute(String key,Object value);

    Object getUserAttribute(String key);
}
