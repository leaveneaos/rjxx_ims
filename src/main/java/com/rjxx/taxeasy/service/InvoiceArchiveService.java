package com.rjxx.taxeasy.service;

import com.rjxx.taxeasy.dao.KplsJpaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangyahui on 2017/11/21 0021.
 */
@Service("invoiceArchiveService")
public class InvoiceArchiveService {

    @Value("${gd_request_Path}")
    private String path;

    @Autowired
    private KplsJpaDao kplsJpaDao;

    public Map getPDFPath(String date, String taxNo){
        try {
            Integer count = kplsJpaDao.countByLrsj(date, taxNo);
            if(count==null||count==0){
                return null;
            }
            String pdfPath = path + taxNo + "/" + taxNo + "-" + date + "-" + count;
            Map<String,Object> map = new HashMap();
            map.put("path", pdfPath);
            map.put("count", count);
            return map;
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Map getPDFPath_Day(String date, String taxNo){
        try {
            Integer count = kplsJpaDao.countByLrsjDay(date, taxNo);
            if(count==null||count==0){
                return null;
            }
            String pdfPath = path +"day/"+ taxNo + "/" + taxNo + "-" + date + "-" + count;
            Map<String,Object> map = new HashMap();
            map.put("path", pdfPath);
            map.put("count", count);
            return map;
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
