package com.rjxx.taxeasy.job;

import com.rabbitmq.client.Channel;
import com.rjxx.taxeasy.bizcomm.utils.DealQueueUtil;
import com.rjxx.taxeasy.config.RabbitmqUtils;
import com.rjxx.taxeasy.domains.Dlclb;
import com.rjxx.taxeasy.domains.Tqmtq;
import com.rjxx.taxeasy.service.DlclbService;
import com.rjxx.taxeasy.service.TqmtqService;
import com.rjxx.utils.StringUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.support.PublisherCallbackChannel;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;

/**
 * Created by kzx on 2017/10/23.
 * 异常队列处理机制定时任务
 */
@DisallowConcurrentExecution
public class QueueProcessingJob implements Job {

    @Autowired
    private RabbitmqUtils rabbitmqUtils;

    @Autowired
    private DlclbService dlclbService;

    @Autowired
    private DealQueueUtil dealQueueUtil;

    @Autowired
    private TqmtqService tqmtqService;

    private static Logger logger = LoggerFactory.getLogger(QueueProcessingJob.class);


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            logger.info("-------进入队列处理定时任务开始---------" + context.getNextFireTime());
            boolean flag = true;
            //数据库取出需要进行处理的队列名及处理对应业务类型队列的后台方法
            List<Dlclb> dlclbList = dlclbService.findAllByParams(new HashMap());
            if (null != dlclbList && !dlclbList.isEmpty()) {
                for (int i = 0; i < dlclbList.size(); i++) {
                    Dlclb dlclb = dlclbList.get(i);
                    //String clff = dlclb.getClff();//获取处理队列数据需要调动的后台方法
                    Channel channel = ((PublisherCallbackChannel) rabbitmqUtils.getChannel()).getDelegate();
                    String QueueName = rabbitmqUtils.getQueueName(dlclb.getDlmc(), "");
                    int queueCount = (int) channel.messageCount(QueueName);
                    int count = 0;
                    //测试
                    /*if(queueCount==0){
                        dlclbService.sendQueueMessage(dlclb.getDlmc(), "25", 1);
                    }*/
                    while (flag && count<=queueCount) {
                        count++;
                        String result = (String) rabbitmqUtils.receiveMsg(dlclb.getDlmc(), "");
                        if (StringUtils.isNotBlank(result)) {
                            String[] dataArray = result.split("-");
                            if(dlclb.getDlmc().equals("Queue_wsc")){
                                Tqmtq tqmtq = tqmtqService.findOne(Integer.valueOf(dataArray[0]));
                                if(null !=tqmtq && !tqmtq.equals("")){
                                    dealQueueUtil.dealQueueData(dataArray[0],Integer.valueOf(dataArray[1]),dlclb.getDlmc(),tqmtq.getGsdm());
                                }else {
                                    flag = false;
                                }
                            }else{
                                dealQueueUtil.dealQueueData(dataArray[0],Integer.valueOf(dataArray[1]),dlclb.getDlmc(),"");
                            }
                        } else {
                            flag = false;
                        }
                    }
                }
            }
            logger.info("-------进入队列处理定时任务结束---------" + context.getNextFireTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
