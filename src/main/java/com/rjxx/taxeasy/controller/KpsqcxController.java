package com.rjxx.taxeasy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.domains.Skp;
import com.rjxx.taxeasy.domains.Smtq;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.service.SmtqService;
import com.rjxx.taxeasy.web.BaseController;

/**
 * 发票换开审核业务
 * 
 */
@Controller
@RequestMapping("/kpsqcx")
public class KpsqcxController extends BaseController {
	@Autowired
	private SmtqService sqService;

	@RequestMapping
	public String index() throws Exception {
		return "kpsqcx/index";
	}

	@RequestMapping(value = "/getItems")
	@ResponseBody
	public Map<String, Object> getItems(int length, int start, int draw, String ddh, String gfmc, String sqrqq,
			String sqrqz) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map params = new HashMap<>();
		Pagination pagination = new Pagination();
		pagination.setPageNo(start / length + 1);
		pagination.setPageSize(length);
		String gsdm = getGsdm();
		List<Xf> xfs = getXfList();
		List<Skp> skps = getSkpList();
		if (xfs != null && xfs.size() > 0) {
			pagination.addParam("xfs", xfs);
		}
		if (skps != null && skps.size() > 0) {
			pagination.addParam("skps", skps);
		}
		pagination.addParam("fpzt", "07");
		pagination.addParam("gsdm", gsdm);
		pagination.addParam("ddh", ddh);
		pagination.addParam("gfmc", gfmc);
		pagination.addParam("sqrqq", sqrqq);
		pagination.addParam("sqrqz", sqrqz);
		List<Smtq> list = sqService.findByPage(pagination);
		int total = pagination.getTotalRecord();
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		result.put("draw", draw);
		result.put("data", list);
		return result;
	}

}
