package com.rjxx.taxeasy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.domains.Fpyjdy;
import com.rjxx.taxeasy.domains.Skp;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.service.FpkcService;
import com.rjxx.taxeasy.service.FpyjdyService;
import com.rjxx.taxeasy.vo.Fpkcvo;
import com.rjxx.taxeasy.vo.Fpyjdyvo;
import com.rjxx.taxeasy.web.BaseController;

@Controller
@RequestMapping("/fpyjdy")
public class YjdyController extends BaseController {

	@Autowired
	private FpyjdyService dyService;
	@Autowired
	private FpkcService kcService;

	@RequestMapping
	public String index() throws Exception {
		List<Xf> xfList = getXfList();
		request.setAttribute("xfList", xfList);
		return "fpyjdy/index";
	}

	@RequestMapping(value = "/getItems")
	@ResponseBody
	public Map<String, Object> getItems(int length, int start, int draw, Integer xfid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		int yhid = getYhid();
		Pagination pagination = new Pagination();
		pagination.setPageNo(start / length + 1);
		pagination.setPageSize(length);
		String gsdm = getGsdm();
		pagination.addParam("gsdm", gsdm);
		pagination.addParam("xfid", xfid);
		String skpStr = "";
		List<Skp> skpList = getSkpList();
		if (skpList != null) {
			for (int j = 0; j < skpList.size(); j++) {
				int skpid = skpList.get(j).getId();
				if (j == skpList.size() - 1) {
					skpStr += skpid + "";
				} else {
					skpStr += skpid + ",";
				}
			}
		}
		String[] skpid = skpStr.split(",");
		if (skpid.length == 0) {
			skpid = null;
		}
		pagination.addParam("skpid", skpid);
		List<Fpyjdyvo> dyList = dyService.findFpyjdyByPage(pagination);
		for (Fpyjdyvo item : dyList) {
			Map params = new HashMap<>();
			params.put("gsdm", gsdm);
			params.put("xfid", item.getXfid());
			params.put("skpid", item.getSkpid());
			params.put("yhid", yhid);
			Fpyjdyvo dybz = dyService.findDyxx(params);
			if (dybz == null) {
				item.setSfsy("未订阅");
				item.setSfemail("未订阅");
				item.setYjkcl("");
			} else {
				if (dybz.getSfsy() != null && "1".equals(dybz.getSfsy())) {
					item.setSfsy("已订阅");
				} else {
					item.setSfsy("未订阅");
				}
				if (dybz.getSfemail() != null && "1".equals(dybz.getSfemail())) {
					item.setSfemail("已订阅");
				} else {
					item.setSfemail("未订阅");
				}
				item.setYjkcl(dybz.getYjkcl());
			}
		}
		int total = pagination.getTotalRecord();
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		result.put("draw", draw);
		result.put("data", dyList);
		return result;
	}

	@RequestMapping(value = "/update")
	@ResponseBody
	public Map<String, Object> update(Integer xfid, Integer skpid, String sfsy, String sfemail, Integer yjkcl) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		result.put("msg", "保存成功！");
		int yhid = getYhid();
		String gsdm = getGsdm();
		Map params = new HashMap<>();
		params.put("yhid", yhid);
		params.put("gsdm", gsdm);
		params.put("xfid", xfid);
		params.put("skpid", skpid);
		try {
			Fpyjdy item = dyService.findOneByParams(params);
			if (item == null) {
				Fpyjdy yjdy = new Fpyjdy();
				yjdy.setYhid(yhid);
				yjdy.setGsdm(gsdm);
				yjdy.setXfid(xfid);
				yjdy.setSkpid(skpid);
				yjdy.setSfsy(sfsy);
				yjdy.setSfemail(sfemail);
				yjdy.setYjkcl(yjkcl);
				yjdy.setYxbz("1");
				dyService.save(yjdy);
			} else {
				Integer id = item.getId();
				params.put("id", id);
				params.put("sfsy", sfsy);
				params.put("sfemail", sfemail);
				params.put("yjkcl", yjkcl);
				dyService.updateDyxx(params);
			}
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "保存失败！");
		}

		return result;
	}

	@RequestMapping(value = "/plupdate")
	@ResponseBody
	public Map<String, Object> plupdate(Integer xfid, String skpids, String sfsy, String sfemail, Integer yjkcl) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		result.put("msg", "保存成功！");
		int yhid = getYhid();
		String gsdm = getGsdm();
		Map params = new HashMap<>();
		String[] skpid = skpids.substring(0, skpids.length() - 1).split(",");
		for (int i = 0; i < skpid.length; i++) {
			params.put("yhid", yhid);
			params.put("gsdm", gsdm);
			params.put("xfid", xfid);
			params.put("skpid", skpid[i]);
			try {
				Fpyjdy item = dyService.findOneByParams(params);
				if (item == null) {
					Fpyjdy yjdy = new Fpyjdy();
					yjdy.setYhid(yhid);
					yjdy.setGsdm(gsdm);
					yjdy.setXfid(xfid);
					yjdy.setSkpid(Integer.parseInt(skpid[i]));
					yjdy.setSfsy(sfsy);
					yjdy.setSfemail(sfemail);
					yjdy.setYjkcl(yjkcl);
					yjdy.setYxbz("1");
					dyService.save(yjdy);
				} else {
					Integer id = item.getId();
					params.put("id", id);
					params.put("sfsy", sfsy);
					params.put("sfemail", sfemail);
					params.put("yjkcl", yjkcl);
					dyService.updateDyxx(params);
				}
			} catch (Exception e) {
				result.put("success", false);
				result.put("msg", "保存失败！");
				break;
			}
		}
		return result;
	}

	// 主页订阅查询
	@RequestMapping(value = "/zydy")
	@ResponseBody
	public Map<String, Object> zydy(int length, int start, int draw) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Pagination pagination = new Pagination();
		pagination.setPageNo(start / length + 1);
		pagination.setPageSize(length);
		int yhid = getYhid();
		pagination.addParam("yhid", yhid);
		List<Fpyjdyvo> zyList = dyService.findYhZyDy(pagination);
		for (Fpyjdyvo item : zyList) {
			Map params = new HashMap<>();
			params.put("gsdm", item.getGsdm());
			params.put("xfid", item.getXfid());
			params.put("skpid", item.getSkpid());
			Fpkcvo kc = kcService.findZyKyl(params);
			if (kc != null) {
				item.setKyl(kc.getKyl());
			}
		}
		int total = pagination.getTotalRecord();
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		result.put("draw", draw);
		result.put("data", zyList);
		return result;
	}
}
