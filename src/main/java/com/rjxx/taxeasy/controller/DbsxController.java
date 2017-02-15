package com.rjxx.taxeasy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjxx.taxeasy.service.FpzlService;
import com.rjxx.taxeasy.vo.Fpnum;
import com.rjxx.taxeasy.web.BaseController;


@Controller
@RequestMapping("/dbsx")
public class DbsxController extends BaseController{
	
	@Autowired
	private FpzlService fpzlService;
	
	@RequestMapping
	public String index() {
		Map params = new HashMap<>();
		String gsdm = this.getGsdm();
		params.put("gsdm", gsdm);
		List<Fpnum> list = fpzlService.findDbsx(params);
		Integer kpd = list.get(0).getFpnum();
		request.setAttribute("kpd", kpd);
		Integer fpkj = list.get(1).getFpnum();
		request.setAttribute("fpkj", fpkj);
		Integer fphc = list.get(2).getFpnum();
		request.setAttribute("fphc", fphc);
		Integer fphk = list.get(3).getFpnum();
		request.setAttribute("fphk", fphk);
		Integer fpzf = list.get(4).getFpnum();
		request.setAttribute("fpzf", fpzf);
		Integer fpck = list.get(5).getFpnum();
		request.setAttribute("fpck", fpck);
		Integer fpcd = list.get(6).getFpnum();
		request.setAttribute("fpcd", fpcd);
		return "dbsx/index";
	}
	
	@RequestMapping(value = "/getPlot")
	@ResponseBody
	public Map<String,Object> getPlot(){
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("2017-02-10",15);
		result.put("2017-02-11",3);
		result.put("2017-02-12",15);
		result.put("2017-02-13",28);
		result.put("2017-02-14",30);
		result.put("2017-02-15",47);
		return result;
	}

}
