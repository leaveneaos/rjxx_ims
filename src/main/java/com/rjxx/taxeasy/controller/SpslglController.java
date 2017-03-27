package com.rjxx.taxeasy.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.domains.DrPz;
import com.rjxx.taxeasy.domains.Sm;
import com.rjxx.taxeasy.domains.Sp;
import com.rjxx.taxeasy.domains.Spz;
import com.rjxx.taxeasy.domains.SpzSp;
import com.rjxx.taxeasy.domains.SpzXf;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.filter.SystemControllerLog;
import com.rjxx.taxeasy.service.SmService;
import com.rjxx.taxeasy.service.SpService;
import com.rjxx.taxeasy.service.SpbmService;
import com.rjxx.taxeasy.service.SpvoService;
import com.rjxx.taxeasy.service.SpzService;
import com.rjxx.taxeasy.service.SpzSpService;
import com.rjxx.taxeasy.service.SpzXfService;
import com.rjxx.taxeasy.service.XfService;
import com.rjxx.taxeasy.vo.Spbm;
import com.rjxx.taxeasy.vo.Spvo;
import com.rjxx.taxeasy.web.BaseController;
import com.rjxx.time.TimeUtil;
import com.rjxx.utils.ExcelUtil;

@Controller
@RequestMapping("/spslgl")
public class SpslglController extends BaseController {
	@Autowired
	private SmService smService;

	@Autowired
	private SpService spService;

	@Autowired
	private SpvoService spvoService;

	@Autowired
	private SpbmService spbmService;

	@Autowired
	private XfService xfService;

	@Autowired
	private SpzService spzService;

	@Autowired
	private SpzSpService sss;

	@Autowired
	private SpzXfService sxs;

	/**
	 * 导入字段映射
	 */
	private static Map<String, String> importColumnMapping = new HashMap<String, String>() {
		{
			put("spbm", "商品编码");
			put("spmc", "商品名称");
			put("spsl", "商品税率");
			put("spggxh", "商品规格型号");
			put("spdw", "商品单位");
			put("spdj", "商品单价");
		}
	};

	@RequestMapping
	public String index() {
		List<Sm> list = smService.findAllByParams(new Sm());
		request.setAttribute("smlist", list);
		Map<String, Object> params = new HashMap<>();
		List<Spbm> spbms = spbmService.findAllByParam(params);
		request.setAttribute("spbms", spbms);
		params.put("gsdm", getGsdm());
		Sp sp = new Sp();
		sp.setGsdm(getGsdm());
		List<Sp> sps = spService.findAllByParams(sp);
		List<Xf> xfs = xfService.findAllByMap(params);
		request.setAttribute("sps", sps);
		request.setAttribute("xfs", xfs);
		return "spslgl/index";
	}

	@RequestMapping(value = "/getSplist")
	@ResponseBody
	public Map getAllSp(int length, int start, int draw, String spmc, String spdm, String txt, Double sl,
			Integer slid) {
		Map<String, Object> result = new HashMap<String, Object>();
		Pagination pagination = new Pagination();
		pagination.setPageNo(start / length + 1);
		pagination.setPageSize(length);
		pagination.addParam("spmc", spmc);
		pagination.addParam("spdm", spdm);
		pagination.addParam("slid", slid);
		pagination.addParam("sl", txt);
		pagination.addParam("gsdm", getGsdm());
		pagination.addParam("orderBy", "lrsj");
		List<Spvo> list = spvoService.findAllOnPage(pagination);
		int total = pagination.getTotalRecord();
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		result.put("draw", draw);
		result.put("data", list);
		return result;
	}

	@RequestMapping(value = "/getSpzs")
	@ResponseBody
	public Map getSpzs(int length, int start, int draw, String spzmc) {
		Map<String, Object> result = new HashMap<String, Object>();
		Pagination pagination = new Pagination();
		pagination.setPageNo(start / length + 1);
		pagination.setPageSize(length);
		pagination.addParam("spzmc", spzmc);
		pagination.addParam("gsdm", getGsdm());
		List<Spz> list = spzService.findByPage(pagination);
		int total = pagination.getTotalRecord();
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		result.put("draw", draw);
		result.put("data", list);
		return result;
	}

