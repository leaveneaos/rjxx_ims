package com.rjxx.taxeasy.job;

import com.rjxx.taxeasy.bizcomm.utils.SkService;
import com.rjxx.taxeasy.config.RabbitmqUtils;
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
            do {
                String kplshStr = (String) rabbitmqUtils.receiveMsg("ErrorException_Sk", "12");
                if (StringUtils.isNotBlank(kplshStr)) {
                    int kplsh = Integer.valueOf(kplshStr);
                    Map params = new HashMap();
                    params.put("kplsh", kplsh);
                    Kpls kpls = kplsService.findOneByParams(params);
                    skService.SkServerKP(kplsh);
                } else {
                    break;
                }
            } while (true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
