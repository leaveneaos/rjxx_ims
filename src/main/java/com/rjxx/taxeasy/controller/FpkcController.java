package com.rjxx.taxeasy.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.domains.Fpkc;
import com.rjxx.taxeasy.domains.Fpzl;
import com.rjxx.taxeasy.domains.Skp;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.filter.SystemControllerLog;
import com.rjxx.taxeasy.service.FpkcService;
import com.rjxx.taxeasy.service.FpzlService;
import com.rjxx.taxeasy.vo.Fpkcvo;
import com.rjxx.taxeasy.web.BaseController;

@Controller
@RequestMapping("/fpkc")
public class FpkcController extends BaseController {
	@Autowired
	private FpkcService fpkcService;
	@Autowired
	private FpzlService fpzlService;

	@RequestMapping
	public String index() throws Exception {
		List<Xf> xfList = getXfList();
		request.setAttribute("xfList", xfList);
		List<Skp> skpList = getSkpList();
		request.setAttribute("skpList", skpList);
		List<Fpzl> fpzlList = fpzlService.findAllByParams(new HashMap<>());
		request.setAttribute("fplxList",fpzlList);
		return "fpkc/index";
	}

	@RequestMapping(value = "/getKpd")
	@ResponseBody
	public List<Fpkcvo> getKpd(int xfid) throws Exception {
		Map params = new HashMap<>();
		String gsdm = getGsdm();
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
		params.put("xfid", xfid);
		params.put("gsdm", gsdm);
		params.put("skpid", skpid);
		return fpkcService.findKpd(params);
	}

