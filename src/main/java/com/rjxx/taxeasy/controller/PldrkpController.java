package com.rjxx.taxeasy.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.bizcomm.utils.FpclService;
import com.rjxx.taxeasy.domains.Cszb;
import com.rjxx.taxeasy.domains.Drmb;
import com.rjxx.taxeasy.domains.Jyxxsq;
import com.rjxx.taxeasy.domains.Pldrjl;
import com.rjxx.taxeasy.domains.Sm;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.filter.SystemControllerLog;
import com.rjxx.taxeasy.service.CszbService;
import com.rjxx.taxeasy.service.DrmbService;
import com.rjxx.taxeasy.service.JyxxsqService;
import com.rjxx.taxeasy.service.PldrjlService;
import com.rjxx.taxeasy.service.SmService;
import com.rjxx.taxeasy.service.SpvoService;
import com.rjxx.taxeasy.service.SpzService;
import com.rjxx.taxeasy.vo.Spvo;
import com.rjxx.taxeasy.web.BaseController;

@Controller
@RequestMapping("/pldrkp")
public class PldrkpController extends BaseController {

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
	private PldrjlService pldrjlService;
	@Autowired
	private FpclService fpclService;
	@Autowired
	private JyxxsqService JyxxsqService;

	@RequestMapping
	@SystemControllerLog(description = "批量导入页面初始化", key = "")
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
		request.setAttribute("xfSum", xfList.size());

		return "pldrkp/index";
	}

	@ResponseBody
	@RequestMapping("/getItems")
	public Map<String, Object> getItems(int length, int start, int draw, String jylsh, String jyrqq, String jyrqz,
			String xfsh, boolean loaddata) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		if (loaddata) {
			Pagination pagination = new Pagination();
			pagination.setPageNo(start / length + 1);
			pagination.setPageSize(length);
			String gsdm = getGsdm();
			String xfid = "";
			List<Xf> xfs = getXfList();
			if (xfs != null) {
				for (int i = 0; i < xfs.size(); i++) {
					String xfsh2 = xfs.get(i).getXfsh();
					if (xfsh2.equals("xfsh")) {
						xfid = String.valueOf(xfs.get(i).getId());
						break;
					}
				}
			}

			/*
			 * String skpStr = ""; List<Skp> skpList = getSkpList(); if (skpList
			 * != null) { for (int j = 0; j < skpList.size(); j++) { int skpid =
			 * skpList.get(j).getId(); if (j == skpList.size() - 1) { skpStr +=
			 * skpid + ""; } else { skpStr += skpid + ","; } } } String[] skpid
			 * = skpStr.split(","); if (skpid.length == 0) { skpid = null; }
			 */
			pagination.addParam("jylsh", jylsh);
			pagination.addParam("xfs", xfs);
			pagination.addParam("xfid", xfid);
			pagination.addParam("jyrqq", jyrqq);
			pagination.addParam("jyrqz", jyrqz);
			pagination.addParam("gsdm", gsdm);
			pagination.addParam("orderBy", "xh desc");
			List<Pldrjl> pldrjlList = pldrjlService.findByPage(pagination);

			int total = pagination.getTotalRecord();
			result.put("recordsTotal", total);
			result.put("recordsFiltered", total);
			result.put("draw", draw);
			result.put("data", pldrjlList);
		} else {
			int total = 0;
			result.put("recordsTotal", total);
			result.put("recordsFiltered", total);
			result.put("draw", draw);
			result.put("data", new ArrayList<>());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/plkjcl")
	public Map<String, Object> plkjcl(String jylsh, String xfid, String lrsj, String xh) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		String gsdm = this.getGsdm();
		params.put("xh", xh);
		params.put("jylsh", jylsh);
		params.put("lrsj", lrsj);
		params.put("xfid", xfid);
		params.put("gsdm", gsdm);
		Pldrjl pljl = pldrjlService.findOneByParams(params);
		pljl.setZtbz("1");
		pldrjlService.save(pljl);
		List<Jyxxsq> jyxxsqList = pldrjlService.findAllJyxxsqByParams(params);
		try {
			fpclService.zjkp(jyxxsqList, "01");
			result.put("success", true);
			result.put("msg", "数据已处理，请关注发票开具结果");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("success", true);
			result.put("msg", "数据已处理，请关注发票开具结果");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/plkjsh")
	public Map<String, Object> plkjsh(String jylsh, String xfid, String lrsj, String xh) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		String gsdm = this.getGsdm();
		params.put("xh", xh);
		params.put("jylsh", jylsh);
		params.put("lrsj", lrsj);
		params.put("xfid", xfid);
		params.put("gsdm", gsdm);

		try {
			Pldrjl pljl = pldrjlService.findOneByParams(params);
			pldrjlService.delete(pljl);

			List<Jyxxsq> jyxxsqList = pldrjlService.findAllJyxxsqByParams(params);
			List<Integer> sqlshList = new ArrayList<Integer>();
			if (null != jyxxsqList && !jyxxsqList.isEmpty()) {
				for (int i = 0; i < jyxxsqList.size(); i++) {
					Jyxxsq jyxxsq = jyxxsqList.get(i);
					sqlshList.add(jyxxsq.getSqlsh());
				}
			}
			JyxxsqService.delBySqlshList2(sqlshList);
			result.put("success", true);
			result.put("msg", "删除成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", "删除失败");
		}
		return result;
	}

}
