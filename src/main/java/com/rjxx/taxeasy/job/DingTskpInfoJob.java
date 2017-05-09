package com.rjxx.taxeasy.job;

import com.alibaba.fastjson.JSON;
import com.dingtalk.open.client.api.model.corp.MessageBody;
import com.rjxx.taxeasy.bizcomm.utils.SuiteManageService;
import com.rjxx.taxeasy.configuration.LightAppMessageDelivery;
import com.rjxx.taxeasy.configuration.MessageHelper;
import com.rjxx.taxeasy.domains.*;
import com.rjxx.taxeasy.service.*;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 钉钉推送开票信息任务
 * Created by xlm on 2017/4/28.
 */
public class DingTskpInfoJob implements Job {
    private static Logger logger = LoggerFactory.getLogger(DingTskpInfoJob.class);

    @Autowired
    private IsvCorpTokenService isvcorptokenservice;
    @Autowired
    private IsvCorpSuiteAuthService isvcorpsuiteauthservice;
    @Autowired
    private SuiteManageService suitemanageservice;
    @Autowired
    private KplsService kplsService;
    @Autowired
    private KpspmxService kpspmxService;
    @Autowired
    private IsvCorpSuiteJsapiTicketService isvcorpsuitejsapiticketservice;
    @Autowired
    private IsvCorpAppService isvcorpappservice;
    @Autowired
    private JyxxsqService jyxxsqservice;
    @Autowired
    private SkpService skpservice;
    @Autowired
    private XfService xfService;
    @Autowired
    private JymxsqService jymxsqservice;
    @Autowired
    private JylsService jylsService;
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.info("钉钉开票信息推送任务执行开始,nextFireTime:{},"+context.getNextFireTime());
        try{
            /*Map map =new HashMap<>();
            List<Kpls> kplslist=kplsService.findDingdingTsInfo(map);
            for(Kpls kpls:kplslist){
                Map map2=new HashMap();
                map2.put("kplsh",kpls.getKplsh());
                kpls.setDdtsbz("1");//推送过钉钉
                kplsService.save(kpls);
                List<Kpspmx> kpspmx= kpspmxService.findMxList(map2);
                String userid = kpls.getDinguserid();
                Map params=new HashMap();
                params.put("corpId", kpls.getDingcorpid());
                IsvCorpSuiteJsapiTicket isvcorptoken=isvcorpsuitejsapiticketservice.findOneByParams(params);
                String accessToken=isvcorptoken.getCorpaccesstoken();
                IsvCorpApp isvCorpApp=isvcorpappservice.findOneByParams(params);
                LightAppMessageDelivery lightAppMessageDelivery=new LightAppMessageDelivery();
                lightAppMessageDelivery.setAgentid(isvCorpApp.getAgentId().toString());
                lightAppMessageDelivery.setToparty("");
                lightAppMessageDelivery.setTouser(userid);
                lightAppMessageDelivery.setMsgType("oa");
                MessageBody.OABody oaBody=new MessageBody.OABody();
                MessageBody.OABody.Body body=new MessageBody.OABody.Body();
                body.setTitle("发票开具通知");
                MessageBody.OABody.Body.Form form0=new MessageBody.OABody.Body.Form();
                form0.setKey("发票抬头：");
                form0.setValue(kpls.getGfmc());
                MessageBody.OABody.Body.Form form=new MessageBody.OABody.Body.Form();
                form.setKey("订单号：");
                Jyls jyls1=new Jyls();
                jyls1.setDjh(kpls.getDjh());
                Jyls jyls=jylsService.findOneByParams(jyls1);
                form.setValue(jyls.getDdh());
                MessageBody.OABody.Body.Form form4=new MessageBody.OABody.Body.Form();
                form4.setKey("发票代码：");
                form4.setValue(kpls.getFpdm());
                MessageBody.OABody.Body.Form form5=new MessageBody.OABody.Body.Form();
                form5.setKey("发票号码：");
                form5.setValue(kpls.getFphm());
                MessageBody.OABody.Body.Form form1=new MessageBody.OABody.Body.Form();
                form1.setKey("价税合计：");
                form1.setValue(kpls.getJshj().toString());
                MessageBody.OABody.Body.Form form2=new MessageBody.OABody.Body.Form();
                form2.setKey("申请发票类型：");
                if(kpls.getFpzldm().equals("12")){
                    form2.setValue("电子发票(增普)");
                    oaBody.setMessage_url(kpls.getPdfurl());
                }else if(kpls.getFpzldm().equals("02")){
                    form2.setValue("纸质普票");
                }else if(kpls.getFpzldm().equals("01")){
                    form2.setValue("纸质专票");
                }
                MessageBody.OABody.Body.Form form3=new MessageBody.OABody.Body.Form();
                form3.setKey("发票明细：");
                form3.setValue(kpspmx.size()+"行");
                MessageBody.OABody.Body.Rich rich=new MessageBody.OABody.Body.Rich();
                rich.setNum("");
                rich.setUnit("");
                List<MessageBody.OABody.Body.Form> forms=new ArrayList<>();
                forms.add(form0);
                forms.add(form);
                forms.add(form4);
                forms.add(form5);
                forms.add(form1);
                forms.add(form2);
                forms.add(form3);
                body.setContent("您提交的开票申请已开具成功，内容如下：");
                body.setForm(forms);
                oaBody.setBody(body);
                MessageBody.OABody.Head head=new MessageBody.OABody.Head();
                head.setBgcolor("FFBBBBBB");
                head.setText("开票通");
                oaBody.setHead(head);
                lightAppMessageDelivery.setMessage(oaBody);
                System.out.println(JSON.toJSON(oaBody));
                MessageHelper.Receipt receipt=MessageHelper.send(accessToken,lightAppMessageDelivery);
                System.out.println(JSON.toJSON(receipt));
            }*/
        }catch (Exception e){
            e.printStackTrace();
            logger.info("钉钉开票信息推送任务执行失败{},");
        }
    }
}
