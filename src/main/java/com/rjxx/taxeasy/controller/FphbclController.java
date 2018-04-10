package com.rjxx.taxeasy.controller;

import com.rjxx.taxeasy.filter.SystemControllerLog;
import com.rjxx.taxeasy.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: zsq
 * @date: 2018/4/10 13:49
 * @describe: 发票合并处理
 */
@Controller
@RequestMapping("/fphbcl")
public class FphbclController extends BaseController {

    @RequestMapping
    @SystemControllerLog(description = "发票合并处理", key = "")
    public String index() {

        return "fphbcl/index";
    }
}
