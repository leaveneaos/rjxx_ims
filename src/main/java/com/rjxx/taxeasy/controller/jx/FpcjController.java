package com.rjxx.taxeasy.controller.jx;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2018-01-29.
 * 进项管理-发票采集
 */
@Controller
@RequestMapping("/fpcj")
public class FpcjController {


    @RequestMapping
    public String index() throws Exception {
        return "jx/fpcj/index";
    }

}
