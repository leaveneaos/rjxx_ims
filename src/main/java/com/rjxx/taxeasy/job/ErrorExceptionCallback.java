package com.rjxx.taxeasy.job;

import com.alibaba.fastjson.JSON;
import com.rjxx.taxeasy.bizcomm.utils.GeneratePdfService;
import com.rjxx.taxeasy.bizcomm.utils.HttpUtils;
import com.rjxx.taxeasy.config.RabbitmqUtils;
import com.rjxx.taxeasy.domains.Fphxwsjl;
import com.rjxx.taxeasy.domains.Gsxx;
import com.rjxx.taxeasy.domains.Kpls;
import com.rjxx.taxeasy.service.FphxwsjlService;
import com.rjxx.taxeasy.service.GsxxService;
import com.rjxx.taxeasy.service.KplsService;
import com.rjxx.utils.StringUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xlm on 2017/12/14.
 */
@DisallowConcurrentExecution
public class ErrorExceptionCallback implements Job {

    @Autowired
    private RabbitmqUtils rabbitmqSend;

    @Autowired
    private KplsService kplsService;

    @Autowired
    private GeneratePdfService generatePdfService;

    @Autowired
    private GsxxService gsxxService;

    @Autowired
    private FphxwsjlService fphxwsjlService;



    private static Logger logger = LoggerFactory.getLogger(ErrorExceptionCallback.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            logger.info("-------进入定时任务开始---------"+context.getNextFireTime());
            do {
                String kplshStr = (String) rabbitmqSend.receiveMsg("ErrorException_Callback", "12");
                if (StringUtils.isNotBlank(kplshStr)) {
                    int kplsh = Integer.valueOf(kplshStr);
                    Map params = new HashMap();
                    params.put("kplsh", kplsh);
                    Kpls kpls = kplsService.findOneByParams(params);
                    Map parms = new HashMap();
                    parms.put("gsdm", kpls.getGsdm());
                    Gsxx gsxx = gsxxService.findOneByParams(parms);
                    String url = gsxx.getCallbackurl();
                    String returnmessage = "";
                    if (kpls.getFpzldm().equals("12") && kpls.getGsdm().equals("Family")) {
                        returnmessage = generatePdfService.CreateReturnMessage2(kpls.getKplsh());
                    }else {
                        returnmessage = generatePdfService.CreateReturnMessage(kpls.getKplsh());
                    }
                    //输出调用结果
                    logger.info("回写报文" + returnmessage);
                    if (returnmessage != null && !"".equals(returnmessage)) {
                            Map returnMap = generatePdfService.httpPost(returnmessage, kpls);
                            logger.info("返回报文" + JSON.toJSONString(returnMap));
                    }
                    if(kpls.getGsdm().equals("fwk")){
                        returnmessage = generatePdfService.CreateReturnMessage3(kpls.getKplsh());
                        logger.info("回写报文" + returnmessage);
                        if (returnmessage != null && !"".equals(returnmessage)) {
                            try {
                                String ss = generatePdfService.netWebService(url, "CallBack", returnmessage, gsxx.getAppKey(), gsxx.getSecretKey());
                                String fwkReturnMessageStr = generatePdfService.fwkReturnMessage(kpls);
                                logger.info("----------sap回写报文----------" + fwkReturnMessageStr);
                                String Data = HttpUtils.doPostSoap1_2(gsxx.getSapcallbackurl(), fwkReturnMessageStr, null, "Wendy", "Welcome9");
                                logger.info("----------fwk平台回写返回报文--------" + ss);
                                logger.info("----------sap回写返回报文----------" + Data);
                                Fphxwsjl fphxwsjl = new Fphxwsjl();
                                fphxwsjl.setGsdm("fwk");
                                fphxwsjl.setEnddate(new Date());
                                fphxwsjl.setReturncode("0000");
                                fphxwsjl.setStartdate(new Date());
                                fphxwsjl.setSecretKey("");
                                fphxwsjl.setSign("");
                                fphxwsjl.setWsurl(gsxx.getSapcallbackurl());
                                fphxwsjl.setReturncontent(fwkReturnMessageStr);
                                fphxwsjl.setReturnmessage(Data);
                                fphxwsjlService.save(fphxwsjl);
                            }catch (Exception e){
                                e.printStackTrace();
                                rabbitmqSend.sendMsg("ErrorException_Callback", kpls.getFpzldm(), kpls.getKplsh() + "");
                            }
                        }
                    }
                } else {
                    break;
                }
                logger.info("-------进入定时任务结束---------"+context.getNextFireTime());
            } while (true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
