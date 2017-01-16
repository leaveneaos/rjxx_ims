package com.rjxx.taxeasy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjxx.taxeasy.service.JylsService;
import com.rjxx.taxeasy.vo.FpxxVo;
import com.rjxx.taxeasy.web.BaseController;

@Controller
@RequestMapping("/dzfpcx")
public class DzfpcxController extends BaseController {

	@Autowired
	private JylsService jylsService;

	@RequestMapping(value = "/fpcx")
	@ResponseBody
	public Map fpcx(String ddh, String jshj) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<>();
		params.put("ddh", ddh);
		params.put("jshj", jshj);
		List<FpxxVo> list = jylsService.findFpxx(params);
		if (!list.isEmpty()) {
			result.put("success", true);
			result.put("data", list.get(0));
		} else {
			result.put("success", false);
			result.put("message", "没有该电子发票信息");
		}

		return result;
	}
}
