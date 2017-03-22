package com.rjxx.taxeasy.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rjxx.taxeasy.domains.Drmb;
import com.rjxx.taxeasy.domains.Sm;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.filter.SystemControllerLog;
import com.rjxx.taxeasy.service.DrmbService;
import com.rjxx.taxeasy.service.SmService;
import com.rjxx.taxeasy.service.SpvoService;
import com.rjxx.taxeasy.vo.Spvo;
import com.rjxx.taxeasy.web.BaseController;

@Controller
@RequestMapping("/kpdshxb")
public class KpdshxbController extends BaseController{
	
	@Autowired
	private SmService smService;
	
	@Autowired
	private SpvoService spvoService;
	@Autowired
	DrmbService drmbService;
	@RequestMapping
	@SystemControllerLog(description = "开票单审核页面进入",key = "")
	public String index() {
		request.setAttribute("xfList", getXfList());
		request.setAttribute("skpList", getSkpList());
		List<Sm> list = smService.findAllByParams(new Sm());
		request.setAttribute("smlist", list);
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
		request.setAttribute("spList", spList);
		request.setAttribute("xfSum", xfList.size());

		return "kpdshxb/index";
	}

}
