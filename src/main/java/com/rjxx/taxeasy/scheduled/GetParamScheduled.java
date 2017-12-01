package com.rjxx.taxeasy.scheduled;

import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.service.CsUserService;
import com.rjxx.utils.IOhelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wangyahui on 2017/11/23 0023.
 * 获取参数写入文件定时器
 */
@Component
public class GetParamScheduled {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CsUserService csUserService;
    @Value("${gd_file_path}")
    private String gdFilePath;
    @Value("${gd_file_path_day}")
    private String gdFilePathDay;

    @Scheduled(cron = "0 0 13 1 * ?")
    public void getSfgd(){
    logger.info("--------get param start---------");
        List<Xf> xfs = csUserService.getXfsByCsm("sfgd", "月");
        if(xfs!=null){
            IOhelper.clearInfoForFile(gdFilePath);
            for(Xf xf:xfs){
                IOhelper.wirteString(gdFilePath,xf.getXfsh());
            }
            logger.info("----------over-----------");
        }else{
            logger.error("------get xf list faild------");
        }

        List<Xf> xfs_day = csUserService.getXfsByCsm("sfgd", "日");
        if(xfs_day!=null){
            IOhelper.clearInfoForFile(gdFilePathDay);
            for(Xf xf_day:xfs_day){
                IOhelper.wirteString(gdFilePathDay,xf_day.getXfsh());
            }
            logger.info("===========over============");
        }else{
            logger.error("===========get xf list faild===========");
        }

    }
}
