package com.rjxx.taxeasy.controller;

import com.alibaba.fastjson.JSON;
import com.rjxx.taxeasy.bizcomm.utils.Transferdata;
import com.rjxx.taxeasy.domains.*;
import com.rjxx.taxeasy.service.JylsService;
import com.rjxx.taxeasy.service.JyspmxService;
import com.rjxx.taxeasy.service.KplsService;
import com.rjxx.taxeasy.service.KpspmxService;
import com.rjxx.taxeasy.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xlm on 2017/7/6.
 */
@Controller
@RequestMapping("/QyData")
public class QyDataController extends BaseController {

    @Autowired
    private KplsService kplsService;
    @Autowired
    private KpspmxService kpspmxService;
    @Autowired
    private JylsService jylsService;
    @Autowired
    private JyspmxService jyspmxService;

    @RequestMapping
    @ResponseBody
    public Map transferdata(){
        Map result=new HashMap();
        String gsdm=getGsdm();
        List<Object> jylslist=Transferdata.getdata("t_jyls",gsdm,0,0,0,0);
        List<Jyls> jylsList=new ArrayList<>();
        List<Xf> xfList=getXfList();
        List<Skp> skpList=getSkpList();

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
            for(Jyls jyls:jylsList){
                List<Object> jyspmxlist=Transferdata.getdata("t_jyspmx",gsdm,jyls.getDjh(),0,0,0);
                List<Object> kplslist=Transferdata.getdata("t_kpls",gsdm,jyls.getDjh(),0,0,0);
                jyls.setXfid(xfList.get(0).getId());
                jyls.setSkpid(skpList.get(0).getId());
                jyls.setDjh(null);
                jylsService.save(jyls);
                List<Jyspmx> jyspmxList=reload1(jyspmxlist);
                for(Jyspmx jyspmx:jyspmxList){
                    jyspmx.setId(null);
                    jyspmx.setDjh(jyls.getDjh());
                    jyspmxService.save(jyspmx);
                }
                List<Kpls> kplsList=reload(kplslist);
                for(Kpls kpls:kplsList){
                    List<Object> kpspmxlist=Transferdata.getdata("t_kpspmx",gsdm,0,kpls.getKplsh(),0,0);
                    kpls.setDjh(jyls.getDjh());
                    kpls.setXfid(xfList.get(0).getId());
                    kpls.setSkpid(skpList.get(0).getId());
                    kpls.setKplsh(null);
                    kplsService.save(kpls);
                    List<Kpspmx> kpspmxList=reload2(kpspmxlist);
                    for(Kpspmx kpspmx:kpspmxList){
                        kpspmx.setId(null);
                        kpspmx.setDjh(jyls.getDjh());
                        kpspmx.setKplsh(kpls.getKplsh());
                        kpspmxService.save(kpspmx);
                    }
                }
            }

        return result;
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
