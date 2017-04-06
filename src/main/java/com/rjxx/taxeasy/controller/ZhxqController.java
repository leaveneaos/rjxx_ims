package com.rjxx.taxeasy.controller;

import java.util.Date;
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
		map.put("id", getYhid());
		Gsxx gsxx=gsxxservice.findOneByParams(map);
		YhVO yh=yhservice.findOneByYhVo(map);
		XfVo xfvo= xfservice.findAllByXfxx(map);
		SkpVo skpvo=skpservice.findXfSkpNum(map);
		Fpcxvo fpcxvo=kplsservice.findykpCount(map);
		YhVO yhvo = yhservice.findAllByYHCount(map);
		String zhlxmc=null;
		if(yh==null){
			zhlxmc="免费测试用户";
		}else{
			zhlxmc=yh.getZhlxmc();
		}
		Date yxqsrq=null;
		if(gsxx.getYxqsrq()==null){
			yxqsrq=new Date();
		}else{
			yxqsrq=gsxx.getYxqsrq();
		}
		Date yxjzrq=null;
		if(gsxx.getYxjzrq()==null){
			yxjzrq=new Date();
		}else{
			yxjzrq=gsxx.getYxjzrq();
		}
		Integer xfnum=0;
		if(gsxx.getXfnum()==null){
			xfnum=0;
		}else{
			xfnum=gsxx.getXfnum();
		}
		Integer yhnum=null;
		if(gsxx.getYhnum()==null){
			yhnum=0;
		}else{
			yhnum=gsxx.getYhnum();
		}
		Integer kpnum=null;
		if(gsxx.getKpnum()==null){
			kpnum=0;
		}else{
			kpnum=gsxx.getKpnum();
		}
		Integer kpdnum=null;
		if(gsxx.getKpdnum()==null){
			kpdnum=0;
		}else{
			kpdnum=gsxx.getKpdnum();
		}
		request.setAttribute("yhcount", yhvo.getYhcount());//已开票数
		request.setAttribute("kpcount", fpcxvo.getKpcount());//已开票数
		request.setAttribute("skpcount", skpvo.getSkpcount());//已使用税控盘数
		request.setAttribute("XfCount", xfvo.getCount());//已使用税号数
		request.setAttribute("zhlxmc", zhlxmc);//账户类型名称
		request.setAttribute("yxqsrq", yxqsrq);//有效起始日期
		request.setAttribute("yxjzrq", yxjzrq);//有效截止日期
		request.setAttribute("xfnum", xfnum);//税号数量
		request.setAttribute("yhnum", yhnum);//用户数量
		request.setAttribute("kpnum", kpnum);//开票数量
		request.setAttribute("kpdnum",kpdnum);//税控设备
		return "zhxq/index";
	}
}
