package com.rjxx.taxeasy.controller;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjxx.taxeasy.bizcomm.utils.InvoiceQueryUtil;
import com.rjxx.taxeasy.bizcomm.utils.MailService;
import com.rjxx.taxeasy.domains.*;
import com.rjxx.taxeasy.service.*;
import com.rjxx.taxeasy.vo.Fpcxvo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.bizcomm.utils.GetYjnr;
import com.rjxx.taxeasy.bizcomm.utils.SendalEmail;
import com.rjxx.taxeasy.bizcomm.utils.pdf.TwoDimensionCode;
import com.rjxx.taxeasy.filter.SystemControllerLog;
import com.rjxx.taxeasy.vo.KplsVO;
import com.rjxx.taxeasy.web.BaseController;

/**
 * Created by lenovo on 2015/12/18.
 */
@Controller
@RequestMapping("/yjfs")
public class YjfsController extends BaseController {

	@Autowired
	private KplsService ks;

	@Autowired
	private KplsvoService kvs;

	@Autowired
	private JylsService js;

	@Autowired
	private GsxxService gs;

	@Autowired
	private SendalEmail se;

	@Autowired
	private JylsService jylsService;

	@Autowired
	private KplsService kplsService;

	@Autowired
	private GsxxService gsxxService;

	@Autowired
	private YjmbService yjmbService;
	@Autowired
	private JyxxsqService jyxxsqService;
	@Autowired
	private MailService mailService;
	@Autowired
	private InvoiceQueryUtil invoiceQueryUtil;

	@Value("${pdf.save-path:}")
	private String pdfSavePath;
	@Value("${emailInfoUrl:}")
	private String emailInfoUrl;
	@Value("${imgdz_:}")
	private String imgdz_;
	@Value("${fpdz_:}")
	private String fpdz_;
	
	@RequestMapping
	public String index() {
		request.setAttribute("xfs", getXfList());
		return "yjfs/index";
	}

