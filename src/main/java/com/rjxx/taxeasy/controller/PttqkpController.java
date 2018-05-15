package com.rjxx.taxeasy.controller;

import com.alibaba.fastjson.JSON;
import com.rjxx.taxeasy.bizcomm.utils.*;
import com.rjxx.taxeasy.dao.JylsJpaDao;
import com.rjxx.taxeasy.dao.KplsJpaDao;
import com.rjxx.taxeasy.domains.*;
import com.rjxx.taxeasy.filter.SystemControllerLog;
import com.rjxx.taxeasy.invoice.ResponseUtil;
import com.rjxx.taxeasy.service.*;
import com.rjxx.taxeasy.service.adapter.AdapterService;
import com.rjxx.taxeasy.vo.Fpkcvo;
import com.rjxx.taxeasy.vo.Jyzfmxvo;
import com.rjxx.taxeasy.vo.Spvo;
import com.rjxx.taxeasy.web.BaseController;
import com.rjxx.utils.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/pttqkp")
public class PttqkpController extends BaseController {

	@Autowired
	private SmService smService;
	@Autowired
	private CszbService cszbService;
	@Autowired
	private SpvoService spvoService;
	@Autowired
	private SpzService spzService;
	@Autowired
	private DrmbService drmbService;
	@Autowired
	private GetDataService getDataService;
	@Autowired
	private ZffsService zffsService;
	@Autowired
	private JyxxsqService jyxxsqService;
	@Autowired
	private GsxxService gsxxservice;
	@Autowired
	private XfService xfService;
	@Autowired
	private SkpService skpService;
	@Autowired
	private JylsJpaDao jylsJpaDao;
	@Autowired
	private KplsJpaDao kplsJpaDao;
	@Autowired
	private JymxsqService jymxsqService;
	@Autowired
	private FpclService fpclService;
	@Autowired
	private ResponseUtil responseUtil;
	@Autowired
	private AdapterService adapterService;

	@RequestMapping
	@SystemControllerLog(description = "平台提取开票", key = "")
	public String index() {
		request.setAttribute("xfList", getXfList());
		request.setAttribute("skpList", getSkpList());
		List<Sm> list = smService.findAllByParams(new Sm());
		request.setAttribute("smlist", list);
		String gsdm = this.getGsdm();
		List<Object> argList = new ArrayList<>();
		argList.add(gsdm);
		Cszb cszb = cszbService.getSpbmbbh(gsdm, getXfid(), null, "sfqyspz");
		List<Spvo> list2 = new ArrayList<>();
		if (null != cszb && cszb.getCsz().equals("是")) {
			Map<String, Object> pMap = new HashMap<>();
			pMap.put("xfs", getXfList());
			list2 = spzService.findAllByParams(pMap);
		}
		if (list2.size() == 0) {
			list2 = spvoService.findAllByGsdm(gsdm);
		}

		List<Xf> xfList = this.getXfList();
		if (!list2.isEmpty()) {
			request.setAttribute("sp", list2.get(0));
		}
		if (xfList.size() == 1) {
			Map<String, Object> map = new HashMap<>();
			/*
			 * map.put("xfsh", xfList.get(0).getXfsh()); map.put("xfs",
			 * getXfList());
			 */
			map.put("gsdm", gsdm);
			List<Drmb> mbList = drmbService.findAllByParams(map);
			Drmb mb = new Drmb();
			mb.setXfsh(xfList.get(0).getXfsh());
			request.setAttribute("skps", getSkpList());
			request.setAttribute("skpSum", getSkpList().size());
			request.setAttribute("mrmb", drmbService.findMrByParams(mb));
			request.setAttribute("mbList", mbList);
			request.setAttribute("mbSum", mbList.size());
		}
		if (xfList != null && xfList.size() > 0) {
			request.setAttribute("xf", xfList.get(0));
		}
		request.setAttribute("spList", list2);
		request.setAttribute("xfList", getXfList());
		request.setAttribute("xfnum", getXfList().size());
		System.out.println("销方下拉--"+ JSON.toJSONString(getXfList()));
		return "pttqkp/index";
	}

