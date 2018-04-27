package com.rjxx.taxeasy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.rjxx.taxeasy.dubbo.business.ims.service.DubboSkpService;
import com.rjxx.taxeasy.dubbo.business.tcs.service.DubboInvoiceService;
import com.rjxx.taxeasy.web.BaseController;
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
public class TestCRESTVSKP extends BaseController {


    @Reference(version = "1.0.0",group = "tcs",timeout = 12000,retries = '0')
    private DubboInvoiceService dubboInvoiceService;

    @Reference(version = "1.0.0",group = "ims",timeout = 12000,retries = '0')
    private DubboSkpService dubboSkpService;

    @RequestMapping("/register")
    @ResponseBody
    public String register(String skpid) throws Exception {
        return dubboSkpService.deviceAuth(Integer.valueOf(skpid));
    }

    @RequestMapping("/inputUDiskPassword")
    @ResponseBody
    public String inputUDiskPassword(String skpid) throws Exception {
        return dubboSkpService.inputUDiskPassword(Integer.valueOf(skpid));
    }
}
