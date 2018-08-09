package com.rjxx.taxeasy.configuration;

import com.rjxx.comm.utils.ApplicationContextUtils;
import com.rjxx.taxeasy.bizcomm.utils.InvoiceResponse;
import com.rjxx.taxeasy.bizcomm.utils.SkService;
import com.rjxx.taxeasy.domains.Cszb;
import com.rjxx.taxeasy.domains.Kpcf;
import com.rjxx.taxeasy.domains.Kpls;
import com.rjxx.taxeasy.service.CszbService;
import com.rjxx.taxeasy.service.KpcfService;
import com.rjxx.taxeasy.service.KplsService;
import com.rjxx.time.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

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
    @Autowired
    private KplsService kplsService;
    @Autowired
    private CszbService cszbService;
    @Autowired
    private KpcfService kpcfService;

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
                Timestamp allTime = TimeUtil.getNowDate();
                Kpls kpls=kplsService.findOne(kplsh);
                Cszb cszb=cszbService.getSpbmbbh(kpls.getGsdm(),kpls.getXfid(),kpls.getSkpid(),"kpfs");
                if(cszb.getCsz().equals("01")){
                    skService.callService(kplsh);
                }else if(cszb.getCsz().equals("03")){
                    if(!("09D103:发票领购信息已用完").equals(kpls.getErrorReason())){
                        //skService.SkServerKP(kplsh);
                        InvoiceResponse invoiceResponse = skService.SkServerQuery(kplsh);
                        if(null==invoiceResponse || (null !=invoiceResponse && !invoiceResponse.getReturnCode().equals("0000"))){
                            //调用税控服务器开票
                          InvoiceResponse invoiceResponse1 =  skService.SkServerKP(kplsh);

                          if (null !=invoiceResponse1 && invoiceResponse1.getReturnCode().equals("0000")){
                              //如果开票成功 删除开票重发表中对应的记录
                              kpcfService.deleteById(kplsh);
                          }
                        }else if(invoiceResponse.getReturnCode().equals("0000")  && null !=invoiceResponse.getFphm()){
                            //如果开票成功 删除开票重发表中对应的记录
                            kpcfService.deleteById(kplsh);

                        }
                    }
                }else if(cszb.getCsz().equals("04")){
                    skService.SkBoxKP(kplsh);
                }else if(cszb.getCsz().equals("05")){
                    skService.skEkyunKP(kplsh);
                }
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