	@ResponseBody
	@RequestMapping("/getItems")
	public Map<String, Object> getItems(int length, int start, int draw, String jylsh, String jyrqq, String jyrqz,
										String xfsh, boolean loaddata) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		if (loaddata) {
			String gsdm = getGsdm();
			Map map = getDataService.getldyxFirData(jylsh,gsdm);
			String accessToken = map.get("accessToken").toString();
			Map resultMap = getDataService.getldyxSecData(jylsh,gsdm,accessToken);
			if(null == resultMap || 0 == resultMap.size() || (null !=resultMap.get("tmp")&&resultMap.get("tmp").equals(""))){
				int total = 0;
				result.put("recordsTotal", total);
				result.put("recordsFiltered", total);
				result.put("draw", draw);
				result.put("data", new ArrayList<>());
			}else{
				List<Jyxxsq> jyxxsqList = (List<Jyxxsq>) resultMap.get("jyxxsqList");
				result.put("recordsTotal", jyxxsqList.size());
				result.put("recordsFiltered", jyxxsqList.size());
				result.put("draw", draw);
				result.put("data", jyxxsqList);
			}
		} else {
			int total = 0;
			result.put("recordsTotal", total);
			result.put("recordsFiltered", total);
			result.put("draw", draw);
			result.put("data", new ArrayList<>());
		}
		return result;
	}


	@RequestMapping(value = "/findjyxxsq")
	@ResponseBody
	public Map findjyxxsq(String ddh){
		Map resultMap=new HashMap();
		String gsdm = getGsdm();
		Map resMap = null;
		Cszb  csz =  cszbService.getSpbmbbh(gsdm, null,null, "sfhhurl");
		if(csz!=null&& csz.getCsz()!=null){
			if(null!=gsdm && gsdm.equals("ldyx")){
				if(ddh.length()!=19){
					resultMap.put("msg", "您输入的订单号不符合规定，请重试！");
					return resultMap;
				}
				Map map = getDataService.getldyxFirData(ddh,gsdm);
				if(map==null){
					resultMap.put("msg", "系统出现异常，请重试！");
					return resultMap;
				}
				String accessToken = map.get("accessToken").toString();
				if(accessToken==null || "".equals(accessToken)){
					resultMap.put("msg", "未查询到数据，请重试！");
					return resultMap;
				}
				resMap = getDataService.getldyxSecData(ddh,gsdm,accessToken);
			}else if(null != gsdm && gsdm.equals("Family")){
				resMap=getDataService.getData(ddh,gsdm);
			}else if(gsdm.equals("bqw")){
				resMap=getDataService.getDataForBqw(ddh,gsdm,csz.getCsz());
			}else if(gsdm.equals("gvc")){
				resMap=getDataService.getDataForGvc(ddh,gsdm,csz.getCsz());
			}
		}else {
			Map map = adapterService.getApiMsg(gsdm, ddh);
			if(map==null){
				resultMap.put("msg","未查询到数据");
				return resultMap;
			}
			String msg = (String) map.get("msg");
			if(StringUtils.isNotBlank(msg)){
				resultMap.put("msg",msg);
				return resultMap;
			}
			Jyxxsq jyxxsq = (Jyxxsq) map.get("jyxxsq");
			List<Jyxxsq> jyxxsqList = new ArrayList<>();
			List<Jyxxsq> rejyxxsqList = (List<Jyxxsq>) map.get("jyxxsqList");
			List<Jymxsq> jymxsqList = (List<Jymxsq>) map.get("jymxsqList");
			List<Jyzfmx> jyzfmxList = (List<Jyzfmx>) map.get("jyzfmxList");
			if(jyxxsq!=null){
				jyxxsqList.add(jyxxsq);
			}
			if( rejyxxsqList!=null && rejyxxsqList.size()>0){
				jyxxsqList =rejyxxsqList;
			}
			List zflist = new ArrayList();
			if(jyzfmxList!=null && jyzfmxList.size() > 0){
				for (Jyzfmx jyzfmx : jyzfmxList){
					Map parmMap = new HashMap();
					parmMap.put("zffsDm",jyzfmx.getZffsDm());
					List<Zffs> zffsss = zffsService.findAllByParams(parmMap);
					Jyzfmxvo jyzfmxVo = new Jyzfmxvo();
					jyzfmxVo.setZfmc(zffsss.get(0).getZffsMc());
					jyzfmxVo.setZffsDm(zffsss.get(0).getZffsDm());
					jyzfmxVo.setZfje(jyzfmx.getZfje());
					zflist.add(jyzfmxVo);
				}
			}
			resultMap.put("jyxxsq", jyxxsqList);
			resultMap.put("jymxsq", jymxsqList);
			resultMap.put("jyzflist", jyzfmxList);
			request.getSession().setAttribute("jyxxsq",jyxxsqList);
			request.getSession().setAttribute("jymxsq",jymxsqList);
			request.getSession().setAttribute("jyzfmx",jyzfmxList);
			return resultMap;
		}
		List<Jyxxsq> jyxxsqList = null;
		List<Jymxsq>jymxsqList=null;
		List<Jyzfmx>jyzfmxList=null;
		List list = new ArrayList();
		if(resMap!=null){
			jyxxsqList = (List<Jyxxsq>) resMap.get("jyxxsqList");
			jymxsqList = (List<Jymxsq>) resMap.get("jymxsqList");
			jyzfmxList = (List<Jyzfmx>) resMap.get("jyzfmxList");
		}else {
			resultMap.put("msg", "未查询到数据，请重试！");
			return  resultMap;
		}
		if(jyzfmxList.size() > 0){
			for (Jyzfmx jyzfmx : jyzfmxList){
				Map parmMap = new HashMap();
				parmMap.put("zffsDm",jyzfmx.getZffsDm());
				List<Zffs> zffsss = zffsService.findAllByParams(parmMap);
				Jyzfmxvo jyzfmxVo = new Jyzfmxvo();
				jyzfmxVo.setZfmc(zffsss.get(0).getZffsMc());
				jyzfmxVo.setZffsDm(zffsss.get(0).getZffsDm());
				jyzfmxVo.setZfje(jyzfmx.getZfje());
				list.add(jyzfmxVo);
			}
		}
		//logger.info("---"+JSON.toJSONString(list));
		if(jyxxsqList.size()>0){
			resultMap.put("jyxxsq",jyxxsqList);
			resultMap.put("jymxsq",jymxsqList);
			resultMap.put("jyzflist",list);
			request.getSession().setAttribute("jyxxsq",jyxxsqList);
			request.getSession().setAttribute("jymxsq",jymxsqList);
			request.getSession().setAttribute("jyzfmx",jyzfmxList);
		}else {
			if(null!=resMap.get("msg")|| !"".equals(resultMap.get("msg"))){
				resultMap.put("msg", resMap.get("msg"));
			}
			if(null!=resultMap.get("tmp")|| !"".equals(resultMap.get("tmp"))){
				resultMap.put("temp", resMap.get("tmp"));
			}
			if(null!= resMap.get("error")|| !"".equals(resultMap.get("error"))){
				resultMap.put("error", resMap.get("error"));
			}
		}

		return resultMap;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Map save() {
		Map<String, Object> result = new HashMap<String, Object>();
		String gsdm = getGsdm();
		Cszb  csz =  cszbService.getSpbmbbh(gsdm, null,null, "sfhhurl");
		List<Jyxxsq> jyxxsqList = (List<Jyxxsq>) request.getSession().getAttribute("jyxxsq");
		List<Jymxsq>jymxsqList= (List<Jymxsq>) request.getSession().getAttribute("jymxsq");
		List<Jyzfmx>jyzfmxList= (List<Jyzfmx>) request.getSession().getAttribute("jyzfmx");
		if(jyxxsqList.size()>0) {
			Jyxxsq jyxxsq = jyxxsqList.get(0);
			//销方
			String xfid = request.getParameter("xf");
			Xf xf = xfService.findOne(Integer.parseInt(xfid));
			jyxxsq.setXfid(xf.getId());
			jyxxsq.setXfsh(xf.getXfsh());
			jyxxsq.setXfmc(xf.getXfmc());
			String skpid = request.getParameter("kpd");
			if (skpid != null && !"".equals(skpid)) {
				jyxxsq.setSkpid(Integer.parseInt(skpid));
			}
			Skp skp = skpService.findOne(Integer.valueOf(skpid));
			jyxxsq.setKpddm(skp.getKpddm());
			Map xfxxmap= GetXfxx.getXfxx(xf,skp);
			jyxxsq.setXfdz(xfxxmap.get("xfdz").toString());
			jyxxsq.setXfdh(xfxxmap.get("xfdh").toString());
			jyxxsq.setXfyh(xfxxmap.get("xfyh").toString());
			jyxxsq.setXfyhzh(xfxxmap.get("xfyhzh").toString());
			jyxxsq.setSkr(xfxxmap.get("skr").toString());
			jyxxsq.setKpr(xfxxmap.get("kpr").toString());
			jyxxsq.setFhr(xfxxmap.get("fhr").toString());
			//发票种类代码
			jyxxsq.setFpzldm(request.getParameter("fpzldm"));
			logger.info("发票种类代码" + jyxxsq.getFpzldm());
			jyxxsq.setClztdm("00");
			jyxxsq.setBz(request.getParameter("bz"));
			jyxxsq.setGfmc(request.getParameter("gfmc"));
			jyxxsq.setGfsh(request.getParameter("gfsh"));
			jyxxsq.setGfdz(request.getParameter("gfdz"));
			jyxxsq.setGfdh(request.getParameter("gfdh"));
			jyxxsq.setGfyh(request.getParameter("gfyh"));
			jyxxsq.setGfyhzh(request.getParameter("yhzh"));
			jyxxsq.setGfemail(request.getParameter("yjdz"));
			jyxxsq.setZtbz("3");//'状态标识 0 待提交,1已申请,2退回,3已处理,4删除,5部分处理,6待处理'
			jyxxsq.setSjly("0");//0平台录入，1接口接入
			if(null!=request.getParameter("gfsh")&&!"".equals(request.getParameter("gfsh")) ){
				jyxxsq.setGflx("1");
			}else {
				jyxxsq.setGflx("0");
			}
			String tqm = request.getParameter("tqm");
			if (StringUtils.isNotBlank(tqm)) {
				Map params = new HashMap();
				params.put("gsdm", gsdm);
				params.put("tqm", tqm);
				Jyxxsq tmp = jyxxsqService.findOneByParams(params);
				if (tmp != null) {
					result.put("failure", true);
					result.put("msg", "提取码已经存在");
					return result;
				}
			}
			try {
				Map gsMap = new HashMap();
				gsMap.put("gsdm", jyxxsq.getGsdm());
				Gsxx gsxx = gsxxservice.findOneByGsdm(gsMap);
				if (jyzfmxList != null && jyzfmxList.size()>0) {
					String kpfsDm = "";
					for (Jyzfmx jyzfmx : jyzfmxList) {
						Map parmMap = new HashMap();
						parmMap.put("zffsDm", jyzfmx.getZffsDm());
						List<Zffs> zffsss = zffsService.findAllByParams(parmMap);
						System.out.println("---支付金额" + jyzfmx.getZfje());
						String str = zffsss.get(0).getKpfsDm();
						System.out.println("---开票方式代码" + str);
						if (!"0.0".equals(jyzfmx.getZfje()) && str.equals("01")) {
							kpfsDm = zffsss.get(0).getKpfsDm();
						}
					}
					if ("".equals(kpfsDm) || null == kpfsDm) {
						result.put("failure", true);
						result.put("msg", "可开票金额为0元");
						return result;
					}
				}
				//获取分票规则信息
				jyxxsq.setSfdyqd("0");
				jyxxsq.setSfdy("0");
				String resultxml="";
				if(csz!=null&& csz.getCsz()!=null){
					String xml = GetXmlUtil.getFpkjXml(jyxxsq, jymxsqList, jyzfmxList);
					 resultxml = HttpUtils.HttpUrlPost(xml, gsxx.getAppKey(), gsxx.getSecretKey());
				}else {
					try {
						Cszb cszb2 = cszbService.getSpbmbbh(gsdm, null, null, "kpfs");
						String kpfs=cszb2.getCsz();
						fpclService.zjkp(jyxxsqList,kpfs);
						result.put("success", true);
						result.put("msg", "开票申请成功！");
						return result;
					} catch (Exception e) {
						e.printStackTrace();
						result.put("failure", true);
						result.put("msg", "开票申请错误！");
						return result;
					}
				}
				logger.info("-------返回值---------" + resultxml);
				Document document = DocumentHelper.parseText(resultxml);
				Element root = document.getRootElement();
				List<Element> childElements = root.elements();
				Map xmlMap = new HashMap();
				for (Element child : childElements) {
					xmlMap.put(child.getName(), child.getText());
				}
				String returncode = (String) xmlMap.get("ReturnCode");
				String ReturnMessage = (String) xmlMap.get("ReturnMessage");
				if (returncode.equals("9999")) {
					logger.info("发送客户端失败----msg--" + ReturnMessage);
					result.put("failure", true);
					result.put("msg", ReturnMessage);
					return result;
				} else {
					result.put("success", true);
					result.put("djh", jyxxsq.getSqlsh());
					result.put("msg", "开票申请成功！");
				}
			} catch (DocumentException e) {
				e.printStackTrace();
				result.put("failure", true);
				result.put("msg", "保存出现错误: " + e.getMessage());
			}
		}else {
			result.put("failure", true);
			result.put("msg", "保存出现错误，请重试！");
			return result;
		}
		return result;
	}




	@RequestMapping(value = "/getFpzldm")
	@ResponseBody
	public List<Fpkcvo> getKpd(String skpid,String kpddm) throws Exception {
		List<Fpkcvo> list = new ArrayList();
		String gsdm = getGsdm();
		Map map = new HashMap();
		map.put("gsdm",gsdm);
		if(StringUtils.isBlank(skpid) && StringUtils.isBlank(kpddm)){
			return new ArrayList<>();
		}
		if(StringUtils.isNotBlank(skpid)){
			map.put("kpdid",Integer.valueOf(skpid));
		}
		map.put("kpddm",kpddm);
		Skp skp = skpService.findOneByParams(map);
		if(skp.getKplx()!=null){
			String kplx = skp.getKplx();
			String[] strs=kplx.split(",");
			for(int i=0,len=strs.length;i<len;i++){
				Fpkcvo fpkcvo = new Fpkcvo();
				if(strs[i].toString().equals("01")){
					fpkcvo.setFpzlmc("专用发票");
					fpkcvo.setFpzldm("01");
				}
				if(strs[i].toString().equals("02")){
					fpkcvo.setFpzlmc("普通发票");
					fpkcvo.setFpzldm("02");
				}
				if(strs[i].toString().equals("12")){
					fpkcvo.setFpzlmc("电子发票");
					fpkcvo.setFpzldm("12");
				}
				list.add(fpkcvo);
			}
		}
		return list;
	}

}
