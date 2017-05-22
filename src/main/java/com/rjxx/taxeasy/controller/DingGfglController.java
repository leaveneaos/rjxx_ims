package com.rjxx.taxeasy.controller;

import com.rjxx.taxeasy.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2017-05-22.
 */
@Controller
@RequestMapping("/dinggfgl")
public class DingGfglController extends BaseController{
    @RequestMapping
    public String index() throws Exception {
        return "dingding/gfgl";
    }

}
