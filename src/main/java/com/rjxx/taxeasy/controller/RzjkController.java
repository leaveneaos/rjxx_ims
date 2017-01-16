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
import com.rjxx.taxeasy.service.DzfplogvoService;
import com.rjxx.taxeasy.vo.DzfpLogVO;

@Controller
@RequestMapping("/rzjk")
public class RzjkController {
	@Autowired
	private DzfplogvoService dls;

	@RequestMapping
	public String index() {
		return "rzjk/index";
	}

	/**
	 * 初始化显示列表
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getKplsList")
	@ResponseBody
	public Map getLogList(int length, int start, int draw, String jylsh, String ddh, String lrsjq, String lrsjz)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Pagination pagination = new Pagination();
		pagination.setPageNo(start / length + 1);
		pagination.setPageSize(length);
		String cond = "1=1";
		pagination.addParam("ddh", ddh);
		pagination.addParam("jylsh", jylsh);
		pagination.addParam("lrsjq", lrsjq);
		pagination.addParam("lrsjz", lrsjz);
		List<DzfpLogVO> logLists = dls.findByPage(pagination);
		int total = pagination.getTotalRecord();
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		result.put("draw", draw);
		result.put("data", logLists);
		return result;
	}
}
