package com.rjxx.taxeasy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjxx.taxeasy.domains.Sm;
import com.rjxx.taxeasy.domains.Sp;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.service.SmService;
import com.rjxx.taxeasy.service.SpService;
import com.rjxx.taxeasy.service.SpbmService;
import com.rjxx.taxeasy.service.XfService;
import com.rjxx.taxeasy.vo.Spbm;
import com.rjxx.taxeasy.web.BaseController;

@Controller
@RequestMapping("spxx")
public class SpxxController extends BaseController{
	@Autowired
	private SmService smService;
	@Autowired
	private SpbmService spbmService;
	@Autowired
	private SpService spService;
	@Autowired
	private XfService xfService;
	@RequestMapping
	public String index(){
		List<Sm> list = smService.findAllByParams(new Sm());
		request.setAttribute("smlist", list);
		Map<String, Object> params = new HashMap<>();
		List<Spbm> spbms = spbmService.findAllByParam(params);
		request.setAttribute("spbms", spbms);
		params.put("gsdm", getGsdm());
		Sp sp = new Sp();
		sp.setGsdm(getGsdm());
		List<Sp> sps = spService.findAllByParams(sp);
		List<Xf> xfs = xfService.findAllByMap(params);
		request.setAttribute("sps", sps);
		request.setAttribute("xfs", xfs);
		return "spxx/index";
	}
	@RequestMapping("delete")
	@ResponseBody
	public Map<String, Object> delete(String spdm){
		Map<String, Object> result = new HashMap<>();
		Sp sp = new Sp();
		sp.setSpdm(spdm);
		sp.setGsdm(getGsdm());
		Sp sp2 = spService.findOneByParams(sp);
		if (sp2!=null) {
			sp2.setYxbz("0");
			spService.save(sp2);
		}
		result.put("success", true);
		result.put("msg", "删除商品成功");
		return result;
	}
	

}
