package com.rjxx.taxeasy.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.domains.Dyfs;
import com.rjxx.taxeasy.domains.Dyzl;
import com.rjxx.taxeasy.domains.Yhdyk;
import com.rjxx.taxeasy.service.DyfsService;
import com.rjxx.taxeasy.service.DyzlService;
import com.rjxx.taxeasy.service.YhdykService;
import com.rjxx.taxeasy.vo.Yhdykvo;
import com.rjxx.taxeasy.web.BaseController;


@Controller
@RequestMapping("/mydy")
public class DyglController extends BaseController{
	@Autowired
	private DyzlService dyzlService;
	@Autowired
	private YhdykService dykService;
	@Autowired
	private DyfsService dyfsService;
	
	@RequestMapping
	public String index() {
		List<Dyzl> dyzlList = dyzlService.findAllByParams(new HashMap<>());
		request.setAttribute("dybtList", dyzlList);
		List<Dyfs> dyfsList = dyfsService.findAllByParams(new HashMap<>());
		request.setAttribute("dyfsList", dyfsList);
		return "mydy/index";
	}
	
	@RequestMapping(value = "/getBz")
	@ResponseBody
	public Map<String,Object> getBz(Integer id){
		Map<String,Object> result = new HashMap<String,Object>();
		if(id!=null){
			Dyzl item = dyzlService.findOne(id);
			if(item==null){
				result.put("bz", "");			
			}else{
				String bz = item.getBz();
				result.put("bz", bz);
			}
		}else{
			result.put("bz", "");
		}		
		return result;
	}
	
	@RequestMapping(value = "/getItems")
	@ResponseBody
	public Map<String,Object> getItems(int length, int start, int draw,String btmc){
		Map<String,Object> result = new HashMap<String,Object>();
		Pagination pagination = new Pagination();
		pagination.setPageNo(start / length + 1);
		pagination.setPageSize(length);
		int yhid = getYhid();
		pagination.addParam("yhid", yhid);
		pagination.addParam("btmc", btmc);
		List<Yhdykvo> list = dykService.findByPage(pagination);
		if(list !=null&&list.size()>0){			
			for(Yhdykvo item:list){
				String dyfsk = "";
				if(item.getDyfs()!=null&&!"".equals(item.getDyfs())){
					String[] dyfs = item.getDyfs().split(",");					
					for(int i=0;i<dyfs.length;i++){
						Map params = new HashMap<>();
						params.put("dyfsdm", dyfs[i]);
						Dyfs model = dyfsService.findOneByParams(params);
						String dyfsmc = model.getDyfsmc();
						if(i==dyfs.length-1){
							dyfsk += dyfsmc;
						}else{
							dyfsk += dyfsmc+"，";
						}
					}
					item.setDyfsmc(dyfsk);
				}				
			}
		}
		int total = pagination.getTotalRecord();
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		result.put("draw", draw);
		result.put("data", list);
		return result;
	}
	
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String,Object> save(Integer dybtid,String dyfs,String sjhm,String email,String openid) throws Exception{
		Map<String,Object> result = new HashMap<String,Object>();
		int yhid = getYhid();
		Map params = new HashMap<>();
		params.put("yhid", yhid);
		params.put("dybtid", dybtid);
		Yhdyk item = dykService.findDyxx(params);
		if(item !=null){
			result.put("success", false);
			result.put("msg", "该标题您已经订阅！");
		}else{
			Yhdyk yhdy = new Yhdyk();
			yhdy.setDybtid(dybtid);
			yhdy.setDyfs(dyfs);
			yhdy.setYxbz("1");
			yhdy.setYhid(yhid);
			yhdy.setLrsj(new Date());
			yhdy.setSjhm(sjhm);
			yhdy.setEmail(email);
			yhdy.setOpenid(openid);
			dykService.save(yhdy);
			result.put("success", true);
			result.put("msg", "保存成功！");
		}
		return result;		
	}
	
	@RequestMapping(value = "/update")
	@ResponseBody
	public Map<String,Object> update(Integer id,Integer dybtid,String dyfs){
		Map<String,Object> result = new HashMap<String,Object>();
		int yhid = getYhid();
		Map params = new HashMap<>();
		params.put("yhid", yhid);
		params.put("dybtid", dybtid);
		params.put("id",id);
		Yhdyk item = dykService.findDyxx(params);
		if(item !=null){
			result.put("success", false);
			result.put("msg", "该标题您已经订阅！");
		}else{
			Yhdyk yhdy = new Yhdyk();
			yhdy.setId(id);
			yhdy.setDybtid(dybtid);
			yhdy.setDyfs(dyfs);
			yhdy.setYxbz("1");
			yhdy.setYhid(yhid);
			yhdy.setLrsj(new Date());
			dykService.save(yhdy);
			result.put("success", true);
			result.put("msg", "保存成功！");
		}
		return result;		
	}
	
	@RequestMapping(value = "/del")
	@ResponseBody
	public Map<String,Object> delete(Integer id){
		Map<String,Object> result = new HashMap<String,Object>();
		Map params = new HashMap<>();
		params.put("id", id);
		try{
			dykService.updateYxbz(params);
			result.put("success", true);
			result.put("msg", "删除成功！");
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", "删除失败，请检查！");
		}		
		return result;
	}

}
