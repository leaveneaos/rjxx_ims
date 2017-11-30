package com.rjxx.taxeasy.scheduled;

import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.service.CsUserService;
import com.rjxx.utils.IOhelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

//    @Scheduled(cron = "0 0 1 1 * ?")
    public void getSfgd(){
        List<Xf> xfs = csUserService.getXfsByCsm("sfgd", "月");
        if(xfs!=null){
            IOhelper.clearInfoForFile(gdFilePath);
            for(Xf xf:xfs){
                IOhelper.wirteString(gdFilePath,xf.getXfsh());
            }
        }else{
            logger.error("【写入税号文件】获取销方列表失败");
        }

        List<Xf> xfs_day = csUserService.getXfsByCsm("sfgd", "日");
        if(xfs_day!=null){
            IOhelper.clearInfoForFile(gdFilePathDay);
            for(Xf xf:xfs_day){
                IOhelper.wirteString(gdFilePathDay,xf.getXfsh());
            }
        }else{
            logger.error("【写入税号文件】获取销方列表失败");
        }

    }
}
