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

import com.rjxx.taxeasy.domains.Mkgl;
import com.rjxx.taxeasy.domains.Mkpz;
import com.rjxx.taxeasy.service.MkglService;
import com.rjxx.taxeasy.service.MkpzService;
import com.rjxx.taxeasy.vo.Mkvo;
import com.rjxx.taxeasy.web.BaseController;

@Controller
@RequestMapping("/main")
public class MkglController extends BaseController {

	@Autowired
	private MkglService mkglService;

	@Autowired
	private MkpzService mkpzService;

	@RequestMapping
	public String index() throws Exception {
		int yhid = getYhid();
		// 区块内容下拉
		List<Mkpz> mkpzList = new ArrayList<>();
		mkpzList = mkpzService.findAll();
		request.setAttribute("mkpzList", mkpzList);
		Map params = new HashMap<>();
		params.put("yhid", yhid);
		List<Mkvo> mkvoList = mkglService.findAllConnect(params);
		if (mkvoList == null || mkvoList.size() == 0) {
			List<Mkpz> mkvoList1 = mkpzService.findAllByParams(params);
			if (mkvoList1 != null) {
				List<Mkgl> mkglList = new ArrayList<Mkgl>();
				for (int i = 0; i < mkvoList1.size(); i++) {
					Mkpz mkpz = mkvoList1.get(i);
					Mkgl item = new Mkgl();
					item.setYhid(yhid);
					item.setPzid(mkpz.getId());
					item.setMkmc(mkpz.getMkmc());
					item.setMkbl(mkpz.getMkbl());
					item.setYxbz("1");
					item.setLrry(yhid);
					mkglList.add(item);
				}
				mkglService.save(mkglList);
			}
		}
		params.put("yxbz", "1");
		mkvoList = mkglService.findAllConnect(params);
		request.setAttribute("mkglList", mkvoList);
		return "main/index";
	}

	// 保存方法
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save(Mkgl item) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		int yhid = getYhid();
		Map params = new HashMap<>();
		params.put("yhid", yhid);
		params.put("pzid", item.getPzid());
		Mkgl mk = mkglService.findQkExist(params);
		if (mk != null) {
			result.put("success", false);
			result.put("msg", "区块内容首页已经展示，请重新选择！");
			return result;
		}
		item.setYhid(yhid);
		item.setLrry(yhid);
		item.setYxbz("1");
		item.setLrsj(new Date());
		item.setSort(10);
		result.put("success", true);
		result.put("msg", "保存成功！");
		try {
			mkglService.save(item);
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "保存失败，" + e.getMessage());
		}
		return result;
	}

	// 更新方法
	@RequestMapping(value = "/update")
	@ResponseBody
	public Map<String, Object> save(int id, String pzid, String mkmc, String mkbl) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		int yhid = getYhid();
		Map params = new HashMap<>();
		params.put("id", id);
		params.put("pzid", pzid);
		params.put("mkmc", mkmc);
		params.put("mkbl", mkbl);
		params.put("yhid", yhid);
		Mkgl mk = mkglService.findQkExistUpdate(params);
		if (mk != null) {
			result.put("success", false);
			result.put("msg", "区块内容首页已经展示，请重新选择！");
			return result;
		}
		result.put("success", true);
		result.put("msg", "修改成功！");
		try {
			mkglService.update(params);
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "修改失败，" + e.getMessage());
		}
		return result;
	}

	// 删除方法
	@RequestMapping(value = "/destory")
	@ResponseBody
	public Map<String, Object> destory(int id) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map params = new HashMap<>();
		params.put("id", id);
		result.put("success", true);
		result.put("msg", "删除成功！");
		try {
			mkglService.destory(params);
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "删除失败，" + e.getMessage());
		}
		return result;
	}

	// 更新div的顺序方法
	@RequestMapping(value = "/updateSort")
	@ResponseBody
	public void updateSort(String idStr) throws Exception {
		Map params = new HashMap<>();
		String ids[] = idStr.split(",");
		try {
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i].replace("-fdiv", "");
				params.put("i", i);
				params.put("id", id);
				mkglService.updateSort(params);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
