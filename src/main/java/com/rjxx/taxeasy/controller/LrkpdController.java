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
import com.rjxx.taxeasy.domains.Drmb;
import com.rjxx.taxeasy.domains.Jyxx;
import com.rjxx.taxeasy.domains.Skp;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.filter.SystemControllerLog;
import com.rjxx.taxeasy.service.DrPzService;
import com.rjxx.taxeasy.service.DrmbService;
import com.rjxx.taxeasy.service.JyxxService;
import com.rjxx.taxeasy.service.KplsvoService;
import com.rjxx.taxeasy.service.SpvoService;
import com.rjxx.taxeasy.vo.KplsVO;
import com.rjxx.taxeasy.vo.Spvo;
import com.rjxx.taxeasy.web.BaseController;

@Controller
@RequestMapping("/lrkpd")
public class LrkpdController extends BaseController {

	@Autowired
	private JyxxService jys;

	@Autowired
	private SpvoService spvoService;

	@Autowired
	DrmbService drmbService;
	@RequestMapping
	@SystemControllerLog(description = "功能首页",key = "xfsh")   
	public String index() {
		String gsdm = this.getGsdm();
		List<Object> argList = new ArrayList<>();
		argList.add(gsdm);
		List<Spvo> spList = spvoService.findAllByGsdm(gsdm);
		List<Xf> xfList = this.getXfList();
		if (!spList.isEmpty()) {
			request.setAttribute("sp", spList.get(0));
		}
		if (xfList.size() == 1) {
			Map<String, Object> map = new HashMap<>();
			map.put("xfsh", xfList.get(0).getXfsh());
			map.put("xfs", getXfList());
			map.put("gsdm", gsdm);
			List<Drmb> mbList = drmbService.findAllByParams(map);
			Drmb mb = new Drmb();
			mb.setXfsh(xfList.get(0).getXfsh());
			request.setAttribute("skps", getSkpList());
			request.setAttribute("skpSum", getSkpList().size());
			request.setAttribute("mrmb", drmbService.findMrByParams(mb));
			request.setAttribute("mbList", mbList);
			request.setAttribute("mbSum", mbList.size());
		}
		if (xfList != null && xfList.size() > 0) {
			request.setAttribute("xf", xfList.get(0));
		}
		List<Skp> skpList = this.getSkpList();
		request.setAttribute("spList", spList);
		request.setAttribute("xfSum", xfList.size());
		request.setAttribute("xfList", xfList);
		request.setAttribute("skpList", skpList);
		return "lrkpd/index";
	}

	/**
	 * 初始化显示列表
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getGfxxList")
	@ResponseBody
	public Map getGfxxList(int length, int start, int draw, String ddh, String kpddm, String ddrqq, String ddrqz)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Pagination pagination = new Pagination();
		pagination.setPageNo(start / length + 1);
		pagination.setPageSize(length);
		String gsdm = getGsdm() ;
		pagination.addParam("gsdm", gsdm);
		pagination.addParam("ddh", ddh);
		pagination.addParam("kpddm", kpddm);
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd");
        SimpleDateFormat for2 = new SimpleDateFormat ("yyyyMMdd"); 
		if (!"".equals(ddrqq)) {
			Date kprqt = new Date();
			kprqt =formatter.parse(ddrqq);
			ddrqq = for2.format(kprqt);
			pagination.addParam("ddrqq", ddrqq);
		}
		if (!"".equals(ddrqz)) {
			Date kprqt = new Date();
			kprqt =formatter.parse(ddrqz);
			ddrqz = for2.format(kprqt);
			pagination.addParam("ddrqz", ddrqz);
		}
		//pagination.addParam("fpczlxdm", "12");
		List<Jyxx> list =jys.findByPage(pagination);
		int total = pagination.getTotalRecord();
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		result.put("draw", draw);
		result.put("data", list);

		return result;
	}
}
