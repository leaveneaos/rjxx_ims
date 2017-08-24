package com.rjxx.taxeasy.controller;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import com.rjxx.taxeasy.domains.*;
import com.rjxx.taxeasy.service.*;
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
import com.rjxx.taxeasy.bizcomm.utils.DataOperte;
import com.rjxx.taxeasy.bizcomm.utils.FpclService;
import com.rjxx.taxeasy.bizcomm.utils.InvoiceResponse;
import com.rjxx.taxeasy.bizcomm.utils.SkService;
import com.rjxx.taxeasy.filter.SystemControllerLog;
import com.rjxx.taxeasy.vo.JyspmxVo;
import com.rjxx.taxeasy.vo.Spvo;
import com.rjxx.taxeasy.web.BaseController;
import com.rjxx.time.TimeUtil;
import com.rjxx.utils.ChinaNumber;
import com.rjxx.utils.ExcelUtil;
import com.rjxx.utils.Tools;

/**
 * Created by lenovo on 2015/12/14.
 */
@Controller
@RequestMapping("/kp")
public class KpController extends BaseController {
	private static final Integer DEF_DIV_SCALE = 2;

	@Autowired
	private SpvoService spvoService;
	
	@Autowired
	private FpztService ztService;
	
	@Autowired
	private SkService skService;
	
	@Autowired
	private DataOperte dataOperate;

	@Autowired
	private DrPzService drPzService;

	@Autowired
	private JylsService jylsService;

	@Autowired
	private JyspmxService jyspmxService;

	@Autowired
	private SmService smService;

	@Autowired
	private XfService xfService;
	@Autowired
	private FpclService fpclService;

	@Autowired
	DrmbService drmbService;
	@Autowired
	CszbService cszbService;
	@Autowired
	private YhcljlService cljlService;

	@Autowired
	XfMbService xfmbService;

	@Autowired
	GsxxService gsxxService;
	@Autowired
	KplsService kplsService;

