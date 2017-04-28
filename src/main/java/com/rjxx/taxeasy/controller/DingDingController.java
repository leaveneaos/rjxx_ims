package com.rjxx.taxeasy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.open.client.api.model.corp.CorpUserDetail;
import com.rjxx.taxeasy.configuration.Message;
import com.rjxx.taxeasy.dingding.Helper.HttpHelper;
import com.rjxx.taxeasy.dingding.Helper.UserHelper;
import com.rjxx.taxeasy.domains.IsvCorpSuiteJsapiTicket;
import com.rjxx.taxeasy.service.IsvCorpAppService;
import com.rjxx.taxeasy.service.IsvCorpSuiteJsapiTicketService;
import com.rjxx.taxeasy.service.IsvCorpTokenService;
import com.rjxx.taxeasy.service.IsvSuiteTokenService;
import com.rjxx.taxeasy.service.YhService;
import com.rjxx.taxeasy.web.BaseController;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
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
	@Autowired
	private YhService yhService;
	@Autowired
	protected AuthenticationManager authenticationManager;
	
    @RequestMapping
    public String index() throws Exception {
		String corpid=request.getParameter("corpid");//企业id
		System.out.println(corpid);
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

}
