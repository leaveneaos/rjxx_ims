package com.rjxx.taxeasy.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rjxx.taxeasy.domains.Cszb;
import com.rjxx.taxeasy.domains.Drmb;
import com.rjxx.taxeasy.domains.Sm;
import com.rjxx.taxeasy.domains.Sp;
import com.rjxx.taxeasy.domains.Spz;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.filter.SystemControllerLog;
import com.rjxx.taxeasy.service.CszbService;
import com.rjxx.taxeasy.service.DrmbService;
import com.rjxx.taxeasy.service.SmService;
import com.rjxx.taxeasy.service.SpvoService;
import com.rjxx.taxeasy.service.SpzService;
import com.rjxx.taxeasy.vo.Spvo;
import com.rjxx.taxeasy.web.BaseController;

@Controller
@RequestMapping("/kpdshxb")
public class KpdshxbController extends BaseController{
	
	@Autowired
	private SmService smService;
	@Autowired
	private CszbService cszbService;
	@Autowired
	private SpvoService spvoService;
	@Autowired
	private SpzService spzService;
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
		Cszb cszb = cszbService.getSpbmbbh(gsdm, getXfid(), null, "sfqyspz");
		List<Spvo> list2 = new ArrayList<>();
		if (null!=cszb&&cszb.getCsz().equals("是")) {
			Map<String, Object> pMap = new HashMap<>();
			pMap.put("xfs", getXfList());
			list2 = spzService.findAllByParams(pMap);
		}
		if (list2.size()==0) {
			list2 = spvoService.findAllByGsdm(gsdm);
		}
		
		List<Xf> xfList = this.getXfList();
		if (!list2.isEmpty()) {
			request.setAttribute("sp", list2.get(0));
		}
		if (xfList.size() == 1) {
			Map<String, Object> map = new HashMap<>();
			/*map.put("xfsh", xfList.get(0).getXfsh());
			map.put("xfs", getXfList());*/
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
		request.setAttribute("spList", list2);
		request.setAttribute("xfSum", xfList.size());

		return "kpdshxb/index";
	}

}
