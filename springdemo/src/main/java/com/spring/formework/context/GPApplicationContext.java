package com.spring.formework.context;

import com.spring.formework.beans.config.GPBeanDefinition;
import com.spring.formework.beans.config.GPBeanWrapper;
import com.spring.formework.context.support.GPBeanDefinitionReader;
import com.spring.formework.context.support.GPDefaultListableBeanFactory;
import com.spring.formework.core.GPBeanFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 创建人Jack
 */
public class GPApplicationContext extends GPDefaultListableBeanFactory implements GPBeanFactory {

    private String [] configlocations;
    private GPBeanDefinitionReader reader;

    //单例的IOC容器缓存
    private Map<String,Object> factoryBeanObjectCache = new ConcurrentHashMap<String,Object>();
    //通用的IOC容器
    private Map<String, GPBeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<String,GPBeanWrapper>();

    public GPApplicationContext(String... initParameter) {
    }

    public String[] getBeanDefinitionNames() {
    }

    public Object getBean(String beanName) {
    }
}
