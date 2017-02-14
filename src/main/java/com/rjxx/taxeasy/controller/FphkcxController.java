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
import com.rjxx.taxeasy.domains.Fpzl;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.service.FpzlService;
import com.rjxx.taxeasy.service.KplsvoService;
import com.rjxx.taxeasy.vo.KplsVO;
import com.rjxx.taxeasy.web.BaseController;

@Controller
@RequestMapping("/fphkcx")
public class FphkcxController extends BaseController {

	@Autowired
	private KplsvoService kvs;
	@Autowired
	private FpzlService fpzlService;

	@RequestMapping
	public String index() {
		List<Fpzl> fpzlList = fpzlService.findAllByParams(new HashMap<>());
		request.setAttribute("fpzlList", fpzlList);
		return "fphkcx/index";
	}

	/**
	 * 初始化显示列表
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getKplsList")
	@ResponseBody
	public Map getKplsList(int length, int start, int draw, String ddh, String fphm, 
			String kprqq,String kprqz,String fpzl) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Pagination pagination = new Pagination();
		pagination.setPageNo(start / length + 1);
		pagination.setPageSize(length);
		List<Integer> xfs = new ArrayList<>();
		if (!getXfList().isEmpty()) {
			for (Xf xf : getXfList()) {
				xfs.add(xf.getId());
			}
		}
		if (xfs.size() > 0) {
			pagination.addParam("xfList", xfs);
		}
		pagination.addParam("skps", getSkpList());
		pagination.addParam("gsdm", getGsdm());
		pagination.addParam("ddh", ddh);
		pagination.addParam("fphm", fphm);
		if (!"".equals(kprqq)) {
			pagination.addParam("kprqq", kprqq);
		}
		if (!"".equals(kprqz)) {
			pagination.addParam("kprqz", kprqz);
		}

		pagination.addParam("fpczlxdm", "13");
		pagination.addParam("fpzl", fpzl);
		List<KplsVO> list = kvs.findByPage(pagination);
		int total = pagination.getTotalRecord();
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		result.put("draw", draw);
		result.put("data", list);

		return result;
	}
}
