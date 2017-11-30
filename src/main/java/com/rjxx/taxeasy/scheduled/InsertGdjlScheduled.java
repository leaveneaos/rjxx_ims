package com.rjxx.taxeasy.scheduled;

import com.rjxx.taxeasy.dao.GdjlJpaDao;
import com.rjxx.taxeasy.dao.XfJpaDao;
import com.rjxx.taxeasy.domains.Gdjl;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.service.InvoiceArchiveService;
import com.rjxx.utils.IOhelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by wangyahui on 2017/11/22 0022.
 * 插入归档记录表定时器
 */
@Component
public class InsertGdjlScheduled {

    @Autowired
    private GdjlJpaDao gdjlJpaDao;
    @Autowired
    private InvoiceArchiveService invoiceArchiveService;
    @Autowired
    private XfJpaDao xfJpaDao;

    @Value("${gd_file_path}")
    private String gdFilePath;
    @Value("${gd_file_path_day}")
    private String gdFilePathDay;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

//    @Scheduled(cron = "0 0 6 1 * ?")
    public void start(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH,-1);
        String lastMonth = calendar.getTime().toString();
        BufferedReader r = IOhelper.readString(gdFilePath);
        try {
            while(r.readLine()!=null){
                try {
                    Map result = invoiceArchiveService.getPDFPath(lastMonth, r.readLine());
                    if(result!=null){
                        Integer count = (Integer) result.get("count");
                        String pdfPath = (String) result.get("path");
                        Gdjl gdjl = new Gdjl();
                        gdjl.setYxbz("1");
                        gdjl.setLrsj(new Date());
                        gdjl.setZzrq(lastMonth);
                        gdjl.setWjsl(count);
                        gdjl.setXzlj(pdfPath);
                        Xf oneByXfsh = xfJpaDao.findOneByXfsh(r.readLine());
                        Integer xfid = oneByXfsh.getId();
                        gdjl.setXfid(xfid);
                        gdjl.setGsdm(oneByXfsh.getGsdm());
                        gdjlJpaDao.save(gdjl);
                    }else{
                        logger.warn("【发票归档】【"+r.readLine()+"】获取pdf数量为0或未知异常");
                        continue;
                    }
                } catch(Exception e){
                    e.printStackTrace();
                    logger.error("【发票归档】【"+r.readLine()+"】保存归档记录失败");
                }
            }
            logger.info("【发票归档】读取完毕");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("【发票归档】读取税号文件失败");
        }


        Calendar calendar_day = Calendar.getInstance();
        calendar_day.setTime(new Date());
        calendar_day.add(Calendar.DAY_OF_MONTH,-1);
        String yesterday = calendar_day.getTime().toString();
        BufferedReader re = IOhelper.readString(gdFilePathDay);
        try {
            while(re.readLine()!=null){
                try {
                    Map result = invoiceArchiveService.getPDFPath_Day(yesterday, re.readLine());
                    if(result!=null){
                        Integer count = (Integer) result.get("count");
                        String pdfPath = (String) result.get("path");
                        Gdjl gdjl = new Gdjl();
                        gdjl.setYxbz("1");
                        gdjl.setLrsj(new Date());
                        gdjl.setZzrq(yesterday);
                        gdjl.setWjsl(count);
                        gdjl.setXzlj(pdfPath);
                        Xf oneByXfsh = xfJpaDao.findOneByXfsh(re.readLine());
                        Integer xfid = oneByXfsh.getId();
                        gdjl.setXfid(xfid);
                        gdjl.setGsdm(oneByXfsh.getGsdm());
                        gdjlJpaDao.save(gdjl);
                    }else{
                        logger.warn("【发票归档】【"+re.readLine()+"】获取pdf数量为0或未知异常");
                        continue;
                    }
                } catch(Exception e){
                    e.printStackTrace();
                    logger.error("【发票归档】【"+re.readLine()+"】保存归档记录失败");
                }
            }
            logger.info("【发票归档】读取完毕");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("【发票归档】读取税号文件失败");
        }
    }
}
