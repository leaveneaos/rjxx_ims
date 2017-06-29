package com.rjxx.taxeasy.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.aspectj.weaver.ast.Var;
import org.eclipse.jdt.internal.compiler.ast.DoubleLiteral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.config.SpringDataJacksonConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.bizcomm.utils.InvoiceResponse;
import com.rjxx.taxeasy.bizcomm.utils.InvoiceSplitUtils;
import com.rjxx.taxeasy.bizcomm.utils.SeperateInvoiceUtils;
import com.rjxx.taxeasy.domains.DrPz;
import com.rjxx.taxeasy.domains.Drmb;
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
import com.rjxx.taxeasy.filter.SystemControllerLog;
import com.rjxx.taxeasy.service.DrPzService;
import com.rjxx.taxeasy.service.DrmbService;
import com.rjxx.taxeasy.service.FpgzService;
import com.rjxx.taxeasy.service.JylsService;
import com.rjxx.taxeasy.service.JymxsqService;
import com.rjxx.taxeasy.service.JyspmxService;
import com.rjxx.taxeasy.service.JyxxsqService;
import com.rjxx.taxeasy.service.SkpService;
import com.rjxx.taxeasy.service.SmService;
import com.rjxx.taxeasy.service.SpService;
import com.rjxx.taxeasy.service.SpvoService;
import com.rjxx.taxeasy.service.XfService;
import com.rjxx.taxeasy.service.YhcljlService;
import com.rjxx.taxeasy.vo.FpcljlVo;
import com.rjxx.taxeasy.vo.JyspmxDecimal;
import com.rjxx.taxeasy.vo.JyspmxDecimal2;
import com.rjxx.taxeasy.vo.JyxxsqVO;
import com.rjxx.taxeasy.vo.Spvo;
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
	DrmbService drmbService;
	@Autowired
	DrPzService drPzService;
	@Autowired
	private SpService spService;
	@Autowired
	private SkpService skpService;
	@Autowired
	private SmService smService;
	@Autowired
	private SpvoService spvoService;

	@RequestMapping
	@SystemControllerLog(description = "开票单审核页面进入", key = "")
	public String index() {
		request.setAttribute("xfList", getXfList());
		request.setAttribute("skpList", getSkpList());
		List<Sm> list = smService.findAllByParams(new Sm());
		request.setAttribute("smlist", list);
		String gsdm = this.getGsdm();
		List<Object> argList = new ArrayList<>();
		argList.add(gsdm);
		List<Spvo> spList = spvoService.findAllByGsdm(gsdm);
		List<Xf> xfList = this.getXfList();
		if (!spList.isEmpty()) {
			request.setAttribute("sp", spList.get(0));
		}
		if (xfList.size() == 1) {
			Map<String, Object> map = new HashMap<>();
			map.put("xfsh", xfList.get(0).getXfsh());
			map.put("xfs", getXfList());
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
		request.setAttribute("spList", spList);
		request.setAttribute("xfSum", xfList.size());

		return "kpdshxb/index";
	}

	@ResponseBody
	@RequestMapping("/getItems")
	public Map<String, Object> getItems(int length, int start, int draw, String ddh, String kprqq, String kprqz,
			String spmc, String gfmc, String xfsh, String fpzldm,boolean loaddata) throws Exception {
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
		} else {
			pagination.addParam("fpzldm", fpzldm);
		}
		pagination.addParam("ddh", ddh);
		pagination.addParam("xfs", xfs);
		pagination.addParam("ztbz", "6");
		pagination.addParam("bfzt", "5");
		pagination.addParam("skps", skpList);
		pagination.addParam("kprqq", kprqq);
		pagination.addParam("kprqz", kprqz);
		pagination.addParam("gsdm", gsdm);
		pagination.addParam("spmc", spmc);
		pagination.addParam("gfmc", gfmc);
		pagination.addParam("order", "sqlsh");
		pagination.addParam("ord", "asc");
		if (null != xfsh && !"".equals(xfsh) && !"-1".equals(xfsh)) {
			pagination.addParam("xfsh", xfsh);
		}
		List<JyxxsqVO> ykfpList = jyxxsqService.findByPage(pagination);
		for (JyxxsqVO jyxxsqVO : ykfpList) {
			List<Fpgz> listt = fpgzService.findAllByParams(new HashMap<>());
			Xf xf2 = new Xf();
			xf2.setXfsh(jyxxsqVO.getXfsh());
			xf2.setGsdm(gsdm);
			Xf xf = xfService.findOneByParams(xf2);
			Skp skp = skpService.findOne(jyxxsqVO.getSkpid());
			double fpje = 0d;
			Double zdje = 0d;
			String hsbz = "";
			String qdbz = "";
			/**
			 * 取税控盘的开票限额
			 */
			if ("01".equals(jyxxsqVO.getFpzldm())) {
				zdje = skp.getZpmax();
			} else if ("02".equals(jyxxsqVO.getFpzldm())) {
				zdje = skp.getPpmax();
			} else if ("12".equals(jyxxsqVO.getFpzldm())) {
				zdje = skp.getDpmax();
			}
			boolean flag = false;
			/**
			 * 先取税控盘的分票金额
			 */
			for (Fpgz fpgz : listt) {
				if (fpgz.getXfids().contains(String.valueOf(xf.getId()))) {
					if ("01".equals(jyxxsqVO.getFpzldm())) {
						fpje = fpgz.getZpxe();
					} else if ("02".equals(jyxxsqVO.getFpzldm())) {
						fpje = fpgz.getPpxe();
					} else if ("12".equals(jyxxsqVO.getFpzldm())) {
						fpje = fpgz.getDzpxe();
					}
					flag = true;
					hsbz = fpgz.getHsbz();
					qdbz = fpgz.getQdbz();
					break;
				}
			}
			/**
			 * 分票规则没有取到，就取税控盘的分票金额
			 */
			if (!flag) {
				if ("01".equals(jyxxsqVO.getFpzldm())) {
					if (skp.getZpfz() != null && (skp.getZpfz() > 0)) {
						fpje = skp.getZpfz();
					}
				} else if ("02".equals(jyxxsqVO.getFpzldm())) {
					if (skp.getPpfz() != null && (skp.getPpfz() > 0)) {
						fpje = skp.getPpfz();
					}
				} else if ("12".equals(jyxxsqVO.getFpzldm())) {
					if (skp.getFpfz() != null && (skp.getFpfz()) > 0) {
						fpje = skp.getFpfz();
					}
				}
			}
			if (null == zdje) {
				zdje = 0d;
			}
			if (hsbz != null && hsbz.equals("1")) {
				zdje = (double) Math.round(zdje * 1.17 * 100) / 100;
				System.out.println(zdje);
			}
			if (zdje >= fpje) {
				jyxxsqVO.setFpje(fpje);
			} else {
				jyxxsqVO.setFpje(zdje);
			}
			jyxxsqVO.setZdje(zdje);
			jyxxsqVO.setFpjshsbz(hsbz);
			jyxxsqVO.setQdbz(qdbz);
		}
        if(loaddata){
			int total = pagination.getTotalRecord();
			result.put("recordsTotal", total);
			result.put("recordsFiltered", total);
			result.put("draw", draw);
			result.put("data", ykfpList);
		}else{
			int total = 0;
			result.put("recordsTotal", total);
			result.put("recordsFiltered", total);
			result.put("draw", draw);
			result.put("data", new ArrayList<>());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/getMx")
	public Map<String, Object> getMx(int length, int start, int draw,String sqlsh) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Pagination pagination = new Pagination();
		pagination.setPageNo(start / length + 1);
		pagination.setPageSize(length);
		String []sqlshs=sqlsh.split(",");
		List sqlshlist=new ArrayList();
		for(int i=0;i<sqlshs.length;i++){
			//if(!"0".equals(sqlshs[i])){
				sqlshlist.add(sqlshs[i]);
			//}
		}
		pagination.addParam("sqlshlist",sqlshlist);
		List<Jymxsq> ykfpList = jymxsqService.findByPage(pagination);
		int total = pagination.getTotalRecord();
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		result.put("draw", draw);
		result.put("data", ykfpList);
		return result;
	}

	@ResponseBody
	@RequestMapping("/th")
	@SystemControllerLog(description = "开票单审核退回", key = "ddhs")
	public Map<String, Object> th(String ddhs) {
		Map<String, Object> result = new HashMap<String, Object>();
		String[] sqlshs = ddhs.split(",");
		for (String sqlsh : sqlshs) {
			Jyls jylsparams=new Jyls();
			jylsparams.setSqlsh(Integer.valueOf(sqlsh));
            boolean f =true;
			List<Jyls> jylslist=jylsService.findAllByParams(jylsparams);
            for(Jyls jyls:jylslist){
				if(jyls.getClztdm().equals("40")){
					f=false;
					break;
				}
			}
            if(f){
				for(Jyls jyls:jylslist){
					jyls.setYxbz("0");
					jylsService.save(jyls);
				}
				Jyxxsq jyxxsq = jyxxsqService.findOne(Integer.valueOf(sqlsh));
				jyxxsq.setZtbz("2");
				jyxxsqService.save(jyxxsq);
				result.put("msg", "退回成功");
			}else{
				result.put("msg", "退回失败，该笔数据已部分开具！");
			}
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/sc")
	@SystemControllerLog(description = "开票单审核删除", key = "ddhs")
	public Map<String, Object> sc(String ddhs) {
		Map<String, Object> result = new HashMap<String, Object>();
		String[] sqlshs = ddhs.split(",");
		for (String sqlsh : sqlshs) {
			Jyxxsq jyxxsq = jyxxsqService.findOne(Integer.valueOf(sqlsh));
			jyxxsq.setYxbz("0");
			jyxxsqService.save(jyxxsq);
		}
		result.put("msg", "删除成功");
		return result;
	}

	@ResponseBody
	@RequestMapping("/xgkpd")
	public Map<String, Object> xgkpd(Integer sqlsh) {
		Map<String, Object> result = new HashMap<String, Object>();
		Jyxxsq jyxxsq = jyxxsqService.findOne(sqlsh);
		result.put("jyxx", jyxxsq);
		return result;
	}

	@ResponseBody
	@RequestMapping("/xgbckpd")
	@SystemControllerLog(description = "开票单审核修改开票单", key = "sqlsh")
	public Map<String, Object> xgbckpd(Jyxxsq jyxxsq) {
		Map<String, Object> result = new HashMap<String, Object>();
		Xf xf = new Xf();
		xf.setXfsh(jyxxsq.getXfsh());
		xf.setGsdm(getGsdm());
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
	@SystemControllerLog(description = "开票单审核明细删除", key = "id")
	public Map<String, Object> mxsc(Integer id) {
		Map<String, Object> result = new HashMap<String, Object>();
		Jymxsq jymxsq = jymxsqService.findOne(id);
		Map<String, Object> params = new HashMap<>();
		params.put("sqlsh", jymxsq.getSqlsh());
		List<Jymxsq> ykfpList = jymxsqService.findAllByParams(params);
		if (ykfpList.size() < 2) {
			result.put("msg", "只有一条明细,请直接删除流水!");
			return result;
		}
		jymxsq.setYxbz("0");
		Jyxxsq jyxxsq = jyxxsqService.findOne(jymxsq.getSqlsh());
		jyxxsq.setJshj(jyxxsq.getJshj() - jymxsq.getJshj());
		jyxxsqService.save(jyxxsq);
		jymxsqService.save(jymxsq);
		result.put("msg", "删除成功!");
		return result;
	}

	@ResponseBody
	@RequestMapping("/xgbcmx")
	@SystemControllerLog(description = "开票单审核明细修改", key = "id")
	public Map<String, Object> xgbcmx(Jymxsq jymxsq/* ,Integer spid */) {
		Map<String, Object> result = new HashMap<String, Object>();
		/* Sp sp = spService.findOne(spid); */
		Jymxsq jymxsq2 = jymxsqService.findOne(jymxsq.getId());
		jymxsq2.setSpmc(jymxsq.getSpmc());
		/* jymxsq2.setSpid(spid); */
		jymxsq2.setSpggxh(jymxsq.getSpggxh());
		/* jymxsq2.setSpdm(sp.getSpbm()); */
		jymxsq2.setSpmc(jymxsq.getSpmc());
		jymxsq2.setSpdw(jymxsq.getSpdw());
		jymxsq2.setSps(jymxsq.getSps());
		jymxsq2.setSpdj(jymxsq.getSpdj());
		jymxsq2.setSpje(jymxsq.getSpje());
		jymxsq2.setSpse(jymxsq.getSpse());
		Jyxxsq jyxxsq = jyxxsqService.findOne(jymxsq2.getSqlsh());
		jyxxsq.setJshj(jyxxsq.getJshj() + jymxsq.getJshj() - jymxsq2.getJshj());
		jymxsq2.setJshj(jymxsq.getJshj());
		if (null == jymxsq.getYkjje()) {
			jymxsq.setYkjje(0d);
		}
		if (null == jymxsq.getKkjje()) {
			jymxsq.setKkjje(0d);
		}
		jymxsq2.setYkjje(jymxsq.getYkjje());
		jymxsq2.setKkjje(jymxsq2.getJshj() - jymxsq2.getYkjje());
		jymxsqService.save(jymxsq2);
		jyxxsqService.save(jyxxsq);
		result.put("msg", true);
		return result;
	}

	@ResponseBody
	@RequestMapping("/kpdshkp")
	@SystemControllerLog(description = "开票单审核审核开票", key = "sqlshs")
	public Map<String, Object> kpdshkp(String sqlshs, String fpxes, String bckpje, String fpjshsbz, String qdbzs)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String[] bckkje = bckpje.split(",");
		String[] fpjehsbzs = fpjshsbz.split(",");
		List<Jyxxsq> listsq = new ArrayList<>();
		List<List<JyspmxDecimal2>> mxList = new ArrayList<>();
		List<FpcljlVo> listfpcl = new ArrayList<>();
		if (sqlshs == null || sqlshs.equals("")) {
			result.put("recordsTotal", listfpcl.size());
			result.put("recordsFiltered", listfpcl.size());
			result.put("data", listfpcl);
			return result;
		}
		String[] sqlsht = sqlshs.split(",");
		String[] fpxels = fpxes.split(",");
		String[] qdbz = qdbzs.split(",");
		int i = 0;
		for (String sqh : sqlsht) {
			Jyxxsq jyxxsq = jyxxsqService.findOne(Integer.valueOf(sqh));
			jyxxsq.setSfdyqd(qdbz[i]);
			listsq.add(jyxxsq);
			Map<String, Object> params = new HashMap<>();
			params.put("sqlsh", jyxxsq.getSqlsh());
			List<Jymxsq> list = jymxsqService.findAllByParams(params);
			// 转换明细
			Map<String, Object> params1 = new HashMap<>();
			params1.put("sqlsh", sqh);
			List<JyspmxDecimal2> jyspmxs = jyspmxService.getNeedToKP4(params1);

			// 价税分离
			if ("1".equals(jyxxsq.getHsbz())) {
				jyspmxs = SeperateInvoiceUtils.separatePrice2(jyspmxs);
			}
			if (sqlsht.length == 1) {
				for (int j = 0; j < jyspmxs.size(); j++) {
					if (Double.valueOf(bckkje[j]) == jyspmxs.get(j).getJshj().doubleValue()) {

					} else {
						jyspmxs.get(j).setJshj(new BigDecimal(bckkje[j]));
						jyspmxs.get(j).setSpje(jyspmxs.get(j).getJshj().divide(
								new BigDecimal("1").add(jyspmxs.get(j).getSpsl()), 6, BigDecimal.ROUND_HALF_UP));
						jyspmxs.get(j).setSpse(jyspmxs.get(j).getJshj().subtract(jyspmxs.get(j).getSpje()));
						if (jyspmxs.get(j).getSpdj() != null) {
							jyspmxs.get(j).setSps(jyspmxs.get(j).getSpje().divide(jyspmxs.get(j).getSpdj(), 6,
									BigDecimal.ROUND_HALF_UP));
						} else {
							jyspmxs.get(j).setSps(null);
						}

					}
				}
			} else {
				for (int j = 0; j < jyspmxs.size(); j++) {
					if (jyspmxs.get(j).getKkjje().compareTo(jyspmxs.get(j).getJshj()) == 0) {

					} else {

						jyspmxs.get(j).setJshj(jyspmxs.get(j).getKkjje());
						jyspmxs.get(j).setSpje(jyspmxs.get(j).getJshj().divide(
								new BigDecimal("1").add(jyspmxs.get(j).getSpsl()), 6, BigDecimal.ROUND_HALF_UP));
						jyspmxs.get(j).setSpse(jyspmxs.get(j).getJshj().subtract(jyspmxs.get(j).getSpje()));
						if (jyspmxs.get(j).getSpdj() != null) {
							jyspmxs.get(j).setSps(jyspmxs.get(j).getSpje().divide(jyspmxs.get(j).getSpdj(), 6,
									BigDecimal.ROUND_HALF_UP));
						} else {
							jyspmxs.get(j).setSps(null);
						}

					}
				}
			}
			int fphs1 = 8;
			int fphs2 = 100;
			double zdje = 0d;
			boolean flag = false;
			boolean qzfp = true;//是否强制分票
			boolean spzsfp = false;//是否按商品整数分票

			List<Fpgz> listt = fpgzService.findAllByParams(new HashMap<>());
			Xf x = new Xf();
			x.setGsdm(getGsdm());
			x.setXfsh(jyxxsq.getXfsh());
			Xf xf = xfService.findOneByParams(x);
			Skp skp = skpService.findOne(jyxxsq.getSkpid());
			if (null != qdbz[i] && qdbz[i].equals("1")) {
				fphs1 = 99999999;
				fphs2 = 99999999;
			} else {
				for (Fpgz fpgz : listt) {
					if (fpgz.getXfids().contains(String.valueOf(xf.getId()))) {
						if ("01".equals(jyxxsq.getFpzldm())) {
							if(!"".equals(fpgz.getZphs())&&null!=fpgz.getZphs()){
								fphs1 = fpgz.getZphs();
							}
						} else if ("02".equals(jyxxsq.getFpzldm())) {
							if(!"".equals(fpgz.getPphs())&&null!=fpgz.getPphs()){
								fphs1 = fpgz.getPphs();
							}
						} else if ("12".equals(jyxxsq.getFpzldm())) {
							if(!"".equals(fpgz.getDzphs())&&null!=fpgz.getDzphs()){
								fphs2 = fpgz.getDzphs();
							}
						}
						if (fpgz.getSfqzfp().equals("0")) {
							qzfp = false;
						}
						if (fpgz.getSfspzsfp().equals("1")) {
							spzsfp = true;
						}
						flag = true;
						break;
					}
				}
				if (!flag) {
					qzfp = false;
					spzsfp = false;
					/*if ("01".equals(jyxxsq.getFpzldm())) {

					} else if ("02".equals(jyxxsq.getFpzldm())) {

					} else if ("12".equals(jyxxsq.getFpzldm())) {

					}*/
				}
			}
			if ("01".equals(jyxxsq.getFpzldm())) {
				zdje = skp.getZpmax();
			} else if ("02".equals(jyxxsq.getFpzldm())) {
				zdje = skp.getPpmax();
			} else if ("12".equals(jyxxsq.getFpzldm())) {
				zdje = skp.getDpmax();
			}
			// 分票
			List<JyspmxDecimal2> splitKpspmxs = new ArrayList<JyspmxDecimal2>();
			Map mapResult = new HashMap();
			mapResult = InvoiceSplitUtils.dealDiscountLine(jyspmxs);
			if (jyxxsq.getFpzldm().equals("12")) {
				if (null != fpjehsbzs[i] && "1".equals(fpjehsbzs[i])) {
					 InvoiceSplitUtils.splitInvoiceshs((List)mapResult.get("jymxsqs"), (Map)mapResult.get("zkAndbzk"), new BigDecimal(Double.valueOf(zdje)).setScale(2, BigDecimal.ROUND_HALF_UP), new BigDecimal(Double.valueOf(fpxels[i])).setScale(2, BigDecimal.ROUND_HALF_UP), fphs2, qzfp, spzsfp, 0, splitKpspmxs);
					/*jyspmxs = SeperateInvoiceUtils.splitInvoicesbhs(jyspmxs, new BigDecimal(Double.valueOf(zdje)),
							new BigDecimal(Double.valueOf(fpxels[i])), fphs2, qzfp,spzsfp);*/
				} else {
					/*jyspmxs = SeperateInvoiceUtils.splitInvoices2(jyspmxs, new BigDecimal(Double.valueOf(zdje)),
							new BigDecimal(Double.valueOf(fpxels[i])), fphs2, qzfp,spzsfp);*/
					InvoiceSplitUtils.splitInvoices((List)mapResult.get("jymxsqs"), (Map)mapResult.get("zkAndbzk"), new BigDecimal(Double.valueOf(zdje)).setScale(2, BigDecimal.ROUND_HALF_UP), new BigDecimal(Double.valueOf(fpxels[i])).setScale(2, BigDecimal.ROUND_HALF_UP), fphs2, qzfp, spzsfp, 0, splitKpspmxs);
				}
			} else {
				if (null != fpjehsbzs[i] && "1".equals(fpjehsbzs[i])) {
					/*jyspmxs = SeperateInvoiceUtils.splitInvoicesbhs(jyspmxs, new BigDecimal(Double.valueOf(zdje)),
							new BigDecimal(Double.valueOf(fpxels[i])), fphs1, qzfp,spzsfp);*/
					 InvoiceSplitUtils.splitInvoiceshs((List)mapResult.get("jymxsqs"), (Map)mapResult.get("zkAndbzk"), new BigDecimal(Double.valueOf(zdje)).setScale(2, BigDecimal.ROUND_HALF_UP), new BigDecimal(Double.valueOf(fpxels[i])).setScale(2, BigDecimal.ROUND_HALF_UP), fphs1, qzfp, spzsfp, 0, splitKpspmxs);

				} else {
					/*jyspmxs = SeperateInvoiceUtils.splitInvoices2(jyspmxs, new BigDecimal(Double.valueOf(zdje)),
							new BigDecimal(Double.valueOf(fpxels[i])), fphs1, qzfp,spzsfp);*/
					InvoiceSplitUtils.splitInvoices((List)mapResult.get("jymxsqs"), (Map)mapResult.get("zkAndbzk"), new BigDecimal(Double.valueOf(zdje)).setScale(2, BigDecimal.ROUND_HALF_UP), new BigDecimal(Double.valueOf(fpxels[i])).setScale(2, BigDecimal.ROUND_HALF_UP), fphs1, qzfp, spzsfp, 0, splitKpspmxs);

				}
			}
			
			if(null == splitKpspmxs || splitKpspmxs.isEmpty()){
				session.setAttribute("listsq", new ArrayList());
				session.setAttribute("mxList", mxList);
				if (listsq.size() == 1) {
					session.setAttribute("bckkje", bckkje);
				}
				
				result.put("recordsTotal", listfpcl.size());
				result.put("recordsFiltered", listfpcl.size());
				result.put("data", listfpcl);
				result.put("msg", "所选信息不能进行整数分票!");
				return result;
			}
			
			// 保存进交易流水
			Map<Integer, List<JyspmxDecimal2>> fpMap = new HashMap<>();
			for (JyspmxDecimal2 jysmx : splitKpspmxs) {
				Date date = new Date();
				Long long1 = date.getTime();
				FpcljlVo fpcljlVo = new FpcljlVo(Integer.valueOf(jyxxsq.getSqlsh()), Integer.valueOf(jysmx.getSpmxxh()),
						jysmx.getSpdm(), jysmx.getSpmc(), jysmx.getSpggxh(), jysmx.getSpdw(),
						jysmx.getSps() == null ? null : jysmx.getSps().doubleValue(),
						jysmx.getSpdj() == null ? null : jysmx.getSpdj().doubleValue(),
						jysmx.getSpje() == null ? null : jysmx.getSpje().doubleValue(),
						jysmx.getSpsl() == null ? null : jysmx.getSpsl().doubleValue(),
						jysmx.getSpse() == null ? null : jysmx.getSpse().doubleValue(),
						jysmx.getJshj() == null ? null : jysmx.getJshj().doubleValue(),
						jysmx.getYkphj() == null ? null : jysmx.getYkphj().doubleValue(), jysmx.getHzkpxh(),
						jysmx.getLrsj(), jysmx.getLrry(), jysmx.getXgsj(), jysmx.getXgry(),
						Integer.valueOf(jysmx.getFpnum()), long1, jysmx.getFphxz(), jyxxsq.getKpddm(), jyxxsq.getGfsh(),
						jyxxsq.getGfmc(), jyxxsq.getGfdz(), String.valueOf(long1), String.valueOf(jyxxsq.getSqlsh()),
						Integer.valueOf(jysmx.getSpmxxh()), jyxxsq.getXfid(), jyxxsq.getFpzldm(), "1",
						Integer.valueOf(1), jysmx.getGsdm());
				fpcljlVo.setSjts(i + 1);
				if (fpcljlVo.getJshj() > 0) {
					listfpcl.add(fpcljlVo);
				}
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
				List<JyspmxDecimal2> fpJyspmxList1 = new ArrayList<>();
				for (JyspmxDecimal2 jyspmxDecimal2 : fpJyspmxList) {
					if (jyspmxDecimal2.getJshj() != null
							&& jyspmxDecimal2.getJshj().compareTo(new BigDecimal("0")) > 0) {
						fpJyspmxList1.add(jyspmxDecimal2);
					}
				}
				if (null != fpJyspmxList1 && fpJyspmxList1.size() > 0) {
					mxList.add(fpJyspmxList1);
				}

				/*
				 * Jyls jyls = saveJyls(jyxxsq, fpJyspmxList); saveKpspmx(jyls,
				 * fpJyspmxList);
				 */
				// fpNumKplshMap.put(fpNum, kpls.getKplsh());
			}

			i++;
		}
		session.setAttribute("listsq", listsq);
		session.setAttribute("mxList", mxList);
		if (listsq.size() == 1) {
			session.setAttribute("bckkje", bckkje);
		}
		/*
		 * for (String str : sqlsht) { Jyxxsq jyxxsq =
		 * jyxxsqService.findOne(Integer.valueOf(str)); Jyls jyls1 = new Jyls();
		 * jyls1.setDdh(jyxxsq.getDdh()); String jylsh = "JY" + new
		 * SimpleDateFormat("yyyyMMddHHmmssSS").format(new Date());
		 * jyls1.setJylsh(jylsh); jyls1.setJylssj(TimeUtil.getNowDate());
		 * jyls1.setFpzldm(jyxxsq.getFpzldm()); jyls1.setFpczlxdm("11");
		 * jyls1.setXfid(jyxxsq.getXfid()); jyls1.setXfsh(jyxxsq.getXfsh());
		 * jyls1.setXfmc(jyxxsq.getXfmc()); jyls1.setXfyh(jyxxsq.getXfyh());
		 * jyls1.setXfyhzh(jyxxsq.getXfyhzh());
		 * jyls1.setXflxr(jyxxsq.getXflxr()); jyls1.setXfdh(jyxxsq.getXfdh());
		 * jyls1.setXfdz(jyxxsq.getXfdz()); jyls1.setGfid(jyxxsq.getGfid());
		 * jyls1.setGfsh(jyxxsq.getGfsh()); jyls1.setGfmc(jyxxsq.getGfmc());
		 * jyls1.setGfyh(jyxxsq.getGfyh()); jyls1.setGfyhzh(jyxxsq.getGfyhzh());
		 * jyls1.setGflxr(jyxxsq.getGflxr()); jyls1.setGfdh(jyxxsq.getGfdh());
		 * jyls1.setGfdz(jyxxsq.getGfdz()); jyls1.setGfyb(jyxxsq.getGfyb());
		 * jyls1.setGfemail(jyxxsq.getGfemail()); jyls1.setClztdm("00");
		 * jyls1.setBz(jyxxsq.getBz()); jyls1.setSkr(jyxxsq.getSkr());
		 * jyls1.setKpr(jyxxsq.getKpr()); jyls1.setFhr(jyxxsq.getFhr());
		 * jyls1.setSsyf(jyxxsq.getSsyf()); jyls1.setYfpdm(null);
		 * jyls1.setYfphm(null); jyls1.setHsbz(jyxxsq.getHsbz());
		 * jyls1.setJshj(jyxxsq.getJshj()); jyls1.setYkpjshj(0d);
		 * jyls1.setYxbz("1"); jyls1.setGsdm(jyxxsq.getGsdm());
		 * jyls1.setLrry(getYhid()); jyls1.setLrsj(TimeUtil.getNowDate());
		 * jyls1.setXgry(getYhid()); jyls1.setXgsj(TimeUtil.getNowDate());
		 * jyls1.setSkpid(jyxxsq.getSkpid()); jylsService.save(jyls1);
		 * Map<String, Object> params = new HashMap<>(); params.put("sqlsh",
		 * jyxxsq.getSqlsh()); List<Jymxsq> list =
		 * jymxsqService.findAllByParams(params); for (Jymxsq mxItem : list) {
		 * Jyspmx jymx = new Jyspmx(); jymx.setDjh(jyls1.getDjh());
		 * jymx.setSpmxxh(mxItem.getSpmxxh()); jymx.setSpdm(mxItem.getSpdm());
		 * jymx.setSpmc(mxItem.getSpmc()); jymx.setSpggxh(mxItem.getSpggxh());
		 * jymx.setSpdw(mxItem.getSpdw()); jymx.setSps(mxItem.getSps());
		 * jymx.setSpdj(mxItem.getSpdj() == null ? null : mxItem.getSpdj());
		 * jymx.setSpje(mxItem.getSpje()); jymx.setSpsl(mxItem.getSpsl());
		 * jymx.setSpse(mxItem.getSpse()); jymx.setJshj(mxItem.getJshj());
		 * jymx.setYkphj(0d); jymx.setGsdm(getGsdm());
		 * jymx.setLrsj(TimeUtil.getNowDate()); jymx.setLrry(getYhid());
		 * jymx.setXgsj(TimeUtil.getNowDate()); jymx.setXgry(getYhid());
		 * jymx.setFphxz("0"); jyspmxService.save(jymx); } jyxxsq.setZtbz("2");
		 * cljlService.saveYhcljl(getYhid(), "开票单审核");
		 * jyxxsqService.save(jyxxsq); }
		 */
		result.put("recordsTotal", listfpcl.size());
		result.put("recordsFiltered", listfpcl.size());
		result.put("data", listfpcl);
		result.put("msg", "审核成功!");
		return result;
	}

	@ResponseBody
	@RequestMapping("/cxsp")
	public Map<String, Object> cxsp() {
		Map<String, Object> result = new HashMap<String, Object>();
		Sp sp = new Sp();
		sp.setGsdm(getGsdm());
		List<Sp> sps = spService.findAllByParams(sp);
		result.put("sps", sps);
		return result;
	}

	@ResponseBody
	@RequestMapping("/hqsl")
	public Map<String, Object> hqsl(Integer spid) {
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
	 * @param
	 * @return
	 */
	public Jyls saveJyls(Jyxxsq jyxxsq, List<JyspmxDecimal2> jyspmxList) throws Exception {
		Jyls jyls1 = new Jyls();
		jyls1.setDdh(jyxxsq.getDdh());
		jyls1.setSqlsh(jyxxsq.getSqlsh());
		jyls1.setJylsh(jyxxsq.getJylsh());
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
		jyls1.setSfdyqd(jyxxsq.getSfdyqd());
		jyls1.setBz(jyxxsq.getBz());
		jyls1.setSkr(jyxxsq.getSkr());
		jyls1.setKpr(jyxxsq.getKpr());
		jyls1.setFhr(jyxxsq.getFhr());
		jyls1.setSsyf(jyxxsq.getSsyf());
		jyls1.setYfpdm(null);
		jyls1.setYfphm(null);
		jyls1.setSffsyj(jyxxsq.getSffsyj());
		jyls1.setHsbz(jyxxsq.getHsbz());
		double hjje = 0;
		double hjse = 0;
		for (JyspmxDecimal2 jyspmx : jyspmxList) {
			hjje += jyspmx.getSpje().doubleValue();
			hjse += jyspmx.getSpse().doubleValue();
		}
		jyls1.setJshj(hjje + hjse);
		jyls1.setYkpjshj(0d);
		jyls1.setYxbz("1");
		jyls1.setGsdm(jyxxsq.getGsdm());
		jyls1.setLrry(getYhid());
		jyls1.setLrsj(TimeUtil.getNowDate());
		jyls1.setXgry(getYhid());
		jyls1.setXgsj(TimeUtil.getNowDate());
		jyls1.setSkpid(jyxxsq.getSkpid());
		jyls1.setSqlsh(jyxxsq.getSqlsh());
		jylsService.save(jyls1);
		return jyls1;
	}

	public void saveKpspmx(Jyls jyls, List<JyspmxDecimal2> fpJyspmxList) throws Exception {
		int djh = jyls.getDjh();
		int i=1;
		for (JyspmxDecimal2 mxItem : fpJyspmxList) {
			Jyspmx jymx = new Jyspmx();
			jymx.setDjh(djh);
			jymx.setSpmxxh(i);
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
			i++;
		}
	}

	@ResponseBody
	@RequestMapping("/yhqrbc")
	@Transactional
	public Map<String, Object> yhqrbc() throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Jyxxsq> listsq = (List<Jyxxsq>) session.getAttribute("listsq");
		List<List<JyspmxDecimal2>> mxList = (List<List<JyspmxDecimal2>>) session.getAttribute("mxList");
		Map<Integer, List<JyspmxDecimal2>> fpMap = new HashMap<>();
		if (null != listsq && !listsq.isEmpty()) {
			if (listsq.size() == 1) {
				String[] bckkje = (String[]) session.getAttribute("bckkje");
				Jyxxsq jyxxsq1 = listsq.get(0);
				// 保存交易流水
				for (int i = 0; i < mxList.size(); i++) {
					Jyls jyls = saveJyls(jyxxsq1, mxList.get(i));
					saveKpspmx(jyls, mxList.get(i));
				}
				// 保存审核状态
				double zje = 0d;
				double zje1 = 0d;
				for (String bck : bckkje) {
					zje += Double.valueOf(bck);
				}
				// 保存剩余明细
				Map<String, Object> params = new HashMap<>();
				params.put("sqlsh", jyxxsq1.getSqlsh());
				List<Jymxsq> list = jymxsqService.findAllByParams(params);
				for (int i = 0; i < bckkje.length; i++) {
					zje1 += list.get(i).getKkjje();
					list.get(i).setYkjje(Double.valueOf(bckkje[i]) + list.get(i).getYkjje());
					list.get(i).setKkjje(list.get(i).getJshj() - list.get(i).getYkjje());

				}
				System.out.println(zje);
				System.out.println(zje1);
				if (zje == zje1) {
					jyxxsq1.setZtbz("3");
					cljlService.saveYhcljl(getYhid(), "开票单审核");
					jyxxsqService.save(jyxxsq1);
				} else {
					jyxxsq1.setZtbz("5");
					cljlService.saveYhcljl(getYhid(), "开票单审核");
					jyxxsqService.save(jyxxsq1);
				}
				jymxsqService.save(list);
			} else {
				for (int i = 0; i < mxList.size(); i++) {
					Jyxxsq jyxxsq1 = new Jyxxsq();
					for (Jyxxsq jyxxsq : listsq) {
						if (jyxxsq.getSqlsh().equals(mxList.get(i).get(0).getsqlsh())) {
							jyxxsq1 = jyxxsq;
						}
					}
					Jyls jyls = saveJyls(jyxxsq1, mxList.get(i));
					saveKpspmx(jyls, mxList.get(i));
				}
				for (Jyxxsq jyxxsq : listsq) {
					jyxxsq.setZtbz("3");
					cljlService.saveYhcljl(getYhid(), "开票单审核");
					jyxxsqService.save(jyxxsq);
				}
			}
			result.put("msg", true);
			return result;
		} else {
			result.put("msg", false);
			return result;
		}
	}

	@RequestMapping(value = "/getyscjyxxsqlist")
	@ResponseBody
	public Map getyscjyxxsqlist(int length, int start, int draw, String clztdm, String xfsh, String gfmc, String ddh,
			String fpzldm, String rqq, String rqz,boolean  loaddata2) {
		Pagination pagination = new Pagination();
		pagination.setPageNo(start / length + 1);
		pagination.setPageSize(length);
		List<Xf> xfs = getXfList();
		List<Skp> skps = getSkpList();
		if (xfs != null && xfs.size() > 0) {
			pagination.addParam("xfs", xfs);
		}
		if (skps != null && skps.size() > 0) {
			pagination.addParam("skps", skps);
		}
		if (null != xfsh && !"".equals(xfsh) && !"-1".equals(xfsh)) {
			pagination.addParam("xfsh", xfsh);
		}
		pagination.addParam("gfmc", gfmc);
		pagination.addParam("ddh", ddh);
		if ("".equals(fpzldm)) {
			pagination.addParam("fpzldm", null);
		} else {
			pagination.addParam("fpzldm", fpzldm);
		}

		if (rqq != null && !rqq.trim().equals("") && rqz != null && !rqz.trim().equals("")) { // 名称参数非空时增加名称查询条件
			pagination.addParam("rqq", rqq);
			pagination.addParam("rqz", TimeUtil.getAfterDays(rqz, 1));
		} else if (rqq != null && !rqq.trim().equals("") && (rqz == null || rqz.trim().equals(""))) {
			pagination.addParam("rqq", rqq);
			pagination.addParam("rqz", TimeUtil.getAfterDays(rqq, 1));
		} else if ((rqq == null || rqq.trim().equals("")) && rqz != null && !rqz.trim().equals("")) {
			pagination.addParam("rqq", rqz);
			pagination.addParam("rqz", TimeUtil.getAfterDays(rqz, 1));
		}
		pagination.addParam("clztdm", "00");
		// pagination.addParam("fpzldm", "12");
		pagination.addParam("fpczlxdm", "11");
		pagination.addParam("ztbz", "3");
		pagination.addParam("gsdm", this.getGsdm());
		pagination.addParam("orderBy", "lrsj desc");

		List<JyxxsqVO> jyxxsqList = jyxxsqService.findByPage(pagination);
        for(JyxxsqVO jyxxsqVO:jyxxsqList){
			Jyls jylsparams=new Jyls();
			jylsparams.setSqlsh(Integer.valueOf(jyxxsqVO.getSqlsh()));
			Double f =0.0;
			List<Jyls> jylslist=jylsService.findAllByParams(jylsparams);
			for(Jyls Jyls:jylslist){
				if(Jyls.getClztdm().equals("40")){
					f+=Jyls.getJshj();
				}
			}
			jyxxsqVO.setYkjje(f);
		}


		int total = pagination.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		if(loaddata2){
			result.put("recordsTotal", total);
			result.put("recordsFiltered", total);
			result.put("draw", draw);
			result.put("data", jyxxsqList);
		}else{
			result.put("recordsTotal", 0);
			result.put("recordsFiltered", 0);
			result.put("draw", draw);
			result.put("data", new ArrayList<>());
		}

		return result;
	}

	// 下载模板
	@RequestMapping(value = "/xzmb")
	@ResponseBody
	public void xzmb(Integer mbid) throws IOException {
		// 查询需要的表头
		DrPz drPz = new DrPz();
		drPz.setMbid(mbid);
		List<DrPz> drPzs = drPzService.findAllByParams(drPz);
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("已开发票");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow(0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		HSSFCell cell = null;
		int i = 0;
		for (DrPz drPz2 : drPzs) {
			if (drPz2.getPzlx().equals("config") && null != drPz2.getPzz() && !"".equals(drPz2.getPzz())) {
				cell = row.createCell(i);
				cell.setCellValue(drPz2.getPzz());
				cell.setCellStyle(style);
				i++;
			}
		}
		// SimpleDateFormat timeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String filename = "ImportTemplate.xls";
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		response.setHeader("Content-Disposition",
				"attachment;filename=".concat(String.valueOf(URLEncoder.encode(filename, "UTF-8"))));
		OutputStream out = response.getOutputStream();
		wb.write(out);
		out.close();
	}

	@RequestMapping(value = "/bmlj")
	@ResponseBody
	public Map<String, Object> getxzlj(Integer mbid) {
		Map<String, Object> result = new HashMap<>();
		Drmb drmb = drmbService.findOne(mbid);
		result.put("drmb", drmb);
		return result;
	}
}
