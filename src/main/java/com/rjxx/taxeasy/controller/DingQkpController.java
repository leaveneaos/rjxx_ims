package com.rjxx.taxeasy.controller;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

import com.dingtalk.oapi.lib.aes.DingTalkJsApiSingnature;
import com.dingtalk.oapi.lib.aes.Utils;
import com.rjxx.taxeasy.domains.*;
import com.rjxx.taxeasy.service.*;
import com.rjxx.time.TimeUtil;
import com.rjxx.utils.Tools;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rjxx.taxeasy.configuration.Message;
import com.rjxx.taxeasy.dingding.Helper.HttpHelper;
import com.rjxx.taxeasy.web.BaseController;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/dingqkp")
public class DingQkpController extends BaseController{
	
	@Autowired
	private IsvCorpSuiteJsapiTicketService isvcorpsuitejsapiticketservice;
	@Autowired
	private IsvCorpAppService isvcorpappservice;
	@Autowired
	private JyxxsqService jyxxsqservice;
	@Autowired
	private SkpService skpservice;
	@Autowired
	private XfService xfService;

	@RequestMapping
    public String index() throws Exception {
		String corpid=request.getParameter("corpid");//企业id
		String userid=request.getParameter("userid");//钉钉用户id
		String xfmc= URLDecoder.decode(request.getParameter("xfmc"),"utf8");//销方名称
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

		com.rjxx.taxeasy.domains.Xf xf = xfService.findOne(Integer.parseInt(xfid));
		Jyxxsq jyxxsq=new Jyxxsq();
		int yhid = getYhid();
		jyxxsq.setDingcorpid(corpid);
		jyxxsq.setDinguserid(userid);
		jyxxsq.setBz(bz);
		jyxxsq.setDdh(ddh);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		if(("").equals(kprq)&&null==kprq){
		}else{
		jyxxsq.setDdrq(sdf.parse(kprq));
		}
		jyxxsq.setFpzldm(fpzldm);
		jyxxsq.setGfdh(zcdh);
		jyxxsq.setGfdz(zcdz);
		jyxxsq.setGflxr(lxr);
		jyxxsq.setGfmc(gfmc);
		jyxxsq.setGfsh(nsrsbh);
		jyxxsq.setGfyh(khyh);
		jyxxsq.setGfyhzh(yhzh);
		jyxxsq.setTqm(tqm);
		jyxxsq.setSjly("3");
		jyxxsq.setClztdm("00");
		jyxxsq.setZtbz("6");//0未提交，1已提交
		jyxxsq.setJylsh("JY" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
		jyxxsq.setJshj(0.00);
		jyxxsq.setYkpjshj(0.00);
		jyxxsq.setHsbz("0");



		jyxxsq.setXfid(xf.getId());
		jyxxsq.setXfsh(xf.getXfsh());
		jyxxsq.setXfmc(xf.getXfmc());
		jyxxsq.setLrsj(TimeUtil.getNowDate());
		jyxxsq.setXgsj(TimeUtil.getNowDate());
		jyxxsq.setDdrq(TimeUtil.getNowDate());
		jyxxsq.setFpczlxdm("11");
		jyxxsq.setYxbz("1");
		jyxxsq.setSsyf(new SimpleDateFormat("yyyyMM").format(new Date()));
		jyxxsq.setXfyh(xf.getXfyh());
		jyxxsq.setXfyhzh(xf.getXfyhzh());
		jyxxsq.setXfdz(xf.getXfdz());
		jyxxsq.setXfdh(xf.getXfdh());
		jyxxsq.setKpr(xf.getKpr());
		jyxxsq.setFhr(xf.getFhr());
		jyxxsq.setSkr(xf.getSkr());
		List<Skp> skpList = getSkpList();
		jyxxsq.setSkpid(skpList.get(0).getId());
		jyxxsq.setGsdm("zydc");
		Skp skp = skpservice.findOne(skpList.get(0).getId());
		jyxxsq.setKpddm(skp.getKpddm());

		if (StringUtils.isNotBlank(jyxxsq.getGfemail())) {
			jyxxsq.setSffsyj("1");
		}


		jyxxsq.setLrry(yhid);
		jyxxsq.setXgry(yhid);
		jyxxsq.setGsdm(getGsdm());


		Map params = Tools.getParameterMap(request);
		int mxcount = Integer.valueOf(request.getParameter("mxcount"));
		String[] spdms = ((String) params.get("spdm")).split(",");
		String[] spmcs = ((String) params.get("spmc")).split(",");
		String[] spjes = ((String) params.get("je")).split(",");
		String[] spsls = ((String) params.get("sl")).split(",");//商品税率
		String[] jshjs = ((String) params.get("hsje")).split(",");
		String[] spges = ((String) params.get("ggxh")).split(",");
		String[] spss = ((String) params.get("spsl")).split(",");//商品数量
		String[] spdws = ((String) params.get("spdw")).split(",");
		String[] spdjs = ((String) params.get("spdj")).split(",");
		String[] spses = ((String) params.get("se")).split(",");
		double jshj = 0.00;
		List<Jymxsq> jymxsqList = new ArrayList<>();
		for (int c = 0; c < mxcount; c++) {
			Jymxsq jymxsq = new Jymxsq();
			int xxh = c + 1;
			jymxsq.setSpmxxh(xxh);
			jymxsq.setFphxz("0");
			jymxsq.setSpdm(spdms[c]);
			jymxsq.setSpmc(URLDecoder.decode(spmcs[c],"utf8"));
			jymxsq.setSpje(Double.valueOf(spjes[c]));
			if (spsls.length != 0) {
				jymxsq.setSpsl(Double.valueOf(spsls[c]));
			}
			jymxsq.setJshj(Double.valueOf(jshjs[c]));
			jymxsq.setKkjje(Double.valueOf(jshjs[c]));
			jymxsq.setSpje(Double.valueOf(jshjs[c]));
			jymxsq.setYkjje(0d);
			if (spges.length != 0) {
				try {
					jymxsq.setSpggxh(spges[c]);
				} catch (Exception e) {
					jymxsq.setSpggxh(null);

				}
			}
			try {
				jymxsq.setSps(Double.valueOf(spss[c]));
			} catch (Exception e) {
				jymxsq.setSps(null);
			}
			if (spdws.length != 0) {
				try {
					jymxsq.setSpdw(URLDecoder.decode(spdws[c],"utf8"));
				} catch (Exception e) {
					jymxsq.setSpdw(null);

				}
			}
			if (spdjs.length != 0) {
				try {
					jymxsq.setSpdj(Double.valueOf(spdjs[c]));
				} catch (Exception e) {
					jymxsq.setSpdj(null);

				}
			}
			if (spses.length != 0) {
				jymxsq.setSpse(Double.valueOf(spses[c]));
			}

			jymxsq.setLrry(yhid);
			jymxsq.setYxbz("1");
			jymxsq.setLrsj(TimeUtil.getNowDate());
			jymxsq.setXgsj(TimeUtil.getNowDate());
			jymxsq.setXgry(yhid);
			jymxsq.setGsdm(getGsdm());

			jshj += jymxsq.getJshj();
			jymxsqList.add(jymxsq);
		}
		jyxxsq.setJshj(jshj);
		jyxxsqservice.saveJyxxsq(jyxxsq, jymxsqList);
		System.out.println(JSON.toJSON(jyxxsq));
		System.out.println(JSON.toJSON(jymxsqList));
		request.setAttribute("jylsh", jyxxsq.getJylsh());
		Map map=new HashMap();
		map.put("jylsh", jyxxsq.getJylsh());
		Jyxxsq jyxxsq1=jyxxsqservice.findOneByParams(map);
		request.setAttribute("sqlsh", jyxxsq1.getSqlsh());
		request.setAttribute("corpid", corpid);
		request.setAttribute("userid", userid);
        return "dingding/qkp";
    }
	/**
	 * 获取jsAPI签名
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/jssqm")
	@ResponseBody
	public Map<String, Object> getItems() throws Exception {
		String corpid=request.getParameter("corpId");//企业id
		String url=request.getParameter("url");//当前网页url
		Map params=new HashMap();
		params.put("corpId", corpid);
		IsvCorpSuiteJsapiTicket isvCorpSuiteJsapiTicket=isvcorpsuitejsapiticketservice.findOneByParams(params);
		IsvCorpApp isvCorpApp=isvcorpappservice.findOneByParams(params);
		url = URLDecoder.decode(url,"utf8");
		String nonce = Utils.getRandomStr(8);
		Long timeStamp = System.currentTimeMillis();
		String sign = DingTalkJsApiSingnature.getJsApiSingnature(url, nonce, timeStamp, isvCorpSuiteJsapiTicket.getCorpJsapiTicket());
		System.out.println(sign);
		Map<String,Object> jsapiConfig = new HashMap<String, Object>();
		jsapiConfig.put("signature",sign);
		jsapiConfig.put("nonce",nonce);
		jsapiConfig.put("timeStamp",timeStamp);
		jsapiConfig.put("agentId",isvCorpApp.getAgentId());
		jsapiConfig.put("corpId",corpid);
		return jsapiConfig;
	}
}
