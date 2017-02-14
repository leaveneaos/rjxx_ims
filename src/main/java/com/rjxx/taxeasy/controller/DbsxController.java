package com.rjxx.taxeasy.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjxx.taxeasy.web.BaseController;


@Controller
@RequestMapping("/dbsx")
public class DbsxController extends BaseController{
	
	@RequestMapping
	public String index() {
		return "dbsx/index";
	}
	
	@RequestMapping(value = "/getPlot")
	@ResponseBody
	public Map<String,Object> getPlot(){
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("2017-02-10",15);
		result.put("2017-02-11",3);
		result.put("2017-02-12",15);
		result.put("2017-02-13",28);
		result.put("2017-02-14",30);
		result.put("2017-02-15",47);
		return result;
	}

}
