package com.rjxx.taxeasy.controller;

import com.rjxx.taxeasy.domains.Fpzl;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.service.FpzlService;
import com.rjxx.taxeasy.service.KplsvoService;
import com.rjxx.taxeasy.vo.KplsVO;
import com.rjxx.taxeasy.web.BaseController;
import com.rjxx.time.TimeUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.NumberFormat;
import java.util.*;

@Controller
@RequestMapping("/sjdtjbb")
public class SjdtjbbController extends BaseController {
	@Autowired
	private KplsvoService ks;
	@Autowired
	private FpzlService fpzlService;

	@RequestMapping
	public String index() {
		List<Fpzl> fpzlList = fpzlService.findAllByParams(new HashMap<>());
		request.setAttribute("fpzlList", fpzlList);
		List<Xf> xfs = getXfList();
		request.setAttribute("xfs", xfs);
		return "sjdtjbb/index";
	}

	/**
	 * 根据日期获得相应信息
	 *
	 * @return
	 */
	@RequestMapping(value = "/getList")
	@ResponseBody
	public Map getList(String kprq, String kprq1) {
		Map<String, Object> result = new HashMap<String, Object>();
		double // je3 = 0.00,se3 = 0.00,jshj3 = 0.00,
		je1 = 0.00, se1 = 0.00, jshj1 = 0.00, je2 = 0.00, se2 = 0.00, jshj2 = 0.00;
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("gsdm", getGsdm());
			params.put("kprqq", kprq);
			params.put("kprqz", kprq1);
			params.put("xxzs", "11");
			List<KplsVO> ls1 = ks.findAllByParams(params);
			if (ls1.size() > 0) {
				for (int j = 0; j < ls1.size(); j++) {
					je1 += ls1.get(j).getJe();
					se1 += ls1.get(j).getSe();
					jshj1 += ls1.get(j).getJshj();
					// System.out.println(ls1.get(j).getDdh());
				}
			}
			result.put("je1", getNumberFormat(je1));
			result.put("se1", getNumberFormat(se1));
			result.put("jshj1", getNumberFormat(jshj1));
			result.put("fpsl1", ls1.size());
			// 红冲
			params.put("xxzs", null);
			params.put("xxfs", "12");
			List<KplsVO> ls2 = ks.findAllByParams(params);
			if (ls2.size() > 0) {
				for (int k = 0; k < ls2.size(); k++) {
					je2 += ls2.get(k).getJe();
					se2 += ls2.get(k).getSe();
					jshj2 += ls2.get(k).getJshj();
				}
			}
			result.put("je2", getNumberFormat(je2));
			result.put("se2", getNumberFormat(se2));
			result.put("jshj2", getNumberFormat(jshj2));
			result.put("fpsl2", ls2.size());
			result.put("je3", getNumberFormat(je1 + je2));
			result.put("se3", getNumberFormat(se1 + se2));
			result.put("jshj3", getNumberFormat(jshj1 + jshj2));
			result.put("fpsl3", ls1.size() + ls2.size());
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("failure", true);
			result.put("msg", "后台出现错误: " + e.getMessage());
		}
		return result;
	}

	/**
	 * 得到前一天
	 *
	 * @return
	 */
	@RequestMapping(value = "/getPre")
	@ResponseBody
	public Map getPre(String kprq) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Date d = TimeUtil.getStringInDate(kprq, null);
			d.setDate(d.getDate() - 1);
			String rq = TimeUtil.formatDate(d, null);
			result.put("prerq", rq);
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("failure", true);
			result.put("msg", "后台出现错误: " + e.getMessage());
		}
		return result;
	}

	/**
	 * 得到后一天
	 *
	 * @return
	 */
	@RequestMapping(value = "/getLater")
	@ResponseBody
	public Map getLater(String kprq) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Date d = TimeUtil.getStringInDate(kprq, null);
			d.setDate(d.getDate() + 1);
			String rq = TimeUtil.formatDate(d, null);
			result.put("laterrq", rq);
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("failure", true);
			result.put("msg", "后台出现错误: " + e.getMessage());
		}
		return result;
	}

	public void save() {
		/*
		 * Map<String, Object> result = new HashMap<String, Object>(); return
		 * new JsonView(result);
		 */
	}

	/**
	 * 将double类型数据保留两位小数并返回金钱格式
	 *
	 * @param data
	 * @return
	 */
	public static String getNumberFormat(double data) {
		NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.CHINA);
		return nf.format(data).replace("￥", "");
	}

	public static void main(String[] args) throws Exception {
		Date d = TimeUtil.getStringInDate("2015-12-12", null);
		// d.setTime(d.getTime()+1);
		d.setDate(d.getDate() + 1);
		String rq = TimeUtil.formatDate(d, null);
		System.out.print(rq);
	}
}
