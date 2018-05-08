package com.rjxx.taxeasy.job;


import com.rjxx.taxeasy.bizcomm.utils.MailService;
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
    private MailService mailService;


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
                        String [] to=new String[1];
                        to[0]=fpkcYjtzVo.getEmail();
                        mailService.sendSimpleMail(to,"发票库存预警",fpkcYjtzVo.getTzy());
                    }else if(fpkcYjtzVo.getTzfs().contains("02")){
                        //发邮件
                        String [] to=new String[1];
                        to[0]=fpkcYjtzVo.getEmail();
                        mailService.sendSimpleMail(to,"发票库存预警",fpkcYjtzVo.getTzy());
                    }else if(fpkcYjtzVo.getTzfs().contains("03")){
                        //短信
                    }
                }

            logger.info("-------进入库存预警定时任务结束---------"+context.getNextFireTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
