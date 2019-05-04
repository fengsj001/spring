package com.spring.formework.annotation;

import java.lang.annotation.*;

/**
 * 创建人Jack
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
    String value() default "";
}
