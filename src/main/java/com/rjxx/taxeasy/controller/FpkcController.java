package com.rjxx.taxeasy.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjxx.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.bizcomm.utils.SkService;
import com.rjxx.taxeasy.domains.Fpkc;
import com.rjxx.taxeasy.domains.FpkcMx;
import com.rjxx.taxeasy.domains.Fpzl;
import com.rjxx.taxeasy.domains.Skp;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.filter.SystemControllerLog;
import com.rjxx.taxeasy.service.FpkcMxService;
import com.rjxx.taxeasy.service.FpkcService;
import com.rjxx.taxeasy.service.FpzlService;
import com.rjxx.taxeasy.service.SkpService;
import com.rjxx.taxeasy.service.XfService;
import com.rjxx.taxeasy.vo.FpkcYjvo;
import com.rjxx.taxeasy.vo.Fpkcvo;
import com.rjxx.taxeasy.web.BaseController;
import com.rjxx.utils.Tools;

@Controller
@RequestMapping("/fpkc")
public class FpkcController extends BaseController {
	@Autowired
	private FpkcService fpkcService;
	@Autowired
	private FpzlService fpzlService;
	@Autowired
	private SkpService skpService;
	@Autowired
	private XfService xfService;
	@Autowired
	private SkService skService;
	@Autowired
	private FpkcMxService fpkcMxService;

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
	public Map<String, Object> getItems(int length, int start, int draw,Integer xfids,Integer skpids, String fpdm,String fplx,boolean loaddata ) throws Exception {
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
		int total = pagination.getTotalRecord();
		if(loaddata){
			result.put("recordsTotal", total);
			result.put("recordsFiltered", total);
			result.put("draw", draw);
			result.put("data", kcList);
		}else {
			result.put("recordsTotal", 0);
			result.put("recordsFiltered", 0);
			result.put("draw", draw);
			result.put("data", new ArrayList<>());
		}
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
		params.put("fpzldm", item.getFpzldm());
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
				String xfsh,Integer fpsl,boolean loaddata) throws Exception {
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
			if(loaddata){
				result.put("recordsTotal", total);
				result.put("recordsFiltered", total);
				result.put("draw", draw);
				result.put("data", kcjkList);
			}else {
				result.put("recordsTotal", 0);
				result.put("recordsFiltered", 0);
				result.put("draw", draw);
				result.put("data", new ArrayList<>());
			}
			return result;
		}
		//发票库存查询
		@RequestMapping(value = "/getAllkc")
		@ResponseBody
		public Map<String, Object> getAllkc(int length,int start,int draw,Integer xfid,Integer skpid,String fplx,
				String xfsh,Integer fpsl,boolean loaddata) throws Exception {
			Map<String, Object> result = new HashMap<String, Object>();
			Pagination pagination = new Pagination();
			pagination.setPageNo(start / length + 1);
			pagination.setPageSize(length);
			int yhid = getYhid();
			
			pagination.addParam("yhid", yhid);
			pagination.addParam("xfid", xfid);
			pagination.addParam("skpid", skpid);
			pagination.addParam("fplx", fplx);
			pagination.addParam("xfsh", xfsh);
			pagination.addParam("fpsl", fpsl);
			if(loaddata){
				List<FpkcYjvo> kcjkList = fpkcService.findKcYjByPage(pagination);
				
				int total = pagination.getTotalRecord();
				result.put("recordsTotal", total);
				result.put("recordsFiltered", total);
				result.put("draw", draw);
				result.put("data", kcjkList);
				result.put("recordsTotal", total);
				result.put("recordsFiltered", total);
				result.put("draw", draw);
				result.put("data", kcjkList);
			}else {
				result.put("recordsTotal", 0);
				result.put("recordsFiltered", 0);
				result.put("draw", draw);
				result.put("data", new ArrayList<>());
			}
			return result;
		}
		//发票库存明细查询
		@RequestMapping(value = "/getKcmx")
		@ResponseBody
		public Map<String, Object> getKcmx(int length,int start,int draw,Integer kcid,boolean loaddata) throws Exception {
			Map<String, Object> result = new HashMap<String, Object>();
			Pagination pagination = new Pagination();
			pagination.setPageNo(start / length + 1);
			pagination.setPageSize(length);
			pagination.addParam("kcid", kcid);
			
			//List<FpkcYjvo> kcmxList = fpkcService.findKcmxByPage(pagination);
			List<FpkcMx> kcmxList=fpkcMxService.findByPage(pagination);
			int total = pagination.getTotalRecord();
			result.put("recordsTotal", total);
			result.put("recordsFiltered", total);
			result.put("draw", draw);
			result.put("data", kcmxList);
			if(loaddata){
				result.put("recordsTotal", total);
				result.put("recordsFiltered", total);
				result.put("draw", draw);
				result.put("data", kcmxList);
			}else {
				result.put("recordsTotal", 0);
				result.put("recordsFiltered", 0);
				result.put("draw", draw);
				result.put("data", new ArrayList<>());
			}
			return result;
		}
		/**
		 * 自动读取发票库存
		 * */
		@RequestMapping(value = "/getServerFpkc")
		@ResponseBody
		public Map<String,Object> getFpkc(Integer skpid){
			Map<String,Object> result = new HashMap<String,Object>();
			List<Fpkcvo> fpkcList = new ArrayList<Fpkcvo>();
			if(skpid==null||"".equals(skpid)){
				result.put("data", fpkcList);
				return result;
			}
			List<Map<String,Object>> list = skService.getKc(skpid);
			if(list !=null && list.size()>0){
				for(Map<String,Object> map:list){
					Map params = new HashMap<>();
					params.put("id",skpid);
					Skp skp = skpService.findOneByParams(params);
					String gsdm = skp.getGsdm();
					Integer xfid = skp.getXfid();
					Xf xf = new Xf();
					xf.setId(xfid);
					xf = xfService.findOneByParams(xf);
					String xfmc = xf.getXfmc();
					String xfsh = xf.getXfsh();
					String kpdmc = skp.getKpdmc();
					String fpzldm = (String)map.get("fpzldm");
					params.put("fpzldm", fpzldm);
					Fpzl fpzl = fpzlService.findOneByParams(params);
					String fpzlmc = fpzl.getFpzlmc();
					String fpdm = (String)map.get("fpdm");
					String fphms = (String)map.get("fphms");
					String fphmz = (String)map.get("fphmz");
					Integer kyl = (Integer)map.get("kyl");
					Fpkcvo fpkc = new Fpkcvo();
					fpkc.setGsdm(gsdm);
					fpkc.setXfid(xfid);
					fpkc.setSkpid(skpid);
					fpkc.setXfmc(xfmc);
					fpkc.setXfsh(xfsh);
					fpkc.setKpdmc(kpdmc);
					fpkc.setFpzlmc(fpzlmc);
					fpkc.setFpdm(fpdm);
					fpkc.setFphms(fphms);
					fpkc.setFphmz(fphmz);
					fpkc.setFpkcl(Integer.parseInt(fphms)-Integer.parseInt(fphmz));
					fpkc.setKyl(kyl);
					fpkcList.add(fpkc);
				}
				result.put("success", true);
				result.put("data", fpkcList);
			}else{
				result.put("success", false);
				result.put("data", fpkcList);
			}			
			return result;
		}
        
