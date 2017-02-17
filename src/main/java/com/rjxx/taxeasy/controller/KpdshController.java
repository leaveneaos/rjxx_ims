package com.rjxx.taxeasy.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.domains.Fpzt;
import com.rjxx.taxeasy.domains.Jyls;
import com.rjxx.taxeasy.domains.Jymxsq;
import com.rjxx.taxeasy.domains.Jyspmx;
import com.rjxx.taxeasy.domains.Jyxxsq;
import com.rjxx.taxeasy.domains.Skp;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.service.JylsService;
import com.rjxx.taxeasy.service.JymxsqService;
import com.rjxx.taxeasy.service.JyspmxService;
import com.rjxx.taxeasy.service.JyxxsqService;
import com.rjxx.taxeasy.service.SkpService;
import com.rjxx.taxeasy.service.XfService;
import com.rjxx.taxeasy.web.BaseController;
import com.rjxx.time.TimeUtil;

@Controller
@RequestMapping("/kpdsh")
public class KpdshController extends BaseController {
	@Autowired
	private JyxxsqService jyxxsqService;
	@Autowired
	private JyspmxService jyspmxService;
	@Autowired
	private JymxsqService jymxsqService;
	@Autowired
	private JylsService jylsService;
	@Autowired
	private XfService xfService;
	@Autowired
	private SkpService skpService;
	@RequestMapping
	public String index() {
		request.setAttribute("xfList", getXfList());
		request.setAttribute("skpList", getSkpList());
		List<Double> list = new ArrayList<>();
		list.add(0.17);
		list.add(0.13);
		list.add(0.11);
		list.add(0.06);
		list.add(0.03);
		request.setAttribute("slList", list);
		return "kpdsh/index";
	}
	
	@ResponseBody
	@RequestMapping("/getItems")
	public Map<String, Object> getItems(int length, int start, int draw, String ddh, String kprqq, String kprqz,
			String spmc, String gfmc, String xfsh) throws Exception {
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
		// pagination.addParam("gsdm", gsdm);
		// pagination.addParam("xfid", xfid);
		// pagination.addParam("skpid", skpid);
		pagination.addParam("ddh", ddh);
		pagination.addParam("kprqq", kprqq);
		pagination.addParam("kprqz", kprqz);
		pagination.addParam("gsdm", gsdm);
		pagination.addParam("spmc", spmc);
		pagination.addParam("gfmc", gfmc);
		if (null != xfsh && !"".equals(xfsh) && !"-1".equals(xfsh)) {
			pagination.addParam("xfsh", xfsh);
		}
		List<Jyxxsq> ykfpList = jyxxsqService.findByPage(pagination);
		int total = pagination.getTotalRecord();
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		result.put("draw", draw);
		result.put("data", ykfpList);
		return result;
	}

