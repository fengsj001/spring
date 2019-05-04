package com.spring.formework.context.support;

import com.spring.formework.beans.config.GPBeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 创建人￥Jack
 */
public class GPDefaultListableBeanFactory extends GPAbstractApplicationContext{

    //存储注册信息的BeanDefinition
    protected final Map<String, GPBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String,GPBeanDefinition>();

}
