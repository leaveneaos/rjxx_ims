package com.rjxx.taxeasy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.domains.FpkcYzsz;
import com.rjxx.taxeasy.domains.Fpyjdy;
import com.rjxx.taxeasy.domains.Fpzl;
import com.rjxx.taxeasy.domains.Skp;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.service.FpkcService;
import com.rjxx.taxeasy.service.FpkcYzszService;
import com.rjxx.taxeasy.service.FpyjdyService;
import com.rjxx.taxeasy.service.FpzlService;
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
	@Autowired
	private FpzlService fpzlService;
	@Autowired
	private FpkcYzszService yzszService;

	@RequestMapping
	public String index() throws Exception {
		List<Xf> xfList = getXfList();
		request.setAttribute("xfList", xfList);
		List<Skp> skpList = getSkpList();
		request.setAttribute("skpList", skpList);
		List<Fpzl> fpzlList = fpzlService.findAllByParams(new HashMap<>());
		request.setAttribute("fplxList",fpzlList);
		return "fpyjdy/index";
	}

	@RequestMapping(value = "/getItems")
	@ResponseBody
	public Map<String, Object> getItems(int length, int start, int draw, Integer xfid,Integer skpider,String fpzl,String xfsh) throws Exception {
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
		pagination.addParam("skpider", skpider);
		pagination.addParam("xfsh", xfsh);
		pagination.addParam("fpzl", fpzl);
		List<Fpyjdyvo> dyList = dyService.findFpyjdyByPage(pagination);
		for (Fpyjdyvo item : dyList) {
			Map params = new HashMap<>();
			params.put("gsdm", gsdm);
			params.put("xfid", item.getXfid());
			params.put("skpid", item.getSkpid());
			params.put("fpzldm",item.getFpzldm());
			params.put("yhid", yhid);
			Fpyjdyvo dybz = dyService.findDyxx(params);
			if(dybz!=null){
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
	public Map<String, Object> update(Integer xfid, Integer skpid,String fpzldm,Integer yjkcl) {
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
		params.put("fpzldm", fpzldm);
		try {
			FpkcYzsz item = yzszService.findOneByParams(params);
			if (item == null) {
				FpkcYzsz yjdy = new FpkcYzsz();
				yjdy.setYhid(yhid);
				yjdy.setGsdm(gsdm);
				yjdy.setXfid(xfid);
				yjdy.setSkpid(skpid);
				yjdy.setFpzldm(fpzldm);
				yjdy.setYjyz(yjkcl);
				yjdy.setYxbz("1");
				yzszService.save(yjdy);
			} else {
				item.setYjyz(yjkcl);
				yzszService.save(item);
			}
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "保存失败！");
		}

		return result;
	}

	@RequestMapping(value = "/plupdate")
	@ResponseBody
	public Map<String, Object> plupdate(String skpids,String fpzldms,Integer yjkcl) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		result.put("msg", "保存成功！");
		int yhid = getYhid();
		String gsdm = getGsdm();
		Map params = new HashMap<>();
		String[] skpid = skpids.substring(0, skpids.length() - 1).split(",");
		for (int i = 0; i < skpid.length; i++) {
			String[] sf = skpid[i].split("-");
			params.put("yhid", yhid);
			params.put("gsdm", gsdm);
			params.put("xfid", sf[0]);
			params.put("skpid", sf[1]);
			params.put("fpzldm", sf[2]);
			try {
				FpkcYzsz item = yzszService.findOneByParams(params);
				if (item == null) {
					FpkcYzsz yjdy = new FpkcYzsz();
					yjdy.setYhid(yhid);
					yjdy.setGsdm(gsdm);
					yjdy.setXfid(Integer.parseInt(sf[0]));
					yjdy.setSkpid(Integer.parseInt(sf[1]));
					yjdy.setFpzldm(sf[2]);
					yjdy.setYjyz(yjkcl);
					yjdy.setYxbz("1");
					yzszService.save(yjdy);
				} else {
					item.setYjyz(yjkcl);
					yzszService.save(item);
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
