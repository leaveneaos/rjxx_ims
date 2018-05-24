package com.rjxx.taxeasy.job;

import com.rjxx.taxeasy.bizcomm.utils.SkService;
import com.rjxx.taxeasy.config.RabbitmqSend;
import com.rjxx.taxeasy.service.CszbService;
import com.rjxx.taxeasy.service.KplsService;
import com.rjxx.taxeasy.service.SkpService;
import com.rjxx.utils.StringUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName BoxKpJob
 * @Description TODO
 * @Author 许黎明
 * @Date 2018-05-24 11:00
 * @Version 1.0
 **/
@DisallowConcurrentExecution
public class BoxKpJob implements Job {

    @Autowired
    private RabbitmqSend rabbitmqSend;
    @Autowired
    private KplsService kplsService;
    @Autowired
    private SkpService skpService;
    @Autowired
    private CszbService cszbService;
    @Autowired
    private SkService skService;

    private static Logger logger = LoggerFactory.getLogger(BoxKpJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        try {
            logger.info("-------进入定时任务开始---------"+context.getNextFireTime());
            String kplshStr = (String) rabbitmqSend.receivebox();
            if (StringUtils.isNotBlank(kplshStr)) {
                int kplsh = Integer.valueOf(kplshStr);
                skService.SkBoxKP(kplsh);
            }
            logger.info("-------进入定时任务结束---------"+context.getNextFireTime());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
