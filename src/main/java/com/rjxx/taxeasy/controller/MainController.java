package com.rjxx.taxeasy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjxx.taxeasy.domains.Jyxxsq;
import com.rjxx.taxeasy.domains.Kpls;
import com.rjxx.taxeasy.domains.PrivilegeTypes;
import com.rjxx.taxeasy.domains.Skp;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.service.FpzlService;
import com.rjxx.taxeasy.service.PrivilegeTypesService;
import com.rjxx.taxeasy.web.BaseController;


@Controller
@RequestMapping("/mainjsp")
public class MainController extends BaseController{
	
	@Autowired
	private FpzlService fpzlService;
	@Autowired
	private PrivilegeTypesService ptypeService;
	@RequestMapping
	public String index() throws Exception{
		boolean flag1 = false;
		boolean flag2 = false;
		boolean flag3 = false;
		Map param = new HashMap<>();
        String gsdm = this.getGsdm();
		param.put("gsdm", gsdm);
		List<Xf> xfs = getXfList();
		if(xfs !=null && xfs.size()>0){
			param.put("xfs", xfs);
		}
		List<Skp> skps = getSkpList();
		if(skps !=null && skps.size()>0){
			param.put("skps", skps);
		}
		param.put("ztbz", "2");
		List<Jyxxsq> list1 = fpzlService.findDbsx(param);//录入开票单的代办
		param.put("ztbz", "0");
		List<Jyxxsq> list2 = fpzlService.findDbsx(param);//开票单审核的代办
		Kpls kpls = fpzlService.findDkpsj(param);
		if(list1 != null && list1.size()>0){
			flag1 = true;
		}
		if(list2 != null && list2.size()>0){
			flag2 = true;
		}
		if(kpls != null){
			flag3 = true;
		}
		if(flag1||flag2||flag3){
			request.setAttribute("dbsl", "...");
		}else{
			request.setAttribute("dbsl", "");
		}
		
		return "mainjsp/index";
	}
	
	@RequestMapping(value = "/getName")
	@ResponseBody
	public Map<String,Object> getName(String url) throws Exception{
		Map<String,Object> result = new HashMap<String,Object>();
		Map params = new HashMap();
		params.put("url", url);
		PrivilegeTypes item = ptypeService.findPriviName(params);
		String name = "业务处理";
		if(item!=null){
			name = item.getName();
		}
		result.put("name", name);
		return result;
	}

}
