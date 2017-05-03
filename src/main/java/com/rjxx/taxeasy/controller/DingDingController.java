package com.rjxx.taxeasy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.open.client.api.model.corp.CorpUserDetail;
import com.rjxx.taxeasy.configuration.Message;
import com.rjxx.taxeasy.dingding.Helper.HttpHelper;
import com.rjxx.taxeasy.dingding.Helper.UserHelper;
import com.rjxx.taxeasy.domains.Gsxx;
import com.rjxx.taxeasy.domains.IsvCorpSuiteJsapiTicket;
import com.rjxx.taxeasy.domains.Yh;
import com.rjxx.taxeasy.service.*;
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
	private GsxxService gsxxService;
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
	/**
	 * 获取钉钉用户用户是否在平台用户中存在
	 *
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getsqzhinfo")
	@ResponseBody
	public Map getsqzhinfo() throws Exception {
		String userid = request.getParameter("userid");
		String corpId = request.getParameter("corpid");
		System.out.println("userid:"+userid+" corpid:"+corpId);
		Map params=new HashMap();
		params.put("corpId", corpId);
		params.put("userid", userid);
		Yh yh=yhService.findOneByParams(params);
		Gsxx gsxx=gsxxService.findOneByDingCorpid(params);
		Map map=new HashMap();
		if(gsxx==null){
			if(yh==null){
				map.put("code","0");//该公司信息及账户未在平台中存在
			}else if(yh.getSup()!="1"){
				map.put("code","1 ");//该公司信息及账户未在平台中存在
			}
		}
		return map;
	}
}
