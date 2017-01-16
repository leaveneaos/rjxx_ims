package com.rjxx.taxeasy.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.bizcomm.utils.DataOperte;
import com.rjxx.taxeasy.domains.Skp;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.service.CkhkService;
import com.rjxx.taxeasy.service.JylsvoService;
import com.rjxx.taxeasy.service.KplsService;
import com.rjxx.taxeasy.vo.Fpcxvo;
import com.rjxx.taxeasy.vo.Jylsvo;
import com.rjxx.taxeasy.web.BaseController;

/**
 * 发票换开审核业务
 * 
 */
@Controller
@RequestMapping("/fphksh")
public class FphkshController extends BaseController {

	@Autowired
	private KplsService kplsService;
	@Autowired
	private CkhkService ckService;
	@Autowired
	private DataOperte dc;
	@Autowired
	private JylsvoService lsvoService;

	@RequestMapping
	public String index() throws Exception {
		return "fphksh/index";
	}

	@RequestMapping(value = "/getJylsList")
	@ResponseBody
	public Map<String, Object> getItems(int length, int start, int draw, String ddh, String ztbz, String gfmc)
			throws Exception {
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
		pagination.addParam("ztbz", ztbz);
		pagination.addParam("ddh", ddh);
		pagination.addParam("gfmc", gfmc);
		List<Jylsvo> hksqList = lsvoService.findHhsqByPage(pagination);
		int total = pagination.getTotalRecord();
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		result.put("draw", draw);
		result.put("data", hksqList);
		return result;
	}

	@Transactional
	@RequestMapping(value = "/qrhk")
	@ResponseBody
	public Map<String, Object> qrhk(int djh, int sqid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		int yhid = getYhid();
		Map map = new HashMap<>();
		map.put("djh", djh);
		List<Fpcxvo> lsList = kplsService.findFpcksqKpls(map);
		List<Integer> kplshList = new ArrayList<>();
		if (lsList != null && lsList.size() > 0) {
			for (Fpcxvo item : lsList) {
				if (!"00".equals(item.getFpztdm())) {
					result.put("success", false);
					result.put("msg", "存在非正常状态的发票，不能换开！");
					return result;
				} else {
					kplshList.add(item.getKplsh());
				}
			}
			/*
			 * if(lsList.get(0).getXcyf()!=null && lsList.get(0).getXcyf()>6){
			 * result.put("success", false); result.put("msg",
			 * "超过开票日期6个月，不能重开！"); return result; }
			 */
		}
		try {
			dc.addJyls(kplshList, djh, "13", yhid);
			Map params = new HashMap<>();
			params.put("id", sqid);
			params.put("ztbz", "4");
			ckService.updateZtbz(params);
			dc.saveLog(djh, "01", "0", "电子发票服务平台换开操作", "已向服务端发送换开开请求", yhid, lsList.get(0).getXfsh(), "");
			result.put("success", true);
			result.put("msg", "换开请求提交成功，请注意查看操作结果！");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", "后台出现错误: " + e.getMessage());
			dc.saveLog(djh, "92", "1", "", "电子发票服务平台换开请求失败!", 2, lsList.get(0).getXfsh(), "");
		}
		return result;
	}

	@RequestMapping(value = "/update")
	@ResponseBody
	public Map<String, Object> update(int id, String reason) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		result.put("msg", "撤销成功！");
		Map params = new HashMap<>();
		params.put("id", id);
		params.put("ztbz", "5");
		params.put("reason", reason);
		try {
			ckService.updateZtbz(params);
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "撤销失败，请检查！");
		}

		return result;
	}
}
