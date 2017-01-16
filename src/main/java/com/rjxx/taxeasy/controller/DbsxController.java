package com.rjxx.taxeasy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rjxx.taxeasy.web.BaseController;


@Controller
@RequestMapping("/dbsx")
public class DbsxController extends BaseController{
	
	@RequestMapping
	public String index() {
		return "dbsx/index";
	}

}
