package com.rjxx.taxeasy.controller;

/**
 * Created by Administrator on 2017/7/18 0018.
 */

import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.bizcomm.utils.InvoiceResponse;
import com.rjxx.taxeasy.bizcomm.utils.SkService;
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
    @Autowired
    private SkService skService;


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
    public Map getKplsList(int length, int start, int draw,String ddh, String gfmc,
                           String kprqq,String kprqz,boolean loaddata) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        Pagination pagination = new Pagination();
        pagination.setPageNo(start / length + 1);
        pagination.setPageSize(length);
        pagination.addParam("ddh", ddh);
        pagination.addParam("gfmc", gfmc);
        if (!"".equals(kprqq)) {
            pagination.addParam("kprqq", kprqq);
        }
        if (!"".equals(kprqz)) {
            pagination.addParam("kprqz", kprqz);
        }
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
    @RequestMapping(value = "/recreate")
    @ResponseBody
    public Map<String, Object> update(String djhArr) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();

        String[] djhs = djhArr.split(",");
        for (int j = 0; j < djhs.length; j++) {
            InvoiceResponse flag = skService.ReCreatePdf(Integer.valueOf(djhs[j]));
            if (flag.getReturnCode().equals("0000")) {
                result.put("success", true);
                result.put("msg", "重新生成PDF成功！");
            }else{
                result.put("success", false);
                result.put("msg", "第"+(j+1)+"重新生成PDF失败!"+flag.getReturnMessage());
                return result;
            }
        }
        return result;
    }
}
