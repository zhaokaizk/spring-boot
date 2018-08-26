package com.cache.service;

import com.cache.db.entity.DbConfig;
import com.cache.db.mapper.DbConfigMapper;
import com.cache.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DbConfigService {

    private static String buildContent = "AaBbCcDdEeFfGg" +
            "\n" +
            "HhIiJjKkLlMmNn" +
            "\n" +
            "OoPpQqRrSsTt\n" +
            "\n" +
            "UuVvWwXxYyZz!@#$%^&*";
    private static Integer[] buildNumber = new Integer[]{1,2,3,4,5,6,7,8,9};

    private static String db_config_list = "db_config_list";

    @Autowired
    DbConfigMapper dbConfigMapper;
    @Autowired
    RedisUtils redisUtils;

    /**
     * 直接查询数据库
     */
    public List<DbConfig> getDBList() {
        return dbConfigMapper.selectAll();
    }

    /**
     * 从redis中查询
     */
    public List<DbConfig> getRedisList() {
        return redisUtils.getList(db_config_list, DbConfig.class);
    }

    /**
     * 添加到reids
     */
    public void addRedis() {
        List<DbConfig> dbConfigs = dbConfigMapper.selectAll();
        redisUtils.addList(db_config_list, dbConfigs);
    }

    /**
     * 批量创建数据
     */
    public void BuildData() {
        dbConfigMapper.insertList(batchCreateData());
    }

    private List<DbConfig> batchCreateData() {
        List<DbConfig> configList = new ArrayList<>();
        for(int i = 0; i < 1000; i++) {
            DbConfig config = new DbConfig();
            config.setDbPlatId(buildString());
            config.setDbPasswd(buildString());
            config.setCrtTime(new Date());
            configList.add(config);
        }
        return configList;
    }

    private String buildString() {
        StringBuffer sb = new StringBuffer();
        for(int i =0; i <= 10; i++) {
            int next = (int)(Math.random()*64);
            if(next == buildContent.length()) {
                next = next - 2;
            }
            sb.append(buildContent.substring(next, next + 1));
        }
        for(int i =0; i<= 5; i++) {
            int next = (int)(Math.random()*9);
            if(next == buildNumber.length) {
                next = next -1;
            }
            sb.append(buildNumber[next]);
        }
        return sb.toString();
    }

}
