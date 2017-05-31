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
import com.rjxx.taxeasy.domains.Skp;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.filter.SystemControllerLog;
import com.rjxx.taxeasy.service.DmFpbcService;
import com.rjxx.taxeasy.service.GroupService;
import com.rjxx.taxeasy.service.GsxxService;
import com.rjxx.taxeasy.service.SkpService;
import com.rjxx.taxeasy.service.XfService;
import com.rjxx.taxeasy.vo.Spbm;
import com.rjxx.taxeasy.vo.XfVo;
import com.rjxx.taxeasy.web.BaseController;
import com.rjxx.time.TimeUtil;
import com.rjxx.utils.ExcelUtil;

@Controller
@RequestMapping("/xfxxwh")
public class XfxxwhController extends BaseController {

	@Autowired
	private XfService xfService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private GsxxService gs;

	@Autowired
	private DmFpbcService dfs;

	@Autowired
	private SkpService ss;

	/**
	 * 导入字段映射
	 */
	private static Map<String, String> importColumnMapping = new HashMap<String, String>() {
		{
			put("xfsh", "销方税号");
			put("xfmc", "销方名称");
			put("xfdz", "销方地址");
			put("xfdh", "销方电话");
			put("xfyh", "销方银行");
			put("xfyhzh", "销方银行账号");
			put("lxr", "联系人");
			put("xfyb", "邮编");
			put("kpr", "开票人");
			put("skr", "收款人");
			put("fhr", "复核人");
			put("zfr", "作废人");
			put("dzpzdje", "电子票开票限额");
			put("dzpfpje", "电子票分票金额");
			put("zpzdje", "专票开票限额");
			put("zpfpje", "专票分票金额");
			put("ppzdje", "普票开票限额");
			put("ppfpje", "普票分票金额");
		}
	};

	@RequestMapping
	public String index() {
		request.setAttribute("xfs", getXfList());
		request.setAttribute("bc", dfs.findAllByParams(null));
		return "xfxxwh/index";
	}

	/**
	 * 分页查询销方信息
	 * 
	 * @param xfmc
	 * @param xfsh
	 * @param length
	 * @param start
	 * @param draw
	 * @return
	 */
	@RequestMapping(value = "/getXfxx")
	@ResponseBody
	public Map getXfxx(String xfmc, String xfsh, String kpr, String sjxfmc, String sjgj, int length, int start, int draw) {
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
		pagination.addParam("xfid", xfid);
		pagination.addParam("skpid", skpid);
		pagination.addParam("xfmc", xfmc);
		pagination.addParam("xfsh", xfsh);
		pagination.addParam("sjgj", sjgj);
		pagination.addParam("kpr", kpr);
		pagination.addParam("sjxfmc", sjxfmc);
		pagination.addParam("gsdm", this.getGsdm());
		pagination.addParam("orderBy", "lrsj");
		List<XfVo> list = xfService.findByPages(pagination);
		int total = pagination.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		result.put("draw", draw);
		result.put("data", list);
		return result;
	}