	@ResponseBody
	@RequestMapping("/getMx")
	public Map<String, Object> getMx(String sqlsh) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<>();
		params.put("sqlsh", sqlsh);
		List<Jymxsq> ykfpList = jymxsqService.findAllByParams(params);
		result.put("data", ykfpList);
		return result;
	}

	@ResponseBody
	@RequestMapping("/th")
	public Map<String, Object> th(String ddhs) {
		Map<String, Object> result = new HashMap<String, Object>();
		String[] sqlshs = ddhs.split(",");
		for (String sqlsh : sqlshs) {
			Jyxxsq jyxxsq = jyxxsqService.findOne(Integer.valueOf(sqlsh));
			jyxxsq.setZtbz("0");
			jyxxsqService.save(jyxxsq);
		}
		result.put("msg", "退回成功");
		return result;
	}
	@ResponseBody
	@RequestMapping("/xgkpd")
	public Map<String, Object> xgkpd(Integer sqlsh){
		Map<String, Object> result = new HashMap<String, Object>();
		Jyxxsq jyxxsq = jyxxsqService.findOne(sqlsh);
		result.put("jyxx", jyxxsq);
		return result;
	}
	@ResponseBody
	@RequestMapping("/xgbckpd")
	public Map<String, Object> xgbckpd(Jyxxsq jyxxsq){
		Map<String, Object> result = new HashMap<String, Object>();
		Xf xf = new Xf();
		xf.setXfsh(jyxxsq.getXfsh());
		Xf xf1 = xfService.findOneByParams(xf);
		Map<String, Object> map = new HashMap<>();
		map.put("kpddm", jyxxsq.getKpddm());
		Skp skp1 = skpService.findOne(jyxxsq.getSkpid());
		Jyxxsq jyxxsq2 = jyxxsqService.findOne(jyxxsq.getSqlsh());
		jyxxsq2.setXfid(xf1.getId());
		jyxxsq2.setXfdh(xf1.getXfdh());
		jyxxsq2.setXfdz(xf1.getXfdz());
		jyxxsq2.setXfsh(xf1.getXfsh());
		jyxxsq2.setXfmc(xf1.getXfmc());
		jyxxsq2.setXfyh(xf1.getXfyh());
		jyxxsq2.setXfyhzh(xf1.getXfyhzh());
		jyxxsq2.setXflxr(xf1.getXflxr());
		jyxxsq2.setXfyb(xf1.getXfyb());
		jyxxsq2.setKpddm(skp1.getKpddm());
		jyxxsq2.setSkpid(skp1.getId());
		jyxxsq2.setDdh(jyxxsq.getDdh());
		jyxxsq2.setGfsh(jyxxsq.getGfsh());
		jyxxsq2.setGfmc(jyxxsq2.getGfmc());
		jyxxsq2.setGfyh(jyxxsq.getGfyh());
		jyxxsq2.setGfyhzh(jyxxsq.getGfyhzh());
		jyxxsq2.setGflxr(jyxxsq.getGflxr());
		jyxxsq2.setGfemail(jyxxsq.getGfemail());
		jyxxsq2.setGfsjh(jyxxsq.getGfsjh());
		jyxxsq2.setGfdz(jyxxsq.getGfdz());
		jyxxsq2.setBz(jyxxsq.getBz());
		jyxxsq2.setXgry(getYhid());
		jyxxsq2.setXgsj(new Date());
		jyxxsqService.save(jyxxsq2);
		result.put("msg", true);
		return result;
	}
	@ResponseBody
	@RequestMapping("/mxsc")
	public Map<String, Object> mxsc(Integer id){
		Map<String, Object> result = new HashMap<String, Object>();	
		Jymxsq jymxsq = jymxsqService.findOne(id);
		Map<String, Object> params = new HashMap<>();
		params.put("sqlsh", jymxsq.getSqlsh());
		List<Jymxsq> ykfpList = jymxsqService.findAllByParams(params);
		if (ykfpList.size()<2) {
			result.put("msg", "只有一条明细,请直接删除流水!");
			return result;
		}
		jymxsq.setYxbz("0");
		Jyxxsq jyxxsq = jyxxsqService.findOne(jymxsq.getSqlsh());
		jyxxsq.setJshj(jyxxsq.getJshj()-jymxsq.getJshj());
		jyxxsqService.save(jyxxsq);
		jymxsqService.save(jymxsq);
		result.put("msg", "删除成功!");
		return result;
	}
	@ResponseBody
	@RequestMapping("/xgbcmx")
	public Map<String, Object> xgbcmx(Jymxsq jymxsq){
		Map<String, Object> result = new HashMap<String, Object>();
		Jymxsq jymxsq2 = jymxsqService.findOne(jymxsq.getId());
		jymxsq2.setSpmc(jymxsq.getSpmc());
		jymxsq2.setSpggxh(jymxsq.getSpggxh());
		jymxsq2.setSpmc(jymxsq.getSpmc());
		jymxsq2.setSpdw(jymxsq.getSpdw());
		jymxsq2.setSps(jymxsq.getSps());
		jymxsq2.setSpdj(jymxsq.getSpdj());
		jymxsq2.setSpje(jymxsq.getSpje());
		jymxsq2.setSpse(jymxsq.getSpse());
		Jyxxsq jyxxsq = jyxxsqService.findOne(jymxsq2.getSqlsh());
		jyxxsq.setJshj(jyxxsq.getJshj()+jymxsq.getJshj()-jymxsq2.getJshj());
		jymxsq2.setJshj(jymxsq.getJshj());
		
		
		jymxsqService.save(jymxsq2);
		jyxxsqService.save(jyxxsq);
		result.put("msg", true);
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping("/kpdshkp")
	public Map<String, Object> kpdshkp(String sqlshs){
		Map<String, Object> result = new HashMap<String, Object>();
		String [] sqlsht=sqlshs.split(",");
		for (String str : sqlsht) {
			Jyxxsq jyxxsq = jyxxsqService.findOne(Integer.valueOf(str));
			Jyls jyls1 = new Jyls();
			jyls1.setDdh(jyxxsq.getDdh());
			String jylsh = "JY" + new SimpleDateFormat("yyyyMMddHHmmssSS").format(new Date());
			jyls1.setJylsh(jylsh);
			jyls1.setJylssj(TimeUtil.getNowDate());
			jyls1.setFpzldm(jyxxsq.getFpzldm());
			jyls1.setFpczlxdm("11");
			jyls1.setXfid(jyxxsq.getXfid());
			jyls1.setXfsh(jyxxsq.getXfsh());
			jyls1.setXfmc(jyxxsq.getXfmc());
			jyls1.setXfyh(jyxxsq.getXfyh());
			jyls1.setXfyhzh(jyxxsq.getXfyhzh());
			jyls1.setXflxr(jyxxsq.getXflxr());
			jyls1.setXfdh(jyxxsq.getXfdh());
			jyls1.setXfdz(jyxxsq.getXfdz());
			jyls1.setGfid(jyxxsq.getGfid());
			jyls1.setGfsh(jyxxsq.getGfsh());
			jyls1.setGfmc(jyxxsq.getGfmc());
			jyls1.setGfyh(jyxxsq.getGfyh());
			jyls1.setGfyhzh(jyxxsq.getGfyhzh());
			jyls1.setGflxr(jyxxsq.getGflxr());
			jyls1.setGfdh(jyxxsq.getGfdh());
			jyls1.setGfdz(jyxxsq.getGfdz());
			jyls1.setGfyb(jyxxsq.getGfyb());
			jyls1.setGfemail(jyxxsq.getGfemail());
			jyls1.setClztdm("00");
			jyls1.setBz(jyxxsq.getBz());
			jyls1.setSkr(jyxxsq.getSkr());
			jyls1.setKpr(jyxxsq.getKpr());
			jyls1.setFhr(jyxxsq.getFhr());
			jyls1.setSsyf(jyxxsq.getSsyf());
			jyls1.setYfpdm(null);
			jyls1.setYfphm(null);
			jyls1.setHsbz(jyxxsq.getHsbz());
			jyls1.setJshj(jyxxsq.getJshj());
			jyls1.setYkpjshj(0d);
			jyls1.setYxbz("1");
			jyls1.setGsdm(jyxxsq.getGsdm());
			jyls1.setLrry(getYhid());
			jyls1.setLrsj(TimeUtil.getNowDate());
			jyls1.setXgry(getYhid());
			jyls1.setXgsj(TimeUtil.getNowDate());
			jyls1.setSkpid(jyxxsq.getSkpid());
			jylsService.save(jyls1);
			Map<String, Object> params = new HashMap<>();
			params.put("sqlsh", jyxxsq.getSqlsh());
			List<Jymxsq> list = jymxsqService.findAllByParams(params);
			for (Jymxsq mxItem : list) {
				Jyspmx jymx = new Jyspmx();
				jymx.setDjh(jyls1.getDjh());
				jymx.setSpmxxh(mxItem.getSpmxxh());
				jymx.setSpdm(mxItem.getSpdm());
				jymx.setSpmc(mxItem.getSpmc());
				jymx.setSpggxh(mxItem.getSpggxh());
				jymx.setSpdw(mxItem.getSpdw());
				jymx.setSps(mxItem.getSps());
				jymx.setSpdj(mxItem.getSpdj() == null ? null : mxItem.getSpdj());
				jymx.setSpje(mxItem.getSpje());
				jymx.setSpsl(mxItem.getSpsl());
				jymx.setSpse(mxItem.getSpse());
				jymx.setJshj(mxItem.getJshj());
				jymx.setYkphj(0d);
				jymx.setGsdm(getGsdm());
				jymx.setLrsj(TimeUtil.getNowDate());
				jymx.setLrry(getYhid());
				jymx.setXgsj(TimeUtil.getNowDate());
				jymx.setXgry(getYhid());
				jymx.setFphxz("0");
				jyspmxService.save(jymx);
			}
			jyxxsq.setZtbz("2");
			jyxxsqService.save(jyxxsq);
		}
		result.put("msg", "审核成功!");
		return result;
		
	}
}
 