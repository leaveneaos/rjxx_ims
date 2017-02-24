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

import com.rjxx.taxeasy.domains.Jyxxsq;
import com.rjxx.taxeasy.domains.Kpls;
import com.rjxx.taxeasy.domains.Privileges;
import com.rjxx.taxeasy.domains.Skp;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.domains.Yh;
import com.rjxx.taxeasy.domains.Yhcljl;
import com.rjxx.taxeasy.service.FpzlService;
import com.rjxx.taxeasy.service.PrivilegesService;
import com.rjxx.taxeasy.service.YhService;
import com.rjxx.taxeasy.service.YhcljlService;
import com.rjxx.taxeasy.vo.Yhcljlvo;
import com.rjxx.taxeasy.web.BaseController;


@Controller
@RequestMapping("/dbsx")
public class DbsxController extends BaseController{
	
	@Autowired
	private FpzlService fpzlService;
	@Autowired
    private YhService yhService;
	@Autowired
    private PrivilegesService privilegesService;
	@Autowired
	private YhcljlService cljlService;
	
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
		if(list1 !=null && list1.size()>0){
			request.setAttribute("lrkpddb","...");
		}else{
			request.setAttribute("lrkpddb","");
		}
		param.put("ztbz", "0");
		List<Jyxxsq> list2 = fpzlService.findDbsx(param);//开票单审核的代办
		if(list2 !=null && list2.size()>0){
			request.setAttribute("kpdshdb","...");
		}else{
			request.setAttribute("kpdshdb","");
		}
		Kpls kpls = fpzlService.findDkpsj(param);
		if(kpls !=null){
			request.setAttribute("fpkjdb", "...");
		}else{
			request.setAttribute("fpkjdb", "");
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
	
	/**
	 * ylmc:用例名称
	 * 
	 * */
	public void saveYhcljl(Integer yhid,String ylmc){
		Yhcljl item = new Yhcljl();
		item.setClrq(new Date());
		item.setYhid(yhid);
		item.setYlmc(ylmc);
		item.setLrsj(new Date());
		item.setLrry(yhid);
		cljlService.save(item);
	}

}
