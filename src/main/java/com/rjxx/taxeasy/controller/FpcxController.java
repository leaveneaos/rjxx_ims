package com.rjxx.taxeasy.controller;

import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.bizcomm.utils.DataOperte;
import com.rjxx.taxeasy.domains.Fpzt;
import com.rjxx.taxeasy.domains.Kpls;
import com.rjxx.taxeasy.domains.Skp;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.service.FpztService;
import com.rjxx.taxeasy.service.KplsService;
import com.rjxx.taxeasy.service.SkpService;
import com.rjxx.taxeasy.service.YhDczdylService;
import com.rjxx.taxeasy.utils.UrlUtils;
import com.rjxx.taxeasy.vo.DczydlVo;
import com.rjxx.taxeasy.vo.Fpcxvo;
import com.rjxx.taxeasy.web.BaseController;
import com.rjxx.utils.HtmlUtils;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/fpcx")
public class FpcxController extends BaseController {

    @Autowired
    private KplsService kplsService;
    @Autowired
    private FpztService ztService;
    @Autowired
    private DataOperte dc;
    @Autowired
    private SkpService skpService;
    @Autowired
    private YhDczdylService yhDczdylService;

    @RequestMapping
    public String index() throws Exception {
        Map params = new HashMap<>();
        List<Fpzt> ztList = ztService.findAllByParams(params);
        if (ztList != null) {
            request.setAttribute("ztList", ztList);
        }
        request.setAttribute("xfList", getXfList());
        request.setAttribute("skpList", getSkpList());
        return "fpcx/index";
    }

    @RequestMapping(value = "/getKplsList")
    @ResponseBody
    public Map<String, Object> getItems(int length, int start, int draw, String ddh, String fphm, String kprqq,
                                        String kprqz, String spmc, String printflag, String gfmc, String fpdm, String fpzt, String fpczlx, String fpzldm,
                                        String xfsh, String sk, String xfmc,boolean loaddata2,String errorReason) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        Pagination pagination = new Pagination();
        pagination.setPageNo(start / length + 1);
        pagination.setPageSize(length);
        String gsdm = getGsdm();
        String xfStr = "";
        List<Xf> xfs = getXfList();
        if (xfs != null) {
            for (int i = 0; i < xfs.size(); i++) {
                int xfid = xfs.get(i).getId();
                if (i == xfs.size() - 1) {
                    xfStr += xfid + "";
                } else {
                    xfStr += xfid + ",";
                }
            }
        }
        String[] xfid = xfStr.split(",");
        if (xfid.length == 0) {
            xfid = null;
        }
        String skpStr = "";
        List<Skp> skpList = getSkpList();
        if (skpList != null) {
            for (int j = 0; j < skpList.size(); j++) {
                int skpid = skpList.get(j).getId();
                if (j == skpList.size() - 1) {
                    skpStr += skpid + "";
                } else {
                    skpStr += skpid + ",";
                }
            }
        }
        String[] skpid = skpStr.split(",");
        if (skpid.length == 0) {
            skpid = null;
        }
        pagination.addParam("gsdm", gsdm);
        pagination.addParam("xfid", xfid);
        pagination.addParam("skpid", skpid);
        pagination.addParam("ddh", ddh);
        pagination.addParam("fpzldm", fpzldm);
        pagination.addParam("fphm", fphm);
        pagination.addParam("kprqq", kprqq);
        pagination.addParam("kprqz", kprqz);
        pagination.addParam("spmc", spmc);
        pagination.addParam("printflag", printflag);
        pagination.addParam("gfmc", gfmc);
        pagination.addParam("fpdm", fpdm);
        pagination.addParam("fpzt", fpzt);
        pagination.addParam("xfmc", xfmc);
        pagination.addParam("fpczlx", fpczlx);
        pagination.addParam("errorReason",errorReason);
        if (null != xfsh && !"".equals(xfsh) && !"-1".equals(xfsh)) {
            pagination.addParam("xfsh", xfsh);
        }
        if (null != sk && !"".equals(sk) && !"-1".equals(sk)) {
            pagination.addParam("sk", sk);
        }
        List<Fpcxvo> ykfpList = kplsService.findByPage(pagination);
        String requestDomain = HtmlUtils.getDomainPath(request);
        for (Fpcxvo fpcxvo : ykfpList) {
            String pdfurl = UrlUtils.convertPdfUrlDomain(requestDomain, fpcxvo.getPdfurl());
            fpcxvo.setPdfurl(pdfurl);
            if(fpcxvo.getSkpid()!=null){
                Skp skp = skpService.findOne(fpcxvo.getSkpid());
                fpcxvo.setKpdmc(skp.getKpdmc());
                fpcxvo.setKpddm(skp.getKpddm());
            }
        }
        int total = pagination.getTotalRecord();
        if(loaddata2){
            result.put("recordsTotal", total);
            result.put("recordsFiltered", total);
            result.put("draw", draw);
            result.put("data", ykfpList);
        }else{
            result.put("recordsTotal", 0);
            result.put("recordsFiltered", 0);
            result.put("draw", draw);
            result.put("data", new ArrayList<>());
        }

