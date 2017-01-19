package com.rjxx.taxeasy.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//import com.rjxx.taxeasy.service.BmbbService;
import com.rjxx.taxeasy.web.BaseController;

@Controller
@RequestMapping("/kpd")
public class KpdController extends BaseController {
//	@Autowired
//	private BmbbService bs;

	@RequestMapping
	public String index() throws Exception {
		session.setAttribute("xfs", getXfList());
		session.setAttribute("xf", getXfList().get(0));
//		session.setAttribute("bmbbs", bs.findAllByParams(new HashMap<>()));
		return "kpd/index";
	}
}
