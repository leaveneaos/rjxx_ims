package com.rjxx.taxeasy.db;

import java.util.Map;

/**
 * Created by Administrator on 2017-06-16.
 */
public class SqlFactory {

    private Map<String, String> sqlMap;

    public Map<String, String> getSqlMap() {
        return sqlMap;
    }

    public void setSqlMap(Map<String, String> sqlMap) {
        this.sqlMap = sqlMap;
    }
}
