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
import com.rjxx.taxeasy.domains.Skp;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.service.FpzlService;
import com.rjxx.taxeasy.service.KplsvoService;
import com.rjxx.taxeasy.vo.KplsVO;
import com.rjxx.taxeasy.web.BaseController;

@Controller
@RequestMapping("/fphccx")
public class FphccxController extends BaseController {

	@Autowired
	private KplsvoService kvs;
	@Autowired
	private FpzlService fpzlService;

	@RequestMapping
	public String index() {
		List<Fpzl> fpzlList = fpzlService.findAllByParams(new HashMap<>());
		request.setAttribute("fpzlList", fpzlList);
		List<Xf> xfList = getXfList();
		request.setAttribute("xfList", xfList);
		List<Skp> skpList = getSkpList();
		request.setAttribute("skpList", skpList);
		return "fphccx/index";
	}

	/**
	 * 初始化显示列表
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getKplsList")
	@ResponseBody
	public Map getKplsList(int length, int start, int draw, Integer xfid,Integer skpid,String ddh, String gfmc, 
			String kprqq, String kprqz,String fpzl) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Pagination pagination = new Pagination();
		pagination.setPageNo(start / length + 1);
		pagination.setPageSize(length);
		pagination.addParam("gsdm", getGsdm());
		List<Integer> xfs = new ArrayList<>();
		List<Integer> skps = new ArrayList<>();
		if (!getXfList().isEmpty()) {
			for (Xf xf : getXfList()) {
				xfs.add(xf.getId());
			}
		}
		if (xfs.size() > 0) {
			pagination.addParam("xfList", xfs);
		}
		if (!getSkpList().isEmpty()) {
			for (Skp skp : getSkpList()) {
				skps.add(skp.getId());
			}
		}
		if (skps.size() > 0) {
			pagination.addParam("skpList", skps);
		}
		pagination.addParam("xfid", xfid);
		pagination.addParam("skpid", skpid);
		pagination.addParam("ddh", ddh);
		pagination.addParam("gfmc", gfmc);
		if (!"".equals(kprqq)) {
			pagination.addParam("kprqq", kprqq);
		}
		if (!"".equals(kprqz)) {
			pagination.addParam("kprqz", kprqz);
		}
		pagination.addParam("fpczlxdm", "12");
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
