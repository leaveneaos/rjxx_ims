package com.rjxx.taxeasy.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.loader.collection.OneToManyJoinWalker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itextpdf.text.pdf.parser.clipper.Clipper.ZFillCallback;
import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.domains.Csb;
import com.rjxx.taxeasy.domains.Fpgz;
import com.rjxx.taxeasy.domains.Skp;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.domains.Yh;
import com.rjxx.taxeasy.service.FpgzService;
import com.rjxx.taxeasy.service.XfService;
import com.rjxx.taxeasy.service.YhService;
import com.rjxx.taxeasy.vo.CsbVo;
import com.rjxx.taxeasy.vo.FpgzVo;
import com.rjxx.taxeasy.web.BaseController;

@Controller
@RequestMapping("/fpgz")
public class FpgzController extends BaseController{

	@Autowired
	private FpgzService fpgzService;
	
	@Autowired
	private YhService yhService;
	
	@Autowired
	private XfService XfService;
	
	@RequestMapping
	public String index() {
		request.setAttribute("xfs", getXfList());
		request.setAttribute("skpList", getSkpList());
		return "fpgz/index";
	}
	//查询
	@RequestMapping(value = "/getList")
	@ResponseBody
	public Map getList(int length, int start, int draw) {
		Pagination pagination = new Pagination();
		pagination.setPageNo(start / length + 1);
		pagination.setPageSize(length);
		pagination.addParam("gsdm", this.getGsdm());
		pagination.addParam("orderBy", "a.lrsj");
		List<FpgzVo> list = fpgzService.findByPage(pagination);
		int total = pagination.getTotalRecord();
	/*	for (FpgzVo cszb : list) {
			Yh yh = yhService.findOne(cszb.getLrry());
			
		}*/
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		result.put("draw", draw);
		result.put("data", list);
		return result;
	}
	
	//新增
	@RequestMapping(value = "/xzgz")
	@ResponseBody
	public Map<String, Object> xzgz(Fpgz fpgz,String[] xfid){
		Map<String, Object> result = new HashMap<String, Object>();
		if (null==fpgz.getSfqzfp()) {
			fpgz.setSfqzfp("0");
		}
		if (null==fpgz.getSfspzsfp()) {//是否按商品整数分票
			fpgz.setSfspzsfp("0");
		}
		String xfs="";
		if (null==xfid||xfid.length==0) {
			
		}else{
			for (String id : xfid) {
				xfs+=id+",";
			}
			xfs=xfs.substring(0,xfs.length()-1);
		}
		if (fpgz!=null&&fpgz.getMrbz()!=null&&fpgz.getMrbz().equals("1")) {
			Map map = new HashMap<>();
			map.put("mrbz", "1");
			map.put("gsdm", getGsdm());
			List<Fpgz> listfp = fpgzService.findAllByParams(map);
			for (Fpgz fpgz3 : listfp) {
				fpgz3.setMrbz("0");
			}
			fpgzService.save(listfp);
		}
		fpgz.setXfids(xfs);
		fpgz.setYxbz("1");
		fpgz.setLrry(getYhid());
		fpgz.setXgry(getYhid());
		fpgz.setGsdm(getGsdm());
		fpgz.setLrsj(new Date());
		fpgz.setXgsj(new Date());
		fpgzService.save(fpgz);
		result.put("success", true);
		result.put("msg", "新增规则成功");
		return result;
	}
	//删除
	@RequestMapping(value = "/scgz")
	@ResponseBody
	public Map<String, Object> scgz(String id,String[] xfid){
		Map<String, Object> result = new HashMap<String, Object>();
		String[] gzid = id.split(",");
		for (String gzid1 : gzid) {
			Fpgz fpgz = fpgzService.findOne(Integer.valueOf(gzid1));
			fpgz.setYxbz("0");
			fpgzService.save(fpgz);
		}
		result.put("msg", "删除成功");
		return result;
	}
	
	//修改
	@RequestMapping(value = "/xggz")
	@ResponseBody
	public Map<String, Object> xgbcgz(Fpgz fpgz,String[] xfid,Integer idd){
		Map<String, Object> result = new HashMap<String, Object>();
		Fpgz fpgz2 = fpgzService.findOne(idd);
		String xfs="";
		if (null==xfid||xfid.length==0) {
			
		}else{
			for (String id : xfid) {
				xfs+=id+",";
			}
			xfs=xfs.substring(0,xfs.length()-1);
		}
		fpgz.setId(idd);
		if (fpgz!=null&&fpgz.getMrbz()!=null&&fpgz.getMrbz().equals("1")) {
			Map map = new HashMap<>();
			map.put("mrbz", "1");
			map.put("gsdm", getGsdm());
			List<Fpgz> listfp = fpgzService.findAllByParams(map);
			for (Fpgz fpgz3 : listfp) {
				fpgz3.setMrbz("0");
			}
			fpgzService.save(listfp);
		}
		if (null==fpgz.getSfqzfp()) {
			fpgz.setSfqzfp("0");
		}
		if (null==fpgz.getSfspzsfp()) {//是否按商品整数分票
			fpgz.setSfspzsfp("0");
		}
		fpgz.setYxbz("1");
		fpgz.setLrry(fpgz2.getLrry());
		fpgz.setXgry(getYhid());
		fpgz.setLrsj(fpgz2.getLrsj());
		fpgz.setGsdm(fpgz2.getGsdm());
		fpgz.setXfids(xfs);
		fpgz.setXgry(getYhid());
		fpgz.setXgsj(new Date());
		fpgzService.save(fpgz);
		result.put("msg", "修改成功");
		return result;
	}
	
	//修改
	@RequestMapping(value = "/hqfpxe")
	@ResponseBody
	public Map<String, Object> hqfpxe(String xfsh,String fpzldm){
		Map<String, Object> result = new HashMap<String, Object>();
        List<Fpgz> listt = fpgzService.findAllByParams(new HashMap<>());
        Xf xf2 = new Xf();
        xf2.setXfsh(xfsh);
        Xf xf = XfService.findOneByParams(xf2);
        double fpje = Double.MAX_VALUE;
        double zdje = Double.MAX_VALUE;
        if ("01".equals(fpzldm)) {
      	  fpje = xf.getZpfpje();
            zdje = xf.getZpzdje();
		}else if ("02".equals(fpzldm)) {
			  fpje = xf.getPpfpje();
		      zdje = xf.getPpzdje();
		}else if ("11".equals(fpzldm)) {
			  fpje = xf.getDzpfpje();
		      zdje = xf.getDzpzdje();
		}
        for (Fpgz fpgz : listt) {
			if (fpgz.getXfids().contains(String.valueOf(xf.getId()))) {
				if ("01".equals(fpzldm)) {
					fpje=fpgz.getZpxe();
				}else if ("02".equals(fpzldm)) {
					fpje=fpgz.getPpxe();
				}else if ("11".equals(fpzldm)) {
					fpje=fpgz.getDzpxe();
				}
			}
		}
        result.put("fpje", fpje);
        result.put("success", true);
		return result;
	}

}
