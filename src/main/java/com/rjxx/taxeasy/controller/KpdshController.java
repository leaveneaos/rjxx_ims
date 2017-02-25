package com.rjxx.taxeasy.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.aspectj.weaver.ast.Var;
import org.eclipse.jdt.internal.compiler.ast.DoubleLiteral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.bizcomm.utils.InvoiceResponse;
import com.rjxx.taxeasy.bizcomm.utils.SeperateInvoiceUtils;
import com.rjxx.taxeasy.domains.Fpgz;
import com.rjxx.taxeasy.domains.Fpzt;
import com.rjxx.taxeasy.domains.Jyls;
import com.rjxx.taxeasy.domains.Jymxsq;
import com.rjxx.taxeasy.domains.Jyspmx;
import com.rjxx.taxeasy.domains.Jyxxsq;
import com.rjxx.taxeasy.domains.Kpls;
import com.rjxx.taxeasy.domains.Kpspmx;
import com.rjxx.taxeasy.domains.Skp;
import com.rjxx.taxeasy.domains.Sm;
import com.rjxx.taxeasy.domains.Sp;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.service.FpgzService;
import com.rjxx.taxeasy.service.JylsService;
import com.rjxx.taxeasy.service.JymxsqService;
import com.rjxx.taxeasy.service.JyspmxService;
import com.rjxx.taxeasy.service.JyxxsqService;
import com.rjxx.taxeasy.service.SkpService;
import com.rjxx.taxeasy.service.SmService;
import com.rjxx.taxeasy.service.SpService;
import com.rjxx.taxeasy.service.XfService;
import com.rjxx.taxeasy.service.YhcljlService;
import com.rjxx.taxeasy.vo.JyspmxDecimal;
import com.rjxx.taxeasy.vo.JyspmxDecimal2;
import com.rjxx.taxeasy.vo.JyxxsqVO;
import com.rjxx.taxeasy.web.BaseController;
import com.rjxx.time.TimeUtil;

@Controller
@RequestMapping("/kpdsh")
public class KpdshController extends BaseController {
	@Autowired
	private JyxxsqService jyxxsqService;
	@Autowired
	private FpgzService fpgzService;
	@Autowired
	private JyspmxService jyspmxService;
	@Autowired
	private YhcljlService cljlService;
	@Autowired
	private JymxsqService jymxsqService;
	@Autowired
	private JylsService jylsService;
	@Autowired
	private XfService xfService;
	@Autowired
	private SpService spService;
	@Autowired
	private SkpService skpService;
	@Autowired
	private SmService smService;
	@RequestMapping
	public String index() {
		request.setAttribute("xfList", getXfList());
		request.setAttribute("skpList", getSkpList());
		List<Sm> list = smService.findAllByParams(new Sm());
		request.setAttribute("smlist", list);
		return "kpdsh/index";
	}
	
