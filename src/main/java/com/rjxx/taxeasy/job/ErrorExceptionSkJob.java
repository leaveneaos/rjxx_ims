package com.rjxx.taxeasy.job;

import com.rjxx.taxeasy.bizcomm.utils.InvoiceResponse;
import com.rjxx.taxeasy.bizcomm.utils.SkService;
import com.rjxx.taxeasy.config.RabbitmqUtils;
import com.rjxx.taxeasy.domains.Cszb;
import com.rjxx.taxeasy.domains.Kpcf;
import com.rjxx.taxeasy.domains.Kpls;
import com.rjxx.taxeasy.service.CszbService;
import com.rjxx.taxeasy.service.KpcfService;
import com.rjxx.taxeasy.service.KplsService;
import com.rjxx.taxeasy.service.SkpService;
import com.rjxx.time.TimeUtil;
import com.rjxx.utils.StringUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
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

    @Autowired
    private KpcfService kpcfService;



    private static Logger logger = LoggerFactory.getLogger(ErrorExceptionSkJob.class);


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            //do{
                logger.info("-------进入ErrorExceptionSkJob发票异常补偿定时任务开始---------"+context.getNextFireTime());
                //获取重发表中次数大于等于4小于6的记录
                List<Kpcf> kpcfList = kpcfService.findAllByCount();
                if (!kpcfList.isEmpty() && kpcfList.size() > 0){
                    for (Kpcf kpcf : kpcfList){
                     int kplsh = kpcf.getKplsh();
                     //查询确认是否开成功
                     InvoiceResponse invoiceResponse = skService.SkServerQuery(kplsh);
                        if (null==invoiceResponse || (null !=invoiceResponse && null != invoiceResponse.getReturnCode() && !invoiceResponse.getReturnCode().equals("0000"))){
                            //开票
                           InvoiceResponse invoiceResponse1 =  skService.SkServerKP(kplsh);
                           if (null !=invoiceResponse1 && invoiceResponse1.getReturnCode().equals("0000")){
                               //成功 删除记录
                               kpcfService.deleteById(kplsh);
                           }else {

                               //count +1
                               kpcf.setXgsj(TimeUtil.getNowDate());
                               kpcf.setKpcfcs(kpcf.getKpcfcs()+1);
                               if (kpcf.getKpcfcs() < 6){
                                   kpcfService.save(kpcf);
                               }
                               if(kpcf.getKpcfcs() == 6){
                                   Kpls kpls=kplsService.findOne(kplsh);
                                   kpls.setFpztdm("05");
                                   kplsService.save(kpls);
                                   kpcfService.deleteById(kplsh);

                               }
//                               kpcfService.save(kpcf);
                           }
                        }else if (null != invoiceResponse.getReturnCode() && invoiceResponse.getReturnCode().equals("0000")  && null !=invoiceResponse.getFphm()){
                            kpcfService.deleteById(kplsh);
                        }else if (null !=invoiceResponse && invoiceResponse.getReturnCode() == null){
                            InvoiceResponse invoiceResponse2 =  skService.SkServerKP(kplsh);
                            if (null !=invoiceResponse2 && invoiceResponse2.getReturnCode().equals("0000")){
                                //成功 删除记录
                                kpcfService.deleteById(kplsh);
                            }else {

                                //count +1
                                kpcf.setXgsj(TimeUtil.getNowDate());
                                kpcf.setKpcfcs(kpcf.getKpcfcs()+1);
                                if (kpcf.getKpcfcs() < 6){
                                    kpcfService.save(kpcf);
                                }
                                if(kpcf.getKpcfcs() == 6){
                                    Kpls kpls=kplsService.findOne(kplsh);
                                    kpls.setFpztdm("05");
                                    kplsService.save(kpls);
                                    kpcfService.deleteById(kplsh);

                                }
//                                kpcfService.save(kpcf);
                            }

                        }
                    }
                }/*else{
                    break;
                }*/
               /* String kplshStr = (String) rabbitmqUtils.receiveMsg("ErrorException_Sk", "12");
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
                }*/
            //}while (true);
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
                 logger.info("-------进入ErrorExceptionSkJob发票异常补偿定时任务结束---------"+context.getNextFireTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
