package com.rjxx.taxeasy.controller;

import com.rjxx.taxeasy.bizcomm.utils.DataOperte;
import com.rjxx.taxeasy.bizcomm.utils.FpcdService;
import com.rjxx.taxeasy.bizcomm.utils.InvoiceResponse;
import com.rjxx.taxeasy.domains.*;
import com.rjxx.taxeasy.service.*;
import com.rjxx.taxeasy.vo.Fpcxvo;
import com.rjxx.taxeasy.vo.Kpspmxvo;
import com.rjxx.taxeasy.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/fpcd")
public class FpcdController extends BaseController{


	@Autowired
	private KplsService kplsService;
	@Autowired
	private KpspmxService mxService;
	@Autowired
	private JylsService jylsService;
	@Autowired
	private JyspmxService jyspmxService;
	@Autowired
	private FpcdService fpcdService;
	@Autowired
	private DataOperte dc;
	@Autowired
	private SkpService skpService;

	@Autowired
	private CszbService cszbService;
	@RequestMapping
	public String index() {
		request.setAttribute("xfList", getXfList());
		request.setAttribute("skpList", getSkpList());
		return "fpcd/index";
	}



	@RequestMapping(value = "/getKplsList")
	@ResponseBody
	public Map<String, Object> getZfItems(int length, int start, int draw,boolean loaddata) throws Exception {

		String kprqq=request.getParameter("kprqq");//开票日期起
		String kprqz=request.getParameter("kprqz");//开票日期止

		String gfmc=request.getParameter("gfmc");//购方名称

		String xfi=request.getParameter("xfi");//销方税号

		String fpzldm=request.getParameter("fplx");//发票类型

		String ddh=request.getParameter("ddh");//订单号

		String fpdm=request.getParameter("fpdm");//发票代码

		String fphm=request.getParameter("fphm");//发票号码


		Map<String, Object> result = new HashMap<String, Object>();
		//Pagination pagination = new Pagination();
		//pagination.setPageNo(start / length + 1);
		//pagination.setPageSize(length);
		Map params = new HashMap();
		params.put("start",start);
		params.put("length",length);
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
		params.put("fpzldm", fpzldm);
		params.put("gsdm", gsdm);
		params.put("xfid", xfid);
		params.put("xfi", xfi);
		params.put("skpid", skpid);
		params.put("kprqq2", kprqq);
		params.put("kprqz2", kprqz);
		params.put("gfmc", gfmc);
		params.put("ddh", ddh);
		params.put("fpdm", fpdm);
		params.put("fphm", fphm);
		List fpzllist = new ArrayList();
		fpzllist.add("01");
		fpzllist.add("02");
		params.put("fpzllist",fpzllist);
		params.put("fpztdm", "00");
		params.put("fpczlx","11");
		//List<Fpcxvo> khcfpList = kplsService.findKzffpByPage(pagination);
		int total ;
		if(0 == start && loaddata){
			total = kplsService.findTotal(params);
			request.getSession().setAttribute("total",total);
		}else{
			if(!loaddata){
				 total = 0;
			}else {
				total = (Integer) request.getSession().getAttribute("total");
			}
			//request.getSession().getAttribute("total");
		}
		if(loaddata){
			List<Fpcxvo> khcfpList = kplsService.findByPage2(params);
			result.put("recordsTotal", total);
			result.put("recordsFiltered", total);
			result.put("draw", draw);
			result.put("data", khcfpList);
		}else{
			result.put("recordsTotal", 0);
			result.put("recordsFiltered", 0);
			result.put("draw", draw);
			result.put("data", new ArrayList<>());
		}
		return result;
	}


