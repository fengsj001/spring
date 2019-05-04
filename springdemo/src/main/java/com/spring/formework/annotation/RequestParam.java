package com.spring.formework.annotation;

import java.lang.annotation.*;

/**
 * 2019/4/16
 * 创建人￥Jack
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestParam {

    String value() default "";
}
