package com.rjxx.taxeasy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rjxx.taxeasy.domains.Cszb;
import com.rjxx.taxeasy.service.CszbService;
import com.rjxx.taxeasy.web.BaseController;
import com.rjxx.utils.leshui.LeShuiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018-01-22.
 * 进项管理页面
 */
@Controller
@RequestMapping("/income")
public class IncomeController extends BaseController {

    @Autowired
    private CszbService cszbService;


    @RequestMapping
    public String index() throws Exception {
        return "fpcy/index";
    }

    /**
     * 发票查验详情
     * @param sglr_fpzl
     * @param sglr_jym
     * @param sglr_fpdm
     * @param sglr_fphm
     * @param sglr_kprq
     * @param sglr_je
     * @return
     */
    @RequestMapping(value = "/invoiceCheck" , method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> invoiceCheck(String sglr_fpzl,String sglr_jym ,String sglr_fpdm,
                                    String sglr_fphm,String sglr_kprq,String sglr_je) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            //校验是否报销
            String gsdm = getGsdm();
            Cszb sfyybx = cszbService.getSpbmbbh(gsdm, null, null, "sfyybx");
            if(sfyybx.equals("是")){
                logger.info("用于报销！");
            }
            LeShuiUtil leShuiUtil = new LeShuiUtil();
            String res = leShuiUtil.invoiceInfoForCom(sglr_fpdm, sglr_fphm, sglr_kprq, sglr_jym, sglr_je);
            JSONObject resjson = JSON.parseObject(res);
            String rtnCode = resjson.getString("RtnCode");
            String resultMsg = resjson.getString("resultMsg");
            String invoiceName = resjson.getString("invoiceName");
            String resultCode = resjson.getString("resultCode");
            if(rtnCode!=null &&rtnCode.equals("00")){
                JSONObject invoiceResult = resjson.getJSONObject("invoiceResult");
            }else {
                String invoicefalseCode = resjson.getString("invoicefalseCode");
            }
            logger.info(res);
            result.put("status", true);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", false);
            result.put("msg", "发票查验出现错误: " + e.getMessage());
        }
        return result;
    }





}
