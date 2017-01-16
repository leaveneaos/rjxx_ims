package com.rjxx.taxeasy.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjxx.taxeasy.bizcomm.utils.SendalEmail;
import com.rjxx.taxeasy.domains.Gszc;
import com.rjxx.taxeasy.domains.Pp;
import com.rjxx.taxeasy.domains.Tqlj;
import com.rjxx.taxeasy.domains.Xxts;
import com.rjxx.taxeasy.service.GszcService;
import com.rjxx.taxeasy.service.PpService;
import com.rjxx.taxeasy.service.TqjlService;
import com.rjxx.taxeasy.service.TqljService;
import com.rjxx.taxeasy.service.XxtsService;
import com.rjxx.taxeasy.web.BaseController;

@Controller
@RequestMapping("gszc")
public class GszcController extends BaseController {
	@Autowired
	GszcService gszcService;
	@Autowired
	XxtsService xxtsservice;
	@Autowired
	SendalEmail sendalEmail;
	@Autowired
	TqljService tqljService;
	@Autowired
	PpService ppService;
	@RequestMapping("save")
	@ResponseBody
	public Map<String, Object> zcyz(String gsmc, String lxr, String zw, String lxdh, String sscs, String code) {
		Map<String, Object> result = new HashMap<String, Object>();
		String sessionCode = (String) session.getAttribute("rand");
		if (code != null && sessionCode != null && code.equals(sessionCode)) {
			Map<String, Object> params = new HashMap<>();
			params.put("gsmc", gsmc);
			Gszc gszc1 = gszcService.findOneByParams(params);
			if (null != gszc1 && null != gszc1.getId()) {
				result.put("msg", "该公司已经注册,请等待!");
				return result;
			}
			Gszc gszc = new Gszc();
			gszc.setGsmc(gsmc);
			gszc.setLxr(lxr);
			gszc.setZw(zw);
			gszc.setLxdh(lxdh);
			gszc.setSscs(sscs);
			gszc.setLrsj(new Date());
			gszcService.save(gszc);
			result.put("msg", "注册成功");
			List<Xxts> list = xxtsservice.findAllByParams(params);
			for (Xxts xxts : list) {
				sendalEmail.sendEmail("", "", xxts.getYx(), "公司注册", "", gsmc+" 刚刚在官网上成功注册了,请尽快与其取得联系!", "客户注册");
			}
			return result;
		}else{
			result.put("msg", "验证码不正确");
			return result;
		}
	}
	//获取要显示的链接图标品牌
	@RequestMapping("getTqlj")
	@ResponseBody
		public Map<String, Object> getTqlj(){
			Map<String, Object> result = new HashMap<String, Object>();
			List<Tqlj> list = tqljService.findAllByParams(new HashMap<>());
			result.put("list", list);
			return result;
		}
	//获取要显示的链接图标品牌
	@RequestMapping("getLj")
	@ResponseBody
		public Map<String, Object> getLj(Integer id){
			Map<String, Object> result = new HashMap<String, Object>();
			Tqlj tqlj = tqljService.findOne(id);
			result.put("tqlj", tqlj);
			return result;
		}
	
}
