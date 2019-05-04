package com.spring.demo.service;

/**
 * 2019/4/18
 * 创建人￥Jack
 */
public interface IModifyService {
    /**
     * 增加
     */
    public String add(String name,String addr);

    public String edit(Integer id, String name);

    public String remove(Integer id);
}