	@RequestMapping(value = "/add")
	@ResponseBody
	@SystemControllerLog(description = "新增商品",key = "spdm")  
	public Map add(String sl, Integer smid, String spflmc, String spdj, String spdw, String spdm, String spggxh,
			String spmc, String spbm) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Sp tp = new Sp();
			tp.setGsdm(getGsdm());
			tp.setSpdm(spdm);
			Sp sp = spService.findOneByParams(tp);
			if (sp != null) {
				result.put("failure", true);
				result.put("msg", "商品代码已经存在");
				return result;
			}
			tp.setLrry(getYhid());
			tp.setLrsj(TimeUtil.getNowDate());
			tp.setSmid(smid);
			;
			if (spdj != null && !"".equals(spdj)) {
				tp.setSpdj(Double.parseDouble(spdj));
			}
			tp.setSpdw(spdw);
			tp.setSpdm(spdm);
			tp.setSpfldm("1");
			tp.setSpggxh(spggxh);
			tp.setSpmc(spmc);
			tp.setSpbm(spbm);
			tp.setXgry(getYhid());
			tp.setXgsj(TimeUtil.getNowDate());
			tp.setYxbz("1");
			tp.setGsdm(getGsdm());
			spService.save(tp);
			Sm sm = smService.findOne(tp.getSmid());
			result.put("spfhsl", sm.getSl());
			result.put("spfh", tp);
			result.put("success", true);
			result.put("msg", "保存成功");
			
	
		} catch (Exception e) {
			result.put("failure", false);
			result.put("msg", "保存出现错误:服务异常或者该记录已存在" + e);
			e.printStackTrace();
		}

		return result;
	}

	@RequestMapping(value = "/update")
	@ResponseBody
	@SystemControllerLog(description = "修改商品",key = "id")  
	public Map udpate(int id, String sl, Integer smid, String spflmc, String spdj, String spdw, String spdm,
			String spggxh, String spmc, String spbm) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> prms = new HashMap<>();
			Sp tp = new Sp();
			tp.setGsdm(getGsdm());
			tp.setSpdm(spdm);
			tp.setId(id);
			Sp sp = spService.findOneByParams(tp);
			if (sp != null) {
				result.put("failure", true);
				result.put("msg", "商品代码已经存在");
				return result;
			}
			tp.setSmid(smid);
			if (spdj != null && !"".equals(spdj)) {
				tp.setSpdj(Double.parseDouble(spdj));
			}
			tp.setSpdw(spdw);
			tp.setSpdm(spdm);
			tp.setSpfldm("1");
			tp.setSpggxh(spggxh);
			tp.setSpmc(spmc);
			tp.setSpbm(spbm);
			tp.setLrry(sp == null ? getYhid() : sp.getLrry());
			tp.setLrsj(sp == null ? TimeUtil.getNowDate() : sp.getLrsj());
			tp.setXgry(getYhid());
			tp.setXgsj(TimeUtil.getNowDate());
			tp.setYxbz("1");
			tp.setGsdm(getGsdm());
			spService.save(tp);
			result.put("success", true);
			result.put("msg", "保存成功");
		} catch (Exception e) {
			result.put("failure", false);
			result.put("msg", "保存出现错误:服务异常或者该记录已存在" + e);
			e.printStackTrace();
		}

		return result;
	}

	@RequestMapping(value = "/getSpbm")
	@ResponseBody
	public Map getSpbm(String spbm) {
		Map<String, Object> result = new HashMap<>();
		Map<String, Object> params = new HashMap<>();
		params.put("spbm", spbm);
		try {
			List<Spbm> list = spbmService.findAllByParam(params);
			result.put("spbms", list);
			result.put("success", true);
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "查询异常，请稍后再试");
		}
		return result;
	}

	@RequestMapping(value = "/getSpbms")
	@ResponseBody
	public List<Map<String, Object>> getSpbms() {
		List<Map<String, Object>> nodes = new ArrayList<Map<String, Object>>();
		Map<String, Object> params = new HashMap<>();
		params.put("spbms", "1");
		List<Spbm> spbms = spbmService.findAllByParams(params);
		for (Spbm spbm : spbms) {
			Map<String, Object> node = new HashMap<String, Object>();
			node.put("id", spbm.getSpbm());
			node.put("text", spbm.getSpbm() + "|" + spbm.getSpmc());
			node.put("pid", spbm.getSjspbm());
			nodes.add(node);
		}
		// 循环获取全部子节点
		params.put("spbms", null);
		spbms = spbmService.findAllByParam(params);
		List<Map<String, Object>> doing = new ArrayList<Map<String, Object>>();
		doing.addAll(nodes);
		while (!doing.isEmpty()) {
			List<Map<String, Object>> todo = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> item : doing) {
				List<Spbm> spbms2 = new ArrayList<Spbm>();
				if (spbms != null) {
					for (int i = 0; i < spbms.size(); i++) {
						if ((spbms.get(i).getSjspbm() == null ? "" : spbms.get(i).getSjspbm()).equals(item.get("id"))) {
							spbms2.add(spbms.get(i));
						}
					}
				}
				if (spbms2.isEmpty())
					continue;
				List<Object> children = new ArrayList<Object>();
				for (Spbm spbm : spbms2) {
					Map<String, Object> node = new HashMap<String, Object>();
					node.put("id", spbm.getSpbm());
					node.put("text", spbm.getSpbm() + "|" + spbm.getSpmc());
					node.put("pid", spbm.getSjspbm());
					children.add(node);
					todo.add(node);
				}
				item.put("children", children.toArray(new Object[children.size()]));
			}
			doing = todo;
		}
		return nodes;
	}

	@RequestMapping(value = "/delete")
	@ResponseBody
	@SystemControllerLog(description = "删除商品",key = "ids")  
	public Map delete(String ids) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String[] strs = ids.split(",");
			List<Sp> list = new ArrayList<>();
			for (String str : strs) {
				Sp sp = spService.findOne(Integer.valueOf(str));
				sp.setYxbz("0");
				sp.setXgsj(TimeUtil.getNowDate());
				sp.setXgry(getYhid());
				list.add(sp);
			}
			spService.save(list);
			result.put("success", true);
			result.put("msg", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("failure", true);
			result.put("msg", "删除失败：" + e);
		}

		return result;
	}

	@RequestMapping(value = "/saveSpz")
	@ResponseBody
	@Transactional
	@SystemControllerLog(description = "新增商品组",key = "spzmc")  
	public Map saveSpz(String spzmc, String zbz) {
		Map<String, Object> result = new HashMap<String, Object>();
		String[] sps = request.getParameterValues("spz");
		String[] xfids = request.getParameterValues("xfid");
		if (sps == null || sps.length < 1) {
			result.put("success", false);
			result.put("msg", "请勾选商品组商品");
			return result;
		}
		if (xfids == null || xfids.length < 1) {
			result.put("success", false);
			result.put("msg", "请勾选商品组销方");
			return result;
		}
		Map<String, Object> params = new HashMap<>();
		params.put("gsdm", getGsdm());
		params.put("spzmc", spzmc);
		try {
			Spz spz = spzService.findOneByParams(params);
			if (spz != null) {
				result.put("success", false);
				result.put("msg", "商品组名称已存在");
				return result;
			}
			spz = new Spz();
			spz.setZbz(zbz);
			spz.setSpzmc(spzmc);
			spz.setGsdm(getGsdm());
			spz.setYxbz("1");
			spz.setLrry(getYhid());
			spz.setLrsj(new Date());
			spz.setXgry(getYhid());
			spz.setXgsj(new Date());
			spzService.save(spz);
			saveSpzsp(spz.getId(), sps, 0, 1);
			saveSpzxf(spz.getId(), xfids, 0, 1);
			result.put("success", true);
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "新增商品组异常：" + e);
		}
		return result;
	}

	@RequestMapping(value = "/updateSpz")
	@ResponseBody
	@Transactional
	@SystemControllerLog(description = "修改商品组",key = "spzid")  
	public Map updateSpz(Integer spzid, String spzmc, String zbz) {
		Map<String, Object> result = new HashMap<String, Object>();
		String[] sps = request.getParameterValues("spz");
		String[] xfids = request.getParameterValues("xfid");
		if (sps == null || sps.length < 1) {
			result.put("success", false);
			result.put("msg", "请勾选商品组商品");
			return result;
		}
		if (xfids == null || xfids.length < 1) {
			result.put("success", false);
			result.put("msg", "请勾选商品组销方");
			return result;
		}
		Map<String, Object> params = new HashMap<>();
		params.put("gsdm", getGsdm());
		params.put("spzmc", spzmc);
		params.put("spzid", spzid);
		try {
			Spz spz = spzService.findOneByParams(params);
			if (spz != null) {
				result.put("success", false);
				result.put("msg", "商品组名称已存在");
				return result;
			}
			spz = spzService.findOne(spzid);
			spz.setZbz(zbz);
			spz.setSpzmc(spzmc);
			spz.setXgry(getYhid());
			spz.setXgsj(new Date());
			spzService.save(spz);
			saveSpzsp(spz.getId(), sps, 1, 1);
			saveSpzxf(spz.getId(), xfids, 1, 1);
			result.put("success", true);
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "新增商品组异常：" + e);
		}
		return result;
	}

	@RequestMapping(value = "/getSpzsp")
	@ResponseBody
	public Map getSpzsp(Integer spzid) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<>();
		params.put("gsdm", getGsdm());
		params.put("spzid", spzid);
		List<SpzSp> list = sss.findAllByParams(params);
		result.put("sps", list);
		List<SpzXf> list1 = sxs.findAllByParams(params);
		result.put("xfs", list1);
		return result;
	}

	@RequestMapping(value = "/getSpzxf")
	@ResponseBody
	public Map getSpzxf(Integer spzid) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<>();
		params.put("gsdm", getGsdm());
		params.put("spzid", spzid);
		List<SpzXf> list = sxs.findAllByParams(params);
		result.put("xfs", list);
		return result;
	}

	@RequestMapping(value = "/deleteSpz")
	@ResponseBody
	@SystemControllerLog(description = "删除商品组",key = "ids")  
	public Map deleteSpz(String ids) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<Spz> list = new ArrayList<>();
			String[] strs = ids.split(",");
			for (String str : strs) {
				Spz spz = spzService.findOne(Integer.valueOf(str));
				spz.setYxbz("0");
				list.add(spz);
			}
 			spzService.save(list);
			Map<String, Object> params = new HashMap<>();
 			for (Spz spz : list) {
 				params.put("gsdm", getGsdm());
 				params.put("spzid", spz.getId());
 				List<SpzSp> oldList = sss.findAllByParams(params);
 				for (SpzSp spzSp : oldList) {
 					spzSp.setYxbz("0");
 				}
 				sss.save(oldList);
 				List<SpzXf> oldList1 = sxs.findAllByParams(params);
 				for (SpzXf spzXf : oldList1) {
 					spzXf.setYxbz("0");
 				}
 				sxs.save(oldList1);
			}
