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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by wangyahui on 2017/11/22 0022.
 * 插入归档记录表定时器
 */
@Component
public class InsertGdjlForDayScheduled {

    @Autowired
    private GdjlJpaDao gdjlJpaDao;
    @Autowired
    private InvoiceArchiveService invoiceArchiveService;
    @Autowired
    private XfJpaDao xfJpaDao;

    @Value("${gd_file_path_day}")
    private String gdFilePathDay;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Scheduled(cron = "0 30 2 * * ?")
    public void start(){
        logger.info("[insert t_gdjl for day] start");
        Calendar calendar_day = Calendar.getInstance();
        calendar_day.setTime(new Date());
        calendar_day.add(Calendar.DAY_OF_MONTH,-1);
        String yesterday = new SimpleDateFormat("yyyyMMdd").format(calendar_day.getTime());
        BufferedReader re = IOhelper.readString(gdFilePathDay);
        try {
            String line_day="";
            while((line_day=re.readLine())!=null){
                if("".equals(line_day)){
                    continue;
                }
                try {
                    Map result = invoiceArchiveService.getPDFPath_Day(yesterday, line_day);
                    if(result!=null){
                        Integer count = (Integer) result.get("count");
                        String pdfPath = (String) result.get("path");
                        Gdjl gdjl = new Gdjl();
                        gdjl.setYxbz("1");
                        gdjl.setLrsj(new Date());
                        gdjl.setZzrq(yesterday);
                        gdjl.setWjsl(count);
                        gdjl.setXzlj(pdfPath);
                        Xf oneByXfsh = xfJpaDao.findOneByXfsh(line_day).get(0);
                        Integer xfid = oneByXfsh.getId();
                        gdjl.setXfid(xfid);
                        gdjl.setGsdm(oneByXfsh.getGsdm());
                        gdjlJpaDao.save(gdjl);
                    }else{
                        logger.warn("[insert t_gdjl for day] ["+line_day+"]pdfcount is 0 or error");
                        continue;
                    }
                } catch(Exception e){
                    e.printStackTrace();
                    logger.error("[insert t_gdjl for day] ["+line_day+"]save t_gdjl error");
                }
            }
            logger.info("[insert t_gdjl for day] read over");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("[insert t_gdjl for day] read error");
        }
    }
}