	@RequestMapping(value = "/getKplsList1")
	@ResponseBody
	public Map<String, Object> getItems1(int length, int start, int draw,boolean loaddata2) throws Exception {

		String kprqq=request.getParameter("kprqq");//开票日期起
		String kprqz=request.getParameter("kprqz");//开票日期止

		String gfmc=request.getParameter("gfmc");//购方名称

		String xfi=request.getParameter("xfi");//销方税号

		String fplx=request.getParameter("fplx");//发票类型

		String ddh=request.getParameter("ddh");//订单号

		String fpdm=request.getParameter("fpdm");//发票代码

		String fphm=request.getParameter("fphm");//发票号码

		Map<String, Object> result = new HashMap<String, Object>();
		/*Pagination pagination = new Pagination();
		pagination.setPageNo(start / length + 1);
		pagination.setPageSize(length);*/
		Map params = new HashMap();
		params.put("start",start);
		params.put("length",length);
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
		params.put("fpzldm", fplx);
		params.put("gsdm", gsdm);
		params.put("xfid", xfid);
		params.put("xfi", xfi);
		params.put("skpid", skpid);
		params.put("kprqq2", kprqq);
		params.put("kprqz2", kprqz);
		params.put("gfmc", gfmc);
		params.put("ddh", ddh);
		params.put("fpdm", fpdm);
		params.put("fphm", fphm);

		List fpzllist = new ArrayList();
		fpzllist.add("01");
		fpzllist.add("02");
		params.put("fpzllist",fpzllist);
		//发票状态代码06:被重开
		params.put("fpzt", "06");
		//发票操作类型24:发票重打
		params.put("fpczlx","24");
		params.put("yxbz","1");
		//List<Fpcxvo> khcfpList = kplsService.findKzffpByPage1(pagination);

		int total ;
		if(0 == start && loaddata2){
			total = kplsService.findTotal(params);
			request.getSession().setAttribute("total",total);
		}else{
			if(!loaddata2){
				total = 0;
			}else {
				total = (Integer) request.getSession().getAttribute("total");
			}
			//request.getSession().getAttribute("total");
		}
		if(loaddata2){
			List<Fpcxvo> khcfpList = kplsService.findByPage2(params);
			result.put("recordsTotal", total);
			result.put("recordsFiltered", total);
			result.put("draw", draw);
			result.put("data", khcfpList);
		}else{
			result.put("recordsTotal", 0);
			result.put("recordsFiltered", 0);
			result.put("draw", draw);
			result.put("data", new ArrayList<>());
		}
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

	@RequestMapping(value = "/cd")
	@ResponseBody
	public Map<String, Object> update(Integer kplsh) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		boolean cdb = false;

//			if(cszb!=null&& "03".equals(cszb.getCsz())&&kpls.getFpzldm().contains("0"))

			InvoiceResponse flag = fpcdService.cdcl(kplsh, getYhid(), getGsdm());
			if ("0000".equals(flag.getReturnCode())) {
				result.put("success", true);
				result.put("msg", "重打请求成功！");
			}else{
				result.put("success", true);
				result.put("msg", "重打请求失败!"+flag.getReturnMessage());
			}
		result.put("zfb",cdb);
		return result;
	}


/*
	@RequestMapping(value = "/zfKpls1",method = RequestMethod.POST)
	@ResponseBody
	public Map saveKpls(String returncode,String returnmsg,String kplsh,
						String fpdm,String fphm,String zfrq){
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if(StringUtils.isBlank(kplsh)){
				result.put("success", false);
				return result;
			}
			Kpls kpls = kplsService.findOne(Integer.valueOf(kplsh));
			if(kpls==null){
				result.put("success", false);
				return result;
			}
			if("0".equals(returncode)){
				kpls.setZfrq(TimeUtil.getSysDateInDate(zfrq, null));
				kpls.setFpczlxdm("14");
				kpls.setFpztdm("08");
				kplsService.save(kpls);
				result.put("success", true);
				return result;
			}else {
				result.put("success", false);
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			return result;
		}
	}


	@RequestMapping(value = "/kpyl")
	public String kpyl(String kpsqhs) throws Exception {
		Map<String, Object> result = new HashMap<>();
		String kpsqh= kpsqhs.split(",")[0];
		Jyspmx jyspmx = new Jyspmx();
		jyspmx.setDjh(Integer.valueOf(kpsqh));
		List<Jyspmx> mxcl = jyspmxService.findAllByParams(jyspmx);
		Jyls jyls = jylsService.findOne(Integer.valueOf(kpsqh));
		List dxlist = new ArrayList();
		ChinaNumber cn = new ChinaNumber();
		Double aa = 0.00;
		for (int x = 0; x < mxcl.size(); x++) {
			aa = aa + mxcl.get(x).getJshj();
		}
		String jshjstr = new DecimalFormat("0.00").format(aa);
		dxlist.add(cn.getCHSNumber(jshjstr));
		session.setAttribute("cffplList", mxcl);
		session.setAttribute("jyls", jyls);
		session.setAttribute("zwlist", dxlist);
		return "fpzf/fapiao";
	}*/
}
