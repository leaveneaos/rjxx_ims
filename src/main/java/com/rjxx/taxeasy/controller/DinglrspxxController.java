package com.rjxx.taxeasy.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rjxx.taxeasy.domains.Jyxxsq;
import com.rjxx.taxeasy.service.SpvoService;
import com.rjxx.taxeasy.vo.Spvo;
import com.rjxx.taxeasy.web.BaseController;

@Controller
@RequestMapping("/dinglrspxx")
public class DinglrspxxController extends BaseController{
	
	@Autowired
	private SpvoService spvoService;
	
	@RequestMapping
    public String index() throws Exception {
		String corpid=request.getParameter("corpid");//企业id
		String xfmc=request.getParameter("xfmc");//销方名称
		String kprq=request.getParameter("kprq");//开票日期
		String fpzldm=request.getParameter("fpzldm");//发票种类
		String bz=request.getParameter("bz");//备注
		String ddh=request.getParameter("ddh");//订单号
		
		String gfmc=request.getParameter("gfmc");//购方名称
		String nsrsbh=request.getParameter("nsrsbh");//纳税人识别号
		String zcdz=request.getParameter("zcdz");//注册地址
		String zcdh=request.getParameter("zcdh");//注册电话
		String khyh=request.getParameter("khyh");//开户银行
		String yhzh=request.getParameter("yhzh");//银行账号
		
		String lxr=request.getParameter("lxr");//联系人
		String lxdh=request.getParameter("lxdh");//联系电话
		String lxdz=request.getParameter("lxdz");//联系地址
		String yjdz=request.getParameter("yjdz");//邮寄地址
		String tqm=request.getParameter("tqm");//提取码
		Jyxxsq Jyxxsq=new Jyxxsq();
		Jyxxsq.setBz(bz);
		Jyxxsq.setDdh(ddh);
		SimpleDateFormat sdf=new SimpleDateFormat();
		Jyxxsq.setDdrq(sdf.parse(kprq));
		Jyxxsq.setFpzldm(fpzldm);
		Jyxxsq.setGfdh(zcdh);
		Jyxxsq.setGfdz(zcdz);
		Jyxxsq.setGflxr(lxr);
		Jyxxsq.setGfmc(gfmc);
		Jyxxsq.setGfsh(nsrsbh);
		Jyxxsq.setGfyh(khyh);
		Jyxxsq.setGfyhzh(yhzh);
		Jyxxsq.setTqm(tqm);
		Jyxxsq.setSjly("3");
		/*Jyxxsq.setXfmc(xfmc);
		Jyxxsq.setXfdh(zcdh);
		Jyxxsq.setXfdz(zcdz);
		Jyxxsq.setXfyh(khyh);
		Jyxxsq.setXfyhzh(yhzh);
		Jyxxsq.setXfsh(nsrsbh);*/
		List<Spvo>list2 = spvoService.findAllByGsdm("zydc");
		request.setAttribute("spList", list2);
        request.setAttribute("corpid", corpid);
        request.setAttribute("Jyxxsq", Jyxxsq);
        return "dingding/lrspxx";
    }
}
