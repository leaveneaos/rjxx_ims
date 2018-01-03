package com.rjxx.taxeasy.db;


import com.rjxx.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;

/**
 * Created by Administrator on 2017-05-26.
 */
public class DBFactory {

    private static Logger logger = LoggerFactory.getLogger(DBFactory.class);

    private String driveClass;

    private String jdbcUrl;

    private String username;

    private String password;

    private Map<String, String> sqlFactory;

    public Connection getConnection() throws Exception {
        Class.forName(driveClass);
        Connection connection = null;
        if (StringUtils.isBlank(username)) {
            connection = DriverManager.getConnection(jdbcUrl);
        } else {
            connection = DriverManager.getConnection(jdbcUrl, username, password);
        }
        return connection;
    }

    public String getDriveClass() {
        return driveClass;
    }

    public void setDriveClass(String driveClass) {
        this.driveClass = driveClass;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, String> getSqlFactory() {
        return sqlFactory;
    }

    public void setSqlFactory(Map<String, String> sqlFactory) {
        this.sqlFactory = sqlFactory;
    }
}
