package com.rjxx.taxeasy.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.domains.Jyxx;
import com.rjxx.taxeasy.service.JyxxService;
import com.rjxx.taxeasy.service.XtLogService;
import com.rjxx.taxeasy.vo.XtLogVo;
import com.rjxx.taxeasy.web.BaseController;

@Controller
@RequestMapping("/xtrzcx")
public class SystemLogController extends BaseController {

	@Autowired
	private XtLogService xtLogService;

	@RequestMapping
	public String index() {
		return "xtrzcx/index";
	}

	/**
	 * 初始化显示列表
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getXtrzList")
	@ResponseBody
	public Map getKplsList(int length, int start, int draw, String ddh, String kpddm, String ddrqq, String ddrqz)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Pagination pagination = new Pagination();
		pagination.setPageNo(start / length + 1);
		pagination.setPageSize(length);
		String gsdm = getGsdm() ;
		pagination.addParam("gsdm", gsdm);
		List<XtLogVo> list = xtLogService.findByPage(pagination);
		int total = pagination.getTotalRecord();
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		result.put("draw", draw);
		result.put("data", list);

		return result;
	}
}
