package com.rjxx.taxeasy.controller;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import com.rjxx.taxeasy.service.*;
import com.rjxx.taxeasy.vo.DczydlVo;
import com.rjxx.utils.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.domains.Jymxsq;
import com.rjxx.taxeasy.domains.Jyspmx;
import com.rjxx.taxeasy.domains.Kpls;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.vo.Jylsvo;
import com.rjxx.taxeasy.vo.JyxxsqVO;
import com.rjxx.taxeasy.web.BaseController;
import com.rjxx.time.TimeUtil;

@Controller
@RequestMapping("/ddcx")
public class DdcxController extends BaseController {

	@Autowired
	private JyxxsqService jyxxsqservice;

	@Autowired
	private JymxsqService jymxsqservice;

	@Autowired
	private KplsService kplsService;
	@Autowired
	private YhDczdylService yhDczdylService;

	@RequestMapping
	public String index() {
		List<Xf> list = getXfList();
		request.setAttribute("xfSum", list.size());
		request.setAttribute("xfList", list);
		return "ddcx/index";
	}

	@RequestMapping(value = "/getJylsDd")
	@ResponseBody
	public Map getItems(int length, int start, int draw, String xfsh, String gfmc, String ddh, String rqq, String rqz,
			String jylsh,boolean loaddata,String ztbz) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(loaddata){
			Map params = new HashMap();
			/*Pagination pagination = new Pagination();
			pagination.setPageNo(start / length + 1);
			pagination.setPageSize(length);*/
			List<Integer> xfs = new ArrayList<>();
			if (!getXfList().isEmpty()) {
				for (Xf xf : getXfList()) {
					xfs.add(xf.getId());
				}
			}
			if (xfs.size() > 0) {
				params.put("xfList", xfs);
			}
			params.put("xfsh", xfsh);
			params.put("gfmc", gfmc);
			params.put("ddh", ddh);
			params.put("jylsh", jylsh);
			params.put("ztbz", ztbz);
			if (rqq != null && !rqq.trim().equals("") && rqz != null && !rqz.trim().equals("")) { // 名称参数非空时增加名称查询条件
				params.put("rqq", rqq);
				params.put("rqz", TimeUtil.getAfterDays(rqz, 1));
			} else if (rqq != null && !rqq.trim().equals("") && (rqz == null || rqz.trim().equals(""))) {
				params.put("rqq", rqq);
				params.put("rqz", TimeUtil.getAfterDays(rqq, 1));
			} else if ((rqq == null || rqq.trim().equals("")) && rqz != null && !rqz.trim().equals("")) {
				params.put("rqq", rqz);
				params.put("rqz", TimeUtil.getAfterDays(rqz, 1));
			}

			//params.addParam("orderBy", "ddrq desc");
			params.put("gsdm", this.getGsdm());
			params.put("start",start);
			params.put("length",length);
			List<JyxxsqVO> list = jyxxsqservice.findBykplscxPage(params);
			//int total = pagination.getTotalRecord();
			int total;
			if(0 == start){
				total = jyxxsqservice.findBykplscxtotal(params);
				request.getSession().setAttribute("total",total);
			}else{
				total =  (Integer)request.getSession().getAttribute("total");
				//request.getSession().getAttribute("total");
			}
			result.put("recordsTotal",total);
			result.put("recordsFiltered",total);
			result.put("draw",draw);
			result.put("data",list);
		}else {
			result.put("recordsTotal",0);
			result.put("recordsFiltered",0);
			result.put("draw",draw);
			result.put("data",new ArrayList<>());
		}
		return result;
	}

	@RequestMapping(value = "/getMx")
	@ResponseBody
	public Map getMx(int length, int start, int draw, String sqlsh) {
		Map<String, Object> result = new HashMap<String, Object>();
		Pagination pagination = new Pagination();
		pagination.setPageNo(start / length + 1);
		pagination.setPageSize(length);
		pagination.addParam("sqlsh", sqlsh);
		pagination.addParam("gsdm", this.getGsdm());
		List<Jymxsq> list = jymxsqservice.findByPage(pagination);
		int total = pagination.getTotalRecord();
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		result.put("draw", draw);
		result.put("data", list);
		return result;
	}

	@RequestMapping(value = "/getFp")
	@ResponseBody
	public Map getFp(Integer sqlsh) {
		Map<String, Object> result = new HashMap<String, Object>();
		Kpls kp = new Kpls();
		kp.setDjh(sqlsh);
		kp.setGsdm(getGsdm());
		List<Kpls> list = kplsService.findAllByKpls(kp);
		if (!list.isEmpty()) {
			String fpdm = list.get(0).getFpdm();
			String fphm = "";
			double hjje = 0;
			double hjse = 0;
			for (Kpls kpls : list) {
				fphm += kpls.getFphm() + ",";
				hjje += kpls.getHjje();
				hjse += kpls.getHjse();
			}
			result.put("fpdm", fpdm);
			result.put("fphm", fphm.subSequence(0, fphm.length() - 1));
			result.put("hjje", hjje);
			result.put("hjse", hjse);
			result.put("success", true);
		} else {
			result.put("none", true);
		}
		return result;
	}

	// 文件导出
	@RequestMapping(value = "/exportExcel1")
	@ResponseBody
	public Map<String, Object> exportExcel(String dxcsm, String w_kprqq, String w_kprqz,
										  String s_rqq ,String s_rqz, String s_xfsh, String s_gfmc,String s_ddh,String s_lsh,String dxcsz,String s_ddzt) throws Exception {

		Map params = new HashMap();
		List<Integer> xfs = new ArrayList<>();
		if (!getXfList().isEmpty()) {
			for (Xf xf : getXfList()) {
				xfs.add(xf.getId());
			}
		}
		if (xfs.size() > 0) {
			params.put("xfList", xfs);
		}
		if("ddh".equals(dxcsm)){
			params.put("ddh",dxcsz);
		}else if("gfmc".equals(dxcsm)){
			params.put("gfmc",dxcsz);
		}else {
			params.put("ddh",s_ddh);
			params.put("gfmc",s_gfmc);
		}
		params.put("xfsh",s_xfsh);
		params.put("jylsh",s_lsh);
		params.put("ztbz",s_ddzt);
		if(StringUtils.isNotBlank(w_kprqq)&&StringUtils.isNotBlank(w_kprqz)){
			params.put("rqq",w_kprqq);
			params.put("rqz", TimeUtil.getAfterDays(w_kprqz, 1));
		}else {
			params.put("rqq",s_rqq);
			params.put("rqz", TimeUtil.getAfterDays(s_rqz, 1));
		}
		params.put("gsdm", this.getGsdm());
		List<JyxxsqVO> kplscxList = jyxxsqservice.findAllBykplscx(params);
//		Map<String, Object> map = new HashMap<>();
//		int yhid = getYhid();
//		map.put("yhid", yhid);
//		List<DczydlVo> list = yhDczdylService.findAllByParams(map);
		String headers1 = "流水号,订单号, 价税合计, 购方名称, 订单日期,发票类型,数据来源,订单状态";
//		for (DczydlVo yhDczdyl : list) {
//			headers1 += "," + yhDczdyl.getZdzwm();
//		}
		String[] headers = headers1.split(",");
		// 第一步，创建一个webbook，对应一个Excel文件
		SXSSFWorkbook wb = new SXSSFWorkbook(100); // 这里100是在内存中的数量，如果大于此数量时，会写到硬盘，以避免在内存导致内存溢出
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		Sheet sheet = wb.createSheet("开票流水");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		Row row = sheet.createRow(0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		CellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Cell cell = null;
		for (int i = 0; i < headers.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(headers[i]);
			cell.setCellStyle(style);
		}
		JyxxsqVO kplscx = null;
		if(!kplscxList.isEmpty()) {
			for (int i = 0; i < kplscxList.size(); i++) {
				row = sheet.createRow((short) i + 1);
				kplscx = kplscxList.get(i);
				String sjly = kplscx.getSjly();
				if (StringUtils.isNotBlank(sjly)) {
					if ("0".equals(sjly)) {
						sjly = "平台录入";
					} else if ("1".equals(sjly)) {
						sjly = "接口接入";
					} else if ("2".equals(sjly)) {
						sjly = "平台导入";
					} else if ("3".equals(sjly)) {
						sjly = "钉钉录入";
					} else if ("4".equals(sjly)) {
						sjly = "微信录入";
					} else if ("5".equals(sjly)) {
						sjly = "支付宝录入";
					} else if ("6".equals(sjly)) {
						sjly = "其他浏览器录入";
					}
				} else {
					sjly = "";
				}
				row.createCell(0).setCellValue(kplscx.getJylsh() == null ? "" : kplscx.getJylsh());
				row.createCell(1).setCellValue(kplscx.getDdh() == null ? "" : kplscx.getDdh());
				row.createCell(2).setCellValue(kplscx.getJshj() == null ? "0.00" : String.format("%.2f", kplscx.getJshj()));
				row.createCell(3).setCellValue(kplscx.getGfmc() == null ? "" : kplscx.getGfmc());
				row.createCell(4).setCellValue(kplscx.getDdrq() == null ? "" : sdf1.format(kplscx.getDdrq()));
				row.createCell(5).setCellValue(kplscx.getFpzlmc() == null ? "" : kplscx.getFpzlmc());
				row.createCell(6).setCellValue(sjly == null ? "" : sjly);
				row.createCell(7).setCellValue(kplscx.getZtbzmc() == null ? "" : kplscx.getZtbzmc());
			}
		}
		SimpleDateFormat timeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String filename = timeFormat.format(new Date()) + ".xlsx";
		response.setContentType("application/ms-excel;charset=UTF-8");
		response.setHeader("Content-Disposition",
				"attachment;filename=".concat(String.valueOf(URLEncoder.encode(filename, "UTF-8"))));
		OutputStream out = response.getOutputStream();
		wb.write(out);
		out.close();
		return null;
	}
}
