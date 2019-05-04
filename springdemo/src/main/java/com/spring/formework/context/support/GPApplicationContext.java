package com.spring.formework.context.support;

import com.spring.formework.beans.config.GPBeanDefinition;
import com.spring.formework.core.GPBeanFactory;
import com.srping.formework.context.GPBeanDefinitionReader;

import java.util.List;
import java.util.Map;

/**
 * 按之前源码分析的套路，IOC、DI 、MVC、AOP
 *
 * 创建人￥Jack
 */
public class GPApplicationContext extends GPDefaultListableBeanFactory implements GPBeanFactory {

    private String [] configLocations;
    private GPBeanDefinitionReader reader;
    
    public GPApplicationContext(String... configLocations){
        this.configLocations = configLocations;
        try {
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void refresh() throws Exception {
        //1、定位，定位配置文件
        reader = new GPBeanDefinitionReader(this.configLocations);
        
        //2、加载配置文件，扫描相关的类，把它们封装成BeanDefinition
        List<GPBeanDefinition> beanDefinitions = reader.loadBeanDefinitions();
        
        //3、注册，把配置信息放到容器里面（伪IOC容器）
        doRegisterBeanDefinition(beanDefinitions);

        //4、把不是延时加载的类，有提前初始化
        doAutwrited();
    }

    //只处理非延时加载的情况
    private void doAutwrited() {
        for(Map.Entry<String,GPBeanDefinition> beanDefinitionEntry : super.beanDefinitionMap.entrySet()){
            String beanName = beanDefinitionEntry.getKey();
            if(!beanDefinitionEntry.getValue().isLazyInit()){
                getBean(beanName);
            }
        }
    }

    private void doRegisterBeanDefinition(List<GPBeanDefinition> beanDefinitions) throws Exception {

        for(GPBeanDefinition beanDefinition : beanDefinitions) {
            if(super.beanDefinitionMap.containsKey(beanDefinition.getFactoryBeanName())){
                throw new Exception("The“" + beanDefinition.getFactoryBeanName() + "”is exists!!");
            }
            super.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(),beanDefinition);
        }
        //到这里为止，容器初始化完毕
    }

    //依赖注入，从这里开始，通过读取BeanDifinition中的信息
    //然后，通过反射机制创建一个实例并返回
    //Spring做法是不会吧罪原始的对象放出去，会用一个BeanWrapper来进行一次包装
    //装饰器模式：
    //1、保留原来的oop关系
    //2、我需要对他进行扩展，增强（为以后aop打基础）
    @Override
    public Object getBean(String beanName) {
        return null;
    }
}
