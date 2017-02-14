package com.rjxx.taxeasy.controller;

import java.text.SimpleDateFormat;
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
import com.rjxx.taxeasy.domains.Jyxx;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.service.GfxxService;
import com.rjxx.taxeasy.service.JyxxService;
import com.rjxx.taxeasy.service.KplsvoService;
import com.rjxx.taxeasy.vo.KplsVO;
import com.rjxx.taxeasy.web.BaseController;
import com.rjxx.utils.ChinaInitial;

@Controller
@RequestMapping("/gfqymp")
public class GfqympController extends BaseController {

	@Autowired
	private GfxxService gfxxservice;

	@RequestMapping
	public String index() {
		return "gfqymp/index";
	}

	/**
	 * 初始化显示列表
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getGfxxList")
	@ResponseBody
	public Map getGfxxList(int length, int start, int draw) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Pagination pagination = new Pagination();
		pagination.setPageNo(start / length + 1);
		pagination.setPageSize(length);
		String gsdm = getGsdm();

		// pagination.addParam("fpczlxdm", "12");
		List<Gfxx> list = gfxxservice.findByPage(pagination);
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

		// 校验数据 未完成
		try {
			// 购房名称生成首字母缩写
			ChinaInitial chinain = new ChinaInitial();
			String mcszmsx = chinain.getPYIndexStr(gfmc, false);//第二个参数代表是否大小写，ture大写，false小写。
			Gfxx gfxx = new Gfxx();
			gfxx.setGfsh(gfsh);
			gfxx.setGfmc(gfmc);
			gfxx.setGfdz(gfdz);
			gfxx.setGfdh(gfdh);
			gfxx.setGfyh(gfyh);
			gfxx.setGfyhzh(gfyhzh);
			gfxx.setMcszmsx(mcszmsx);
			gfxx.setYxbz("1");
			gfxx.setLrsj(new Date());
			gfxx.setLrry(1);
			gfxx.setXgsj(new Date());
			gfxx.setXgry(1);
			gfxxservice.save(gfxx);
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
