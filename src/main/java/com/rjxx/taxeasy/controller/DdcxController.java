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
import com.rjxx.taxeasy.domains.Jymxsq;
import com.rjxx.taxeasy.domains.Jyspmx;
import com.rjxx.taxeasy.domains.Kpls;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.service.JylsvoService;
import com.rjxx.taxeasy.service.JymxsqService;
import com.rjxx.taxeasy.service.JyspmxService;
import com.rjxx.taxeasy.service.JyxxsqService;
import com.rjxx.taxeasy.service.KplsService;
import com.rjxx.taxeasy.vo.Jylsvo;
import com.rjxx.taxeasy.vo.JyxxsqVO;
import com.rjxx.taxeasy.web.BaseController;
import com.rjxx.time.TimeUtil;

@Controller
@RequestMapping("/ddcx")
public class DdcxController extends BaseController {

	@Autowired
	private JyxxsqService jyxxsqservice;

	@Autowired
	private JymxsqService jymxsqservice;

	@Autowired
	private KplsService kplsService;

	@RequestMapping
	public String index() {
		List<Xf> list = getXfList();
		request.setAttribute("xfSum", list.size());
		request.setAttribute("xfList", list);
		return "ddcx/index";
	}

	@RequestMapping(value = "/getJylsDd")
	@ResponseBody
	public Map getItems(int length, int start, int draw, String xfsh, String gfmc, String ddh, String rqq, String rqz,
			String jylsh,boolean loaddata) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(loaddata){
			Map params = new HashMap();
			/*Pagination pagination = new Pagination();
			pagination.setPageNo(start / length + 1);
			pagination.setPageSize(length);*/
			List<Integer> xfs = new ArrayList<>();
			if (!getXfList().isEmpty()) {
				for (Xf xf : getXfList()) {
					xfs.add(xf.getId());
				}
			}
			if (xfs.size() > 0) {
				params.put("xfList", xfs);
			}
			params.put("xfsh", xfsh);
			params.put("gfmc", gfmc);
			params.put("ddh", ddh);
			params.put("jylsh", jylsh);
			if (rqq != null && !rqq.trim().equals("") && rqz != null && !rqz.trim().equals("")) { // 名称参数非空时增加名称查询条件
				params.put("rqq", rqq);
				params.put("rqz", TimeUtil.getAfterDays(rqz, 1));
			} else if (rqq != null && !rqq.trim().equals("") && (rqz == null || rqz.trim().equals(""))) {
				params.put("rqq", rqq);
				params.put("rqz", TimeUtil.getAfterDays(rqq, 1));
			} else if ((rqq == null || rqq.trim().equals("")) && rqz != null && !rqz.trim().equals("")) {
				params.put("rqq", rqz);
				params.put("rqz", TimeUtil.getAfterDays(rqz, 1));
			}

			//params.addParam("orderBy", "ddrq desc");
			params.put("gsdm", this.getGsdm());
			params.put("start",start);
			params.put("length",length);
			List<JyxxsqVO> list = jyxxsqservice.findBykplscxPage(params);
			//int total = pagination.getTotalRecord();
			int total;
			if(0 == start){
				total = jyxxsqservice.findBykplscxtotal(params);
				request.getSession().setAttribute("total",total);
			}else{
				total =  (Integer)request.getSession().getAttribute("total");
				//request.getSession().getAttribute("total");
			}
			result.put("recordsTotal",total);
			result.put("recordsFiltered",total);
			result.put("draw",draw);
			result.put("data",list);
		}else {
			result.put("recordsTotal",0);
			result.put("recordsFiltered",0);
			result.put("draw",draw);
			result.put("data",new ArrayList<>());
		}
		return result;
	}

	@RequestMapping(value = "/getMx")
	@ResponseBody
	public Map getMx(int length, int start, int draw, String sqlsh) {
		Map<String, Object> result = new HashMap<String, Object>();
		Pagination pagination = new Pagination();
		pagination.setPageNo(start / length + 1);
		pagination.setPageSize(length);
		pagination.addParam("sqlsh", sqlsh);
		pagination.addParam("gsdm", this.getGsdm());
		List<Jymxsq> list = jymxsqservice.findByPage(pagination);
		int total = pagination.getTotalRecord();
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		result.put("draw", draw);
		result.put("data", list);
		return result;
	}

	@RequestMapping(value = "/getFp")
	@ResponseBody
	public Map getFp(Integer sqlsh) {
		Map<String, Object> result = new HashMap<String, Object>();
		Kpls kp = new Kpls();
		kp.setDjh(sqlsh);
		kp.setGsdm(getGsdm());
		List<Kpls> list = kplsService.findAllByKpls(kp);
		if (!list.isEmpty()) {
			String fpdm = list.get(0).getFpdm();
			String fphm = "";
			double hjje = 0;
			double hjse = 0;
			for (Kpls kpls : list) {
				fphm += kpls.getFphm() + ",";
				hjje += kpls.getHjje();
				hjse += kpls.getHjse();
			}
			result.put("fpdm", fpdm);
			result.put("fphm", fphm.subSequence(0, fphm.length() - 1));
			result.put("hjje", hjje);
			result.put("hjse", hjse);
			result.put("success", true);
		} else {
			result.put("none", true);
		}
		return result;
	}
}
