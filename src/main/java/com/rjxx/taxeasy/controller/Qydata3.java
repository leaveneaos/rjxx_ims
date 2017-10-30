package com.rjxx.taxeasy.controller;

import com.rjxx.comm.utils.ApplicationContextUtils;
import com.rjxx.taxeasy.bizcomm.utils.Transferdata;
import com.rjxx.taxeasy.domains.*;
import com.rjxx.taxeasy.service.*;
import com.rjxx.taxeasy.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by xlm on 2017/10/20.
 */
@Controller
@RequestMapping("/QyData3")
public class Qydata3 extends BaseController{
    @Autowired
    private KplsService kplsService;
    @Autowired
    private KpspmxService kpspmxService;
    @Autowired
    private JylsService jylsService;
    @Autowired
    private JyspmxService jyspmxService;
    @Autowired
    private XfService xfService;
    @Autowired
    private SkpService skpService;
    /**
     * 线程池执行任务
     */
    private static ThreadPoolTaskExecutor taskExecutor = null;
    /**
     * 多线程执行生成pdf
     */
    class QydataTask implements Runnable {

        private Xf xf;
        private String gsdm;

        @Override
        public void run() {
            //synchronized (this){
            logger.info("------多线程执行生成pdf----------");
            Qydata(xf,gsdm);
           // }
        }
        public void setXf(Xf xf) {
            this.xf = xf;
        }
        public void setGsdm(String gsdm) {
            this.gsdm = gsdm;
        }
    }
    /**
     * 多线程执行迁移数据
     */
    class Qydata2Task implements Runnable {

        private Xf xf;
        private String gsdm;
        private Skp skp;
        private Jyls jyls;

        @Override
        public void run() {
            //synchronized (this){
            logger.info("------多线程执行生成pdf----------");
            qyda2(gsdm,jyls,xf,skp);
            // }
        }
        public void setXf(Xf xf) {
            this.xf = xf;
        }
        public void setGsdm(String gsdm) {
            this.gsdm = gsdm;
        }

        public void setSkp(Skp skp) {
            this.skp = skp;
        }

        public void setJyls(Jyls jyls) {
            this.jyls = jyls;
        }
    }
    @RequestMapping
    @ResponseBody
    public Map transferdata(){
        Map result=new HashMap();
        try{
            String gsdm = request.getParameter("gsdm");
            String xfsh = request.getParameter("xfsh");
            List<Object> xflist= Transferdata.getdata("t_xf",gsdm,xfsh,0,0,0,0);
            List<Xf> xfList=reload3(xflist);
            for(int i=0;i<xfList.size();i++){
                Xf xf=(Xf)xfList.get(i);
                Qydata(xf,gsdm);
               /* QydataTask qydataTask=new QydataTask();
                qydataTask.setXf(xf);
                qydataTask.setGsdm(gsdm);
                if (taskExecutor == null) {
                    taskExecutor = ApplicationContextUtils.getBean(ThreadPoolTaskExecutor.class);
                }
                taskExecutor.execute(qydataTask);*/
            }
            result.put("msg","迁移数据成功！");
        }catch (Exception e){
            result.put("msg","迁移数据失败！");
        }
        return result;
    }

