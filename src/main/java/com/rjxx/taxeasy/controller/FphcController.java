package com.rjxx.taxeasy.controller;

import com.rjxx.taxeasy.bizcomm.utils.FphcService;
import com.rjxx.taxeasy.bizcomm.utils.GetXmlUtil;
import com.rjxx.taxeasy.bizcomm.utils.InvoiceResponse;
import com.rjxx.taxeasy.domains.*;
import com.rjxx.taxeasy.service.*;
import com.rjxx.taxeasy.vo.Fpcxvo;
import com.rjxx.taxeasy.vo.Kpspmxvo;
import com.rjxx.taxeasy.web.BaseController;
import com.rjxx.utils.ChinaNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/fphc")
public class FphcController extends BaseController {

	@Autowired
	private KplsService kplsService;
	@Autowired
	private KpspmxService mxService;
	@Autowired
	private FphcService fphcService;
	@Autowired 
	private JylsService jylsService;
	@Autowired 
	private JyspmxService jyspmxService;
	@Autowired
	private CszbService cszbService;
	@Autowired
	private SkpService skpService;
	
	@RequestMapping
	public String index() throws Exception {
		request.setAttribute("xfList", getXfList());
		request.setAttribute("skpList", getSkpList());
		return "fphc/index";
	}

	@RequestMapping(value = "/getKplsList")
	@ResponseBody
	public Map<String, Object> getItems(int length, int start, int draw,boolean loaddata) throws Exception {
		int total;
		Map<String, Object> result = new HashMap<String, Object>();
		if(loaddata){
			String kprqq=request.getParameter("kprqq");//开票日期起
			String kprqz=request.getParameter("kprqz");//开票日期止

			String gfmc=request.getParameter("gfmc");//购方名称

			String xfi=request.getParameter("xfi");//销方税号

			String fplx=request.getParameter("fplx");//发票类型

			String ddh=request.getParameter("ddh");//订单号

			String fpdm=request.getParameter("fpdm");//发票代码

			String fphm=request.getParameter("fphm");//发票号码

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
			params.put("yxbz","1");
			params.put("fpczlx","11");
			List fpztlist = new ArrayList();
			fpztlist.add("00");
			fpztlist.add("01");
			params.put("fpztlist",fpztlist);
			//List<Fpcxvo> khcfpList = kplsService.findKhcfpByPage(pagination);
			List<Fpcxvo> khcfpList = kplsService.findByPage2(params);
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
		//logger.info("result={}", JSON.toJSONString(result));
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
		params.put("fpzt", "00");
		params.put("fpczlx", "12");
		params.put("yxbz","1");
		int total;
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
			//int total = pagination.getTotalRecord();
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
	/**
	 * 弃用
	 *
	 * @return
	 * @throws Exception
	 */
	/*@RequestMapping(value = "/getKplsList")
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
		if(loaddata){
			int total = pagination.getTotalRecord();
			List<Fpcxvo> khcfpList = kplsService.findKhcfpByPage(pagination);
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
		//logger.info("result={}", JSON.toJSONString(result));
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
		if(loaddata2){
			int total = pagination.getTotalRecord();
			List<Fpcxvo> khcfpList = kplsService.findKhcfpByPage1(pagination);
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
	}*/

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

	/**
	 * 发票红冲查询商品明细
	 * @param kplsh
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getFphccxMx")
	@ResponseBody
	public Map<String, Object> getFphccxMx(int kplsh) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map params = new HashMap<>();
		params.put("id", kplsh);
		List<Kpspmxvo> kpspmxList = mxService.findFphccxByParams(params);
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

	@RequestMapping(value = "/hc")
	@ResponseBody
	public Map<String, Object> update(String hcjeStr, String xhStr, Integer kplsh,String hztzdh,String jylsh) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		boolean isb= false;
		result.put("success", true);
		result.put("msg", "红冲成功！");
		Map map = new HashMap<>();
		map.put("kplsh", kplsh);
		Fpcxvo cxvo = kplsService.selectMonth(map);
		if (cxvo != null) {
			if (cxvo.getXcyf() != null && cxvo.getXcyf() > 12) {
				result.put("success", false);
				result.put("msg", "超过开票日期一年，不能红冲！");
				return result;
			}
		}
		//服务器-纸票-红冲
		Kpls kpls = kplsService.findOneByParams(map);
		Skp skp = skpService.findOne(kpls.getSkpid());
		Cszb cszb=cszbService.getSpbmbbh(getGsdm(),kpls.getXfid(),kpls.getSkpid(),"kpfs");
		if(cszb!=null&&"03".equals(cszb.getCsz())&&kpls.getFpzldm().contains("0")){
			isb=true;
			Cszb skurl=cszbService.getSpbmbbh(getGsdm(),skp.getXfid(),skp.getId(),"skurl");
			String servletip = skurl.getCsz().split("http://")[1].split("/")[0].split(":")[0];
			String servletport = skurl.getCsz().split("http://")[1].split("/")[0].split(":")[1];
			result.put("servletip",servletip);
			result.put("servletport",servletport);
			result.put("zsmm",skp.getZsmm());
			result.put("success", true);
			result.put("msg", "红冲请求已接受！");
			result.put("isb", isb);
			Map map1 = fphcService.hccl1(kplsh, getYhid(), getGsdm(), hcjeStr, xhStr,hztzdh,jylsh);
			if(map1.get("xml")==null){
				result.put("success", false);
				result.put("msg", "红冲请求失败，封装数据异常!");
				return result;
			}
			result.put("xml", map1.get("xml"));
			result.put("hckplsh",map1.get("kplsh"));
			return result;
		}
		InvoiceResponse flag = fphcService.hccl(kplsh, getYhid(), getGsdm(), hcjeStr, xhStr,hztzdh,jylsh);
		if (flag.getReturnCode().equals("0000")) {
			result.put("success", true);
			result.put("msg", "红冲请求已接受!");
		}else{
			result.put("success", false);
			result.put("msg", "红冲请求失败!"+flag.getReturnMessage());
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
		return "fphc/fapiao";
	}
}
