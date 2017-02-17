package com.rjxx.taxeasy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rjxx.taxeasy.service.DmFpbcService;
import com.rjxx.taxeasy.web.BaseController;

@Controller
@RequestMapping("/xfxe")
public class KpxeController extends BaseController {

	@Autowired
	private DmFpbcService dfs;
	
	@RequestMapping
	public String index() throws Exception {
		request.setAttribute("xfs", getXfList());
		request.setAttribute("xf", getXfList().get(0));
		request.setAttribute("kpds", getSkpList());
		request.setAttribute("skp", getSkpList().get(0));
		request.setAttribute("kplxs", getSkpList().get(0).getKplx().split(","));
		request.setAttribute("bc", dfs.findAllByParams(null));
		return "kpxe/index";
	}
}
