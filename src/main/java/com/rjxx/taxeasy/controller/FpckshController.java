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
 * 发票重开审核业务
 * 
 */
@Controller
@RequestMapping("/fpcksh")
public class FpckshController extends BaseController {
	@Autowired
	private KplsService kplsService;
	@Autowired
	private DataOperte dc;
	@Autowired
	private CkhkService ckService;
	@Autowired
	private JylsvoService lsvoService;

	@RequestMapping
	public String index() throws Exception {
		return "fpcksh/index";
	}

	@RequestMapping(value = "/getJylsList")
	@ResponseBody
	public Map<String, Object> getItems(int length, int start, int draw, String ztbz, String ddh, String gfmc)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
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
		pagination.addParam("gsdm", gsdm);
		pagination.addParam("xfs", xfs);
		pagination.addParam("skps", skps);
		pagination.addParam("ddh", ddh);
		pagination.addParam("gfmc", gfmc);
		pagination.addParam("ztbz", ztbz);
		List<Jylsvo> ckList = lsvoService.findChsqByPage(pagination);
		int total = pagination.getTotalRecord();
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		result.put("draw", draw);
		result.put("data", ckList);
		return result;
	}

	@RequestMapping(value = "/getKpMx")
	@ResponseBody
	public Map<String, Object> getKpMx(Integer djh) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map params = new HashMap<>();
		params.put("djh", djh);
		List<Fpcxvo> kplsList = kplsService.findFpcksqKpls(params);
		result.put("data", kplsList);
		return result;
	}

	@RequestMapping(value = "/qrck")
	@ResponseBody
	public Map<String, Object> qrck(int djh) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map map = new HashMap<>();
		map.put("djh", djh);
		int yhid = getYhid();
		List<Fpcxvo> lsList = kplsService.findFpcksqKpls(map);
		List<Integer> kplshList = new ArrayList<>();
		if (lsList != null && lsList.size() > 0) {
			for (Fpcxvo item : lsList) {
				if (!"00".equals(item.getFpztdm())) {
					result.put("success", false);
					result.put("msg", "存在非正常状态的发票，不能重开！");
					return result;
				} else {
					kplshList.add(item.getKplsh());
				}
			}
			if (lsList.get(0).getXcyf() != null && lsList.get(0).getXcyf() > 6) {
				result.put("success", false);
				result.put("msg", "超过开票日期6个月，不能重开！");
				return result;
			}
		}
		try {
			dc.updateKpls(kplshList, djh, yhid);
			dc.saveLog(djh, "01", "0", "电子发票服务平台重开操作", "已向服务端发送重开请求", yhid, lsList.get(0).getXfsh(), "");
			result.put("success", true);
			result.put("msg", "重开请求提交成功，请注意查看操作结果！");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", "后台出现错误: " + e.getMessage());
			dc.saveLog(djh, "92", "1", "", "电子发票服务平台重开请求失败!", 2, lsList.get(0).getXfsh(), "");
		}
		return result;
	}

	/**
	 * 重开审核撤销操作
	 * 
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public Map<String, Object> update(int id, String reason) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map params = new HashMap<>();
		result.put("success", true);
		result.put("msg", "撤销成功！");
		params.put("id", id);
		params.put("ztbz", "2");
		params.put("reason", reason);
		ckService.updateZtbz(params);
		return result;
	}

}
