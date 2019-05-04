package com.spring.formework.context.support;

/**
 * 2019/4/16
 * 创建人￥Jack
 */
public abstract class GPAbstractApplicationContext {

    //受保护，只提供给子类重写
    public void refresh()  throws Exception {}
}
