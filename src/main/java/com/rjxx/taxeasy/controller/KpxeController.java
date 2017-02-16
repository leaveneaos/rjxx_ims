package com.rjxx.taxeasy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rjxx.taxeasy.web.BaseController;

@Controller
@RequestMapping("/xfxe")
public class KpxeController extends BaseController {

	@RequestMapping
	public String index() throws Exception {
		session.setAttribute("xfs", getXfList());
		session.setAttribute("xf", getXfList().get(0));
		session.setAttribute("kpds", getSkpList());
		session.setAttribute("skp", getSkpList().get(0));
		session.setAttribute("kplxs", getSkpList().get(0).getKplx().split(","));
		return "kpxe/index";
	}
}
