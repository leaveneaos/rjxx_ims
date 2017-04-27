package com.rjxx.taxeasy.controller;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

import com.dingtalk.oapi.lib.aes.DingTalkJsApiSingnature;
import com.dingtalk.oapi.lib.aes.Utils;
import com.rjxx.taxeasy.domains.*;
import com.rjxx.taxeasy.service.*;
import com.rjxx.time.TimeUtil;
import com.rjxx.utils.Tools;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rjxx.taxeasy.configuration.Message;
import com.rjxx.taxeasy.dingding.Helper.HttpHelper;
import com.rjxx.taxeasy.web.BaseController;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/dingqkp")
public class DingQkpController extends BaseController{
	
	@Autowired
	private IsvCorpSuiteJsapiTicketService isvcorpsuitejsapiticketservice;
	@Autowired
	private IsvCorpAppService isvcorpappservice;
	@Autowired
	private JyxxsqService jyxxsqservice;
	@Autowired
	private SkpService skpservice;
	@Autowired
	private XfService xfService;

	@RequestMapping
    public String index() throws Exception {
		String corpid=request.getParameter("corpid");//企业id
		String userid=request.getParameter("userid");//钉钉用户id
		String sqlsh=request.getParameter("sqlsh");//钉钉用户id
		String jylsh=request.getParameter("jylsh");//钉钉用户id
		request.setAttribute("jylsh", jylsh);
		request.setAttribute("sqlsh", sqlsh);
		request.setAttribute("corpid", corpid);
		request.setAttribute("userid", userid);
        return "dingding/qkp";
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
		url = URLDecoder.decode(url,"utf8");
		String nonce = Utils.getRandomStr(8);
		Long timeStamp = System.currentTimeMillis();
		String sign = DingTalkJsApiSingnature.getJsApiSingnature(url, nonce, timeStamp, isvCorpSuiteJsapiTicket.getCorpJsapiTicket());
		System.out.println(sign);
		Map<String,Object> jsapiConfig = new HashMap<String, Object>();
		jsapiConfig.put("signature",sign);
		jsapiConfig.put("nonce",nonce);
		jsapiConfig.put("timeStamp",timeStamp);
		jsapiConfig.put("agentId",isvCorpApp.getAgentId());
		jsapiConfig.put("corpId",corpid);
		return jsapiConfig;
	}
}