	/**
	 * 新增
	 * 
	 * @param xfsh
	 * @param xfmc
	 * @param dz
	 * @param xfdh
	 * @param xflxr
	 * @param xfyb
	 * @param khyh
	 * @param yhzh
	 * @param kpr
	 * @param skr
	 * @param fhr
	 * @param zfr
	 * @param kpzdje
	 * @param sqyhs
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	@SystemControllerLog(description = "新增销方",key = "xfsh")  
	public Map save(String sjxf, String xfsh, String xfmc, String dz, String xfdh, String xflxr, String xfyb,
			String khyh, String yhzh, String kpr, String skr, String fhr, String zfr, Double dzpzdje, Double dzpfpje,
			Double zpzdje, Double zpfpje, Double ppzdje, Double ppfpje) {

		Map<String, Object> result = new HashMap<String, Object>();

		try {

			Xf xf = new Xf();
			xf.setXfsh(xfsh);
			xf.setGsdm(getGsdm());
			Map<String, Object> params = new HashMap<>();
			params.put("gsdm", getGsdm());
			int sum = xfService.findAllByMap(params).size();
			Gsxx gsxx = gs.findOneByParams(params);
			if (gsxx.getXfnum() != null && sum >= gsxx.getXfnum()) {
				result.put("success", true);
				result.put("msg", "销方数量已达到销方数量设置最大值，不能添加，如需增加请联系平台开发商");
				return result;
			}
			if (xfService.findOneByParams(xf) == null) {
				xf.setXfmc(xfmc);
				xf.setSjjgbm("0".equals(sjxf) ? null : sjxf);
				xf.setXfdh(xfdh);
				xf.setXfdz(dz);
				xf.setXfyh(khyh);
				xf.setXfyhzh(yhzh);
				xf.setYxbz("1");
				xf.setFhr(fhr);
				xf.setGsdm(getGsdm());
				xf.setKpr(kpr);
				xf.setSkr(skr);
				xf.setLrry(getYhid());
				xf.setXgry(getYhid());
				xf.setLrsj(new Date());
				xf.setXgsj(new Date());
				xf.setXflxr(xflxr);
				xf.setXfyb(xfyb);
				xf.setZfr(zfr);
				xf.setDzpzdje(dzpzdje);
				xf.setDzpfpje(dzpfpje);
				xf.setZpzdje(zpzdje);
				xf.setZpfpje(zpfpje);
				xf.setPpzdje(ppzdje);
				xf.setPpfpje(ppfpje);
				xfService.update(xf);
				xfService.saveNew(xf);
				getXfList().add(xf);
				// TODO: 2016/10/27 保存两遍
				// Group g = new Group();
				// g.setXfid(xf.getId());
				// g.setYhid(getYhid());
				// groupService.save(g);
				result.put("success", true);
				result.put("msg", "保存成功");
			} else {
				result.put("repeat", true);
				result.put("msg", "销方税号已存在，请重新输入");
			}

		} catch (Exception e) {
			e.printStackTrace();
			result.put("failure", true);
			result.put("msg", "保存出现错误: " + e);
		}
		return result;
	}

	/**
	 * 修改
	 * 
	 * @param xfid
	 * @param xfsh
	 * @param xfmc
	 * @param dz
	 * @param xfdh
	 * @param xflxr
	 * @param xfyb
	 * @param khyh
	 * @param yhzh
	 * @param kpr
	 * @param skr
	 * @param fhr
	 * @param zfr
	 * @param kpzdje
	 * @param sqyhs
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "修改销方",key = "xfid")  
	public Map update(String sjxf, Integer xfid, String xfsh, String xfmc, String dz, String xfdh, String xflxr,
			String xfyb, String khyh, String yhzh, String kpr, String skr, String fhr, String zfr, Double dzpzdje,
			Double dzpfpje, Double zpzdje, Double zpfpje, Double ppzdje, Double ppfpje) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (!"0".equals(sjxf) && !"".equals(sjxf) && sjxf != null) {
				Map<String, Object> node = new HashMap<>();
				node.put("sjxf", xfid);
				List<Map<String, Object>> doing = new ArrayList<Map<String, Object>>();
				doing.add(node);
				List<Xf> xfs = getXfList();
				List<Xf> xfs1 = new ArrayList<>();
				while (!doing.isEmpty()) {
					List<Map<String, Object>> todo = new ArrayList<Map<String, Object>>();
					for (Map<String, Object> item : doing) {
						List<Xf> xfs2 = new ArrayList<Xf>();
						for (int i = 0; i < xfs.size(); i++) {
							if ((xfs.get(i).getSjjgbm() == null ? "" : xfs.get(i).getSjjgbm())
									.equals(String.valueOf(item.get("sjxf")))) {
								xfs2.add(xfs.get(i));
								xfs1.add(xfs.get(i));
							}
						}
						if (xfs2.isEmpty())
							continue;
						List<Object> children = new ArrayList<Object>();
						for (Xf spbm : xfs2) {
							Map<String, Object> node1 = new HashMap<String, Object>();
							node1.put("sjxf", spbm.getId());
							children.add(node1);
							todo.add(node1);
						}
						item.put("children", children.toArray(new Object[children.size()]));
					}
					doing = todo;
				}
				for (Xf xf : xfs1) {
					if (xf.getId().equals(Integer.valueOf(sjxf))) {
						result.put("failure", true);
						result.put("msg", "上级销方在【" + xfmc + "】下级存在");
						return result;
					}
				}
			}
			Xf xf = new Xf();
			xf.setXfsh(xfsh);
			xf.setId(xfid);
			xf.setGsdm(getGsdm());
			if (xfService.findOneByParams(xf) == null || xfid.equals(xfService.findOneByParams(xf).getId())) {
				xf.setXfmc(xfmc);
				xf.setSjjgbm("0".equals(sjxf) ? null : sjxf);
				xf.setXfdh(xfdh);
				xf.setXfdz(dz);
				xf.setXfyh(khyh);
				xf.setXfyhzh(yhzh);
				xf.setYxbz("1");
				xf.setFhr(fhr);
				xf.setGsdm(getGsdm());
				xf.setKpr(kpr);
				xf.setSkr(skr);
				xf.setLrry(getYhid());
				xf.setLrsj(xfService.findOneByParams(xf) == null ? TimeUtil.getNowDate()
						: xfService.findOneByParams(xf).getLrsj());
				xf.setXgry(getYhid());
				xf.setXgsj(new Date());
				xf.setXflxr(xflxr);
				xf.setXfyb(xfyb);
				xf.setZfr(zfr);
				xf.setDzpzdje(dzpzdje);
				xf.setDzpfpje(dzpfpje);
				xf.setZpzdje(zpzdje);
				xf.setZpfpje(zpfpje);
				xf.setPpzdje(ppzdje);
				xf.setPpfpje(ppfpje);
				xfService.update(xf);
				List<Xf> xfs3 = new ArrayList<>();
				for (Xf xf2 : getXfList()) {
					if (xf2.equals(xfid)) {
						xfs3.add(xf2);
						getXfList().remove(xf2);
					}
				}
				getXfList().removeAll(xfs3);
				getXfList().add(xf);
				result.put("success", true);
				result.put("msg", "保存成功");
			} else {
				result.put("repeat", true);
				result.put("msg", "销方税号已存在，请重新输入");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("failure", true);
			result.put("msg", "保存出现错误: " + e);
		}
		return result;
	}

	@RequestMapping(value = "/updateJe", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "修改金额",key = "xfid")  
	public Map updateJe(int xfid, Double kpxe1, Double fpje1, Double kpxe2, Double fpje2, Double kpxe3, Double fpje3) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {

			Xf xf = xfService.findOne(xfid);
			xf.setDzpzdje(kpxe3);
			xf.setDzpfpje(fpje3);
			xf.setPpzdje(kpxe1);
			xf.setPpfpje(fpje1);
			xf.setZpfpje(fpje2);
			xf.setZpzdje(kpxe2);
			xf.setXgry(getYhid());
			xf.setXgsj(new Date());
			xfService.update(xf);
			Xf x = null;
			for (Xf x1 : getXfList()) {
				if (xf.getId().equals(x1.getId())) {
					x = x1;
				}
			}
			getXfList().remove(x);
			getXfList().add(0, xf);
			result.put("xf", xf);
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
			Xf xf = xfService.findOne(id);
			result.put("xf", xf);
			result.put("success", true);
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "查询异常，请稍后再试");
		}

		return result;
	}

	@RequestMapping(value = "/destroy", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	@SystemControllerLog(description = "删除销方",key = "ids")  
	public Map destroy(String ids) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<Xf> xfs = new ArrayList<>();
			List<Skp> skplist = new ArrayList<>();
			String[] strs = ids.split(",");
			List<Skp> skpList = new ArrayList<>();
			Skp s = new Skp();
			for (String str : strs) {
				Xf xf = xfService.findOne(Integer.valueOf(str));
				xf.setYxbz("0");
				xfService.delete(xf);
				getXfList().remove(xf);
				Group g = new Group();
				g.setXfid(xf.getId());
				s.setXfid(Integer.valueOf(str));
				List<Skp> skps = ss.findAllByParams(s);
				skpList.addAll(skps);
				List<Group> list = groupService.findAllByParams(g);
				groupService.save(list);
				List<Xf> xflist = getXfList();
				for (int i = 0; i < xflist.size(); i++) {
					if (xflist.get(i).getId().equals(xf.getId())) {
						xfs.add(getXfList().get(i));
					}
				}
				for (Skp skp : skplist) {
					for (Xf x : xfs) {
						if (skp.getXfid().equals(x.getId())) {
							skplist.add(skp);
						}
					}
				}
			}
			for (Skp skp : skpList) {
				skp.setYxbz("0");
			}
			ss.save(skpList);
			getXfList().removeAll(xfs);
			getSkpList().removeAll(skplist);
			result.put("success", true);
			result.put("msg", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("failure", true);
			result.put("msg", "删除出现错误: " + e);
		}
		return result;
	}

	@RequestMapping(value = "/downloadDefaultImportTemplate")
	@ResponseBody
	public void downTemplate() throws Exception {
		InputStream inputStream = this.getClass().getClassLoader()
				.getResourceAsStream("/template/importXfTemplate.xls");
		// 1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=importXfTemplate.xls");
		ServletOutputStream out = response.getOutputStream();
		IOUtils.copy(inputStream, out);
		inputStream.close();
		out.flush();
		out.close();
	}

	@RequestMapping(value = "/importExcel", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "导入销方",key = "importFile")  
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
		Xf xx = new Xf();
		xx.setGsdm(getGsdm());
		List<Xf> xfList = xfService.findAllByParams(xx);
		List<String> slList = new ArrayList<String>();
		// 数据的校验
		String msgg = "";
		String msg = "";
		List<Xf> list = new ArrayList<>();

		List<String> jylshList = new ArrayList<>();
		for (int i = 1; i < dataList.size(); i++) {
			List row = dataList.get(i);
			Xf xf = new Xf();
			xf.setGsdm(gsdm);
			xf.setLrry(yhid);
			xf.setXgry(yhid);
			xf.setLrsj(TimeUtil.getNowDate());
			xf.setXgsj(TimeUtil.getNowDate());
			xf.setYxbz("1");
			try {
				xf.setXfsh(row.get(0) == null ? null : row.get(0).toString());
			} catch (Exception e) {
				xf.setXfsh(null);
			}
			try {
				xf.setXfmc(row.get(1) == null ? null : row.get(1).toString());
			} catch (Exception e) {
				xf.setXfmc(null);
			}
			try {
				xf.setXfdz(row.get(2) == null ? null : row.get(2).toString());
			} catch (Exception e) {
				xf.setXfdz(null);
			}
			try {
				xf.setXfdh(row.get(3) == null ? null : row.get(3).toString());
			} catch (Exception e) {
				xf.setXfdh(null);
			}
			try {
				xf.setXfyh(row.get(4) == null ? null : row.get(4).toString());
			} catch (Exception e) {
				xf.setXfyh(null);
			}
			try {
				xf.setXfyhzh(row.get(5) == null ? null : row.get(5).toString());
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				xf.setXflxr(row.get(6) == null ? null : row.get(6).toString());
			} catch (Exception e) {
				xf.setXflxr(null);
			}
			try {
				xf.setXfyb(row.get(7) == null ? null : row.get(7).toString());
			} catch (Exception e) {
				xf.setXfyb(null);
			}
			try {
				xf.setSkr(row.get(8) == null ? null : row.get(8).toString());
			} catch (Exception e) {
				xf.setSkr(null);
			}
			try {
				xf.setFhr(row.get(9) == null ? null : row.get(9).toString());
			} catch (Exception e) {
				xf.setFhr(null);
			}
			try {
				xf.setZfr(row.get(10) == null ? null : row.get(10).toString());
			} catch (Exception e) {
				xf.setZfr(null);
			}
			try {
				xf.setKpr(row.get(11) == null ? null : row.get(11).toString());
			} catch (Exception e) {
				xf.setKpr(null);
			}
			try {
				xf.setKpr(row.get(11) == null ? null : row.get(11).toString());
			} catch (Exception e) {
				xf.setKpr(null);
			}

			try {
				xf.setDzpzdje(row.get(12) == null ? null : Double.valueOf(row.get(12).toString()));
			} catch (Exception e) {
				xf.setDzpzdje(null);
			}
			try {
				xf.setDzpfpje(row.get(13) == null ? null : Double.valueOf(row.get(13).toString()));
			} catch (Exception e) {
				xf.setDzpfpje(null);
			}

			try {
				xf.setZpzdje(row.get(14) == null ? null : Double.valueOf(row.get(14).toString()));
			} catch (Exception e) {
				xf.setZpzdje(null);
			}
			try {
				xf.setZpfpje(row.get(15) == null ? null : Double.valueOf(row.get(15).toString()));
			} catch (Exception e) {
				xf.setZpfpje(null);
			}

			try {
				xf.setPpzdje(row.get(16) == null ? null : Double.valueOf(row.get(16).toString()));
			} catch (Exception e) {
				xf.setPpzdje(null);
			}
			try {
				xf.setPpfpje(row.get(17) == null ? null : Double.valueOf(row.get(17).toString()));
			} catch (Exception e) {
				xf.setPpfpje(null);
			}
			list.add(xf);
		}
		for (int i = 0; i < list.size(); i++) {
			Xf xf = list.get(i);
			String xfsh = xf.getXfsh();
			if (xfsh == null || "".equals(xfsh)) {
				msgg = "第" + (i + 2) + "行销方税号不能为空，请重新填写！";
				msg += msgg;
			} else if (xfsh.length() != 15 && xfsh.length() != 18) {
				msgg = "第" + (i + 2) + "行销方税号不是15位或18位，请重新填写！";
				msg += msgg;
			}
			String xfmc = xf.getXfmc();
			if (xfmc == null || "".equals(xfmc)) {
				msgg = "第" + (i + 2) + "行销方名称不能为空，请重新填写！";
				msg += msgg;
			} else if (xfmc.length() > 50) {
				msgg = "第" + (i + 2) + "行销方名称超过50个字符，请重新填写！";
				msg += msgg;
			}
			String xfdz = xf.getXfdz();
			if (xfdz == null || "".equals(xfdz)) {
				msgg = "第" + (i + 2) + "行销方地址不能为空，请重新填写！";
				msg += msgg;
			} else if (xfdz.length() > 100) {
				msgg = "第" + (i + 2) + "行销方地址超过100个字符，请重新填写！";
				msg += msgg;
			}
			String xfdh = xf.getXfdh();
			if (xfdh == null || "".equals(xfdh)) {
				msgg = "第" + (i + 2) + "行销方电话不能为空，请重新填写！";
				msg += msgg;
			} else if (xfdh.length() > 25) {
				msgg = "第" + (i + 2) + "行销方电话超过25个字符，请重新填写！";
				msg += msgg;
			}
			String xfyh = xf.getXfyh();
			if (xfyh == null || "".equals(xfyh)) {
				msgg = "第" + (i + 2) + "行销方银行不能为空，请重新填写！";
				msg += msgg;
			} else if (xfyh.length() > 100) {
				msgg = "第" + (i + 2) + "行销方银行超过100个字符，请重新填写！";
				msg += msgg;
			}
			String xfyhzh = xf.getXfyhzh();
			if (xfyhzh == null || "".equals(xfyhzh)) {
				msgg = "第" + (i + 2) + "行销方银行账号不能为空，请重新填写！";
				msg += msgg;
			} else if (xfyhzh.length() > 30) {
				msgg = "第" + (i + 2) + "行销方银行账号超过30个字符，请重新填写！";
				msg += msgg;
			}
			String kpr = xf.getKpr();
			if (kpr == null || "".equals(kpr)) {
				msgg = "第" + (i + 2) + "行销方银行账号不能为空，请重新填写！";
				msg += msgg;
			} else if (kpr.length() > 50) {
				msgg = "第" + (i + 2) + "行开票人超过50个字符，请重新填写！";
				msg += msgg;
			}
			Double dzpzdje = xf.getDzpzdje();
			Double dzpfpje = xf.getDzpfpje();
			if (dzpzdje == null) {
				msgg = "第" + (i + 2) + "行电子票开票限额为空或者格式不正确，请重新填写！";
				msg += msgg;
			}
			if (dzpfpje == null) {
				msgg = "第" + (i + 2) + "行电子票分票金额为空或者格式不正确，请重新填写！";
				msg += msgg;
			}
			if (dzpzdje != null && dzpfpje != null) {
				if (dzpzdje < dzpfpje) {
					msgg = "第" + (i + 2) + "行电子票分票金额不能大于电子票开票限额，请重新填写！";
					msg += msgg;
				}
			}
			Double zpzdje = xf.getZpzdje();
			Double zpfpje = xf.getZpfpje();
			if (zpzdje == null) {
				msgg = "第" + (i + 2) + "行专票开票限额为空或者格式不正确，请重新填写！";
				msg += msgg;
			}
			if (zpfpje == null) {
				msgg = "第" + (i + 2) + "行专票分票金额为空或者格式不正确，请重新填写！";
				msg += msgg;
			}
			if (zpzdje != null && zpfpje != null) {
				if (zpzdje < zpfpje) {
					msgg = "第" + (i + 2) + "行专票分票金额不能大于专票开票限额，请重新填写！";
					msg += msgg;
				}
			}
			Double ppzdje = xf.getPpzdje();
			Double ppfpje = xf.getPpfpje();
			if (ppzdje == null) {
				msgg = "第" + (i + 2) + "行普票开票限额为空或者格式不正确，请重新填写！";
				msg += msgg;
			}
			if (ppfpje == null) {
				msgg = "第" + (i + 2) + "行普票分票金额为空或者格式不正确，请重新填写！";
				msg += msgg;
			}
			if (ppzdje != null && ppfpje != null) {
				if (ppzdje < ppfpje) {
					msgg = "第" + (i + 2) + "行普票分票金额不能大于普票开票限额，请重新填写！";
					msg += msgg;
				}
			}
			// 判断税号是否存在
			for (int j = 0; j < xfList.size(); j++) {
				Xf x = xfList.get(j);
				if (x.getXfsh().equals(xf.getXfsh())) {
					msgg = "第" + (i + 2) + "行销方税号已存在，请重新填写！";
					msg += msgg;
				}
			}
			// 判断excel税号是否有重复
			for (int j = 0; j < list.size(); j++) {
				Xf x = list.get(j);
				if (x.getXfsh() != null && x.getXfsh().equals(xf.getXfsh()) && i != j) {
					msgg = "第" + (i + 2) + "行销方税号和第" + (j + 2) + "行相同，请重新填写！";
					msg += msgg;
				}
			}
		}

		// 没有异常，保存
		if ("".equals(msg)) {
			Map<String, Object> prms = new HashMap<>();
			prms.put("gsdm", getGsdm());
			int sum = xfService.findAllByMap(prms).size();
			Gsxx gsxx = gs.findOneByParams(prms);
			if (gsxx.getXfnum() - sum < list.size()) {
				msg = "导入销方数量(" + list.size() + ")已超过可增加销方数量(" + (gsxx.getXfnum() - sum) + ")，不能导入，如需增加请联系平台开发商";
				return msg;
			}
			xfService.save(list);
			getXfList().addAll(list);
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
