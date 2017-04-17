package com.rjxx.taxeasy.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dingtalk.oapi.lib.aes.DingTalkJsApiSingnature;
import com.rjxx.taxeasy.domains.IsvCorpApp;
import com.rjxx.taxeasy.domains.IsvCorpSuiteJsapiTicket;
import com.rjxx.taxeasy.service.IsvCorpAppService;
import com.rjxx.taxeasy.service.IsvCorpSuiteJsapiTicketService;
import com.rjxx.taxeasy.web.BaseController;

/**
 * 录入开票单开票单
 * Created by xlm on 2017/4/14.
 */
@Controller
@RequestMapping("dinglrkpd")
public class DingLrKpdController extends BaseController{
    
	@Autowired
	private IsvCorpSuiteJsapiTicketService isvcorpsuitejsapiticketservice;
	@Autowired
	private IsvCorpAppService isvcorpappservice;
	
    @RequestMapping
    public String index() throws Exception {
    	String corpid=request.getParameter("corpid");//企业id
        request.setAttribute("corpid", corpid);
        return "dingding/lrkpd";
    }
    /**
     * 获取jsAPI签名
     * @return
     * @throws Exception
     */
    @RequestMapping("/jssqm")
    @ResponseBody
    public Map<String, Object> getItems() throws Exception {
    	String corpid=request.getParameter("corpId");//企业id
    	String url=request.getParameter("url");//当前网页url
    	Map params=new HashMap();
    	params.put("corpId", corpid);
    	IsvCorpSuiteJsapiTicket isvCorpSuiteJsapiTicket=isvcorpsuitejsapiticketservice.findOneByParams(params);
    	IsvCorpApp isvCorpApp=isvcorpappservice.findOneByParams(params);
    	 String nonce = com.dingtalk.oapi.lib.aes.Utils.getRandomStr(8);
         Long timeStamp = System.currentTimeMillis();
         String sign = DingTalkJsApiSingnature.getJsApiSingnature(url, nonce, timeStamp, isvCorpSuiteJsapiTicket.getCorpJsapiTicket());
         Map<String,Object> jsapiConfig = new HashMap<String, Object>();
         jsapiConfig.put("signature",sign);
         jsapiConfig.put("nonce",nonce);
         jsapiConfig.put("timeStamp",timeStamp);
         jsapiConfig.put("agentId",isvCorpApp.getAgentId());
         jsapiConfig.put("corpId",corpid);
    	return jsapiConfig;
    }
}
