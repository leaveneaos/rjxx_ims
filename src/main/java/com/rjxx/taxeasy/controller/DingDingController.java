package com.rjxx.taxeasy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.open.client.ServiceFactory;
import com.dingtalk.open.client.api.model.corp.CorpUserDetail;
import com.dingtalk.open.client.api.service.corp.JsapiService;
import com.dingtalk.open.client.api.service.corp.MessageService;
import com.rjxx.taxeasy.configuration.Message;
import com.rjxx.taxeasy.dingding.Helper.HttpHelper;
import com.rjxx.taxeasy.dingding.Helper.UserHelper;
import com.rjxx.taxeasy.domains.IsvCorpSuiteJsapiTicket;
import com.rjxx.taxeasy.domains.IsvCorpToken;
import com.rjxx.taxeasy.domains.IsvSuiteToken;
import com.rjxx.taxeasy.service.IsvCorpAppService;
import com.rjxx.taxeasy.service.IsvCorpSuiteJsapiTicketService;
import com.rjxx.taxeasy.service.IsvCorpTokenService;
import com.rjxx.taxeasy.service.IsvSuiteService;
import com.rjxx.taxeasy.service.IsvSuiteTokenService;
import com.rjxx.taxeasy.vo.Spvo;
import com.rjxx.taxeasy.web.BaseController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by xlm on 2017/4/14.
 */
@Controller
@RequestMapping("/ding")
public class DingDingController extends BaseController {
	
	@Autowired
	private IsvCorpSuiteJsapiTicketService isvcorpsuitejsapiticketservice;
	@Autowired
	private IsvCorpAppService isvcorpappservice;
	@Autowired
	private IsvSuiteTokenService isvsuitetokenservice;
	@Autowired
	private IsvCorpTokenService isvcorptokenservice;
    @RequestMapping
    public String index() throws Exception {
		String corpid=request.getParameter("corpid");//企业id
        request.setAttribute("corpid", corpid);
        return "dingding/index";
    }
    
    /**
	 * 获取用户信息
	 *
	 * @param 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userinfo")
	@ResponseBody
	public CorpUserDetail getUserInfo() throws Exception {
		String code = request.getParameter("code");
		String corpId = request.getParameter("corpid");
		System.out.println("code:"+code+" corpid:"+corpId);
		Map params=new HashMap();
		params.put("corpId", corpId);
		IsvCorpSuiteJsapiTicket  isvcorptoken=isvcorpsuitejsapiticketservice.findOneByParams(params);
		String accessToken=isvcorptoken.getCorpaccesstoken();
		CorpUserDetail user = (CorpUserDetail)UserHelper.getUser(accessToken, UserHelper.getUserInfo(accessToken, code).getUserid());
		return user;
	}
	    /**
		 * 发送会话消息
		 *
		 * @param 
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/sendmessage")
		@ResponseBody
		public JSONObject sendmessage() throws Exception {
			String code = request.getParameter("code");
			String corpId = request.getParameter("corpid");
			String agentId = request.getParameter("agentId");
			String userid = request.getParameter("userid");
			System.out.println("code:"+code+" corpid:"+corpId);
			Map params=new HashMap();
			params.put("corpId", corpId);
			IsvCorpSuiteJsapiTicket  isvcorptoken=isvcorpsuitejsapiticketservice.findOneByParams(params);
			String accessToken=isvcorptoken.getCorpaccesstoken();
			Message message=new Message();
			message.setAgentid(agentId);
			message.setCode(code);
			message.setMsgtype("text");
			message.setTouser(userid);
			message.setToparty("");
			Message.text text=new Message.text();
			text.setContent("张三的请假申请");
			message.setText(text);
			System.out.println(JSON.toJSONString(message));
			JSONObject ss=HttpHelper.httpPost("https://oapi.dingtalk.com/message/sendByCode?access_token="+accessToken, message);
			
			
			/*ServiceFactory serviceFactory = ServiceFactory.getInstance();

			MessageService messageservice = serviceFactory.getOpenService(MessageService.class);
			messageservice.sendToCorpConversation(paramString1, paramString2, paramString3, paramString4, paramString5, paramMessageBody)*/
			
			/*IsvCorpSuiteJsapiTicket  isvcorptoken=isvcorpsuitejsapiticketservice.findOneByParams(params);
			String accessToken=isvcorptoken.getCorpaccesstoken();
			CorpUserDetail user = (CorpUserDetail)UserHelper.getUser(accessToken, UserHelper.getUserInfo(accessToken, code).getUserid());*/
			return ss;
		}
}
