package com.rjxx.taxeasy.controller;

import com.rjxx.taxeasy.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by xlm on 2017/5/31.
 */
@Controller
@RequestMapping("/sgkj")
public class SgkjController extends BaseController{

    @RequestMapping
    public  String index()throws Exception{
        return "sgkj/index";
    }

}
