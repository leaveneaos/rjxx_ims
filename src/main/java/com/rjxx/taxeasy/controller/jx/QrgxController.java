package com.rjxx.taxeasy.controller.jx;

import com.rjxx.taxeasy.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2018-01-29.
 * 进项管理-确认勾选
 */
@Controller
@RequestMapping("/qrgx")
public class QrgxController extends BaseController{

    @RequestMapping
    public String index() throws Exception {
        return "jx/qrgx/index";
    }
}