	/**
	 * 页面初始化
	 *
	 * @throws Exception
	 */
	@RequestMapping
	public String index() throws Exception {
		String gsdm = this.getGsdm();
		List<Object> argList = new ArrayList<>();
		argList.add(gsdm);
		Map params = new HashMap<>();
		List<Fpzt> ztList = ztService.findAllByParams(params);
		if (ztList != null) {
			request.setAttribute("ztList", ztList);
		}
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
		return "kp/index";
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
	 * 初始化导入配置
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/initImport")
	@ResponseBody
	public List initImportConfig(Integer mbid, String xfsh) throws Exception {
		DrPz params = new DrPz();
		params.setMbid(mbid);
		List<DrPz> list = drPzService.findAllByParams(params);
		return list;
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
		jylsService.delByDjhList(convertToList(djhArr));
		return true;
	}

	private List<Integer> convertToList(String djhStrs) {
		String[] djhArr = djhStrs.split(",");
		List<Integer> djhList = new ArrayList<>();
		for (String djhStr : djhArr) {
			djhList.add(new Integer(djhStr));
		}
		return djhList;
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
		List<Jyls> jylsList = new ArrayList<>();
		List<JyspmxVo> mxList = new ArrayList<>();
		Integer num = 1;
		for (int k = 1; k < dataList.size(); k++) {
			List row = dataList.get(k);
			Jyls jyls = new Jyls();
			jyls.setGsdm(gsdm);
			jyls.setLrry(yhid);
			jyls.setXgry(yhid);
			jyls.setLrsj(TimeUtil.getNowDate());
			jyls.setXgsj(TimeUtil.getNowDate());
			jyls.setYkpjshj(0d);
			jyls.setClztdm("00");
			jyls.setFpzldm("12");
			jyls.setFpczlxdm("11");
			jyls.setYxbz("1");
			jyls.setJylsh(getValue("jylsh", pzMap, columnIndexMap, row));
			jyls.setDdh(getValue("ddh", pzMap, columnIndexMap, row));
			jyls.setJylssj(TimeUtil.getNowDate());
			if (!flag) {
				jyls.setXfid(xf.getId());
				jyls.setXfsh(xf.getXfsh());
				jyls.setXfmc(xf.getXfmc());
				jyls.setXfdz(xf.getXfdz());
				jyls.setXfdh(xf.getXfdh());
				jyls.setXfyh(xf.getXfyh());
				jyls.setXfyhzh(xf.getXfyhzh());
				jyls.setSkr(xf.getSkr());
				jyls.setKpr(xf.getKpr());
				jyls.setFhr(xf.getFhr());
			}else{
				jyls.setXfid(xf1.getId());
				jyls.setXfsh(xf1.getXfsh());
				jyls.setXfmc(xf1.getXfmc());
				jyls.setXfdz(getValue("xfdz", pzMap, columnIndexMap, row));
				jyls.setXfdh(getValue("xfdh", pzMap, columnIndexMap, row));
				jyls.setXfyh(getValue("xfyh", pzMap, columnIndexMap, row));
				jyls.setXfyhzh(getValue("xfyhzh", pzMap, columnIndexMap, row));
				jyls.setSkr(getValue("skr", pzMap, columnIndexMap, row));
				jyls.setKpr(getValue("kpr", pzMap, columnIndexMap, row));
				jyls.setFhr(getValue("fhr", pzMap, columnIndexMap, row));
			}
			jyls.setSkpid(skpid);
			jyls.setGfsh(getValue("gfsh", pzMap, columnIndexMap, row));
			jyls.setGfmc(getValue("gfmc", pzMap, columnIndexMap, row));
			jyls.setGfdz(getValue("gfdz", pzMap, columnIndexMap, row));
			jyls.setGfdh(getValue("gfdh", pzMap, columnIndexMap, row));
			jyls.setGfyh(getValue("gfyh", pzMap, columnIndexMap, row));
			jyls.setGfyhzh(getValue("gfyhzh", pzMap, columnIndexMap, row));
			String sjh = getValue("gfsjh", pzMap, columnIndexMap, row);
			if (sjh != null) {
				sjh = new BigDecimal(sjh).toPlainString();
				if (sjh.contains(".")) {
					sjh = sjh.substring(0, sjh.indexOf(","));
				}
			}
			jyls.setGfsjh(sjh);
			jyls.setBz(getValue("bz", pzMap, columnIndexMap, row));
			jyls.setGfemail(getValue("gfemail", pzMap, columnIndexMap, row));
			jyls.setTqm(getValue("tqm", pzMap, columnIndexMap, row));
			if (StringUtils.isNotBlank(jyls.getGfemail())) {
				jyls.setSffsyj("1");
			}
			jyls.setHsbz(getValue("hsbz", pzMap, columnIndexMap, row));
			Double spsl = getValue("spsl", pzMap, columnIndexMap, row) == null ? null : Double.valueOf(getValue("spsl", pzMap, columnIndexMap, row));
			Double spje = getValue("spje", pzMap, columnIndexMap, row) == null ? null : Double.valueOf(getValue("spje", pzMap, columnIndexMap, row));
			Double jshj = spje;
			if (spje != null && spsl != null && "0".equals(jyls.getHsbz())) {
				// 不含税
				jshj = spje * (1.0 + spsl);
			}
			jyls.setJshj(jshj);
//			if (jyls.getGfmc() == null && jyls.getJshj() == null) {
//				continue;
//			}
			if ( !jylsList.isEmpty() && jylsList.size()> 0) {
				boolean tmp = false;
				for (Jyls jy : jylsList) {
					if (jy.getDdh() != null && jy.getDdh().equals(jyls.getDdh())) {
						jy.setJshj(jy.getJshj() + jyls.getJshj());
						tmp = true;
						num++;
						break;
					}
				}
				if (!tmp) {
					num = 1;
					jylsList.add(jyls);
				}
			}else{
				jylsList.add(jyls);
			}
			// int djh = jyls.getDjh();
			JyspmxVo jyspmx = new JyspmxVo();
			// jyspmx.setDjh(djh);
			jyspmx.setDdh(jyls.getDdh());
			jyspmx.setSpmxxh(num);
			jyspmx.setGsdm(gsdm);
			jyspmx.setLrry(yhid);
			jyspmx.setXgry(yhid);
			jyspmx.setYkphj(0d);
			jyspmx.setFphxz("0");
			jyspmx.setLrsj(TimeUtil.getNowDate());
			jyspmx.setXgsj(TimeUtil.getNowDate());
			jyspmx.setSpdm(getValue("spdm", pzMap, columnIndexMap, row));
			jyspmx.setSpmc(getValue("spmc", pzMap, columnIndexMap, row));
			jyspmx.setSpggxh(getValue("spggxh", pzMap, columnIndexMap, row));
			jyspmx.setSpdw(getValue("spdw", pzMap, columnIndexMap, row));
			String sps = getValue("sps", pzMap, columnIndexMap, row);
			if (StringUtils.isNotBlank(sps)) {
				jyspmx.setSps(Double.valueOf(sps));
			}
			String spdjStr = getValue("spdj", pzMap, columnIndexMap, row);
			if (StringUtils.isNotBlank(spdjStr)) {
				jyspmx.setSpdj(Double.valueOf(spdjStr));
			}
			jyspmx.setSpje(spje);
			jyspmx.setSpsl(spsl);
			jyspmx.setJshj(jshj);
			jyspmx.setSpse(Double.valueOf(getValue("spse", pzMap, columnIndexMap, row)));
			if (jyspmx.getSpje() != null && jyspmx.getSpse() == 0) {
				Double temp = div(jyspmx.getJshj(), (1 + jyspmx.getSpsl()), 100);
				String je = new DecimalFormat("0.00").format(temp);
				jyspmx.setSpse(Double.valueOf(new DecimalFormat("0.00").format(jyspmx.getJshj() - Double.valueOf(je))));
			}
			mxList.add(jyspmx);
		}
		// 提取码
		List<String> tqmList = new ArrayList<>();
		// 开始数据校验
		for (int i = 0; i < jylsList.size(); i++) {
			Jyls jyls = jylsList.get(i);
			if (StringUtils.isNotBlank(jyls.getTqm())) {
				tqmList.add(jyls.getTqm());
			}
			String jylsh = jyls.getJylsh();
			if (jylsh == null || "".equals(jylsh)) { // 交易流水号的判断
				msgg = "第" + (i+2) + "行交易流水号没有填写，请重新填写！";
				msg += msgg;
			}
			if (jylsh != null && jylsh.length() > 20) { // 交易流水号长度的判断
				msgg = "第" + (i+2) + "行交易流水号超出20个字符，请重新填写！";
				msg += msgg;
			}
			String ddh = jyls.getDdh();// 订单号的校验
			if (ddh == null || "".equals(ddh)) {
				msgg = "第" + (i+2) + "行订单号不能为空，请重新填写！";
				msg += msgg;
			} else if (ddh.length() > 20) {
				msgg = "第" + (i+2) + "行订单号超出20个字符，请重新填写！";
				msg += msgg;
			}
			String xfsh = jyls.getXfsh();
			String xfmc = jyls.getXfmc();// 销方名称，销方税号的校验
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
			String xfdz = jyls.getXfdz();// 销方地址的校验
			if (xfdz == null || "".equals(xfdz)) {
				msgg = "第" + (i+2) + "行销方地址没有填写，请重新填写！";
				msg += msgg;
			} else if (xfdz.length() > 100) {
				msgg = "第" + (i+2) + "行销方地址超出100个字符，请重新填写！";
				msg += msgg;
			}
			String xfdh = jyls.getXfdh();// 销方电话校验
			if (xfdh == null || "".equals(xfdh)) {
				msgg = "第" + (i+2) + "行销方电话没有填写，请重新填写！";
				msg += msgg;
			} else if (xfdh.length() > 25) {
				msgg = "第" + (i+2) + "行销方电话超出25个字符，请重新填写！";
				msg += msgg;
			}
			String xfyh = jyls.getXfyh();// 销方银行校验
			if (xfyh == null || "".equals(xfyh)) {
				msgg = "第" + (i+2) + "行销方银行没有填写，请重新填写！";
				msg += msgg;
			} else if (xfyh.length() > 25) {
				msgg = "第" + (i+2) + "行销方银行超出25个字符，请重新填写！";
				msg += msgg;
			}
			String xfyhzh = jyls.getXfyhzh();// 销方银行账号的校验
			if (xfyhzh == null || "".equals(xfyhzh)) {
				msgg = "第" + (i+2) + "行销方银行账号没有填写，请重新填写！";
				msg += msgg;
			} else if (xfyhzh.length() > 30) {
				msgg = "第" + (i+2) + "行销方银行超出30个字符，请重新填写！";
				msg += msgg;
			}
			String skr = jyls.getSkr();// 收款人校验
			if (skr != null && skr.length() > 10) {
				msgg = "第" + (i+2) + "行收款人超出10个字符，请重新填写！";
				msg += msgg;
			}
			String fhr = jyls.getFhr();// 复核人校验
			if (fhr != null && fhr.length() > 10) {
				msgg = "第" + (i+2) + "行复核人超出10个字符，请重新填写！";
				msg += msgg;
			}
			String kpr = jyls.getKpr();// 开票人校验
			if (kpr != null && kpr.length() > 10) {
				msgg = "第" + (i+2) + "行开票人超出10个字符，请重新填写！";
				msg += msgg;
			}
			String gfsh = jyls.getGfsh();// 购方税号校验
			if (gfsh != null && !(gfsh.length() == 15 || gfsh.length() == 18)) { // 购方税号长度的判断
				msgg = "第" + (i+2) + "行购方税号不是15位或18位，请重新填写！";
				msg += msgg;
			}
			String gfmc = jyls.getGfmc();// 购方名称校验
			if (gfmc == null || "".equals(gfmc)) {
				msgg = "第" + (i+2) + "行购方名称没有填写，请重新填写！";
				msg += msgg;
			}
			if (gfmc != null && gfmc.length() > 100) { // 购方名称长度的判断
				msgg = "第" + (i+2) + "行购方名称超出100个字符，请重新填写！";
				msg += msgg;
			}
			String gfyh = jyls.getGfyh();// 购方银行校验
			if (gfyh != null && gfyh.length() > 50) { // 购方银行长度的判断
				msgg = "第" + (i+2) + "行购方银行超出50个字符，请重新填写！";
				msg += msgg;
			}
			String gfyhzh = jyls.getGfyhzh();// 购方银行账号校验
			if (gfyhzh != null && gfyhzh.length() > 50) { // 购方银行账号长度的判断
				msgg = "第" + (i+2) + "行购方银行账号超出50个字符，请重新填写！";
				msg += msgg;
			}
			String gfdz = jyls.getGfdz();// 购方地址校验
			if (gfdz != null && gfdz.length() > 100) { // 购方地址长度的判断
				msgg = "第" + (i+2) + "行购方地址超出200个字符，请重新填写！";
				msg += msgg;
			}
			String gfdh = jyls.getGfdh();// 购方电话校验
			if (gfdh != null && gfdh.length() > 25) { // 购方电话长度的判断
				msgg = "第" + (i+2) + "行购方电话超出25位，请重新填写！";
				msg += msgg;
			}
			String gfEmail = jyls.getGfemail();// 购方email校验
			if (gfEmail != null && !"".equals(gfEmail.trim()) && !gfEmail.matches("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$")) {
				msgg = "第" + (i+2) + "行购方email格式不正确，请重新填写！";
				msg += msgg;
			}
			String hsbz = jyls.getHsbz();// 含税标志校验
			if (hsbz == null || "".equals(hsbz)) {
				msgg = "第" + (i+2) + "行含税标志不能为空，请重新填写！";
				msg += msgg;
			} else if (!("0".equals(hsbz) || "1".equals(hsbz))) {
				msgg = "第" + (i+2) + "行含税标志只能填写1或0！";
				msg += msgg;
			}
			JyspmxVo spmx = mxList.get(i);
			String spdm = spmx.getSpdm();
			if (spdm != null && spdm.length() > 20) {
				msgg = "第" + (i+2) + "行商品代码超过20个字符，请重新填写！";
				msg += msgg;
			}
			String spmc = spmx.getSpmc();
			if (spmc == null || "".equals(spmc)) {
				msgg = "第" + (i+2) + "行商品名称不能为空，请重新填写！";
				msg += msgg;
			} else if (spmc.length() > 50) {
				msgg = "第" + (i+2) + "行商品名称超过50个字符，请重新填写！";
				msg += msgg;
			}
			String spggxh = spmx.getSpggxh();
			if (spggxh != null && spggxh.length() > 18) {
				msgg = "第" + (i+2) + "行商品规格型号超过18个字符，请重新填写！";
				msg += msgg;
			}
			String spdw = spmx.getSpdw();
			if (spdw != null && spdw.length() > 5) {
				msgg = "第" + (i+2) + "行商品单位超过5个字符，请重新填写！";
				msg += msgg;
			}
			Double sps = spmx.getSps();
			if (sps != null && !String.valueOf(sps).matches("^[0-9]{0,16}+(.[0-9]{0,6})?$")) {
				msgg = "第" + (i+2) + "行商品数格式不正确，请重新填写！";
				msg += msgg;
			}
			Double spdj = spmx.getSpdj();
			if (spdj != null && !String.valueOf(spdj).matches("^[0-9]{0,16}+(.[0-9]{0,6})?$")) {
				msgg = "第" + (i+2) + "行商品单价格式不正确，请重新填写！";
				msg += msgg;
			}
			Double spje = spmx.getSpje();
			if (spje == null || spje <= 0) {
				msgg = "第" + (i+2) + "行商品金额不能为空或小于等于0，请重新填写！";
				msg += msgg;
			}
			Double spsl = spmx.getSpsl();
			if (spsl == null || "".equals(spsl)) {
				msgg = "第" + (i+2) + "行商品税率不能为空，请重新填写！";
				msg += msgg;
			} else if (!slList.contains(String.valueOf(spsl))) {
				msgg = "第" + (i+2) + "行商品税率数据库中不存在，请重新填写！";
				msg += msgg;
			}
			Double spse = spmx.getSpse();
			if ("0".equals(hsbz)) {
				if (spse == null || spse <= 0) {
					msgg = "第" + (i+2) + "行不含税商品税额不能为空或小于等于0，请重新填写！";
					msg += msgg;
				}
			}
			if (spje != null && spsl != null && spse != null) {
				boolean code = checkWC(spje, spsl, spse);
				if (!code) {
					msgg = "第" + (i+2) + "行商品金额，商品税率，商品税额之间的计算校验不通过，请检查！";
					msg += msgg;
				}
			}
		}
		List<String> jylshList = new ArrayList<>();
		// 判断1条交易流水号不能对应对个购方名称
		if (jylsList != null) {
			for (int i = 0; i < jylsList.size(); i++) {
				Jyls jyls = jylsList.get(i);
				String jylsh = jyls.getJylsh();
				String gfmc = jyls.getGfmc();
				if (jylsh != null) {
					jylshList.add(jylsh);
				}
				for (int j = i + 1; j < jylsList.size(); j++) {
					Jyls jyls1 = jylsList.get(i);
					String jylsh1 = jyls1.getJylsh();
					String gfmc1 = jyls1.getGfmc();
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
			List<Jyls> jylsList1 = jylsService.findByMapParams(mapParams);
			if (jylsList1.size() != 0) {
				for (Jyls jyls : jylsList1) {
					msgg = "交易流水号" + jyls.getJylsh() + "已存在，请重新填写！";
					msg += msgg;
				}
			}
		}
		// 判断提取码不能重复
		if (!tqmList.isEmpty()) {
			Map mapParams = new HashMap();
			mapParams.put("gsdm", this.getGsdm());
			mapParams.put("tqmList", tqmList);
			List<Jyls> jylsList1 = jylsService.findByMapParams(mapParams);
			if (!jylsList1.isEmpty()) {
				for (Jyls jyls : jylsList1) {
					msgg = "提取码" + jyls.getTqm() + "已存在，请重新填写！";
					msg += msgg;
				}
			}
		}
		// 没有异常，保存
		if ("".equals(msg)) {
			jylsService.saveAll(jylsList, mxList);
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
	 * 开票
	 *
	 * @param djhArr
	 * @return
	 * @throws Exception 
	 * @throws Exception 
	 */
	@RequestMapping(value = "/doKp")
	@ResponseBody
	@SystemControllerLog(description = "发票开具",key = "djhArr")
	public Map doKp(String djhArr,Double kpxe,String dybz) throws Exception {
/*		Map<String, Object> result = new HashMap<String, Object>();
		boolean fl=true;
		List<Integer> djhList = convertToList(djhArr);
		String[] djhs = djhArr.split(",");
		HashSet<Integer> set = new HashSet<>();
		for (String st : djhs) {
			Jyls jyls = jylsService.findOne(Integer.valueOf(st));
			set.add(jyls.getSkpid());
		}
		if (set.size()!=djhs.length) {
			fl=false;
		}
		for (int i = 0; i < djhs.length; i++) {
			//判断是否直连开票
			Jyls jyls = jylsService.findOne(Integer.valueOf(djhs[i]));
			Cszb cszb = cszbService.getSpbmbbh(getGsdm(), jyls.getXfid(), jyls.getSkpid(), "sfzlkp");
			if (null!=cszb&&cszb.getCsz().equals("是")) {
				if (!fl) {
					result.put("csz", "2");
					InvoiceResponse invoiceResponse = skService.getCodeAndNo(jyls.getSkpid(), jyls.getFpzldm());
					if ("0000".equals(invoiceResponse.getReturnCode())) {
						result.put("msg", "获取号码成功 ");
						result.put("success", true);
						result.put("fpdm", invoiceResponse.getFpdm());
						result.put("fphm", invoiceResponse.getFphm());
						return result;
					}else{
						result.put("success1", false);
						result.put("msg", invoiceResponse.getReturnMessage());
						return result;
					}
				}else{
					result.put("csz", "1");
					InvoiceResponse invoiceResponse = fpclService.kpcl1(Integer.valueOf(djhs[i]),dybz,1);
					 if (!invoiceResponse.getReturnCode().equals("0000")) {
							result.put("success", false);
							result.put("msg", "第"+(i+1)+"条流水申请开具失败"+invoiceResponse.getReturnMessage());
							return result;
						}
				}
			}else{
				result.put("csz", "0");
				fpclService.kpcl1(Integer.valueOf(djhs[i]),dybz,0);

			}
			cljlService.saveYhcljl(getYhid(), "申请开具发票");
			result.put("success", true);
			result.put("msg", "申请开票成功！");
		}
		
		} catch (Exception ex) {
			ex.printStackTrace();
			result.put("failure", true);
			result.put("msg", "保存出现错误: " + ex.getMessage());
		}
		return result;*/
		Map<String, Object> result = new HashMap<String, Object>();
		List<Integer> djhList = convertToList(djhArr);
		String[] djhs = djhArr.split(",");
		for (int i = 0; i < djhs.length; i++) {
             boolean flag = fpclService.kpcl1(Integer.valueOf(djhs[i]),dybz);
		if (!flag) {
			result.put("success", false);
			result.put("msg", "第"+(i+1)+"条流水申请开具失败");
			return result;
		}
		}
		cljlService.saveYhcljl(getYhid(), "申请开具发票");
		result.put("success", true);
		result.put("msg", "申请开票成功！");
	/*	} catch (Exception ex) {
			ex.printStackTrace();
			result.put("failure", true);
			result.put("msg", "保存出现错误: " + ex.getMessage());
		}*/
		return result;
	}

	
	@RequestMapping(value = "/doKp1")
	@ResponseBody
	@SystemControllerLog(description = "发票开具",key = "djhArr")
	public Map doKp1(String djhArr,Double kpxe,String dybz) throws Exception {
		Map<String, Object> result = new HashMap<>();
		String[] djhs = djhArr.split(",");
			for (int j = 0; j < djhs.length; j++) {
			boolean invoiceResponse = fpclService.kpcl1(Integer.valueOf(djhs[j]),dybz);
			 if (!invoiceResponse) {
					result.put("success", false);
					result.put("msg", "第"+(j+1)+"条流水申请开具失败");
					return result;
				}
		}
			result.put("success", true);
			result.put("msg", "开票成功");
		return result;
	}
	
	/**
	 * 开票
	 *
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/doAllKp")
	@ResponseBody
	@Transactional
	public Map doAllKp(String clztdm, String xfsh, String gfmc, String ddh,
			String jylsh, String rqq, String rqz) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("gsdm", getGsdm());
			Gsxx gsxx = gsxxService.findOneByParams(params);

			List<Xf> xfs = getXfList();
			List<Skp> skps = getSkpList();
			if (xfs != null && xfs.size() > 0) {
				params.put("xfs", xfs);
			}
			if (skps != null && skps.size() > 0) {
				params.put("skps", skps);
			}
			params.put("xfsh", xfsh);
			params.put("gfmc", gfmc);
			params.put("ddh", ddh);
			params.put("jylsh", jylsh);

			if (rqq != null && !rqq.trim().equals("") && rqz != null && !rqz.trim().equals("")) { // 名称参数非空时增加名称查询条件
				params.put("rqq", rqq);
				params.put("rqz", TimeUtil.getAfterDays(rqz, 1));
			} else if (rqq != null && !rqq.trim().equals("") && (rqz == null || rqz.trim().equals(""))) {
				params.put("rqq", rqq);
				params.put("rqz", TimeUtil.getAfterDays(rqq, 1));
			} else if ((rqq == null || rqq.trim().equals("")) && rqz != null && !rqz.trim().equals("")) {
				params.put("rqq", rqz);
				params.put("rqz", TimeUtil.getAfterDays(rqz, 1));
			}
			params.put("clztdm", "00");
			params.put("fpzldm", "12");
			params.put("fpczlxdm", "11");
			params.put("gsdm", this.getGsdm());
			
			List<Jyls> list = jylsService.findAllJyls(params);
			if (gsxx != null && gsxx.getWsUrl() != null && !"".equals(gsxx.getWsUrl())) {
				jylsService.doAllKp(list, "03");
			}else{
				jylsService.doAllKp(list, "01");
			}
			result.put("success", true);
			result.put("msg", "请求成功，请耐心等待开票结果");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.put("failure", true);
			result.put("msg", "保存出现错误: " + ex.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/getXfxx")
	@ResponseBody
	public Map getXfxx() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("xfmc", getXfmc());
		result.put("xfsh", getXfsh());
		return result;
	}

	@RequestMapping(value = "/getjyspmxlist")
	@ResponseBody
	public Map getjyspmxlist( String djh,int length, int start, int draw) {
		String gsdm = this.getGsdm();
		Pagination pagination = new Pagination();
		pagination.setPageNo(start / length + 1);
		pagination.setPageSize(length);
        String []djhs=djh.split(",");
        List djhlist=new ArrayList();
        for(int i=0;i<djhs.length;i++){
			djhlist.add(djhs[i]);
		}
		pagination.addParam("djhlist", djhlist);
		pagination.addParam("gsdm", gsdm);
		List<Jyspmx> jyspmxList = jyspmxService.findByPage(pagination);

		int total = pagination.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		result.put("draw", draw);
		result.put("data", jyspmxList);
		return result;
	}

	@RequestMapping(value = "/getjylslist")
	@ResponseBody
	public Map getjylslist(int length, int start, int draw, String clztdm, String xfsh, String gfmc, String ddh,
			String fpzldm, String rqq, String rqz,boolean loaddata) {
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
		if ("".equals(fpzldm)) {
			pagination.addParam("fpzldm", null);
		}else{
			pagination.addParam("fpzldm", fpzldm);
		}
		pagination.addParam("fpczlxdm", "11");
		pagination.addParam("gsdm", this.getGsdm());
		pagination.addParam("orderBy", "lrsj desc");

		List<Jyls> jylsList = jylsService.findByPage(pagination);
		int total = pagination.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		if(loaddata){
			result.put("recordsTotal", total);
			result.put("recordsFiltered", total);
			result.put("draw", draw);
			result.put("data", jylsList);
		}else{
			result.put("recordsTotal", 0);
			result.put("recordsFiltered", 0);
			result.put("draw", draw);
			result.put("data", new ArrayList<>());
		}
		return result;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Map save() {
		Map<String, Object> result = new HashMap<String, Object>();
		String gsdm = getGsdm();
		String xfid = request.getParameter("xfid_edit");
		int yhid = getYhid();
		com.rjxx.taxeasy.domains.Xf xf = xfService.findOne(Integer.parseInt(xfid));
		Jyls jyls = new Jyls();
		jyls.setClztdm("00");
		jyls.setDdh(request.getParameter("ddh_edit"));
		jyls.setGfsh(request.getParameter("gfsh_edit"));

		jyls.setGfmc(request.getParameter("gfmc_edit"));
		jyls.setGfyh(request.getParameter("gfyh_edit"));
		jyls.setGfyhzh(request.getParameter("gfzh_edit"));
		jyls.setGfsjh(request.getParameter("gfsjh_edit"));

		jyls.setGflxr(request.getParameter("gflxr_edit"));
		jyls.setBz(request.getParameter("gfbz_edit"));
		jyls.setGfemail(request.getParameter("gfemail_edit"));
		jyls.setGfdz(request.getParameter("gfdz_edit"));
		String tqm = request.getParameter("tqm_edit");
		if (StringUtils.isNotBlank(tqm)) {
			Jyls params = new Jyls();
			params.setGsdm(gsdm);
			params.setTqm(tqm);
			Jyls tmp = jylsService.findOneByParams(params);
			if (tmp != null) {
				result.put("failure", true);
				result.put("msg", "提取码已经存在");
				return result;
			}
		}
		jyls.setTqm(tqm);
		jyls.setJylsh("JY" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
		jyls.setJshj(0.00);
		jyls.setYkpjshj(0.00);
		jyls.setHsbz("0");
		jyls.setXfid(xf.getId());
		jyls.setXfsh(xf.getXfsh());
		jyls.setXfmc(xf.getXfmc());
		jyls.setLrsj(TimeUtil.getNowDate());
		jyls.setXgsj(TimeUtil.getNowDate());
		jyls.setJylssj(TimeUtil.getNowDate());
		jyls.setFpzldm("12");
		jyls.setFpczlxdm("11");
		jyls.setXfyh(xf.getXfyh());
		jyls.setXfyhzh(xf.getXfyhzh());
		jyls.setXfdz(xf.getXfdz());
		jyls.setXfdh(xf.getXfdh());
		if (StringUtils.isNotBlank(jyls.getGfemail())) {
			jyls.setSffsyj("1");
		}
		jyls.setKpr(xf.getKpr());
		jyls.setFhr(xf.getFhr());
		jyls.setSkr(xf.getSkr());
		jyls.setSsyf(new SimpleDateFormat("yyyyMM").format(new Date()));
		jyls.setLrry(yhid);
		jyls.setXgry(yhid);
		jyls.setYxbz("1");
		jyls.setGsdm(gsdm);
		String skpid = request.getParameter("skpid_edit");
		if (skpid != null && !"".equals(skpid)) {
			jyls.setSkpid(Integer.parseInt(skpid));
		}
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
			List<Jyspmx> jyspmxList = new ArrayList<>();
			for (int c = 0; c < mxcount; c++) {
				Jyspmx jyspmx = new Jyspmx();
				int xxh = c + 1;
				jyspmx.setSpmxxh(xxh);
				jyspmx.setFphxz("0");
				jyspmx.setSpdm(spdms[c]);
				jyspmx.setSpmc(spmcs[c]);
				jyspmx.setSpje(Double.valueOf(spjes[c]));
				if (spsls.length != 0) {
					jyspmx.setSpsl(Double.valueOf(spsls[c]));
				}
				jyspmx.setJshj(Double.valueOf(jshjs[c]));
				if (spges.length != 0) {
					try {
						jyspmx.setSpggxh(spges[c]);
					} catch (Exception e) {
						jyspmx.setSpggxh(null);

					}
				}
				try {
					jyspmx.setSps(Double.valueOf(spss[c]));
				} catch (Exception e) {
					jyspmx.setSps(null);
				}
				if (spdws.length != 0) {
					try {
						jyspmx.setSpdw(spdws[c]);
					} catch (Exception e) {
						jyspmx.setSpdw(null);

					}
				}
				if (spdjs.length != 0) {
					try {
						jyspmx.setSpdj(Double.valueOf(spdjs[c]));
					} catch (Exception e) {
						jyspmx.setSpdj(null);

					}
				}
				if (spses.length != 0) {
					jyspmx.setSpse(Double.valueOf(spses[c]));
				}
				jyspmx.setYkphj(0.00);
				jyspmx.setLrry(yhid);
				jyspmx.setLrsj(TimeUtil.getNowDate());
				jyspmx.setXgsj(TimeUtil.getNowDate());
				jyspmx.setXgry(yhid);
				jyspmx.setGsdm(gsdm);
				jshj += jyspmx.getJshj();
				jyspmxList.add(jyspmx);
			}
			jyls.setJshj(jshj);
			jylsService.saveJyls(jyls, jyspmxList);
			result.put("success", true);
			result.put("djh", jyls.getDjh());
		} catch (Exception ex) {
			ex.printStackTrace();
			result.put("failure", true);
			result.put("msg", "保存出现错误: " + ex.getMessage());
		}
		return result;
	}

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
	@RequestMapping(value = "/hqfphm",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> hqfphm(String fpzldm,Integer skpid) throws Exception{
		Map<String, Object> result = new HashMap<>();
			InvoiceResponse invoiceResponse = skService.getCodeAndNo(skpid, fpzldm);
		if ("0000".equals(invoiceResponse.getReturnCode())) {
			result.put("msg", "获取号码成功 ");
			result.put("success", true);
			result.put("fpdm", invoiceResponse.getFpdm());
			result.put("fphm", invoiceResponse.getFphm());
		}else{
			result.put("success", false);
			result.put("msg", invoiceResponse.getReturnMessage());
		}
		return result;
	}

	/**
	 * 发票重新开具功能
	 * @param kplshs
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/fpck")
	@ResponseBody
	public Map fpck(String kplshs) throws Exception {
		Map<String, Object> result = new HashMap<>();
		String []kpsqh= kplshs.split(",");
        for(int i=0;i<kpsqh.length;i++){
            Kpls kpls=kplsService.findOne(Integer.parseInt(kpsqh[i]));
            if(kpls.getFpztdm().equals("05")||kpls.getFpztdm().equals("14")){
				try{
					Cszb cszb = cszbService.getSpbmbbh(kpls.getGsdm(), kpls.getXfid(), null, "kpfs");
					if(cszb.getCsz().equals("01")) {
						kpls.setFpztdm("04");
						kplsService.save(kpls);
					}else if(cszb.getCsz().equals("03")){
                       skService.SkServerKP(Integer.parseInt(kpsqh[i]));
					}
					result.put("success", true);
					result.put("msg", "重新开具成功！");
				}catch (Exception e){
					result.put("success", false);
					result.put("msg", "第"+(i+1)+"条流水重新开具失败！");
				}
			}else{
				result.put("success", false);
				result.put("msg", "第"+(i+1)+"条流水不是开具失败的发票流水!");

            }
		}
		return result;
	}
	@RequestMapping(value = "/kpyl")
	public String kpyl(String kpsqhs) throws Exception {
		Map<String, Object> result = new HashMap<>();
		String kpsqh= kpsqhs.split(",")[0];
		Jyspmx jyspmx = new Jyspmx();
		jyspmx.setDjh(Integer.valueOf(kpsqh));
		List<Jyspmx> mxcl = jyspmxService.findAllByParams(jyspmx);
		Jyls jyls = jylsService.findOne(Integer.valueOf(kpsqh));
		List dxlist = new ArrayList();
		ChinaNumber cn = new ChinaNumber();
		Double aa = 0.00;
		for (int x = 0; x < mxcl.size(); x++) {
			aa = aa + mxcl.get(x).getJshj();
		}
		String jshjstr = new DecimalFormat("0.00").format(aa);
		dxlist.add(cn.getCHSNumber(jshjstr));
		session.setAttribute("cffplList", mxcl);
		session.setAttribute("jyls", jyls);
		session.setAttribute("zwlist", dxlist);
		return "kp/fapiao";
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
}
