package com.rjxx.taxeasy.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.domains.Skp;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.service.JylsService;
import com.rjxx.taxeasy.vo.Fptqvo;
import com.rjxx.taxeasy.web.BaseController;

@Controller
@RequestMapping("/fptqcx")
public class FptqcxController extends BaseController {

	@Autowired
	private JylsService jylsService;

	@RequestMapping
	public String index() {
		return "fptqcx/index";
	}

	@RequestMapping(value = "/getItems")
	@ResponseBody
	public Map<String, Object> getItems(int length, int start, int draw, String ddh, String tqrqq, String tqrqz,
			String gfmc, String tqsb, String jlly,boolean loaddata) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Pagination pagination = new Pagination();
		pagination.setPageNo(start / length + 1);
		pagination.setPageSize(length);
		String gsdm = getGsdm();
		List<Xf> xfs = getXfList();
		if (xfs != null && xfs.size() > 0) {
			pagination.addParam("xfs", xfs);
		}
		List<Skp> skps = getSkpList();
		if (skps != null && skps.size() > 0) {
			pagination.addParam("skps", skps);
		}
		pagination.addParam("gsdm", gsdm);
		pagination.addParam("xfs", xfs);
		pagination.addParam("skps", skps);
		pagination.addParam("ddh", ddh);
		pagination.addParam("tqrqq", tqrqq);
		pagination.addParam("tqrqz", tqrqz);
		pagination.addParam("gfmc", gfmc);
		pagination.addParam("tqsb", tqsb);
		pagination.addParam("jlly", jlly);
		List<Fptqvo> tqjlList = jylsService.fptqcx(pagination);
		int total = pagination.getTotalRecord();
		if(loaddata){
			result.put("recordsTotal", total);
			result.put("recordsFiltered", total);
			result.put("draw", draw);
			result.put("data", tqjlList);
		}else {
			result.put("recordsTotal", 0);
			result.put("recordsFiltered", 0);
			result.put("draw", draw);
			result.put("data", new ArrayList<>());
		}
		return result;
	}

}
