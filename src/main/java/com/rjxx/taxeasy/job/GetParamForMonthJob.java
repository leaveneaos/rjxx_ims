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
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wangyahui on 2017/11/23 0023.
 * 获取参数写入文件定时器
 */
@Component
public class GetParamForMonthJob implements Job {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CsUserService csUserService;

    @Value("${gd_file_path}")
    private String month_path;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("====get param sfgd for month==== start");
        List<Xf> xfs = csUserService.getXfsByCsm("sfgd", "月");
        if (xfs != null && xfs.size() > 0) {
            IOhelper.clearInfoForFile(month_path);
            for (Xf xf : xfs) {
                IOhelper.wirteString(month_path, xf.getXfsh());
            }
            logger.info("====get param sfgd for month==== over");
        } else {
            logger.error("====get param sfgd for month==== failure");
        }
    }
}
