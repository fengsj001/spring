package com.spring.formwork.aop.intercept;

/**
 * 创建人Jack
 */
public interface GPMethodInterceptor {
    Object invoke(GPMethodInterceptor mi) throws Throwable;
}
