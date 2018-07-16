package com.rjxx.taxeasy.job;


import com.alibaba.fastjson.JSON;
import com.rjxx.taxeasy.bizcomm.utils.GetYjnr;
import com.rjxx.taxeasy.bizcomm.utils.SaveMessage;
import com.rjxx.taxeasy.bizcomm.utils.SendalEmail;
import com.rjxx.taxeasy.domains.Yjmb;
import com.rjxx.taxeasy.service.*;
import com.rjxx.taxeasy.vo.FpkcYjtzVo;
import com.rjxx.utils.StringUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 发票库存预警定时任务
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
    @Autowired
    private YjmbService yjmbService;
    @Value("${imgdz_:}")
    private String imgdz_;
    @Value("${fpdz_:}")
    private String fpdz_;

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
                Yjmb yjmb = yjmbService.findOne(5);
                String yjbt = yjmb.getYjmbSubject();//邮件标题
                GetYjnr getYjnr = new GetYjnr();
                String yjmbcontent = yjmb.getYjmbNr();
                Map csmap = new HashMap();
                List<FpkcYjtzVo> tzList = fpkcYztzService.findAllTzList(new HashMap());
                SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
                //重新整合数据
                Map<String,String> yhidMap = new HashMap<>();//定义无用变量用来分组。
                List<String> yhlList = new ArrayList<>();// 用户List（说明需要发送给几个用户）
                for (int i =0 ;i<tzList.size();i++) {
                    FpkcYjtzVo fpkcYjtzVo1 = tzList.get(i);
                    if (!yhidMap.containsKey(fpkcYjtzVo1.getTzyhid().toString())) {
                        yhidMap.put(fpkcYjtzVo1.getTzyhid().toString(),fpkcYjtzVo1.getTzyhid().toString());
                        yhlList.add(fpkcYjtzVo1.getTzyhid().toString());
                    }
                }
                System.out.println(JSON.toJSONString(yhlList));
                Map<String,List<FpkcYjtzVo>> resultMap = new HashMap();
                for(int a=0;a<yhlList.size();a++) {
                    String yhid = yhlList.get(a);
                    List<FpkcYjtzVo> infoList = new ArrayList();
                    for(int t=0;t<tzList.size();t++) {
                        FpkcYjtzVo fpkcYjtzVo = tzList.get(t);
                        if(fpkcYjtzVo.getTzyhid().toString().equals(yhid)){
                            infoList.add(fpkcYjtzVo);
                        }
                    }
                    resultMap.put(yhid,infoList);
                }
