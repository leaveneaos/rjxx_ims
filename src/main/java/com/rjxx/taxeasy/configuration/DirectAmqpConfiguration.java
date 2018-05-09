package com.rjxx.taxeasy.configuration;

import com.rjxx.comm.utils.ApplicationContextUtils;
import com.rjxx.taxeasy.bizcomm.utils.SkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * @ClassName DirectAmqpConfiguration
 * @Description TODO
 * @Author 许黎明
 * @Date 2018-05-09 10:18
 * @Version 1.0
 **/
@Component
@RabbitListener(queues = "queue_ErrorException_Sk_12")
public class DirectAmqpConfiguration {

    private Logger logger = LoggerFactory.getLogger(DirectAmqpConfiguration.class);

    @Autowired
    private SkService skService;

    /**
     * 线程池执行任务
     */
    private static ThreadPoolTaskExecutor taskExecutor = null;
    /**
     * 多线程执行生成pdf
     */
    public class MessageTask implements Runnable {

        private int kplsh;
        @Override
        public void run() {
            logger.info("------多线程接收MQ消息----------");
            try {
                skService.SkServerKP(kplsh);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public void setKplsh(int kplsh) {
            this.kplsh = kplsh;
        }
    }

    @RabbitHandler
    public void receive(String message) {
        try{
            logger.info("--------收到异常重开MQ消息:-------- " + message);
            MessageTask messageTask=new MessageTask();
            messageTask.setKplsh(Integer.valueOf(message));
            if (taskExecutor == null) {
                taskExecutor = ApplicationContextUtils.getBean(ThreadPoolTaskExecutor.class);
            }
            taskExecutor.execute(messageTask);
        }catch(Exception e){
            logger.info("--------发送失败信息给用户邮箱，或发送短信，推送消息--------"+e.getMessage());
            e.printStackTrace();
        }
    }
}