	@RequestMapping(value = "/send")
	@ResponseBody
	@SystemControllerLog(description = "发送邮件",key = "ids")  
	public Map send(String ids) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<>();
		String msg = "";
		if (!"".equals(ids)) {
			String[] idls = ids.split(",");
			for (int i = 0; i < idls.length; i++) {
				String id = idls[i].split(":")[0];
				if (id.equals("")) {
					continue;
				}
				try {
					//params.put("kplsh", id);
					params.put("serialorder", id);
					//final Kpls kpls = ks.findOneByParams(params);
					final List<Kpls> list = ks.findBySerialorder(params);
					Kpls kpls = list.get(0);
					if(list==null || list.size()<1) {
						msg +="根据serialorder未查到kpls信息";
						continue;
					}else {
						if(kpls.getGfemail() == null || "".equals(kpls.getGfemail())) {
							msg += "发票代码：" + kpls.getFpdm() + "|发票号码：" + kpls.getFphm() + "无邮箱,发送失败;";
							continue;
						}
					}
					
					
					Jyls jyls = js.findOne(kpls.getDjh());
					Map jyxxsqMap=new HashMap();
					jyxxsqMap.put("gsdm",kpls.getGsdm());
					jyxxsqMap.put("jylsh",jyls.getJylsh());
					Jyxxsq jyxxsq=jyxxsqService.findOneByJylsh(jyxxsqMap);
					//Kpls ls = new Kpls();
					//ls.setDjh(jyls.getDjh());
					//List<Kpls> lslist = kplsService.findAllByKpls(ls);
					List<String> pdfUrlList = new ArrayList<>();
					double jshj = 0;
					for (Kpls kpls1 : list) {
						pdfUrlList.add(kpls1.getPdfurl());
						jshj=jshj+kpls1.getJshj();
					}
					GetYjnr getYjnr = new GetYjnr();
					Map gsxxmap=new HashMap();
					gsxxmap.put("gsdm",kpls.getGsdm());
					Gsxx gsxx=gsxxService.findOneByGsdm(gsxxmap);
					Integer yjmbDm=gsxx.getYjmbDm();
					Yjmb yjmb=yjmbService.findOne(yjmbDm);
					String yjmbcontent=yjmb.getYjmbNr();
					String yjmbSubject = yjmb.getYjmbSubject();
					String q="";
					String infoUrl="";
					List<Fpcxvo> fpcxvos = invoiceQueryUtil.getInvoiceListByDdh(gsxx.getGsdm(), jyls.getDdh());
					if(fpcxvos.size()>0){
						if(fpcxvos.get(0).getTqm()!=null && !fpcxvos.get(0).getTqm().equals("")){
							q=fpcxvos.get(0).getTqm();
							infoUrl=emailInfoUrl+"g="+gsxx.getGsdm()+"&q="+q;
						}else if(fpcxvos.get(0).getKhh()!=null&&!fpcxvos.get(0).getKhh().equals("")){
							q=fpcxvos.get(0).getKhh();
							infoUrl=emailInfoUrl+"g="+gsxx.getGsdm()+"&q="+q;
						}
					}

					Map csmap=new HashMap();
					csmap.put("ddh",jyls.getDdh());
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
					csmap.put("ddrq",sdf.format(jyxxsq.getDdrq()));
					csmap.put("pdfurls",pdfUrlList);
					csmap.put("xfmc",jyls.getXfmc());
					csmap.put("infoUrl",infoUrl);
					
					//-----------
					SimpleDateFormat sdf2=new SimpleDateFormat("yyyy年MM月dd日");
					csmap.put("gs_mc_", kpls.getXfmc());//取销方名称
            		csmap.put("sq_sj_", sdf2.format(kpls.getKprq()));//取开票日期
            		// 二维码生成部分
            		TwoDimensionCode handler = new TwoDimensionCode();
            		ByteArrayOutputStream output = new ByteArrayOutputStream();
            		// 二维码中数据的来源
            		handler.encoderQRCode(fpdz_+"?q="+kpls.getSerialorder(), output);
            		String imgbase64string = org.apache.commons.codec.binary.Base64.encodeBase64String(output.toByteArray());
            		String ewm ="data:image/jpeg;base64,"+imgbase64string;
            		csmap.put("ewm", ewm);
            		csmap.put("e_w_m_", ewm);
            		csmap.put("d_d_h_", jyls.getDdh());
            		csmap.put("gf_mc_", kpls.getGfmc());
            		csmap.put("js_hj_",new DecimalFormat("0.00").format(jshj));
            		csmap.put("fp_dz_", fpdz_+"?q="+kpls.getSerialorder());
            		
            		csmap.put("lo_go_dz_", imgdz_+"emailLogo.png");
            		csmap.put("e_wm_dz_", imgdz_+"emailCode.png");
            		//-----------
            		
            		
            		if(yjmbSubject!=null && !"".equals(yjmbSubject)) {
            			yjmbSubject=yjmbSubject.replace("gs_mc_", kpls.getXfmc());
            			yjmbSubject=yjmbSubject.replace("d_d_h_", jyls.getDdh());
            		}else {
            			yjmbSubject="电子发票";
            		}
            		
					
					String content = getYjnr.getFpkjYj(csmap,yjmbcontent);
					if(kpls.getGsdm().equals("afb")){
						String [] to=new String[1];
						to[0]=kpls.getGfemail();
						String filePaths = "";
						for(Kpls kpls3:list) {
							String filePath=pdfSavePath+kpls3.getPdfurl().substring(kpls3.getPdfurl().indexOf(kpls3.getXfsh()),kpls3.getPdfurl().length());
							filePaths +=","+filePath;
						}
						mailService.sendAttachmentsMail(to,yjmbSubject,content,filePaths);
					}else {
						se.sendEmail(String.valueOf(kpls.getDjh()), kpls.getGsdm(), kpls.getGfemail(), "手工发送邮件",
								String.valueOf(kpls.getDjh()), content, yjmbSubject);
					}
					/*
					 * SendEmail se = new SendEmail();
					 * se.sendMail(jyls.getDdh(), kpls.getGfemail(), new
					 * ArrayList<String>() { { add(kpls.getPdfurl()); } },
					 * gsxx.getGsmc());
					 */
				} catch (Exception e) {
					e.printStackTrace();
					result.put("statu", "1");
					result.put("msg", "保存出现错误: " + e.getMessage());
					return result;
				}
			}
		}
		if ("".equals(msg)) {
			msg = "发送成功";
		}
		result.put("statu", "0");
		result.put("msg", msg);
		return result;
	}

	@RequestMapping(value = "/update")
	@ResponseBody
	public Map update(String serialorder, String gfemail, String wx, String sj) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("serialorder", serialorder);
			//Kpls kpls = ks.findOneByParams(params);
			List<Kpls> list =ks.findAll(params);
			for(Kpls kpls:list) {
				kpls.setGfemail(gfemail);
				ks.save(kpls);
			}
			
			result.put("success", true);
			result.put("statu", "0");
			result.put("msg", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("failure", true);
			result.put("msg", "保存出现错误: " + e.getMessage());
		}
		return result;
	}


	@RequestMapping(value = "/getYjfsList")
	@ResponseBody
	public Map getYjfsList(int length, int start, int draw, String jyrqq, String jyrqz, String kprqq, String kprqz,
						   String gfmc, String ddh, String fpdm, String fphm, String xfmc, Integer xfid,boolean loaddata) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		/*Pagination pagination = new Pagination();
		pagination.setPageNo(start / length + 1);
		pagination.setPageSize(length);*/
		//int total = pagination.getTotalRecord();
		if(loaddata){
			Map maps = new HashMap();
			maps.put("start",start);
			maps.put("length",length);
			List<Xf> xfs = getXfList();
			String xfStr = "";
			if (xfs != null) {
				for (int i = 0; i < xfs.size(); i++) {
					int xfid2 = xfs.get(i).getId();
					if (i == xfs.size() - 1) {
						xfStr += xfid2 + "";
					} else {
						xfStr += xfid2 + ",";
					}
				}
			}
			if(null !=xfid && !xfid.equals("")){
				maps.put("xfid2", xfid);
			}
			String[] xfid2 = xfStr.split(",");

			if (xfid2.length == 0) {
				xfid2 = null;
			}
			String skpStr = "";
			List<Skp> skpList = getSkpList();
			if (skpList != null) {
				for (int j = 0; j < skpList.size(); j++) {
					int skpid = skpList.get(j).getId();
					if (j == skpList.size() - 1) {
						skpStr += skpid + "";
					} else {
						skpStr += skpid + ",";
					}
				}
			}
			String[] skpid = skpStr.split(",");
			if (skpid.length == 0) {
				skpid = null;
			}
			maps.put("gsdm", getGsdm());
			maps.put("gfmc", gfmc);
			maps.put("ddh", ddh);
			maps.put("fphm", fphm);
			maps.put("fpdm", fpdm);
			maps.put("xfmc", xfmc);
			maps.put("xfid", xfid2);
			maps.put("skpid", skpid);
			maps.put("fpzldm", "12");
			maps.put("fpzt","00");
			// pagination.addParam("jyrqq", jyrqq);
			// pagination.addParam("jyrqz", jyrqz);
			if (!"".equals(jyrqq)) {
				maps.put("kprqq", jyrqq);
			}
			if (!"".equals(jyrqz)) {
				maps.put("kprqz", jyrqz);
			}
			if (!"".equals(kprqq)) {
				maps.put("kprqq2", kprqq);
			}
			if (!"".equals(kprqz)) {
				maps.put("kprqz2", kprqz);
			}
			maps.put("fpczlx", "11");
			List<Fpcxvo> list = kplsService.findByPage3(maps);
			int total;
			if(0 == start){
				total = kplsService.findTotal2(maps);
				request.getSession().setAttribute("total",total);
			}else{
				total =  (Integer)request.getSession().getAttribute("total");
				//request.getSession().getAttribute("total");
			}
			result.put("recordsTotal", total);
			result.put("recordsFiltered", total);
			result.put("draw", draw);
			result.put("data", list);
		}else{
			result.put("recordsTotal", 0);
			result.put("recordsFiltered", 0);
			result.put("draw", draw);
			result.put("data", new ArrayList<>());
		}
		return result;
	}

	/*
	*
	* 弃用
	* */
	/*@RequestMapping(value = "/getYjfsList")
	@ResponseBody
	public Map getYjfsList(int length, int start, int draw, String jyrqq, String jyrqz, String kprqq, String kprqz,
			String gfmc, String ddh, String fpdm, String fphm, String xfmc, Integer xfid,boolean loaddata) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Pagination pagination = new Pagination();
		pagination.setPageNo(start / length + 1);
		pagination.setPageSize(length);
		List<Integer> xfs = new ArrayList<>();
		if (!getXfList().isEmpty()) {
			for (Xf xf : getXfList()) {
				xfs.add(xf.getId());
			}
		}
		if (xfs.size() > 0) {
			pagination.addParam("xfList", xfs);
		}
		pagination.addParam("gsdm", getGsdm());
		pagination.addParam("gfmc", gfmc);
		pagination.addParam("ddh", ddh);
		pagination.addParam("fphm", fphm);
		pagination.addParam("fpdm", fpdm);
		pagination.addParam("xfmc", xfmc);
		pagination.addParam("xfid", xfid);
		pagination.addParam("fpzl", "12");
		// pagination.addParam("jyrqq", jyrqq);
		// pagination.addParam("jyrqz", jyrqz);
		if (!"".equals(jyrqq)) {
			pagination.addParam("jyrqq", jyrqq);
		}
		if (!"".equals(jyrqz)) {
			pagination.addParam("jyrqz", jyrqz);
		}
		if (!"".equals(kprqq)) {
			pagination.addParam("kprqq", kprqq);
		}
		if (!"".equals(kprqz)) {
			pagination.addParam("kprqz", kprqz);
		}
		pagination.addParam("fpczlxdm", "11");
		List<KplsVO> list = kvs.findByPage(pagination);
		int total = pagination.getTotalRecord();
		if(loaddata){
			result.put("recordsTotal", total);
			result.put("recordsFiltered", total);
			result.put("draw", draw);
			result.put("data", list);
		}else{
			result.put("recordsTotal", 0);
			result.put("recordsFiltered", 0);
			result.put("draw", draw);
			result.put("data", new ArrayList<>());
		}
		return result;
	}*/
	@RequestMapping(value = "/getDdmxList")
	@ResponseBody
	public Map getDdmxList(int length, int start, int draw, String serialorders,boolean loaddata) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Pagination pagination = new Pagination();
		pagination.setPageNo(start / length + 1);
		pagination.setPageSize(length);
		List<String> ids = new ArrayList<String>();
		if(serialorders!=null && !"".equals(serialorders)) {
			serialorders.split(";");
			ids.addAll(Arrays.asList(serialorders.split(";")));
		}
		if(loaddata){
			if(ids.size()>0) {
				pagination.addParam("serialorders", ids);
				List<Kpls> list =ks.findKplsMxListByPagination(pagination);
				int total = pagination.getTotalRecord();
				result.put("recordsTotal", total);
				result.put("recordsFiltered", total);
				result.put("draw", draw);
				result.put("data", list);
			}else {
				result.put("recordsTotal", 0);
				result.put("recordsFiltered", 0);
				result.put("draw", draw);
				result.put("data", new ArrayList<>());
			}
			
		}else {
			result.put("recordsTotal", 0);
			result.put("recordsFiltered", 0);
			result.put("draw", draw);
			result.put("data", new ArrayList<>());
		}
		return result;
	}
}
