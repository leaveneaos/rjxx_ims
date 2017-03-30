package com.rjxx.taxeasy.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.domains.XxXtxx;
import com.rjxx.taxeasy.service.XxXtxxService;
import com.rjxx.taxeasy.web.BaseController;


@Controller
@RequestMapping("/xtxx")
public class XtxxController extends BaseController{
	
	@Autowired
	private XxXtxxService xtxxService;
	
	@RequestMapping
	public String index(){
		return "xtxx/index";
	}
	
	@RequestMapping(value = "/getList")
	@ResponseBody
	public Map<String,Object> getItems(int length, int start, int draw,String ydbz){
		Map<String,Object> result = new HashMap<String,Object>();
		Pagination pagination = new Pagination();
		pagination.setPageNo(start / length + 1);
		pagination.setPageSize(length);
		Integer yhid = getYhid();
		pagination.addParam("yhid", yhid);
		pagination.addParam("ydbz", ydbz);
		List<XxXtxx> xxList = xtxxService.findByPage(pagination);
		int total = pagination.getTotalRecord();
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		result.put("draw", draw);
		result.put("data", xxList);
		return result;
	}
	/**
	 * 全部已读
	 * 
	 * */
	@RequestMapping(value = "/readAll")
	@ResponseBody
	public Map<String,Object> readAll(){
		Map<String,Object> result = new HashMap<String,Object>();
		Integer yhid = getYhid();
		Map params = new HashMap<>();
		params.put("yhid", yhid);
		xtxxService.updateYdbz(params);
		result.put("success", true);
		return result;		
	}
	
	@RequestMapping(value = "/del")
	@ResponseBody
	public Map<String,Object> delete(Integer id){
		Map<String,Object> result = new HashMap<String,Object>();
		Map params = new HashMap<>();
		params.put("id", id);
		XxXtxx item = xtxxService.findOneByParams(params);
		item.setYxbz("0");
		xtxxService.save(item);
		result.put("success", true);
		return result;
	}
	/**
	 * 批量删除
	 * */
	@RequestMapping(value = "/delMany")
	@ResponseBody
	public Map<String,Object> deleteMany(String ids){
		Map<String,Object> result = new HashMap<String,Object>();
		String[] id = ids.split(",");
		List<Integer> idList = new ArrayList<Integer>();
		for(int i=0;i<id.length-1;i++){
			idList.add(Integer.parseInt(id[i]));
		}
		Map params = new HashMap<>();
		params.put("ids", idList);
		xtxxService.delMany(params);
		result.put("success", true);
		return result;
	}
	
	@RequestMapping(value = "/allRead")
	@ResponseBody
	public Map<String,Object> allRead(String ids){
		Map<String,Object> result = new HashMap<String,Object>();
		String[] id = ids.split(",");
		List<Integer> idList = new ArrayList<Integer>();
		for(int i=0;i<id.length-1;i++){
			idList.add(Integer.parseInt(id[i]));
		}
		Map params = new HashMap<>();
		params.put("ids", idList);
		xtxxService.allRead(params);
		result.put("success", true);
		return result;
	}
	
	
	@RequestMapping(value = "/allNotRead")
	@ResponseBody
	public Map<String,Object> allNotRead(String ids){
		Map<String,Object> result = new HashMap<String,Object>();
		String[] id = ids.split(",");
		List<Integer> idList = new ArrayList<Integer>();
		for(int i=0;i<id.length-1;i++){
			idList.add(Integer.parseInt(id[i]));
		}
		Map params = new HashMap<>();
		params.put("ids", idList);
		xtxxService.allNotRead(params);
		result.put("success", true);
		return result;
	}

}
