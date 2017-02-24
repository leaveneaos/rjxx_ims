package com.rjxx.taxeasy.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.domains.Csb;
import com.rjxx.taxeasy.domains.Cszb;
import com.rjxx.taxeasy.domains.Skp;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.service.CsbService;
import com.rjxx.taxeasy.service.CszbService;
import com.rjxx.taxeasy.service.SkpService;
import com.rjxx.taxeasy.service.XfService;
import com.rjxx.taxeasy.vo.CsbVo;
import com.rjxx.taxeasy.web.BaseController;
import com.sun.pdfview.Flag;

@Controller
@RequestMapping("/csb")
public class CsbController extends BaseController {
	@Autowired
	private CszbService cszbService;
	@Autowired
	private CsbService csbService;
	@Autowired
	private XfService xfservice;
	@Autowired
	private SkpService skpService;

	@RequestMapping
	public String index() {
		request.setAttribute("csbs", getCsb());
		Xf xf = new Xf();
		xf.setGsdm(getGsdm());
		List<Xf> list = xfservice.findAllByParams(xf);
		request.setAttribute("xfs", list);
		return "csb/index";
	}

	@RequestMapping(value = "/getCsbList")
	@ResponseBody
	public Map getXfxx(String csid, String csjb, int length, int start, int draw) {
		Pagination pagination = new Pagination();
		pagination.setPageNo(start / length + 1);
		pagination.setPageSize(length);
		pagination.addParam("csid", csid);
		pagination.addParam("csjb", csjb);
		pagination.addParam("gsdm", this.getGsdm());
		pagination.addParam("orderBy", "a.lrsj");
		List<CsbVo> list = cszbService.findByPage(pagination);
		int total = pagination.getTotalRecord();
		for (CsbVo cszb : list) {
			Csb csb = csbService.findOne(cszb.getCsid());
			cszb.setCsm(csb.getCsm());
			cszb.setCsmc(csb.getCsmc());
			if (null != cszb.getXfid()) {
				Xf xf = xfservice.findOne(cszb.getXfid());
				cszb.setXfmc(xf.getXfmc());
				cszb.setXfsh(xf.getXfsh());
			}
			if (null != cszb.getKpdid()) {
				Skp skp = skpService.findOne(cszb.getKpdid());
				cszb.setKpdmc(skp.getKpdmc());
				cszb.setSkph(skp.getSkph());
			}

		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		result.put("draw", draw);
		result.put("data", list);
		return result;
	}

	public List<Csb> getCsb() {
		Map params = new HashMap<>();
		params.put("gsdm", getGsdm());
		List<Csb> list = csbService.findAllByParams(params);
		return list;
	}

	@RequestMapping(value = "/getXzCsb")
	@ResponseBody
	public Map getCsb(String csid, String csjb) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map params = new HashMap<>();
		params.put("gsdm", getGsdm());
		params.put("csid", csid);
		params.put("csjb", csjb);
		List<Csb> list = csbService.findAllByParams(params);
		result.put("list", list);
		return result;
	}

	@RequestMapping(value = "/hqcszlx")
	@ResponseBody
	public Map hqcszlx(Integer csid) {
		Map<String, Object> result = new HashMap<String, Object>();
		Csb csb = csbService.findOne(csid);
		result.put("csb", csb);
		return result;
	}

