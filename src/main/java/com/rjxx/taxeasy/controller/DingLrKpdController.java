package com.rjxx.taxeasy.controller;

import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.dingtalk.oapi.lib.aes.DingTalkJsApiSingnature;
import com.dingtalk.oapi.lib.aes.Utils;
import com.rjxx.taxeasy.domains.IsvCorpApp;
import com.rjxx.taxeasy.domains.IsvCorpSuiteJsapiTicket;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.service.IsvCorpAppService;
import com.rjxx.taxeasy.service.IsvCorpSuiteJsapiTicketService;
import com.rjxx.taxeasy.web.BaseController;

/**
 * 录入开票单开票单
 * Created by xlm on 2017/4/14.
 */
@Controller
@RequestMapping("/dinglrkpd")
public class DingLrKpdController extends BaseController{
    
	@Autowired
	private IsvCorpSuiteJsapiTicketService isvcorpsuitejsapiticketservice;
	@Autowired
	private IsvCorpAppService isvcorpappservice;
	@Autowired
	protected AuthenticationManager authenticationManager;
    @RequestMapping
    public String index() throws Exception {
    	String corpid=request.getParameter("corpid");//企业id
    	String userid=request.getParameter("userid");//钉钉用户id
        request.setAttribute("corpid", corpid);
        request.setAttribute("userid", userid);
    	UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("zydc", "aff");
		token.setDetails(new WebAuthenticationDetails(request));
		Authentication authenticatedUser = authenticationManager.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
		session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
				SecurityContextHolder.getContext());
		List<Xf> xflist=getXfList();
		System.out.println(JSON.toJSON(xflist));
		 request.setAttribute("xflist", xflist);
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
   	    url = check(url,corpid,isvCorpSuiteJsapiTicket.getSuiteKey(),isvCorpApp.getAppId());
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
    private String check(String url,String corpId,String suiteKey,Long appId) throws Exception{ 
        try {
            url = URLDecoder.decode(url,"UTF-8");
            URL urler = new URL(url);
            StringBuffer urlBuffer = new StringBuffer();
            urlBuffer.append(urler.getProtocol());
            urlBuffer.append(":");
            if (urler.getAuthority() != null && urler.getAuthority().length() > 0) {
                urlBuffer.append("//");
                urlBuffer.append(urler.getAuthority());
            }
            if (urler.getPath() != null) {
                urlBuffer.append(urler.getPath());
            }
            if (urler.getQuery() != null) {
                urlBuffer.append('?');
                urlBuffer.append(URLDecoder.decode(urler.getQuery(), "utf-8"));
            }
            url = urlBuffer.toString();
        } catch (Exception e) {
            throw new IllegalArgumentException("url非法");
        }
        return url;
    }
}
