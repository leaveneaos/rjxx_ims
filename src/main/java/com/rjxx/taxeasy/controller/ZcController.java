package com.rjxx.taxeasy.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin
public class ZcController extends BaseController {
	@Autowired
	private YanzhengmaService yanzhengmaService;
	@Autowired
	private ZclogService zclogService;
	
	@RequestMapping(value = "/getYzm", method={RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public Map<String, Object> getYzm(String phone){
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> map = new HashMap<>();
		if("".equals(phone) || phone ==null){
			result.put("data","");
			result.put("code","0");
			result.put("msg","手机号不能是空");
			return result;
		}
		map.put("phone",phone);
		//判断是否获取过验证码
		Yanzhengma yzm=yanzhengmaService.findBySql(map);

		if(yzm!=null){
			result.put("data",yzm.getCode());
			result.put("code","1");
			result.put("msg","success");
			yzm.setHqnum(yzm.getHqnum()==null?0:(yzm.getHqnum()+1));
			yzm.setXgsj(new Date());
			yanzhengmaService.save(yzm);
			return  result;
		}else{
			//生成验证码
			String code = (int) (Math.random() * 900000 + 100000)+"";
			map.put("code", code);
			map.put("product", "容津信息");
			String requestId = SendMessage.sendSms("泰易电子发票", "SMS_33950398", map, phone);
			if("sendFail".equals(requestId)) {
				result.put("data", "");
				result.put("code","0");
				result.put("msg", "验证码获取失败,稍后重试。");
				return result;
			}else {
				//验证码入库
				Yanzhengma yanzhengma= new Yanzhengma();
				yanzhengma.setPhone(phone);
				yanzhengma.setCode(code);
				yanzhengma.setLrsj(new Date());
				yanzhengma.setRequestid(requestId);
				yanzhengma.setHqnum(1);
				yanzhengma.setYxbz("1");
				yanzhengmaService.save(yanzhengma);
				result.put("code","1");
				result.put("msg","success");
				return  result;
			}
		}
	}
	@RequestMapping("/zhuce")
	@ResponseBody
	public Map<String, Object> zhuce(String phone,String code){
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
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
	
	@RequestMapping(value = "/ljty",method={RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public Map<String, Object> ljty(String phone,String code){
		Map<String, Object> result = new HashMap<String, Object>();
		if(phone==null || "".equals(phone)){
			result.put("code", "0");
			result.put("msg", "手机号不能为空");
			return result;
		}
		if(code==null || "".equals(code)){
			result.put("code", "0");
			result.put("msg", "验证码为空");
			return result;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("phone",phone);
		Yanzhengma yzm=yanzhengmaService.findBySql(map);
		if(yzm==null){
			result.put("code", "0");
			result.put("msg", "手机号未注册");
		}else if(code.equals(yzm.getCode())){
			result.put("code", "1");
			result.put("msg", "验证通过");
			yzm.setTynum(yzm.getTynum()==null?0:(yzm.getTynum()+1));
			yzm.setXgsj(new Date());
			yanzhengmaService.save(yzm);
		}else{
			result.put("code", "0");
			result.put("msg", "验证码错误");
		}
		return result;
	}
}
