package com.rjxx.taxeasy.controller;

import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.service.XfService;
import com.rjxx.taxeasy.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/jkpz")
public class JkpzController extends BaseController {

    @Autowired
    private XfService xfService;

    @RequestMapping
    public String index() {
        request.setAttribute("gsdm", getGsdm());
        Xf xf = new Xf();
        xf.setGsdm(getGsdm());
        List<Xf> xfList = xfService.findAllByParams(xf);
        request.setAttribute("xfList", xfList);
        return "jkpz/index";
    }


}
