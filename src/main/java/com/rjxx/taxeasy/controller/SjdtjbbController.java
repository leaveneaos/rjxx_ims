package com.rjxx.taxeasy.controller;

import com.rjxx.taxeasy.domains.Fpzl;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.service.FpzlService;
import com.rjxx.taxeasy.service.KplsvoService;
import com.rjxx.taxeasy.vo.Cxtjvo;
import com.rjxx.taxeasy.vo.KplsVO;
import com.rjxx.taxeasy.web.BaseController;
import com.rjxx.time.TimeUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/sjdtjbb")
public class SjdtjbbController extends BaseController {
	@Autowired
	private KplsvoService ks;
	@Autowired
	private FpzlService fpzlService;

	@RequestMapping
	public String index() {
		List<Fpzl> fpzlList = fpzlService.findAllByParams(new HashMap<>());
		request.setAttribute("fpzlList", fpzlList);
		List<Xf> xfs = getXfList();
		request.setAttribute("xfs", xfs);
		return "sjdtjbb/index";
	}

	/**
	 * 每月用票量的查询
	 * */
	@RequestMapping(value = "/getYplPlot")
	@ResponseBody
	public Map<String,Object> getYypl(Integer xfid,Integer skpid,String kprqq,String kprqz) throws Exception{
		Map<String,Object> result = new LinkedHashMap<String,Object>();		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		if(kprqq==null||"".equals(kprqq)){
			result.put(" ", 0);
			return result;
		}
		Map params = new HashMap<>();
		String gsdm = getGsdm();
		params.put("gsdm", gsdm);
		params.put("xfid", xfid);
		params.put("skpid", skpid);
		params.put("kprqq", kprqq);
		if(kprqz !=null && !"".equals(kprqz)){
			Date date = sdf.parse(kprqz);
			Calendar calender = Calendar.getInstance();
	        calender.setTime(date);
	        calender.add(Calendar.MONTH, 1);
	        kprqz = sdf.format(calender.getTime());
		}
		params.put("kprqz", kprqz);
		List<Cxtjvo> list = ks.findYypl(params);
		if(list !=null && list.size()>0){
			for(Cxtjvo tjvo:list){
				result.put(tjvo.getKpny(),tjvo.getFpsl());
			}
		}else{
			result.put(" ", 0);
		}
		return result;
	}
	
	/**
	 * 每月提取量查询
	 * 
	 * */
	@RequestMapping(value = "/getTqlPlot")
	@ResponseBody
	public Map<String,Object> getYtql(Integer xfid,Integer skpid,String kprqq,String kprqz) throws Exception{
		Map<String,Object> result = new LinkedHashMap<String,Object>();		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		if(kprqq==null||"".equals(kprqq)){
			result.put(" ", 0);
			return result;
		}
		Map params = new HashMap<>();
		String gsdm = getGsdm();
		params.put("gsdm", gsdm);
		params.put("xfid", xfid);
		params.put("skpid", skpid);
		params.put("kprqq", kprqq);
		/*if(kprqz !=null && !"".equals(kprqz)){
			Date date = sdf.parse(kprqz);
			Calendar calender = Calendar.getInstance();
	        calender.setTime(date);
	        calender.add(Calendar.MONTH, 1);
	        kprqz = sdf.format(calender.getTime());
		}*/
		params.put("kprqz", kprqz);
		List<Cxtjvo> list = ks.findYtql(params);
		if(list !=null && list.size()>0){
			for(Cxtjvo tjvo:list){
				result.put(tjvo.getTqny(),tjvo.getTqsl());
			}
		}else{
			result.put(" ", 0);
		}
		return result;
	}
}