	@RequestMapping(value = "/xzskp")
	@ResponseBody
	public Map xzskp(Integer xfid) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Skp> list = new ArrayList<>();
		List<Skp> list1 = getSkpList();
		for (Skp skp : list1) {
			if (skp.getXfid().equals(xfid)) {
				list.add(skp);
			}
		}
		result.put("skp", list);
		return result;
	}

	@RequestMapping(value = "/csxz")
	@ResponseBody
	public Map csxz(Integer xzxzcs, String xzgsdm, Integer xzxzxf, Integer xzxzkpd, String xzxzcsz, String xzxzcsz1) {
		Map<String, Object> result = new HashMap<String, Object>();
		Cszb cszb = new Cszb();
		cszb.setLrsj(new Date());
		cszb.setXgsj(new Date());
		cszb.setLrry(getYhid());
		cszb.setXgry(getYhid());
		cszb.setYxbz("1");
		cszb.setGsdm(getGsdm());
		if ("".equals(xzxzxf)) {
			cszb.setXfid(null);
		} else {
			cszb.setXfid(xzxzxf);
		}
		if ("".equals(xzxzkpd)) {
			cszb.setKpdid(null);
		} else {
			cszb.setKpdid(xzxzkpd);
		}
		cszb.setCsid(xzxzcs);
		if (null != xzxzcsz && !"".equals(xzxzcsz)) {
			cszb.setCsz(xzxzcsz);
		} else if (null != xzxzcsz1 && !"".equals(xzxzcsz1)) {
			cszb.setCsz(xzxzcsz1);
		}
		Map params = new HashMap<>();
		params.put("csid", xzxzcs);
		params.put("gsdm", xzgsdm);
		params.put("xfid", xzxzxf);
		params.put("kpdid", xzxzkpd);
		List<Cszb> list = cszbService.findAllByParams(params);
		boolean flag = false;
		for (Cszb cszb2 : list) {
			if (((null == cszb.getXfid() && null == cszb2.getXfid())
					|| (((null != cszb.getXfid() && null != cszb2.getXfid()))
							&& (cszb.getXfid().equals(cszb2.getXfid()))))
					&& ((null == cszb.getKpdid() && null == cszb2.getKpdid())
							|| ((null != cszb.getKpdid() && null != cszb2.getKpdid())
									&& (cszb.getKpdid().equals(cszb2.getKpdid()))))
					&& (cszb.getCsid().equals(cszb2.getCsid()))) {
				flag = true;
				break;
			}
		}
		if (flag) {
			result.put("msg", "相同级别参数已存在,请选择修改!");
			result.put("success", false);
			return result;
		}
		cszbService.save(cszb);
		result.put("msg", "新增成功!");
		result.put("success", true);
		return result;
	}

	@RequestMapping(value = "/xgCsb")
	@ResponseBody
	public Map xgcsb(Integer cszid, String xgxzcsz, String xgxzcsz1, Integer xgxzxf, Integer xgxzkpd) {
		Map<String, Object> result = new HashMap<String, Object>();
		Cszb cszb = cszbService.findOne(cszid);
		cszb.setXgsj(new Date());
		cszb.setXgry(getYhid());
		cszb.setGsdm(getGsdm());
		if ("".equals(xgxzxf)) {
			cszb.setXfid(null);
		} else {
			cszb.setXfid(xgxzxf);
		}
		if ("".equals(xgxzkpd)) {
			cszb.setKpdid(null);
		} else {
			cszb.setKpdid(xgxzkpd);
		}
		if (null != xgxzcsz && !"".equals(xgxzcsz)) {
			cszb.setCsz(xgxzcsz);
		} else if (null != xgxzcsz1 && !"".equals(xgxzcsz1)) {
			cszb.setCsz(xgxzcsz1);
		}
		Map params = new HashMap<>();
		params.put("csid", cszb.getCsid());
		params.put("gsdm", getGsdm());
		params.put("xfid", xgxzxf);
		params.put("kpdid", xgxzkpd);
		List<Cszb> list = cszbService.findAllByParams(params);
		for (Cszb cszb3 : list) {
			if (cszb3.getId().equals(cszb.getId())) {
				list.remove(cszb3);
				break;
			}
		}
		boolean flag = false;
		for (Cszb cszb2 : list) {
			if (((null == cszb.getXfid() && null == cszb2.getXfid())
					|| (((null != cszb.getXfid() && null != cszb2.getXfid()))
							&& (cszb.getXfid().equals(cszb2.getXfid()))))
					&& ((null == cszb.getKpdid() && null == cszb2.getKpdid())
							|| ((null != cszb.getKpdid() && null != cszb2.getKpdid())
									&& (cszb.getKpdid().equals(cszb2.getKpdid()))))
					&& (cszb.getCsid().equals(cszb2.getCsid()))) {
				flag = true;
				break;
			}
		}
		if (flag) {
			result.put("msg", "相同级别参数已存在,请选择修改!");
			result.put("success", false);
			return result;
		}
		cszbService.save(cszb);
		result.put("msg", "修改成功!");
		result.put("success", true);
		return result;
	}
	@RequestMapping(value = "/scCsb")
	@ResponseBody
	public Map<String, Object> scCsb(Integer csid){
		Map<String, Object> result = new HashMap<String, Object>();
		Cszb cszb = cszbService.findOne(csid);
		cszb.setYxbz("0");
		cszbService.save(cszb);
		result.put("msg", true);
		return result;
	}
}
