package com.rjxx.taxeasy.controller;

import com.rjxx.taxeasy.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by xlm on 2017/4/14.
 */
@Controller
@RequestMapping("/ding")
public class DingDingController extends BaseController {

    @RequestMapping
    public String index() throws Exception {
		String corpid=request.getParameter("corpid");//企业id
        request.setAttribute("corpid", corpid);
        return "dingding/index";
    }
}
