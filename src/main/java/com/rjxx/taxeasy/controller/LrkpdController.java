package com.rjxx.taxeasy.controller;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.domains.DrPz;
import com.rjxx.taxeasy.domains.Drmb;
import com.rjxx.taxeasy.domains.Gsxx;
import com.rjxx.taxeasy.domains.Jymxsq;
import com.rjxx.taxeasy.domains.Jyxx;
import com.rjxx.taxeasy.domains.Jyxxsq;
import com.rjxx.taxeasy.domains.Skp;
import com.rjxx.taxeasy.domains.Sm;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.domains.XfMb;
import com.rjxx.taxeasy.filter.SystemControllerLog;
import com.rjxx.taxeasy.service.DrPzService;
import com.rjxx.taxeasy.service.DrmbService;
import com.rjxx.taxeasy.service.JymxsqService;
import com.rjxx.taxeasy.service.JyxxService;
import com.rjxx.taxeasy.service.JyxxsqService;
import com.rjxx.taxeasy.service.SkpService;
import com.rjxx.taxeasy.service.SmService;
import com.rjxx.taxeasy.service.SpvoService;
import com.rjxx.taxeasy.service.XfMbService;
import com.rjxx.taxeasy.service.XfService;
import com.rjxx.taxeasy.vo.JymxsqVo;
import com.rjxx.taxeasy.vo.Spvo;
import com.rjxx.taxeasy.web.BaseController;
import com.rjxx.time.TimeUtil;
import com.rjxx.utils.ExcelUtil;
import com.rjxx.utils.Tools;

@Controller
@RequestMapping("/lrkpd")
public class LrkpdController extends BaseController {

	@Autowired
	private JyxxsqService jyxxsqservice;

	@Autowired
	private JymxsqService jymxsqservice;
	
	@Autowired
	private SpvoService spvoService;

	@Autowired
	DrmbService drmbService;
	
	@Autowired
	private XfService xfService;
	
	@Autowired
	private SkpService skpservice;
	
	@Autowired
	private XfMbService xfmbService;
	
	@Autowired
	private DrPzService drPzService;
	
	@Autowired
	private SmService smService;
	
	@RequestMapping
	//@SystemControllerLog(description = "功能首页",key = "xfsh")   
	public String index() {
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
		List<Skp> skpList = this.getSkpList();
		request.setAttribute("spList", spList);
		request.setAttribute("xfSum", xfList.size());
		request.setAttribute("xfList", xfList);
		request.setAttribute("skpList", skpList);
		return "lrkpd/index";
	}
	
