package com.rjxx.taxeasy.job;


import com.alibaba.fastjson.JSON;
import com.rjxx.taxeasy.bizcomm.utils.GeneratePdfService;
import com.rjxx.taxeasy.bizcomm.utils.GetXfxx;
import com.rjxx.taxeasy.bizcomm.utils.HttpUtils;
import com.rjxx.taxeasy.dao.XsqdJpaDao;
import com.rjxx.taxeasy.domains.*;
import com.rjxx.taxeasy.invoice.KpService;
import com.rjxx.taxeasy.service.*;
import com.rjxx.taxeasy.vo.Spvo;
import com.rjxx.utils.StringUtils;
import com.rjxx.utils.XmltoJson;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by xlm on 2017/8/2.
 */
@Service
public class FwkCallBackJob implements Job {

    private static Logger logger = LoggerFactory.getLogger(FwkCallBackJob.class);

    @Autowired
    private GeneratePdfService generatePdfService;
    @Autowired
    private GsxxService gsxxService;
    @Autowired
    private KplsService kplsService;


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("福维克开票数据重新回写,nextFireTime:{},"+context.getNextFireTime());
            try {
                Map map = new HashMap();
                map.put("gsdm","fwk");
                map.put("lrsj","2018-07-06");
                List<Kpls> list = kplsService.findAllByFwk(map);
                for(int i=0;i<list.size();i++){
                    Kpls kpls = list.get(i);
                    Map parms = new HashMap();
                    parms.put("gsdm", "fwk");
                    Gsxx gsxx = gsxxService.findOneByParams(parms);
                    String url = gsxx.getCallbackurl();
                    String returnmessage = generatePdfService.CreateReturnMessage(kpls.getKplsh());
                    String ss = generatePdfService.netWebService(url, "CallBack", returnmessage, gsxx.getAppKey(), gsxx.getSecretKey());
                    String fwkReturnMessageStr = generatePdfService.fwkReturnMessage(kpls);
                    logger.info("----------sap回写报文----------" + fwkReturnMessageStr);
                    String Data = HttpUtils.doPostSoap1_2(gsxx.getSapcallbackurl(), fwkReturnMessageStr, null, "Wendy", "Welcome9");
                    logger.info("----------fwk平台回写返回报文--------" + ss);
                    logger.info("----------sap回写返回报文----------" + Data);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
    }


}
