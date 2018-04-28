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
import com.rjxx.taxeasy.domains.Jyls;
import com.rjxx.taxeasy.domains.Jyxxsq;
import com.rjxx.taxeasy.domains.Kpls;
import com.rjxx.taxeasy.domains.PrivilegeTypes;
import com.rjxx.taxeasy.domains.Privileges;
import com.rjxx.taxeasy.domains.Skp;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.domains.Yh;
import com.rjxx.taxeasy.service.FpzlService;
import com.rjxx.taxeasy.service.JylsService;
import com.rjxx.taxeasy.service.JyxxsqService;
import com.rjxx.taxeasy.service.PrivilegeTypesService;
import com.rjxx.taxeasy.service.PrivilegesService;
import com.rjxx.taxeasy.service.YhService;
import com.rjxx.taxeasy.vo.JyxxsqVO;
import com.rjxx.taxeasy.web.BaseController;


@Controller
@RequestMapping("/mainjsp")
public class MainController extends BaseController{
	
	@Autowired
    private YhService yhService;
	@Autowired
	private PrivilegeTypesService ptypeService;
	@Autowired
    private PrivilegesService privilegesService;
	
	@RequestMapping
	public String index() throws Exception{

		Map params = new HashMap<>();
		Integer yhid = this.getYhid();
		params.put("yhid", yhid);
		Yh yh = yhService.findOneByParams(params);
		String roleIds = yh.getRoleids();
		List<Integer> paramsList = new ArrayList<>();
        String[] arr = roleIds.split(",");
        for (String str : arr) {
            paramsList.add(Integer.valueOf(str));
        }
        params.put("roleIds", paramsList);
        List<Privileges> privilegesList = privilegesService.findByRoleIds(params);
        List<String> list = new ArrayList<String>();
        for(Privileges item:privilegesList){
        	String url = item.getUrls();
        	list.add(url);
        }
        if(list !=null && list.size()>0){
        	if(list.contains("/kpdshxb")){
        		request.setAttribute("kplscl", 1);
        	}
        	if(list.contains("/fpcx")){
        		request.setAttribute("fpcx", 1);
        	}
        	if(list.contains("/fytjbb")){
        		request.setAttribute("ytjbb", 1);
        	}
        	if(list.contains("/fpgdcx")){
        		request.setAttribute("fpgd", 1);
        	}
			if(list.contains("/sgkj")){
				request.setAttribute("sgkj", 1);
			}
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
