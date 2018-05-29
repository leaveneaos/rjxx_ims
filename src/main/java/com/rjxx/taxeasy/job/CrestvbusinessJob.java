package com.rjxx.taxeasy.job;

import com.rjxx.taxeasy.bizcomm.utils.SkService;
import com.rjxx.taxeasy.config.RabbitmqSend;
import com.rjxx.taxeasy.domains.Crestvbusiness;
import com.rjxx.taxeasy.domains.Kpls;
import com.rjxx.taxeasy.service.CrestvbusinessService;
import com.rjxx.taxeasy.service.KplsService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CrestvbusinessJob
 * @Description TODO
 * @Author 许黎明
 * @Date 2018-05-25 16:22
 * @Version 1.0
 **/
@DisallowConcurrentExecution
public class CrestvbusinessJob implements Job {

    @Autowired
    private SkService skService;

    @Autowired
    private KplsService kplsService;

    @Autowired
    private RabbitmqSend rabbitmqSend;

    @Autowired
    private CrestvbusinessService crestvbusinessService;

    private static Logger logger = LoggerFactory.getLogger(CrestvbusinessJob.class);


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
            logger.info("-------进入凯盈盒子断线重开定时任务开始---------"+context.getNextFireTime());
        try {
            Map map=new HashMap(1);
            List<Crestvbusiness> crestvbusinessServiceList=crestvbusinessService.findAllByParams(map);
            for(Crestvbusiness crestvbusiness:crestvbusinessServiceList){
                Kpls kpls=kplsService.findOne(Integer.valueOf(crestvbusiness.getKplsh()));
                if("0".equals(crestvbusiness.getMqbz())){
                    if("04".equals(kpls.getFpztdm())||"14".equals(kpls.getFpztdm())){
                        rabbitmqSend.sendbox(crestvbusiness.getKplsh()+"");
                        crestvbusiness.setMqbz("1");
                        crestvbusinessService.save(crestvbusiness);
                    }
                }else{
                    if("04".equals(kpls.getFpztdm())||"14".equals(kpls.getFpztdm())){
                        crestvbusiness.setMqbz("0");
                        crestvbusinessService.save(crestvbusiness);
                    }
                }
            }
            logger.info("-------进入凯盈盒子断线重开定时任务结束---------"+context.getNextFireTime());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
