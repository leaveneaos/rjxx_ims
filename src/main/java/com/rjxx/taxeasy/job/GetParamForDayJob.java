package com.rjxx.taxeasy.job;

import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.service.CsUserService;
import com.rjxx.utils.IOhelper;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * Created by wangyahui on 2017/12/18 0018.
 */
public class GetParamForDayJob implements Job{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CsUserService csUserService;

    @Value("${gd_file_path_day}")
    private String day_path;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("====get param sfgd for day==== start");
        List<Xf> xfs_day = csUserService.getXfsByCsm("sfgd", "日");
        if (xfs_day != null && xfs_day.size() > 0) {
            IOhelper.clearInfoForFile(day_path);
            for (Xf xf_day : xfs_day) {
                IOhelper.wirteString(day_path, xf_day.getXfsh());
            }
            logger.info("====get param sfgd for day==== over");
        } else {
            logger.error("====get param sfgd for day==== failure");
        }
    }
}
