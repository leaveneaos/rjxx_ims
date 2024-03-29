package com.rjxx.taxeasy.controller;

import com.rjxx.taxeasy.bizcomm.utils.DataOperte;
import com.rjxx.taxeasy.domains.*;
import com.rjxx.taxeasy.service.*;
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

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
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
    @Autowired
    private PdfRulesService pdfRulesService;

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
                                        String kprqz,String kprqq2,String kprqz2, String spmc, String printflag, String gfmc, String fpdm, String fpzt, String fpczlx, String fpzldm,
                                        String xfsh, String sk, String xfmc,String jzjtzt,String check,boolean loaddata2,String errorReason) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        if(loaddata2){
            //Pagination pagination = new Pagination();
            //pagination.setPageNo(start / length + 1);
            //pagination.setPageSize(length);
            Map maps = new HashMap();
            maps.put("start", start);
            maps.put("length", length);
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
            if (null != check && check.equals("1")) {
                maps.put("lrry", getYhid());
            }
            maps.put("gsdm", gsdm);
            maps.put("xfid", xfid);
            maps.put("skpid", skpid);
            maps.put("ddh", ddh);
            maps.put("fpzldm", fpzldm);
            maps.put("fphm", fphm);
            maps.put("kprqq", kprqq);
            maps.put("kprqz", kprqz);
            maps.put("kprqq2", kprqq2);
            maps.put("kprqz2", kprqz2);
            //maps.put("spmc", spmc);
            maps.put("printflag", printflag);
            maps.put("gfmc", gfmc);
            maps.put("fpdm", fpdm);
            maps.put("fpzt", fpzt);
            maps.put("xfmc", xfmc);
            maps.put("fpczlx", fpczlx);
            maps.put("jzjtzt", jzjtzt);
            maps.put("errorReason", errorReason);
            if (null != xfsh && !"".equals(xfsh) && !"-1".equals(xfsh)) {
                maps.put("xfsh", xfsh);
            }
            if (null != sk && !"".equals(sk) && !"-1".equals(sk)) {
                maps.put("sk", sk);
            }
            List<Fpcxvo> ykfpList = kplsService.findByPage2(maps);
            String requestDomain = HtmlUtils.getDomainPath(request);
            for (Fpcxvo fpcxvo : ykfpList) {
                String pdfurl = UrlUtils.convertPdfUrlDomain(requestDomain, fpcxvo.getPdfurl());
                if (pdfurl != null && !"".equals(pdfurl)) {
                    int i = pdfurl.indexOf("/", 10);
                    String pdfurlbasepath = pdfurl.substring(0, i);
                    pdfurl = pdfurl.substring(i);
                    List<PdfRules> pdfrules = pdfRulesService.findAllByParams(new HashMap());
                    for (PdfRules pdfRules : pdfrules) {
                        File pdffile = new File(pdfRules.getPdfPath() + pdfurl);
                        if (pdffile.exists()) {
                            logger.info("PDF文件存在");
                            pdfurl = pdfRules.getNginxPdfurl() + pdfurl;
                        }
                    }
                }
                fpcxvo.setPdfurl(pdfurl);
                if (pdfurl != null && !"".equals(pdfurl)) {
                    String filename = pdfurl.substring(pdfurl.lastIndexOf("/") + 1, pdfurl.length());
                    fpcxvo.setFilename(filename);
                }
                if (fpcxvo.getSkpid() != null) {
                    Skp skp = skpService.findOne(fpcxvo.getSkpid());
                    fpcxvo.setKpdmc(skp.getKpdmc());
                    fpcxvo.setKpddm(skp.getKpddm());
                }
            }

            // String tmp = (String)request.getSession().getAttribute("total");
            int total;
            if (0 == start) {
                total = kplsService.findTotal(maps);
                request.getSession().setAttribute("total", total);
            } else {
                total = (Integer) request.getSession().getAttribute("total");
                //request.getSession().getAttribute("total");
            }

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
    @RequestMapping(value = "/exportExcel1")
    @ResponseBody
    public Map<String, Object> exportExcel1(String w_kprqq,String w_kprqz, String tip, String txt,String time1,String kplsh,String fileFlag) throws Exception {
       return exportExcel(null,w_kprqq,w_kprqz,time1,null,null,null,null,null,null,null,tip,txt,kplsh,null,null,fileFlag);
    }

    // 文件导出 fileFlag 0 --excle  1---txt
    @RequestMapping(value = "/exportExcel")
    @ResponseBody
    public Map<String, Object> exportExcel(String ddh, String kprqq, String kprqz, String time2,String spmc, String fphm,
                                           String printflag,String gfmc, String fpdm, String fpzt, String fpczlx, String tip, String txt,String kplsh1,String jzjtzt,String s_check,String fileFlag) throws Exception {
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

        String[] kplsh;
        if (null == kplsh1 || kplsh1.equals("")) {

            kplsh1 = request.getParameter("kplsh1");
        }
        //ids = ids.substring(0, ids.length() - 1);
        if (kplsh1 != null && !kplsh1.equals("")) {
            kplsh1 = kplsh1.substring(0, kplsh1.length() - 1);
            kplsh = kplsh1.split(",");
        } else {
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
        params.put("kplsh", kplsh);
        params.put("gsdm", gsdm);
        params.put("xfid", xfid);
        params.put("skpid", skpid);
        params.put("ddh", ddh);
        if (null != s_check && s_check.equals("1")) {
            params.put("lrry", getYhid());
        }
        params.put("jzjtzt", jzjtzt);
        params.put("fphm", fphm);
        if (time2.equals("1")) {
            params.put("kprqq", kprqq);
            params.put("kprqz", kprqz);
        } else {
            //  开票日期
            params.put("kprqq2", kprqq);
            params.put("kprqz2", kprqz);
        }

        params.put("spmc", spmc);
        params.put("printflag", printflag);
        params.put("gfmc", gfmc);
        params.put("fpdm", fpdm);
        params.put("fpzt", fpzt);
        params.put("fpczlx", fpczlx);
        List<Fpcxvo> ykfpList = kplsService.findAllByParams2(params);

        //分文件类型
        if (fileFlag.equals("1")) {


            SimpleDateFormat timeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            String filename = timeFormat.format(new Date()) + ".txt";
            response.setContentType("text/plain;charset=UTF-8");
            response.setHeader("Content-Disposition",
                    "attachment;filename=".concat(String.valueOf(URLEncoder.encode(filename, "UTF-8"))));
            OutputStream out = null;
            BufferedOutputStream buffer = null;
            StringBuffer write = null;
            StringBuffer mxWrite = null;
            String enter = "\r\n";
            //分隔符
            String fgf = "~~";
            //发票序号
            String fpxh = "//发票";
            Fpcxvo fpcxvo = new Fpcxvo();

            try {
                out = response.getOutputStream();
                buffer = new BufferedOutputStream(out);
                write = new StringBuffer();
                //日期处理
                String beginTime = dateFromat(kprqq);
                String endTime = dateFromat(kprqz);
                //map 用于处理多笔明细fphmMaps 用于存储子发票
                Map fphmMaps = new HashMap();
                Map zfphs = new HashMap();

                //发票数目
//            for (Fpcxvo fpcxvo1 : ykfpList) {
//                String fphm1 = fpcxvo1.getFphm();
//                zfphs.put(fphm1, fphm1);
//            }
                //总金额
                Map bhsjeMap = new HashMap();
                //税额
                Map zseMap = new HashMap();
                int n1 = 1;
                double je1 = 0d;
                double se1 = 0d;
                for (Fpcxvo fpcxvo1 : ykfpList) {
//                    String fphm1 = fpcxvo1.getFphm();
                    Integer kplsh2 = fpcxvo1.getKplsh();
                    je1 = fpcxvo1.getSpje();
                    se1 = fpcxvo1.getSpse();
                    if (zfphs.containsKey(kplsh2)) {
                        Integer num = (Integer) zfphs.get(kplsh2);
                        //jine
                        double je = (double) bhsjeMap.get(kplsh2);
                        double se = (double) zseMap.get(kplsh2);
                        zseMap.put(kplsh2, se1 + se);
                        zfphs.put(kplsh2, (num.intValue() + 1));
                        bhsjeMap.put(kplsh2, je1 + je);
                    } else {
                        zfphs.put(kplsh2, n1);
                        bhsjeMap.put(kplsh2, je1);
                        zseMap.put(kplsh2, se1);
                    }

                }

                //common
                write.append("SJJK0201" + fgf + "已开发票传出");
                write.append(enter);
                write.append(zfphs.size() + fgf + beginTime + fgf + endTime);
                write.append(enter);
                for (int i = 1, n = 1, size = ykfpList.size(); i <= size; i++, n++) {
                    fpcxvo = ykfpList.get(i - 1);

                    //开票流水号   判断是否含有多笔明细
//                    String fph = fpcxvo.getFphm();
                    Integer kpls = fpcxvo.getKplsh();
                    if (fphmMaps.containsKey(kpls)) {
                        mxWrite = new StringBuffer();
                        //折扣行标记
                        //折扣行标记 0代表正常行
                        mxWrite.append(fpcxvo.getFphxz().equals("0")?"0":"1");
                        mxWrite.append(fgf + fpcxvo.getSpmc());
                        mxWrite.append(fgf + (null == fpcxvo.getSpggxh() || "".equals(fpcxvo.getSpggxh()) ? "" : fpcxvo.getSpggxh()));
                        mxWrite.append(fgf + (null == fpcxvo.getSpdw() || "".equals(fpcxvo.getSpdw()) ? "" : fpcxvo.getSpdw()));
                        mxWrite.append(fgf + (null == fpcxvo.getSps() || "".equals(fpcxvo.getSps()) ? "" : fpcxvo.getSps()));
                        mxWrite.append(fgf + (null == fpcxvo.getSpje() || "".equals(fpcxvo.getSpje()) ? "" : fpcxvo.getSpje()));
                        mxWrite.append(fgf + (null == fpcxvo.getSpsl() || "".equals(fpcxvo.getSpsl()) ? "" : fpcxvo.getSpsl()));
                        mxWrite.append(fgf + (null == fpcxvo.getSpse() || "".equals(fpcxvo.getSpse()) ? "" : fpcxvo.getSpse()));
                        mxWrite.append(fgf + (null == fpcxvo.getSpdj() || "".equals(fpcxvo.getSpdj()) ? "" : fpcxvo.getSpdj()));
                        mxWrite.append(fgf + "1");
                        mxWrite.append(fgf + "");

                        write.append(mxWrite);
                        write.append(enter);
                        n--;
                        continue;

                    } else {
                        fphmMaps.put(kpls, kpls);
                    }
                    write.append(fpxh + n);
                    write.append(enter);

                    //两个标记一个种类
                    //作废标志
                    String zfbj = null;
                    zfbj = fpcxvo.getFpztdm();
                    if (null != zfbj && !"".equals(zfbj)) {
                        if (zfbj.equals("00")) {
                            zfbj = "0";
                        }
                        if (zfbj.equals("08")) {
                            zfbj = "1";
                        }

                    }
                    //清单标志
                    String sfdyqd = null;
                    sfdyqd = fpcxvo.getSfdyqd();
                    if (null != sfdyqd && !"".equals(sfdyqd)) {
                        if (sfdyqd.equals("1")) {
                            sfdyqd = "1";
                        }

                    } else {
                        sfdyqd = "0";
                    }
                    //发票种类
                    String fpzl = null;
                    fpzl = fpcxvo.getFpzldm();
                    if (null != fpzl && !"".equals(fpzl)) {
                        if (fpzl.equals("01")) {
                            fpzl = "0";
                        }
                        if (fpzl.equals("02") || fpzl.equals("12")) {
                            fpzl = "2";
                        }

                    }
                    write.append(zfbj + fgf + sfdyqd);
                    write.append(fgf + fpzl);
                    //"税务月份"20180725
                    String kprq ="";
                    String month ="";
                    if (null != fpcxvo.getKprq() && !"".equals(fpcxvo.getKprq())){
                        kprq = dateFromat(fpcxvo.getKprq());
                        month = kprq.substring(4, 6);
                    }


                    //发票类别代码-发票代码
                    write.append(fgf + (null == fpcxvo.getFpdm() || "".equals(fpcxvo.getFpdm()) ? "" : fpcxvo.getFpdm().trim()));
                    //发票号
                    write.append(fgf + kpls);
                    //商品明细行数
                    write.append(fgf + zfphs.get(kpls));
                    //开票日期 月份
                    write.append(fgf + kprq + fgf + month);
                    //销售单据
                    write.append(fgf + fpcxvo.getDjh());
                    //不含税金额
                    write.append(fgf + bhsjeMap.get(kpls));
                    //税率
                    write.append(fgf + fpcxvo.getSpsl());
                    //税额
                    write.append(fgf + zseMap.get(kpls));
                    //购方信息
                    write.append(fgf + fpcxvo.getGfmc() + fgf + fpcxvo.getGfsh());
                    write.append(fgf + (null == fpcxvo.getGfdz() || "".equals(fpcxvo.getGfdz()) ? "" : fpcxvo.getGfdz().trim()));
                    write.append(fgf + (null == fpcxvo.getGfyh() || "".equals(fpcxvo.getGfyh()) ? "" : fpcxvo.getGfyh().trim()));
                    //销方信息fpcxvo.getGfyh(
                    write.append(fgf + (null == fpcxvo.getXfmc() || "".equals(fpcxvo.getXfmc()) ? "" : fpcxvo.getXfmc().trim()));
                    write.append(fgf + (null == fpcxvo.getXfsh() || "".equals(fpcxvo.getXfsh()) ? "" : fpcxvo.getXfsh().trim()));
                    write.append(fgf + (null == fpcxvo.getXfdz() || "".equals(fpcxvo.getXfdz()) ? "" : fpcxvo.getXfdz().trim()));
                    //电话
                    write.append(null == fpcxvo.getXfdh() || "".equals(fpcxvo.getXfdh()) ? "" : fpcxvo.getXfdh().trim());
                    write.append(fgf + (null == fpcxvo.getXfyh() || "".equals(fpcxvo.getXfyh()) ? "" : fpcxvo.getXfyh().trim()));
                    write.append(null == fpcxvo.getXfyhzh() || "".equals(fpcxvo.getXfyhzh()) ? "" : fpcxvo.getXfyhzh().trim());
                    //备注
                    write.append(fgf + (null == fpcxvo.getBz() || "".equals(fpcxvo.getBz()) ? "" : fpcxvo.getBz().trim()));
                    //几个人员
                    write.append(fgf + (null == fpcxvo.getKpr() || "".equals(fpcxvo.getKpr()) ? "" : fpcxvo.getKpr().trim()));
                    write.append(fgf + fgf);
                    //换行
                    write.append(enter);
                    //明细
                    //折扣行标记 0代表正常行
                    write.append(fpcxvo.getFphxz().equals("0")?"0":"1");
                    write.append(fgf + fpcxvo.getSpmc());
                    write.append(fgf + (null == fpcxvo.getSpggxh() || "".equals(fpcxvo.getSpggxh()) ? "" : fpcxvo.getSpggxh()));
                    write.append(fgf + (null == fpcxvo.getSpdw() || "".equals(fpcxvo.getSpdw()) ? "" : fpcxvo.getSpdw()));
                    write.append(fgf + (null == fpcxvo.getSps() || "".equals(fpcxvo.getSps()) ? "" : fpcxvo.getSps()));
                    write.append(fgf + (null == fpcxvo.getSpje() || "".equals(fpcxvo.getSpje()) ? "" : fpcxvo.getSpje()));
                    write.append(fgf + (null == fpcxvo.getSpsl() || "".equals(fpcxvo.getSpsl()) ? "" : fpcxvo.getSpsl()));
                    write.append(fgf + (null == fpcxvo.getSpse() || "".equals(fpcxvo.getSpse()) ? "" : fpcxvo.getSpse()));
                    write.append(fgf + (null == fpcxvo.getSpdj() || "".equals(fpcxvo.getSpdj()) ? "" : fpcxvo.getSpdj()));
                    write.append(fgf + "1");
                    write.append(fgf + "");
                    write.append(enter);
                }
                buffer.write(write.toString().getBytes("gbk"));
                buffer.flush();
                buffer.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    buffer.close();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        if (fileFlag.equals("0")) {


            Map<String, Object> map = new HashMap<>();
            int yhid = getYhid();
            map.put("yhid", yhid);
            List<DczydlVo> list = yhDczdylService.findAllByParams(map);
            String headers1 = "订单号,操作类型, 发票代码, 发票号码, 价税合计,购方名称,开票日期,发票类型,商品名称,商品数量,商品单价,商品金额,商品税率,商品税额";
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
                //新增数量，单价列
                row.createCell(9).setCellValue(ykfpcx.getSps() == null ? "" : ykfpcx.getSps().toString());
                row.createCell(10).setCellValue(ykfpcx.getSpdj() == null ? "" : ykfpcx.getSpdj().toString());
                row.createCell(11).setCellValue(ykfpcx.getSpje() == null ? "" : ykfpcx.getSpje().toString());
                row.createCell(12).setCellValue(ykfpcx.getSpsl() == null ? "" : String.valueOf(ykfpcx.getSpsl()));
                row.createCell(13).setCellValue(ykfpcx.getSpse() == null ? "" : ykfpcx.getSpse().toString());


                int k = 14;
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
                                ykfpcx.getYkpjshj() == null ? "0.00" : String.format("%.2f", ykfpcx.getSpje()+ykfpcx.getSpse()));
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
                    } else if ("pdfurl".equals(dczydlVo.getZddm())) {
                        row.createCell(k).setCellValue(ykfpcx.getPdfurl());
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
                if(pdfurl != null && !"".equals(pdfurl)){
                    int i=pdfurl.indexOf("/",10);
                    String pdfurlbasepath=pdfurl.substring(0,i);
                    pdfurl =pdfurl.substring(i);
                    List <PdfRules>pdfrules=pdfRulesService.findAllByParams(new HashMap());
                    for(PdfRules pdfRules:pdfrules){
                        File pdffile =new File(pdfRules.getPdfPath() +pdfurl);
                        if(pdffile.exists()) {
                            logger.info("PDF文件存在");
                            pdfurl=pdfRules.getNginxPdfurl()+pdfurl;
                        }
                    }
                }
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
                if(pdfurl != null && !"".equals(pdfurl)){
                    int i=pdfurl.indexOf("/",10);
                    String pdfurlbasepath=pdfurl.substring(0,i);
                    pdfurl =pdfurl.substring(i);
                    List <PdfRules>pdfrules=pdfRulesService.findAllByParams(new HashMap());
                    for(PdfRules pdfRules:pdfrules){
                        File pdffile =new File(pdfRules.getPdfPath() +pdfurl);
                        if(pdffile.exists()) {
                            logger.info("PDF文件存在");
                            pdfurl=pdfRules.getNginxPdfurl()+pdfurl;
                        }
                    }
                }
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

    /**
     *日期处理yyyy-MM-dd to yyyyMMdd
     * @param oldDateStr
     * @return
     * @throws Exception
     */
    public String dateFromat(String oldDateStr)throws Exception{
        SimpleDateFormat timeFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        Date str = (Date)timeFormat1.parse(oldDateStr);
        //日期格式
        SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
        String newDateStr =format1.format(str);
        return  newDateStr;

    }
}
