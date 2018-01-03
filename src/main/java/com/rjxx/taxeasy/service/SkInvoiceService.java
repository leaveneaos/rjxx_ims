package com.rjxx.taxeasy.service;


import com.rjxx.taxeasy.db.DBUtils;
import com.rjxx.taxeasy.db.SqlFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 获取服务器信息Service
 * Created by Administrator on 2017-06-05.
 */
@Service("skInvoiceService")
public class SkInvoiceService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DBUtils dbUtils;

    @Autowired
    private SqlFactory sqlFactory;

    /**
     * 获取需要开票的数据
     *
     * @return
     */
    public List<Map<String, Object>> getFwqxxData(String jqbh,Connection connection) throws Exception {
        String sql1 = sqlFactory.getSqlMap().get("skInvoiceService.getFwqxx");
        List<String> params = new ArrayList<>();
        params.add(jqbh);
        List<Map<String, Object>> dataList = dbUtils.executeQuery(sql1, connection,params);
        return dataList;
    }
}
