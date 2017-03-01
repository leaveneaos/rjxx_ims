package com.rjxx.taxeasy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/kpdshxb")
public class KpdshxbController {
	@RequestMapping
	public String index() {
		return "kpdshxb/index";
	}

}