//			saveSpzsp(spzid, null, 1, 0);
//			saveSpzxf(spzid, null, 1, 0);
			result.put("success", true);
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "删除出错：" + e);
		}
		return result;
	}

	public void saveSpzsp(Integer spzid, String[] sps, int tag, int flag) {
		List<SpzSp> list = new ArrayList<>();
		SpzSp ss;
		if (tag == 1) {
			Map<String, Object> params = new HashMap<>();
			params.put("gsdm", getGsdm());
			params.put("spzid", spzid);
			List<SpzSp> oldList = sss.findAllByParams(params);
			for (SpzSp spzSp : oldList) {
				spzSp.setYxbz("0");
			}
			sss.save(oldList);
			
		}
		if (flag == 1) {
			for (String s : sps) {
				ss = new SpzSp();
				ss.setGsdm(getGsdm());
				ss.setSpdm(s);
				ss.setSpzid(spzid);
				ss.setLrry(getYhid());
				ss.setLrsj(new Date());
				ss.setXgry(getYhid());
				ss.setXgsj(new Date());
				ss.setYxbz("1");
				list.add(ss);
			}
			sss.save(list);
		}
	}

	public void saveSpzxf(Integer spzid, String[] xfids, int tag, int flag) {
		List<SpzXf> list = new ArrayList<>();
		SpzXf sx;
		if (tag == 1) {
			Map<String, Object> params = new HashMap<>();
			params.put("gsdm", getGsdm());
			params.put("spzid", spzid);
			List<SpzXf> oldList = sxs.findAllByParams(params);
			for (SpzXf spzXf : oldList) {
				spzXf.setYxbz("0");
			}
			sxs.save(oldList);
		}
		if (flag == 1) {
			for (String x : xfids) {
				sx = new SpzXf();
				sx.setGsdm(getGsdm());
				sx.setSpzid(spzid);
				sx.setXfid(Integer.valueOf(x));
				sx.setYxbz("1");
				sx.setLrry(getYhid());
				sx.setLrsj(new Date());
				sx.setXgry(getYhid());
				sx.setXgsj(new Date());
				list.add(sx);
			}
			sxs.save(list);
		}
	}

	@RequestMapping(value = "/downloadDefaultImportTemplate")
	@ResponseBody
	public void downTemplate() throws Exception {
		InputStream inputStream = this.getClass().getClassLoader()
				.getResourceAsStream("/template/importSpTemplate.xls");
		// 1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=importSpTemplate.xls");
		ServletOutputStream out = response.getOutputStream();
		IOUtils.copy(inputStream, out);
		inputStream.close();
		out.flush();
		out.close();
	}

	@RequestMapping(value = "/importExcel", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "导入商品",key = "importFile")  
	public Map importExcel(MultipartFile importFile) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
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
			String msg = processExcelList(resultList);
			if (!"".equals(msg)) {
				result.put("success", false);
				result.put("message", msg);
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			String msg = "" + e.toString();
			if (msg.contains("OutIndex") && msg.contains("Index")) {
				result.put("message", "表格中有数据没有填写");
			} else {
				result.put("message", e);
			}
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
	private String processExcelList(List<List> dataList) throws Exception {
		int yhid = this.getYhid();
		DrPz params = new DrPz();
		params.setYhid(yhid);
		Map<String, DrPz> pzMap = new HashMap<>();
		Map<String, String> enCnExcelColumnMap = new HashMap<>();
		enCnExcelColumnMap.putAll(importColumnMapping);
		// 转换成key为中文，value为英文的map
		Map<String, String> cnEnExcelColumnMap = new HashMap<>();
		for (Map.Entry<String, String> entry : enCnExcelColumnMap.entrySet()) {
			cnEnExcelColumnMap.put(entry.getValue(), entry.getKey());
		}
		// 找到excel中有效的字段
		List titleList = dataList.get(0);
		// 字段的顺序
		int columnIndex = 0;
		Map<String, Integer> columnIndexMap = new HashMap<>();
		String gsdm = this.getGsdm();
		for (Object obj : titleList) {
			String columnName = (String) obj;
			if (cnEnExcelColumnMap.containsKey(columnName)) {
				columnIndexMap.put(columnName, columnIndex);
			}
			columnIndex++;
		}
		// 获取所有的销方
		List<Spbm> spbmList = spbmService.findAllByParams(new HashMap<>());
		List<Sm> smList = smService.findAll();
		Sp s = new Sp();
		s.setGsdm(getGsdm());
		List<Sp> spList = spService.findAllByParams(s);
		// 数据的校验
		String msgg = "";
		String msg = "";
		List<Sp> list = new ArrayList<>();

		List<String> jylshList = new ArrayList<>();
		for (int i = 1; i < dataList.size(); i++) {
			List row = dataList.get(i);
			Sp sp = new Sp();
			sp.setGsdm(gsdm);
			sp.setLrry(yhid);
			sp.setXgry(yhid);
			sp.setLrsj(TimeUtil.getNowDate());
			sp.setXgsj(TimeUtil.getNowDate());
			sp.setYxbz("1");
			try {
				sp.setSpdm(row.get(0) == null ? null : row.get(0).toString());
			} catch (Exception e) {
				sp.setSpdm(null);
			}
			try {
				sp.setSpmc(row.get(1) == null ? null : row.get(1).toString());
			} catch (Exception e) {
				sp.setSpmc(null);
			}
			try {
				for (Sm sm : smList) {
					if (row.get(2) != null && String.valueOf(sm.getSl()).contains(row.get(2).toString())) {
						sp.setSmid(sm.getId());
						break;
					} else if (row.get(2) != null && !String.valueOf(sm.getSl()).contains(row.get(2).toString())) {
						sp.setSmid(0);
					}
				}
			} catch (Exception e) {
				sp.setSmid(0);
			}

			try {
				sp.setSpggxh(row.get(3) == null ? null : row.get(3).toString());
			} catch (Exception e) {
				sp.setSpggxh(null);
			}
			try {
				sp.setSpdw(row.get(4) == null ? null : row.get(4).toString());
			} catch (Exception e) {
				sp.setSpdw(null);
			}
			try {
				sp.setSpdj(row.get(5) == null ? null : Double.valueOf(row.get(5).toString()));
			} catch (Exception e) {
				sp.setSpdj(null);
			}

			try {
				sp.setSpbm(row.get(6) == null ? null : row.get(6).toString());
			} catch (Exception e) {

			}

			list.add(sp);
		}
		String reg = "^[0-9]{0,16}+(.[0-9]{0,6})?$";
		for (int i = 0; i < list.size(); i++) {
			Sp sp = list.get(i);
			String spdm = sp.getSpdm();
			if (spdm == null || "".equals(spdm)) {
				msgg = "第" + (i + 2) + "行商品代码不能为空，请重新填写！";
				msg += msgg;
			} else if (spdm.length() > 20) {
				msgg = "第" + (i + 2) + "行商品代码超过20个字符，请重新填写！";
				msg += msgg;
			}
			String spmc = sp.getSpmc();
			if (spmc == null || "".equals(spmc)) {
				msgg = "第" + (i + 2) + "行商品名称不能为空，请重新填写！";
				msg += msgg;
			} else if (spmc.length() > 50) {
				msgg = "第" + (i + 2) + "行商品名称超过50个字符，请重新填写！";
				msg += msgg;
			}
			Integer slid = sp.getSmid();
			if (slid == null || "".equals(slid)) {
				msgg = "第" + (i + 2) + "行商品税率不能为空，请重新填写！";
				msg += msgg;
			} else if (slid == 0) {
				msgg = "第" + (i + 2) + "行商品税率不存在，请重新填写！";
				msg += msgg;
			}
			String ggxh = sp.getSpggxh();
			if (ggxh != null && ggxh.length() > 18) {
				msgg = "第" + (i + 2) + "行规格型号超过18个字符，请重新填写！";
				msg += msgg;
			}
			String spdw = sp.getSpdw();
			if (spdw != null && spdw.length() > 10) {
				msgg = "第" + (i + 2) + "行商品单位超过10个字符，请重新填写！";
				msg += msgg;
			}
			Double spdj = sp.getSpdj();
			if (spdj != null && String.valueOf(spdj).matches(reg)) {
				msgg = "第" + (i + 2) + "行商品单价格式不正确，请重新填写！";
				msg += msgg;
			}

			// 判断商品代码是否存在
			for (int j = 0; j < spList.size(); j++) {
				Sp sk = spList.get(j);
				if (sk.getSpdm().equals(sp.getSpdm())) {
					msgg = "第" + (i + 2) + "行商品代码已存在，请重新填写！";
					msg += msgg;
				}
			}
			boolean flag = false;
			String spbm = sp.getSpbm();
			for (Spbm bm : spbmList) {
				if (bm.getSpbm().equals(spbm)) {
					flag = true;
				}
			}
			if (flag) {
				msgg = "第" + (i + 2) + "行商品和服务税收分类编码不存在，请重新填写！";
				msg += msgg;
			}
			// 判断excel税控盘号是否有重复
			for (int j = 0; j < list.size(); j++) {
				Sp sk = list.get(j);
				if (sk.getSpdm() != null && sk.getSpmc() != null && sk.getSpdm().equals(sp.getSpdm())
						&& sk.getSpmc().equals(sp.getSpmc()) && i != j) {
					msgg = "第" + (i + 2) + "行商品代码和第" + (j + 2) + "行相同，请重新填写！";
					msg += msgg;
				}
			}
		}

		// 没有异常，保存
		if ("".equals(msg)) {
			spService.save(list);
		}
		return msg;
	}

}
