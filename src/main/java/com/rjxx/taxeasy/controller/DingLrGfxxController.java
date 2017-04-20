package com.rjxx.taxeasy.controller;



import java.net.URLDecoder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rjxx.taxeasy.web.BaseController;

@Controller
@RequestMapping("/dinglrgfxx")
public class DingLrGfxxController extends BaseController{
	
	@RequestMapping
    public String index() throws Exception {
		request.setCharacterEncoding("utf-8");
		String corpid=request.getParameter("corpid");//企业id
    	String userid=request.getParameter("userid");//钉钉用户id
		String xfmc=request.getParameter("xfmc");//销方名称
		String kprq=request.getParameter("kprq");//开票日期
		String fpzldm=request.getParameter("fpzldm");//发票种类
		String bz=request.getParameter("bz");//备注
		String ddh=request.getParameter("ddh");//订单号
        request.setAttribute("corpid", corpid);
        request.setAttribute("xfmc", URLDecoder.decode(xfmc,"utf8"));
        request.setAttribute("kprq", kprq);
        request.setAttribute("fpzldm", fpzldm);
        request.setAttribute("bz", URLDecoder.decode(bz,"utf8"));
        request.setAttribute("ddh", ddh);
        request.setAttribute("userid", userid);
        return "dingding/lrgfxx";
    }
}
