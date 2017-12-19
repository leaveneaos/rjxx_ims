package com.rjxx.taxeasy.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangyahui on 2017/11/21 0021.
 */
@Service("invoiceArchiveService")
public class InvoiceArchiveService {

    @Value("${gd_request_Path}")
    private String requestPath;

    @Value("${gd_zip_path}")
    private String zipPath;

    @Value("${gd_zip_path_day}")
    private String zipPathDay;

    public Map getPDFPath(String date, String taxNo) {
        try {
            String count = "";
            File file = new File(zipPath + "/" + taxNo);
            File[] dir = file.listFiles();
            for (int i = 0; i < dir.length; i++) {
                String name = dir[i].getName();
                if (name.indexOf(taxNo + "-" + date) != -1) {
                    int j = name.indexOf(".");
                    int k = name.lastIndexOf("-");
                    count = name.substring(k + 1, j);
                    break;
                }
            }
            String pdfPath = requestPath + taxNo + "/" + taxNo + "-" + date + "-" + count + ".zip";
            Map<String, Object> map = new HashMap();
            map.put("path", pdfPath);
            map.put("count", count);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map getPDFPath_Day(String date, String taxNo) {
        try {
            String count = "";
            File file = new File(zipPathDay + "/" + taxNo);
            File[] dir = file.listFiles();
            for (int i = 0; i < dir.length; i++) {
                String name = dir[i].getName();
                if (name.indexOf(taxNo + "-" + date) != -1) {
                    int j = name.indexOf(".");
                    int k = name.lastIndexOf("-");
                    count = name.substring(k + 1, j);
                    break;
                }
            }
            String pdfPath = requestPath + "day/" + taxNo + "/" + taxNo + "-" + date + "-" + count + ".zip";
            Map<String, Object> map = new HashMap();
            map.put("path", pdfPath);
            map.put("count", count);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
