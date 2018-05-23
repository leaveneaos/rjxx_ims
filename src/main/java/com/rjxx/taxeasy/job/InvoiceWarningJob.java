package com.rjxx.taxeasy.job;


import com.rjxx.taxeasy.bizcomm.utils.SaveMessage;
import com.rjxx.taxeasy.bizcomm.utils.SendalEmail;
import com.rjxx.taxeasy.service.*;
import com.rjxx.taxeasy.vo.FpkcYjtzVo;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created by kzx on 2018/05/04.
 */
@DisallowConcurrentExecution
public class InvoiceWarningJob implements Job {

    @Autowired
    private FpkcYztzService fpkcYztzService;

    @Autowired
    private SendalEmail SendalEmail;

    @Autowired
    private SaveMessage saveMessage;


    private static Logger logger = LoggerFactory.getLogger(InvoiceWarningJob.class);

    /**
     * 服务器发票预警定时任务
     * @param context
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
                logger.info("-------进入库存预警定时任务开始---------"+context.getNextFireTime());
                List<FpkcYjtzVo> tzList = fpkcYztzService.findAllTzList(new HashMap());
                for (int i= 0;i<tzList.size();i++){
                    FpkcYjtzVo fpkcYjtzVo = tzList.get(i);
                    if(fpkcYjtzVo.getTzfs().contains("02") && fpkcYjtzVo.getTzfs().contains("03")){
                        //发邮件
                        String [] to=new String[1];
                        to[0]=fpkcYjtzVo.getEmail();
                        SendalEmail.sendEmail(null, fpkcYjtzVo.getGsdm(),to[0] , "发票库存预警",null, fpkcYjtzVo.getTzy(), "发票库存预警通知");
                        //短信
                        Map<String, String> rep = new HashMap();
                        rep.put("code", "051898");
                        rep.put("product", "容津信息");
                        saveMessage.sendMessage(fpkcYjtzVo.getGsdm(), null, fpkcYjtzVo.getPhone(), rep, "SMS_34725005", "发票库存预警");
                    }else if(fpkcYjtzVo.getTzfs().contains("02")){
                        //发邮件
                        String [] to=new String[1];
                        to[0]=fpkcYjtzVo.getEmail();
                        SendalEmail.sendEmail(null, fpkcYjtzVo.getGsdm(),to[0] , "发票库存预警",null, fpkcYjtzVo.getTzy(), "发票库存预警通知");
                    }else if(fpkcYjtzVo.getTzfs().contains("03")){
                        //短信
                        Map<String, String> rep = new HashMap();
                        rep.put("code", "051898");
                        rep.put("product", "容津信息");
                        saveMessage.sendMessage(fpkcYjtzVo.getGsdm(), null, fpkcYjtzVo.getPhone(), rep, "SMS_34725005", "发票库存预警");
                    }
                }

            logger.info("-------进入库存预警定时任务结束---------"+context.getNextFireTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
