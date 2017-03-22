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
		/*if(kprqz !=null && !"".equals(kprqz)){
			Date date = sdf.parse(kprqz);
			Calendar calender = Calendar.getInstance();
	        calender.setTime(date);
	        calender.add(Calendar.MONTH, 1);
	        kprqz = sdf.format(calender.getTime());
		}*/
		params.put("kprqz", kprqz);
		List<Cxtjvo> list = ks.findYypl(params);
		if(list !=null && list.size()>0){
			long start = Long.valueOf(kprqq.replaceAll("-", ""));
			long end = Long.valueOf(kprqz.replaceAll("-", ""));
			String dateStr = kprqq;
			long time = Long.valueOf(dateStr.replaceAll("-", ""));
			int i=0;
			while(start<=time&&time<=end){
				if(dateStr.equals(list.get(i>list.size()-1?list.size()-1:i).getKpny())){
					result.put(list.get(i).getKpny(),list.get(i).getFpsl());
					i++;
				}else{
					result.put(dateStr, 0);
				}
				Date date = sdf.parse(dateStr);
				Calendar calender = Calendar.getInstance();
			    calender.setTime(date);
			    calender.add(Calendar.MONTH, 1);
			    dateStr = sdf.format(calender.getTime());
			    time = Long.valueOf(dateStr.replaceAll("-", ""));
			}				
		}else{
			long start = Long.valueOf(kprqq.replaceAll("-", ""));
			long end = Long.valueOf(kprqz.replaceAll("-", ""));
			String dateStr = kprqq;
			long time = Long.valueOf(dateStr.replaceAll("-", ""));
			while(start<=time&&time<=end){
				result.put(dateStr, 0);
				Date date = sdf.parse(dateStr);
				Calendar calender = Calendar.getInstance();
		        calender.setTime(date);
		        calender.add(Calendar.MONTH, 1);
		        dateStr = sdf.format(calender.getTime());
		        time = Long.valueOf(dateStr.replaceAll("-", ""));
			}
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
			long start = Long.valueOf(kprqq.replaceAll("-", ""));
			long end = Long.valueOf(kprqz.replaceAll("-", ""));
			String dateStr = kprqq;
			long time = Long.valueOf(dateStr.replaceAll("-", ""));
			int i=0;
			while(start<=time&&time<=end){
				if(dateStr.equals(list.get(i>list.size()-1?list.size()-1:i).getTqny())){
					result.put(list.get(i).getTqny(),list.get(i).getTqsl());
					i++;
				}else{
					result.put(dateStr, 0);
				}
				Date date = sdf.parse(dateStr);
				Calendar calender = Calendar.getInstance();
			    calender.setTime(date);
			    calender.add(Calendar.MONTH, 1);
			    dateStr = sdf.format(calender.getTime());
			    time = Long.valueOf(dateStr.replaceAll("-", ""));
			}				
		}else{
			long start = Long.valueOf(kprqq.replaceAll("-", ""));
			long end = Long.valueOf(kprqz.replaceAll("-", ""));
			String dateStr = kprqq;
			long time = Long.valueOf(dateStr.replaceAll("-", ""));
			while(start<=time&&time<=end){
				result.put(dateStr, 0);
				Date date = sdf.parse(dateStr);
				Calendar calender = Calendar.getInstance();
		        calender.setTime(date);
		        calender.add(Calendar.MONTH, 1);
		        dateStr = sdf.format(calender.getTime());
		        time = Long.valueOf(dateStr.replaceAll("-", ""));
			}
		}
		return result;
	}
}
