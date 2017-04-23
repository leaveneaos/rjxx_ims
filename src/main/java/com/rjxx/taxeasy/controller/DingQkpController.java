package com.rjxx.taxeasy.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rjxx.taxeasy.configuration.Message;
import com.rjxx.taxeasy.dingding.Helper.HttpHelper;
import com.rjxx.taxeasy.domains.IsvCorpApp;
import com.rjxx.taxeasy.domains.IsvCorpSuiteJsapiTicket;
import com.rjxx.taxeasy.domains.Jyxxsq;
import com.rjxx.taxeasy.service.IsvCorpAppService;
import com.rjxx.taxeasy.service.IsvCorpSuiteJsapiTicketService;
import com.rjxx.taxeasy.service.JyxxsqService;
import com.rjxx.taxeasy.web.BaseController;

@Controller
@RequestMapping("/dingqkp")
public class DingQkpController extends BaseController{
	
	@Autowired
	private IsvCorpSuiteJsapiTicketService isvcorpsuitejsapiticketservice;
	@Autowired
	private IsvCorpAppService isvcorpappservice;
	@Autowired
	private JyxxsqService jyxxsqservice;
	
	@RequestMapping
    public String index() throws Exception {
		String code = request.getParameter("code");
		String corpId = request.getParameter("corpid");
		String agentId = request.getParameter("agentId");
		String userid = request.getParameter("userid");
		Map map2=new HashMap();
		map2.put("corpId", corpId);
		IsvCorpSuiteJsapiTicket  isvcorptoken=isvcorpsuitejsapiticketservice.findOneByParams(map2);
		String accessToken=isvcorptoken.getCorpaccesstoken();
		//IsvCorpApp isvcorpapp=   isvcorpappservice.findOneByParams(map2);
		String sqlsh=request.getParameter("sqlsh");
		Map map=new HashMap();
		map.put("sqlsh", sqlsh);
		Jyxxsq jyxxsq=jyxxsqservice.findOneByParams(map);
		
		Message message=new Message();
		message.setAgentid(agentId);
		message.setCode(code);
		message.setMsgtype("text");
		message.setTouser(userid);
		message.setToparty("");
		Message.text text=new Message.text();
		text.setContent("您提交的开票单已成功！交易流水号为"+jyxxsq.getJylsh());
		message.setText(text);
		System.out.println(JSON.toJSONString(message));
		JSONObject ss=HttpHelper.httpPost("https://oapi.dingtalk.com/message/sendByCode?access_token="+accessToken, message);
		
		
        return "dingding/qkp";
    }
}
