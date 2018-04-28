package com.rjxx.taxeasy.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rjxx.taxeasy.domains.Csb;
import com.rjxx.taxeasy.domains.Skp;
import com.rjxx.taxeasy.service.CsbService;
import com.rjxx.taxeasy.web.BaseController;

@Controller
@RequestMapping("/gzsz")
public class GzszController extends BaseController{
	@Autowired
	private CsbService csbService;
	
	@RequestMapping
	public String index() {
		//int xfid = getXfid();
		//String gsdm = getGsdm();
		//List<Skp> list = getSkpList();
		// getCsb();
		Map params = new HashMap<>();
		List<Integer> ids = new ArrayList<>();
		ids.add(33);
		ids.add(32);
		ids.add(34);
		ids.add(37);
		ids.add(50);
		ids.add(51);
		ids.add(52);
		ids.add(56);
		params.put("ids",ids);
		List<Csb> list = csbService.findBySql(params);
		request.setAttribute("csbs", list);
		
		return "gzsz/index";
	}
}
