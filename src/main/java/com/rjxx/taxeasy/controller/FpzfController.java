package com.rjxx.taxeasy.controller;

import java.text.DecimalFormat;
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
import com.rjxx.taxeasy.bizcomm.utils.FpclService;
import com.rjxx.taxeasy.domains.Skp;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.service.GsxxService;
import com.rjxx.taxeasy.service.JylsService;
import com.rjxx.taxeasy.service.JyspmxService;
import com.rjxx.taxeasy.service.KplsService;
import com.rjxx.taxeasy.service.KpspmxService;
import com.rjxx.taxeasy.vo.Fpcxvo;
import com.rjxx.taxeasy.vo.Kpspmxvo;
import com.rjxx.taxeasy.web.BaseController;

@Controller
@RequestMapping("/fpzf")
public class FpzfController extends BaseController{


	@Autowired
	private KplsService kplsService;
	@Autowired
	private KpspmxService mxService;
	@Autowired
	private JylsService jylsService;
	@Autowired
	private JyspmxService jymxService;
	@Autowired
	private FpclService FpclService;
	@Autowired
	private DataOperte dc;

	@Autowired
	private GsxxService gsxxservice;
	@RequestMapping
	public String index() {
		request.setAttribute("xfList", getXfList());
		request.setAttribute("skpList", getSkpList());
		return "fpzf/index";
	}

	@RequestMapping(value = "/getKplsList")
	@ResponseBody
	public Map<String, Object> getItems(int length, int start, int draw, String kprqq, String kprqz,
			 String gfmc,Integer xfi) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Pagination pagination = new Pagination();
		pagination.setPageNo(start / length + 1);
		pagination.setPageSize(length);
		String gsdm = getGsdm();
		String xfStr = "";
		List<Xf> xfs = getXfList();
		if (xfs != null) {
			for (int i = 0; i < xfs.size(); i++) {
				int xfid = xfs.get(i).getId();
				if (i == xfs.size() - 1) {
					xfStr += xfid + "";
				} else {
					xfStr += xfid + ",";
				}
			}
		}
		String[] xfid = xfStr.split(",");
		if (xfid.length == 0) {
			xfid = null;
		}
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
		pagination.addParam("gsdm", gsdm);
		pagination.addParam("xfid", xfid);
		pagination.addParam("xfi", xfi);
		pagination.addParam("skpid", skpid);
		pagination.addParam("kprqq", kprqq);
		pagination.addParam("kprqz", kprqz);
		pagination.addParam("gfmc", gfmc);
		List<Fpcxvo> khcfpList = kplsService.findKzffpByPage(pagination);
		int total = pagination.getTotalRecord();
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		result.put("draw", draw);
		result.put("data", khcfpList);
		return result;
	}
	@RequestMapping(value = "/getKplsList1")
	@ResponseBody
	public Map<String, Object> getItems1(int length, int start, int draw, String kprqq, String kprqz,  String gfmc, Integer xfi) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Pagination pagination = new Pagination();
		pagination.setPageNo(start / length + 1);
		pagination.setPageSize(length);
		String gsdm = getGsdm();
		String xfStr = "";
		List<Xf> xfs = getXfList();
		if (xfs != null) {
			for (int i = 0; i < xfs.size(); i++) {
				int xfid = xfs.get(i).getId();
				if (i == xfs.size() - 1) {
					xfStr += xfid + "";
				} else {
					xfStr += xfid + ",";
				}
			}
		}
		String[] xfid = xfStr.split(",");
		if (xfid.length == 0) {
			xfid = null;
		}
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
		pagination.addParam("gsdm", gsdm);
		pagination.addParam("xfid", xfid);
		pagination.addParam("xfi", xfi);
		pagination.addParam("skpid", skpid);
		pagination.addParam("kprqq", kprqq);
		pagination.addParam("kprqz", kprqz);
		pagination.addParam("gfmc", gfmc);
		List<Fpcxvo> khcfpList = kplsService.findKzffpByPage1(pagination);
		int total = pagination.getTotalRecord();
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		result.put("draw", draw);
		result.put("data", khcfpList);
		return result;
	}

	@RequestMapping(value = "/getMx")
	@ResponseBody
	public Map<String, Object> getMx(int kplsh) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map params = new HashMap<>();
		params.put("id", kplsh);
		List<Kpspmxvo> kpspmxList = mxService.findAllByParams(params);
		// List<Kpspmxvo> mxList = new ArrayList<Kpspmxvo>();
		if (kpspmxList != null) {
			for (Kpspmxvo mx : kpspmxList) {
				if (mx.getKhcje() == null) {
					mx.setKhcje(mx.getJshj());
				}
				if (mx.getYhcje() == null) {
					mx.setYhcje(0.00);
				}
			}
		}
		result.put("data", kpspmxList);
		return result;
	}
	@Transactional
	@RequestMapping(value = "/zf")
	@ResponseBody
	public Map<String, Object> update(String hcjeStr, String xhStr, Integer kplsh) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		//try {
			boolean flag = FpclService.zfcl(kplsh, getYhid(), getGsdm());
			if (flag) {
				result.put("success", true);
				result.put("msg", "作废请求提交成功，请注意查看操作结果！");
			}else{
				result.put("success", true);
				result.put("msg", "作废请求失败!");
			}
		
/*		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", "后台出现错误: " + e.getMessage());
		}*/
		return result;
	}
}
