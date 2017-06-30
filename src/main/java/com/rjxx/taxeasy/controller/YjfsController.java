package com.rjxx.taxeasy.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjxx.taxeasy.domains.*;
import com.rjxx.taxeasy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.bizcomm.utils.GetYjnr;
import com.rjxx.taxeasy.bizcomm.utils.SendalEmail;
import com.rjxx.taxeasy.filter.SystemControllerLog;
import com.rjxx.taxeasy.vo.KplsVO;
import com.rjxx.taxeasy.web.BaseController;

/**
 * Created by lenovo on 2015/12/18.
 */
@Controller
@RequestMapping("/yjfs")
public class YjfsController extends BaseController {

	@Autowired
	private KplsService ks;

	@Autowired
	private KplsvoService kvs;

	@Autowired
	private JylsService js;

	@Autowired
	private GsxxService gs;

	@Autowired
	private SendalEmail se;

	@Autowired
	private JylsService jylsService;

	@Autowired
	private KplsService kplsService;

	@Autowired
	private GsxxService gsxxService;

	@Autowired
	private YjmbService yjmbService;
	@Autowired
	private JyxxsqService jyxxsqService;

	@RequestMapping
	public String index() {
		request.setAttribute("xfs", getXfList());
		return "yjfs/index";
	}

	@RequestMapping(value = "/send")
	@ResponseBody
	@SystemControllerLog(description = "发送邮件",key = "ids")  
	public Map send(String ids) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<>();
		String msg = "";
		if (!"".equals(ids)) {
			String[] idls = ids.split(",");
			for (int i = 0; i < idls.length; i++) {
				String id = idls[i].split(":")[0];
				if (id.equals("")) {
					continue;
				}
				try {
					params.put("kplsh", id);
					final Kpls kpls = ks.findOneByParams(params);
					if (kpls.getGfemail() == null || "".equals(kpls.getGfemail())) {
						msg += "发票代码：" + kpls.getFpdm() + "|发票号码：" + kpls.getFphm() + "无邮箱,发送失败;";
						continue;
					}
					Jyls jyls = js.findOne(kpls.getDjh());
					Map jyxxsqMap=new HashMap();
					jyxxsqMap.put("gsdm",kpls.getGsdm());
					jyxxsqMap.put("jylsh",jyls.getJylsh());
					Jyxxsq jyxxsq=jyxxsqService.findOneByJylsh(jyxxsqMap);
					Kpls ls = new Kpls();
					ls.setDjh(jyls.getDjh());
					List<Kpls> lslist = kplsService.findAllByKpls(ls);
					List<String> pdfUrlList = new ArrayList<>();
					for (Kpls kpls1 : lslist) {
						pdfUrlList.add(kpls1.getPdfurl());
					}
					GetYjnr getYjnr = new GetYjnr();
					Map gsxxmap=new HashMap();
					gsxxmap.put("gsdm",kpls.getGsdm());
					Gsxx gsxx=gsxxService.findOneByGsdm(gsxxmap);
					Integer yjmbDm=gsxx.getYjmbDm();
					Yjmb yjmb=yjmbService.findOne(yjmbDm);
					String yjmbcontent=yjmb.getYjmbNr();
					Map csmap=new HashMap();
					csmap.put("ddh",jyls.getDdh());
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
					csmap.put("ddrq",sdf.format(jyxxsq.getDdrq()));
					csmap.put("pdfurls",pdfUrlList);
					csmap.put("xfmc",jyls.getXfmc());
					String content = getYjnr.getFpkjYj(csmap,yjmbcontent);
					se.sendEmail(String.valueOf(kpls.getDjh()), kpls.getGsdm(), kpls.getGfemail(), "手工发送邮件",
							String.valueOf(kpls.getDjh()), content, "电子发票");
					/*
					 * SendEmail se = new SendEmail();
					 * se.sendMail(jyls.getDdh(), kpls.getGfemail(), new
					 * ArrayList<String>() { { add(kpls.getPdfurl()); } },
					 * gsxx.getGsmc());
					 */
				} catch (Exception e) {
					e.printStackTrace();
					result.put("statu", "1");
					result.put("msg", "保存出现错误: " + e.getMessage());
					return result;
				}
			}
		}
		if ("".equals(msg)) {
			msg = "发送成功";
		}
		result.put("statu", "0");
		result.put("msg", msg);
		return result;
	}

	@RequestMapping(value = "/update")
	@ResponseBody
	public Map update(String kplsh, String gfemail, String wx, String sj) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("kplsh", kplsh);
			Kpls kpls = ks.findOneByParams(params);
			kpls.setGfemail(gfemail);
			ks.save(kpls);
			result.put("success", true);
			result.put("statu", "0");
			result.put("msg", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("failure", true);
			result.put("msg", "保存出现错误: " + e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/getYjfsList")
	@ResponseBody
	public Map getYjfsList(int length, int start, int draw, String jyrqq, String jyrqz, String kprqq, String kprqz,
			String gfmc, String ddh, String fpdm, String fphm, String xfmc, Integer xfid,boolean loaddata) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Pagination pagination = new Pagination();
		pagination.setPageNo(start / length + 1);
		pagination.setPageSize(length);
		List<Integer> xfs = new ArrayList<>();
		if (!getXfList().isEmpty()) {
			for (Xf xf : getXfList()) {
				xfs.add(xf.getId());
			}
		}
		if (xfs.size() > 0) {
			pagination.addParam("xfList", xfs);
		}
		pagination.addParam("gsdm", getGsdm());
		pagination.addParam("gfmc", gfmc);
		pagination.addParam("ddh", ddh);
		pagination.addParam("fphm", fphm);
		pagination.addParam("fpdm", fpdm);
		pagination.addParam("xfmc", xfmc);
		pagination.addParam("xfid", xfid);
		pagination.addParam("fpzl", "12");
		// pagination.addParam("jyrqq", jyrqq);
		// pagination.addParam("jyrqz", jyrqz);
		if (!"".equals(jyrqq)) {
			pagination.addParam("jyrqq", jyrqq);
		}
		if (!"".equals(jyrqz)) {
			pagination.addParam("jyrqz", jyrqz);
		}
		if (!"".equals(kprqq)) {
			pagination.addParam("kprqq", kprqq);
		}
		if (!"".equals(kprqz)) {
			pagination.addParam("kprqz", kprqz);
		}
		pagination.addParam("fpczlxdm", "11");
		List<KplsVO> list = kvs.findByPage(pagination);
		int total = pagination.getTotalRecord();
		if(loaddata){
			result.put("recordsTotal", total);
			result.put("recordsFiltered", total);
			result.put("draw", draw);
			result.put("data", list);
		}else{
			result.put("recordsTotal", 0);
			result.put("recordsFiltered", 0);
			result.put("draw", draw);
			result.put("data", new ArrayList<>());
		}
		return result;
	}
}
