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
                                            logger.info("fwk--回写返回为空，重新放入mq---"+kpls.getKplsh() + "_"+count);
                                            Map map = new HashMap();
                                            map.put("kplsh",kplsh);
                                            map.put("gsdm",kpls.getGsdm());
                                            //更新
                                            Fphxwsjl fphxwsjl1 = fphxwsjlService.findOneByParams(map);
                                            if(fphxwsjl1!=null){
                                                fphxwsjl1.setStartdate(new Date());
                                                fphxwsjl1.setEnddate(new Date());
                                                fphxwsjl1.setReturncode("9999");
                                                fphxwsjlService.save(fphxwsjl1);
                                            }else {
                                                Fphxwsjl fphxwsjl2 = new Fphxwsjl();
                                                fphxwsjl2.setGsdm("fwk");
                                                fphxwsjl2.setXfid(kpls.getXfid());
                                                fphxwsjl2.setSkpid(kpls.getSkpid());
                                                fphxwsjl2.setKplsh(kplsh);
                                                fphxwsjl2.setDdh(jyls.getDdh());
                                                fphxwsjl2.setEnddate(new Date());
                                                fphxwsjl2.setReturncode("9999");
                                                fphxwsjl2.setStartdate(new Date());
                                                fphxwsjl2.setSecretKey("");
                                                fphxwsjl2.setSign("");
                                                fphxwsjl2.setWsurl(gsxx.getSapcallbackurl());
                                                fphxwsjl2.setReturncontent(fwkReturnMessageStr);
                                                fphxwsjlService.save(fphxwsjl2);
                                            }
                                            rabbitmqSend.sendMsg("ErrorException_Callback", kpls.getFpzldm(), kpls.getKplsh() + "_"+count);
                                        }else {
                                            Map resultMap = generatePdfService.handerReturnMes(ss);
                                            String returnCode = resultMap.get("ReturnCode").toString();
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
                                            //fwk 、sap 不成功
                                            if((StringUtils.isBlank(note)|| !"Create operation was successful".equals(note))||(StringUtils.isBlank(returnCode)|| !"0000".equals(returnCode))){
                                                logger.info("sap--回写返回不成功或者 fwk回写返回不成功，放入mq---"+kpls.getKplsh() + "_"+count);
                                                rabbitmqSend.sendMsg("ErrorException_Callback", kpls.getFpzldm(), kpls.getKplsh() + "_"+count);
                                            }
                                            Map map = new HashMap();
                                            map.put("kplsh",kplsh);
                                            map.put("gsdm",kpls.getGsdm());
                                            Fphxwsjl fphxwsjl3 = fphxwsjlService.findOneByParams(map);
                                            //更新
                                            if(fphxwsjl3!=null){
                                                if((StringUtils.isBlank(note)|| !"Create operation was successful".equals(note))||(StringUtils.isBlank(returnCode)|| !"0000".equals(returnCode))){
                                                    fphxwsjl3.setReturncode("9999");
                                                }else {
                                                    fphxwsjl3.setReturncode("0000");
                                                }
                                                fphxwsjl3.setStartdate(new Date());
                                                fphxwsjl3.setEnddate(new Date());
                                                fphxwsjlService.save(fphxwsjl3);
                                            }else {
                                                Fphxwsjl fphxwsjl4 = new Fphxwsjl();
                                                fphxwsjl4.setGsdm("fwk");
                                                fphxwsjl4.setXfid(kpls.getXfid());
                                                fphxwsjl4.setSkpid(kpls.getSkpid());
                                                fphxwsjl4.setKplsh(kplsh);
                                                fphxwsjl4.setDdh(jyls.getDdh());
                                                fphxwsjl4.setEnddate(new Date());
                                                if((StringUtils.isBlank(note)|| !"Create operation was successful".equals(note))||(StringUtils.isBlank(returnCode)|| !"0000".equals(returnCode))){
                                                    fphxwsjl4.setReturncode("9999");
                                                }else {
                                                    fphxwsjl4.setReturncode("0000");
                                                }
                                                fphxwsjl4.setStartdate(new Date());
                                                fphxwsjl4.setSecretKey("");
                                                fphxwsjl4.setSign("");
                                                fphxwsjl4.setWsurl(gsxx.getSapcallbackurl());
                                                fphxwsjl4.setReturncontent(fwkReturnMessageStr);
                                                fphxwsjl4.setReturnmessage(Data);
                                                fphxwsjlService.save(fphxwsjl4);
                                            }
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
                                            logger.info("回写返回为空，放入mq---"+kpls.getKplsh() + "_"+count);
                                            Map map = new HashMap();
                                            map.put("kplsh",kplsh);
                                            map.put("gsdm",kpls.getGsdm());
                                            Fphxwsjl fphxwsjl5 = fphxwsjlService.findOneByParams(map);
                                            if(fphxwsjl5!=null){
                                                fphxwsjl5.setStartdate(new Date());
                                                fphxwsjl5.setEnddate(new Date());
                                                fphxwsjl5.setReturncode("9999");
                                                fphxwsjlService.save(fphxwsjl5);
                                            }else {
                                                Fphxwsjl fphxwsj6 = new Fphxwsjl();
                                                fphxwsj6.setGsdm(kpls.getGsdm());
                                                fphxwsj6.setEnddate(new Date());
                                                fphxwsj6.setReturncode("9999");
                                                fphxwsj6.setStartdate(new Date());
                                                fphxwsj6.setSecretKey(gsxx.getSecretKey());
                                                fphxwsj6.setSign(Secret);
                                                fphxwsj6.setWsurl(gsxx.getCallbackurl());
                                                fphxwsj6.setReturncontent(returnmessage);
                                                fphxwsjlService.save(fphxwsj6);
                                            }
                                            rabbitmqSend.sendMsg("ErrorException_Callback", kpls.getFpzldm(), kpls.getKplsh() + "_"+count);
                                        }else {
                                            String returnCode = returnMap.get("ReturnCode").toString();
                                            String returnMessage = returnMap.get("ReturnMessage").toString();
                                            //回写失败放入mq
                                            if(StringUtils.isBlank(returnCode)|| !"0000".equals(returnCode) || !"0".equals(returnCode)){
                                                logger.info("回写返回不成功，放入mq---"+kpls.getKplsh() + "_"+count);
                                                rabbitmqSend.sendMsg("ErrorException_Callback", kpls.getFpzldm(), kpls.getKplsh() + "_"+count);
                                            }
                                            Map map = new HashMap();
                                            map.put("kplsh",kplsh);
                                            map.put("gsdm",kpls.getGsdm());
                                            Fphxwsjl fphxwsjl7 = fphxwsjlService.findOneByParams(map);
                                            if(fphxwsjl7!=null){
                                                if(StringUtils.isBlank(returnCode)|| !"0000".equals(returnCode) || !"0".equals(returnCode)){
                                                    fphxwsjl7.setReturncode("9999");
                                                }else {
                                                    fphxwsjl7.setReturncode("0000");
                                                }
                                                fphxwsjl7.setStartdate(new Date());
                                                fphxwsjl7.setEnddate(new Date());
                                                fphxwsjlService.save(fphxwsjl7);
                                            }else {
                                                Fphxwsjl fphxwsjl8 = new Fphxwsjl();
                                                fphxwsjl8.setGsdm(kpls.getGsdm());
                                                fphxwsjl8.setEnddate(new Date());
                                                if(StringUtils.isBlank(returnCode)|| !"0000".equals(returnCode) || !"0".equals(returnCode)){
                                                    fphxwsjl8.setReturncode("9999");
                                                }else {
                                                    fphxwsjl8.setReturncode("0000");
                                                }
                                                fphxwsjl8.setStartdate(new Date());
                                                fphxwsjl8.setSecretKey(gsxx.getSecretKey());
                                                fphxwsjl8.setSign(Secret);
                                                fphxwsjl8.setWsurl(gsxx.getCallbackurl());
                                                fphxwsjl8.setReturncontent(returnmessage);
                                                fphxwsjl8.setReturnmessage(returnMessage);
                                                fphxwsjlService.save(fphxwsjl8);
                                            }
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
