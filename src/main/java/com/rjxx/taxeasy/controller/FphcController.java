package com.rjxx.taxeasy.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.rjxx.taxeasy.domains.Gsxx;
import com.rjxx.taxeasy.domains.Jyls;
import com.rjxx.taxeasy.domains.Jyspmx;
import com.rjxx.taxeasy.domains.Kpls;
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
import com.rjxx.time.TimeUtil;

@Controller
@RequestMapping("/fphc")
public class FphcController extends BaseController {

	@Autowired
	private KplsService kplsService;
	@Autowired
	private KpspmxService mxService;
	@Autowired
	private JylsService jylsService;
	@Autowired
	private JyspmxService jymxService;
	@Autowired
	private DataOperte dc;

	@Autowired
	private GsxxService gsxxservice;
	
	@RequestMapping
	public String index() throws Exception {
		return "fphc/index";
	}

	@RequestMapping(value = "/getKplsList")
	@ResponseBody
	public Map<String, Object> getItems(int length, int start, int draw, String kprqq, String kprqz, String fpdm,
			String fphm, String gfmc, String ddh) throws Exception {
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
		pagination.addParam("ddh", ddh);
		pagination.addParam("fphm", fphm);
		pagination.addParam("kprqq", kprqq);
		pagination.addParam("kprqz", kprqz);
		pagination.addParam("gfmc", gfmc);
		List<Fpcxvo> khcfpList = kplsService.findKhcfpByPage(pagination);
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
	@RequestMapping(value = "/hc")
	@ResponseBody
	public Map<String, Object> update(String hcjeStr, String xhStr, String kplsh) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		result.put("success", true);
		result.put("msg", "红冲成功！");
		DecimalFormat df = new DecimalFormat("#.00");
		DecimalFormat df6 = new DecimalFormat("#.000000");
		Map map = new HashMap<>();
		map.put("kplsh", kplsh);
		Fpcxvo cxvo = kplsService.selectMonth(map);
		if (cxvo != null) {
			if (cxvo.getXcyf() != null && cxvo.getXcyf() > 6) {
				result.put("success", false);
				result.put("msg", "超过开票日期6个月，不能红冲！");
				return result;
			}
		}
		//kzx 20161212 如果走税控服务器红冲则设置clztdm为03
		Map paramsTmp = new HashMap();
		paramsTmp.put("gsdm", getGsdm());
		Gsxx gsxx = gsxxservice.findOneByParams(paramsTmp);
		
		String jylsh = "";
		Integer djh = 0;
		try {
			int yhid = getYhid();
			String gsdm = getGsdm();
			double hjhcje = 0;
			String[] hcje = hcjeStr.substring(0, hcjeStr.length() - 1).split(",");
			for (int j = 0; j < hcje.length; j++) {
				hjhcje += Double.valueOf(hcje[j]);
			}
			String hcjshj = df.format(hjhcje); // 本次红冲金额的价税合计
			String[] xh = xhStr.substring(0, xhStr.length() - 1).split(",");
			// 保存交易流水表
			Map param3 = new HashMap<>();
			param3.put("kplsh", kplsh);
			List<Kpls> kplsList = kplsService.printSingle(param3);
			Kpls kpls = kplsList.get(0);
			djh = kpls.getDjh();
			Map param4 = new HashMap<>();
			param4.put("djh", djh);
			Jyls jyls = jylsService.findJylsByDjh(param4);
			String ddh = jyls.getDdh(); // 查询原交易流水得ddh
			Map jylsParam = new HashMap<>();
			Jyls jyls1 = new Jyls();
			jyls1.setDdh(ddh);
			jylsh = "JY" + new SimpleDateFormat("yyyyMMddHHmmssSS").format(new Date());
			jyls1.setJylsh(jylsh);
			jyls1.setJylssj(TimeUtil.getNowDate());
			jyls1.setFpzldm("12");
			jyls1.setFpczlxdm("12");
			jyls1.setXfid(kpls.getXfid());
			jyls1.setXfsh(kpls.getXfsh());
			jyls1.setXfmc(kpls.getXfmc());
			jyls1.setXfyh(kpls.getXfyh());
			jyls1.setXfyhzh(kpls.getXfyhzh());
			jyls1.setXflxr(kpls.getXflxr());
			jyls1.setXfdh(kpls.getXfdh());
			jyls1.setXfdz(kpls.getXfdz());
			jyls1.setGfid(kpls.getGfid());
			jyls1.setGfsh(kpls.getGfsh());
			jyls1.setGfmc(kpls.getGfmc());
			jyls1.setGfyh(kpls.getGfyh());
			jyls1.setGfyhzh(kpls.getGfyhzh());
			jyls1.setGflxr(kpls.getGflxr());
			jyls1.setGfdh(kpls.getGfdh());
			jyls1.setGfdz(kpls.getGfdz());
			jyls1.setGfyb(kpls.getGfyb());
			jyls1.setGfemail(kpls.getGfemail());
			if(null != gsxx.getWsUrl() && !gsxx.getWsUrl().equals("")){
				jyls1.setClztdm("03");
			}else{
				jyls1.setClztdm("01");
			}
			jyls1.setBz(kpls.getBz());
			jyls1.setSkr(kpls.getSkr());
			jyls1.setKpr(kpls.getKpr());
			jyls1.setFhr(kpls.getFhr());
			jyls1.setSsyf(kpls.getSsyf());
			jyls1.setYfpdm(kpls.getFpdm());
			jyls1.setYfphm(kpls.getFphm());
			jyls1.setHsbz("0");
			jyls1.setJshj(-Double.valueOf(hcjshj));
			jyls1.setYkpjshj(0d);
			jyls1.setYxbz("1");
			jyls1.setGsdm(kpls.getGsdm());
			jyls1.setLrry(yhid);
			jyls1.setLrsj(TimeUtil.getNowDate());
			jyls1.setXgry(yhid);
			jyls1.setXgsj(TimeUtil.getNowDate());
			jyls1.setSkpid(kpls.getSkpid());
			jylsService.save(jyls1);
			djh = jyls1.getDjh();
			for (int i = 0; i < xh.length; i++) {
				Map params = new HashMap<>();
				params.put("kplsh", kplsh);
				params.put("xh", xh[i]);
				Kpspmxvo mxItem = mxService.findMxByParams(params);
				if (mxItem.getKhcje() != null) {
					String khcje = df.format(mxItem.getKhcje() - Double.valueOf(hcje[i]));
					Map param1 = new HashMap<>();
					param1.put("khcje", khcje);
					String yhcje = df.format(mxItem.getYhcje() + Double.valueOf(hcje[i]));
					String sps = null;
					if (mxItem.getSps() != null) {
						sps = df6.format(Double.valueOf(hcje[i]) / mxItem.getJshj() * mxItem.getSps()); // 没红冲部分的商品数量
					}
					param1.put("sps", sps);
					Double spje = Double.valueOf(hcje[i]) / mxItem.getJshj() * mxItem.getSpje();
					param1.put("spje", df6.format(spje));
					Double spse = Double.valueOf(hcje[i]) - spje;
					param1.put("spse", df6.format(spse));
					param1.put("yhcje", yhcje);
					param1.put("kplsh", kplsh);
					param1.put("xh", xh[i]);
					mxService.update(param1);
					// 交易保存明细
					if (Double.valueOf(hcje[i]) != 0) {
						Jyspmx jymx = new Jyspmx();
						jymx.setDjh(djh);
						jymx.setSpmxxh(Integer.parseInt(mxItem.getSpmxxh()));
						jymx.setSpdm(mxItem.getSpdm());
						jymx.setSpmc(mxItem.getSpmc());
						jymx.setSpggxh(mxItem.getSpggxh());
						jymx.setSpdw(mxItem.getSpdw());
						if (sps != null) {
							jymx.setSps(-Double.valueOf(sps));
						} else {
							jymx.setSps(null);
						}
						jymx.setSpdj(mxItem.getSpdj() == null ? null : mxItem.getSpdj());
						jymx.setSpje(-Double.valueOf(df6.format(spje)));
						jymx.setSpsl(mxItem.getSpsl());
						jymx.setSpse(-Double.valueOf(df.format(spse)));
						jymx.setJshj(-Double.valueOf(hcje[i]));
						jymx.setYkphj(Double.valueOf(khcje));
						jymx.setGsdm(gsdm);
						jymx.setLrsj(TimeUtil.getNowDate());
						jymx.setLrry(yhid);
						jymx.setXgsj(TimeUtil.getNowDate());
						jymx.setXgry(yhid);
						jymx.setFphxz("0");
						jymxService.save(jymx);
					}
				} else {
					String khcje = df.format(mxItem.getJshj() - Double.valueOf(hcje[i]));
					Map param1 = new HashMap<>();
					param1.put("khcje", khcje);
					String yhcje = df.format(Double.valueOf(hcje[i]));
					String sps = null;
					if (mxItem.getSps() != null) {
						sps = df6.format(Double.valueOf(khcje) / mxItem.getJshj() * mxItem.getSps()); // 没红冲部分的商品数量
					}
					param1.put("sps", sps);
					Double spje = Double.valueOf(khcje) / mxItem.getJshj() * mxItem.getSpje();
					param1.put("spje", df6.format(spje));
					Double spse = Double.valueOf(khcje) - spje;
					param1.put("spse", df6.format(spse));
					param1.put("yhcje", yhcje);
					param1.put("kplsh", kplsh);
					param1.put("xh", xh[i]);
					mxService.update(param1);
					// 交易保存明细
					if (Double.valueOf(hcje[i]) != 0) {
						Jyspmx jymx = new Jyspmx();
						jymx.setDjh(djh);
						jymx.setSpmxxh(Integer.parseInt(mxItem.getSpmxxh()));
						jymx.setSpdm(mxItem.getSpdm());
						jymx.setSpmc(mxItem.getSpmc());
						jymx.setSpggxh(mxItem.getSpggxh());
						jymx.setSpdw(mxItem.getSpdw());
						if (sps != null) {
							jymx.setSps(-Double.valueOf(df6.format(mxItem.getSps() - Double.valueOf(sps))));
						} else {
							jymx.setSps(null);
						}
						jymx.setSpdj(mxItem.getSpdj() == null ? null : mxItem.getSpdj());
						jymx.setSpje(-Double.valueOf(df.format(mxItem.getSpje() - spje)));
						jymx.setSpsl(mxItem.getSpsl());
						jymx.setSpse(-Double.valueOf(df.format(mxItem.getSpse() - spse)));
						jymx.setJshj(-Double.valueOf(hcje[i]));
						jymx.setYkphj(Double.valueOf(khcje));
						jymx.setGsdm(gsdm);
						jymx.setLrsj(TimeUtil.getNowDate());
						jymx.setLrry(yhid);
						jymx.setXgsj(TimeUtil.getNowDate());
						jymx.setXgry(yhid);
						jymx.setFphxz("0");
						jymxService.save(jymx);
					}
				}
			}
			Map param2 = new HashMap<>();
			param2.put("kplsh", kplsh);
			// 部分红冲后修改kpls表的三个金额
			/*
			 * Kpls ls = kplsService.findHjje(param2);
			 * param2.put("hjje",ls.getHjje()); param2.put("hjse",ls.getHjse());
			 * param2.put("jshj",ls.getJshj()); kplsService.updateHjje(param2);
			 */
			// 全部红冲后修改
			Kpspmxvo mxvo = mxService.findKhcje(param2);
			if (mxvo.getKhcje() == 0) {
				param2.put("fpztdm", "02");
				kplsService.updateFpczlx(param2);
			} else {
				param2.put("fpztdm", "01");
				kplsService.updateFpczlx(param2);
			}
			dc.saveLog(cxvo.getDjh(), "01", "0", "电子发票服务平台红冲操作", "已向服务端发送红冲请求", getYhid(), cxvo.getXfsh(), jylsh);
			result.put("success", true);
			result.put("msg", "红冲请求提交成功，请注意查看操作结果！");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", "后台出现错误: " + e.getMessage());
			dc.saveLog(cxvo.getDjh(), "92", "1", "", "电子发票服务平台红冲请求失败!", 2, cxvo.getXfsh(), jylsh);
			if (djh != 0) {
				Map para = new HashMap<>();
				para.put("clztdm", 92);
				para.put("xgsj", TimeUtil.getNowDate());
				para.put("djh", djh);
				jylsService.updateClzt(para);
			}
		}

		return result;
	}
}
