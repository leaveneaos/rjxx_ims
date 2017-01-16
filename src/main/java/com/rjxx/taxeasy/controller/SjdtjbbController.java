package com.rjxx.taxeasy.controller;

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

	@RequestMapping
	public String index() {
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

		// String cond = "1=1";
		// if (!"".equals(kprq)) {
		// cond += " and kprq >= ? and kprq< date_add(?, INTERVAL 1 day) and
		// gsdm = '" + this.getGsdm() + "'";
		// }
		//
		// String sql1 = " and fpzldm = '12' AND fpczlxdm = '11'";
		// String sql2 = " and fpzldm = '12' AND (fpczlxdm = '12' or fpczlxdm =
		// '13')";
		// String sql3 = " and fpzldm = '12' AND fpczlxdm = '11' AND hkbz='1'";

		// List<Object> tmpArgs = new ArrayList<Object>();
		// tmpArgs.add(kprq);
		// tmpArgs.add(kprq1);
		// Object[] args = tmpArgs.toArray();

		double // je3 = 0.00,se3 = 0.00,jshj3 = 0.00,
		je1 = 0.00, se1 = 0.00, jshj1 = 0.00, je2 = 0.00, se2 = 0.00, jshj2 = 0.00;
		try {
			// 换开
			// List<KplsVO> ls3 = KplsVO.findAll(KplsVO.class, cond+sql3,
			// args,"kplsh desc");
			// if(ls3.size()>0){
			// for(int i=0;i<ls3.size();i++){
			// je3+=ls3.get(i).getJe();
			// se3+=ls3.get(i).getSe();
			// jshj3+=ls3.get(i).getJshj();
			// }
			// }
			// result.put("je3", getNumberFormat(je3));
			// result.put("se3", getNumberFormat(se3));
			// result.put("jshj3", getNumberFormat(jshj3));
			// result.put("fpsl3", ls3.size());
			// 正常开具
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

	// public View export(String kprq) throws Exception {
	// String cond = "1=1";
	// if (!"".equals(kprq)) {
	// cond += " and kprq like '%" + kprq + "%' and gsdm = '" + this.getGsdm() +
	// "'";
	// }
	// String sql1 = " and fpzldm = '12' AND fpczlxdm = '11' AND hkbz = '0'";
	// String sql2 = " and fpzldm = '12' AND fpczlxdm = '12' AND hkbz = '0'";
	// String sql3 = " and fpzldm = '12' AND fpczlxdm = '11' AND hkbz='1'";
	// List<Object> tmpArgs = new ArrayList<Object>();
	// Object[] args = tmpArgs.toArray();
	// double je3 = 0.00, se3 = 0.00, jshj3 = 0.00,
	// je1 = 0.00, se1 = 0.00, jshj1 = 0.00,
	// je2 = 0.00, se2 = 0.00, jshj2 = 0.00;
	// //换开
	// List<KplsVO> ls3 = KplsVO.findAll(KplsVO.class, cond + sql3, args, "kplsh
	// desc");
	// if (ls3.size() > 0) {
	// for (int i = 0; i < ls3.size(); i++) {
	// je3 += ls3.get(i).getJe();
	// se3 += ls3.get(i).getSe();
	// jshj3 += ls3.get(i).getJshj();
	// }
	// }
	// //正常开具
	// List<KplsVO> ls1 = KplsVO.findAll(KplsVO.class, cond + sql1, args, "kplsh
	// desc");
	// if (ls1.size() > 0) {
	// for (int j = 0; j < ls1.size(); j++) {
	// je1 += ls1.get(j).getJe();
	// se1 += ls1.get(j).getSe();
	// jshj1 += ls1.get(j).getJshj();
	// }
	// }
	// //红冲
	// List<KplsVO> ls2 = KplsVO.findAll(KplsVO.class, cond + sql2, args, "kplsh
	// desc");
	// if (ls2.size() > 0) {
	// for (int k = 0; k < ls2.size(); k++) {
	// je2 += ls2.get(k).getJe();
	// se2 += ls2.get(k).getSe();
	// jshj2 += ls2.get(k).getJshj();
	// }
	// }
	// try {
	// String[] headers = {"正常开具金额", "正常开具税额", "正常开具价税合计", "正常开具发票数量", "红冲开具金额",
	// "红冲开具税额", "红冲开具价税合计", "红冲开具发票数量", "发票换开金额", "发票换开税额", "发票换开价税合计",
	// "发票换开发票数量"};
	// // 第一步，创建一个webbook，对应一个Excel文件
	// HSSFWorkbook wb = new HSSFWorkbook();
	// // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
	// HSSFSheet sheet = wb.createSheet("已开发票");
	// // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
	// HSSFRow row = sheet.createRow(0);
	// // 第四步，创建单元格，并设置值表头 设置表头居中
	// HSSFCellStyle style = wb.createCellStyle();
	// style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
	// SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	// HSSFCell cell = null;
	// for (int i = 0; i < headers.length; i++) {
	// cell = row.createCell(i);
	// cell.setCellValue(headers[i]);
	// cell.setCellStyle(style);
	// }
	// row = sheet.createRow(1);
	// row.createCell(0).setCellValue(kprq);
	// row.createCell(1).setCellValue(je1);
	// row.createCell(2).setCellValue(se1);
	// row.createCell(3).setCellValue(jshj1);
	// row.createCell(4).setCellValue(ls1.size());
	// row.createCell(5).setCellValue(je2);
	// row.createCell(6).setCellValue(se2);
	// row.createCell(7).setCellValue(jshj2);
	// row.createCell(8).setCellValue(ls2.size());
	// row.createCell(9).setCellValue(je3);
	// row.createCell(10).setCellValue(se3);
	// row.createCell(11).setCellValue(jshj3);
	// row.createCell(12).setCellValue(ls3.size());
	// SimpleDateFormat timeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	// String filename = timeFormat.format(new Date()) + ".xls";
	// response.setContentType("application/vnd.ms-excel;charset=UTF-8");
	// response.setHeader("Content-Disposition",
	// "attachment;filename=".concat(String.valueOf(URLEncoder.encode(filename,
	// "UTF-8"))));
	// OutputStream out = response.getOutputStream();
	// wb.write(out);
	// out.close();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return null;
	// }
}
