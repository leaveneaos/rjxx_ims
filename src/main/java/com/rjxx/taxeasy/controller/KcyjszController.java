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

	@Autowired
	private FpzlService fpzlService;
	@Autowired
	private FpkcYzszService fpkcYzszService;

	@Autowired
	private CszbService cszbService;

	@Autowired
	private YhService yhService;

	@Autowired
	private FpkcYztzService fpkcYztzService;

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
            List<Skp> skpList = getSkpList();
            List<Skp> showSkpList = null;
			List<Fpzl> fpzlList = fpzlService.findAllByParams(new HashMap<>());
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
                showSkpList = skpList;
			}else{
                for (int j = 0; j < skpList.size(); j++) {
                    int skpid2 = skpList.get(j).getId();
                    if(skpid2 ==skpid){
                        showSkpList.add(skpList.get(j));
                    }
                }
            }
			pagination.addParam("skpid", skpid);
			pagination.addParam("fpzldm", fpzldm);
			List<FpkcYzszVo> kcyjList = fpkcYzszService.findByPage(pagination);
            List<FpkcYzszVo> kcyjList2 = new ArrayList<>();
            for(int m=0;m<showSkpList.size();m++){
                Skp skptmp = showSkpList.get(m);
                Map params  = new HashMap();
                params.put("skpid",skptmp.getId());
                KpfsVo kpfsVo = cszbService.findKpfsBySkpid(params);
                if(null!=skptmp.getKplx()){
                    String kplx[] = skptmp.getKplx().split(",");
                    for(int t =0;t<kplx.length;t++){
                        FpkcYzszVo FpkcYzszVoTmp = new FpkcYzszVo();
                        FpkcYzszVoTmp.setGsdm(skptmp.getGsdm());
                        FpkcYzszVoTmp.setXfid(skptmp.getXfid());
                        FpkcYzszVoTmp.setSkpid(skptmp.getId());
                        FpkcYzszVoTmp.setKpfs(kpfsVo.getKpfs());
                        FpkcYzszVoTmp.setKpdmc(skptmp.getKpdmc());
                        FpkcYzszVoTmp.setXfmc(kpfsVo.getXfmc());
                        FpkcYzszVoTmp.setXfsh(kpfsVo.getXfsh());
						FpkcYzszVoTmp.setSkph(skptmp.getSkph());
                        //String fplzmc = kplx.equals("12")?
						for(int aa=0;aa<fpzlList.size();aa++){
							if(kplx[t].equals(fpzlList.get(aa).getFpzldm()))
								FpkcYzszVoTmp.setFpzlmc(fpzlList.get(aa).getFpzlmc());
						}
						FpkcYzszVoTmp.setFpzldm(kplx[t]);
                        FpkcYzszVoTmp.setYhid(getYhid());
                        FpkcYzszVoTmp.setYxbz("1");
                        FpkcYzszVoTmp.setFpzldm(kplx[t]);
                        kcyjList2.add(FpkcYzszVoTmp);
                    }
                }

            }
            for(int q=0;q<kcyjList2.size();q++){
                FpkcYzszVo fpkcYzszVo2 = kcyjList2.get(q);
                for(int w=0;w<kcyjList.size();w++){
                    FpkcYzszVo fpkcYzszVo3 = kcyjList.get(w);
                    if(fpkcYzszVo2.getGsdm().equals(fpkcYzszVo3.getGsdm()) && fpkcYzszVo2.getXfid().equals(fpkcYzszVo3.getXfid())&&
                            fpkcYzszVo2.getSkpid().equals(fpkcYzszVo3.getSkpid())&&fpkcYzszVo2.getFpzldm().equals(fpkcYzszVo3.getFpzldm())){
                        fpkcYzszVo2.setYjyz(fpkcYzszVo3.getYjyz());
                    }
                }
            }
			//int total = pagination.getTotalRecord();
            int total = kcyjList2.size();
			result.put("recordsTotal", total);
			result.put("recordsFiltered", total);
			result.put("draw", draw);
			result.put("data", kcyjList2);
		}else {
			result.put("recordsTotal", 0);
			result.put("recordsFiltered", 0);
			result.put("draw", draw);
			result.put("data", new ArrayList<>());
		}
		return result;
	}


	private String getFpzlmc(String kplx){
		if(kplx.equals("12")){
			return "026";
		}else if(kplx.equals("01")){
			return "004";
		}else if(kplx.equals("02")){
			return "007";
		}
		return "000";
	}



	@RequestMapping(value = "/getItems1")
	@ResponseBody
	public Map<String, Object> getItems1(int length, int start, int draw, Integer xfid,Integer skpid,String fpzldm,String xfsh,boolean loaddata) throws Exception {
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
	public Map<String, Object> addtzfs(String yjids) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		result.put("msg", "保存成功！");
		String gsdm = getGsdm();
		try {
			String[] yjszids = yjids.split(",");
			String[] tzfsids = request.getParameterValues("tzfsid");
			String[] yhids = request.getParameterValues("yhid");
			//List<FpkcYztz> items = fpkcYztzService.findAllByParams(params);
			for(int i=0;i<yjszids.length;i++){
				Integer yjszid = Integer.valueOf(yjszids[i]);
				for(int j=0;j<yhids.length;j++){
					Map params = new HashMap<>();
					params.put("gsdm", gsdm);
					params.put("yjszid",yjszid);
					params.put("tzyhid",Integer.valueOf(yhids[j]));
					FpkcYztz item  = fpkcYztzService.findOneByParams(params);
					if(item == null){
						FpkcYztz fpkcYztz = new FpkcYztz();
						fpkcYztz.setGsdm(gsdm);
						fpkcYztz.setYjszid(yjszid);
						if(tzfsids.length>1){
							fpkcYztz.setTzfs("01");
						}else{
							fpkcYztz.setTzfs(tzfsids[0]);
						}
						fpkcYztz.setTzyhid(Integer.valueOf(yhids[j]));
						fpkcYztz.setLrry(getYhid());
						fpkcYztz.setLrsj(new Date());
						fpkcYztz.setXgry(getYhid());
						fpkcYztz.setXgsj(new Date());
						fpkcYztz.setYxbz("1");
						fpkcYztzService.save(fpkcYztz);
					}else{
						if(tzfsids.length>1){
							item.setTzfs("01");
						}else{
							item.setTzfs(tzfsids[0]);
						}
						item.setXgsj(new Date());
						item.setXgry(getYhid());
						item.setYxbz("1");
						fpkcYztzService.save(item);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
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