//                System.out.println(JSON.toJSONString(resultMap));
                for (int s=0;s<yhlList.size();s++) {
                    String s1 = yhlList.get(s);
                    List list = resultMap.get(s1);
                    String fpInfo="";
                    String yhmc="";
                    String email="";
                    String gsdm="";
                    String tzfs="";
                    String phone="";
                    for(int k = 0;k<list.size();k++){
                        String fpIn="";
                        FpkcYjtzVo fpkcYjtzVo = (FpkcYjtzVo) list.get(k);
                        yhmc=fpkcYjtzVo.getYhmc();
                        if(StringUtils.isNotBlank(fpkcYjtzVo.getEmail())){
                            email=fpkcYjtzVo.getEmail();
                        }
                        if(StringUtils.isNotBlank(fpkcYjtzVo.getPhone())){
                            phone=fpkcYjtzVo.getPhone();
                        }
                        gsdm=fpkcYjtzVo.getGsdm();
                        tzfs+=fpkcYjtzVo.getTzfs()+",";
                        int i = fpkcYjtzVo.getFpzlmc().indexOf(",");
                        if(i>=0) {
                            String[] split = fpkcYjtzVo.getFpzlmc().split(",");
                            String[] split1 = fpkcYjtzVo.getFpkcl().split(",");
                            for (int j = 0; j < split.length; j++) {
                                fpIn +=split[j] +":<a style='color:#ff8314;text-decoration:none;font-weight: bolder;'>"+ split1[j] + "</a>张 &nbsp;&nbsp;&nbsp;&nbsp;";

                            }
                        }else {
                            fpIn = fpkcYjtzVo.getFpzlmc()+":<a style='color:#ff8314;text-decoration:none;font-size:18px;font-weight: bolder;'>"+ fpkcYjtzVo.getFpkcl() + "</a>张 &nbsp;&nbsp;&nbsp;&nbsp;";
                        }
                        fpInfo += "\t<div style=\"margin-top: 10px;\">\n" +
                                "\t\t\t\t\t<p style=\"font-size: 18px;color:#333333;\">您的<span style='font-weight: bolder;'>"+fpkcYjtzVo.getXfmc()+"</span>下对应剩余发票数据:</p>\n" +
                                "\t\t\t\t\t<div>\n" +
                                "\t\t\t\t\t\t<div style=\"border:solid 2px #0964aa;border-radius: 5px;padding: 10px;background-color:#ffffff;\">\n" +
                                "\t\t\t\t\t\t\t<span style=\"display:inline-block;font-size: 16px;color: #333333;padding: 0 0 3px 0px;border-bottom: 2px solid #0964aa;font-weight: bolder;\">盘号"+fpkcYjtzVo.getSkph()+"</span>\n" +
                                "\t\t\t\t\t\t    <div style=\"font-size:16px;color:#333333;background-color:#c9e5fa;border-radius:5px;padding: 5px 5px;margin-top: 5px;\">\n" +
                                "\t\t\t\t\t\t    \t"+fpIn+"\n" +
                                "\t\t\t\t\t\t    </div>\n" +
                                "\t\t\t\t\t\t</div>\t\t\t\t\t\t\n" +
                                "\t\t\t\t\t</div>\n" +
                                "\t\t\t\t</div>";
                    }
                    csmap.put("fp_info_",fpInfo);
                    csmap.put("lo_go_dz_",imgdz_+"emailLogo.png");
                    csmap.put("yh_mc_",yhmc);
                    csmap.put("e_wm_dz_", imgdz_+"emailCode.png");
                    String content = getYjnr.getFpkcyj(csmap,yjmbcontent);
//                    System.out.println(JSON.toJSONString(csmap));
                    if(tzfs.contains("03")){
                        System.out.println("邮件+短信");
                        if(!email.equals("")){
                            SendalEmail.sendEmail(null, gsdm,email , "发票库存预警",null, content, yjbt);
                        }
                        Map<String, String> rep = new HashMap();
                        rep.put("name", yhmc);
                        rep.put("date", sim.format(new Date()));
                        if(!phone.equals(""))
                            saveMessage.saveMessage(gsdm, null, phone, rep, "SMS_138074750", "开票通");
                    }else {
                        System.out.println("只发邮件");
                        if(!email.equals(""))
                        SendalEmail.sendEmail(null, gsdm,email , "发票库存预警",null, content, yjbt);
                    }
                }
                /*for (int i= 0;i<tzList.size();i++){
                    FpkcYjtzVo fpkcYjtzVo = tzList.get(i);
                    Map csmap = new HashMap();
                    String fpInfo="";
                    if(fpkcYjtzVo.getFpzlmc().indexOf(",")>=0) {
                        String[] split = fpkcYjtzVo.getFpzlmc().split(",");
                        String[] split1 = fpkcYjtzVo.getFpkcl().split(",");
                        for (int j = 0; j < split.length; j++) {
                            fpInfo +=split[j] +":<a style='color:red;text-decoration:none'>"+ split1[j] + "</a>张 &nbsp;&nbsp;&nbsp;&nbsp;";
                        }
                    }else {
                        fpInfo = fpkcYjtzVo.getFpzlmc()+":<a style='color:red;text-decoration:none'>"+ fpkcYjtzVo.getFpkcl() + "</a>张 &nbsp;&nbsp;&nbsp;&nbsp;";
                    }
                    //邮件内容
                    csmap.put("fp_info_",fpInfo);
                    csmap.put("lo_go_dz_",imgdz_+"emailLogo.png");
                    csmap.put("yh_mc_",fpkcYjtzVo.getYhmc());
                    csmap.put("xf_mc_",fpkcYjtzVo.getXfmc());
                    //csmap.put("fpzl_mc_",fpkcYjtzVo.getFpzlmc());
                    //csmap.put("fp_sl_",fpkcYjtzVo.getFpkcl());
                    String content = getYjnr.getFpkcyj(csmap,yjmbcontent);
                    if(fpkcYjtzVo.getTzfs().contains("02") && fpkcYjtzVo.getTzfs().contains("03")){
                        //发邮件
                        String [] to=new String[1];
                        to[0]=fpkcYjtzVo.getEmail();
                        if(null!=fpkcYjtzVo.getEmail() && !fpkcYjtzVo.getEmail().equals(""))
                            //SendalEmail.sendEmail(null, fpkcYjtzVo.getGsdm(),to[0] , "发票库存预警",null, fpkcYjtzVo.getTzy(), yjbt);
                            SendalEmail.sendEmail(null, fpkcYjtzVo.getGsdm(),fpkcYjtzVo.getEmail() , "发票库存预警",null, content, yjbt);
                        //短信
                        Map<String, String> rep = new HashMap();
                        rep.put("name", fpkcYjtzVo.getYhmc());
                        rep.put("date", sim.format(new Date()));
                        if(null!=fpkcYjtzVo.getPhone() && !fpkcYjtzVo.getPhone().equals(""))
                            saveMessage.saveMessage(fpkcYjtzVo.getGsdm(), null, fpkcYjtzVo.getPhone(), rep, "SMS_138069573", "开票通");
                    }else if(fpkcYjtzVo.getTzfs().contains("02")){
                        //发邮件
                        String [] to=new String[1];
                        to[0]=fpkcYjtzVo.getEmail();
                        if(null!=fpkcYjtzVo.getEmail() && !fpkcYjtzVo.getEmail().equals(""))
                            //SendalEmail.sendEmail(null, fpkcYjtzVo.getGsdm(),to[0] , "发票库存预警",null, fpkcYjtzVo.getTzy(), "发票库存预警通知");
                            SendalEmail.sendEmail(null, fpkcYjtzVo.getGsdm(),fpkcYjtzVo.getEmail() , "发票库存预警",null, content, yjbt);
                    }else if(fpkcYjtzVo.getTzfs().contains("03")){
                        //短信
                        Map<String, String> rep = new HashMap();
                        rep.put("name", fpkcYjtzVo.getYhmc());
                        rep.put("date", sim.format(new Date()));
                        if(null!=fpkcYjtzVo.getPhone() && !fpkcYjtzVo.getPhone().equals(""))
                            saveMessage.saveMessage(fpkcYjtzVo.getGsdm(), null, fpkcYjtzVo.getPhone(), rep, "SMS_138069573", "开票通");
                    }
                }*/

            logger.info("-------进入库存预警定时任务结束---------"+context.getNextFireTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
