package com.rjxx.taxeasy.controller;

import com.rjxx.taxeasy.bizcomm.utils.SendEmail;
import com.rjxx.taxeasy.domains.Gsxx;
import com.rjxx.taxeasy.domains.Jyls;
import com.rjxx.taxeasy.domains.Kpls;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.service.GsxxService;
import com.rjxx.taxeasy.service.JylsService;
import com.rjxx.taxeasy.service.KplsService;
import com.rjxx.taxeasy.service.XfService;
import com.rjxx.taxeasy.web.BaseController;
import com.rjxx.utils.AESUtils;
import com.rjxx.utils.SysPara;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@org.springframework.stereotype.Controller
@RequestMapping("/tqm")
public class TqmController extends BaseController {

	@Autowired
	private XfService xs;
	@Autowired
	private GsxxService gs;
	@Autowired
	private JylsService js;
	@Autowired
	private KplsService ks;

	public final static String AES_KEY = "QGDYMHVZ";
	// @RequestMapping
	// public String index(String key) throws IOException {
	// if (StringUtils.isBlank(key)) {
	// response.sendError(404);
	// return "tqm/index";
	// }
	// try {
	// byte[] bytes = Base64.decodeBase64(key);
	// String decryptString = new String(AESUtils.decrypt(bytes));
	// String[] arr = decryptString.split(",");
	// String gsdm = arr[0];
	// String tqm = "";
	// if (arr.length > 1) {
	// tqm = arr[1];
	// }
	// Xf x = new Xf();
	// x.setGsdm(gsdm);
	// List<Xf> xfList = xs.findAllByParams(x);
	// if (xfList.isEmpty()) {
	// response.sendError(404);
	// return "tqm/index";
	// }
	// Map<String, Object> params = new HashMap<>();
	// Gsxx gsxx = gs.findOneByParams(params);
	// String gsjc = gsxx.getGsjc();
	// request.setAttribute("gsmc", gsjc);
	// request.setAttribute("gsdm", gsdm);
	// request.setAttribute("tqm", tqm);
	// return "tqm/index";
	// } catch (Exception e) {
	// e.printStackTrace();
	// response.sendError(404);
	// return "tqm/index";
	// }
	// }

	@RequestMapping(value = "/fpsc")
	@ResponseBody
	public String fpsc(String gsdm, String tqm, String yzm) throws Exception {
		String rand = (String) request.getSession().getAttribute("rand");
		request.setAttribute("gsdm", gsdm);
		if (StringUtils.isBlank(yzm) || StringUtils.isBlank(rand) || !yzm.equalsIgnoreCase(rand)) {
			request.setAttribute("tqm", tqm);
			request.setAttribute("error", "验证码输入不正确");
			return "tqm/index";
		}
		if (StringUtils.isBlank(gsdm) || StringUtils.isBlank(tqm)) {
			request.setAttribute("error", "提取码输入不正确");
			return "tqm/index";
		}
		// 提取码存在，跳转到获取发票页面
		Map<String, Object> params = new HashMap<>();
		Jyls ls = new Jyls();
		ls.setTqm(tqm);
		ls.setGsdm(gsdm);
		Jyls jyls = js.findOneByParams(ls);
		params.put("djh", jyls.getDjh());
		params.put("djh", jyls.getDjh());
		if (jyls != null) {
			List<Kpls> kpls = ks.findKplsByDjh(params);
			if (kpls.isEmpty()) {
				request.setAttribute("tqm", tqm);
				request.setAttribute("error", "正在开具发票中，请稍后获取...");
				return "tqm/index";
			}
			List<String> jpgList = new ArrayList<>();
			for (Kpls kpls1 : kpls) {
				// 生成jpgurl
				String pdfurl = kpls1.getPdfurl();
				if (StringUtils.isNotBlank(pdfurl)) {
					int pos22 = pdfurl.lastIndexOf(".");
					String jpgurl = pdfurl.substring(0, pos22) + ".jpg";
					jpgList.add(jpgurl);
				}
			}
			request.setAttribute("jpgList", jpgList);
			request.setAttribute("tqm", tqm);
			return "tqm/index";
		} else {
			request.setAttribute("error", "提取码输入不正确");
			return "tqm/index";
		}
	}

	/**
	 * 下载
	 *
	 * @throws Exception
	 */
	@RequestMapping(value = "/download")
	@ResponseBody
	public void download(String gsdm, String tqm) throws Exception {
		response.setContentType("APPLICATION/OCTET-STREAM");
		response.setHeader("Content-Disposition", "attachment; filename=" + this.getZipFilename());
		ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
		request.setCharacterEncoding("utf-8");
		List<File> files = new ArrayList<>();
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("tqm", tqm);
			params.put("gsdm", gsdm);
			List<Kpls> kpls = ks.findKplsByPms(params);
			if (!kpls.isEmpty()) {
				File file = null;
				for (Kpls kp : kpls) {
					file = new File(
							kp.getPdfurl().replace(SysPara.getValue("serverUrl"), SysPara.getValue("classpath")));
					files.add(file);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		zipFile(files, "", zos);
		zos.flush();
		zos.close();
	}

	private void zipFile(List<File> subs, String baseName, ZipOutputStream zos) throws IOException {
		for (int i = 0; i < subs.size(); i++) {
			File f = subs.get(i);
			zos.putNextEntry(new ZipEntry(baseName + f.getName()));
			FileInputStream fis = new FileInputStream(f);
			byte[] buffer = new byte[1024];
			int r = 0;
			while ((r = fis.read(buffer)) != -1) {
				zos.write(buffer, 0, r);
			}
			fis.close();
		}
	}

	private String getZipFilename() {
		Date date = new Date();
		String s = date.getTime() + ".zip";
		return s;
	}

	@RequestMapping
	public String email(String gsdm, String tqm) {
		request.setAttribute("gsdm", gsdm);
		request.setAttribute("tqm", tqm);
		return "tqm/email";
	}

	@RequestMapping(value = "/sendmail")
	@ResponseBody
	public Map sendmail(String gfemail, String gsdm, String tqm) {
		Map<String, Object> result = new HashMap<>();
		List<String> pdfList = new ArrayList<>();

		try {
			Jyls jy = new Jyls();
			jy.setTqm(tqm);
			jy.setGsdm(gsdm);
			Jyls jyls = js.findOneByParams(jy);
			if (jyls != null) {
				Kpls ls = new Kpls();
				ls.setDjh(jyls.getDjh());
				List<Kpls> kpls = ks.findAllByKpls(ls);
				if (!kpls.isEmpty()) {
					for (Kpls kp : kpls) {
						pdfList.add(kp.getPdfurl());
					}
					SendEmail se = new SendEmail();
					se.sendMail(jyls.getDdh(), gfemail, pdfList, gsdm);
					result.put("success", true);
					result.put("msg", "发送邮件成功");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("failure", true);
			result.put("msg", "发送邮件出现错误: " + e);
		}

		return result;
	}

}
