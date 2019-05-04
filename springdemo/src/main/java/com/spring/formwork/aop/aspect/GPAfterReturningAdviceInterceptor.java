package com.spring.formwork.aop.aspect;

import com.spring.formwork.aop.intercept.GPMethodInvocation;

import java.lang.reflect.Method;

/**
 * 创建人Jack
 */
public  class GPAfterReturningAdviceInterceptor extends GPAbstractAspectAdvice implements GPAdvice,GPMethodInterceptor {


    public GPAfterReturningAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod,aspectTarget);
    }

    @Override
    public Object invoke(GPMethodInvocation gpMethodInvocation) {
        return null;
    }
}
