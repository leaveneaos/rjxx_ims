package com.rjxx.taxeasy.controller;

import com.alibaba.fastjson.JSON;
import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.bizcomm.utils.FpclService;
import com.rjxx.taxeasy.bizcomm.utils.GetDataService;
import com.rjxx.taxeasy.bizcomm.utils.GetXmlUtil;
import com.rjxx.taxeasy.bizcomm.utils.HttpUtils;
import com.rjxx.taxeasy.domains.*;
import com.rjxx.taxeasy.filter.SystemControllerLog;
import com.rjxx.taxeasy.service.*;
import com.rjxx.taxeasy.vo.Jyzfmxvo;
import com.rjxx.taxeasy.vo.Spvo;
import com.rjxx.taxeasy.web.BaseController;
import com.rjxx.time.TimeUtil;
import com.rjxx.utils.BeanConvertUtils;
import com.rjxx.utils.StringUtils;
import com.rjxx.utils.Tools;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
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
	private FpclService fpclService;
	@Autowired
	private JyxxsqService JyxxsqService;
	@Autowired
	private PldrjlService pldrjlService;
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
		Map map = getDataService.getldyxFirData(ddh,gsdm);
		String accessToken = map.get("accessToken").toString();
		if(accessToken==null || "".equals(accessToken)){
			return resultMap;
		}
		Map resMap = getDataService.getldyxSecData(ddh,gsdm,accessToken);
		Jyxxsq jyxxsq = new Jyxxsq();
		List<Jyxxsq> jyxxsqList = null;
		List<Jymxsq>jymxsqList=null;
		List<Jyzfmx>jyzfmxList=null;
		if(resMap!=null){
			jyxxsqList = (List<Jyxxsq>) resMap.get("jyxxsqList");
			jymxsqList = (List<Jymxsq>) resMap.get("jymxsqList");
			jyzfmxList = (List<Jyzfmx>) resMap.get("jyzfmxList");
		}
//		if(jyzfmxList!=null){
//			for (Jyzfmx jyzfmx : jyzfmxList){
//				if(null!=jyzfmxList){
//					Map parmMap = new HashMap();
//					parmMap.put("zffsDm",jyzfmx.getZffsDm());
//					List<Zffs> zffsss = zffsService.findAllByParams(parmMap);
//					jyzfmx.setZffsMc(zffsss.get(0).getZffsMc());
//				}
//			}
//		}
		System.out.println("---"+JSON.toJSONString(jyzfmxList));
		resultMap.put("jyxxsq",jyxxsqList);
		resultMap.put("jymxsq",jymxsqList);
		resultMap.put("jyzfmx",jyzfmxList);
		request.getSession().setAttribute("jyxxsq",jyxxsqList);
		request.getSession().setAttribute("jymxsq",jymxsqList);
		request.getSession().setAttribute("jyzfmx",jyzfmxList);
		return resultMap;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Map save() {
		Map<String, Object> result = new HashMap<String, Object>();
		String gsdm = getGsdm();
		List<Jyxxsq> jyxxsqList = (List<Jyxxsq>) request.getSession().getAttribute("jyxxsq");
		List<Jymxsq>jymxsqList= (List<Jymxsq>) request.getSession().getAttribute("jymxsq");
		List<Jyzfmx>jyzfmxList= (List<Jyzfmx>) request.getSession().getAttribute("jyzfmx");
		Jyxxsq jyxxsq = jyxxsqList.get(0);
		//销方
		String xfid = request.getParameter("xf");
		Xf xf = xfService.findOne(Integer.parseInt(xfid));
		jyxxsq.setXfid(xf.getId());
		jyxxsq.setXfsh(xf.getXfsh());
		jyxxsq.setXfmc(xf.getXfmc());
		jyxxsq.setXfyh(xf.getXfyh());
		jyxxsq.setXfyhzh(xf.getXfyhzh());
		jyxxsq.setXfdz(xf.getXfdz());
		jyxxsq.setXfdh(xf.getXfdh());
		jyxxsq.setKpr(xf.getKpr());
		jyxxsq.setFhr(xf.getFhr());
		jyxxsq.setSkr(xf.getSkr());
		//发票种类代码
		jyxxsq.setFpzldm(request.getParameter("fpzldm"));
		logger.info("发票种类代码"+jyxxsq.getFpczlxdm());
		String skpid = request.getParameter("kpd");
		if (skpid != null && !"".equals(skpid)) {
			jyxxsq.setSkpid(Integer.parseInt(skpid));
		}
		Skp skp = skpService.findOne(Integer.valueOf(skpid));
		jyxxsq.setKpddm(skp.getKpddm());

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
			gsMap.put("gsdm",jyxxsq.getGsdm());
			Gsxx gsxx = gsxxservice.findOneByGsdm(gsMap);
			System.out.println("----交易信息申请"+JSON.toJSONString(jyxxsq));
			System.out.println("----交易信息申请"+JSON.toJSONString(jymxsqList));
			System.out.println("----交易信息申请"+JSON.toJSONString(jyzfmxList));
			String xml=GetXmlUtil.getFpkjXml(jyxxsq,jymxsqList,jyzfmxList);
			logger.info("secretKey------" + gsxx.getSecretKey());
			logger.info("appKey------" + gsxx.getAppKey());
			String resultxml = HttpUtils.HttpUrlPost(xml, gsxx.getAppKey(), gsxx.getSecretKey());
			logger.info("-------返回值---------" + resultxml);
			Document document = DocumentHelper.parseText(resultxml);
			Element root = document.getRootElement();
			List<Element> childElements = root.elements();
			Map xmlMap = new HashMap();
			for (Element child : childElements) {
                xmlMap.put(child.getName(),child.getText());
            }
			String returncode=(String)xmlMap.get("ReturnCode");
			String ReturnMessage=(String)xmlMap.get("ReturnMessage");
			if (returncode.equals("9999")) {
				logger.info("发送客户端失败----msg--"+ReturnMessage);
				result.put("failure", true);
				result.put("msg", ReturnMessage);
				return result;
			}else {
				result.put("success", true);
				result.put("djh", jyxxsq.getSqlsh());
				result.put("msg", "开票申请成功！");
			}
		} catch (DocumentException e) {
			e.printStackTrace();
			result.put("failure", true);
			result.put("msg", "保存出现错误: " + e.getMessage());
		}
		return result;
	}

}
