package com.rjxx.taxeasy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dinglrkpd2")
public class Dinglrkpd2Controller {


	@RequestMapping
    public String index() throws Exception {
        return "dingding/lrkpd2";
    }
}
