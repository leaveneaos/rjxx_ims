package com.rjxx.taxeasy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rjxx.taxeasy.web.BaseController;

@Controller
@RequestMapping("/fpgz")
public class FpgzController extends BaseController{

	@RequestMapping
	public String index() {
		request.setAttribute("xfList", getXfList());
		request.setAttribute("skpList", getSkpList());
		return "fpgz/index";
	}

}