	@ResponseBody
	@RequestMapping("/getItems")
	public Map<String, Object> getItems(int length, int start, int draw, String ddh, String kprqq, String kprqz,
			String spmc, String gfmc, String xfsh,String fpzldm) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Pagination pagination = new Pagination();
		pagination.setPageNo(start / length + 1);
		pagination.setPageSize(length);
		String gsdm = getGsdm();
		String xfStr = "";
		List<Xf> xfs = getXfList();
		if (xfs != null) {
			for (int i = 0; i < xfs.size(); i++) {
				int xfid = xfs.get(i).getId();
				if (i == xfs.size() - 1) {
					xfStr += xfid + "";
				} else {
					xfStr += xfid + ",";
				}
			}
		}
		String[] xfid = xfStr.split(",");
		if (xfid.length == 0) {
			xfid = null;
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
		// pagination.addParam("gsdm", gsdm);
		// pagination.addParam("xfid", xfid);
		// pagination.addParam("skpid", skpid);
		if ("".equals(fpzldm)) {
			pagination.addParam("fpzldm", null);
		}else{
			pagination.addParam("fpzldm", fpzldm);
		}
		pagination.addParam("ddh", ddh);
		pagination.addParam("xfs", xfs);
		pagination.addParam("skps", skpList);
		pagination.addParam("kprqq", kprqq);
		pagination.addParam("kprqz", kprqz);
		pagination.addParam("gsdm", gsdm);
		pagination.addParam("spmc", spmc);
		pagination.addParam("gfmc", gfmc);
		if (null != xfsh && !"".equals(xfsh) && !"-1".equals(xfsh)) {
			pagination.addParam("xfsh", xfsh);
		}
		List<JyxxsqVO> ykfpList = jyxxsqService.findByPage(pagination);
		for (JyxxsqVO jyxxsqVO : ykfpList) {
			  List<Fpgz> listt = fpgzService.findAllByParams(new HashMap<>());
		        Xf xf2 = new Xf();
		        xf2.setXfsh(jyxxsqVO.getXfsh());
		        Xf xf = xfService.findOneByParams(xf2);
		        double fpje = 0d;
		        if ("01".equals(jyxxsqVO.getFpzldm())) {
		        	if (xf.getZpfpje()!=null&&(xf.getZpfpje()>0)) {
		        		  fpje = xf.getZpfpje();
					}else if(xf.getZpzdje()!=null&&(xf.getZpzdje()>0)){
						  fpje = xf.getZpzdje();
					}
				}else if ("02".equals(jyxxsqVO.getFpzldm())) {
					if (xf.getZpfpje()!=null&&(xf.getPpfpje()>0)) {
		        		  fpje = xf.getPpfpje();
					}else if(xf.getZpzdje()!=null&&(xf.getPpzdje()>0)){
						  fpje = xf.getPpzdje();
					}
				}else if ("12".equals(jyxxsqVO.getFpzldm())) {
					if (xf.getZpfpje()!=null&&(xf.getDzpfpje()>0)) {
		        		  fpje = xf.getDzpfpje();
					}else if(xf.getZpzdje()!=null&&(xf.getDzpzdje()>0)){
						  fpje = xf.getDzpzdje();
					}
				}
		        boolean flag=false;
		        for (Fpgz fpgz : listt) {
					if (fpgz.getXfids().contains(String.valueOf(xf.getId()))) {
						if ("01".equals(jyxxsqVO.getFpzldm())) {
							fpje=fpgz.getZpxe();
						}else if ("02".equals(jyxxsqVO.getFpzldm())) {
							fpje=fpgz.getPpxe();
						}else if ("12".equals(jyxxsqVO.getFpzldm())) {
							fpje=fpgz.getDzpxe();
						}
						flag = true;
					}
				}
		        if (!flag) {
		        	Map<String, Object> params = new HashMap<>();
					params.put("mrbz", "1");
					Fpgz fpgz2 = fpgzService.findOneByParams(params);
					if (null!=fpgz2) {
						if ("01".equals(jyxxsqVO.getFpzldm())) {
							fpje=fpgz2.getZpxe();
						}else if ("02".equals(jyxxsqVO.getFpzldm())) {
							fpje=fpgz2.getPpxe();
						}else if ("12".equals(jyxxsqVO.getFpzldm())) {
							fpje=fpgz2.getDzpxe();
						}
					}
				}
		        jyxxsqVO.setFpje(fpje);
		}
		int total = pagination.getTotalRecord();
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		result.put("draw", draw);
		result.put("data", ykfpList);
		return result;
	}