		/**
		 * 发票自动获取库存后保存
		 * */
		@RequestMapping(value = "/saveAllkc")
		@ResponseBody
		public Map<String,Object> savekc()throws Exception{
			Map<String,Object> result = new HashMap<String,Object>();
			Map params = Tools.getParameterMap(request);
			String[] gsdm = ((String) params.get("gsdm")).split(",");
			String[] xfid = ((String) params.get("xfid")).split(",");
			String[] skpid = ((String) params.get("skpid")).split(",");
			String[] fpzldm = ((String) params.get("fpzldm")).split(",");
			String[] fpdm = ((String) params.get("fpdm")).split(",");
			String[] fphms = ((String) params.get("fphms")).split(",");
			String[] fphmz = ((String) params.get("fphmz")).split(",");
			String[] kyl = ((String) params.get("kyl")).split(",");
			for(int i=0;i<fpzldm.length;i++){
				Map param = new HashMap<>();
				param.put("gsdm", gsdm[0]);
				param.put("xfid", Integer.parseInt(xfid[0]));
				param.put("skpid", Integer.parseInt(skpid[0]));
				param.put("fpzldm", fpzldm[i]);
				param.put("fpdm", fpdm[i]);
				param.put("fphms", fphms[i]);
				Fpkc item = fpkcService.findOneByParams(param);
				if(item==null){
					Fpkc fpkc = new Fpkc();
					fpkc.setGsdm(gsdm[0]);
					fpkc.setXfid(Integer.parseInt(xfid[0]));
					fpkc.setSkpid(Integer.parseInt(skpid[0]));
					fpkc.setFpzldm(fpzldm[i]);
					fpkc.setFpdm(fpdm[i]);
					fpkc.setFphms(fphms[i]);
					fpkc.setFphmz(fphmz[i]);
					fpkc.setFpkcl(Integer.parseInt(kyl[i]));
					fpkc.setYxbz("1");
					fpkc.setLrsj(new Date());
					fpkc.setLrry(getYhid());
					fpkcService.save(fpkc);
				}else{
					item.setFphmz(fphmz[i]);
					item.setFpkcl(Integer.parseInt(kyl[i]));
					fpkcService.save(item);
				}				
			}
			result.put("success", true);
			result.put("msg", "保存成功！");
			return result;
		}

	@RequestMapping(value = "/updateYj")
	@ResponseBody
	public Map<String, Object> update( Integer xg_id,String yjkcl) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		result.put("msg", "保存成功！");
		try {
			if(xg_id==null|| StringUtils.isBlank(yjkcl)){
				result.put("success", false);
				result.put("msg", "保存失败！");
				return result;
			}
			Fpkc item = fpkcService.findOne(xg_id);
			item.setXgsj(new Date());
			item.setXgry(getYhid());
			item.setKcyjz(Integer.valueOf(yjkcl));
			fpkcService.save(item);
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "保存失败！");
		}
		return result;
	}

}
