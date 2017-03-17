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

import com.rjxx.taxeasy.domains.DyWddy;
import com.rjxx.taxeasy.domains.DyYhlxfs;
import com.rjxx.taxeasy.domains.Yh;
import com.rjxx.taxeasy.service.DyWddyService;
import com.rjxx.taxeasy.service.DyYhlxfsService;
import com.rjxx.taxeasy.service.DyfsService;
import com.rjxx.taxeasy.service.YhService;
import com.rjxx.taxeasy.vo.Dyvo;
import com.rjxx.taxeasy.web.BaseController;


@Controller
@RequestMapping("/yhdy")
public class YhdyController extends BaseController{
	
	@Autowired
	private DyfsService dyfsService;
	@Autowired
	private YhService yhService;
	@Autowired
	private DyWddyService wddyService;
	@Autowired
	private DyYhlxfsService yhlxfsService;
	@RequestMapping
	public String index() {
		Map params = new HashMap<>();
		params.put("dylx","1");
		List<Dyvo> dyzlList = dyfsService.findDyzl(params);
		request.setAttribute("dyzlList", dyzlList);
		List<Dyvo> dyfsList = dyfsService.findDyfs(params);
		request.setAttribute("dyfsList", dyfsList);		
		return "yhdy/index";
	}
	
	@RequestMapping(value = "/getwddy")
	@ResponseBody
	public Map<String,Object> getWddy(){
		Map<String,Object> result = new HashMap<String,Object>();
		Integer yhid = this.getYhid();
		Map params = new HashMap<>();
		params.put("yhid", yhid);
		List<DyWddy> list = wddyService.findAllByParams(params);
		if(list !=null&&list.size()>0){
			int i = 0;
			for(DyWddy item:list){
				String dyzldm = item.getDyzldm();
				String dyfsdm = item.getDyfsdm();
				result.put(i+"", dyzldm+"-"+dyfsdm);
				i++;
			}
		}
		return result;
	}
	@RequestMapping(value = "/getyhxx")
	@ResponseBody
	public Map<String,Object> getYhxx(){
		Map<String,Object> result = new HashMap<String,Object>();
		Map params = new HashMap<>();
		params.put("yhid", getYhid());
		DyYhlxfs yhlxfs = yhlxfsService.findOneByParams(params);
		if(yhlxfs !=null){
			String name = yhlxfs.getXm();
			String sjhm = yhlxfs.getSjhm();
			String email = yhlxfs.getYx();
			String openid = yhlxfs.getWxOpenid();			
			result.put("yhmc", name);
			result.put("sjhm", sjhm);
			result.put("email", email);
			result.put("openid", openid);
			result.put("success", true);
		}else{
			Yh yh = yhService.findOneByParams(params);
			if(yh !=null){
				String name = yh.getYhmc();
				String sjhm = yh.getSjhm();
				String email = yh.getYx();
				result.put("yhmc", name);
				result.put("sjhm", sjhm);
				result.put("email", email);
				result.put("openid", "");
				result.put("success", true);
			}else{
				result.put("msg", "session超时，请重新登录！");
				result.put("success", false);
			}
		}				
		return result;
	}
	
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String,Object> save(String dyfsstr,String yhmc,String sjhm,String email){
		Map<String,Object> result = new HashMap<String,Object>();
		Integer yhid = this.getYhid();
		Map params = new HashMap<>();
		params.put("yhid", yhid);
		wddyService.updateYxbz(params);
		String[] dyfs = dyfsstr.split(",");
		List<DyWddy> list = new ArrayList<DyWddy>();		
		String gsdm = this.getGsdm();
		try{
			for(int i=0;i<dyfs.length;i++){
				String[] fs = dyfs[i].split("-");
				DyWddy item = new DyWddy();
				item.setYhid(yhid);
				item.setGsdm(gsdm);
				item.setDyzldm(fs[0]);
				item.setDyfsdm(fs[1]);
				item.setLrry(yhid);
				item.setLrsj(new Date());
				item.setYxbz("1");
				list.add(item);
			}
			wddyService.save(list);
			DyYhlxfs lxfs = yhlxfsService.findOneByParams(params);
			if(lxfs==null){
				DyYhlxfs item = new DyYhlxfs();
				item.setYhid(yhid);
				item.setSjhm(sjhm);
				item.setYx(email);
				item.setXm(yhmc);
				item.setYxbz("1");
				item.setLrry(yhid);
				item.setLrsj(new Date());
				yhlxfsService.save(item);
			}else{
				lxfs.setSjhm(sjhm);
				lxfs.setXm(yhmc);
				lxfs.setYx(email);
				lxfs.setLrsj(new Date());
				yhlxfsService.save(lxfs);
			}			
			result.put("success", true);
			result.put("msg", "保存成功！");
		}catch(Exception e){
			result.put("success", false);
			result.put("msg", "保存失败，请检查！");
		}		
		return result;
	}

}
