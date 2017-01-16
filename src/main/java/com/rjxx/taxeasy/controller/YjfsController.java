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
import com.rjxx.taxeasy.bizcomm.utils.GetYjnr;
import com.rjxx.taxeasy.bizcomm.utils.SendalEmail;
import com.rjxx.taxeasy.domains.Jyls;
import com.rjxx.taxeasy.domains.Kpls;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.service.GsxxService;
import com.rjxx.taxeasy.service.JylsService;
import com.rjxx.taxeasy.service.KplsService;
import com.rjxx.taxeasy.service.KplsvoService;
import com.rjxx.taxeasy.vo.KplsVO;
import com.rjxx.taxeasy.web.BaseController;

/**
 * Created by lenovo on 2015/12/18.
 */
@Controller
@RequestMapping("/yjfs")
public class YjfsController extends BaseController {

	@Autowired
	private KplsService ks;

	@Autowired
	private KplsvoService kvs;

	@Autowired
	private JylsService js;

	@Autowired
	private GsxxService gs;
	
	@Autowired
	private SendalEmail se;

	@RequestMapping
	public String index() {
		return "yjfs/index";
	}

	@RequestMapping(value = "/send")
	@ResponseBody
	public Map send(String ids) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<>();
		if (!"".equals(ids)) {
			String[] idls = ids.split(",");
			for (int i = 0; i < idls.length; i++) {
				String id = idls[i].split(":")[0];
				if (id.equals("")) {
					continue;
				}
				try {
					params.put("kplsh", id);
					final Kpls kpls = ks.findOneByParams(params);
					Jyls jyls = js.findOne(kpls.getDjh());
					GetYjnr getYjnr = new GetYjnr();					
					String content = getYjnr.getFpkjYj(jyls.getDdh(), new ArrayList<String>(){{add(kpls.getPdfurl());}},kpls.getXfmc());
					se.sendEmail(String.valueOf(kpls.getDjh()), kpls.getGsdm(), kpls.getGfemail(), "手工发送邮件", String.valueOf(kpls.getDjh()), content, "电子发票");
					/*SendEmail se = new SendEmail();
					se.sendMail(jyls.getDdh(), kpls.getGfemail(), new ArrayList<String>() {
						{
							add(kpls.getPdfurl());
						}
					}, gsxx.getGsmc());*/
				} catch (Exception e) {
					e.printStackTrace();
					result.put("statu", "1");
					result.put("msg", "保存出现错误: " + e.getMessage());
					return result;
				}
			}
		}
		result.put("statu", "0");
		return result;
	}

	@RequestMapping(value = "/update")
	@ResponseBody
	public Map update(String kplsh, String gfemail, String wx, String sj) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("kplsh", kplsh);
			Kpls kpls = ks.findOneByParams(params);
			kpls.setGfemail(gfemail);
			ks.save(kpls);
			result.put("success", true);
			result.put("statu", "0");
			result.put("msg", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("failure", true);
			result.put("msg", "保存出现错误: " + e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/getYjfsList")
	@ResponseBody
	public Map getYjfsList(int length, int start, int draw, String jyrqq, String jyrqz, String kprqq, String kprqz,
			String gfmc, String ddh) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Pagination pagination = new Pagination();
		pagination.setPageNo(start / length + 1);
		pagination.setPageSize(length);
		List<Integer> xfs = new ArrayList<>();
		if (!getXfList().isEmpty()) {
			for (Xf xf : getXfList()) {
				xfs.add(xf.getId());
			}
		}
		if (xfs.size() > 0) {
			pagination.addParam("xfList", xfs);
		}
		pagination.addParam("gsdm", getGsdm());
		pagination.addParam("gfmc", gfmc);
		pagination.addParam("ddh", ddh);
		pagination.addParam("jyrqq", jyrqq);
		pagination.addParam("jyrqz", jyrqz);
		if (!"".equals(kprqq)) {
			pagination.addParam("kprqq", kprqq);
		}
		if (!"".equals(kprqz)) {
			pagination.addParam("kprqz", kprqz);
		}
		pagination.addParam("fpczlxdm", "11");
		List<KplsVO> list = kvs.findByPage(pagination);
		int total = pagination.getTotalRecord();
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		result.put("draw", draw);
		result.put("data", list);
		return result;
	}
}
