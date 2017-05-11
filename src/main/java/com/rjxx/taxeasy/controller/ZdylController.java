package com.rjxx.taxeasy.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjxx.taxeasy.domains.YhCxzdyl;
import com.rjxx.taxeasy.domains.YhDczdyl;
import com.rjxx.taxeasy.service.YhCxzdylService;
import com.rjxx.taxeasy.service.YhDczdylService;
import com.rjxx.taxeasy.vo.DczydlVo;
import com.rjxx.taxeasy.vo.ZdylVo;
import com.rjxx.taxeasy.web.BaseController;

@Controller
@RequestMapping("zdyl")
public class ZdylController extends BaseController {
	@Autowired
	YhCxzdylService yhCxzdylService;
	@Autowired
	YhDczdylService yhdczdylService;

	@RequestMapping("save")
	@ResponseBody
	public Map<String, Object> SaveColumn() {
		Map<String, Object> result = new HashMap<>();
		String[] columns = request.getParameterValues("column");
		Map<String, Object> params = new HashMap<>();
		params.put("yhid", getYhid());
		List<ZdylVo> list = yhCxzdylService.findAllByParams(params);
		for (ZdylVo yhCxzdyl1 : list) {
			YhCxzdyl y = new YhCxzdyl();
			y.setId(yhCxzdyl1.getId());
			y.setYhid(yhCxzdyl1.getYhid());
			y.setDygn(yhCxzdyl1.getDygn());
			y.setZddm(yhCxzdyl1.getZddm());
			y.setYxbz("0");
			y.setLrsj(yhCxzdyl1.getLrsj());
			y.setXgsj(new Date());
			y.setLrry(yhCxzdyl1.getLrry());
			y.setXgry(getYhid());
			yhCxzdylService.save(y);
		}
		if (null != columns) {
			for (String column : columns) {
				YhCxzdyl yhCxzdyl = new YhCxzdyl();
				yhCxzdyl.setYhid(getYhid());
				yhCxzdyl.setDygn("发票查询");
				yhCxzdyl.setZddm(column);
				yhCxzdyl.setYxbz("1");
				yhCxzdyl.setLrsj(new Date());
				yhCxzdyl.setXgsj(new Date());
				yhCxzdyl.setLrry(getYhid());
				yhCxzdyl.setXgry(getYhid());
				yhCxzdylService.save(yhCxzdyl);
			}
		}

		result.put("msg", "保存成功!");
		return result;
	}

	@RequestMapping("query")
	@ResponseBody
	public Map<String, Object> queryColumns() {
		Map<String, Object> result = new HashMap<>();
		Map<String, Object> params = new HashMap<>();
		params.put("yhid", getYhid());
		List<ZdylVo> list = yhCxzdylService.findAllByParams(params);
		// 拼接标题
		String bt = "";
		List<String> da = new ArrayList<>();
		for (ZdylVo yhCxzdyl : list) {
			if ("skpid".equals(yhCxzdyl.getZddm())) {
				da.add("kpdmc");
			} else {
				da.add(yhCxzdyl.getZddm());
			}
			bt += "<th>" + yhCxzdyl.getZdzwm() + "</th>";
		}
		result.put("bt", bt);
		result.put("da", da);
		result.put("list", list);
		return result;
	}

	@RequestMapping("saveOut")
	@ResponseBody
	public Map<String, Object> saveOut() {
		Map<String, Object> result = new HashMap<>();
		String[] columns = request.getParameterValues("column");
		Map<String, Object> params = new HashMap<>();
		params.put("yhid", getYhid());
		List<DczydlVo> list = yhdczdylService.findAllByParams(params);
		for (DczydlVo yhDczdyl : list) {
			YhDczdyl y = new YhDczdyl();
			y.setId(yhDczdyl.getId());
			y.setYhid(yhDczdyl.getYhid());
			y.setDygn(yhDczdyl.getDygn());
			y.setZddm(yhDczdyl.getZddm());
			y.setYxbz("0");
			y.setLrsj(yhDczdyl.getLrsj());
			y.setXgsj(new Date());
			y.setLrry(yhDczdyl.getLrry());
			y.setXgry(getYhid());
			yhdczdylService.save(y);
		}
		if (null != columns) {
			for (String string : columns) {
				YhDczdyl yhDczdyl = new YhDczdyl();
				yhDczdyl.setYhid(getYhid());
				yhDczdyl.setDygn("发票查询导出");
				yhDczdyl.setZddm(string);
				yhDczdyl.setYxbz("1");
				yhDczdyl.setLrsj(new Date());
				yhDczdyl.setXgsj(new Date());
				yhDczdyl.setLrry(getYhid());
				yhDczdyl.setXgry(getYhid());
				yhdczdylService.save(yhDczdyl);
			}
		}
		result.put("msg", "保存成功!");
		return result;
	}

	@RequestMapping("queryOut")
	@ResponseBody
	public Map<String, Object> queryOut() {
		Map<String, Object> result = new HashMap<>();
		Map<String, Object> params = new HashMap<>();
		params.put("yhid", getYhid());
		List<DczydlVo> list = yhdczdylService.findAllByParams(params);
		result.put("list", list);
		return result;
	}
}
