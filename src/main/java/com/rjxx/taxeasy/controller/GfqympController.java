package com.rjxx.taxeasy.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.domains.Gfxx;
import com.rjxx.taxeasy.domains.Jyxx;
import com.rjxx.taxeasy.domains.Qympk;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.filter.SystemControllerLog;
import com.rjxx.taxeasy.service.GfxxService;
import com.rjxx.taxeasy.service.JyxxService;
import com.rjxx.taxeasy.service.KplsvoService;
import com.rjxx.taxeasy.service.QympkService;
import com.rjxx.taxeasy.vo.KplsVO;
import com.rjxx.taxeasy.web.BaseController;
import com.rjxx.utils.ChinaInitial;

@Controller
@RequestMapping("/gfqymp")
public class GfqympController extends BaseController {

	@Autowired
	private GfxxService gfxxservice;
	@Autowired
	private QympkService qympkService;
	
	@RequestMapping
	public String index() {
		return "gfqymp/index";
	}

	/**
	 * 初始化显示列表
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getGfxxList")
	@ResponseBody
	public Map getGfxxList(int length, int start, int draw) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Pagination pagination = new Pagination();
		pagination.setPageNo(start / length + 1);
		pagination.setPageSize(length);
		String gsdm = getGsdm();
		
		String gfmc=request.getParameter("gfmc");
		String nsrsbh=request.getParameter("nsrsbh");
		pagination.addParam("gfmc", gfmc);
		pagination.addParam("nsrsbh", nsrsbh);
		pagination.addParam("gsdm", gsdm);
		// pagination.addParam("fpczlxdm", "12");
		List<Gfxx> list = gfxxservice.findByPage(pagination);
		int total = pagination.getTotalRecord();
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		result.put("draw", draw);
		result.put("data", list);
		return result;
	}

	/**
	 * 保存购方企业信息
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveGfxx", method = RequestMethod.POST)
	@ResponseBody
	public Map saveGfxx() {
		Map<String, Object> result = new HashMap<String, Object>();
		String gfmc = request.getParameter("gfmc");
		String gfsh = request.getParameter("gfsh");
		String gfdz = request.getParameter("gfdz");
		String gfdh = request.getParameter("gfdh");
		String gfyh = request.getParameter("gfyh");
		String gfyhzh = request.getParameter("gfyhzh");
		String lxr = request.getParameter("lxr");
		String lxdh = request.getParameter("lxdh");
		String yjdz = request.getParameter("yjdz");
		
		

		// 校验数据 未完成
		try {
			// 购房名称生成首字母缩写
			ChinaInitial chinain = new ChinaInitial();
			String mcszmsx = chinain.getPYIndexStr(gfmc, false);//第二个参数代表是否大小写，ture大写，false小写。
			Gfxx gfxx = new Gfxx();
			gfxx.setGfsh(gfsh);
			gfxx.setGfmc(gfmc);
			gfxx.setGfdz(gfdz);
			gfxx.setGfdh(gfdh);
			gfxx.setGfyh(gfyh);
			gfxx.setGfyhzh(gfyhzh);
			gfxx.setMcszmsx(mcszmsx);
			gfxx.setYxbz("1");
			gfxx.setLrsj(new Date());
			gfxx.setLrry(1);
			gfxx.setXgsj(new Date());
			gfxx.setXgry(1);
			gfxx.setGsdm(getGsdm());
			gfxx.setLxr(lxr);
			gfxx.setLxdh(lxdh);
			gfxx.setYjdz(yjdz);
			Map map=new HashMap();
			map.put("gsdm", getGsdm());
			map.put("gfmc", gfmc);
			map.put("nsrsbh", gfsh);
			List<Qympk> list=qympkService.findAllByParams(map);
            if(list.size()==0){
            	Qympk qympk=new Qympk();
            	qympk.setDwmc(gfmc);
            	qympk.setNsrsbh(gfsh);
            	qympk.setKhyh(gfyh);
            	qympk.setYhzh(gfyhzh);
            	qympk.setZcdz(gfdz);
            	qympk.setZcdh(gfdh);
            	qympk.setGsdm(getGsdm());
            	qympk.setYxbz("1");
            	qympk.setLxr(lxr);
            	qympk.setLxdh(lxdh);
            	qympk.setYjdz(yjdz);
            	qympkService.save(qympk);
            }
			
			gfxxservice.save(gfxx);
			result.put("success", true);
			result.put("msg", "保存成功");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.put("success", false);
			result.put("msg", "保存出现错误: " + ex.getMessage());
		}
		return result;
	}
	
	

	/**
	 * 删除购方企业信息
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Map delete() {
		Map<String, Object> result = new HashMap<String, Object>();
		String id = request.getParameter("id");
		Map params = new HashMap();
		// 校验数据 未完成
		try {
			params.put("id", id);
			gfxxservice.deleteById(params);
			result.put("success", true);
			result.put("msg", "删除成功");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.put("success", false);
			result.put("msg", "保存出现错误: " + ex.getMessage());
		}
		return result;
	}
	

	/**
	 * 删除
	 *
	 * @param djhArr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/doDel",method = RequestMethod.POST)
	@ResponseBody
	//@SystemControllerLog(description = "开票单删除",key = "djhArr")   
	public boolean doDel(String djhArr) throws Exception {
		gfxxservice.delByIdList(convertToList(djhArr));
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
	 * 保存购方企业信息
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Map updateGfxx() {
		Map<String, Object> result = new HashMap<String, Object>();
		String id = request.getParameter("gfid");
		String gfmc = request.getParameter("gfmc");
		String gfsh = request.getParameter("gfsh");
		String gfdz = request.getParameter("gfdz");
		String gfdh = request.getParameter("gfdh");
		String gfyh = request.getParameter("gfyh");
		String gfyhzh = request.getParameter("gfyhzh");
		String lxr = request.getParameter("lxr");
		String lxdh = request.getParameter("lxdh");
		String yjdz = request.getParameter("yjdz");
		// 校验数据 未完成
		try {
			// 购房名称生成首字母缩写
			ChinaInitial chinain = new ChinaInitial();
			String mcszmsx = chinain.getPYIndexStr(gfmc, false);//第二个参数代表是否大小写，ture大写，false小写。
			Gfxx gfxx = new Gfxx();
			gfxx.setId(Integer.valueOf(id));
			gfxx.setGfsh(gfsh);
			gfxx.setGfmc(gfmc);
			gfxx.setGfdz(gfdz);
			gfxx.setGfdh(gfdh);
			gfxx.setGfyh(gfyh);
			gfxx.setGfyhzh(gfyhzh);
			gfxx.setMcszmsx(mcszmsx);
			gfxx.setYxbz("1");
			gfxx.setGsdm(getGsdm());
			//gfxx.setLrsj(new Date());
			gfxx.setLrry(1);
			gfxx.setXgsj(new Date());
			gfxx.setXgry(1);
			gfxx.setLxr(lxr);
			gfxx.setLxdh(lxdh);
			gfxx.setYjdz(yjdz);
			gfxxservice.save(gfxx);
			result.put("success", true);
			result.put("msg", "更新成功");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.put("success", false);
			result.put("msg", "更新保存出现错误: " + ex.getMessage());
		}
		return result;
	}
}
