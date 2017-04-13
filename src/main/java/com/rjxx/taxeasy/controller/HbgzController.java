package com.rjxx.taxeasy.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.domains.Hbgz;
import com.rjxx.taxeasy.service.HbgzService;
import com.rjxx.taxeasy.web.BaseController;
@Controller
@RequestMapping("/hbgz")
public class HbgzController extends BaseController{
	
	@Autowired
	private HbgzService hbgzservice;
	
	@RequestMapping
	public String index() {
		return "hbgz/index";
	}
	@RequestMapping("getList")
	@ResponseBody
	public Map<String, Object> getList(int length, int start, int draw,String gzmc){
		Map<String, Object> result = new HashMap<>();
		Pagination pagination = new Pagination();
		pagination.setPageNo(start / length + 1);
		pagination.setPageSize(length);
		String gsdm = getGsdm();
		pagination.addParam("gsdm", gsdm);
		pagination.addParam("gzmc", gzmc);
		List<Hbgz> list = hbgzservice.findByPage(pagination);
		int total = pagination.getTotalRecord();
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		result.put("draw", draw);
		result.put("data", list);
		return result;
	}
	
	//新增方法
	@RequestMapping("add")
	@ResponseBody
	public Map<String, Object> add(Hbgz hbgz){
		Map<String, Object> result = new HashMap<>();
		if (null!=hbgz&&null!=hbgz.getMrbz()&&"1".equals(hbgz.getMrbz())) {
			Map<String, Object> params = new HashMap<>();
			params.put("gsdm", getGsdm());
			List<Hbgz> list = hbgzservice.findAllByParams(params);
			for (Hbgz hbgz2 : list) {
				hbgz2.setMrbz("0");
				hbgzservice.save(hbgz2);
			}
 		}
		hbgz.setGsdm(getGsdm());
		hbgz.setLrry(getYhid());
		hbgz.setXgry(getYhid());
		hbgz.setLrsj(new Date());
		hbgz.setXgsj(new Date());
		hbgz.setYxbz("1");
		hbgzservice.save(hbgz);
		result.put("msg", "保存成功");
		return result;
	}
	//删除方法
	@RequestMapping("delete")
	@ResponseBody
	public Map<String, Object> delete(String gzid){
		Map<String, Object> result = new HashMap<>();
		String [] id = gzid.split(",");
		for (String string : id) {
			Hbgz hbgz = hbgzservice.findOne(Integer.valueOf(string));
			hbgz.setYxbz("0");
			hbgzservice.save(hbgz);
		}
		result.put("msg", "删除成功");
		return result;
	}
	//修改方法
	@RequestMapping("update")
	@ResponseBody
	public Map<String, Object> update(Hbgz hbgz,Integer idd){
		Map<String, Object> result = new HashMap<>();
		Hbgz hbgz3 = hbgzservice.findOne(idd);
		if (null!=hbgz&&null!=hbgz.getMrbz()&&"1".equals(hbgz.getMrbz())) {
			Map<String, Object> params = new HashMap<>();
			params.put("gsdm", getGsdm());
			List<Hbgz> list = hbgzservice.findAllByParams(params);
			for (Hbgz hbgz2 : list) {
				hbgz2.setMrbz("0");
				hbgzservice.save(hbgz2);
			}
 		}
		hbgz.setId(idd);
		hbgz.setGsdm(hbgz3.getGsdm());
		hbgz.setLrry(hbgz3.getLrry());
		hbgz.setXgry(getYhid());
		hbgz.setLrsj(hbgz3.getXgsj());
		hbgz.setXgsj(new Date());
		hbgz.setYxbz("1");
		hbgzservice.save(hbgz);
		result.put("msg", "修改成功");
		return result;
	}
}
