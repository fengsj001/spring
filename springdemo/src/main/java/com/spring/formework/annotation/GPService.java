package com.spring.formework.annotation;

import java.lang.annotation.*;

/**
 * 2019/4/16
 * 创建人￥Jack
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GPService {
    String value() default "";
}
