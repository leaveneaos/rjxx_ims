package com.rjxx.taxeasy.job;

import com.alibaba.fastjson.JSON;
import com.rjxx.taxeasy.bizcomm.utils.GeneratePdfService;
import com.rjxx.taxeasy.bizcomm.utils.HttpUtils;
import com.rjxx.taxeasy.config.RabbitmqUtils;
import com.rjxx.taxeasy.domains.*;
import com.rjxx.taxeasy.service.*;
import com.rjxx.utils.StringUtils;
import com.rjxx.utils.XmltoJson;
import org.apache.commons.codec.digest.DigestUtils;
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
    @Autowired
    private CszbService cszbService;
    @Autowired
    private JylsService jylsService;



    private static Logger logger = LoggerFactory.getLogger(ErrorExceptionCallback.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            logger.info("-------进入定时任务开始---------"+context.getNextFireTime());
            do {
                String kplshStr = (String) rabbitmqSend.receiveMsg("ErrorException_Callback", "12");
                if (StringUtils.isNotBlank(kplshStr)) {
                    logger.info("回写失败---开票流水号"+kplshStr);
                    if(kplshStr.indexOf("_")>=0){
                        String[] split = kplshStr.split("_");
                        String s0 = split[0];
                        String s1 = split[1];
                        int kplsh = Integer.valueOf(s0);
                        //int kplsh = Integer.valueOf(kplshStr);
                        Map params = new HashMap();
                        params.put("kplsh", kplsh);
                        Kpls kpls = kplsService.findOneByParams(params);
                        Jyls jyls = jylsService.findOne(kpls.getDjh());
                        Map parms = new HashMap();
                        parms.put("gsdm", kpls.getGsdm());
                        Gsxx gsxx = gsxxService.findOneByParams(parms);
                        if(Integer.valueOf(s1)<=4){
                            Integer count = Integer.valueOf(s1)+ 1;
                            //继续回写，重试3次
                            String url = gsxx.getCallbackurl();
                            String returnmessage = "";
//                            if (kpls.getFpzldm().equals("12") && kpls.getGsdm().equals("Family")) {
//                                returnmessage = generatePdfService.CreateReturnMessage2(kpls.getKplsh());
//                            }else {
//                                returnmessage = generatePdfService.CreateReturnMessage(kpls.getKplsh());
//                            }
                            if (kpls.getFpzldm().equals("12") && kpls.getGsdm().equals("Family")) {
                                returnmessage = generatePdfService.CreateReturnMessage2(kpls.getKplsh());
                            } else if (kpls.getFpzldm().equals("12") && kpls.getGsdm().equals("mcake")) {
                                returnmessage = generatePdfService.CreateReturnMessage(kpls.getKplsh());
                            }else if (kpls.getFpzldm().equals("12") && (kpls.getGsdm().equals("fwk")||kpls.getGsdm().equals("bqw"))) {
                                returnmessage = generatePdfService.CreateReturnMessage3(kpls.getKplsh());
                            }else {
                                Cszb cszb = cszbService.getSpbmbbh(gsxx.getGsdm(), null, null, "callBackType");
                                String callbacktype = cszb.getCsz();
                                if(callbacktype!=null){
                                    if("9".equals(callbacktype)){
                                        returnmessage = generatePdfService.createJsonMsg(kpls.getKplsh());
                                    }
                                }else{
                                    returnmessage = generatePdfService.CreateReturnMessage(kpls.getKplsh());
                                }
                            }
                            //输出调用结果
                            logger.info("回写报文" + returnmessage);
//                            if (returnmessage != null && !"".equals(returnmessage)) {
//                                Map returnMap = generatePdfService.httpPost(returnmessage, kpls);
//                                logger.info("返回报文" + JSON.toJSONString(returnMap));
//                            }
//                            if(kpls.getGsdm().equals("fwk")){
//                                returnmessage = generatePdfService.CreateReturnMessage3(kpls.getKplsh());
//                                logger.info("回写报文" + returnmessage);
//                                if (returnmessage != null && !"".equals(returnmessage)) {
//                                    try {
//                                        String ss = generatePdfService.netWebService(url, "CallBack", returnmessage, gsxx.getAppKey(), gsxx.getSecretKey());
//                                        String fwkReturnMessageStr = generatePdfService.fwkReturnMessage(kpls);
//                                        logger.info("----------sap回写报文----------" + fwkReturnMessageStr);
//                                        String Data = HttpUtils.doPostSoap1_2(gsxx.getSapcallbackurl(), fwkReturnMessageStr, null, "Wendy", "Welcome9");
//                                        logger.info("----------fwk平台回写返回报文--------" + ss);
//                                        logger.info("----------sap回写返回报文----------" + Data);
//                                        Fphxwsjl fphxwsjl = new Fphxwsjl();
//                                        fphxwsjl.setGsdm("fwk");
//                                        fphxwsjl.setEnddate(new Date());
//                                        fphxwsjl.setReturncode("0000");
//                                        fphxwsjl.setStartdate(new Date());
//                                        fphxwsjl.setSecretKey("");
//                                        fphxwsjl.setSign("");
//                                        fphxwsjl.setWsurl(gsxx.getSapcallbackurl());
//                                        fphxwsjl.setReturncontent(fwkReturnMessageStr);
//                                        fphxwsjl.setReturnmessage(Data);
//                                        fphxwsjlService.save(fphxwsjl);
//                                    }catch (Exception e){
//                                        e.printStackTrace();
//                                        rabbitmqSend.sendMsg("ErrorException_Callback", kpls.getFpzldm(), kpls.getKplsh() + "");
//                                    }
//                                }
//                            }
                            if (returnmessage != null && !"".equals(returnmessage)) {
                                if(kpls.getGsdm().equals("afb")){
                                    Map returnMap = generatePdfService.httpPostNoSign(returnmessage, kpls);
                                    logger.info("返回报文" + JSON.toJSONString(returnMap));
                                }if(kpls.getGsdm().equals("fwk")){
                                    try {
                                        String ss = generatePdfService.netWebService(url, "CallBack", returnmessage, gsxx.getAppKey(), gsxx.getSecretKey());
                                        String fwkReturnMessageStr = generatePdfService.fwkReturnMessage(kpls);
                                        logger.info("----------sap回写报文----------" + fwkReturnMessageStr);
                                        String Data = HttpUtils.doPostSoap1_2(gsxx.getSapcallbackurl(), fwkReturnMessageStr, null, "Deepak", "Welcome0");
                                        logger.info("----------fwk平台回写返回报文--------" + ss);
                                        logger.info("----------sap回写返回报文----------" + Data);
                                        //回写失败放入mq
                                        if(StringUtils.isBlank(ss) || StringUtils.isBlank(Data)){
                                            logger.info("fwk--回写返回为空，放入mq---");
                                            Fphxwsjl fphxwsjl = new Fphxwsjl();
                                            fphxwsjl.setGsdm("fwk");
                                            fphxwsjl.setXfid(kpls.getXfid());
                                            fphxwsjl.setSkpid(kpls.getSkpid());
                                            fphxwsjl.setKplsh(kplsh);
                                            fphxwsjl.setDdh(jyls.getDdh());
                                            fphxwsjl.setEnddate(new Date());
                                            fphxwsjl.setReturncode("9999");
                                            fphxwsjl.setStartdate(new Date());
                                            fphxwsjl.setSecretKey("");
                                            fphxwsjl.setSign("");
                                            fphxwsjl.setWsurl(gsxx.getSapcallbackurl());
                                            fphxwsjl.setReturncontent(fwkReturnMessageStr);
                                            fphxwsjl.setReturnmessage(Data);
                                            fphxwsjlService.save(fphxwsjl);
                                            rabbitmqSend.sendMsg("ErrorException_Callback", kpls.getFpzldm(), kpls.getKplsh() + "_"+count);
                                        }else {
                                            Map resultMap = generatePdfService.handerReturnMes(ss);
                                            String returnCode = resultMap.get("ReturnCode").toString();
                                            if(StringUtils.isBlank(returnCode)|| !"0000".equals(returnCode)){
                                                logger.info("fwk--回写返回不成功，放入mq---");
                                                rabbitmqSend.sendMsg("ErrorException_Callback", kpls.getFpzldm(), kpls.getKplsh() + "_"+count);
                                            }
                                            //解析sap返回值
                                            String note = "";
                                            try {
                                                String jsonString= XmltoJson.xml2json(Data);
                                                Map dataMap=XmltoJson.strJson2Map(jsonString);
                                                Map Envelope=(Map)dataMap.get("env:Envelope");
                                                Map Body=(Map)Envelope.get("env:Body");
                                                Map GoldenTaxGoldenTaxCreateConfirmation_sync=(Map)Body.get("n0:GoldenTaxGoldenTaxCreateConfirmation_sync");
                                                Map Log=(Map)GoldenTaxGoldenTaxCreateConfirmation_sync.get("Log");
                                                Map item = (Map)Log.get("Item");
                                                note = (String) item.get("Note");
                                            } catch (Exception e) {
                                                logger.info("解析sap失败");
                                            }
                                            if(StringUtils.isBlank(note)|| !"Create operation was successful".equals(note)){
                                                logger.info("sap--回写返回不成功，放入mq---");
                                                rabbitmqSend.sendMsg("ErrorException_Callback", kpls.getFpzldm(), kpls.getKplsh() + "_"+count);
                                            }
                                            Fphxwsjl fphxwsjl = new Fphxwsjl();
                                            fphxwsjl.setGsdm("fwk");
                                            fphxwsjl.setXfid(kpls.getXfid());
                                            fphxwsjl.setSkpid(kpls.getSkpid());
                                            fphxwsjl.setKplsh(kplsh);
                                            fphxwsjl.setDdh(jyls.getDdh());
                                            fphxwsjl.setEnddate(new Date());
                                            if((StringUtils.isBlank(returnCode)|| !"0000".equals(returnCode)) || (StringUtils.isBlank(note)||!"Create operation was successful".equals(note))){
                                                fphxwsjl.setReturncode("9999");
                                            }else {
                                                fphxwsjl.setReturncode("0000");
                                            }
                                            fphxwsjl.setStartdate(new Date());
                                            fphxwsjl.setSecretKey("");
                                            fphxwsjl.setSign("");
                                            fphxwsjl.setWsurl(gsxx.getSapcallbackurl());
                                            fphxwsjl.setReturncontent(fwkReturnMessageStr);
                                            fphxwsjl.setReturnmessage(Data);
                                            fphxwsjlService.save(fphxwsjl);
                                        }
                                    }catch (Exception e){
                                        e.printStackTrace();
                                        rabbitmqSend.sendMsg("ErrorException_Callback", kpls.getFpzldm(), kpls.getKplsh() + "_"+count);
                                    }
                                } else{
                                    try{
                                        Map returnMap = generatePdfService.httpPost(returnmessage, kpls);
                                        logger.info("返回报文" + JSON.toJSONString(returnMap));
                                        String Secret = this.getSign(returnmessage, gsxx.getSecretKey());
                                        if(returnMap==null){
                                            logger.info("回写返回为空，放入mq---");
                                            Fphxwsjl fphxwsjl = new Fphxwsjl();
                                            fphxwsjl.setGsdm(kpls.getGsdm());
                                            fphxwsjl.setEnddate(new Date());
                                            fphxwsjl.setReturncode("9999");
                                            fphxwsjl.setStartdate(new Date());
                                            fphxwsjl.setSecretKey(gsxx.getSecretKey());
                                            fphxwsjl.setSign(Secret);
                                            fphxwsjl.setWsurl(gsxx.getCallbackurl());
                                            fphxwsjl.setReturncontent(returnmessage);
                                            fphxwsjlService.save(fphxwsjl);
                                            rabbitmqSend.sendMsg("ErrorException_Callback", kpls.getFpzldm(), kpls.getKplsh() + "_"+count);
                                        }else {
                                            String returnCode = returnMap.get("ReturnCode").toString();
                                            String returnMessage = returnMap.get("ReturnMessage").toString();
                                            //回写失败放入mq
                                            if(StringUtils.isBlank(returnCode)|| !"0000".equals(returnCode)){
                                                logger.info("回写返回不成功，放入mq---");
                                                rabbitmqSend.sendMsg("ErrorException_Callback", kpls.getFpzldm(), kpls.getKplsh() + "_"+count);
                                            }
                                            Fphxwsjl fphxwsjl = new Fphxwsjl();
                                            fphxwsjl.setGsdm(kpls.getGsdm());
                                            fphxwsjl.setEnddate(new Date());
                                            fphxwsjl.setReturncode(returnCode);
                                            fphxwsjl.setStartdate(new Date());
                                            fphxwsjl.setSecretKey(gsxx.getSecretKey());
                                            fphxwsjl.setSign(Secret);
                                            fphxwsjl.setWsurl(gsxx.getCallbackurl());
                                            fphxwsjl.setReturncontent(returnmessage);
                                            fphxwsjl.setReturnmessage(returnMessage);
                                            fphxwsjlService.save(fphxwsjl);
                                        }
                                    }catch (Exception e){
                                        e.printStackTrace();
                                        rabbitmqSend.sendMsg("ErrorException_Callback", kpls.getFpzldm(), kpls.getKplsh() + "_"+count);
                                    }
                                }
                            }
                        }
                    }else {
                        //重新放回队列  kplsh_1
                        int kplsh = Integer.valueOf(kplshStr);
                        Map params = new HashMap();
                        params.put("kplsh", kplsh);
                        Kpls kpls = kplsService.findOneByParams(params);
                        logger.info("队列中的开票流水号没有加_1"+kplsh);
                        rabbitmqSend.sendMsg("ErrorException_Callback", kpls.getFpzldm(), kpls.getKplsh() + "_1");
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

    private static String getSign(String QueryData, String key) {
        String signSourceData = "data=" + QueryData + "&key=" + key;
        String newSign = DigestUtils.md5Hex(signSourceData);
        return newSign;
    }
}
