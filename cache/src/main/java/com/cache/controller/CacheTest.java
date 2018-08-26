package com.cache.controller;

import com.alibaba.fastjson.JSONObject;
import com.cache.db.entity.DbConfig;
import com.cache.service.DbConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CacheTest {

    @Autowired
    DbConfigService configService;

    @RequestMapping("getDbList")
    public JSONObject getList() {
        Long startTime = System.currentTimeMillis();
        List<DbConfig> configs = configService.getDBList();
        Long endTime = System.currentTimeMillis();
        return resultData(configs, "数据库查询时间 == " + (endTime - startTime));
    }

    @RequestMapping("buildData")
    public String buildData() {
        Long startTime = System.currentTimeMillis();
        configService.BuildData();
        Long endTime = System.currentTimeMillis();
        return "构建数据花费时间 == " + (endTime - startTime);
    }

    @RequestMapping("addRedis")
    public void addRedis(){
        configService.addRedis();
    }

    @RequestMapping("getRedisList")
    public JSONObject getRedisList() {
        Long startTime = System.currentTimeMillis();
        List<DbConfig> configs = configService.getRedisList();
        Long endTime = System.currentTimeMillis();
        return resultData(configs, "redis查询时间 == " + (endTime - startTime));
    }

    private JSONObject resultData(List<DbConfig> configs, String msg) {
        JSONObject obj = new JSONObject();
        obj.put("msg", msg);
        obj.put("data", configs);
        return obj;
    }
}
