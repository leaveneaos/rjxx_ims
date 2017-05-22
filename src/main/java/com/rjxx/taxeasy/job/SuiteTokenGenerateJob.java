package com.rjxx.taxeasy.job;


import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.rjxx.taxeasy.bizcomm.utils.SuiteManageService;

import com.rjxx.taxeasy.domains.IsvSuite;
import com.rjxx.taxeasy.service.IsvSuiteService;

import org.quartz.Job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生成或更新suite访问开放平台接口token任务
 * Created by xlm on 2017/04/14.
 */
public class SuiteTokenGenerateJob implements Job {
    private static Logger logger = LoggerFactory.getLogger(SuiteTokenGenerateJob.class);
    @Autowired
    private IsvSuiteService IsvSuiteService;
    @Autowired
    private SuiteManageService  suitemanageservice;
    
    
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try{
        	logger.info("套件TOKEN生成任务执行开始,nextFireTime:{},"+context.getNextFireTime());
            Map map=new HashMap<>();
        	List<IsvSuite> suiteList = IsvSuiteService.findAllByParams(map);
            if(CollectionUtils.isEmpty(suiteList)){
            	logger.info("查询套件信息失败,nextFireTime:{},");
                return;
            }
            //分别更换套件token
            for(IsvSuite suiteVO:suiteList){
                boolean f=  suitemanageservice.saveOrUpdateSuiteToken(suiteVO.getSuiteKey());
                if(!f){
                	logger.info("生成token任务失败,suiteKey:{},suiteName:{}"+suiteVO.getSuiteKey()+suiteVO.getSuiteName());
                }
            }
        }catch (Exception e){
        	logger.info("任务执行异常:{}"+e.toString());
        }
    }
}

