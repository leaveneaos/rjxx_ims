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
import com.rjxx.taxeasy.bizcomm.utils.DataOperte;
import com.rjxx.taxeasy.domains.Skp;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.service.JylsService;
import com.rjxx.taxeasy.service.JyspmxService;
import com.rjxx.taxeasy.service.KplsService;
import com.rjxx.taxeasy.vo.Fpcxvo;
import com.rjxx.taxeasy.web.BaseController;

@Controller
@RequestMapping("/fphk")
public class FphkController extends BaseController {
	@Autowired
	private KplsService kplsService;
	@Autowired
	private JylsService jylsService;
	@Autowired
	private JyspmxService jymxService;
	@Autowired
	private DataOperte ac;

	@RequestMapping
	public String index() throws Exception {
		request.setAttribute("xfs", getXfList());
		return "fphk/index";
	}

	@RequestMapping(value = "/getKplsList")
	@ResponseBody
	public Map<String, Object> getItems(int length, int start, int draw, String kprqq, String kprqz, String fphm,
			String fpdm, String ddh, String gfmc) throws Exception {
		Map result = new HashMap<>();
		Pagination pagination = new Pagination();
		pagination.setPageNo(start / length + 1);
		pagination.setPageSize(length);
		String gsdm = getGsdm();
		pagination.addParam("gsdm", gsdm);
		List<Xf> xfs = getXfList();
		if (xfs != null && xfs.size() > 0) {
			pagination.addParam("xfs", xfs);
		}
		List<Skp> skps = getSkpList();
		if (skps != null && skps.size() > 0) {
			pagination.addParam("skps", skps);
		}
		pagination.addParam("kprqq", kprqq);
		pagination.addParam("kprqz", kprqz);
		pagination.addParam("fphm", fphm);
		pagination.addParam("fpdm", fpdm);
		pagination.addParam("ddh", ddh);
		pagination.addParam("gfmc", gfmc);
		List<Fpcxvo> hkList = kplsService.findFphkByPage(pagination);
		int total = pagination.getTotalRecord();
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		result.put("draw", draw);
		result.put("data", hkList);
		return result;
	}

	@RequestMapping(value = "/ykfphk")
	@ResponseBody
	public Map<String, Object> hk(Integer kplsh, String djh, String yfpdm, String yfphm, String xfsh, String jylsh)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		int id = Integer.parseInt(djh);
		List<Integer> kplshList = new ArrayList<>();
		kplshList.add(kplsh);
		try {
			ac.addJyls(kplshList, id, "13", getYhid());
			ac.saveLog(id, "01", "0", "电子发票服务平台换开操作", "已向服务端发送换开请求", getYhid(), xfsh, jylsh);
			result.put("success", true);
			result.put("msg", "换开请求提交成功，请注意查看操作结果！");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("failure", true);
			result.put("msg", "后台出现错误: " + e.getMessage());
			ac.saveLog(id, "92", "1", "", "电子发票服务平台换开请求失败或更新jyls表中clztdm失败", 2, xfsh, jylsh);
		}
		return result;
	}
}
