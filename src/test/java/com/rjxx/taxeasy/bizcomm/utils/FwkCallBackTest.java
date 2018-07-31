package com.rjxx.taxeasy.bizcomm.utils;

import com.rjxx.Application;
import com.rjxx.taxeasy.domains.Gsxx;
import com.rjxx.taxeasy.domains.Kpls;
import com.rjxx.taxeasy.service.GsxxService;
import com.rjxx.taxeasy.service.KplsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: zsq
 * @date: 2018/7/25 10:05
 * @describe:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class FwkCallBackTest {
    private static Logger logger = LoggerFactory.getLogger(FwkCallBackTest.class);
    @Autowired
    private GeneratePdfService generatePdfService;
    @Autowired
    private GsxxService gsxxService;
    @Autowired
    private KplsService kplsService;
    @Autowired
    private FphxUtil fphxUtil;
    @Test
    public void  te(){
        try {
            Map map = new HashMap();
            map.put("gsdm","fwk");
            map.put("lrsj","2018-07-06");
            map.put("fpczlxdm","12");
            List<Kpls> list = kplsService.findAllByFwk(map);
            Map parms = new HashMap();
            parms.put("gsdm", "fwk");
            Gsxx gsxx = gsxxService.findOneByParams(parms);
            String url = gsxx.getCallbackurl();
            for(int i=0;i<list.size();i++){
                Kpls kpls = list.get(i);

                String returnmessage =  fphxUtil.CreateReturnMessage3(kpls.getKplsh());

                String ss = fphxUtil.netWebService(url, "CallBack", returnmessage, gsxx.getAppKey(), gsxx.getSecretKey());
                String fwkReturnMessageStr = fphxUtil.fwkReturnMessage(kpls);
                logger.info("----------sap回写报文----------" + fwkReturnMessageStr);
                String Data = HttpUtils.doPostSoap1_2(gsxx.getSapcallbackurl(), fwkReturnMessageStr, null, "Wendy", "Welcome5");
                logger.info("----------fwk平台回写返回报文--------" + ss);
                logger.info("----------sap回写返回报文----------" + Data);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
