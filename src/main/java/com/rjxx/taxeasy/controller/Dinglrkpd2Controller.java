package com.rjxx.taxeasy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rjxx.taxeasy.domains.Jyxxsq;
import com.rjxx.taxeasy.web.BaseController;

@Controller
@RequestMapping("/dinglrkpd2")
public class Dinglrkpd2Controller extends BaseController{


	@RequestMapping
    public String index() throws Exception {
		String corpid=request.getParameter("corpid");//企业id
		//Jyxxsq Jyxxsq=(Jyxxsq)request.getParameter("Jyxxsq");//销方名称
        return "dingding/lrkpd2";
    }
}
