package com.rjxx.taxeasy.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.domains.Gfxx;
import com.rjxx.taxeasy.domains.Qympk;
import com.rjxx.taxeasy.service.GfxxService;
import com.rjxx.taxeasy.service.QympkService;
import com.rjxx.taxeasy.web.BaseController;
import com.rjxx.utils.ChinaInitial;

@Controller
@RequestMapping("/qympk")
public class QympkController extends BaseController{

	@Autowired
	private GfxxService gfxxservice;
	@Autowired
	private QympkService qympkService;
	
	@RequestMapping
	public String index() {
		return "qympk/index";
	}
	
	/**
	 * 初始化显示列表
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getQympkList")
	@ResponseBody
	public Map getQympkList(int length, int start, int draw) throws Exception {
		
		Map<String, Object> result = new HashMap<String, Object>();
		Pagination pagination = new Pagination();
		pagination.setPageNo(start / length + 1);
		pagination.setPageSize(length);
		
		String searchtext = request.getParameter("searchtext");

		pagination.addParam("text", searchtext);
		List<Qympk> list = qympkService.findByPage(pagination);
		
		int total = pagination.getTotalRecord();
		
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		result.put("draw", draw);
		result.put("data", list);
		return result;
	}
	/**
	 * 初始化显示列表
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDetailList")
	@ResponseBody
	public Map getDetailList(int length, int start, int draw) throws Exception {
		
		Map<String, Object> result = new HashMap<String, Object>();
		Pagination pagination = new Pagination();
		pagination.setPageNo(start / length + 1);
		pagination.setPageSize(length);
		String searchtext = request.getParameter("searchtext");
		String nsrsbh = request.getParameter("nsrsbh");
		pagination.addParam("nsrsbh", nsrsbh);
		pagination.addParam("text", searchtext);
		List<Qympk> list = qympkService.findByPage(pagination);
		
		int total = pagination.getTotalRecord();
		
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		result.put("draw", draw);
		result.put("data", list);
		return result;
	}
	
	
	/**
	 * 保存购方企业信息
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveGfxx", method = RequestMethod.POST)
	@ResponseBody
	public Map saveGfxx() {
		Map<String, Object> result = new HashMap<String, Object>();
		String gfmc = request.getParameter("gfmc");
		String gfsh = request.getParameter("gfsh");
		String gfdz = request.getParameter("gfdz");
		String gfdh = request.getParameter("gfdh");
		String gfyh = request.getParameter("gfyh");
		String gfyhzh = request.getParameter("gfyhzh");
		String lxr = request.getParameter("lxr");
		String lxdh = request.getParameter("lxdh");
		String yjdz = request.getParameter("yjdz");
		
		Map mapStr=new HashMap();
		mapStr.put("nsrsbh", gfsh);
		// 校验数据 未完成
		try {
		   List<Qympk> qympklist=	qympkService.findAllByParams(mapStr);
			List<Gfxx> gfxxlist=new ArrayList();
			for(int i=0;i<qympklist.size();i++){
				Qympk qympk1=qympklist.get(i);
				
				ChinaInitial chinain = new ChinaInitial();
				String mcszmsx = chinain.getPYIndexStr(qympk1.getDwmc(), false);//第二个参数代表是否大小写，ture大写，false小写。
				Gfxx gfxx = new Gfxx();
				gfxx.setGfsh(qympk1.getNsrsbh());
				gfxx.setGfmc(qympk1.getDwmc());
				gfxx.setGfdz(qympk1.getZcdz());
				gfxx.setGfdh(qympk1.getZcdh());
				gfxx.setGfyh(qympk1.getKhyh());
				gfxx.setGfyhzh(qympk1.getYhzh());
				gfxx.setMcszmsx(mcszmsx);
				gfxx.setYxbz("1");
				gfxx.setLrsj(new Date());
				gfxx.setLrry(1);
				gfxx.setXgsj(new Date());
				gfxx.setXgry(1);
				gfxx.setGsdm(getGsdm());
				
				gfxx.setLxr(qympk1.getLxr());
				gfxx.setLxdh(qympk1.getLxdh());
				gfxx.setYjdz(qympk1.getYjdz());
				gfxx.setEmail(qympk1.getEmail());
				gfxxlist.add(gfxx);
			}
			// 购房名称生成首字母缩写
			
			
			gfxxservice.save(gfxxlist);
			result.put("success", true);
			result.put("msg", "保存成功");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.put("success", false);
			result.put("msg", "保存出现错误: " + ex.getMessage());
		}
		return result;
	}
}
