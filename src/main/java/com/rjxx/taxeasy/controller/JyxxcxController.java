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
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.domains.Jyxx;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.service.JyxxService;
import com.rjxx.taxeasy.service.KplsvoService;
import com.rjxx.taxeasy.vo.KplsVO;
import com.rjxx.taxeasy.web.BaseController;

@Controller
@RequestMapping("/jyxxcx")
public class JyxxcxController extends BaseController {

	@Autowired
	private JyxxService jys;

	@RequestMapping
	public String index() {
		return "jyxxcx/index";
	}

	/**
	 * 初始化显示列表
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getJyxxList")
	@ResponseBody
	public Map getKplsList(int length, int start, int draw, String ddh, String kpddm, String ddrqq, String ddrqz)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Pagination pagination = new Pagination();
		pagination.setPageNo(start / length + 1);
		pagination.setPageSize(length);
		String gsdm = getGsdm() ;
		pagination.addParam("gsdm", gsdm);
		pagination.addParam("ddh", ddh);
		pagination.addParam("kpddm", kpddm);
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd");
        SimpleDateFormat for2 = new SimpleDateFormat ("yyyyMMdd"); 
		if (!"".equals(ddrqq)) {
			Date kprqt = new Date();
			kprqt =formatter.parse(ddrqq);
			ddrqq = for2.format(kprqt);
			pagination.addParam("ddrqq", ddrqq);
		}
		if (!"".equals(ddrqz)) {
			Date kprqt = new Date();
			kprqt =formatter.parse(ddrqz);
			ddrqz = for2.format(kprqt);
			pagination.addParam("ddrqz", ddrqz);
		}
		//pagination.addParam("fpczlxdm", "12");
		List<Jyxx> list = jys.findByPage(pagination);
		int total = pagination.getTotalRecord();
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		result.put("draw", draw);
		result.put("data", list);

		return result;
	}
}
