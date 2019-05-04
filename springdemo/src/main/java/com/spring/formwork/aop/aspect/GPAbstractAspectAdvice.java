package com.spring.formwork.aop.aspect;

import java.lang.reflect.Method;

/**
 * 创建人Jack
 */
public class GPAbstractAspectAdvice {
    private Method aspectMethod;
    private Object aspectTarget;

    public GPAbstractAspectAdvice(Method aspectMethod, Object aspectTarget) {
        this.aspectMethod = aspectMethod;
        this.aspectTarget = aspectTarget;
    }
}
