package com.rjxx.taxeasy.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rjxx.taxeasy.domains.Gsxx;
import com.rjxx.taxeasy.domains.Yh;
import com.rjxx.taxeasy.service.GsxxService;
import com.rjxx.taxeasy.service.KplsService;
import com.rjxx.taxeasy.service.SkpService;
import com.rjxx.taxeasy.service.XfService;
import com.rjxx.taxeasy.service.YhService;
import com.rjxx.taxeasy.vo.Fpcxvo;
import com.rjxx.taxeasy.vo.KplsVO;
import com.rjxx.taxeasy.vo.SkpVo;
import com.rjxx.taxeasy.vo.XfVo;
import com.rjxx.taxeasy.vo.YhVO;
import com.rjxx.taxeasy.web.BaseController;

@Controller
@RequestMapping("/zhxq")
public class ZhxqController extends BaseController{
	
	
	@Autowired
    private GsxxService gsxxservice;  
    @Autowired
    private SkpService skpservice;
	@Autowired
    private YhService yhservice;
	@Autowired
    private XfService xfservice;
	@Autowired
    private KplsService kplsservice;
	
	@RequestMapping
	public String index() {
		Map map =new HashMap<>();
		map.put("gsdm", getGsdm());
		Gsxx gsxx=gsxxservice.findOneByParams(map);
		YhVO yh=yhservice.findOneByYhVo(map);
		XfVo xfvo= xfservice.findAllByXfxx(map);
		SkpVo skpvo=skpservice.findXfSkpNum(map);
		Fpcxvo fpcxvo=kplsservice.findykpCount(map);
		YhVO yhvo = yhservice.findAllByYHCount(map);
		
		request.setAttribute("yhcount", yhvo.getYhcount());//已开票数
		request.setAttribute("kpcount", fpcxvo.getKpcount());//已开票数
		request.setAttribute("skpcount", skpvo.getSkpcount());//已使用税控盘数
		request.setAttribute("XfCount", xfvo.getCount());//已使用税号数
		request.setAttribute("zhlxmc", yh.getZhlxmc());//账户类型名称
		request.setAttribute("yxqsrq", gsxx.getYxqsrq());//有效起始日期
		request.setAttribute("yxjzrq", gsxx.getYxjzrq());//有效截止日期
		request.setAttribute("xfnum", gsxx.getXfnum());//税号数量
		request.setAttribute("yhnum", gsxx.getYhnum());//用户数量
		request.setAttribute("kpnum", gsxx.getKpnum());//开票数量
		request.setAttribute("kpdnum",gsxx.getKpdnum());//税控设备
		return "zhxq/index";
	}
}
