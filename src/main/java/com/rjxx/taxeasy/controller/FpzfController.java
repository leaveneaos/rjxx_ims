package com.rjxx.taxeasy.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.rjxx.taxeasy.bizcomm.utils.FpzfService;
import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.bizcomm.utils.DataOperte;
import com.rjxx.taxeasy.bizcomm.utils.InvoiceResponse;
import com.rjxx.taxeasy.domains.Jyls;
import com.rjxx.taxeasy.domains.Jyspmx;
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
import com.rjxx.utils.ChinaNumber;

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
	private JyspmxService jyspmxService;
	@Autowired
	private FpzfService FpzfService;
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
	public Map<String, Object> getItems(int length, int start, int draw,boolean loaddata) throws Exception {
		
		String kprqq=request.getParameter("kprqq");//开票日期起
		String kprqz=request.getParameter("kprqz");//开票日期止

		String gfmc=request.getParameter("gfmc");//购方名称

		String xfi=request.getParameter("xfi");//销方税号

		String fplx=request.getParameter("fplx");//发票类型
		
		String ddh=request.getParameter("ddh");//订单号

		String fpdm=request.getParameter("fpdm");//发票代码
		
		String fphm=request.getParameter("fphm");//发票号码
		
		
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
		pagination.addParam("fpzldm", fplx);
		pagination.addParam("gsdm", gsdm);
		pagination.addParam("xfid", xfid);
		pagination.addParam("xfi", xfi);
		pagination.addParam("skpid", skpid);
		pagination.addParam("kprqq", kprqq);
		pagination.addParam("kprqz", kprqz);
		pagination.addParam("gfmc", gfmc);
		pagination.addParam("ddh", ddh);
		pagination.addParam("fpdm", fpdm);
		pagination.addParam("fphm", fphm);
		
		List<Fpcxvo> khcfpList = kplsService.findKzffpByPage(pagination);
		int total = pagination.getTotalRecord();
		if(loaddata){
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
		pagination.addParam("fpzldm", fplx);
		pagination.addParam("gsdm", gsdm);
		pagination.addParam("xfid", xfid);
		pagination.addParam("xfi", xfi);
		pagination.addParam("skpid", skpid);
		pagination.addParam("kprqq", kprqq);
		pagination.addParam("kprqz", kprqz);
		pagination.addParam("gfmc", gfmc);
		pagination.addParam("ddh", ddh);
		pagination.addParam("fpdm", fpdm);
		pagination.addParam("fphm", fphm);
		
		
		List<Fpcxvo> khcfpList = kplsService.findKzffpByPage1(pagination);
		int total = pagination.getTotalRecord();
		if(loaddata2){
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

	@RequestMapping(value = "/zf")
	@ResponseBody
	public Map<String, Object> update(Integer kplsh) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
			InvoiceResponse flag = FpzfService.zfcl(kplsh, getYhid(), getGsdm());
			if (flag.getReturnCode().equals("0000")) {
				result.put("success", true);
				result.put("msg", "作废申请成功！");
			}else{
				result.put("success", true);
				result.put("msg", "作废请求失败!"+flag.getReturnMessage());
			}
		return result;
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
	}
}
