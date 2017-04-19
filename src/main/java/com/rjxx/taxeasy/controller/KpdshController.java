package com.rjxx.taxeasy.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.bizcomm.utils.SeperateInvoiceUtils;
import com.rjxx.taxeasy.domains.DrPz;
import com.rjxx.taxeasy.domains.Drmb;
import com.rjxx.taxeasy.domains.Fpgz;
import com.rjxx.taxeasy.domains.Hbgz;
import com.rjxx.taxeasy.domains.Jyls;
import com.rjxx.taxeasy.domains.Jymxsq;
import com.rjxx.taxeasy.domains.Jyspmx;
import com.rjxx.taxeasy.domains.Jyxxsq;
import com.rjxx.taxeasy.domains.Skp;
import com.rjxx.taxeasy.domains.Sm;
import com.rjxx.taxeasy.domains.Sp;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.filter.SystemControllerLog;
import com.rjxx.taxeasy.service.DrPzService;
import com.rjxx.taxeasy.service.DrmbService;
import com.rjxx.taxeasy.service.FpgzService;
import com.rjxx.taxeasy.service.HbgzService;
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
import com.rjxx.utils.Randomutil;

@Controller
@RequestMapping("/kpdsh")
public class KpdshController extends BaseController {
	 // 默认除法运算精度
    private static final Integer DEF_DIV_SCALE = 2;
	static int fpxh = 1;
	static int clxh = 1;
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
	private HbgzService hbgzService;
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
			String spmc, String gfmc, String xfsh, String fpzldm) throws Exception {
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
		pagination.addParam("order", "ddh");
		pagination.addParam("ord", "desc");
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
			if ("01".equals(jyxxsqVO.getFpzldm())) {
				if (skp.getZpfz() != null && (skp.getZpfz() > 0)) {
					fpje = skp.getZpfz();
				} else if (skp.getZpmax() != null && (skp.getZpmax() > 0)) {
					fpje = skp.getZpmax();
				}
				zdje = skp.getZpmax();
			} else if ("02".equals(jyxxsqVO.getFpzldm())) {
				if (skp.getPpfz() != null && (skp.getPpfz() > 0)) {
					fpje = skp.getPpfz();
				} else if (skp.getPpmax() != null && (skp.getPpmax() > 0)) {
					fpje = skp.getPpmax();
				}
				zdje = skp.getPpmax();
			} else if ("12".equals(jyxxsqVO.getFpzldm())) {
				if (skp.getFpfz() != null && (skp.getFpfz()) > 0) {
					fpje = skp.getFpfz();
				} else if (skp.getDpmax() != null && (skp.getDpmax() > 0)) {
					fpje = skp.getDpmax();
				}
				zdje = skp.getDpmax();
			}
			boolean flag = false;
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
			if (!flag) {
				Map<String, Object> params = new HashMap<>();
				params.put("mrbz", "1");
				Fpgz fpgz2 = fpgzService.findOneByParams(params);
				if (null != fpgz2) {
					if ("01".equals(jyxxsqVO.getFpzldm())) {
						fpje = fpgz2.getZpxe();
					} else if ("02".equals(jyxxsqVO.getFpzldm())) {
						fpje = fpgz2.getPpxe();
					} else if ("12".equals(jyxxsqVO.getFpzldm())) {
						fpje = fpgz2.getDzpxe();
					}
					hsbz = fpgz2.getHsbz();
					qdbz = fpgz2.getQdbz();
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
	@SystemControllerLog(description = "开票单审核退回", key = "ddhs")
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
	public Map<String, Object> kpdshkp(String sqlshs, String fpxes, String bckpje, String fpjshsbz, String qdbzs,String sfhbkp,String fpzldm)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		if (null!=sfhbkp&&sfhbkp.equals("1")) {
			Map<String, Object> resu= this.hbcl(sqlshs, fpxes, fpjshsbz, qdbzs, fpzldm);
			List<FpcljlVo> fpcljlVos = (List<FpcljlVo>) resu.get("rows");
			if (null!=fpcljlVos) {
				result.put("recordsTotal", fpcljlVos.size());
				result.put("recordsFiltered", fpcljlVos.size());
				result.put("data", resu.get("rows"));
				result.put("msg", "审核成功!");
			}else{
				result.put("data", null);
				result.put("msg", resu.get("msg"));
			}
			
			return result;
		}else{
	
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
			List<JyspmxDecimal2> jyspmxs = jyspmxService.getNeedToKP3(params1);

			// 价税分离
			if ("1".equals(jyxxsq.getHsbz())) {
				jyspmxs = SeperateInvoiceUtils.separatePrice2(jyspmxs);
			}
			if (sqlsht.length == 1) {
				for (int j = 0; j < jyspmxs.size(); j++) {
					jyspmxs.get(j).setJshj(new BigDecimal(bckkje[j]));
					jyspmxs.get(j).setSpje(jyspmxs.get(j).getJshj()
							.divide(new BigDecimal("1").add(jyspmxs.get(j).getSpsl()), 6, BigDecimal.ROUND_HALF_UP));
					jyspmxs.get(j).setSpse(jyspmxs.get(j).getJshj().subtract(jyspmxs.get(j).getSpje()));
					if (jyspmxs.get(j).getSpdj() != null) {
						jyspmxs.get(j).setSps(
								jyspmxs.get(j).getJshj().divide(jyspmxs.get(j).getSpdj(), 6, BigDecimal.ROUND_HALF_UP));
					} else {
						jyspmxs.get(j).setSps(null);
					}
				}
			} else {
				for (int j = 0; j < jyspmxs.size(); j++) {
					jyspmxs.get(j).setJshj(jyspmxs.get(j).getKkjje());
					jyspmxs.get(j).setSpje(jyspmxs.get(j).getJshj()
							.divide(new BigDecimal("1").add(jyspmxs.get(j).getSpsl()), 6, BigDecimal.ROUND_HALF_UP));
					jyspmxs.get(j).setSpse(jyspmxs.get(j).getJshj().subtract(jyspmxs.get(j).getSpje()));
					if (jyspmxs.get(j).getSpdj() != null) {
						jyspmxs.get(j).setSps(
								jyspmxs.get(j).getJshj().divide(jyspmxs.get(j).getSpdj(), 6, BigDecimal.ROUND_HALF_UP));
					} else {
						jyspmxs.get(j).setSps(null);
					}
				}
			}
			int fphs1 = 8;
			int fphs2 = 100;
			double zdje = 0d;
			boolean flag = false;
			boolean qzfp = true;
			List<Fpgz> listt = fpgzService.findAllByParams(new HashMap<>());
			Xf x = new Xf();
			x.setGsdm(getGsdm());
			x.setXfsh(jyxxsq.getXfsh());
			Xf xf = xfService.findOneByParams(x);
			Skp skp = skpService.findOne(jyxxsq.getSkpid());
			if (null != qdbz[i] && qdbz[i].equals("1")) {
				fphs1 = 99999;
				fphs2 = 99999;
			} else {
				for (Fpgz fpgz : listt) {
					if (fpgz.getXfids().contains(String.valueOf(xf.getId()))) {
						if ("01".equals(jyxxsq.getFpzldm())) {
							fphs1 = fpgz.getZphs();
						} else if ("02".equals(jyxxsq.getFpzldm())) {
							fphs1 = fpgz.getPphs();
						} else if ("12".equals(jyxxsq.getFpzldm())) {
							fphs2 = fpgz.getDzphs();
						}
						if (fpgz.getSfqzfp().equals("0")) {
							qzfp = false;
						}
						flag = true;
						break;
					}
				}
				if (!flag) {
					Map<String, Object> paramse = new HashMap<>();
					paramse.put("mrbz", "1");
					paramse.put("gsdm", getGsdm());
					Fpgz fpgz2 = fpgzService.findOneByParams(paramse);
					if (null != fpgz2) {
						if ("01".equals(jyxxsq.getFpzldm())) {
							fphs1 = fpgz2.getZphs();
						} else if ("02".equals(jyxxsq.getFpzldm())) {
							fphs1 = fpgz2.getPphs();
						} else if ("12".equals(jyxxsq.getFpzldm())) {
							fphs2 = fpgz2.getDzphs();
						}
						if (fpgz2.getSfqzfp().equals("0")) {
							qzfp = false;
						}
					}
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
			if (jyxxsq.getFpzldm().equals("12")) {
				if (null != fpjehsbzs[i] && "1".equals(fpjehsbzs[i])) {
					jyspmxs = SeperateInvoiceUtils.splitInvoicesbhs(jyspmxs, new BigDecimal(Double.valueOf(zdje)),
							new BigDecimal(Double.valueOf(fpxels[i])), fphs2, qzfp);
				} else {
					jyspmxs = SeperateInvoiceUtils.splitInvoices2(jyspmxs, new BigDecimal(Double.valueOf(zdje)),
							new BigDecimal(Double.valueOf(fpxels[i])), fphs2, qzfp);
				}
			} else {
				if (null != fpjehsbzs[i] && "1".equals(fpjehsbzs[i])) {
					jyspmxs = SeperateInvoiceUtils.splitInvoicesbhs(jyspmxs, new BigDecimal(Double.valueOf(zdje)),
							new BigDecimal(Double.valueOf(fpxels[i])), fphs1, qzfp);
				} else {
					jyspmxs = SeperateInvoiceUtils.splitInvoices2(jyspmxs, new BigDecimal(Double.valueOf(zdje)),
							new BigDecimal(Double.valueOf(fpxels[i])), fphs1, qzfp);
				}
			}
			// 保存进交易流水
			Map<Integer, List<JyspmxDecimal2>> fpMap = new HashMap<>();
			for (JyspmxDecimal2 jysmx : jyspmxs) {
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
	 * @param jyls
	 * @return
	 */
	public Jyls saveJyls(Jyxxsq jyxxsq, List<JyspmxDecimal2> jyspmxList) throws Exception {
		Jyls jyls1 = new Jyls();
		jyls1.setDdh(jyxxsq.getDdh());
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
		jylsService.save(jyls1);
		return jyls1;
	}

	public void saveKpspmx(Jyls jyls, List<JyspmxDecimal2> fpJyspmxList) throws Exception {
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

	@ResponseBody
	@RequestMapping("/yhqrbc")
	@Transactional
	public Map<String, Object> yhqrbc() throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Jyxxsq> listsq = (List<Jyxxsq>) session.getAttribute("listsq");
		List<List<JyspmxDecimal2>> mxList = (List<List<JyspmxDecimal2>>) session.getAttribute("mxList");
		Map<Integer, List<JyspmxDecimal2>> fpMap = new HashMap<>();
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
	}

	@RequestMapping(value = "/getyscjyxxsqlist")
	@ResponseBody
	public Map getyscjyxxsqlist(int length, int start, int draw, String clztdm, String xfsh, String gfmc, String ddh,
			String fpzldm, String rqq, String rqz) {
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
		int total = pagination.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		result.put("draw", draw);
		result.put("data", jyxxsqList);
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

	public Map<String, Object> hbcl(String sqlshs, String fpxe, String fpjshsbz, String qdbzs,String fpzldm) throws Exception {
		Map<String, Object> result = new HashMap<>();
		List<FpcljlVo> resultlist = new ArrayList<>();
		// 查询默认合并规则
		Map<String, Object> pMap = new HashMap<>();
		pMap.put("gsdm", getGsdm());
		pMap.put("mrbz", "1");
		Hbgz hbgz = hbgzService.findOneByParams(pMap);
		if (null == hbgz) {
			result.put("msg", "无默认合并规则,请维护后使用");
			return result;
		}
		String[] sqlsht = sqlshs.split(",");
		List<Jyxxsq> jyxxsqList = new ArrayList<>();
		List<FpcljlVo> FpcljlVoList = new ArrayList<>();
		long kpsqh = Randomutil.next();
		// 查询主分表数据
		int i=1;
		for (String st : sqlsht) {
			Jyxxsq jyxxsq = jyxxsqService.findOne(Integer.valueOf(st));
			jyxxsqList.add(jyxxsq);
			Map<String, Object> params1 = new HashMap<>();
			params1.put("sqlsh", st);
			List<JyspmxDecimal2> jyspmxs = jyspmxService.getNeedToKP3(params1);
			// 明细价税分离
			if ("1".equals(jyxxsq.getHsbz())) {
				jyspmxs = SeperateInvoiceUtils.separatePrice2(jyspmxs);
			}
			for (JyspmxDecimal2 jyspmxDecimal2 : jyspmxs) {
				FpcljlVo fpcljlVo = new FpcljlVo(jyspmxDecimal2, jyxxsq.getGfmc(), jyxxsq.getGfsh(), kpsqh,jyxxsq.getXfid(),jyxxsq.getSkpid());
				fpcljlVo.setSjts(i);
				FpcljlVoList.add(fpcljlVo);
			}
			i++;
		}
		// 按照购方进行数据分组
		Map<String, Object> par = new HashMap<>();
		par.put("sqlshs", sqlshs.split(","));
		List<Jyxxsq> list1 = jyxxsqService.findDisGf(par);
		List<ArrayList<FpcljlVo>> list = khhb1(FpcljlVoList, list1);
		// 校验同客户商品金额是否大于零
		Map<Integer, List> map = this.lshb(FpcljlVoList);
		double khje1 = 0;
		for (Integer key : map.keySet()) {
			List<FpcljlVo> list2 = map.get(key);
			for (FpcljlVo fpcljlVo : list2) {
				khje1 += fpcljlVo.getSpje();
			}
			if (khje1 <= 0) {
				result.put("success", false);
				result.put("msg",
						"交易流水号为:" + list2.get(0).getJylsh() + "的,客户名称为:" + list2.get(0).getGfmc() + "的客户，明细金额不大于0");
				return result;
			} else {
				khje1 = 0;
			}
		}
		this.khhb(list);
		for (ArrayList<FpcljlVo> arrayList : list) {
			if (hbgz.getPxbz().equals("1")) {
				this.sphb(arrayList);
			} else if (hbgz.getSpmcbz().equals("1")) {
				this.sphb1(arrayList);
			} else {
				this.sphb2(arrayList);
			}
		}
		//排序
		Comparator<FpcljlVo> comparator = new Comparator<FpcljlVo>() {
			@Override
			public int compare(FpcljlVo o1, FpcljlVo o2) {
				if (Double.valueOf(o1.getClmxxh()) > Double.valueOf(o2.getClmxxh())) {
					return 1;
				}
				if (o1.getClmxxh() == o2.getClmxxh()) {
					return 0;
				}
				return -1;
			}
		};
		//进行分票
		int fphs1 = 8;
		int fphs2 = 100;
		double zdje = 0d;
		boolean flag = false;
		boolean qzfp = true;
		List<Fpgz> listt = fpgzService.findAllByParams(new HashMap<>());
		for (ArrayList<FpcljlVo> arrayList : list) {
			List<FpcljlVo> arrayList1 = new ArrayList<>();
			arrayList1.addAll(arrayList);
			Collections.sort(arrayList1, comparator);
			Xf xf = xfService.findOne(arrayList.get(0).getXfid());
			Skp skp = skpService.findOne(arrayList.get(0).getSkpid());
			if (null != qdbzs && qdbzs.equals("1")) {
				fphs1 = 99999;
				fphs2 = 99999;
			} else {
				for (Fpgz fpgz : listt) {
					if (fpgz.getXfids().contains(String.valueOf(xf.getId()))) {
						if ("01".equals(fpzldm)) {
							fphs1 = fpgz.getZphs();
						} else if ("02".equals(fpzldm)) {
							fphs1 = fpgz.getPphs();
						} else if ("12".equals(fpzldm)) {
							fphs2 = fpgz.getDzphs();
						}
						if (fpgz.getSfqzfp().equals("0")) {
							qzfp = false;
						}
						flag = true;
						break;
					}
				}
				if (!flag) {
					Map<String, Object> paramse = new HashMap<>();
					paramse.put("mrbz", "1");
					paramse.put("gsdm", getGsdm());
					Fpgz fpgz2 = fpgzService.findOneByParams(paramse);
					if (null != fpgz2) {
						if ("01".equals(fpzldm)) {
							fphs1 = fpgz2.getZphs();
						} else if ("02".equals(fpzldm)) {
							fphs1 = fpgz2.getPphs();
						} else if ("12".equals(fpzldm)) {
							fphs2 = fpgz2.getDzphs();
						}
						if (fpgz2.getSfqzfp().equals("0")) {
							qzfp = false;
						}
					}
				}
			}
			if ("01".equals(fpzldm)) {
				zdje = skp.getZpmax();
			} else if ("02".equals(fpzldm)) {
				zdje = skp.getPpmax();
			} else if ("12".equals(fpzldm)) {
				zdje = skp.getDpmax();
			}
			resultlist.addAll(this.fpgz(arrayList1,new BigDecimal(Double.valueOf(zdje)),new BigDecimal(Double.valueOf(fpxe)),fpzldm, fphs1, qzfp,fpxh));
		}
		fpxh = 1;
		clxh = 1;
		result.put("rows", resultlist);
		result.put("total", resultlist.size());
		return result;
	}

	/**
	 * 合并规则处理(客户并添加序号)
	 */
	public List<ArrayList<FpcljlVo>> khhb1(List<FpcljlVo> list, List<Jyxxsq> jylslist) throws Exception {// 参数为明细list和交易流水list
		// 按照客户组进行合并
		List<ArrayList<FpcljlVo>> resultlist = new ArrayList<>();
		for (Jyxxsq jyls : jylslist) {
			ArrayList<FpcljlVo> list5 = new ArrayList<>();
			for (FpcljlVo jyspmxcl : list) {
				if ((jyls.getGfmc().equals(jyspmxcl.getGfmc())
						&& (null == jyls.getGfsh() && null == jyspmxcl.getGfsh()))
						|| ((jyls.getGfmc().equals(jyspmxcl.getGfmc()))
								&& (null != jyls.getGfsh() && null != jyspmxcl.getGfsh())
								&& jyls.getGfsh().equals(jyspmxcl.getGfsh()))) {
					list5.add(jyspmxcl);
				}
			}
			resultlist.add(list5);
		}
		return resultlist;
	}

	public Map<Integer, List> lshb(List<FpcljlVo> list) {
		Map<Integer, List> map = new HashMap<Integer, List>();
		for (int i = 0; i < list.size(); i++) {
			FpcljlVo user = list.get(i);
			Integer djh = user.getDjh();
			if (map.containsKey(djh)) {
				map.get(djh).add(user);
			} else {
				List l = new ArrayList();
				l.add(user);
				map.put(djh, l);
			}
		}
		return map;
	}

	/**
	 * 合并规则处理(相同客户合并)
	 */
	public void khhb(List<ArrayList<FpcljlVo>> list) throws Exception {
		for (ArrayList<FpcljlVo> arrayList : list) {
			for (FpcljlVo fpcljlVo : arrayList) {
				fpcljlVo.setClxh(fpcljlVo.getKpsqh() + "" + clxh);
			}
			clxh++;
		}
	}

	/**
	 * 合并规则处理(相同商品合并)
	 */
	public void sphb(ArrayList<FpcljlVo> arrayList) throws Exception {
		int clmxxh = 1;
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < arrayList.size() - 1; i++) {
			FpcljlVo Jyspmxcl = arrayList.get(i);
			if (list.contains(Jyspmxcl.getClmxxh())) {
				continue;
			}
			Jyspmxcl.setClmxxh(clmxxh);
			for (int j = i + 1; j < arrayList.size(); j++) {
				FpcljlVo Jyspmxcl1 = arrayList.get(j);
				if (((Jyspmxcl.getSpdm() == null && Jyspmxcl1.getSpdm() == null)
						|| ((Jyspmxcl.getSpdm() != null && Jyspmxcl1.getSpdm() != null)
								&& (Jyspmxcl.getSpdm().equals(Jyspmxcl1.getSpdm()))))
						&& Jyspmxcl.getSpmc().equals(Jyspmxcl1.getSpmc())
						&& ((Jyspmxcl.getSpdj() == null && Jyspmxcl1.getSpdj() == null)
								|| ((Jyspmxcl.getSpdj() != null && Jyspmxcl1.getSpdj() != null)
										&& (Jyspmxcl.getSpdj().equals(Jyspmxcl1.getSpdj()))))
						&& ((Jyspmxcl.getSpdw() == null && Jyspmxcl1.getSpdw() == null)
								|| ((Jyspmxcl.getSpdw() != null && Jyspmxcl1.getSpdw() != null)
										&& (Jyspmxcl.getSpdw().equals(Jyspmxcl1.getSpdw()))))
						&& ((Jyspmxcl.getSpggxh() == null && Jyspmxcl1.getSpggxh() == null)
								|| ((Jyspmxcl.getSpggxh() != null && Jyspmxcl1.getSpggxh() != null)
										&& (Jyspmxcl.getSpggxh().equals(Jyspmxcl1.getSpggxh()))))
						&& Jyspmxcl.getSpsl().equals(Jyspmxcl1.getSpsl())) {
					Jyspmxcl1.setClmxxh(clmxxh);
				}
			}
			list.add(clmxxh);
			clmxxh++;
		}
		if (arrayList.size() == 1) {
			arrayList.get(0).setClmxxh(clmxxh);
		}
		if (arrayList.get(arrayList.size() - 1).getClmxxh() == null) {
			arrayList.get(arrayList.size() - 1).setClmxxh(clmxxh);
		}
	}

	/**
	 * 合并规则处理(相同商品合并)
	 */
	public void sphb1(ArrayList<FpcljlVo> arrayList) throws Exception {// 参数为明细list和购方客户组id
		int clmxxh = 1;
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < arrayList.size() - 1; i++) {
			FpcljlVo Jyspmxcl = arrayList.get(i);
			if (list.contains(Jyspmxcl.getClmxxh())) {
				continue;
			}
			Jyspmxcl.setClmxxh(clmxxh);
			for (int j = i + 1; j < arrayList.size(); j++) {
				FpcljlVo Jyspmxcl1 = arrayList.get(j);
				if (((Jyspmxcl.getSpdm() == null && Jyspmxcl1.getSpdm() == null)
						|| ((Jyspmxcl.getSpdm() != null && Jyspmxcl1.getSpdm() != null)
								&& (Jyspmxcl.getSpdm().equals(Jyspmxcl1.getSpdm()))))
						&& Jyspmxcl.getSpmc().equals(Jyspmxcl1.getSpmc())
						&& Jyspmxcl.getSpsl().equals(Jyspmxcl1.getSpsl())) {
					Jyspmxcl1.setClmxxh(clmxxh);
				}
			}
			list.add(clmxxh);
			clmxxh++;
		}
		if (arrayList.size() == 1) {
			arrayList.get(0).setClmxxh(clmxxh);
		}
		if (arrayList.get(arrayList.size() - 1).getClmxxh() == null) {
			arrayList.get(arrayList.size() - 1).setClmxxh(clmxxh);
		}
	}

	/**
	 * 合并规则处理(相同商品合并)商品不合并
	 */
	public void sphb2(ArrayList<FpcljlVo> arrayList) throws Exception {// 参数为明细list和购方客户组id
		// 相同商品进行合并
		int clmxxh = 1;
		for (FpcljlVo fpcljlVo : arrayList) {
			fpcljlVo.setClmxxh(clmxxh);
			clmxxh++;
		}

	}
	/**
	 * 分票规则处理
	 */
	public List<FpcljlVo> fpgz(List<FpcljlVo> jyspmxs,BigDecimal zdje,BigDecimal fpje, String fpzldm,int fphs,boolean qzfp, int fpnum) throws Exception {// 参数为明细list和购方客户组id
		// 对不同规则进行判断
		if (jyspmxs.size() == 0) {
			return jyspmxs;
		}
		Double maxje = zdje.doubleValue();
		Double total = 0d;
	        for (FpcljlVo jyspmx : jyspmxs) {
				total +=jyspmx.getSpje();
		}
	    if (!qzfp&&total<zdje.doubleValue()) {
				maxje=zdje.doubleValue();
		}else{
			maxje=fpje.doubleValue();
		}   
		Double maxfpje = fpje.doubleValue();
	
		if (fpzldm.equals("12")) {
			if (fphs>100) {
				fphs=100;
			}
		}else{
			if (fphs>8) {
				fphs=8;
			}
		}
		/*
		 * 拆分发票
		 */
		int fpbj =0;
		int mxnum = fphs;
		int mxsl = jyspmxs.size();
		if (mxsl != 0 && mxsl <= fphs) {
			mxnum = mxsl;
		}
		List<FpcljlVo> tempJyspmxs = new ArrayList<FpcljlVo>();// 缓存商品明细表
		List<FpcljlVo> splitKpspmxs = new ArrayList<FpcljlVo>();// 拆分发票后的list
		double zje = 0.00;// 汇总金额
		// int fpnum = fpxh+1;
		Integer djh;
		Integer spmxxh;
		long kpsqh;
		String fphxz;
		Timestamp lrsj;
		String spdm;
		Integer lrry;
		//查clmxxh的最大值
		List<List<FpcljlVo>> list3 = new ArrayList<>();
		int maxclmxxh=jyspmxs.get(jyspmxs.size()-1).getClmxxh();
		for (int i = 1; i <=maxclmxxh; i++) {
			List<FpcljlVo> list = new ArrayList<>();
			for (FpcljlVo list2 : jyspmxs) {
				if (list2.getClmxxh()==i) {
					list.add(list2);
				}
				
			}
			list3.add(list);
		}
		for (int k = 1; k <= maxclmxxh; k++) {
			fpbj++;
				for (int i = 0; i < list3.get(k-1).size(); i++) {
					FpcljlVo jyspmx = new FpcljlVo();
					jyspmx = list3.get(k-1).get(i);
					djh = jyspmx.getDjh();
					kpsqh = jyspmx.getKpsqh();
					fphxz = jyspmx.getFphxz();
					spmxxh = jyspmx.getSpmxxh();
					tempJyspmxs.add(jyspmx);
					zje = zje + jyspmx.getSpje();
					lrsj = jyspmx.getLrsj();
					spdm = jyspmx.getSpdm();
					lrry = jyspmx.getLrry();
					if (zje >= maxje) {
							// Jyspmx ccjyspmx = new Jyspmx();//超出金额对象
							FpcljlVo cfjyspmx = new FpcljlVo();// 拆分金额对象
							// ccjyspmx = jyspmx;//超出金额对象
							// cfjyspmx = jyspmx;//拆分金额对象
							// 商品名称
							String spmc = jyspmx.getSpmc();
							// 规格型号
							String spggxh = jyspmx.getSpggxh();
							// 单位
							String spdw = jyspmx.getSpdw();
							// 单价
							Double spdj = jyspmx.getSpdj();
							// 税率
							Double spsl = jyspmx.getSpsl();
							Double spje = jyspmx.getSpje();// 原商品金额
							Double spsm = jyspmx.getSps();// 原商品数量
							Double spse = jyspmx.getSpse();// 原商品税额
							Double ccje = sub(zje, maxje);// 超出金额
							Double cfje = sub(spje, ccje);// 拆分金额
							Double cfsm = div(spsm, div(spje, cfje, 100));// 拆分数量
							Double cfse = div(spse, div(spje, cfje, 100));// 拆分税额
							Double jshj = add(cfje, cfse);
							cfjyspmx.setKpsqh(kpsqh);
							cfjyspmx.setFphxz(fphxz);
							cfjyspmx.setDjh(djh);
							cfjyspmx.setSpmxxh(spmxxh);
							cfjyspmx.setSpje(cfje);
							cfjyspmx.setSps(cfsm);
							cfjyspmx.setSpse(cfse);
							cfjyspmx.setFpnum(fpnum);
							cfjyspmx.setClmxxh(jyspmx.getClmxxh());
							cfjyspmx.setClxh(jyspmx.getClxh());
							cfjyspmx.setGfsh(jyspmx.getGfsh());
							cfjyspmx.setFpzldm(jyspmx.getFpzldm());
							cfjyspmx.setXfid(jyspmx.getXfid());
							cfjyspmx.setJylsh(jyspmx.getJylsh());
							cfjyspmx.setSpmc(spmc);
							cfjyspmx.setGfmc(jyspmx.getGfmc());
							cfjyspmx.setSpggxh(spggxh);
							cfjyspmx.setSpdw(spdw);
							cfjyspmx.setSpdj(spdj);
							cfjyspmx.setSpsl(spsl);
							cfjyspmx.setJshj(jshj);
							cfjyspmx.setYkphj(0d);
							cfjyspmx.setLrsj(lrsj);
							cfjyspmx.setSpdm(spdm);
							cfjyspmx.setLrry(lrry);
							splitKpspmxs.add(cfjyspmx);

							int n = (int) Math.floor(div(ccje, maxje,100));
							Double cfsm1 = 0.00;
							Double cfse1 = 0.00;
							if (n > 0) {
								cfsm1 = div(spsm, div(spje, maxje, 100));// 拆分数量
								cfse1 = div(spse, div(spje, maxje, 100));// 拆分税额
								for (int j = 0; j < n; j++) {
									FpcljlVo ccjyspmx1 = new FpcljlVo();
									// ccjyspmx1 = ccjyspmx;
									fpnum++;
									double jshj1 = add(maxje, cfse1);
									ccjyspmx1.setKpsqh(kpsqh);
									ccjyspmx1.setFphxz(fphxz);
									ccjyspmx1.setDjh(djh);
									ccjyspmx1.setSpmxxh(spmxxh);
									ccjyspmx1.setSpje(maxje);
									ccjyspmx1.setSps(cfsm1);
									ccjyspmx1.setSpse(cfse1);
									ccjyspmx1.setFpnum(fpnum);
									ccjyspmx1.setClmxxh(jyspmx.getClmxxh());
									ccjyspmx1.setSpmc(spmc);
									ccjyspmx1.setXfid(jyspmx.getXfid());
									ccjyspmx1.setGfmc(jyspmx.getGfmc());
									ccjyspmx1.setJylsh(jyspmx.getJylsh());
									ccjyspmx1.setGfsh(jyspmx.getGfsh());
									ccjyspmx1.setFpzldm(jyspmx.getFpzldm());
									ccjyspmx1.setSpggxh(spggxh);
									ccjyspmx1.setSpdw(spdw);
									ccjyspmx1.setSpdj(spdj);
									ccjyspmx1.setSpsl(spsl);
									ccjyspmx1.setJshj(jshj1);
									ccjyspmx1.setYkphj(0d);
									ccjyspmx1.setLrsj(lrsj);
									ccjyspmx1.setSpdm(spdm);
									ccjyspmx1.setLrry(lrry);
									ccjyspmx1.setClxh(jyspmx.getClxh());
									splitKpspmxs.add(ccjyspmx1);

								}
							}
							ccje = sub(ccje, mul(n, maxje));
							FpcljlVo ccjyspmx2 = new FpcljlVo();
							// ccjyspmx2 = ccjyspmx;
							ccjyspmx2.setKpsqh(kpsqh);
							ccjyspmx2.setFphxz(fphxz);
							ccjyspmx2.setDjh(djh);
							ccjyspmx2.setSpmxxh(spmxxh);
							ccjyspmx2.setSpje(ccje);
							ccjyspmx2.setSpmc(spmc);
							ccjyspmx2.setGfsh(jyspmx.getGfsh());
							ccjyspmx2.setJylsh(jyspmx.getJylsh());
							ccjyspmx2.setGfmc(jyspmx.getGfmc());
							ccjyspmx2.setFpzldm(jyspmx.getFpzldm());
							ccjyspmx2.setSpggxh(spggxh);
							ccjyspmx2.setSpdj(spdj);
							ccjyspmx2.setSpsl(spsl);
							ccjyspmx2.setSpdw(spdw);
							ccjyspmx2.setSps(sub(sub(spsm, cfsm),mul(n, cfsm1)));
							ccjyspmx2.setSpse(sub(sub(spse, cfse),mul(n, cfse1)));
							ccjyspmx2.setJshj(add(ccjyspmx2.getSpje(), ccjyspmx2.getSpse()));
							ccjyspmx2.setYkphj(0d);
							ccjyspmx2.setLrsj(lrsj);
							ccjyspmx2.setSpdm(spdm);
							ccjyspmx2.setLrry(lrry);
							fpnum++;
							ccjyspmx2.setFpnum(fpnum);
							ccjyspmx2.setClmxxh(jyspmx.getClmxxh());
							ccjyspmx2.setClxh(jyspmx.getClxh());
							ccjyspmx2.setXfid(jyspmx.getXfid());
							if (ccje != 0) {
								splitKpspmxs.add(ccjyspmx2);
//								if (mxnum==1) {
//									fpnum++;
//								}
							}else {
								fpbj--;
							}
							zje = ccje;
							tempJyspmxs.clear();
						
					} else {
						jyspmx.setFpnum(fpnum);
						if (i==list3.get(k-1).size()-1&&k==maxclmxxh) {
							fpnum++;
						}
						splitKpspmxs.add(jyspmx);
					}
				}
				if (mxnum==fpbj&&splitKpspmxs.get(splitKpspmxs.size()-1).getFpnum()==fpnum) {
					fpnum++;
				}
		}
		
	
		fpxh = fpnum;
		return splitKpspmxs;
	}
	
	  /**
     * 提供精确的加法运算。
     *
     * @param value1 被加数
     * @param value2 加数
     * @return 两个参数的和
     */
    public Double add(Number value1, Number value2) {
        if (value1 == null) {
            value1 = 0;
            return null;
        }
        if (value2 == null) {
            value2 = 0;
            return null;
        }
        BigDecimal b1 = new BigDecimal(Double.toString(value1.doubleValue()));
        BigDecimal b2 = new BigDecimal(Double.toString(value2.doubleValue()));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param value1 被减数
     * @param value2 减数
     * @return 两个参数的差
     */
    public Double sub(Number value1, Number value2) {
        if (value1 == null) {
            value1 = 0;
            return null;
        }
        if (value2 == null) {
            value2 = 0;
            return null;
        }
        BigDecimal b1 = new BigDecimal(Double.toString(value1.doubleValue()));
        BigDecimal b2 = new BigDecimal(Double.toString(value2.doubleValue()));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param value1 被乘数
     * @param value2 乘数
     * @return 两个参数的积
     */
    public Double mul(Number value1, Number value2) {
        if (value1 == null) {
            value1 = 0;
            return null;
        }
        if (value2 == null) {
            value2 = 0;
            return null;
        }
        BigDecimal b1 = new BigDecimal(Double.toString(value1.doubleValue()));
        BigDecimal b2 = new BigDecimal(Double.toString(value2.doubleValue()));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时， 精确到小数点以后指定位，以后的数字四舍五入。
     *
     * @param dividend 被除数
     * @param divisor  除数
     * @return 两个参数的商
     */
    public Double div(Double dividend, Double divisor) {
        if (dividend == null) {
            return null;
        }
        if (divisor == null || divisor == 0) {
            return null;
        }
        return div(dividend, divisor, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。 当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入。
     *
     * @param dividend 被除数
     * @param divisor  除数
     * @param scale    表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public Double div(Double dividend, Double divisor, Integer scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        if (dividend == null) {
            return null;
        }
        if (divisor == null || divisor == 0) {
            return null;
        }
        BigDecimal b1 = new BigDecimal(Double.toString(dividend));
        BigDecimal b2 = new BigDecimal(Double.toString(divisor));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}