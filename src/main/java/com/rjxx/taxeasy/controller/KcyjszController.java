package com.rjxx.taxeasy.controller;

import java.util.*;

import com.rjxx.taxeasy.domains.*;
import com.rjxx.taxeasy.service.*;
import com.rjxx.taxeasy.vo.FpkcYzszVo;
import com.rjxx.taxeasy.vo.KpfsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.vo.Fpkcvo;
import com.rjxx.taxeasy.vo.Fpyjdyvo;
import com.rjxx.taxeasy.web.BaseController;

@Controller
@RequestMapping("/kcyjsz")
public class KcyjszController extends BaseController {

	/*@Autowired
	private FpyjdyService dyService;*/
	@Autowired
	private FpkcService kcService;
	@Autowired
	private FpzlService fpzlService;
	@Autowired
	private FpkcYzszService fpkcYzszService;

	@Autowired
	private CszbService cszbService;

	@Autowired
	private YhService yhService;

	@RequestMapping
	public String index() throws Exception {
		List<Xf> xfList = getXfList();
		request.setAttribute("xfList", xfList);
		List<Skp> skpList = getSkpList();
		request.setAttribute("skpList", skpList);
		List<Fpzl> fpzlList = fpzlService.findAllByParams(new HashMap<>());
		request.setAttribute("fplxList",fpzlList);
		Map map = new HashMap();
		map.put("yhid",getYhid());
		map.put("gsdm",getGsdm());
		List<Yh> yhList = yhService.findAllByParams(map);
		request.setAttribute("yhList",yhList);
		return "kcyjsz/index";
	}

