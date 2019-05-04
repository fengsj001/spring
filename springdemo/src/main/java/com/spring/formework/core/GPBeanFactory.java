package com.spring.formework.core;

/**
 * 2019/4/16
 * 创建人￥Jack
 */
public interface GPBeanFactory {

    /**
     * 根据beanName从IOC容器之中获得一个实例Bean
     * @parem beanName
     * @return
     */
    Object getBean(String beanName);
}