	// 查询方法
	@RequestMapping(value = "/getItems")
	@ResponseBody
	public Map<String, Object> getItems(int length, int start, int draw,Integer xfids,Integer skpids, String fpdm,String fplx) throws Exception {
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
		pagination.addParam("skpid", skpid);
		pagination.addParam("xfids", xfids);
		pagination.addParam("skpids", skpids);
		pagination.addParam("fpdm", fpdm);
		pagination.addParam("fplx", fplx);
		List<Fpkcvo> kcList = fpkcService.findByPage(pagination);
		List<Fpkcvo> kcList1 = new ArrayList<Fpkcvo>();
		if (kcList != null) {
			for (Fpkcvo item : kcList) {
				Map param = new HashMap<>();
				param.put("fpdm", item.getFpdm());
				param.put("fphms", item.getFphms());
				param.put("fphmz", item.getFphmz());
				Fpkcvo fpkcItem = fpkcService.findKyl(param);
				item.setKyl(item.getFpkcl() - fpkcItem.getKpl());
				if (item.getFpkcl() - fpkcItem.getKpl() > 0) {
					kcList1.add(item);
				}
			}
		}
		int total = pagination.getTotalRecord();
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		result.put("draw", draw);
		result.put("data", kcList1);
		return result;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description="发票库存保存",key="fpkcModel")
	public Map<String, Object> save(Fpkc item) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		int yhid = getYhid();
		int fpkcl = 0;
		Map params = new HashMap<>();
		params.put("skpid", item.getSkpid());
		params.put("fpdm", item.getFpdm());
		List<Fpkc> kcList = fpkcService.findFphmd(params);
		if (kcList != null && kcList.size() > 0) {
			for (Fpkc fpkc : kcList) {
				if (fpkc.getFphms() != null && fpkc.getFphmz() != null) {
					int yfphms = Integer.parseInt(fpkc.getFphms());
					int yfphmz = Integer.parseInt(fpkc.getFphmz());
					int xfphms = Integer.parseInt(item.getFphms());
					int xfphmz = Integer.parseInt(item.getFphmz());
					if ((yfphms <= xfphms && xfphms <= yfphmz) || (yfphms <= xfphmz && xfphmz <= yfphmz)) {
						result.put("success", false);
						result.put("msg", "发票号码与库中的发票号码存在重叠！");
						return result;
					}
				}
			}
		}
		fpkcl = Integer.parseInt(item.getFphmz()) - Integer.parseInt(item.getFphms()) + 1;
		String gsdm = getGsdm();
		item.setGsdm(gsdm);
		item.setLrry(yhid);
		item.setYxbz("1");
		item.setLrsj(new Date());
		item.setFpkcl(fpkcl);
		result.put("success", true);
		result.put("msg", "保存成功！");
		try {
			fpkcService.save(item);
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "保存失败，" + e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description="发票库存修改",key="id")
	public Map<String, Object> update(int id, Integer xfid, Integer skpid, String fpdm, 
			String fphms, String fphmz,String fpzldm) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		int yhid = getYhid();
		int fpkcl = 0;
		Map params = new HashMap<>();
		params.put("id", id);
		params.put("skpid", skpid);
		params.put("fpdm", fpdm);
		params.put("fpzldm", fpzldm);
		List<Fpkc> kcList = fpkcService.findFphmdxg(params);
		if (kcList != null && kcList.size() > 0) {
			for (Fpkc fpkc : kcList) {
				if (fpkc.getFphms() != null && fpkc.getFphmz() != null) {
					int yfphms = Integer.parseInt(fpkc.getFphms());
					int yfphmz = Integer.parseInt(fpkc.getFphmz());
					int xfphms = Integer.parseInt(fphms);
					int xfphmz = Integer.parseInt(fphmz);
					if ((yfphms <= xfphms && xfphms <= yfphmz) || (yfphms <= xfphmz && xfphmz <= yfphmz)) {
						result.put("success", false);
						result.put("msg", "发票号码与库中的发票号码存在重叠！");
						return result;
					}
				}
			}
		}
		fpkcl = Integer.parseInt(fphmz) - Integer.parseInt(fphms) + 1;
		Map param = new HashMap<>();
		param.put("id", id);
		param.put("xfid", xfid);
		param.put("skpid", skpid);
		param.put("fpdm", fpdm);
		param.put("fphms", fphms);
		param.put("fphmz", fphmz);
		param.put("fpkcl", fpkcl);
		param.put("fpzldm", fpzldm);
		result.put("success", true);
		result.put("msg", "修改成功！");
		try {
			fpkcService.update(param);
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "修改失败，" + e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/destory")
	@ResponseBody
	@SystemControllerLog(description="发票库存删除",key="id")
	public Map<String, Object> destory(int id) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		result.put("msg", "删除成功！");
		Map param = new HashMap<>();
		param.put("id", id);
		try {
			fpkcService.destory(param);
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "删除失败，" + e.getMessage());
		}
		return result;
	}
	
	// 发票监控查询方法
		@RequestMapping(value = "/getItems2")
		@ResponseBody
		public Map<String, Object> getItems2(int length,int start,int draw,Integer xfid,Integer skpid,String fplx,
				String xfsh,Integer fpsl) throws Exception {
			Map<String, Object> result = new HashMap<String, Object>();
			Pagination pagination = new Pagination();
			pagination.setPageNo(start / length + 1);
			pagination.setPageSize(length);
			String gsdm = getGsdm();
			List<Xf> xfs = getXfList();
			List<Skp> skps = getSkpList();
			pagination.addParam("gsdm", gsdm);
			if(xfs !=null&&xfs.size()>0){
				pagination.addParam("xfs", xfs);
			}
			if(skps !=null&&skps.size()>0){
				pagination.addParam("skps", skps);
			}
			pagination.addParam("xfid", xfid);
			pagination.addParam("skpid", skpid);
			pagination.addParam("fplx", fplx);
			pagination.addParam("xfsh", xfsh);
			pagination.addParam("fpsl", fpsl);
			List<Fpkcvo> kcjkList = fpkcService.findKcjkByPage(pagination);
			int total = pagination.getTotalRecord();
			result.put("recordsTotal", total);
			result.put("recordsFiltered", total);
			result.put("draw", draw);
			result.put("data", kcjkList);
			return result;
		}


}
