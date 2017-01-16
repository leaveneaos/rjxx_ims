package com.rjxx.taxeasy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rjxx.taxeasy.web.BaseController;


@Controller
@RequestMapping("/mydy")
public class DyglController extends BaseController{
	
	@RequestMapping
	public String index() {
		return "mydy/index";
	}

}
