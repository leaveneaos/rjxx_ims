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
		/*boolean flag1 = false;
		boolean flag2 = false;
		boolean flag3 = false;
		Pagination pagination = new Pagination();
        String gsdm = this.getGsdm();
        pagination.addParam("gsdm", gsdm);
		List<Xf> xfs = getXfList();
		if(xfs !=null && xfs.size()>0){
			pagination.addParam("xfs", xfs);
		}
		List<Skp> skps = getSkpList();
		if(skps !=null && skps.size()>0){
			pagination.addParam("skps", skps);
		}
		pagination.addParam("ztbz", "2");
		List<JyxxsqVO> list1 = jyxxService.findByPage(pagination);//录入开票单的代办
		pagination.addParam("ztbz", "0");
		List<JyxxsqVO> list2 = jyxxService.findByPage(pagination);//开票单审核的代办
		pagination.addParam("clztdm", "00");
		pagination.addParam("fpczlxdm", "11");
		List<Jyls> list3 = jylsService.findByPage(pagination);
		if(list1 != null && list1.size()>0){
			flag1 = true;
		}
		if(list2 != null && list2.size()>0){
			flag2 = true;
		}
		if(list3 != null&& list3.size()>0){
			flag3 = true;
		}
		if(flag1||flag2||flag3){
			request.setAttribute("dbsl", 1);
		}else{
			request.setAttribute("dbsl", 0);
		}*/
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
