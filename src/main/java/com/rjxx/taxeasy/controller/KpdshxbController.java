package com.rjxx.taxeasy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rjxx.taxeasy.domains.Sm;
import com.rjxx.taxeasy.filter.SystemControllerLog;
import com.rjxx.taxeasy.service.SmService;
import com.rjxx.taxeasy.web.BaseController;

@Controller
@RequestMapping("/kpdshxb")
public class KpdshxbController extends BaseController{
	
	@Autowired
	private SmService smService;
	@RequestMapping
	@SystemControllerLog(description = "开票单审核页面进入",key = "")
	public String index() {
		request.setAttribute("xfList", getXfList());
		request.setAttribute("skpList", getSkpList());
		List<Sm> list = smService.findAllByParams(new Sm());
		request.setAttribute("smlist", list);
		return "kpdshxb/index";
	}

}