    public void Qydata(Xf xf,String gsdm){
            Xf xfparms=new Xf();
            xfparms.setGsdm(gsdm);
            xfparms.setXfsh(xf.getXfsh());
            Xf xfims=xfService.findOneByParams(xfparms);
            List<Object> skplist=Transferdata.getdata("t_skp",gsdm,"",0,0,xf.getId(),0);
                /* xf.setLrry(getYhid());
                xf.setId(null);
                xfService.saveNew(xf);*/
            List<Skp> skpList= reload4(skplist);
            for(Skp skp:skpList){
                List<Object> jylslist=Transferdata.getdata("t_jyls",gsdm,"",0,0,0,skp.getId());
                Map skpmap=new HashMap();
                skpmap.put("kpddm",skp.getKpddm());
                Skp skp1 =skpService.findOneByParams(skpmap);
                   /* skp.setId(null);
                    skp.setXfid(xf.getId());
                    skpService.save(skp);*/
                List<Jyls> jylsList= reload5(jylslist);
                for(Jyls jyls:jylsList){
                    qyda2(gsdm,jyls,xfims,skp1);
                  /*  Qydata2Task qydataTask2=new Qydata2Task();
                    qydataTask2.setXf(xfims);
                    qydataTask2.setGsdm(gsdm);
                    qydataTask2.setJyls(jyls);
                    qydataTask2.setSkp(skp1);
                    if (taskExecutor == null) {
                        taskExecutor = ApplicationContextUtils.getBean(ThreadPoolTaskExecutor.class);
                    }
                    taskExecutor.execute(qydataTask2);*/
                }
            }
    }
    public void qyda2(String gsdm,Jyls jyls,Xf xfims,Skp skp1){
        List<Object> jyspmxlist=Transferdata.getdata("t_jyspmx",gsdm,"",jyls.getDjh(),0,0,0);
        List<Object> kplslist=Transferdata.getdata("t_kpls",gsdm,"",jyls.getDjh(),0,0,0);
        jyls.setXfid(xfims.getId());
        jyls.setSkpid(skp1.getId());
        jyls.setHsbz("1");
        if(jyls.getLrsj()==null){
            jyls.setLrsj(new Date());
        }else if(jyls.getXgsj()==null){
            jyls.setXgsj(new Date());
        }
        jyls.setDjh(null);
        jylsService.save(jyls);
        List<Jyspmx> jyspmxList=reload1(jyspmxlist);
        for(Jyspmx jyspmx:jyspmxList){
            jyspmx.setId(null);
            jyspmx.setDjh(jyls.getDjh());
            jyspmx.setXfid(xfims.getId());
            jyspmx.setSkpid(skp1.getId());
            if(jyspmx.getLrsj()==null){
                jyspmx.setLrsj(new Date());
            }else if(jyspmx.getXgsj()==null){
                jyspmx.setXgsj(new Date());
            }
            jyspmxService.save(jyspmx);
        }
        List<Kpls> kplsList=reload(kplslist);
        for(Kpls kpls:kplsList){
            List<Object> kpspmxlist=Transferdata.getdata("t_kpspmx",gsdm,"",0,kpls.getKplsh(),0,0);
            kpls.setDjh(jyls.getDjh());
            kpls.setXfid(xfims.getId());
            kpls.setSkpid(skp1.getId());
            if(kpls.getLrsj()==null){
                kpls.setLrsj(new Date());
            }else if(kpls.getXgsj()==null){
                kpls.setXgsj(new Date());
            }
            kpls.setKplsh(null);
            kplsService.save(kpls);
            List<Kpspmx> kpspmxList=reload2(kpspmxlist);
            for(Kpspmx kpspmx:kpspmxList){
                kpspmx.setId(null);
                kpspmx.setDjh(jyls.getDjh());
                kpspmx.setKplsh(kpls.getKplsh());
                if(kpspmx.getLrsj()==null){
                    kpspmx.setLrsj(new Date());
                }else if(kpspmx.getXgsj()==null){
                    kpspmx.setXgsj(new Date());
                }
                kpspmxService.save(kpspmx);
            }
        }
    }
    public List<Kpls> reload(List<Object> kplslist){
        List<Kpls> kplsList=new ArrayList<>();
        for(int i=0;i<kplslist.size();i++) {
            Kpls kpls = (Kpls) kplslist.get(i);
            for (int j = i + 1; j < kplslist.size(); j++) {
                Kpls kplsj = (Kpls) kplslist.get(j);
                if (kpls.getKplsh() == (kplsj.getKplsh())) {
                    System.out.println("第" + (i + 1) + "个跟第" + (j + 1) + "个重复，值是：" + kpls.getKplsh());
                }
            }
            kplsList.add(kpls);
        }
        return kplsList;
    }
    public List<Jyspmx> reload1(List<Object> jyspmxlist){
        List<Jyspmx> jyspmxList=new ArrayList<>();
        for(int i=0;i<jyspmxlist.size();i++) {
            Jyspmx jyspmx=(Jyspmx)jyspmxlist.get(i);
            for (int j = i + 1; j < jyspmxlist.size(); j++)
            {
                Jyspmx jyspmxj=(Jyspmx)jyspmxlist.get(j);
                if (jyspmx.getId()==(jyspmxj.getId()))
                {
                    System.out.println("第" + (i + 1) + "个跟第" + (j + 1) + "个重复，值是：" + jyspmx.getId());
                }
            }
            jyspmxList.add(jyspmx);
        }
        return jyspmxList;
    }
    public List<Kpspmx> reload2(List<Object> kpspmxlist){

        List<Kpspmx> kpspmxList=new ArrayList<>();
        for(int i=0;i<kpspmxlist.size();i++) {
            Kpspmx kpspmx=(Kpspmx)kpspmxlist.get(i);
            for (int j = i + 1; j < kpspmxlist.size(); j++)
            {
                Kpspmx kpspmxj=(Kpspmx)kpspmxlist.get(j);
                if (kpspmx.getId()==(kpspmxj.getId()))
                {
                    System.out.println("第" + (i + 1) + "个跟第" + (j + 1) + "个重复，值是：" + kpspmx.getId());
                }
            }
            kpspmxList.add(kpspmx);
        }
        return kpspmxList;
    }
    public List<Xf> reload3(List<Object> xflist){

        List<Xf> XfList=new ArrayList<>();
        for(int i=0;i<xflist.size();i++) {
            Xf Xf=(Xf)xflist.get(i);
            for (int j = i + 1; j < xflist.size(); j++)
            {
                Xf xfj=(Xf)xflist.get(j);
                if (Xf.getId()==(xfj.getId()))
                {
                    System.out.println("第" + (i + 1) + "个跟第" + (j + 1) + "个重复，值是：" + Xf.getId());
                }
            }
            XfList.add(Xf);
        }
        return XfList;
    }
    public List<Jyls> reload5(List<Object> jylslist){
        List<Jyls> jylsList=new ArrayList<>();

        for(int i=0;i<jylslist.size();i++) {
            Jyls jyls=(Jyls)jylslist.get(i);
            for (int j = i + 1; j < jylslist.size(); j++)
            {
                Jyls jylsj=(Jyls)jylslist.get(j);
                if (jyls.getDjh()==(jylsj.getDjh()))
                {
                    System.out.println("第" + (i + 1) + "个跟第" + (j + 1) + "个重复，值是：" + jyls.getDjh());
                }
            }
            jylsList.add(jyls);
        }
        return jylsList;
    }
    public List<Skp> reload4(List<Object> skplist){

        List<Skp> SkpList=new ArrayList<>();
        for(int i=0;i<skplist.size();i++) {
            Skp Skp=(Skp)skplist.get(i);
            for (int j = i + 1; j < skplist.size(); j++)
            {
                Skp skpj=(Skp)skplist.get(j);
                if (Skp.getId()==(skpj.getId()))
                {
                    System.out.println("第" + (i + 1) + "个跟第" + (j + 1) + "个重复，值是：" + Skp.getId());
                }
            }
            SkpList.add(Skp);
        }
        return SkpList;
    }
}
