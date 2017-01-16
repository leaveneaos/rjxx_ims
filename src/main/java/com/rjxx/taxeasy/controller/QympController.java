package com.rjxx.taxeasy.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjxx.taxeasy.service.SkpService;
import com.rjxx.taxeasy.service.XfService;
import com.rjxx.taxeasy.web.BaseController;

@Controller
@RequestMapping("/qymp")
public class QympController extends BaseController {
	
	@Autowired private XfService xs;
	@Autowired private SkpService ss;

	@RequestMapping
	public String index() throws Exception {
		return "qymp/index";
	}

	@RequestMapping(value = "/toKpd")
	public String kpd(){
		return "qymp/kpd";
	}

	@RequestMapping(value = "/toKpxe")
	public String kpxe(){
		return "qymp/kpxe";
	}
}
