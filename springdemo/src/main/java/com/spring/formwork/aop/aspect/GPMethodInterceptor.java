package com.spring.formwork.aop.aspect;

import com.spring.formwork.aop.intercept.GPMethodInvocation;

/**
 * 创建人￥Jack
 */
public interface GPMethodInterceptor {
    Object invoke(GPMethodInvocation gpMethodInvocation);
}
