package com.rjxx.taxeasy.controller;


import java.util.*;


import com.rjxx.taxeasy.configuration.LightAppMessageDelivery;
import com.dingtalk.open.client.api.model.corp.MessageBody;
import com.rjxx.taxeasy.configuration.MessageHelper;
import com.rjxx.taxeasy.domains.*;
import com.rjxx.taxeasy.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

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
	@Autowired
	private JymxsqService jymxsqservice;
	@RequestMapping
    public String index() throws Exception {
		String corpid=request.getParameter("corpid");//企业id
		String userid=request.getParameter("userid");//钉钉用户id
		String sqlsh=request.getParameter("sqlsh");//钉钉用户id
		String jylsh=request.getParameter("jylsh");//钉钉用户id
		request.setAttribute("jylsh", jylsh);
		request.setAttribute("sqlsh", sqlsh);
		request.setAttribute("corpid", corpid);
		request.setAttribute("userid", userid);
        return "dingding/qkp";
    }
	/**
	 * 发送会话消息
	 *
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sendmessage")
	@ResponseBody
	public Map sendmessage() throws Exception {
		String code = request.getParameter("code");
		String corpId = request.getParameter("corpid");
		String agentId = request.getParameter("agentId");
		String userid = request.getParameter("userid");
		System.out.println("code:"+code+" corpid:"+corpId);
		Map params=new HashMap();
		params.put("corpId", corpId);
		IsvCorpSuiteJsapiTicket  isvcorptoken=isvcorpsuitejsapiticketservice.findOneByParams(params);
		String accessToken=isvcorptoken.getCorpaccesstoken();

		String sqlsh=request.getParameter("sqlsh");
		Map map=new HashMap();
		map.put("sqlsh", sqlsh);
		Jyxxsq jyxxsq=jyxxsqservice.findOneByParams(map);
		List<Jymxsq> jymxsqlist=jymxsqservice.findAllByParams(map);
		LightAppMessageDelivery lightAppMessageDelivery=new LightAppMessageDelivery();
		lightAppMessageDelivery.setAgentid(agentId);
		lightAppMessageDelivery.setToparty("");
		lightAppMessageDelivery.setTouser(userid);
		lightAppMessageDelivery.setMsgType("oa");
		MessageBody.OABody oaBody=new MessageBody.OABody();
		MessageBody.OABody.Body body=new MessageBody.OABody.Body();
		body.setTitle("开票申请信息");
		MessageBody.OABody.Body.Form form4=new MessageBody.OABody.Body.Form();
		form4.setKey("发票抬头：");
		form4.setValue(jyxxsq.getGfmc());
		MessageBody.OABody.Body.Form form=new MessageBody.OABody.Body.Form();
		form.setKey("订单号：");
		form.setValue(jyxxsq.getDdh());
		MessageBody.OABody.Body.Form form1=new MessageBody.OABody.Body.Form();
		form1.setKey("价税合计：");
		form1.setValue(jyxxsq.getJshj().toString());
		MessageBody.OABody.Body.Form form2=new MessageBody.OABody.Body.Form();
		form2.setKey("申请发票类型：");
		if(jyxxsq.getFpzldm().equals("12")){
			form2.setValue("电子发票(增普)");
		}else if(jyxxsq.getFpzldm().equals("02")){
			form2.setValue("纸质普票");
		}else if(jyxxsq.getFpzldm().equals("01")){
			form2.setValue("纸质专票");
		}
		MessageBody.OABody.Body.Form form3=new MessageBody.OABody.Body.Form();
		form3.setKey("发票明细：");
		form3.setValue(jymxsqlist.size()+"行");


		MessageBody.OABody.Body.Rich rich=new MessageBody.OABody.Body.Rich();
		rich.setNum("");
		rich.setUnit("");

        List<MessageBody.OABody.Body.Form> forms=new ArrayList<>();
		forms.add(form4);
		forms.add(form);
		forms.add(form1);
		forms.add(form2);
		forms.add(form3);
		body.setContent("您已提交开票申请，内容如下：");
		body.setForm(forms);
		oaBody.setBody(body);
		MessageBody.OABody.Head head=new MessageBody.OABody.Head();
		head.setBgcolor("FFBBBBBB");
		head.setText("开票通");
		oaBody.setHead(head);
		lightAppMessageDelivery.setMessage(oaBody);
		System.out.println(JSON.toJSON(oaBody));
		MessageHelper.Receipt receipt=MessageHelper.send(accessToken,lightAppMessageDelivery);
		System.out.println(JSON.toJSON(receipt));
		Map result=new HashMap();
		result.put("errcode","0");
		return result;
	}
}
