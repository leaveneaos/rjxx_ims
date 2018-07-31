package com.rjxx.taxeasy.controller;

import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.bizcomm.utils.FphxUtil;
import com.rjxx.taxeasy.domains.*;
import com.rjxx.taxeasy.service.FphxwsjlService;
import com.rjxx.taxeasy.service.GsxxService;
import com.rjxx.taxeasy.service.JylsService;
import com.rjxx.taxeasy.service.KplsService;
import com.rjxx.taxeasy.vo.Yjfsvo;
import com.rjxx.taxeasy.web.BaseController;
import com.rjxx.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: zsq
 * @date: 2018/7/30 15:33
 * @describe: 手动回写
 */

@Controller
@RequestMapping("/sdhx")
public class SdhxController extends BaseController {


    @Autowired
    private KplsService kplsService;
    @Autowired
    private FphxwsjlService fphxwsjlService;
    @Autowired
    private JylsService jylsService;
    @Autowired
    private GsxxService gsxxService;
    @Autowired
    private FphxUtil fphxUtil;

    @RequestMapping
    public String index() {
        List<Xf> xfList = getXfList();
        request.setAttribute("xfList", xfList);
        List<Skp> skpList = getSkpList();
        request.setAttribute("skpList", skpList);
        return "sdhx/index";
    }

    /**
     * 初始化显示回写失败列表
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getFphxList")
    @ResponseBody
    public Map getKplsList(int length, int start, int draw, String ddh, String gfmc,
                           String xfid, String kprqq, String kprqz, boolean loaddata) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        Pagination pagination = new Pagination();
        pagination.setPageNo(start / length + 1);
        pagination.setPageSize(length);
        pagination.addParam("ddh", ddh);
        //pagination.addParam("gfmc", gfmc);
        pagination.addParam("gsdm",getGsdm());
        pagination.addParam("xfi",xfid);
        List<Xf> xfs = getXfList();
        String xfStr = "";
        if (xfs != null) {
            for (int i = 0; i < xfs.size(); i++) {
                int xfid2 = xfs.get(i).getId();
                if (i == xfs.size() - 1) {
                    xfStr += xfid2 + "";
                } else {
                    xfStr += xfid2 + ",";
                }
            }
        }
        if(null !=xfid && !xfid.equals("")){
            pagination.addParam("xfid2", xfid);
        }
        String[] xfid2 = xfStr.split(",");

        if (xfid2.length == 0) {
            xfid2 = null;
        }
        String skpStr = "";
        List<Skp> skpList = getSkpList();
        if (skpList != null) {
            for (int j = 0; j < skpList.size(); j++) {
                int skpid = skpList.get(j).getId();
                if (j == skpList.size() - 1) {
                    skpStr += skpid + "";
                } else {
                    skpStr += skpid + ",";
                }
            }
        }
        String[] skpid = skpStr.split(",");
        if (skpid.length == 0) {
            skpid = null;
        }
        if (!"".equals(kprqq)) {
            pagination.addParam("kprqq", kprqq);
        }
        if (!"".equals(kprqz)) {
            pagination.addParam("kprqz", kprqz);
        }
        pagination.addParam("xfid",xfid2);
        pagination.addParam("skpid",skpid);
        if(loaddata){
            List<Yjfsvo> list = kplsService.findFphxwsjl(pagination);
            int total = pagination.getTotalRecord();
            result.put("recordsTotal",total);
            result.put("recordsFiltered",total);
            result.put("draw",draw);
            result.put("data",list);
        }else{
            result.put("recordsTotal",0);
            result.put("recordsFiltered",0);
            result.put("draw",draw);
            result.put("data",new ArrayList<>());
        }
        return result;
    }
    @RequestMapping(value = "/callback",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> update(String djhArr) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        String msg="";
        String[] ids = djhArr.split(",");
        for (int j = 0; j < ids.length; j++) {
            Fphxwsjl fphxwsjl = fphxwsjlService.findOne(Integer.valueOf(ids[j]));
            Map params = new HashMap();
            params.put("kplsh", fphxwsjl.getKplsh());
            Kpls kpls = kplsService.findOneByParams(params);
            Jyls jyls = jylsService.findOne(kpls.getDjh());
            Map parms = new HashMap();
            parms.put("gsdm", fphxwsjl.getGsdm());
            Gsxx gsxx = gsxxService.findOneByParams(parms);
            boolean flag =true;
            try {
                flag = fphxUtil.fphx(kpls, jyls, gsxx);
            } catch (Exception e) {
                e.printStackTrace();
                msg +="第"+(j+1)+"行,订单号："+fphxwsjl.getDdh()+"重新回写失败!";
            }
            if (!flag) {
                msg +="第"+(j+1)+"行，订单号："+fphxwsjl.getDdh()+"重新回写失败!";
            }
        }
        if(StringUtils.isNotBlank(msg)){
            result.put("success", false);
            result.put("msg", msg);
            return result;
        }
        result.put("success", true);
        result.put("msg", "重新回写成功");
        return result;
    }
}
