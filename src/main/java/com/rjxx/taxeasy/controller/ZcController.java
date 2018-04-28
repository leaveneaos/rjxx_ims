package com.rjxx.taxeasy.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.rjxx.taxeasy.domains.Yanzhengma;
import com.rjxx.taxeasy.domains.Zclog;
import com.rjxx.taxeasy.service.YanzhengmaService;
import com.rjxx.taxeasy.service.ZclogService;
import com.rjxx.taxeasy.web.BaseController;
import com.rjxx.utils.SendMessage;

@Controller
@RequestMapping("zc")
public class ZcController extends BaseController {
	@Autowired
	private YanzhengmaService yanzhengmaService;
	@Autowired
	private ZclogService zclogService;
	
	@RequestMapping("/getYzm")
	@ResponseBody
	public Map<String, Object> getYzm(String phone){
		Map<String, Object> result = new HashMap<String, Object>();
		String code = (int) (Math.random() * 900000 + 100000)+"";
		Map<String, String> map = new HashMap<>();
		map.put("code", code);
		map.put("product", "容津信息");
		String requestId = SendMessage.sendSms("泰易电子发票", "SMS_33950398", map, phone);
		if("sendFail".equals(requestId)) {
			result.put("success", false);
			result.put("msg", "验证码获取失败,稍后重试。");
		}else {
			//验证码入库
			Yanzhengma yanzhengma= new Yanzhengma();
			yanzhengma.setPhone(phone);
			yanzhengma.setCode(code);
			yanzhengma.setLrsj(new Date());
			yanzhengma.setRequestid(requestId);
			yanzhengmaService.save(yanzhengma);
			result.put("success", true);
			result.put("msg", "收到验证后，请输入。");
		}
		
		return result;
	}
	@RequestMapping("/zhuce")
	@ResponseBody
	public Map<String, Object> zhuce(String phone,String code){
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<>();
		params.put("phone", phone);
		params.put("code", code);
		Yanzhengma yanzm = yanzhengmaService.findBySql(params);
		if(yanzm!=null) {
			result.put("success", true);
			//记录一条数据
			Zclog zclog = zclogService.findOneBySql(params);
			if(zclog==null) {
				Zclog zclog2 = new Zclog();
				zclog2.setPhone(phone);
				zclog2.setZcnum(1);
				zclogService.save(zclog2);
			}else {
				Map<String, Object> map = new HashMap<>();
				map.put("id", zclog.getId());
				map.put("zcnum", zclog.getZcnum()+1);
				zclogService.updateZcnum(map);
			}
			
			
		}else {
			result.put("success", false);
			result.put("msg", "验证码错误或过期");
		}
		return result;
	}
	
	@RequestMapping("/zcmain")
	public ModelAndView tiyanPage(String phone) {
		ModelAndView mav = new ModelAndView("tiyan/zcmain");
		mav.addObject("phone", phone);
		return mav;
	}
	
	@RequestMapping("/ljty")
	@ResponseBody
	public Map<String, Object> ljty(String phone){
		Map<String, Object> result = new HashMap<String, Object>();
		if(phone!=null && !"".equals(phone)) {
			result.put("issuc", true);
			Map<String, Object> params = new HashMap<>();
			params.put("phone", phone);
			Zclog zclog = zclogService.findOneBySql(params);
			if(zclog==null) {
				result.put("issuc", false);
			}else {
				Map<String, Object> map = new HashMap<>();
				map.put("id", zclog.getId());
				map.put("tynum", zclog.getTynum()==null?1:zclog.getTynum()+1);
				zclogService.updateTynum(map);
			}
			
		}else {
			result.put("issuc", false);
		}
		return result;
	}
}