	@ResponseBody
	@RequestMapping("/getMx")
	public Map<String, Object> getMx(String sqlsh) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<>();
		params.put("sqlsh", sqlsh);
		List<Jymxsq> ykfpList = jymxsqService.findAllByParams(params);
		result.put("data", ykfpList);
		return result;
	}

	@ResponseBody
	@RequestMapping("/th")
	public Map<String, Object> th(String ddhs) {
		Map<String, Object> result = new HashMap<String, Object>();
		String[] sqlshs = ddhs.split(",");
		for (String sqlsh : sqlshs) {
			Jyxxsq jyxxsq = jyxxsqService.findOne(Integer.valueOf(sqlsh));
			jyxxsq.setZtbz("2");
			jyxxsqService.save(jyxxsq);
		}
		result.put("msg", "退回成功");
		return result;
	}
	@ResponseBody
	@RequestMapping("/xgkpd")
	public Map<String, Object> xgkpd(Integer sqlsh){
		Map<String, Object> result = new HashMap<String, Object>();
		Jyxxsq jyxxsq = jyxxsqService.findOne(sqlsh);
		result.put("jyxx", jyxxsq);
		return result;
	}
	@ResponseBody
	@RequestMapping("/xgbckpd")
	public Map<String, Object> xgbckpd(Jyxxsq jyxxsq){
		Map<String, Object> result = new HashMap<String, Object>();
		Xf xf = new Xf();
		xf.setXfsh(jyxxsq.getXfsh());
		Xf xf1 = xfService.findOneByParams(xf);
		Map<String, Object> map = new HashMap<>();
		map.put("kpddm", jyxxsq.getKpddm());
		Skp skp1 = skpService.findOne(jyxxsq.getSkpid());
		Jyxxsq jyxxsq2 = jyxxsqService.findOne(jyxxsq.getSqlsh());
		jyxxsq2.setXfid(xf1.getId());
		jyxxsq2.setXfdh(xf1.getXfdh());
		jyxxsq2.setXfdz(xf1.getXfdz());
		jyxxsq2.setXfsh(xf1.getXfsh());
		jyxxsq2.setFpzldm(jyxxsq.getFpzldm());
		jyxxsq2.setXfmc(xf1.getXfmc());
		jyxxsq2.setXfyh(xf1.getXfyh());
		jyxxsq2.setXfyhzh(xf1.getXfyhzh());
		jyxxsq2.setXflxr(xf1.getXflxr());
		jyxxsq2.setXfyb(xf1.getXfyb());
		jyxxsq2.setKpddm(skp1.getKpddm());
		jyxxsq2.setSkpid(skp1.getId());
		jyxxsq2.setDdh(jyxxsq.getDdh());
		jyxxsq2.setGfsh(jyxxsq.getGfsh());
		jyxxsq2.setGfmc(jyxxsq.getGfmc());
		jyxxsq2.setGfyh(jyxxsq.getGfyh());
		jyxxsq2.setGfyhzh(jyxxsq.getGfyhzh());
		jyxxsq2.setGflxr(jyxxsq.getGflxr());
		jyxxsq2.setGfemail(jyxxsq.getGfemail());
		jyxxsq2.setGfsjh(jyxxsq.getGfsjh());
		jyxxsq2.setGfdz(jyxxsq.getGfdz());
		jyxxsq2.setBz(jyxxsq.getBz());
		jyxxsq2.setGfdh(jyxxsq.getGfdh());
		jyxxsq2.setXgry(getYhid());
		jyxxsq2.setXgsj(new Date());
		jyxxsqService.save(jyxxsq2);
		result.put("msg", true);
		return result;
	}
	@ResponseBody
	@RequestMapping("/mxsc")
	public Map<String, Object> mxsc(Integer id){
		Map<String, Object> result = new HashMap<String, Object>();	
		Jymxsq jymxsq = jymxsqService.findOne(id);
		Map<String, Object> params = new HashMap<>();
		params.put("sqlsh", jymxsq.getSqlsh());
		List<Jymxsq> ykfpList = jymxsqService.findAllByParams(params);
		if (ykfpList.size()<2) {
			result.put("msg", "只有一条明细,请直接删除流水!");
			return result;
		}
		jymxsq.setYxbz("0");
		Jyxxsq jyxxsq = jyxxsqService.findOne(jymxsq.getSqlsh());
		jyxxsq.setJshj(jyxxsq.getJshj()-jymxsq.getJshj());
		jyxxsqService.save(jyxxsq);
		jymxsqService.save(jymxsq);
		result.put("msg", "删除成功!");
		return result;
	}
	@ResponseBody
	@RequestMapping("/xgbcmx")
	public Map<String, Object> xgbcmx(Jymxsq jymxsq/*,Integer spid*/){
		Map<String, Object> result = new HashMap<String, Object>();
/*		Sp sp = spService.findOne(spid);*/
		Jymxsq jymxsq2 = jymxsqService.findOne(jymxsq.getId());
		jymxsq2.setSpmc(jymxsq.getSpmc());
/*		jymxsq2.setSpid(spid);*/
		jymxsq2.setSpggxh(jymxsq.getSpggxh());
	/*	jymxsq2.setSpdm(sp.getSpbm());*/
		jymxsq2.setSpmc(jymxsq.getSpmc());
		jymxsq2.setSpdw(jymxsq.getSpdw());
		jymxsq2.setSps(jymxsq.getSps());
		jymxsq2.setSpdj(jymxsq.getSpdj());
		jymxsq2.setSpje(jymxsq.getSpje());
		jymxsq2.setSpse(jymxsq.getSpse());
		Jyxxsq jyxxsq = jyxxsqService.findOne(jymxsq2.getSqlsh());
		jyxxsq.setJshj(jyxxsq.getJshj()+jymxsq.getJshj()-jymxsq2.getJshj());
		jymxsq2.setJshj(jymxsq.getJshj());
		
		
		jymxsqService.save(jymxsq2);
		jyxxsqService.save(jyxxsq);
		result.put("msg", true);
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping("/kpdshkp")
	@Transactional
	public Map<String, Object> kpdshkp(String sqlshs,String fpxes) throws Exception{
		Map<String, Object> result = new HashMap<String, Object>();
		String [] sqlsht=sqlshs.split(",");
		String [] fpxels = fpxes.split(",");
		int i=0;
		for (String sqh : sqlsht) {
			Jyxxsq jyxxsq = jyxxsqService.findOne(Integer.valueOf(sqh));
			Map<String, Object> params = new HashMap<>();
			params.put("sqlsh", jyxxsq.getSqlsh());
			List<Jymxsq> list = jymxsqService.findAllByParams(params);
			//转换明细
			Map<String, Object> params1 = new HashMap<>();
	    	params1.put("sqlsh",sqh);
			List<JyspmxDecimal2> jyspmxs = jyspmxService.getNeedToKP3(params1);
			//价税分离
			 if ("1".equals(jyxxsq.getHsbz())) {
	             jyspmxs = SeperateInvoiceUtils.separatePrice2(jyspmxs);
	         }
			 int fphs1 = 8;
			 int fphs2 = 100;
		        boolean flag=false;
		        List<Fpgz> listt = fpgzService.findAllByParams(new HashMap<>());
		   	 Xf x = new Xf();
	         x.setGsdm(getGsdm());
	         x.setXfsh(jyxxsq.getXfsh());
	         Xf xf = xfService.findOneByParams(x);
		        for (Fpgz fpgz : listt) {
					if (fpgz.getXfids().contains(String.valueOf(xf.getId()))) {
						if ("01".equals(jyxxsq.getFpzldm())) {
							fphs1=fpgz.getZphs();
						}else if ("02".equals(jyxxsq.getFpzldm())) {
							fphs1=fpgz.getPphs();
						}else if ("12".equals(jyxxsq.getFpzldm())) {
							fphs2=fpgz.getDzphs();
						}
						flag = true;
					}
				}
		        if (!flag) {
		        	Map<String, Object> paramse = new HashMap<>();
					params.put("mrbz", "1");
					Fpgz fpgz2 = fpgzService.findOneByParams(paramse);
					if (null!=fpgz2) {
						if ("01".equals(jyxxsq.getFpzldm())) {
							fphs1=fpgz2.getZphs();
						}else if ("02".equals(jyxxsq.getFpzldm())) {
							fphs1=fpgz2.getPphs();
						}else if ("12".equals(jyxxsq.getFpzldm())) {
							fphs1=fpgz2.getDzphs();
						}
					}
				}
			 //分票
	         if(jyxxsq.getFpzldm().equals("12")){
	        	 jyspmxs = SeperateInvoiceUtils.splitInvoices2(jyspmxs,new BigDecimal(Double.valueOf(fpxels[i])),new BigDecimal(Double.valueOf(fpxels[i])),fphs2);
	         }else{
				 jyspmxs = SeperateInvoiceUtils.splitInvoices2(jyspmxs,new BigDecimal(Double.valueOf(fpxels[i])),new BigDecimal(Double.valueOf(fpxels[i])), fphs1);
	         }
	         //保存进交易流水
	         Map<Integer, List<JyspmxDecimal2>> fpMap = new HashMap<>();
	         for (JyspmxDecimal2 jysmx : jyspmxs) {
	             int fpnum = jysmx.getFpnum();
	             List<JyspmxDecimal2> list2 = fpMap.get(fpnum);
	             if (list2 == null) {
	                 list2 = new ArrayList<>();
	                 fpMap.put(fpnum, list2);
	             }
	             list2.add(jysmx);
	         }
	         Map<Integer, Integer> fpNumKplshMap = new HashMap<>();
	         for (Map.Entry<Integer, List<JyspmxDecimal2>> entry : fpMap.entrySet()) {
	             int fpNum = entry.getKey();
	             List<JyspmxDecimal2> fpJyspmxList = entry.getValue();
	             Jyls jyls = saveJyls(jyxxsq, fpJyspmxList);
	             saveKpspmx(jyls, fpJyspmxList);
	            // fpNumKplshMap.put(fpNum, kpls.getKplsh());
	         }
	     	jyxxsq.setZtbz("2");
			cljlService.saveYhcljl(getYhid(), "开票单审核");
			jyxxsqService.save(jyxxsq);
	         i++;
		}
		
/*		for (String str : sqlsht) {
			Jyxxsq jyxxsq = jyxxsqService.findOne(Integer.valueOf(str));
			Jyls jyls1 = new Jyls();
			jyls1.setDdh(jyxxsq.getDdh());
			String jylsh = "JY" + new SimpleDateFormat("yyyyMMddHHmmssSS").format(new Date());
			jyls1.setJylsh(jylsh);
			jyls1.setJylssj(TimeUtil.getNowDate());
			jyls1.setFpzldm(jyxxsq.getFpzldm());
			jyls1.setFpczlxdm("11");
			jyls1.setXfid(jyxxsq.getXfid());
			jyls1.setXfsh(jyxxsq.getXfsh());
			jyls1.setXfmc(jyxxsq.getXfmc());
			jyls1.setXfyh(jyxxsq.getXfyh());
			jyls1.setXfyhzh(jyxxsq.getXfyhzh());
			jyls1.setXflxr(jyxxsq.getXflxr());
			jyls1.setXfdh(jyxxsq.getXfdh());
			jyls1.setXfdz(jyxxsq.getXfdz());
			jyls1.setGfid(jyxxsq.getGfid());
			jyls1.setGfsh(jyxxsq.getGfsh());
			jyls1.setGfmc(jyxxsq.getGfmc());
			jyls1.setGfyh(jyxxsq.getGfyh());
			jyls1.setGfyhzh(jyxxsq.getGfyhzh());
			jyls1.setGflxr(jyxxsq.getGflxr());
			jyls1.setGfdh(jyxxsq.getGfdh());
			jyls1.setGfdz(jyxxsq.getGfdz());
			jyls1.setGfyb(jyxxsq.getGfyb());
			jyls1.setGfemail(jyxxsq.getGfemail());
			jyls1.setClztdm("00");
			jyls1.setBz(jyxxsq.getBz());
			jyls1.setSkr(jyxxsq.getSkr());
			jyls1.setKpr(jyxxsq.getKpr());
			jyls1.setFhr(jyxxsq.getFhr());
			jyls1.setSsyf(jyxxsq.getSsyf());
			jyls1.setYfpdm(null);
			jyls1.setYfphm(null);
			jyls1.setHsbz(jyxxsq.getHsbz());
			jyls1.setJshj(jyxxsq.getJshj());
			jyls1.setYkpjshj(0d);
			jyls1.setYxbz("1");
			jyls1.setGsdm(jyxxsq.getGsdm());
			jyls1.setLrry(getYhid());
			jyls1.setLrsj(TimeUtil.getNowDate());
			jyls1.setXgry(getYhid());
			jyls1.setXgsj(TimeUtil.getNowDate());
			jyls1.setSkpid(jyxxsq.getSkpid());
			jylsService.save(jyls1);
			Map<String, Object> params = new HashMap<>();
			params.put("sqlsh", jyxxsq.getSqlsh());
			List<Jymxsq> list = jymxsqService.findAllByParams(params);
			for (Jymxsq mxItem : list) {
				Jyspmx jymx = new Jyspmx();
				jymx.setDjh(jyls1.getDjh());
				jymx.setSpmxxh(mxItem.getSpmxxh());
				jymx.setSpdm(mxItem.getSpdm());
				jymx.setSpmc(mxItem.getSpmc());
				jymx.setSpggxh(mxItem.getSpggxh());
				jymx.setSpdw(mxItem.getSpdw());
				jymx.setSps(mxItem.getSps());
				jymx.setSpdj(mxItem.getSpdj() == null ? null : mxItem.getSpdj());
				jymx.setSpje(mxItem.getSpje());
				jymx.setSpsl(mxItem.getSpsl());
				jymx.setSpse(mxItem.getSpse());
				jymx.setJshj(mxItem.getJshj());
				jymx.setYkphj(0d);
				jymx.setGsdm(getGsdm());
				jymx.setLrsj(TimeUtil.getNowDate());
				jymx.setLrry(getYhid());
				jymx.setXgsj(TimeUtil.getNowDate());
				jymx.setXgry(getYhid());
				jymx.setFphxz("0");
				jyspmxService.save(jymx);
			}
			jyxxsq.setZtbz("2");
			cljlService.saveYhcljl(getYhid(), "开票单审核");
			jyxxsqService.save(jyxxsq);
		}*/
		result.put("msg", "审核成功!");
		return result;
		
	}
	@ResponseBody
	@RequestMapping("/cxsp")
	public Map<String, Object> cxsp(){
		Map<String, Object> result = new HashMap<String, Object>();
		Sp sp = new Sp();
		sp.setGsdm(getGsdm());
		List<Sp> sps = spService.findAllByParams(sp);
		result.put("sps", sps);
		return result;
	}
	@ResponseBody
	@RequestMapping("/hqsl")
	public Map<String, Object> hqsl(Integer spid){
		Map<String, Object> result = new HashMap<String, Object>();
		Sp sp = spService.findOne(spid);
		Sm sm = smService.findOne(sp.getSmid());
		result.put("sm", sm);
		result.put("sp", sp);
		return result;
	}
	
	
	 /**
     * 保存交易流水
     *
     * @param jyls
     * @return
     */
    private Jyls saveJyls(Jyxxsq jyxxsq, List<JyspmxDecimal2> jyspmxList) throws Exception {
		Jyls jyls1 = new Jyls();
    	jyls1.setDdh(jyxxsq.getDdh());
		String jylsh = "JY" + new SimpleDateFormat("yyyyMMddHHmmssSS").format(new Date());
		jyls1.setJylsh(jylsh);
		jyls1.setJylssj(TimeUtil.getNowDate());
		jyls1.setFpzldm(jyxxsq.getFpzldm());
		jyls1.setFpczlxdm("11");
		jyls1.setXfid(jyxxsq.getXfid());
		jyls1.setXfsh(jyxxsq.getXfsh());
		jyls1.setXfmc(jyxxsq.getXfmc());
		jyls1.setXfyh(jyxxsq.getXfyh());
		jyls1.setTqm(jyxxsq.getTqm());
		jyls1.setXfyhzh(jyxxsq.getXfyhzh());
		jyls1.setXflxr(jyxxsq.getXflxr());
		jyls1.setXfdh(jyxxsq.getXfdh());
		jyls1.setXfdz(jyxxsq.getXfdz());
		jyls1.setGfid(jyxxsq.getGfid());
		jyls1.setGfsh(jyxxsq.getGfsh());
		jyls1.setGfmc(jyxxsq.getGfmc());
		jyls1.setGfyh(jyxxsq.getGfyh());
		jyls1.setGfyhzh(jyxxsq.getGfyhzh());
		jyls1.setGflxr(jyxxsq.getGflxr());
		jyls1.setGfdh(jyxxsq.getGfdh());
		jyls1.setGfdz(jyxxsq.getGfdz());
		jyls1.setGfyb(jyxxsq.getGfyb());
		jyls1.setGfemail(jyxxsq.getGfemail());
		jyls1.setClztdm("00");
		jyls1.setBz(jyxxsq.getBz());
		jyls1.setSkr(jyxxsq.getSkr());
		jyls1.setKpr(jyxxsq.getKpr());
		jyls1.setFhr(jyxxsq.getFhr());
		jyls1.setSsyf(jyxxsq.getSsyf());
		jyls1.setYfpdm(null);
		jyls1.setYfphm(null);
		jyls1.setHsbz(jyxxsq.getHsbz());
	      double hjje = 0;
	        double hjse = 0;
		 for (JyspmxDecimal2 jyspmx : jyspmxList) {
	            hjje += jyspmx.getSpje().doubleValue();
	            hjse += jyspmx.getSpse().doubleValue();
	      }
		jyls1.setJshj(hjje+hjse);
		jyls1.setYkpjshj(0d);
		jyls1.setYxbz("1");
		jyls1.setGsdm(jyxxsq.getGsdm());
		jyls1.setLrry(getYhid());
		jyls1.setLrsj(TimeUtil.getNowDate());
		jyls1.setXgry(getYhid());
		jyls1.setXgsj(TimeUtil.getNowDate());
		jyls1.setSkpid(jyxxsq.getSkpid());
        jylsService.save(jyls1);
        return jyls1;
    }
    
    private void saveKpspmx(Jyls jyls, List<JyspmxDecimal2> fpJyspmxList) throws Exception {
        int djh = jyls.getDjh();
        for (JyspmxDecimal2 mxItem : fpJyspmxList) {
			Jyspmx jymx = new Jyspmx();
			jymx.setDjh(djh);
			jymx.setSpmxxh(mxItem.getSpmxxh());
			jymx.setSpdm(mxItem.getSpdm());
			jymx.setSpmc(mxItem.getSpmc());
			jymx.setSpggxh(mxItem.getSpggxh());
			jymx.setSpdw(mxItem.getSpdw());
			jymx.setSps(mxItem.getSps() == null ? null : mxItem.getSps().doubleValue());
			jymx.setSpdj(mxItem.getSpdj() == null ? null : mxItem.getSpdj().doubleValue());
			jymx.setSpje(mxItem.getSpje() == null ? null : mxItem.getSpje().doubleValue());
			jymx.setSpsl(mxItem.getSpsl().doubleValue());
			jymx.setSpse(mxItem.getSpse() == null ? null : mxItem.getSpse().doubleValue());
			jymx.setJshj(mxItem.getJshj() == null ? null : mxItem.getJshj().doubleValue());
			jymx.setYkphj(0d);
			jymx.setGsdm(getGsdm());
			jymx.setLrsj(TimeUtil.getNowDate());
			jymx.setLrry(getYhid());
			jymx.setXgsj(TimeUtil.getNowDate());
			jymx.setXgry(getYhid());
			jymx.setFphxz("0");
			jyspmxService.save(jymx);
        }
    }
}
 