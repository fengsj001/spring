package com.spring.demo.service.iml;

import com.spring.demo.service.IModifyService;

/**
 * 2019/4/18
 * 创建人￥Jack
 */
public class ModifyService implements IModifyService {
    public String add(String name, String addr){
        return "modifyService add,name=" + name + ",addr=" + addr;
    }

    @Override
    public String edit(Integer id, String name) {
        return "modifyService edit ,id=" + id + ",name" + name;
    }

    @Override
    public String remove(Integer id) {
        return "modifyService id=" + id;
    }

}
