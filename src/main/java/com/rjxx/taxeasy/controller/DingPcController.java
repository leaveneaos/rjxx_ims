package com.rjxx.taxeasy.controller;

import com.rjxx.taxeasy.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by xlm on 2017/4/24.
 */
@Controller
@RequestMapping("/dingpc")
public class DingPcController extends BaseController{
    @RequestMapping
    public String index() throws Exception {
        String corpid=request.getParameter("corpid");//企业id
        request.setAttribute("corpid", corpid);
        return "dingding/dingpc";
    }
}
