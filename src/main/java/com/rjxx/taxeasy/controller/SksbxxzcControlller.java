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
import com.rjxx.taxeasy.domains.Group;
import com.rjxx.taxeasy.domains.Gsxx;
import com.rjxx.taxeasy.domains.Pp;
import com.rjxx.taxeasy.domains.Skp;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.filter.SystemControllerLog;
import com.rjxx.taxeasy.service.DmFpbcService;
import com.rjxx.taxeasy.service.FpzlService;
import com.rjxx.taxeasy.service.GroupService;
import com.rjxx.taxeasy.service.GsxxService;
import com.rjxx.taxeasy.service.PpService;
import com.rjxx.taxeasy.service.SkpService;
import com.rjxx.taxeasy.service.XfService;
import com.rjxx.taxeasy.vo.SkpVo;
import com.rjxx.taxeasy.web.BaseController;
import com.rjxx.time.TimeUtil;
import com.rjxx.utils.ExcelUtil;

@Controller
@RequestMapping("/sksbxxzc")
public class SksbxxzcControlller extends BaseController {

	@Autowired
	private XfService xfService;

	@Autowired
	private SkpService skpService;

	@Autowired
	GroupService groupService;

	@Autowired
	PpService ps;

	@Autowired
	GsxxService gs;

	@Autowired
	FpzlService fs;

	@Autowired
	private DmFpbcService dfs;

	/**
	 * 导入字段映射
	 */
	private static Map<String, String> importColumnMapping = new HashMap<String, String>() {
		{
			put("kpddm", "开票点代码");
			put("kpdmc", "开票点名称");
			// put("skpmm", "税控盘密码");
			// put("zsmm", "证书密码");
			// put("zcm", "注册码");
			put("xfsh", "销方税号");
			/*
			 * put("dpmax", "电子发票开票限额"); put("fpfz", "电子发票分票金额"); put("ppmax",
			 * "普通发票开票限额"); put("ppfz", "普通发票分票金额"); put("zpmax", "专用发票开票限额");
			 * put("zpfz", "专用发票分票金额");
			 */
		}
	};

	@RequestMapping
	public String index() {
		request.setAttribute("xfs", getXfList());
		request.setAttribute("xf", getXfList().get(0));
		Map<String, Object> prms = new HashMap<>();
		prms.put("gsdm", getGsdm());
		List<Pp> ppList = ps.findAllByParams(prms);
		request.setAttribute("pps", ppList);
		request.setAttribute("fpzls", fs.findAllByParams(null));
		request.setAttribute("bc", dfs.findAllByParams(null));
		return "sksbxxzc/index";
	}

