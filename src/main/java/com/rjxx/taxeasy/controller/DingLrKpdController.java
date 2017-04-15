package com.rjxx.taxeasy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rjxx.taxeasy.web.BaseController;

/**
 * 录入开票单开票单
 * Created by xlm on 2017/4/14.
 */
@Controller
@RequestMapping("dinglrkpd")
public class DingLrKpdController extends BaseController{

    @RequestMapping
    public String index() throws Exception {
        return "dingding/lrkpd";
    }
}
