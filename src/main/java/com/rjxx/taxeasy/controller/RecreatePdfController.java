package com.rjxx.taxeasy.controller;

/**
 * Created by Administrator on 2017/7/18 0018.
 */

import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.domains.Skp;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.service.KplsService;
import com.rjxx.taxeasy.vo.Fpcxvo;
import com.rjxx.taxeasy.vo.KplsVO;
import com.rjxx.taxeasy.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/recreatePdf")
public class RecreatePdfController extends BaseController{

    @Autowired
    private KplsService kplsService;


    @RequestMapping
    public String index() {
        List<Xf> xfList = getXfList();
        request.setAttribute("xfList", xfList);
        List<Skp> skpList = getSkpList();
        request.setAttribute("skpList", skpList);
        return "recreatePdf/index";
    }

    /**
     * 初始化显示列表
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getKplsList")
    @ResponseBody
    public Map getKplsList(int length, int start, int draw, Integer xfid,Integer skpid,String ddh, String gfmc,
                           String kprqq,String kprqz,String fpzl,boolean loaddata) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        Pagination pagination = new Pagination();
        pagination.setPageNo(start / length + 1);
        pagination.setPageSize(length);
        List<Integer> xfs = new ArrayList<>();
        if (!getXfList().isEmpty()) {
            for (Xf xf : getXfList()) {
                xfs.add(xf.getId());
            }
        }
        if (xfs.size() > 0) {
            pagination.addParam("xfList", xfs);
        }
        pagination.addParam("skps", getSkpList());
        pagination.addParam("gsdm", getGsdm());
        pagination.addParam("xfid", xfid);
        pagination.addParam("skpid", skpid);
        pagination.addParam("ddh", ddh);
        pagination.addParam("gfmc", gfmc);
        if (!"".equals(kprqq)) {
            pagination.addParam("kprqq", kprqq);
        }
        if (!"".equals(kprqz)) {
            pagination.addParam("kprqz", kprqz);
        }

       // pagination.addParam("fpczlxdm", "14");
        pagination.addParam("fpzl", fpzl);
        List<Fpcxvo> list = kplsService.findPdf(pagination);
        int total = pagination.getTotalRecord();
        if(loaddata){
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
}