        return result;
    }


    /**
     * 获取销方下的有操作权限的开票点
     *
     * @param xfsh
     * @return
     */
    @RequestMapping(value = "/getSkpList")
    @ResponseBody
    public Map getSkpList(String xfsh) {
        Map<String, Object> result = new HashMap<>();
        Integer xfid = null;
        List<Skp> list = new ArrayList<>();
        for (Xf xf : getXfList()) {
            if (xfsh.equals(xf.getXfsh())) {
                xfid = xf.getId();
            }
        }
        for (Skp skp : getSkpList()) {
            if (xfid != null && skp.getXfid().equals(xfid)) {
                list.add(skp);
            }
        }
        result.put("skps", list);
        return result;
    }

    // 文件导出
    @RequestMapping(value = "/exportExcel")
    @ResponseBody
    public Map<String, Object> exportExcel(String ddh, String kprqq, String kprqz, String spmc, String fphm,
                                           String printflag, String gfmc, String fpdm, String fpzt, String fpczlx, String tip, String txt,String kplsh1) throws Exception {
        String gsdm = this.getGsdm();
        String xfStr = "";
        List<Xf> xfs = getXfList();
        if (xfs != null) {
            for (int i = 0; i < xfs.size(); i++) {
                int xfid = xfs.get(i).getId();
                if (i == xfs.size() - 1) {
                    xfStr += xfid + "";
                } else {
                    xfStr += xfid + ",";
                }
            }
        }
        String[] xfid = xfStr.split(",");
        if (xfid.length == 0) {
            xfid = null;
        }
        String skpStr = "";
        List<Skp> skpList = getSkpList();
        if (skpList != null) {
            for (int j = 0; j < skpList.size(); j++) {
                int skpid = skpList.get(j).getId();
                if (j == skpList.size() - 1) {
                    skpStr += skpid + "";
                } else {
                    skpStr += skpid + ",";
                }
            }
        }
        String[] skpid = skpStr.split(",");
        if (skpid.length == 0) {
            skpid = null;
        }

        String []  kplsh ;
        kplsh1 = request.getParameter("kplsh1");
        //ids = ids.substring(0, ids.length() - 1);
        if(kplsh1!=null&&!kplsh1.equals("")){
            kplsh1 = kplsh1.substring(0,kplsh1.length() - 1);
            kplsh = kplsh1.split(",");
        }else {
            kplsh = null;
        }
        Map<String, Object> params = new HashMap<>();
        if (tip == "1") {
            params.put("gfmc", txt);
        } else if (tip == "2") {
            params.put("ddh", txt);
        } else if (tip == "3") {
            params.put("spmc", txt);
        } else if (tip == "4") {
            params.put("xfmc", txt);
        } else if (tip == "5") {
            params.put("fphm", txt);
        } else if (tip == "6") {
            params.put("fpdm", txt);
        }
        params.put("kplsh",kplsh);
        params.put("gsdm", gsdm);
        params.put("xfid", xfid);
        params.put("skpid", skpid);
        params.put("ddh", ddh);
        params.put("fphm", fphm);
        params.put("kprqq", kprqq);
        params.put("kprqz", kprqz);
        params.put("spmc", spmc);
        params.put("printflag", printflag);
        params.put("gfmc", gfmc);
        params.put("fpdm", fpdm);
        params.put("fpzt", fpzt);
        params.put("fpczlx", fpczlx);
        List<Fpcxvo> ykfpList = kplsService.findAllByParams(params);
        Map<String, Object> map = new HashMap<>();
        int yhid = getYhid();
        map.put("yhid", yhid);
        List<DczydlVo> list = yhDczdylService.findAllByParams(map);
        String headers1 = "订单号,操作类型, 发票代码, 发票号码, 价税合计,购方名称,开票日期,发票类型,商品名称,商品金额,商品税额";
        for (DczydlVo yhDczdyl : list) {
            headers1 += "," + yhDczdyl.getZdzwm();
        }
        String[] headers = headers1.split(",");
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("已开发票");
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow(0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        HSSFCell cell = null;
        for (int i = 0; i < headers.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(style);
        }
        Fpcxvo ykfpcx = null;
        for (int i = 0; i < ykfpList.size(); i++) {
            row = sheet.createRow((short) i + 1);
            ykfpcx = ykfpList.get(i);
            row.createCell(0).setCellValue(ykfpcx.getDdh());

            String czlx = ykfpcx.getFpczlxdm();
            if (czlx != null) {
                if ("00".equals(czlx)) {
                    czlx = "不开票";
                } else if ("11".equals(czlx)) {
                    czlx = "正常开具";
                } else if ("12".equals(czlx)) {
                    czlx = "红冲开具";
                } else if ("13".equals(czlx)) {
                    czlx = "纸质发票换开";
                } else if ("21".equals(czlx)) {
                    czlx = "填开作废";
                } else if ("22".equals(czlx)) {
                    czlx = "空白作废";
                }
            }
            row.createCell(1).setCellValue(czlx == null ? "" : czlx);
            row.createCell(2).setCellValue(ykfpcx.getFpdm() == null ? "" : ykfpcx.getFpdm());
            row.createCell(3).setCellValue(ykfpcx.getFphm() == null ? "" : ykfpcx.getFphm());
            row.createCell(4).setCellValue(ykfpcx.getJshj() == null ? "0.00" : String.format("%.2f", ykfpcx.getJshj()));
            row.createCell(5).setCellValue(ykfpcx.getGfmc() == null ? "" : ykfpcx.getGfmc());
            row.createCell(6).setCellValue(ykfpcx.getKprq() == null ? "" : ykfpcx.getKprq());
            row.createCell(7).setCellValue(ykfpcx.getFpzlmc() == null ? "" : ykfpcx.getFpzlmc());
            row.createCell(8).setCellValue(ykfpcx.getSpmc() == null ? "" : ykfpcx.getSpmc());
            row.createCell(9).setCellValue(ykfpcx.getSpje().toString() == null ? "" : ykfpcx.getSpje().toString());
            row.createCell(10).setCellValue(ykfpcx.getSpse().toString() == null ? "" : ykfpcx.getSpse().toString());


            int k = 11;
            for (DczydlVo dczydlVo : list) {
                if ("gfsjh".equals(dczydlVo.getZddm())) {
                    row.createCell(k).setCellValue(ykfpcx.getGfsjh() == null ? "" : ykfpcx.getGfsjh());
                } else if ("jylsh".equals(dczydlVo.getZddm())) {
                    row.createCell(k).setCellValue(ykfpcx.getJylsh() == null ? "" : ykfpcx.getJylsh());
                } else if ("jylssj".equals(dczydlVo.getZddm())) {
                    row.createCell(k).setCellValue(sdf1.format(ykfpcx.getJylssj()));
                } else if ("xfsh".equals(dczydlVo.getZddm())) {
                    row.createCell(k).setCellValue(ykfpcx.getXfsh() == null ? "" : ykfpcx.getXfsh());
                } else if ("xfmc".equals(dczydlVo.getZddm())) {
                    row.createCell(k).setCellValue(ykfpcx.getXfmc() == null ? "" : ykfpcx.getXfmc());
                } else if ("xfyh".equals(dczydlVo.getZddm())) {
                    row.createCell(k).setCellValue(ykfpcx.getXfyh() == null ? "" : ykfpcx.getXfyh());
                } else if ("xfyhzh".equals(dczydlVo.getZddm())) {
                    row.createCell(k).setCellValue(ykfpcx.getXfyhzh() == null ? "" : ykfpcx.getXfyhzh());
                } else if ("xfdz".equals(dczydlVo.getZddm())) {
                    row.createCell(k).setCellValue(ykfpcx.getXfdz() == null ? "" : ykfpcx.getXfdz());
                } else if ("xfdh".equals(dczydlVo.getZddm())) {
                    row.createCell(k).setCellValue(ykfpcx.getXfdh() == null ? "" : ykfpcx.getXfdh());
                } else if ("xfyb".equals(dczydlVo.getZddm())) {
                    row.createCell(k).setCellValue(ykfpcx.getXfyb() == null ? "" : ykfpcx.getXfyb());
                } else if ("gfsh".equals(dczydlVo.getZddm())) {
                    row.createCell(k).setCellValue(ykfpcx.getGfsh() == null ? "" : ykfpcx.getGfsh());
                } else if ("gfyh".equals(dczydlVo.getZddm())) {
                    row.createCell(k).setCellValue(ykfpcx.getGfyh() == null ? "" : ykfpcx.getGfyh());
                } else if ("skpid".equals(dczydlVo.getZddm())) {
                    if (null != ykfpcx.getSkpid()) {
                        Skp skp = skpService.findOne(ykfpcx.getSkpid());
                        row.createCell(k).setCellValue(skp.getKpdmc());
                    } else {
                        row.createCell(k).setCellValue("");
                    }
                } else if ("gfyhzh".equals(dczydlVo.getZddm())) {
                    row.createCell(k).setCellValue(ykfpcx.getGfyhzh() == null ? "" : ykfpcx.getGfyhzh());
                } else if ("gfdz".equals(dczydlVo.getZddm())) {
                    row.createCell(k).setCellValue(ykfpcx.getGfdz() == null ? "" : ykfpcx.getGfdz());
                } else if ("gfdh".equals(dczydlVo.getZddm())) {
                    row.createCell(k).setCellValue(ykfpcx.getGfdh() == null ? "" : ykfpcx.getGfdh());
                } else if ("gfemail".equals(dczydlVo.getZddm())) {
                    row.createCell(k).setCellValue(ykfpcx.getGfemail() == null ? "" : ykfpcx.getGfemail());
                } else if ("bz".equals(dczydlVo.getZddm())) {
                    row.createCell(k).setCellValue(ykfpcx.getBz() == null ? "" : ykfpcx.getBz());
                } else if ("skr".equals(dczydlVo.getZddm())) {
                    row.createCell(k).setCellValue(ykfpcx.getSkr() == null ? "" : ykfpcx.getSkr());
                } else if ("kpr".equals(dczydlVo.getZddm())) {
                    row.createCell(k).setCellValue(ykfpcx.getKpr() == null ? "" : ykfpcx.getKpr());
                } else if ("fhr".equals(dczydlVo.getZddm())) {
                    row.createCell(k).setCellValue(ykfpcx.getFhr() == null ? "" : ykfpcx.getFhr());
                } else if ("ssyf".equals(dczydlVo.getZddm())) {
                    row.createCell(k).setCellValue(ykfpcx.getSsyf() == null ? "" : ykfpcx.getSsyf());
                } else if ("yfphm".equals(dczydlVo.getZddm())) {
                    row.createCell(k).setCellValue(ykfpcx.getYfphm() == null ? "" : ykfpcx.getYfphm());
                } else if ("yfpdm".equals(dczydlVo.getZddm())) {
                    row.createCell(k).setCellValue(ykfpcx.getYfpdm() == null ? "" : ykfpcx.getYfpdm());
                } else if ("ykpjshj".equals(dczydlVo.getZddm())) {
                    row.createCell(k).setCellValue(
                            ykfpcx.getYkpjshj() == null ? "0.00" : String.format("%.2f", ykfpcx.getYkpjshj()));
                } else if ("tqm".equals(dczydlVo.getZddm())) {
                    row.createCell(k).setCellValue(ykfpcx.getTqm() == null ? "" : ykfpcx.getTqm());
                } else if ("kpddm".equals(dczydlVo.getZddm())) {
                    if (null != ykfpcx.getSkpid()) {
                        Skp skp = skpService.findOne(ykfpcx.getSkpid());
                        row.createCell(k).setCellValue(skp.getKpddm());
                    } else {
                        row.createCell(k).setCellValue("");
                    }
                } else if ("hjje".equals(dczydlVo.getZddm())) {
                    row.createCell(k)
                            .setCellValue(ykfpcx.getHjje() == null ? "0.00" : String.format("%.2f", ykfpcx.getHjje()));
                } else if ("hjse".equals(dczydlVo.getZddm())) {
                    row.createCell(k)
                            .setCellValue(ykfpcx.getHjse() == null ? "0.00" : String.format("%.2f", ykfpcx.getHjse()));
                }
                k++;
            }
            // row.createCell(4).setCellValue(ykfpcx.getDdh() == null ? "" :
            // ykfpcx.getDdh());

			/*
             * row.createCell(6).setCellValue(ykfpcx.getSpmc() == null ? "" :
			 * ykfpcx.getSpmc());// 主要商品名称 row.createCell(7).setCellValue ("");
			 * row.createCell(8).setCellValue(ykfpcx.getHjse() == null ? "0.00"
			 * : String.format("%.2f", ykfpcx.getHjse()));
			 * row.createCell(9).setCellValue(ykfpcx.getHjje() == null ? "0.00"
			 * : String.format("%.2f", ykfpcx.getHjje()));
			 * 
			 * row.createCell(11).setCellValue(ykfpcx.getHzyfphm() == null ? ""
			 * : ykfpcx.getHzyfphm()); String hkfp = ykfpcx.getFpzt(); if (hkfp
			 * != null) { if ("被换开".equals(hkfp)) { hkfp = "是"; } else { hkfp =
			 * "否"; } } row.createCell(12).setCellValue(hkfp == null ? "" :
			 * hkfp); row.createCell(13).setCellValue(ykfpcx.getKpr() == null ?
			 * "" : ykfpcx.getKpr());
			 */
            // row.createCell(14).setCellValue(ykfpcx.getKprq() == null ? "" :
            // sdf.format(ykfpcx.getKprq()));

        }
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String filename = timeFormat.format(new Date()) + ".xls";
        response.setContentType("application/ms-excel;charset=UTF-8");
        response.setHeader("Content-Disposition",
                "attachment;filename=".concat(String.valueOf(URLEncoder.encode(filename, "UTF-8"))));
        OutputStream out = response.getOutputStream();
        wb.write(out);
        out.close();
        return null;
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public void update(String ids) throws Exception {
        Map params = new HashMap<>();
        String[] id = ids.split(",");
        for (int i = 0; i < id.length; i++) {
            params.put("id", id[i]);
            kplsService.update(params);
        }
    }

    // 打印
    @RequestMapping(value = "/printSingle")
    public String printSingle(int kplsh) throws Exception {
        Map params = new HashMap<>();
        params.put("kplsh", kplsh);
        List<Kpls> kplsList = kplsService.printSingle(params);
        List<Kpls> kpList = new ArrayList<Kpls>();
        if (kplsList != null) {
            String requestDomain = HtmlUtils.getDomainPath(request);
            for (Kpls kpls : kplsList) {
                String pdfurl = kpls.getPdfurl().replace(".pdf", ".jpg");
                pdfurl = UrlUtils.convertPdfUrlDomain(requestDomain, pdfurl);
                kpls.setPdfurl(pdfurl);
                kpList.add(kpls);
            }
        }
        request.setAttribute("kpList", kpList);
        return "fpcx/printandview";
    }

    // 批量打印
    @RequestMapping(value = "/printmany")
    public String printSingle(String ids) throws Exception {
        Map params = new HashMap<>();
        ids = ids.substring(0, ids.length() - 1);
        String[] idArr = ids.split(",");
        params.put("id", idArr);
        List<Fpcxvo> kplsList = kplsService.printmany(params);
        List<Fpcxvo> kpList = new ArrayList<Fpcxvo>();
        if (kplsList != null) {
            String requestDomain = HtmlUtils.getDomainPath(request);
            for (Fpcxvo kpls : kplsList) {
                String pdfurl = kpls.getPdfurl().replace(".pdf", ".jpg");
                pdfurl = UrlUtils.convertPdfUrlDomain(requestDomain, pdfurl);
                kpls.setPdfurl(pdfurl);
                kpList.add(kpls);
            }
        }
        request.setAttribute("kpList", kpList);
        request.setAttribute("num", kpList.size());
        return "fpcx/printandview";
    }

    @RequestMapping(value = "/ck")
    @ResponseBody
    public Map<String, Object> qrck(int kplsh, int djh, String yfpdm, String yfphm, String jylsh) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        Map map = new HashMap<>();
        map.put("kplsh", kplsh);
        int yhid = getYhid();
        Fpcxvo cxvo = kplsService.selectMonth(map);
        try {
            dc.ckSaveJyls(kplsh, djh, yhid);
            dc.saveLog(djh, "01", "0", "电子发票服务平台重开操作", "已向服务端发送重开请求", yhid, cxvo.getXfsh(), jylsh);
            result.put("success", true);
            result.put("msg", "重开请求提交成功，请注意查看操作结果！");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("msg", "后台出现错误: " + e.getMessage());
            dc.saveLog(djh, "92", "1", "", "电子发票服务平台重开请求失败!", 2, cxvo.getXfsh(), jylsh);
        }

        return result;
    }
}
