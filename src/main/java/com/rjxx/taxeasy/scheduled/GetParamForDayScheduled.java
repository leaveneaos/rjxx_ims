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
 * Created by wangyahui on 2017/12/18 0018.
 */
@Component
public class GetParamForDayScheduled {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CsUserService csUserService;

    @Value("${gd_file_path_day}")
    private String day_path;

    @Scheduled(cron = "0 0 1 * * ?")
    public void getSfgd() {
        logger.info("[get param sfgd for day] start");
        List<Xf> xfs_day = csUserService.getXfsByCsm("sfgd", "日");
        if (xfs_day != null) {
            IOhelper.clearInfoForFile(day_path);
            for (Xf xf_day : xfs_day) {
                IOhelper.wirteString(day_path, xf_day.getXfsh());
            }
            logger.info("[get param sfgd for day] over");
        } else {
            logger.error("[get param sfgd for day] failure");
        }
    }
}
