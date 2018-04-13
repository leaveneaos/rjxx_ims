package com.rjxx.taxeasy.job;

import com.rjxx.taxeasy.bizcomm.utils.SkService;
import com.rjxx.taxeasy.config.RabbitmqUtils;
import com.rjxx.taxeasy.domains.Cszb;
import com.rjxx.taxeasy.domains.Kpls;
import com.rjxx.taxeasy.service.CszbService;
import com.rjxx.taxeasy.service.KplsService;
import com.rjxx.taxeasy.service.SkpService;
import com.rjxx.utils.StringUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xlm on 2017/10/18.
 */
@DisallowConcurrentExecution
public class ErrorExceptionSkJob implements Job {

    @Autowired
    private RabbitmqUtils rabbitmqUtils;
    @Autowired
    private KplsService kplsService;
    @Autowired
    private SkpService skpService;
    @Autowired
    private CszbService cszbService;
    @Autowired
    private SkService skService;

    private static Logger logger = LoggerFactory.getLogger(ErrorExceptionSkJob.class);


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            do{
                logger.info("-------进入定时任务开始---------"+context.getNextFireTime());
                String kplshStr = (String) rabbitmqUtils.receiveMsg("ErrorException_Sk", "12");
                if (StringUtils.isNotBlank(kplshStr)) {
                    int kplsh = Integer.valueOf(kplshStr);
                    Map params = new HashMap();
                    params.put("kplsh", kplsh);
                    Kpls kpls = kplsService.findOneByParams(params);

                    Cszb cszb=cszbService.getSpbmbbh(kpls.getGsdm(),kpls.getXfid(),kpls.getSkpid(),"kpfs");
                    if(cszb.getCsz().equals("01")){
                        skService.callService(kplsh);
                    }else if(cszb.getCsz().equals("03")){
                        if(!("09D103:发票领购信息已用完").equals(kpls.getErrorReason())){
                            skService.SkServerKP(kplsh);
                        }
                    }
                }else{
                    break;
                }
            }while (true);
            /*do{
                String kplshzpstr = (String) rabbitmqUtils.receiveMsg("ErrorException_Sk", "01");
                if (StringUtils.isNotBlank(kplshzpstr)) {
                    int kplshzp = Integer.valueOf(kplshzpstr);
                    Map params = new HashMap();
                    params.put("kplsh", kplshzp);
                    Kpls kpls = kplsService.findOneByParams(params);
                    Cszb cszb=cszbService.getSpbmbbh(kpls.getGsdm(),kpls.getXfid(),kpls.getSkpid(),"kpfs");
                    if(cszb.getCsz().equals("01")){
                        skService.callService(kplshzp);
                    }else if(cszb.getCsz().equals("03")){
                        skService.SkServerKP(kplshzp);
                    }
                }else{
                    break;
                }
              }while(true);
              do {
                    String kplshppstr = (String) rabbitmqUtils.receiveMsg("ErrorException_Sk", "02");
                    if (StringUtils.isNotBlank(kplshppstr)) {
                        int kplshpp = Integer.valueOf(kplshppstr);
                        Map params = new HashMap();
                        params.put("kplsh", kplshpp);
                        Kpls kpls = kplsService.findOneByParams(params);
                        Cszb cszb=cszbService.getSpbmbbh(kpls.getGsdm(),kpls.getXfid(),kpls.getSkpid(),"kpfs");
                        if(cszb.getCsz().equals("01")){
                            skService.callService(kplshpp);
                        }else if(cszb.getCsz().equals("03")){
                            skService.SkServerKP(kplshpp);
                        }
                    }else{
                        break;
                    }
               }while (true);*/
                 logger.info("-------进入定时任务结束---------"+context.getNextFireTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
