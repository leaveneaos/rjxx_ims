package com.rjxx.taxeasy.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.domains.Jyls;
import com.rjxx.taxeasy.domains.Kpls;
import com.rjxx.taxeasy.domains.Privileges;
import com.rjxx.taxeasy.domains.Skp;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.domains.Yh;
import com.rjxx.taxeasy.service.FpzlService;
import com.rjxx.taxeasy.service.JylsService;
import com.rjxx.taxeasy.service.JyxxsqService;
import com.rjxx.taxeasy.service.PrivilegesService;
import com.rjxx.taxeasy.service.YhService;
import com.rjxx.taxeasy.service.YhcljlService;
import com.rjxx.taxeasy.vo.JyxxsqVO;
import com.rjxx.taxeasy.vo.Yhcljlvo;
import com.rjxx.taxeasy.web.BaseController;


@Controller
@RequestMapping("/dbsx")
public class DbsxController extends BaseController{

	@Autowired
    private YhService yhService;
	@Autowired
    private PrivilegesService privilegesService;
	@Autowired
	private YhcljlService cljlService;
	@Autowired
	private JyxxsqService jyxxService;
	@Autowired
	private JylsService jylsService;
	
	@RequestMapping
	public String index() {
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
        List<Integer> list = new ArrayList<Integer>();
        for(Privileges item:privilegesList){
        	Integer id = item.getId();
        	list.add(id);
        }
        if(list.size()>0){
        	if(list.contains(44)){//录入开票单的权限
        		request.setAttribute("lrkpd", 1);
        	}
        	if(list.contains(41)){//开票单审核的权限
        		request.setAttribute("kpdsh", 1);
        	}
        	if(list.contains(4)){//发票开具
        		request.setAttribute("fpkj", 1);
        	}
        	if(list.contains(5)){//发票红冲
        		request.setAttribute("fphc", 1);
        	}
        	if(list.contains(6)){//发票换开
        		request.setAttribute("fphk", 1);
        	}
        	if(list.contains(40)){//发票作废
        		request.setAttribute("fpzf", 1);
        	}
        	if(list.contains(50)){//发票重开权限
        		request.setAttribute("fpck", 1);
        	}
        	if(list.contains(51)){//发票重打权限
        		request.setAttribute("fpcd", 1);
        	}
        	if(list.contains(7)){//发票发送权限
        		request.setAttribute("fpfs", 1);
        	}
        	if(list.contains(52)){//发票邮寄权限
        		request.setAttribute("fpyj", 1);
        	}
        }
        //是否有代办的查询
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
		if(list1 !=null && list1.size()>0){
			request.setAttribute("lrkpddb",1);
		}else{
			request.setAttribute("lrkpddb",0);
		}
		pagination.addParam("ztbz", "0");
		List<JyxxsqVO> list2 = jyxxService.findByPage(pagination);//开票单审核的代办
		if(list2 !=null && list2.size()>0){
			request.setAttribute("kpdshdb",1);
		}else{
			request.setAttribute("kpdshdb",0);
		}
		pagination.addParam("clztdm", "00");
		pagination.addParam("fpczlxdm", "11");
		List<Jyls> list3 = jylsService.findByPage(pagination);
		if(list1 !=null && list3.size()>0){
			request.setAttribute("fpkjdb", 1);
		}else{
			request.setAttribute("fpkjdb", 0);
		}
		return "dbsx/index";
	}
	
	@RequestMapping(value = "/getPlot")
	@ResponseBody
	public Map<String,Object> getPlot(){
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		Integer yhid = this.getYhid();
		Map params = new HashMap<>();
		params.put("yhid", yhid);
		List<Yhcljlvo> list = cljlService.findYhcljl(params);
		if(list !=null && list.size()>0){
			for(Yhcljlvo item:list){
				result.put(item.getClrq(), item.getYbsl());
			}
		}else{
			SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
			Date date=new Date();
			String time = dateFormater.format(date);
			result.put(time, 0);
		}		
		return result;
	}
	
	
	
	

}
