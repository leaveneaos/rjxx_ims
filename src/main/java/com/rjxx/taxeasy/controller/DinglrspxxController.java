package com.rjxx.taxeasy.controller;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    	String userid=request.getParameter("userid");//钉钉用户id
		String xfmc=URLDecoder.decode(request.getParameter("xfmc"),"utf8");//销方名称
		String xfid=request.getParameter("xfid");//销方id
		String kprq=request.getParameter("kprq");//开票日期
		String fpzldm=request.getParameter("fpzldm");//发票种类
		String bz=URLDecoder.decode(request.getParameter("bz"),"utf8");//备注
		String ddh=request.getParameter("ddh");//订单号
		
		String gfmc=URLDecoder.decode(request.getParameter("gfmc"),"utf8");//购方名称
		String nsrsbh=request.getParameter("nsrsbh");//纳税人识别号
		String zcdz=URLDecoder.decode(request.getParameter("zcdz"),"utf8");//注册地址
		String zcdh=request.getParameter("zcdh");//注册电话
		String khyh=URLDecoder.decode(request.getParameter("khyh"),"utf8");//开户银行
		String yhzh=request.getParameter("yhzh");//银行账号
		
		String lxr=URLDecoder.decode(request.getParameter("lxr"),"utf8");//联系人
		String lxdh=request.getParameter("lxdh");//联系电话
		String lxdz=URLDecoder.decode(request.getParameter("lxdz"),"utf8");//联系地址
		String yjdz=URLDecoder.decode(request.getParameter("yjdz"),"utf8");//邮寄地址
		String tqm=request.getParameter("tqm");//提取码
		
		List<Spvo>list2 = spvoService.findAllByGsdm("zydc");
		String jyxxsq="&xfmc="+xfmc+"&xfid="+(xfid)+"&kprq="+kprq+"&fpzldm="+fpzldm+"&bz="+bz+"&ddh="+
		ddh+"&gfmc="+gfmc+"&nsrsbh="+nsrsbh+"&zcdz="+zcdz+"&zcdh="+zcdh+"&khyh="+khyh+
		"&yhzh="+yhzh+"&lxr="+lxr+"&lxdh="+lxdh+"&lxdz="+lxdz+"&yjdz="+yjdz+"&tqm="+tqm;
		
		request.setCharacterEncoding("utf-8");
		request.setAttribute("jyxxsq", jyxxsq);
		System.out.println(jyxxsq);
		request.setAttribute("spList", list2);
        request.setAttribute("corpid", corpid);
        request.setAttribute("userid", userid);
        return "dingding/lrspxx";
    }
	/**
	 * 获取商品详情
	 *
	 * @param spdm
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSpxq")
	@ResponseBody
	public Spvo getSpxq(String spdm, String spmc) throws Exception {
		Spvo params = new Spvo();
		params.setGsdm("zydc");
		//params.setSpdm(spdm);
		//使用商品编码查询
		params.setSpbm(spdm);
		params.setSpmc(spmc);
		List<Spvo> list = spvoService.findAllByParams(params);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
}