	@RequestMapping(value = "/getItems")
	@ResponseBody
	public Map<String, Object> getItems(int length, int start, int draw, Integer xfid,Integer skpid,String fpzldm,String xfsh,boolean loaddata) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		//int yhid = getYhid();
		if(loaddata){
			Pagination pagination = new Pagination();
			pagination.setPageNo(start / length + 1);
			pagination.setPageSize(length);
			String gsdm = getGsdm();
			String xfidstr = "";
			if(null ==xfid && (null ==xfsh || xfsh.equals(""))){
				List<Xf> xflist = getXfList();
				for(int i= 0;i<xflist.size();i++){
					if(xfsh.equals(xflist.get(i).getXfsh())){
						pagination.addParam("xfid", xflist.get(i).getId());
					}
					if (i == xflist.size() - 1) {
						xfidstr += xflist.get(i).getId() + "";
					} else {
						xfidstr += xflist.get(i).getId() + ",";
					}
				}
				String[] xfids = xfidstr.split(",");
				if (xfids.length == 0) {
					xfids = null;
				}
				pagination.addParam("xfids", xfids);
			}else{
				if(null!= xfid){
					pagination.addParam("xfid", xfid);
				}else{
					List<Xf> xflist = getXfList();
					for(int i= 0;i<xflist.size();i++){
						if(xfsh.equals(xflist.get(i).getXfsh())) {
							pagination.addParam("xfid", xflist.get(i).getId());
							break;
						}
					}
				}
			}
			pagination.addParam("gsdm", gsdm);
			if(null==skpid || skpid.equals("")){
				String skpStr = "";
				List<Skp> skpList = getSkpList();
				if (skpList != null) {
					for (int j = 0; j < skpList.size(); j++) {
						int skpid2 = skpList.get(j).getId();
						if (j == skpList.size() - 1) {
							skpStr += skpid2 + "";
						} else {
							skpStr += skpid2 + ",";
						}
					}
				}
				String[] skpids = skpStr.split(",");
				if (skpids.length == 0) {
					skpids = null;
				}
				pagination.addParam("skpids", skpids);
			}
			pagination.addParam("skpid", skpid);
			pagination.addParam("fpzldm", fpzldm);
			List<FpkcYzszVo> kcyjList = fpkcYzszService.findByPage(pagination);
		/*for (FpkcYzsz item : kcyjList) {
			Map params = new HashMap<>();
			params.put("gsdm", gsdm);
			params.put("xfid", item.getXfid());
			params.put("skpid", item.getSkpid());
			params.put("fpzldm",item.getFpzldm());
			params.put("yhid", yhid);
			Fpyjdyvo dybz = dyService.findDyxx(params);
			if(dybz!=null){
				item.setYjkcl(dybz.getYjkcl());
			}
		}*/
			int total = pagination.getTotalRecord();
			result.put("recordsTotal", total);
			result.put("recordsFiltered", total);
			result.put("draw", draw);
			result.put("data", kcyjList);
		}else {
			result.put("recordsTotal", 0);
			result.put("recordsFiltered", 0);
			result.put("draw", draw);
			result.put("data", new ArrayList<>());
		}
		return result;
	}


	@RequestMapping(value = "/addyjz", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveYjz(Integer xfid, Integer skpid,String fpzldm,Integer yjkcl,String kpfs) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		result.put("msg", "保存成功！");
		int yhid = getYhid();
		String gsdm = getGsdm();
		Map params = new HashMap<>();
		params.put("yhid", yhid);
		params.put("gsdm", gsdm);
		params.put("xfid", xfid);
		params.put("skpid", skpid);
		params.put("fpzldm", fpzldm);
		try {
			FpkcYzsz item = fpkcYzszService.findOneByParams(params);
			if (item == null) {
				FpkcYzsz yjdy = new FpkcYzsz();
				yjdy.setYhid(yhid);
				yjdy.setGsdm(gsdm);
				yjdy.setXfid(xfid);
				yjdy.setSkpid(skpid);
				yjdy.setFpzldm(fpzldm);
				yjdy.setYjyz(yjkcl);
				yjdy.setYxbz("1");
				fpkcYzszService.save(yjdy);
			} else {
				item.setYjyz(yjkcl);
				fpkcYzszService.save(item);
			}
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "保存失败！");
		}

		return result;
	}


	@RequestMapping(value = "/addtzfs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addtzfs() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		result.put("msg", "保存成功！");
		int yhid = getYhid();
		String gsdm = getGsdm();
		Map params = new HashMap<>();
		params.put("yhid", yhid);
		params.put("gsdm", gsdm);
		/*params.put("xfid", xfid);
		params.put("skpid", skpid);
		params.put("fpzldm", fpzldm);*/
		try {
			FpkcYzsz item = fpkcYzszService.findOneByParams(params);
			if (item == null) {
				FpkcYzsz yjdy = new FpkcYzsz();
				yjdy.setYhid(yhid);
				yjdy.setGsdm(gsdm);
				/*yjdy.setXfid(xfid);
				yjdy.setSkpid(skpid);
				yjdy.setFpzldm(fpzldm);
				yjdy.setYjyz(yjkcl);*/
				yjdy.setYxbz("1");
				fpkcYzszService.save(yjdy);
			} else {
				/*item.setYjyz(yjkcl);*/
				fpkcYzszService.save(item);
			}
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "保存失败！");
		}

		return result;
	}


	@RequestMapping(value = "/update")
	@ResponseBody
	public Map<String, Object> update(Integer xfid, Integer skpid,String fpzldm,Integer yjkcl,Integer xg_id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		result.put("msg", "保存成功！");
		try {
			FpkcYzsz item = fpkcYzszService.findOne(xg_id);
			item.setYjyz(yjkcl);
			fpkcYzszService.save(item);

		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "保存失败！");
		}

		return result;
	}

	@RequestMapping(value = "/plupdate")
	@ResponseBody
	public Map<String, Object> plupdate(String skpids,String fpzldms,Integer yjkcl) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		result.put("msg", "保存成功！");
		int yhid = getYhid();
		String gsdm = getGsdm();
		Map params = new HashMap<>();
		String[] skpid = skpids.substring(0, skpids.length() - 1).split(",");
		for (int i = 0; i < skpid.length; i++) {
			String[] sf = skpid[i].split("-");
			params.put("yhid", yhid);
			params.put("gsdm", gsdm);
			params.put("xfid", sf[0]);
			params.put("skpid", sf[1]);
			params.put("fpzldm", sf[2]);
			try {
				FpkcYzsz item = fpkcYzszService.findOneByParams(params);
				if (item == null) {
					FpkcYzsz yjdy = new FpkcYzsz();
					yjdy.setYhid(yhid);
					yjdy.setGsdm(gsdm);
					yjdy.setXfid(Integer.parseInt(sf[0]));
					yjdy.setSkpid(Integer.parseInt(sf[1]));
					yjdy.setFpzldm(sf[2]);
					yjdy.setYjyz(yjkcl);
					yjdy.setYxbz("1");
					fpkcYzszService.save(yjdy);
				} else {
					item.setYjyz(yjkcl);
					fpkcYzszService.save(item);
				}
			} catch (Exception e) {
				result.put("success", false);
				result.put("msg", "保存失败！");
				break;
			}
		}
		return result;
	}


	/**
	 * 获取销方下的有操作权限的开票点
	 *
	 * @param xfid
	 * @return
	 */
	@RequestMapping(value = "/getSkpList")
	@ResponseBody
	public Map getSkpList(Integer xfid) {
		Map<String, Object> result = new HashMap<>();
		//Integer xfid = null;
		List<Skp> list = new ArrayList<>();
		for (Skp skp : getSkpList()) {
			if (xfid != null && skp.getXfid().equals(xfid)) {
				list.add(skp);
			}
		}
		result.put("skps", list);
		return result;
	}

	/**
	 * 获取销方下的有操作权限的发票种类
	 *
	 * @param skpid
	 * @return
	 */
	@RequestMapping(value = "/getFpzlList")
	@ResponseBody
	public Map getFpzlList(Integer skpid) {
		Map<String, Object> result = new HashMap<>();
		//Integer xfid = null;
		List<Fpzl> fpzlList = fpzlService.findAllByParams(new HashMap<>());
		List<Fpzl> list = new ArrayList<>();
		Skp skp = null;
		for (Skp skp2 : getSkpList()) {
			if (skpid != null && skp2.getId().equals(skpid)) {
				skp = skp2;
				break;
			}
		}
		if(skp !=null){
			Map param = new HashMap();
			param.put("gsdm",skp.getGsdm());
			param.put("xfid",skp.getXfid());
			param.put("kpdid",skp.getId());
			KpfsVo kpfsVo = cszbService.findKpfsBySkpid(param);
			String fpzl[] = skp.getKplx().split(",");
			for (int i=0;i<fpzl.length;i++){
				String fpzldm = fpzl[i];
				for(int j=0;j<fpzlList.size();j++){
					if(fpzldm.equals(fpzlList.get(j).getFpzldm())){
						list.add(fpzlList.get(j));
					}
				}
			}
			result.put("kpfs",kpfsVo.getCsz());
			result.put("show_kpfs",kpfsVo.getKpfs());
		}
		result.put("fpzls", list);

		return result;
	}



	// 主页订阅查询
	/*@RequestMapping(value = "/zydy")
	@ResponseBody
	public Map<String, Object> zydy(int length, int start, int draw) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Pagination pagination = new Pagination();
		pagination.setPageNo(start / length + 1);
		pagination.setPageSize(length);
		int yhid = getYhid();
		pagination.addParam("yhid", yhid);
		List<Fpyjdyvo> zyList = dyService.findYhZyDy(pagination);
		for (Fpyjdyvo item : zyList) {
			Map params = new HashMap<>();
			params.put("gsdm", item.getGsdm());
			params.put("xfid", item.getXfid());
			params.put("skpid", item.getSkpid());
			Fpkcvo kc = kcService.findZyKyl(params);
			if (kc != null) {
				item.setKyl(kc.getKyl());
			}
		}
		int total = pagination.getTotalRecord();
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		result.put("draw", draw);
		result.put("data", zyList);
		return result;
	}*/
}