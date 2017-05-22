package com.rjxx.taxeasy.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.domains.DrPz;
import com.rjxx.taxeasy.domains.Drmb;
import com.rjxx.taxeasy.domains.Skp;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.domains.XfMb;
import com.rjxx.taxeasy.service.DrPzService;
import com.rjxx.taxeasy.service.DrmbService;
import com.rjxx.taxeasy.service.SpvoService;
import com.rjxx.taxeasy.service.XfMbService;
import com.rjxx.taxeasy.service.XfService;
import com.rjxx.taxeasy.vo.DrmbVo;
import com.rjxx.taxeasy.vo.Spvo;
import com.rjxx.taxeasy.web.BaseController;

@Controller
@RequestMapping("/mbsz")
public class MbszController extends BaseController {

	@Autowired
	private XfService xfService;

	@Autowired
	private DrmbService drmbService;

	@Autowired
	private SpvoService spvoService;

	@Autowired
	private DrPzService drPzService;

	@Autowired
	XfMbService xfmbService;

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
		List<Spvo> spList = spvoService.findAllByGsdm(gsdm);
		List<Xf> xfList = this.getXfList();
		if (!spList.isEmpty()) {
			request.setAttribute("sp", spList.get(0));
		}
		if (xfList.size() == 1) {
			Map<String, Object> map = new HashMap<>();
			map.put("xfsh", xfList.get(0).getXfsh());
			map.put("xfs", getXfList());
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
		return "mbsz/index";
	}

	/**
	 * 查询模板
	 * 
	 * @param xfsh
	 * @return
	 */
	@RequestMapping(value = "/getItem")
	@ResponseBody
	public Map getItems(String xfsh, String mbmc,String gxbz, int length, int start, int draw) {
		Map<String, Object> result = new HashMap<>();
		Pagination pagination = new Pagination();
		pagination.setPageNo(start / length + 1);
		pagination.setPageSize(length);
		List<Xf> xfs = getXfList();
		if (xfs != null && xfs.size() > 0) {
			pagination.addParam("xfs", xfs);
		}
		pagination.addParam("xfsh", xfsh);
		pagination.addParam("mbmc", mbmc);
		pagination.addParam("gxbz", gxbz);
		pagination.addParam("gsdm", getGsdm());
		List<DrmbVo> list = drmbService.findByPage(pagination);
		/*if (!list.isEmpty()) {
			for (DrmbVo drmbVo : list) {
				for (Xf xf : xfs) {
					if (drmbVo.getXfsh().equals(xf.getXfsh())) {
						drmbVo.setXfmc(xf.getXfmc());
					}
				}
			}
		}*/
		int total = pagination.getTotalRecord();
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		result.put("draw", draw);
		result.put("data", list);
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
	 * 保存导入配置
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveImportConfig", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public Map saveImportConfig(Integer mbid, String mbmc, String config_gs_radio)
			throws Exception {
		Map<String, Object> data = new HashMap<>();
		int yhid = this.getYhid();
		Map<String, String[]> requestMap = request.getParameterMap();
		Iterator<String> iterator = requestMap.keySet().iterator();
		List<DrPz> drPzList = new ArrayList<>();
		Drmb mb = null;
		Drmb drmb = new Drmb();
		drmb.setMbmc(mbmc);
		//drmb.setXfsh(config_xfsh);
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
					/*if (xfMb.getXfsh().equals(config_xfsh)) {
						delList.add(xfMb);
					} else {*/
						xfMb.setYxbz("0");
						xfMb.setXgry(getYhid());
						xfMb.setXgsj(new Date());
					//}
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

		//drmb.setXfsh(config_xfsh);
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
				if(zdm.equals("fpzldm")||zdm.equals("hsbz")){ //发票种类代码另外处理
                      if(request.getParameter(key).equals("auto")){
						  drPz.setPzz(request.getParameter("config_" + zdm));
					  }else{  //当配置类型为config时，取input框的值
						  drPz.setPzz(request.getParameter("config_" + zdm+"_input"));
					  }
				}else{
					drPz.setPzz(request.getParameter("config_" + zdm));
				}
				drPz.setYhid(yhid);
				drPz.setMbid(drmb.getId());
				drPzList.add(drPz);
			}
		}
		drPzService.deleteAndSave(drmb.getId(), drPzList);
		data.put("success", true);
		data.put("drmb", drmb);
		data.put("message","保存成功！");
		return data;
	}

	/**
	 * 保存导入配置
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveConfig", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public Map saveConfig(Integer mbid1, String mbmc1, String config_xfsh, String config_gs_radio) throws Exception {
		Map<String, Object> data = new HashMap<>();
		int yhid = this.getYhid();
		Map<String, String[]> requestMap = request.getParameterMap();
		Iterator<String> iterator = requestMap.keySet().iterator();
		List<DrPz> drPzList = new ArrayList<>();
		Drmb mb = null;
		Drmb drmb = new Drmb();
		drmb.setMbmc(mbmc1);
		drmb.setXfsh(config_xfsh);
		if (mbid1 != null) {
			mb = drmbService.findOne(mbid1);
			if (mb != null && !mb.getYhid().equals(yhid)) {
				data.put("error", true);
				data.put("message", "你没有权限修改此模板！");
				return data;
			}
			drmb.setId(mbid1);
			drmb.setXgry(getYhid());
			drmb.setXgsj(new Date());
			if (config_gs_radio.equals("0")) {
				Map<String, Object> map = new HashMap<>();
				map.put("mbid", mbid1);
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
			result.put("msg","删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", "删除模板出错：" + e);
		}

		return result;
	}

	/**
	 * 删除模板
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/doDel", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public Map doDel(String mbidArr) throws Exception {
		List mbidList = convertToList(mbidArr);
		Map result = new HashMap();
		String tmp ="";
		for(int i=0;i<mbidList.size();i++){
			Integer mbid =(Integer) mbidList.get(i);
			Drmb drmb = drmbService.findOne(mbid);
			if (!drmb.getYhid().equals(getYhid())) {
				tmp =tmp +" "+drmb.getMbmc();
			}
		}
		if(tmp.equals("")){
			for(int i=0;i<mbidList.size();i++){
				Integer mbid =(Integer) mbidList.get(i);
				result = deleteMb(mbid);
			}
		}else{
			result.put("success", false);
			result.put("msg", tmp+"没有权限删除！");
		}
		return result;
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
	 * 获取商品详情
	 *
	 * @param  spid  spmc
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSpxq")
	@ResponseBody
	public Spvo getSpxq(String spid, String spmc) throws Exception {
		Spvo params = new Spvo();
		params.setGsdm(this.getGsdm());
		params.setId(Integer.parseInt(spid));
		params.setSpmc(spmc);
		List<Spvo> list = spvoService.findAllByParams(params);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

}
