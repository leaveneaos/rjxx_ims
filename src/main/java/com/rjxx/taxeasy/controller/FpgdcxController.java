package com.rjxx.taxeasy.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.bizcomm.utils.FileCopyAndCompass;
import com.rjxx.taxeasy.domains.Gdjl;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.service.GdjlService;
import com.rjxx.taxeasy.service.KplsService;
import com.rjxx.taxeasy.vo.Fpcxvo;
import com.rjxx.taxeasy.vo.Gdjlvo;
import com.rjxx.taxeasy.web.BaseController;

@Controller
@RequestMapping("/fpgdcx")
public class FpgdcxController extends BaseController {
	@Autowired
	private KplsService kplsService;
	@Autowired
	private GdjlService gdService;

	@RequestMapping
	public String index() {
		List<Xf> xfs = getXfList();
		request.setAttribute("xfs", xfs);
		return "fpgdcx/index";
	}

	@RequestMapping(value = "/getFPList")
	@ResponseBody
	public Map<String, Object> getItems(int length, int start, int draw) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Pagination pagination = new Pagination();
		pagination.setPageNo(start / length + 1);
		pagination.setPageSize(length);
		pagination.addParam("gsdm", this.getGsdm());
		List<Gdjlvo> list = gdService.findByPage(pagination);
		int total = pagination.getTotalRecord();
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		result.put("draw", draw);
		result.put("data", list);
		return result;
	}

	// pdf打包
	@Transactional
	@RequestMapping(value = "/compass")
	@ResponseBody
	public Map<String, Object> fileCompass(Integer xfid, String kprqq, String kprqz) throws Exception {
		Map<String, Object> result = new HashMap<>();
		result.put("success", true);
		result.put("msg", "");
		Map params = new HashMap<>();
		params.put("xfid", xfid);
		params.put("gsdm", this.getGsdm());
		params.put("kprqq", kprqq);
		params.put("kprqz", kprqz);
		List<Fpcxvo> list = kplsService.fpgdcxdb(params);
		if (list != null && list.size() > 0) {
			Gdjl tm = new Gdjl();
			tm.setGsdm(this.getGsdm());
			tm.setQsrq(kprqq);
			tm.setZzrq(kprqz);
			tm.setZt("0");
			tm.setXfid(xfid);
			tm.setYxbz("1");
			tm.setLrry(this.getYhid());
			tm.setLrsj(new Date());
			gdService.save(tm);
			Integer id = tm.getId();
			FileCopyAndCompass fc = new FileCopyAndCompass(id, list);
			Thread t = new Thread(fc);
			t.start();
		} else {
			result.put("success", false);
			result.put("msg", "设置条件下没有发票！");
			return result;
		}
		return result;
	}

}
