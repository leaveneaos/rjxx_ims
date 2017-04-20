package com.rjxx.taxeasy.job;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.rjxx.taxeasy.bizcomm.utils.SuiteManageService;
import com.rjxx.taxeasy.dingding.Helper.CorpOapiRequestHelper;
import com.rjxx.taxeasy.domains.IsvCorpSuiteAuth;
import com.rjxx.taxeasy.domains.IsvCorpSuiteJsapiTicket;
import com.rjxx.taxeasy.domains.IsvCorpToken;
import com.rjxx.taxeasy.service.IsvCorpSuiteAuthService;
import com.rjxx.taxeasy.service.IsvCorpTokenService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生成或者更新jsapiticket
 * Created by xlm on 2017/4/13.
 */
public class JsapiticketJob implements Job {
	
    private static Logger logger = LoggerFactory.getLogger(JsapiticketJob.class);
    
    @Autowired
    private IsvCorpTokenService isvcorptokenservice;
    @Autowired
    private IsvCorpSuiteAuthService isvcorpsuiteauthservice;
    @Autowired
    private SuiteManageService suitemanageservice;
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
        	logger.info("jsapiticket生成任务执行开始,nextFireTime:{},"+context.getNextFireTime());
            //获取所有corptoken
//            XmlWebApplicationContext xmlWebApplicationContext = (XmlWebApplicationContext) jobExecutionContext.getScheduler().getContext().get("applicationContextKey");
//            CorpManageService corpManageService=(CorpManageService)xmlWebApplicationContext.getBean("corpManageService");
            Map params=new HashMap<>();
            List<IsvCorpSuiteAuth> IsvCorpSuiteAuth  =isvcorpsuiteauthservice.findAllByParams(params);
        	/*List<IsvCorpToken> IsvCorpTokenlist = isvcorptokenservice.findAllByParams(params);
            if(CollectionUtils.isEmpty(IsvCorpTokenlist)){
            	logger.info("CorpToken查询失败");
                return;
            }*/
            //更换corp token
            for(IsvCorpSuiteAuth isvcorpsuiteauth:IsvCorpSuiteAuth){
                boolean f=  suitemanageservice.getCorpJSAPITicket(isvcorpsuiteauth.getSuiteKey(),isvcorpsuiteauth.getCorpId(),isvcorpsuiteauth.getPermanentCode());
                if(!f){
                	logger.info("jsapiticket生成任务失败,suiteKey:{},Corpid:{}"+isvcorpsuiteauth.getSuiteKey(),isvcorpsuiteauth.getCorpId());
                }
            }
        } catch (Exception e) {
        	logger.info("jsapiticket生成任务执行异常");
        }
    }
}
