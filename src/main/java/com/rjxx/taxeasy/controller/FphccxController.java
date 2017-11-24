package com.rjxx.taxeasy.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjxx.taxeasy.service.KplsService;
import com.rjxx.taxeasy.vo.Fpcxvo;
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
	private KplsService kplsService;
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
			String kprqq, String kprqz,String fpzl,String fphm,boolean loaddata) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		/*Pagination pagination = new Pagination();
		pagination.setPageNo(start / length + 1);
		pagination.setPageSize(length);*/
		Map params = new HashMap();
		if(loaddata){
			params.put("gsdm", getGsdm());
			List<Integer> skps = new ArrayList<>();
			List<Xf> xfs = getXfList();
			String xfStr = "";
			if (xfs != null) {
				for (int i = 0; i < xfs.size(); i++) {
					int xfid2 = xfs.get(i).getId();
					if (i == xfs.size() - 1) {
						xfStr += xfid2 + "";
					} else {
						xfStr += xfid2 + ",";
					}
				}
			}
			String[] xfid2 = xfStr.split(",");
			if (xfid2.length == 0) {
				xfid2 = null;
			}
			params.put("xfid", xfid2);
			String skpStr = "";
			List<Skp> skpList = getSkpList();
			if (skpList != null) {
				for (int j = 0; j < skpList.size(); j++) {
					int skpid2 = skpList.get(j).getId();
					if (j == skpList.size() - 1) {
						skpStr += skpid2 + "";
					} else {
						skpStr += skpid2 + ",";
					}
				}
			}
			String[] skpid2 = skpStr.split(",");
			if (skpid2.length == 0) {
				skpid2 = null;
			}
			params.put("skpid", skpid2);
			params.put("xfid2",xfid);
			params.put("sk",skpid);
			params.put("start",start);
			params.put("length",length);
			params.put("fphm",fphm);
			params.put("ddh", ddh);
			params.put("gfmc", gfmc);
			if (!"".equals(kprqq)) {
				params.put("kprqq2", kprqq);
			}
			if (!"".equals(kprqz)) {
				params.put("kprqz2", kprqz);
			}
			params.put("fpczlx", "12");
			params.put("fpzldm", fpzl);
			List<Fpcxvo> list = kplsService.findByPage2(params);
			int total;
			if(0 == start){
				total = kplsService.findTotal(params);
				request.getSession().setAttribute("total",total);
			}else{
				total = (Integer) request.getSession().getAttribute("total");
			}
			//int total = pagination.getTotalRecord();
			result.put("recordsTotal", total);
			result.put("recordsFiltered", total);
			result.put("draw", draw);
			result.put("data", list);
			result.put("recordsTotal", total);
			result.put("recordsFiltered", total);
			result.put("draw", draw);
			result.put("data", list);
		}else {
			result.put("recordsTotal", 0);
			result.put("recordsFiltered", 0);
			result.put("draw", draw);
			result.put("data", new ArrayList<>());
		}

		return result;
	}
}
