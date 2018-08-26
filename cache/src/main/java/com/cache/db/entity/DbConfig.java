package com.cache.db.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "db_config")
public class DbConfig {

    @Id
    private Integer dbId;

    private String dbPlatId;

    private String dbPasswd;

    private Date crtTime;

    public Integer getDbId() {
        return dbId;
    }

    public void setDbId(Integer dbId) {
        this.dbId = dbId;
    }

    public String getDbPlatId() {
        return dbPlatId;
    }

    public void setDbPlatId(String dbPlatId) {
        this.dbPlatId = dbPlatId;
    }

    public String getDbPasswd() {
        return dbPasswd;
    }

    public void setDbPasswd(String dbPasswd) {
        this.dbPasswd = dbPasswd;
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    @Override
    public String toString() {
        return "DbConfig{" +
                "dbId=" + dbId +
                ", dbPlatId='" + dbPlatId + '\'' +
                ", dbPasswd='" + dbPasswd + '\'' +
                ", crtTime=" + crtTime +
                '}';
    }
}
