package com.rjxx.taxeasy.job;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.rjxx.taxeasy.dao.FpyltjJpaDao;
import com.rjxx.taxeasy.domains.Fpyltj;
import com.rjxx.taxeasy.service.FpyltjService;
import com.rjxx.taxeasy.service.KplsService;
import com.rjxx.taxeasy.vo.FpyltjVo;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: zsq
 * @date: 2018/5/3 14:05
 * @describe: 发票用量统计----天-----定时任务
 */
public class GetFpyltjForDayJob implements Job {
    private  Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KplsService kplsService;
    @Autowired
    private FpyltjJpaDao fpyltjJpaDao;
    @Autowired
    private FpyltjService fpyltjService;
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            logger.info("====发票用量统计for day==== start");
            Calendar calendar_day = Calendar.getInstance();
            calendar_day.setTime(new Date());
            calendar_day.add(Calendar.DAY_OF_MONTH,-1);
            String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(calendar_day.getTime());
//            String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            Map map = new HashMap();
            map.put("kprqq",yesterday);
            List<FpyltjVo> list = kplsService.findFpylByParams(map);
            Date date = new Date();
            for (FpyltjVo fpyltjVo : list) {
                Map map1 = new HashMap();
                map1.put("gsdm",fpyltjVo.getGsdm());
                map1.put("xfid",fpyltjVo.getXfid());
                map1.put("skpid",fpyltjVo.getSkpid());
                map1.put("kprqq",new SimpleDateFormat("yyyy-MM-dd").format(fpyltjVo.getKprq()));
                Fpyltj fpyltj1 = fpyltjService.findOneByParams(map1);
                if(fpyltj1==null){
                    Fpyltj fpyltj = new Fpyltj();
                    fpyltj.setZjshj(fpyltjVo.getSumhjje());
                    fpyltj.setFpsl(fpyltjVo.getFpsl());
                    fpyltj.setGsdm(fpyltjVo.getGsdm());
                    fpyltj.setSkpid(fpyltjVo.getSkpid());
                    fpyltj.setXfid(fpyltjVo.getXfid());
                    fpyltj.setFpzldm(fpyltjVo.getFpzldm());
                    fpyltj.setFpztdm(fpyltjVo.getFpztdm());
                    fpyltj.setKprq(fpyltjVo.getKprq());
                    fpyltj.setLrsj(date);
                    fpyltjJpaDao.save(fpyltj);
                }
            }
            logger.info("====发票用量统计for day==== end");
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("====发票用量统计for day==== error");
        }
    }
}
