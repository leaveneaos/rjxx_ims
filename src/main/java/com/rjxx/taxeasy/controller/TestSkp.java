package com.rjxx.taxeasy.controller;

import com.rjxx.taxeasy.bizcomm.utils.SkService;
import com.rjxx.taxeasy.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName TestCRESTVSKP
 * @Description TODO
 * @Author 许黎明
 * @Date 2018-04-27 19:40
 * @Version 1.0
 **/
@Controller
@RequestMapping("/testSkp")
public class TestSkp extends BaseController {



    @Autowired
    private SkService skService;

    @RequestMapping("/register")
    @ResponseBody
    public String register(String skpid) throws Exception {
        return skService.register(Integer.valueOf(skpid));
    }

    @RequestMapping("/inputUDiskPassword")
    @ResponseBody
    public String inputUDiskPassword(String skpid) throws Exception {
        return skService.inputUDiskPassword(Integer.valueOf(skpid));
    }



}
