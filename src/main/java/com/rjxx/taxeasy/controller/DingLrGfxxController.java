package com.rjxx.taxeasy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rjxx.taxeasy.web.BaseController;

@Controller
@RequestMapping("/dinglrgfxx")
public class DingLrGfxxController extends BaseController{
	
	@RequestMapping
    public String index() throws Exception {
        return "dingding/lrgfxx";
    }
}
