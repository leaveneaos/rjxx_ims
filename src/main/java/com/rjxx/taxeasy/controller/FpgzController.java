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
import com.rjxx.taxeasy.domains.Csb;
import com.rjxx.taxeasy.domains.Fpgz;
import com.rjxx.taxeasy.domains.Skp;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.domains.Yh;
import com.rjxx.taxeasy.service.FpgzService;
import com.rjxx.taxeasy.service.YhService;
import com.rjxx.taxeasy.vo.CsbVo;
import com.rjxx.taxeasy.vo.FpgzVo;
import com.rjxx.taxeasy.web.BaseController;

@Controller
@RequestMapping("/fpgz")
public class FpgzController extends BaseController{

	@Autowired
	private FpgzService fpgzService;
	
	@Autowired
	private YhService yhService;
	
	@RequestMapping
	public String index() {
		request.setAttribute("xfs", getXfList());
		request.setAttribute("skpList", getSkpList());
		return "fpgz/index";
	}
	//查询
	@RequestMapping(value = "/getList")
	@ResponseBody
	public Map getList(int length, int start, int draw) {
		Pagination pagination = new Pagination();
		pagination.setPageNo(start / length + 1);
		pagination.setPageSize(length);
		pagination.addParam("gsdm", this.getGsdm());
		pagination.addParam("orderBy", "a.lrsj");
		List<FpgzVo> list = fpgzService.findByPage(pagination);
		int total = pagination.getTotalRecord();
	/*	for (FpgzVo cszb : list) {
			Yh yh = yhService.findOne(cszb.getLrry());
			
		}*/
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		result.put("draw", draw);
		result.put("data", list);
		return result;
	}
	
	//新增
	@RequestMapping(value = "/xzgz")
	@ResponseBody
	public Map<String, Object> xzgz(Fpgz fpgz,String[] xfid){
		Map<String, Object> result = new HashMap<String, Object>();
		String xfs="";
		if (null==xfid||xfid.length==0) {
			
		}else{
			for (String id : xfid) {
				xfs+=id+",";
			}
			xfs=xfs.substring(0,xfs.length()-1);
		}
		fpgz.setXfids(xfs);
		fpgz.setYxbz("1");
		fpgz.setLrry(getYhid());
		fpgz.setXgry(getYhid());
		fpgz.setGsdm(getGsdm());
		fpgz.setLrsj(new Date());
		fpgz.setXgsj(new Date());
		fpgzService.save(fpgz);
		result.put("success", true);
		result.put("msg", "新增规则成功");
		return result;
	}
	//新增
	@RequestMapping(value = "/scgz")
	@ResponseBody
	public Map<String, Object> scgz(Integer id,String[] xfid){
		Map<String, Object> result = new HashMap<String, Object>();
		Fpgz fpgz = fpgzService.findOne(id);
		fpgz.setYxbz("0");
		fpgzService.save(fpgz);
		result.put("msg", "删除成功");
		return result;
	}
	
	//修改
	@RequestMapping(value = "/xggz")
	@ResponseBody
	public Map<String, Object> xgbcgz(Fpgz fpgz,String[] xfid,Integer idd){
		Map<String, Object> result = new HashMap<String, Object>();
		Fpgz fpgz2 = fpgzService.findOne(idd);
		String xfs="";
		if (null==xfid||xfid.length==0) {
			
		}else{
			for (String id : xfid) {
				xfs+=id+",";
			}
			xfs=xfs.substring(0,xfs.length()-1);
		}
		fpgz.setId(idd);
		fpgz.setYxbz("1");
		fpgz.setLrry(fpgz2.getLrry());
		fpgz.setXgry(getYhid());
		fpgz.setLrsj(fpgz2.getLrsj());
		fpgz.setGsdm(fpgz2.getGsdm());
		fpgz.setXfids(xfs);
		fpgz.setXgry(getYhid());
		fpgz.setXgsj(new Date());
		fpgzService.save(fpgz);
		result.put("msg", "修改成功");
		return result;
	}

}