	/**
	 * 获取销方下的有操作权限的开票点
	 * 
	 * @param xfsh
	 * @return
	 */
	@RequestMapping(value = "/getSkpList")
	@ResponseBody
	public Map getSkpList(String xfsh) {
		Map<String, Object> result = new HashMap<>();
		Integer xfid = null;
		List<Skp> list = new ArrayList<>();
		for (Xf xf : getXfList()) {
			if (xfsh.equals(xf.getXfsh())) {
				xfid = xf.getId();
			}
		}
		for (Skp skp : getSkpList()) {
			if (xfid != null && skp.getXfid().equals(xfid)) {
				list.add(skp);
			}
		}
		result.put("skps", list);
		return result;
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
		params.setGsdm(this.getGsdm());
		params.setSpdm(spdm);
		params.setSpmc(spmc);
		List<Spvo> list = spvoService.findAllByParams(params);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 初始化显示列表
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getjyxxsqlist")
	@ResponseBody
	public Map getjylslist(int length, int start, int draw, String clztdm, String xfsh, String gfmc, String ddh,
			String spmc, String rqq, String rqz) {
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
		pagination.addParam("xfsh", xfsh);
		pagination.addParam("gfmc", gfmc);
		pagination.addParam("ddh", ddh);
		pagination.addParam("spmc", spmc);

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
		//pagination.addParam("fpzldm", "12");
		pagination.addParam("fpczlxdm", "11");
		pagination.addParam("gsdm", this.getGsdm());
		pagination.addParam("orderBy", "lrsj desc");

		List<Jyxxsq> jyxxsqList = jyxxsqservice.findByPage1(pagination);
		int total = pagination.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		result.put("draw", draw);
		result.put("data", jyxxsqList);
		return result;
	}
	
	@RequestMapping(value = "/getjymxsqlist")
	@ResponseBody
	public Map getjyspmxlist(int length, int start, int draw, int sqlsh) {
		String gsdm = this.getGsdm();
		Pagination pagination = new Pagination();
		pagination.setPageNo(start / length + 1);
		pagination.setPageSize(length);
		pagination.addParam("sqlsh", sqlsh);
		pagination.addParam("gsdm", gsdm);
		List<Jymxsq> jymxsqList = jymxsqservice.findByPage(pagination);

		int total = pagination.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		result.put("draw", draw);
		result.put("data", jymxsqList);
		return result;
	}
	

	/**
	 * 删除
	 *
	 * @param djhArr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/doDel")
	@ResponseBody
	public boolean doDel(String djhArr) throws Exception {
		jyxxsqservice.delBySqlshList(convertToList(djhArr));
		return true;
	}

	private List<Integer> convertToList(String sqlshStrs) {
		String[] sqlshArr = sqlshStrs.split(",");
		List<Integer> sqlshList = new ArrayList<>();
		for (String sqlshStr : sqlshArr) {
			sqlshList.add(new Integer(sqlshStr));
		}
		return sqlshList;
	}
	
	/**
	 * 获取销方信息
	 *
	 * @param xfid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getXfxxById")
	@ResponseBody
	public Xf getXfxxById(int xfid) throws Exception {
		Xf xf = xfService.findOne(xfid);
		return xf;
	}
	
	/**
	 * 保存交易信息申请表和交易明细申请表
	 *
	 * @param 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Map save() {
		Map<String, Object> result = new HashMap<String, Object>();
		String gsdm = getGsdm();
		String xfid = request.getParameter("xfid_edit");
		int yhid = getYhid();
		com.rjxx.taxeasy.domains.Xf xf = xfService.findOne(Integer.parseInt(xfid));
		Jyxxsq jyxxsq = new Jyxxsq();
		jyxxsq.setClztdm("00");
		jyxxsq.setDdh(request.getParameter("ddh_edit"));
		jyxxsq.setGfsh(request.getParameter("gfsh_edit"));

		jyxxsq.setGfmc(request.getParameter("gfmc_edit"));
		jyxxsq.setGfyh(request.getParameter("gfyh_edit"));
		jyxxsq.setGfyhzh(request.getParameter("gfzh_edit"));
		jyxxsq.setGfsjh(request.getParameter("gfsjh_edit"));

		jyxxsq.setGflxr(request.getParameter("gflxr_edit"));
		jyxxsq.setBz(request.getParameter("gfbz_edit"));
		jyxxsq.setGfemail(request.getParameter("gfemail_edit"));
		jyxxsq.setGfdz(request.getParameter("gfdz_edit"));
		jyxxsq.setZtbz("0");//0待审，1已审
		jyxxsq.setSjly("0");//0平台接入，1接口接入
		String tqm = request.getParameter("tqm_edit");
		if (StringUtils.isNotBlank(tqm)) {
			Map params = new HashMap();
			params.put("gsdm", gsdm);
			params.put("tqm", tqm);
			Jyxxsq tmp = jyxxsqservice.findOneByParams(params);
			if (tmp != null) {
				result.put("failure", true);
				result.put("msg", "提取码已经存在");
				return result;
			}
		}
		jyxxsq.setTqm(tqm);
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
		jyxxsq.setFpzldm("12");
		jyxxsq.setFpczlxdm("11");
		jyxxsq.setXfyh(xf.getXfyh());
		jyxxsq.setXfyhzh(xf.getXfyhzh());
		jyxxsq.setXfdz(xf.getXfdz());
		jyxxsq.setXfdh(xf.getXfdh());
		if (StringUtils.isNotBlank(jyxxsq.getGfemail())) {
			jyxxsq.setSffsyj("1");
		}
		jyxxsq.setKpr(xf.getKpr());
		jyxxsq.setFhr(xf.getFhr());
		jyxxsq.setSkr(xf.getSkr());
		jyxxsq.setSsyf(new SimpleDateFormat("yyyyMM").format(new Date()));
		jyxxsq.setLrry(yhid);
		jyxxsq.setXgry(yhid);
		jyxxsq.setYxbz("1");
		jyxxsq.setGsdm(gsdm);
		String skpid = request.getParameter("skpid_edit");
		if (skpid != null && !"".equals(skpid)) {
			jyxxsq.setSkpid(Integer.parseInt(skpid));
		}
		Map paramskp = new HashMap();
		paramskp.put("id", skpid);
		Skp skp = skpservice.findOneByParams(paramskp);
		jyxxsq.setKpddm(skp.getKpddm());
		try {
			// Map<String, Object> params = null;
			Map params = Tools.getParameterMap(request);
			int mxcount = Integer.valueOf(params.get("mxcount").toString());
			String[] spdms = ((String) params.get("spdm")).split(",");
			String[] spmcs = ((String) params.get("spmc")).split(",");
			String[] spjes = ((String) params.get("je")).split(",");
			String[] spsls = ((String) params.get("rate")).split(",");
			String[] jshjs = ((String) params.get("jshj")).split(",");
			String[] spges = ((String) params.get("ggxh")).split(",");
			String[] spss = ((String) params.get("sl")).split(",");
			String[] spdws = ((String) params.get("dw")).split(",");
			String[] spdjs = ((String) params.get("dj")).split(",");
			String[] spses = ((String) params.get("se")).split(",");

			double jshj = 0.00;
			List<Jymxsq> jymxsqList = new ArrayList<>();
			for (int c = 0; c < mxcount; c++) {
				Jymxsq jymxsq = new Jymxsq();
				int xxh = c + 1;
				jymxsq.setSpmxxh(xxh);
				jymxsq.setFphxz("0");
				jymxsq.setSpdm(spdms[c]);
				jymxsq.setSpmc(spmcs[c]);
				jymxsq.setSpje(Double.valueOf(spjes[c]));
				if (spsls.length != 0) {
					jymxsq.setSpsl(Double.valueOf(spsls[c]));
				}
				jymxsq.setJshj(Double.valueOf(jshjs[c]));
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
						jymxsq.setSpdw(spdws[c]);
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
				//jymxsq.setYkphj(0.00);
				jymxsq.setLrry(yhid);
				jymxsq.setLrsj(TimeUtil.getNowDate());
				jymxsq.setXgsj(TimeUtil.getNowDate());
				jymxsq.setXgry(yhid);
				jymxsq.setGsdm(gsdm);
				jshj += jymxsq.getJshj();
				jymxsqList.add(jymxsq);
			}
			jyxxsq.setJshj(jshj);
			jyxxsqservice.saveJyxxsq(jyxxsq, jymxsqList);
			result.put("success", true);
			result.put("djh", jyxxsq.getSqlsh());
		} catch (Exception ex) {
			ex.printStackTrace();
			result.put("failure", true);
			result.put("msg", "保存出现错误: " + ex.getMessage());
		}
		return result;
	}
	
	/**
	 * 开票请求
	 *
	 * @param djhArr
	 * @return
	 */
	@RequestMapping(value = "/sqKp")
	@ResponseBody
	public Map doKp(String djhArr) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Integer> sqlshList = convertToList(djhArr);
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("gsdm", getGsdm());
			
             jyxxsqservice.updateJyxxsqZtzt(sqlshList, "1");

			result.put("success", true);
			result.put("msg", "请求成功，请耐心等待开票结果");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.put("failure", true);
			result.put("msg", "保存出现错误: " + ex.getMessage());
		}
		return result;
	}
	
	/**
	 * 保存导入配置
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveImportConfig", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public Map saveImportConfig(Integer mbid, String mbmc, String config_xfsh, String config_gs_radio)
			throws Exception {
		Map<String, Object> data = new HashMap<>();
		int yhid = this.getYhid();
		Map<String, String[]> requestMap = request.getParameterMap();
		Iterator<String> iterator = requestMap.keySet().iterator();
		List<DrPz> drPzList = new ArrayList<>();
		Drmb mb = null;
		Drmb drmb = new Drmb();
		drmb.setMbmc(mbmc);
		drmb.setXfsh(config_xfsh);
		drmb.setGsdm(getGsdm());
		if (mbid != null) {
			mb = drmbService.findOne(mbid);
			if (mb != null && !mb.getYhid().equals(yhid)) {
				data.put("error", true);
				data.put("message", "你没有权限修改此模板！");
				return data;
			}
			drmb.setId(mbid);
			drmb.setXgry(getYhid());
			drmb.setXgsj(new Date());
			if (config_gs_radio.equals("0")) {
				Map<String, Object> map = new HashMap<>();
				map.put("mbid", mbid);
				List<XfMb> list = xfmbService.findAllByParams(map);
				List<XfMb> delList = new ArrayList<>();
				for (XfMb xfMb : list) {
					if (xfMb.getXfsh().equals(config_xfsh)) {
						delList.add(xfMb);
					} else {
						xfMb.setYxbz("0");
						xfMb.setXgry(getYhid());
						xfMb.setXgsj(new Date());
					}
				}
				list.removeAll(delList);
				xfmbService.save(list);
			}
		} else {
			drmb.setLrry(getYhid());
			drmb.setLrsj(new Date());
			drmb.setXgry(getYhid());
			drmb.setXgsj(new Date());
		}
		mb = drmbService.findOneByParams(drmb);
		if (mb != null) {
			data.put("error", true);
			data.put("message", "模板名称已存在，请重新输入");
			return data;
		}

		drmb.setXfsh(config_xfsh);
		drmb.setYhid(yhid);
		drmb.setGsdm(getGsdm());
		drmb.setGxbz(config_gs_radio);
		drmbService.save(drmb);
		while (iterator.hasNext()) {
			String key = iterator.next();
			if (key.startsWith("config_") && key.endsWith("_radio")) {
				String[] arr = key.split("_");
				String zdm = arr[1];
				DrPz drPz = new DrPz();
				drPz.setZdm(zdm);
				drPz.setPzlx(request.getParameter(key));
				drPz.setPzz(request.getParameter("config_" + zdm));
				drPz.setYhid(yhid);
				drPz.setMbid(drmb.getId());
				drPzList.add(drPz);
			}
		}
		drPzService.deleteAndSave(drmb.getId(), drPzList);
		data.put("success", true);
		data.put("drmb", drmb);
		return data;
	}

	
	/**
	 * 初始化导入配置
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/initImportConfig")
	@ResponseBody
	public List initImportConfig() throws Exception {
		int yhid = getYhid();
		DrPz params = new DrPz();
		params.setYhid(yhid);
		List<DrPz> list = drPzService.findAllByParams(params);
		return list;
	}
	
	/**
	 * 根据模板id获取模板信息
	 * 
	 * @param mbid
	 * @return
	 */
	@RequestMapping(value = "/getMb")
	@ResponseBody
	public Drmb getMb(Integer mbid) {
		return drmbService.findOne(mbid);
	}

	/**
	 * 删除模板
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteMb", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public Map deleteMb(Integer mbid) {
		Map<String, Object> result = new HashMap<String, Object>();
		Drmb drmb = drmbService.findOne(mbid);
		String xfsh = drmb.getXfsh();
		try {
			if (!drmb.getYhid().equals(getYhid())) {
				result.put("success", false);
				result.put("msg", "你没有权限删除模板！");
				return result;
			}
			drmbService.delete(drmb);
			DrPz drPz = new DrPz();
			drPz.setMbid(mbid);
			List<DrPz> list = drPzService.findAllByParams(drPz);
			if (!list.isEmpty()) {
				drPzService.deleteAll(list);
			}
			Map<String, Object> map = new HashMap<>();
			map.put("mbid", mbid);
			List<XfMb> list1 = xfmbService.findAllByParams(map);
			List<XfMb> delList = new ArrayList<>();
			for (XfMb xfMb : list1) {
				if (xfMb.getXfsh().equals(xfsh)) {
					delList.add(xfMb);
				} else {
					xfMb.setYxbz("0");
					xfMb.setXgry(getYhid());
					xfMb.setXgsj(new Date());
				}
			}
			list1.removeAll(delList);
			xfmbService.save(list1);
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", "删除模板出错：" + e);
		}

		return result;
	}
	
	/**
	 * 下载导入默认模板
	 *
	 * @throws Exception
	 */
	@RequestMapping(value = "/downloadDefaultImportTemplate")
	@ResponseBody
	public void downloadDefaultImportTemplate() throws Exception {
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("/template/importTemplate.xls");
		// 1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=importTemplate.xls");
		ServletOutputStream out = response.getOutputStream();
		IOUtils.copy(inputStream, out);
		inputStream.close();
		out.flush();
		out.close();
	}
	
	
	/**
	 * 设置销方默认导入模板
	 * 
	 * @param xfsh
	 * @param mbid
	 * @return
	 */
	@RequestMapping(value = "/saveMb")
	@ResponseBody
	public Map saveMrmb(String xfsh, Integer mbid) {
		Map<String, Object> result = new HashMap<>();
		try {
			XfMb mrmb = new XfMb();
			mrmb.setMbid(mbid);
			mrmb.setXfsh(xfsh);
			mrmb.setYxbz("1");
			mrmb.setLrry(getYhid());
			mrmb.setLrsj(new Date());
			mrmb.setXgry(getYhid());
			mrmb.setXgsj(new Date());
			xfmbService.save(mrmb);
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", "设置默认模板出错" + e.getMessage());
		}

		return result;
	}
	
	/**
	 * 获取销方的默认导入模板
	 * 
	 * @param xfsh
	 * @return
	 */
	@RequestMapping(value = "/getMrmb")
	@ResponseBody
	public Map getMrmb(String xfsh) {
		Map<String, Object> result = new HashMap<>();
		Drmb mb = new Drmb();
		mb.setXfsh(xfsh);
		result.put("mrmb", drmbService.findMrByParams(mb));
		return result;
	}
	
	/**
	 * 获取销方的导入模板及其他销方共享的模板
	 * 
	 * @param xfsh
	 * @return
	 */
	@RequestMapping(value = "/getTemplate")
	@ResponseBody
	public Map getTemplate(String xfsh) {
		Map<String, Object> result = new HashMap<>();
		Map<String, Object> map = new HashMap<>();
		map.put("xfsh", xfsh);
		map.put("xfs", getXfList());
		map.put("gsdm", getGsdm());
		List<Drmb> mbList = drmbService.findAllByParams(map);
		result.put("mbs", mbList);
		return result;
	}
	
	/**
	 * 导入excel数据
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importExcel", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public Map importExcel(MultipartFile importFile, Integer mb, Integer mrmb, String mb_xfsh, Integer mb_skp)
			throws Exception {
		Map<String, Object> result = new HashMap<>();
		if (importFile == null || importFile.isEmpty()) {
			result.put("success", false);
			result.put("message", "请选择要导入的文件");
			return result;
		}
		List<List> resultList = ExcelUtil.exportListFromExcel(importFile.getInputStream(),
				FilenameUtils.getExtension(importFile.getOriginalFilename()), 0);
		if (resultList.size() < 2) {
			result.put("success", false);
			result.put("message", "行数少于2行，没有数据");
			return result;
		}
		try {
			String fileName = importFile.getOriginalFilename();
			if (fileName != null && fileName.length() > 0) {
				fileName = fileName.substring(0, fileName.lastIndexOf("."));
				if (mb > 0) {
					Drmb d = drmbService.findOne(mb);
					if (d != null && fileName.equals(d.getMbmc())) {
						XfMb mr = new XfMb();
						mr.setMbid(mb);
						mr.setXfsh(mb_xfsh);
						mr.setYxbz("1");
						mr.setLrry(getYhid());
						mr.setLrsj(new Date());
						mr.setXgry(getYhid());
						mr.setXgsj(new Date());
						xfmbService.save(mr);
						result.put("yes", true);
					}
				}
			}
			if (mb == null || mb < 1) {
				mb = mrmb;
			}
			String msg = processExcelList(resultList, mb, mb_xfsh, mb_skp);
			if (!"".equals(msg)) {
				result.put("success", false);
				result.put("message", msg);
				return result;
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("message", "模板的配置和excel表格表头不一致，请修改模板或表头使两者一致再导入！");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("message", "导入出错");
			return result;
		}

		result.put("success", true);
		result.put("count", resultList.size() - 1);
		return result;
	}

	/**
	 * 导入字段映射
	 */
	private static Map<String, String> importColumnMapping = new HashMap<String, String>() {
		{
			put("jylsh", "交易流水号");
			put("ddh", "订单号");
			put("xfsh", "销方税号");
			put("xfmc", "销方名称");
			put("xfdz", "销方地址");
			put("xfdh", "销方电话");
			put("xfyh", "销方银行");
			put("xfyhzh", "销方银行账号");
			put("skr", "收款人");
			put("kpr", "开票人");
			put("fhr", "复核人");
			put("gfsh", "购方税号");
			put("gfmc", "购方名称");
			put("gfdz", "购方地址");
			put("gfdh", "购方电话");
			put("gfyh", "购方银行");
			put("gfyhzh", "购方银行账号");
			put("spdm", "商品代码");
			put("spmc", "商品名称");
			put("spggxh", "规格型号");
			put("spdw", "商品单位");
			put("sps", "商品数量");
			put("spdj", "商品单价");
			put("spje", "商品金额");
			put("spsl", "商品税率");
			put("spse", "商品税额");
			put("hsbz", "含税标志");
			put("gfemail", "购方邮箱");
			put("bz", "备注");
			put("gfsjh", "购方手机号");
		}
	};
	
	@Value("${tax-amount.wc:}")
	private String sewc;

	// 金额，税率，税额校验
	private boolean checkWC(double je, double sl, double se) {
		boolean flate = false;
		double wc = Double.parseDouble(sewc);
		double cfwc = sub(mul(sub(je, se), sl), se);
		if (Math.abs(cfwc) <= wc) {
			flate = true;
		}
		return flate;
	}
	
	/**
	 * 提供精确的减法运算。
	 *
	 * @param value1
	 *            被减数
	 * @param value2
	 *            减数
	 * @return 两个参数的差
	 */
	private Double sub(Number value1, Number value2) {
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
	 * @param value1
	 *            被乘数
	 * @param value2
	 *            乘数
	 * @return 两个参数的积
	 */
	private Double mul(Number value1, Number value2) {
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
	 * 提供（相对）精确的除法运算。 当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入。
	 *
	 * @param dividend
	 *            被除数
	 * @param divisor
	 *            除数
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
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
	
	/**
	 * 处理excel的记录
	 *
	 * @param dataList
	 * @throws Exception
	 */
	private String processExcelList(List<List> dataList, Integer mb, String xfsh1, Integer skpid) throws Exception {

		// 数据的校验
		String msgg = "";
		String msg = "";
		int yhid = this.getYhid();
		DrPz params = new DrPz();
		if (mb == null) {
			mb = -1;
		}
		params.setMbid(mb);
		List<DrPz> drPzList = drPzService.findAllByParams(params);
		Map<String, DrPz> pzMap = new HashMap<>();
		Map<String, String> enCnExcelColumnMap = new HashMap<>();
		Map<String, String> drpzMap = new HashMap<>();
		enCnExcelColumnMap.putAll(importColumnMapping);
		if (drPzList != null && !drPzList.isEmpty()) {
			for (DrPz drPz : drPzList) {
				pzMap.put(drPz.getZdm(), drPz);
				if ("config".equals(drPz.getPzlx())) {
					enCnExcelColumnMap.put(drPz.getZdm(), drPz.getPzz());
					drpzMap.put(drPz.getZdm(), drPz.getPzz());
				}
			}
		}
		// 找到excel中有效的字段
		List titleList = dataList.get(0);
		boolean flag1;
		for (String str : drpzMap.values()) {
			flag1 = false;
			for (Object obj : titleList) {
				String tmp = (String)obj;
				if ("".equals(str) || (str.equals(tmp))) {
					flag1 = true;
					break;
				}
			}
			if (!flag1) {
				msgg = "excel表格中没有“"+str+"”列，请修改！";
				msg += msgg;
			}
		}
		if (!"".equals(msg)) {
			return msg;
		}
		// 转换成key为中文，value为英文的map
		Map<String, String> cnEnExcelColumnMap = new HashMap<>();
		for (Map.Entry<String, String> entry : enCnExcelColumnMap.entrySet()) {
			cnEnExcelColumnMap.put(entry.getValue(), entry.getKey());
		}
		// 字段的顺序
		int columnIndex = 0;
		Map<String, Integer> columnIndexMap = new HashMap<>();
		String gsdm = this.getGsdm();
		for (Object obj : titleList) {
			String columnName = (String) obj;
			// if (cnEnExcelColumnMap.containsKey(columnName)) {
			columnIndexMap.put(columnName, columnIndex);
			// }
			columnIndex++;
		}
		// 获取所有的销方
		List<Xf> xfList = this.getXfList();
		// 获取税率
		List<Sm> slModel = smService.findAll();
		List<String> slList = new ArrayList<String>();
		if (slModel != null) { // 税率的查询
			for (int y = 0; y < slModel.size(); y++) {
				slList.add(String.valueOf(slModel.get(y).getSl()));
			}
		}
		// 获取导入销方
		Xf xf = null;
		for (Xf x : xfList) {
			if (x.getXfsh().equals(xfsh1)) {
				xf = x;
				break;
			}
		}
		Xf xf1 = null;
		Drmb drmb = drmbService.findOne(mb);
		Xf x1 = new Xf();
		x1.setGsdm(gsdm);
		x1.setXfsh(drmb.getXfsh());
		xf1 = xfService.findOneByParams(x1);
//		List row1 = dataList.get(0);
//		String xfsh2 = getValue("xfsh", pzMap, columnIndexMap, dataList.get(1));
//		for (Xf x : xfList) {
//			if (x.getXfsh().equals(xfsh2)) {
//				xf1 = x;
//				break;
//			}
//		}
		boolean flag = false;
		if (xf1 != null && xfsh1.equals(xf1.getXfsh())) {
			flag = true;
		}
		List<Jyxxsq> jyxxsqList = new ArrayList<>();
		List<JymxsqVo> mxList = new ArrayList<>();
		Integer num = 1;
		for (int k = 1; k < dataList.size(); k++) {
			List row = dataList.get(k);
			Jyxxsq jyxxsq = new Jyxxsq();
			jyxxsq.setGsdm(gsdm);
			jyxxsq.setLrry(yhid);
			jyxxsq.setXgry(yhid);
			jyxxsq.setLrsj(TimeUtil.getNowDate());
			jyxxsq.setXgsj(TimeUtil.getNowDate());
			jyxxsq.setYkpjshj(0d);
			jyxxsq.setClztdm("00");
			//Jyxxsq.setFpzldm("12");
			jyxxsq.setFpczlxdm("11");
			jyxxsq.setYxbz("1");
			jyxxsq.setJylsh(getValue("jylsh", pzMap, columnIndexMap, row));
			jyxxsq.setDdh(getValue("ddh", pzMap, columnIndexMap, row));
			jyxxsq.setDdrq(TimeUtil.getNowDate());
			if (!flag) {
				jyxxsq.setXfid(xf.getId());
				jyxxsq.setXfsh(xf.getXfsh());
				jyxxsq.setXfmc(xf.getXfmc());
				jyxxsq.setXfdz(xf.getXfdz());
				jyxxsq.setXfdh(xf.getXfdh());
				jyxxsq.setXfyh(xf.getXfyh());
				jyxxsq.setXfyhzh(xf.getXfyhzh());
				jyxxsq.setSkr(xf.getSkr());
				jyxxsq.setKpr(xf.getKpr());
				jyxxsq.setFhr(xf.getFhr());
			}else{
				jyxxsq.setXfid(xf1.getId());
				jyxxsq.setXfsh(xf1.getXfsh());
				jyxxsq.setXfmc(xf1.getXfmc());
				jyxxsq.setXfdz(getValue("xfdz", pzMap, columnIndexMap, row));
				jyxxsq.setXfdh(getValue("xfdh", pzMap, columnIndexMap, row));
				jyxxsq.setXfyh(getValue("xfyh", pzMap, columnIndexMap, row));
				jyxxsq.setXfyhzh(getValue("xfyhzh", pzMap, columnIndexMap, row));
				jyxxsq.setSkr(getValue("skr", pzMap, columnIndexMap, row));
				jyxxsq.setKpr(getValue("kpr", pzMap, columnIndexMap, row));
				jyxxsq.setFhr(getValue("fhr", pzMap, columnIndexMap, row));
			}
			jyxxsq.setSkpid(skpid);
			jyxxsq.setGfsh(getValue("gfsh", pzMap, columnIndexMap, row));
			jyxxsq.setGfmc(getValue("gfmc", pzMap, columnIndexMap, row));
			jyxxsq.setGfdz(getValue("gfdz", pzMap, columnIndexMap, row));
			jyxxsq.setGfdh(getValue("gfdh", pzMap, columnIndexMap, row));
			jyxxsq.setGfyh(getValue("gfyh", pzMap, columnIndexMap, row));
			jyxxsq.setGfyhzh(getValue("gfyhzh", pzMap, columnIndexMap, row));
			String sjh = getValue("gfsjh", pzMap, columnIndexMap, row);
			if (sjh != null) {
				sjh = new BigDecimal(sjh).toPlainString();
				if (sjh.contains(".")) {
					sjh = sjh.substring(0, sjh.indexOf(","));
				}
			}
			jyxxsq.setGfsjh(sjh);
			jyxxsq.setZtbz("0");
			jyxxsq.setSjly("0");
			jyxxsq.setBz(getValue("bz", pzMap, columnIndexMap, row));
			jyxxsq.setGfemail(getValue("gfemail", pzMap, columnIndexMap, row));
			jyxxsq.setTqm(getValue("tqm", pzMap, columnIndexMap, row));
			if (StringUtils.isNotBlank(jyxxsq.getGfemail())) {
				jyxxsq.setSffsyj("1");
			}
			jyxxsq.setHsbz(getValue("hsbz", pzMap, columnIndexMap, row));
			Double spsl = getValue("spsl", pzMap, columnIndexMap, row) == null ? null : Double.valueOf(getValue("spsl", pzMap, columnIndexMap, row));
			Double spje = getValue("spje", pzMap, columnIndexMap, row) == null ? null : Double.valueOf(getValue("spje", pzMap, columnIndexMap, row));
			Double jshj = spje;
			if (spje != null && spsl != null && "0".equals(jyxxsq.getHsbz())) {
				// 不含税
				jshj = spje * (1.0 + spsl);
			}
			jyxxsq.setJshj(jshj);
//			if (jyls.getGfmc() == null && jyls.getJshj() == null) {
//				continue;
//			}
			if ( !jyxxsqList.isEmpty() && jyxxsqList.size()> 0) {
				boolean tmp = false;
				for (Jyxxsq jy : jyxxsqList) {
					if (jy.getDdh() != null && jy.getDdh().equals(jyxxsq.getDdh())) {
						jy.setJshj(jy.getJshj() + jyxxsq.getJshj());
						tmp = true;
						num++;
						break;
					}
				}
				if (!tmp) {
					num = 1;
					jyxxsqList.add(jyxxsq);
				}
			}else{
				jyxxsqList.add(jyxxsq);
			}
			// int djh = jyls.getDjh();
			JymxsqVo jymxsq = new JymxsqVo();
			// jyspmx.setDjh(djh);
			jymxsq.setDdh(jyxxsq.getDdh());
			jymxsq.setSpmxxh(num);
			jymxsq.setGsdm(gsdm);
			jymxsq.setLrry(yhid);
			jymxsq.setXgry(yhid);
			//jymxsq.setYkphj(0d);
			jymxsq.setFphxz("0");
			jymxsq.setLrsj(TimeUtil.getNowDate());
			jymxsq.setXgsj(TimeUtil.getNowDate());
			jymxsq.setSpdm(getValue("spdm", pzMap, columnIndexMap, row));
			jymxsq.setSpmc(getValue("spmc", pzMap, columnIndexMap, row));
			jymxsq.setSpggxh(getValue("spggxh", pzMap, columnIndexMap, row));
			jymxsq.setSpdw(getValue("spdw", pzMap, columnIndexMap, row));
			String sps = getValue("sps", pzMap, columnIndexMap, row);
			if (StringUtils.isNotBlank(sps)) {
				jymxsq.setSps(Double.valueOf(sps));
			}
			String spdjStr = getValue("spdj", pzMap, columnIndexMap, row);
			if (StringUtils.isNotBlank(spdjStr)) {
				jymxsq.setSpdj(Double.valueOf(spdjStr));
			}
			jymxsq.setSpje(spje);
			jymxsq.setSpsl(spsl);
			jymxsq.setJshj(jshj);
			jymxsq.setSpse(Double.valueOf(getValue("spse", pzMap, columnIndexMap, row)));
			if (jymxsq.getSpje() != null && jymxsq.getSpse() == 0) {
				Double temp = div(jymxsq.getJshj(), (1 + jymxsq.getSpsl()), 100);
				String je = new DecimalFormat("0.00").format(temp);
				jymxsq.setSpse(Double.valueOf(new DecimalFormat("0.00").format(jymxsq.getJshj() - Double.valueOf(je))));
			}
			mxList.add(jymxsq);
		}
		// 提取码
		List<String> tqmList = new ArrayList<>();
		// 开始数据校验
		for (int i = 0; i < jyxxsqList.size(); i++) {
			Jyxxsq jyxxsq = jyxxsqList.get(i);
			if (StringUtils.isNotBlank(jyxxsq.getTqm())) {
				tqmList.add(jyxxsq.getTqm());
			}
			String jylsh = jyxxsq.getJylsh();
			if (jylsh == null || "".equals(jylsh)) { // 交易流水号的判断
				msgg = "第" + (i+2) + "行交易流水号没有填写，请重新填写！";
				msg += msgg;
			}
			if (jylsh != null && jylsh.length() > 20) { // 交易流水号长度的判断
				msgg = "第" + (i+2) + "行交易流水号超出20个字符，请重新填写！";
				msg += msgg;
			}
			String ddh = jyxxsq.getDdh();// 订单号的校验
			if (ddh == null || "".equals(ddh)) {
				msgg = "第" + (i+2) + "行订单号不能为空，请重新填写！";
				msg += msgg;
			} else if (ddh.length() > 20) {
				msgg = "第" + (i+2) + "行订单号超出20个字符，请重新填写！";
				msg += msgg;
			}
			String xfsh = jyxxsq.getXfsh();
			String xfmc = jyxxsq.getXfmc();// 销方名称，销方税号的校验
			if (xfList != null) {
				for (int x = 0; x < xfList.size(); x++) {
					if (!xfsh.equals(xfList.get(x).getXfsh()) && xfmc.equals(xfList.get(x).getXfmc())) {
						msgg = "第" + (i+2) + "行销方税号，销方名称不存在或者不对应，请重新填写！";
						msg += msgg;
					}
				}
			} else {
				msgg = "数据库中不存在有效的销方,请先去维护销方信息！";
				msg += msgg;
			}
			String xfdz = jyxxsq.getXfdz();// 销方地址的校验
			if (xfdz == null || "".equals(xfdz)) {
				msgg = "第" + (i+2) + "行销方地址没有填写，请重新填写！";
				msg += msgg;
			} else if (xfdz.length() > 100) {
				msgg = "第" + (i+2) + "行销方地址超出100个字符，请重新填写！";
				msg += msgg;
			}
			String xfdh = jyxxsq.getXfdh();// 销方电话校验
			if (xfdh == null || "".equals(xfdh)) {
				msgg = "第" + (i+2) + "行销方电话没有填写，请重新填写！";
				msg += msgg;
			} else if (xfdh.length() > 25) {
				msgg = "第" + (i+2) + "行销方电话超出25个字符，请重新填写！";
				msg += msgg;
			}
			String xfyh = jyxxsq.getXfyh();// 销方银行校验
			if (xfyh == null || "".equals(xfyh)) {
				msgg = "第" + (i+2) + "行销方银行没有填写，请重新填写！";
				msg += msgg;
			} else if (xfyh.length() > 25) {
				msgg = "第" + (i+2) + "行销方银行超出25个字符，请重新填写！";
				msg += msgg;
			}
			String xfyhzh = jyxxsq.getXfyhzh();// 销方银行账号的校验
			if (xfyhzh == null || "".equals(xfyhzh)) {
				msgg = "第" + (i+2) + "行销方银行账号没有填写，请重新填写！";
				msg += msgg;
			} else if (xfyhzh.length() > 30) {
				msgg = "第" + (i+2) + "行销方银行超出30个字符，请重新填写！";
				msg += msgg;
			}
			String skr = jyxxsq.getSkr();// 收款人校验
			if (skr != null && skr.length() > 10) {
				msgg = "第" + (i+2) + "行收款人超出10个字符，请重新填写！";
				msg += msgg;
			}
			String fhr = jyxxsq.getFhr();// 复核人校验
			if (fhr != null && fhr.length() > 10) {
				msgg = "第" + (i+2) + "行复核人超出10个字符，请重新填写！";
				msg += msgg;
			}
			String kpr = jyxxsq.getKpr();// 开票人校验
			if (kpr != null && kpr.length() > 10) {
				msgg = "第" + (i+2) + "行开票人超出10个字符，请重新填写！";
				msg += msgg;
			}
			String gfsh = jyxxsq.getGfsh();// 购方税号校验
			if (gfsh != null && !(gfsh.length() == 15 || gfsh.length() == 18)) { // 购方税号长度的判断
				msgg = "第" + (i+2) + "行购方税号不是15位或18位，请重新填写！";
				msg += msgg;
			}
			String gfmc = jyxxsq.getGfmc();// 购方名称校验
			if (gfmc == null || "".equals(gfmc)) {
				msgg = "第" + (i+2) + "行购方名称没有填写，请重新填写！";
				msg += msgg;
			}
			if (gfmc != null && gfmc.length() > 100) { // 购方名称长度的判断
				msgg = "第" + (i+2) + "行购方名称超出100个字符，请重新填写！";
				msg += msgg;
			}
			String gfyh = jyxxsq.getGfyh();// 购方银行校验
			if (gfyh != null && gfyh.length() > 50) { // 购方银行长度的判断
				msgg = "第" + (i+2) + "行购方银行超出50个字符，请重新填写！";
				msg += msgg;
			}
			String gfyhzh = jyxxsq.getGfyhzh();// 购方银行账号校验
			if (gfyhzh != null && gfyhzh.length() > 50) { // 购方银行账号长度的判断
				msgg = "第" + (i+2) + "行购方银行账号超出50个字符，请重新填写！";
				msg += msgg;
			}
			String gfdz = jyxxsq.getGfdz();// 购方地址校验
			if (gfdz != null && gfdz.length() > 100) { // 购方地址长度的判断
				msgg = "第" + (i+2) + "行购方地址超出200个字符，请重新填写！";
				msg += msgg;
			}
			String gfdh = jyxxsq.getGfdh();// 购方电话校验
			if (gfdh != null && gfdh.length() > 25) { // 购方电话长度的判断
				msgg = "第" + (i+2) + "行购方电话超出25位，请重新填写！";
				msg += msgg;
			}
			String gfEmail = jyxxsq.getGfemail();// 购方email校验
			if (gfEmail != null && !"".equals(gfEmail.trim()) && !gfEmail.matches("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$")) {
				msgg = "第" + (i+2) + "行购方email格式不正确，请重新填写！";
				msg += msgg;
			}
			String hsbz = jyxxsq.getHsbz();// 含税标志校验
			if (hsbz == null || "".equals(hsbz)) {
				msgg = "第" + (i+2) + "行含税标志不能为空，请重新填写！";
				msg += msgg;
			} else if (!("0".equals(hsbz) || "1".equals(hsbz))) {
				msgg = "第" + (i+2) + "行含税标志只能填写1或0！";
				msg += msgg;
			}
			JymxsqVo mxsq = mxList.get(i);
			String spdm = mxsq.getSpdm();
			if (spdm != null && spdm.length() > 20) {
				msgg = "第" + (i+2) + "行商品代码超过20个字符，请重新填写！";
				msg += msgg;
			}
			String spmc = mxsq.getSpmc();
			if (spmc == null || "".equals(spmc)) {
				msgg = "第" + (i+2) + "行商品名称不能为空，请重新填写！";
				msg += msgg;
			} else if (spmc.length() > 50) {
				msgg = "第" + (i+2) + "行商品名称超过50个字符，请重新填写！";
				msg += msgg;
			}
			String spggxh = mxsq.getSpggxh();
			if (spggxh != null && spggxh.length() > 18) {
				msgg = "第" + (i+2) + "行商品规格型号超过18个字符，请重新填写！";
				msg += msgg;
			}
			String spdw = mxsq.getSpdw();
			if (spdw != null && spdw.length() > 5) {
				msgg = "第" + (i+2) + "行商品单位超过5个字符，请重新填写！";
				msg += msgg;
			}
			Double sps = mxsq.getSps();
			if (sps != null && !String.valueOf(sps).matches("^[0-9]{0,16}+(.[0-9]{0,6})?$")) {
				msgg = "第" + (i+2) + "行商品数格式不正确，请重新填写！";
				msg += msgg;
			}
			Double spdj = mxsq.getSpdj();
			if (spdj != null && !String.valueOf(spdj).matches("^[0-9]{0,16}+(.[0-9]{0,6})?$")) {
				msgg = "第" + (i+2) + "行商品单价格式不正确，请重新填写！";
				msg += msgg;
			}
			Double spje = mxsq.getSpje();
			if (spje == null || spje <= 0) {
				msgg = "第" + (i+2) + "行商品金额不能为空或小于等于0，请重新填写！";
				msg += msgg;
			}
			Double spsl = mxsq.getSpsl();
			if (spsl == null || "".equals(spsl)) {
				msgg = "第" + (i+2) + "行商品税率不能为空，请重新填写！";
				msg += msgg;
			} else if (!slList.contains(String.valueOf(spsl))) {
				msgg = "第" + (i+2) + "行商品税率数据库中不存在，请重新填写！";
				msg += msgg;
			}
			Double spse = mxsq.getSpse();
			if ("0".equals(hsbz)) {
				if (spse == null || spse <= 0) {
					msgg = "第" + (i+2) + "行不含税商品税额不能为空或小于等于0，请重新填写！";
					msg += msgg;
				}
			}
			if (spje != null && spsl != null && spse != null && "1".equals(hsbz)) {
				boolean code = checkWC(spje, spsl, spse);
				if (!code) {
					msgg = "第" + (i+2) + "行商品金额，商品税率，商品税额之间的计算校验不通过，请检查！";
					msg += msgg;
				}
			}
		}
		List<String> jylshList = new ArrayList<>();
		// 判断1条交易流水号不能对应对个购方名称
		if (jyxxsqList != null) {
			for (int i = 0; i < jyxxsqList.size(); i++) {
				Jyxxsq jyxxsq = jyxxsqList.get(i);
				String jylsh = jyxxsq.getJylsh();
				String gfmc = jyxxsq.getGfmc();
				if (jylsh != null) {
					jylshList.add(jylsh);
				}
				for (int j = i + 1; j < jyxxsqList.size(); j++) {
					Jyxxsq jyxxsq1 = jyxxsqList.get(i);
					String jylsh1 = jyxxsq1.getJylsh();
					String gfmc1 = jyxxsq1.getGfmc();
					if (jylsh1 != null && gfmc != null && jylsh.equals(jylsh1) && !gfmc.equals(gfmc1)) {
						msgg = "交易流水号" + jylsh + "不能对应多个不同的购方，请重新填写！";
						msg += msgg;
					}
				}
			}
		}
		// 判断交易流水号不能重复
		if (!jylshList.isEmpty()) {
			Map mapParams = new HashMap();
			mapParams.put("gsdm", this.getGsdm());
			mapParams.put("jylshList", jylshList);
			List<Jyxxsq> jyxxsqList1 = jyxxsqservice.findByMapParams(mapParams);
			if (jyxxsqList1.size() != 0) {
				for (Jyxxsq jyxxsq : jyxxsqList1) {
					msgg = "交易流水号" + jyxxsq.getJylsh() + "已存在，请重新填写！";
					msg += msgg;
				}
			}
		}
		// 判断提取码不能重复
		if (!tqmList.isEmpty()) {
			Map mapParams = new HashMap();
			mapParams.put("gsdm", this.getGsdm());
			mapParams.put("tqmList", tqmList);
			List<Jyxxsq> jyxxsqList1 = jyxxsqservice.findByMapParams(mapParams);
			if (!jyxxsqList1.isEmpty()) {
				for (Jyxxsq jyxxq : jyxxsqList1) {
					msgg = "提取码" + jyxxq.getTqm() + "已存在，请重新填写！";
					msg += msgg;
				}
			}
		}
		// 没有异常，保存
		if ("".equals(msg)) {
			jyxxsqservice.saveAll(jyxxsqList, mxList);
		}
		return msg;
	}
	
	
	/**
	 * 获取每一行中的
	 *
	 * @param enColumnName
	 * @param pzMap
	 * @param columnIndexMap
	 * @param row
	 * @return
	 * @throws Exception
	 */
	private String getValue(String enColumnName, Map<String, DrPz> pzMap, Map<String, Integer> columnIndexMap, List row)
			throws Exception {
		DrPz drPz = pzMap.get(enColumnName);
		// Integer temp = columnIndexMap.size() - row.size();
		if (drPz == null) {
			String cnColumnName = importColumnMapping.get(enColumnName);
			Integer index = columnIndexMap.get(cnColumnName);
			if (index == null) {
				return null;
			}
			return row.get(index) == null ? null : row.get(index).toString();
		}
		if ("auto".equals(drPz.getPzlx())) {
			if ("jylsh".equals(enColumnName)) {
				String value = "JY" + System.currentTimeMillis();
				Thread.sleep(1);
				return value;
			} else if ("ddh".equals(enColumnName)) {
				String value = "DD" + System.currentTimeMillis();
				Thread.sleep(1);
				return value;
			} else if ("spse".equals(enColumnName)) {
				return "0";
			}
			return drPz.getPzz();
		} else {
			String value = "";
			String cnColumnName = drPz.getPzz();
			String pzlx = drPz.getPzlx();
			Integer columnIndex;
			boolean flag = false;
			if ("config".equals(pzlx)) {
				columnIndex = columnIndexMap.get(cnColumnName);
				flag = true;
			} else {
				columnIndex = columnIndexMap.get(importColumnMapping.get(enColumnName));
			}
			if (flag && columnIndex == null) {
				value = null;
			} else {
				try {
					value = row.get(columnIndex) == null ? null : row.get(columnIndex).toString();
				} catch (Exception e) {
					value = null;
				}
			}

			// if (value != null && !"".equals(value) &&
			// !(("spsl").equals(enColumnName) || ("sps").equals(enColumnName)
			// ||("spje").equals(enColumnName) ||("spdj").equals(enColumnName)
			// ||("spse").equals(enColumnName))) {
			// if (value.contains(".00")){
			// value = value.replace(".00", "");
			// } else if (value.contains(".0")) {
			// value = value.replace(".0", "");
			// }
			// }
			if ("hsbz".equals(enColumnName)) {
				if ("是".equals(value)) {
					return "1";
				} else if ("否".equals(value)) {
					return "0";
				} else {
					return value;
				}
			}
			return value;
		}
	}

	
}
