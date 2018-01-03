package com.rjxx.taxeasy.db;


import com.rjxx.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-05-26.
 */
@Service("dbUtils")
public class DBUtils {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 返回执行结果，按字段驼峰规则
     *
     * @param sql
     * @param connection
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> executeQuery(String sql, Connection connection) throws Exception {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        List<Map<String, Object>> retList = resultSet2List(resultSet);
        closeConnection(null, statement, resultSet);
        return retList;
    }

    /**
     * 返回执行结果，按字段驼峰规则
     *
     * @param sql
     * @param connection
     * @param params
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> executeQuery(String sql, Connection connection, List<String> params) throws Exception {
        if (params == null || params.isEmpty()) {
            return executeQuery(sql, connection);
        }
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int i = 1;
        for (String param : params) {
            preparedStatement.setString(i, param);
            i++;
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Map<String, Object>> retList = resultSet2List(resultSet);
        closeConnection(null, preparedStatement, resultSet);
        return retList;
    }

    /**
     * 结果集转换成list
     *
     * @param resultSet
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> resultSet2List(ResultSet resultSet) throws Exception {
        ResultSetMetaData rsmd = resultSet.getMetaData();
        List<Map<String, Object>> retList = new ArrayList<>();
        int columnCount = rsmd.getColumnCount();
        while (resultSet.next()) {
            Map<String, Object> map = new HashMap<>();
            retList.add(map);
            for (int i = 1; i <= columnCount; i++) {
                String columnName = rsmd.getColumnName(i);
                Object value = resultSet.getObject(i);
                map.put(StringUtils.underlineToCamel2(columnName), value);
            }
        }
        return retList;
    }

    /**
     * 执行更新
     *
     * @param sql
     * @param connection
     * @param params
     * @return
     * @throws Exception
     */
    public int executeUpdate(String sql, Connection connection, List<String> params) throws Exception {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int i = 1;
        for (String param : params) {
            preparedStatement.setString(i, param);
            i++;
        }
        int ret = preparedStatement.executeUpdate();
        closeConnection(null, preparedStatement, null);
        return ret;
    }

    /**
     * 关闭数据库连接
     *
     * @param connection
     * @param statement
     * @param resultSet
     */
    public void closeConnection(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            logger.error("", e);
        }
    }

}
