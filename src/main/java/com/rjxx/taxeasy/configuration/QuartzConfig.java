package com.rjxx.taxeasy.configuration;


import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by xlm on 2017/3/14.
 */
@Configuration
public class QuartzConfig {
	
	@Autowired
	private MyjobFactory MyjobFactory;

	@Autowired
	private DataSource dataSource;
	
    @Bean(name = "dingdingScheduler")
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException, SchedulerException {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        
        
        //schedulerFactory.setOverwriteExistingJobs(true);
        // 延时启动
        //schedulerFactory.setStartupDelay(20);
        
        // 加载quartz数据源配置
        schedulerFactory.setQuartzProperties(quartzProperties());
        
        // 自定义Job Factory，用于Spring注入
        schedulerFactory.setJobFactory(MyjobFactory);
        
        schedulerFactory.setAutoStartup(true);
        
        schedulerFactory.setDataSource(dataSource);
        
        return schedulerFactory;
    }
    
    @Bean
    public Scheduler scheduler() throws IOException, SchedulerException {
    	
    	Scheduler scheduler=schedulerFactoryBean().getScheduler();

    	
    	scheduler.start();
        return scheduler;
    }
    public Properties quartzProperties() throws IOException {
        Properties prop = new Properties();
        prop.put("quartz.scheduler.instanceName", "dingdingScheduler");
        prop.put("org.quartz.scheduler.instanceId", "AUTO");
        prop.put("org.quartz.scheduler.skipUpdateCheck", "true");
        prop.put("org.quartz.scheduler.jobFactory.class", "org.quartz.simpl.SimpleJobFactory");
        prop.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
        prop.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
        prop.put("org.quartz.jobStore.tablePrefix", "QRTZ_");
        prop.put("org.quartz.jobStore.isClustered", "true");
        prop.put("org.quartz.jobStore.clusterCheckinInterval", "20000");
        prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        prop.put("org.quartz.threadPool.threadCount", "5");
        return prop;
    }
}