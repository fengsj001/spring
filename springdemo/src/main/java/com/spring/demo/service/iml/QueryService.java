package com.spring.demo.service.iml;

import com.spring.demo.service.IQueryService;
import com.spring.formework.annotation.GPService;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 查询业务
 * 创建人Jack
 */
@GPService
@Slf4j
public class QueryService implements IQueryService {

    /**
     * 查询
     * @param name
     * @return
     */
    @Override
    public String query(String name) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        String time = sdf.format(new Date());
        String json = "{name:\"" + name + "\",time:\"" + time + "\"}";
        log.info("这是在业务方法中打印的：" + json);
        return json;
    }
}
