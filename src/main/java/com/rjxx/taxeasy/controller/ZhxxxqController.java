package com.rjxx.taxeasy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rjxx.taxeasy.web.BaseController;
/**
 * 账户信息详情
 * @author xlm
 *
 */
@Controller
@RequestMapping("/zhxxxq")
public class ZhxxxqController extends BaseController{
	
	
	@RequestMapping
	public String index() {
		return "zhxxxq/index";
	}

}