	/**
	 * 分页查询税控设备信息
	 * 
	 * @param length
	 * @param start
	 * @param draw
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getsksblist")
	@ResponseBody
	public Map getsksblist(int length, int start, int draw, String kpdmc, String kpddm, Integer xfid1, String txt,
			String xfmc, String kpr, String kplx, String sbcs) throws Exception {
		Pagination pagination = new Pagination();
		pagination.setPageNo(start / length + 1);
		pagination.setPageSize(length);
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
		String[] xfi = xfStr.split(",");
		if (xfi.length == 0) {
			xfi = null;
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
		pagination.addParam("xfid", xfid1);
		pagination.addParam("kpdmc", kpdmc);
		pagination.addParam("kpddm", kpddm);
		pagination.addParam("kpr", kpr);
		pagination.addParam("xfmc", xfmc);
		pagination.addParam("sbcs", sbcs);
		pagination.addParam("kplx", kplx);
		pagination.addParam("xfi", xfi);
		pagination.addParam("skpid", skpid);
		pagination.addParam("gsdm", getGsdm());
		pagination.addParam("orderBy", "lrsj");
		pagination.addParam("xfs", getXfList());
		List<SkpVo> list = skpService.findByPage(pagination);
		int total = pagination.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		result.put("draw", draw);
		result.put("data", list);
		return result;
	}

	@RequestMapping(value = "/getXf")
	@ResponseBody
	public Map getXf(Integer xfid) {
		Map<String, Object> result = new HashMap<>();
		Xf x = null;
		for (Xf xf : getXfList()) {
			if (xf.getId().equals(xfid)) {
				x = xf;
			}
		}
		result.put("xf", x);
		return result;
	}

	/**
	 * 新增税控设备
	 * 
	 * @param skph
	 * @param xfid
	 * @param skpmm
	 * @param zsmm
	 * @param
	 * @param
	 * @param
	 * @param
	 * @param
	 * @param
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	@Transactional
	@SystemControllerLog(description = "新增开票点",key = "kpddm")  
	public Map save(int xfid, String kpddm, String kpdmc, String skph, String skpmm, String zsmm, String lxdz,
			String lxdh, String khyh, String yhzh, String skr, String fhr, String kpr, String sbcs, Integer pid,
			Integer bmbb, String fplx, String wrzs, Double kpxe1, Double fpje1, Double kpxe2, Double fpje2, Double kpxe3, Double fpje3) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> prms = new HashMap<>();
			prms.put("gsdm", getGsdm());

			Skp old = new Skp();
			old.setKpddm(kpddm);
			old.setKpdmc(kpdmc);
			old.setGsdm(getGsdm());
			if (pid != null && pid != 0) {
				old.setPid(pid);
			}
			if (fplx == null || "".equals(fplx)) {
				result.put("failure", true);
				result.put("msg", "请选择开票类型");
				return result;
			}
			int sum = skpService.findAllByParams(old).size();
			;
			Gsxx gsxx = gs.findOneByParams(prms);
			if (gsxx.getKpdnum() != null && gsxx.getKpdnum() - sum <= 0) {
				result.put("failure", true);
				result.put("msg", "税控盘数量已达到税控盘数量设置最大值，不能添加，如需增加请联系平台开发商");
				return result;
			}
			Map<String, Object> params = new HashMap<>();
			params.put("gsdm", getGsdm());
			params.put("kpddm", kpddm);
			Skp skp = skpService.findOneByParams(params);
			if (skp != null) {
				result.put("failure", true);
				result.put("msg", "此开票点已经存在！");
				return result;
			}
			old.setXfid(xfid);
			old.setKpddm(kpddm);
			old.setKpdmc(kpdmc);
			old.setSkph(skph);
			old.setSbcs(sbcs);
			old.setLxdz(lxdz);
			old.setSkpmm(skpmm);
			old.setZsmm(zsmm);
			old.setLxdh(lxdh);
			old.setKhyh(khyh);
			old.setYhzh(yhzh);
			old.setSkr(skr);
			old.setFhr(fhr);
			old.setKpr(kpr);
			old.setDpmax(kpxe3);
			old.setFpfz(fpje3);
			old.setZpmax(kpxe2);
			old.setZpfz(fpje2);
			old.setPpmax(kpxe1);
			old.setPpfz(fpje1);
			old.setLrry(getYhid());
			old.setLrsj(new Date());
			old.setXgry(getYhid());
			old.setXgsj(new Date());
			old.setYxbz("1");
			old.setKplx(fplx);
			if(wrzs!=null&&!"".equals(wrzs)&&wrzs.equals("1")){
				old.setWrzs(wrzs);
			}else{
				old.setWrzs("0");
			}
			skpService.save(old);
			Group group = new Group();
			group.setYhid(getYhid());
			group.setXfid(xfid);
			group.setYxbz("1");
			group.setSkpid(old.getId());
			group.setLrry(getYhid());
			group.setXgry(getYhid());
			group.setLrsj(new Date());
			group.setXgsj(new Date());
			groupService.save(group);
			getSkpList().add(old);
			result.put("success", true);
			result.put("msg", "添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("failure", true);
			result.put("msg", "添加出现错误: 服务异常" + e);
		}
		return result;
	}

	/**
	 * 修改税控设备
	 * 
	 * @param skph
	 * @param xfid
	 * @param skpmm
	 * @param zsmm
	 * @param
	 * @param
	 * @param
	 * @param
	 * @param
	 * @param
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	@SystemControllerLog(description = "修改开票点",key = "id")  
	public Map  update(int id, int xfid, String kpddm, String kpdmc, String skph, String skpmm, String zsmm, String lxdz,
			String lxdh, String khyh, String yhzh, String skr, String fhr, String kpr, String sbcs, Integer pid,
			Integer bmbb, String fplx,String wrzs, Double kpxe1, Double fpje1, Double kpxe2, Double fpje2, Double kpxe3, Double fpje3) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("kpddm", kpddm);
			params.put("gsdm", getGsdm());

			if (fplx == null || "".equals(fplx)) {
				result.put("failure", true);
				result.put("msg", "请选择开票类型");
				return result;
			}
			Skp skp = skpService.findOneByParams(params);

			if (skp != null && !kpddm.equals(skp.getKpddm()) && !kpdmc.equals(skp.getKpdmc())) {
				result.put("failure", true);
				result.put("msg", "此开票点已经存在");
				return result;
			}
			skp = skpService.findOne(id);
			skp.setKpdmc(kpdmc);
			skp.setKpddm(kpddm);
			skp.setGsdm(getGsdm());
			if (!pid.equals(0)) {
				skp.setPid(pid);
			}
			skp.setSkph(skph);
			skp.setSbcs(sbcs);
			skp.setSkpmm(skpmm);
			skp.setZsmm(zsmm);
			if(wrzs!=null&&!"".equals(wrzs)&&wrzs.equals("1")){
				skp.setWrzs(wrzs);
			}else{
				skp.setWrzs("0");
			}
			skp.setLxdz(lxdz);
			skp.setLxdh(lxdh);
			skp.setKhyh(khyh);
			skp.setYhzh(yhzh);
			skp.setSkr(skr);
			skp.setFhr(fhr);
			skp.setKpr(kpr);
			skp.setKplx(fplx);
			skp.setXfid(xfid);
			skp.setDpmax(kpxe3);
			skp.setFpfz(fpje3);
			skp.setZpmax(kpxe2);
			skp.setZpfz(fpje2);
			skp.setPpmax(kpxe1);
			skp.setPpfz(fpje1);
			skp.setLrry(skp.getLrry());
			skp.setLrsj(skp.getLrsj());
			skp.setXgry(getYhid());
			skp.setXgsj(new Date());
			skp.setYxbz("1");
			skpService.save(skp);
			result.put("success", true);
			result.put("msg", "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("failure", true);
			result.put("msg", "修改出现错误: 服务异常" + e);
		}
		return result;
	}

	@RequestMapping(value = "/updateJe")
	@ResponseBody
	@SystemControllerLog(description = "修改金额",key = "skpid")  
	public Map updateJe(int skpid, Double kpxe1, Double fpje1, Double kpxe2, Double fpje2, Double kpxe3, Double fpje3) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {

			Skp skp = skpService.findOne(skpid);
			if (kpxe3 == null) {
				skp.setDpmax(0.00);
				skp.setFpfz(0.00);
			} else {
				skp.setDpmax(kpxe3);
				skp.setFpfz(fpje3);
			}

			if (kpxe1 == null) {
				skp.setPpmax(0.00);
				skp.setPpfz(0.00);
			} else {
				skp.setPpmax(kpxe1);
				skp.setPpfz(fpje1);
			}
			if (kpxe2 == null) {
				skp.setZpmax(0.00);
				skp.setZpfz(0.00);
			} else {
				skp.setZpmax(kpxe2);
				skp.setZpfz(fpje2);
			}
			skp.setXgry(getYhid());
			skp.setXgsj(new Date());
			skpService.save(skp);
			List<Skp> skps = new ArrayList<>();
			for (Skp skp2 : getSkpList()) {
				if (skp2.getId().equals(skpid)) {
					skps.add(skp2);
					break;
				}
			}
			getSkpList().removeAll(skps);
			getSkpList().add(0, skp);
			result.put("success", true);
			result.put("msg", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("failure", true);
			result.put("msg", "修改出现错误: 服务异常" + e);
		}
		return result;
	}

	@RequestMapping(value = "/getJe")
	@ResponseBody
	public Map<String, Object> getSkp(Integer id) {
		Map<String, Object> result = new HashMap<>();
		try {
			Skp skp = skpService.findOne(id);
			result.put("skp", skp);
			result.put("success", true);
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "查询异常，请稍后再试");
		}

		return result;
	}

	@RequestMapping(value = "/del")
	@ResponseBody
	@Transactional
	@SystemControllerLog(description = "删除开票点",key = "ids")  
	public Map delete(int ids) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Skp skp = skpService.findOne(ids);
			skp.setYxbz("0");
			skpService.save(skp);
			Group g = new Group();
			g.setSkpid(ids);
			List<Group> list = groupService.findAllByParams(g);
			if (!list.isEmpty()) {
				for (Group group : list) {
					group.setYxbz("0");
				}
				groupService.save(list);
			}
			List<Skp> skps = new ArrayList<>();
			for (int i = 0; i < getSkpList().size(); i++) {
				if (skp.getId().equals(getSkpList().get(i).getId())) {
					skps.add(getSkpList().get(i));
				}
			}
			getSkpList().removeAll(skps);
			result.put("success", true);
			result.put("msg", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("failure", true);
			result.put("msg", "删除出现错误: " + e);
		}
		return result;
	}

	@RequestMapping(value = "/delele")
	@ResponseBody
	@Transactional
	@SystemControllerLog(description = "删除开票点",key = "ids")  
	public Map delete(String ids) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (!"".equals(ids)) {
			String[] idls = ids.split(",");
			for (int i = 0; i < idls.length; i++) {
				String id = idls[i].split(":")[0];
				try {
					Skp skp = skpService.findOne(Integer.valueOf(id));
					skp.setYxbz("0");
					skpService.save(skp);
					Group g = new Group();
					g.setSkpid(Integer.valueOf(id));
					List<Group> list = groupService.findAllByParams(g);
					if (!list.isEmpty()) {
						for (Group group : list) {
							group.setYxbz("0");
						}
						groupService.save(list);
					}
					List<Skp> skps = new ArrayList<>();
					for (int j = 0; j < getSkpList().size(); j++) {
						if (skp.getId().equals(getSkpList().get(j).getId())) {
							skps.add(getSkpList().get(j));
						}
					}
					getSkpList().removeAll(skps);
					result.put("success", true);
					result.put("msg", "删除成功");
				} catch (Exception e) {
					e.printStackTrace();
					result.put("failure", true);
					result.put("msg", "删除出现错误: " + e);
				}
			}
		}
		return result;
	}

	@RequestMapping(value = "/downloadDefaultImportTemplate")
	@ResponseBody
	public void downTemplate() throws Exception {
		InputStream inputStream = this.getClass().getClassLoader()
				.getResourceAsStream("/template/importSkpTemplate.xls");
		// 1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=importSkpTemplate.xls");
		ServletOutputStream out = response.getOutputStream();
		IOUtils.copy(inputStream, out);
		inputStream.close();
		out.flush();
		out.close();
	}

	@RequestMapping(value = "/importExcel", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "导入开票点",key = "importFile")  
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
			result.put("message", e);
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
		Xf xx = new Xf();
		xx.setGsdm(getGsdm());
		List<Xf> xfList = xfService.findAllByParams(xx);
		Skp s = new Skp();
		s.setGsdm(getGsdm());
		List<Skp> skpList = skpService.findAllByParams(s);
		Map<String, Object> prms = new HashMap<>();
		prms.put("gsdm", getGsdm());
		List<Pp> ppList = ps.findAllByParams(prms);
		// 数据的校验
		String msgg = "";
		String msg = "";
		List<Skp> list = new ArrayList<>();
		for (int i = 1; i < dataList.size(); i++) {
			List row = dataList.get(i);
			Skp skp = new Skp();
			skp.setGsdm(gsdm);
			skp.setLrry(yhid);
			skp.setXgry(yhid);
			skp.setLrsj(TimeUtil.getNowDate());
			skp.setXgsj(TimeUtil.getNowDate());
			skp.setYxbz("1");
			try {
				skp.setKpddm(row.get(0) == null ? null : row.get(0).toString());
			} catch (Exception e) {
				skp.setKpddm(null);
			}
			try {
				skp.setKpdmc(row.get(1) == null ? null : row.get(1).toString());
			} catch (Exception e) {
				skp.setKpdmc(null);
			}
			try {
				for (Xf xf : xfList) {
					if (row.get(2) != null && xf.getXfsh().equals(row.get(2).toString())) {
						skp.setXfid(xf.getId());
						break;
					} else if (row.get(2) != null && !xf.getXfsh().equals(row.get(2).toString())) {
						skp.setXfid(0);
					}
				}
			} catch (Exception e) {
				skp.setXfid(0);
			}
			try {
				for (Pp p : ppList) {
					if (row.get(3) != null && p.getPpmc().equals(row.get(3).toString())) {
						skp.setPid(p.getId());
						break;
					}
				}
			} catch (Exception e) {

			}
			try {
				skp.setSbcs(row.get(4) == null ? null : row.get(4).toString());
			} catch (Exception e) {

			}
			try {
				skp.setSkph(row.get(5) == null ? null : row.get(5).toString());
			} catch (Exception e) {

			}
			try {
				skp.setSkpmm(row.get(6) == null ? null : row.get(6).toString());
			} catch (Exception e) {

			}
			try {
				skp.setZsmm(row.get(7) == null ? null : row.get(7).toString());
			} catch (Exception e) {

			}
			try {
				skp.setLxdz(row.get(8) == null ? null : row.get(8).toString());
			} catch (Exception e) {

			}
			try {
				skp.setLxdh(row.get(9) == null ? null : row.get(9).toString());
			} catch (Exception e) {

			}
			try {
				skp.setKhyh(row.get(10) == null ? null : row.get(10).toString());
			} catch (Exception e) {

			}
			try {
				skp.setYhzh(row.get(11) == null ? null : row.get(11).toString());
			} catch (Exception e) {

			}
			try {
				skp.setSkr(row.get(12) == null ? null : row.get(12).toString());
			} catch (Exception e) {

			}
			try {
				skp.setFhr(row.get(13) == null ? null : row.get(13).toString());
			} catch (Exception e) {

			}
			try {
				skp.setKpr(row.get(14) == null ? null : row.get(14).toString());
			} catch (Exception e) {

			}
			try {
				skp.setKplx(row.get(15) == null ? null : row.get(15).toString());
			} catch (Exception e) {

			}
			list.add(skp);

		}
		for (int i = 0; i < list.size(); i++) {
			Skp skp = list.get(i);
			Integer xfid = skp.getXfid();
			if (xfid == null || "".equals(xfid)) {
				msgg = "第" + (i + 2) + "行销方税号不能为空，请重新填写！";
				msg += msgg;
			} else if (xfid == 0) {
				msgg = "第" + (i + 2) + "行销方税号不存在，请重新填写！";
				msg += msgg;
			}
			String kpddm = skp.getKpddm();
			if (kpddm == null || "".equals(kpddm)) {
				msgg = "第" + (i + 2) + "行开票点代码不能为空，请重新填写！";
				msg += msgg;
			} else if (kpddm.length() > 50) {
				msgg = "第" + (i + 2) + "行开票点超过50个字符，请重新填写！";
				msg += msgg;
			}
			String kpdmc = skp.getKpdmc();
			if (kpdmc == null || "".equals(kpdmc)) {
				msgg = "第" + (i + 2) + "行开票点名称不能为空，请重新填写！";
				msg += msgg;
			} else if (kpdmc.length() > 50) {
				msgg = "第" + (i + 2) + "行开票点名称超过50个字符，请重新填写！";
				msg += msgg;
			}
			String sksb = skp.getSbcs();
			if (sksb != null && (!sksb.equals("1") || !sksb.equals("2"))) {
				msgg = "第" + (i + 2) + "行税控设备填写不正确，请重新填写！";
				msg += msgg;
			}
			String kplx = skp.getKplx();
			if (kplx != null) {
				String[] strs = kplx.split(",");
				for (String str : strs) {
					if (!str.equals("01") || !str.equals("02") || !str.equals("12")) {
						msgg = "第" + (i + 2) + "行开票类型填写不正确（应为01,02,12三种，多种用英文“,”隔开），请重新填写！";
						msg += msgg;
					}
				}
			}
			// 判断税控盘号是否存在
			/*
			 * for (int j = 0; j < skpList.size(); j++) { Skp sk =
			 * skpList.get(j); if (sk.getSkph().equals(skp.getSkph())) { msgg =
			 * "第" + (i + 2) + "行税控盘号已存在，请重新填写！"; msg += msgg; } }
			 */
			// 判断excel税控盘号是否有重复
			/*
			 * for (int j = 0; j < list.size(); j++) { Skp sk = list.get(i); if
			 * (sk.getSkph() != null && sk.getSkph().equals(skp.getSkph()) && i
			 * != j) { msgg = "第" + (i + 2) + "行税控盘号和第" + (j + 2) +
			 * "行相同，请重新填写！"; msg += msgg; } }
			 */
		}

		// 没有异常，保存
		if ("".equals(msg)) {
			Map<String, Object> parms = new HashMap<>();
			parms.put("gsdm", getGsdm());
			Skp sk = new Skp();
			sk.setGsdm(getGsdm());
			int sum = skpService.findAllByParams(sk).size();
			Gsxx gsxx = gs.findOneByParams(parms);
			if (gsxx.getKpdnum() - sum < list.size()) {
				msg = "导入税控盘数量(" + list.size() + ")已超过可增加税控盘数量(" + (gsxx.getKpdnum() - sum) + ")，不能导入，如需增加请联系平台开发商";
				return msg;
			}
			skpService.save(list);
			getSkpList().addAll(list);
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
		if (drPz == null) {
			Integer index = columnIndexMap.get(enColumnName);
			if (index == null) {
				return null;
			}
			return row.get(index).toString();
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
			String cnColumnName = drPz.getPzz();
			Integer columnIndex = columnIndexMap.get(cnColumnName);
			if (columnIndex == null) {
				return null;
			}
			String value = row.get(columnIndex).toString();
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
