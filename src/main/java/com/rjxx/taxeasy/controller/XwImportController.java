package com.rjxx.taxeasy.controller;

import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.bizcomm.utils.DiscountDealUtil;
import com.rjxx.taxeasy.bizcomm.utils.GetXfxx;
import com.rjxx.taxeasy.domains.*;
import com.rjxx.taxeasy.filter.SystemControllerLog;
import com.rjxx.taxeasy.service.*;
import com.rjxx.taxeasy.vo.JyxxsqVO;
import com.rjxx.taxeasy.vo.Spbm;
import com.rjxx.taxeasy.vo.Spvo;
import com.rjxx.taxeasy.web.BaseController;
import com.rjxx.time.TimeUtil;
import com.rjxx.utils.BeanConvertUtils;
import com.rjxx.utils.ExcelUtil;
import com.rjxx.utils.StringUtils;
import com.rjxx.utils.Tools;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/xwImport")
public class XwImportController extends BaseController {

    @Autowired
    private JyxxsqService jyxxsqservice;


    @Autowired
    private SpvoService spvoService;

    @Autowired
    DrmbService drmbService;

    @Autowired
    private XfService xfService;

    @Autowired
    private SkpService skpservice;


    @Autowired
    private SmService smService;

    @Autowired
    private DiscountDealUtil discountDealUtil;


    @Autowired
    private SpbmService spbmService;


    /**
     * 导入excel数据
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/xwImportExcel", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public Map xwImportExcel(MultipartFile xwimportFile, /*Integer mb, Integer mrmb,*/ String xw_xfsh, Integer xw_skp)
            throws Exception {
        Map<String, Object> result = new HashMap<>();
        java.sql.Timestamp allTime = TimeUtil.getNowDate();
        if (xwimportFile == null || xwimportFile.isEmpty()) {
            result.put("success", false);
            result.put("message", "请选择要导入的文件");
            return result;
        }
        List<List> resultList = ExcelUtil.exportListFromExcel(xwimportFile.getInputStream(),
                FilenameUtils.getExtension(xwimportFile.getOriginalFilename()), 0);
        if (resultList.size() < 2) {
            result.put("success", false);
            result.put("message", "行数少于2行，没有数据");
            return result;
        }
        try {
            Map msgMap = processExcelList(resultList, xw_xfsh, xw_skp, allTime);
            if (!"".equals(msgMap.get("msg").toString())) {
                result.put("success", false);
                result.put("message", msgMap.get("msg").toString());
                return result;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "excel表头不一致(正确表头：日期\t年级\t班级\t姓名\t学费\t住宿费\t小计\t备注字段)，请检查表头使再导入！");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "导入出错");
            return result;
        }

        result.put("success", true);
        result.put("allTime", allTime);
        result.put("count", resultList.size() - 1);
        return result;
    }

    /**
     * 导入字段映射
     */
    private static Map<String, String> importColumnMapping = new HashMap<String, String>() {
        {
            put("日期", "日期");
            put("年级", "年级");
            put("班级", "班级");
            put("姓名", "姓名");
            put("学费", "学费");
            put("住宿费", "住宿费");
            put("小计", "小计");
            put("备注字段", "备注字段");
        }
    };

    @Value("${tax-amount.wc:}")
    private String sewc;

    // 金额，税率，税额校验
    private boolean checkWC(double je, double sl, double se) {
        boolean flate = false;
        double wc = Double.parseDouble(sewc);
        double cfwc = sub(mul(sub(je, se), sl), se);
        if (Math.abs(cfwc) <= wc) {
            flate = true;
        }
        return flate;
    }

    /**
     * 提供精确的减法运算。
     *
     * @param value1 被减数
     * @param value2 减数
     * @return 两个参数的差
     */
    private Double sub(Number value1, Number value2) {
        if (value1 == null) {
            value1 = 0;
            return null;
        }
        if (value2 == null) {
            value2 = 0;
            return null;
        }
        BigDecimal b1 = new BigDecimal(Double.toString(value1.doubleValue()));
        BigDecimal b2 = new BigDecimal(Double.toString(value2.doubleValue()));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param value1 被乘数
     * @param value2 乘数
     * @return 两个参数的积
     */
    private Double mul(Number value1, Number value2) {
        if (value1 == null) {
            value1 = 0;
            return null;
        }
        if (value2 == null) {
            value2 = 0;
            return null;
        }
        BigDecimal b1 = new BigDecimal(Double.toString(value1.doubleValue()));
        BigDecimal b2 = new BigDecimal(Double.toString(value2.doubleValue()));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算。 当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入。
     *
     * @param dividend 被除数
     * @param divisor  除数
     * @param scale    表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public Double div(Double dividend, Double divisor, Integer scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        if (dividend == null) {
            return null;
        }
        if (divisor == null || divisor == 0) {
            return null;
        }
        BigDecimal b1 = new BigDecimal(Double.toString(dividend));
        BigDecimal b2 = new BigDecimal(Double.toString(divisor));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 处理excel的记录
     *
     * @param dataList
     * @throws Exception
     */
    private Map processExcelList(List<List> dataList, String xfsh1, Integer skpid, java.sql.Timestamp allTime) throws Exception {

        // 数据的校验
        String msgg = "";
        String msg = "";
        Map result = new HashMap();
        double zjshj = 0.0; //导入明细的总价税合计
        int yhid = this.getYhid();

        Map<String, String> cnEnExcelColumnMap = new HashMap<>();
        cnEnExcelColumnMap.putAll(importColumnMapping);
        Map<String, String> pzMap = new HashMap<>();
        pzMap.put("jylsh","auto");
        pzMap.put("ddh","auto");

        // 找到excel中有效的字段
        List titleList = dataList.get(0);
        boolean flag1;
        for (String str : cnEnExcelColumnMap.values()) {
            flag1 = false;
            for (Object obj : titleList) {
                String tmp = (String) obj;
                if ("".equals(str) || (str.equals(tmp))) {
                    flag1 = true;
                    break;
                }
            }
            if (!flag1) {
                msgg = "excel表格中没有“" + str + "”列，请修改！";
                msg += msgg;
            }
        }
        if (!"".equals(msg)) {
            result.put("msg", msg);
            result.put("jshj", zjshj);
            return result;
        }

        // 字段的顺序
        int columnIndex = 0;
        Map<String, Integer> columnIndexMap = new HashMap<>();
        String gsdm = this.getGsdm();
        for (Object obj : titleList) {
            String columnName = (String) obj;
            // if (cnEnExcelColumnMap.containsKey(columnName)) {
            columnIndexMap.put(columnName, columnIndex);
            // }
            columnIndex++;
        }
        // 获取所有的销方
        List<Xf> xfList = this.getXfList();
        List<Skp> skpList = this.getSkpList();

        // 获取导入销方
        Xf xf = null;
        for (Xf x : xfList) {
            if (x.getXfsh().equals(xfsh1)) {
                xf = x;
                break;
            }
        }

        Skp skp = null;
        for (Skp x : skpList) {
            if (x.getId().equals(skpid)) {
                skp = x;
                break;
            }
        }
        List<Jyxxsq> jyxxsqList = new ArrayList<>();
        List<Jymxsq> mxList = new ArrayList<>();
        Integer num = 1;
        for (int k = 1; k < dataList.size(); k++) {
            List row = dataList.get(k);
            Jyxxsq jyxxsq = new Jyxxsq();
            jyxxsq.setGsdm(gsdm);
            jyxxsq.setLrry(yhid);
            jyxxsq.setXgry(yhid);
            jyxxsq.setLrsj(allTime);
            jyxxsq.setXgsj(allTime);
            jyxxsq.setYkpjshj(0d);
            jyxxsq.setClztdm("00");
            jyxxsq.setFpzldm("02");
            jyxxsq.setFpczlxdm("11");
            jyxxsq.setYxbz("1");

            jyxxsq.setJylsh(getValue("jylsh", pzMap, columnIndexMap, row));


            jyxxsq.setDdh(getValue("ddh", pzMap, columnIndexMap, row));
            jyxxsq.setDdrq(allTime);
           /* if (!flag) {*/
            jyxxsq.setXfid(xf.getId());
            jyxxsq.setXfsh(xf.getXfsh());
            jyxxsq.setXfmc(xf.getXfmc());
            Map xfxxmap = GetXfxx.getXfxx(xf, skp);
            jyxxsq.setXfdz(xfxxmap.get("xfdz").toString());
            jyxxsq.setXfdh(xfxxmap.get("xfdh").toString());
            jyxxsq.setXfyh(xfxxmap.get("xfyh").toString());
            jyxxsq.setXfyhzh(xfxxmap.get("xfyhzh").toString());
            jyxxsq.setSkr(xfxxmap.get("skr").toString());
            jyxxsq.setKpr(xfxxmap.get("kpr").toString());
            jyxxsq.setFhr(xfxxmap.get("fhr").toString());

            jyxxsq.setSkpid(skpid);
            jyxxsq.setKpddm(skp.getKpddm());//解决没有kpddm问题。
            jyxxsq.setGfsh(getValue("购方税号", pzMap, columnIndexMap, row));
            jyxxsq.setGfmc(getValue("年级", pzMap, columnIndexMap, row)
                    + getValue("班级", pzMap, columnIndexMap, row) + getValue("姓名", pzMap, columnIndexMap, row));

            jyxxsq.setZtbz("6");
            jyxxsq.setSjly("2");
            jyxxsq.setBz(getValue("备注字段", pzMap, columnIndexMap, row));
            Double jshj = getValue("小计", pzMap, columnIndexMap, row) == null ? null : Double.valueOf(getValue("小计", pzMap, columnIndexMap, row));
            jyxxsq.setJshj(jshj);
            jyxxsq.setHsbz("1");
            jyxxsqList.add(jyxxsq);

            Double xfspje = getValue("学费", pzMap, columnIndexMap, row) == null ? null : Double.valueOf(getValue("学费", pzMap, columnIndexMap, row));
            if(xfspje!=null){
                Map params = new HashMap();
                params.put("spmc","学费");
                params.put("gsdm",getGsdm());
                Spvo spvo = spvoService.findOneSpvo(params);
                if(null !=spvo){
                    Jymxsq jymxsq = new Jymxsq();
                    jymxsq.setDdh(jyxxsq.getDdh());
                    jymxsq.setSpmxxh(num);
                    jymxsq.setGsdm(gsdm);
                    jymxsq.setLrry(yhid);
                    jymxsq.setXgry(yhid);
                    jymxsq.setYxbz("1");
                    jymxsq.setFphxz("0");
                    jymxsq.setLrsj(allTime);
                    jymxsq.setXgsj(allTime);
                    jymxsq.setSpdm(spvo.getSpbm());
                    jymxsq.setSpmc(spvo.getSpmc());
                    jymxsq.setSpggxh(spvo.getSpggxh());
                    jymxsq.setSpdw(spvo.getSpdw());
                   /* String sps = spvo.;
                    if (StringUtils.isNotBlank(sps)) {
                        jymxsq.setSps(Double.valueOf(sps));
                    }
                    String spdjStr = getValue("spdj", pzMap, columnIndexMap, row);
                    if (StringUtils.isNotBlank(spdjStr)) {
                        jymxsq.setSpdj(Double.valueOf(spdjStr));
                    }*/
                    jymxsq.setSpje(xfspje);
                    jymxsq.setSpsl(spvo.getSl());
                    jymxsq.setJshj(xfspje);
                    jymxsq.setKkjje(xfspje);
                    jymxsq.setYkjje(0d);
                    jymxsq.setSpse(0d);
                    if (jymxsq.getSpje() != null && jymxsq.getSpse() == 0) {
                        Double temp = div(jymxsq.getJshj(), (1 + jymxsq.getSpsl()), 100);
                        String je = new DecimalFormat("0.00").format(temp);
                        jymxsq.setSpse(Double.valueOf(new DecimalFormat("0.00").format(jymxsq.getJshj() - Double.valueOf(je))));
                    }
                    mxList.add(jymxsq);
                    num++;
                }
            }
            Double zsfspje = getValue("住宿费", pzMap, columnIndexMap, row) == null ? null : Double.valueOf(getValue("住宿费", pzMap, columnIndexMap, row));
            if(zsfspje!=null){
                Map params = new HashMap();
                params.put("spmc","住宿费");
                params.put("gsdm",getGsdm());
                Spvo spvo = spvoService.findOneSpvo(params);
                if(null !=spvo){
                    Jymxsq jymxsq = new Jymxsq();
                    jymxsq.setDdh(jyxxsq.getDdh());
                    jymxsq.setSpmxxh(num);
                    jymxsq.setGsdm(gsdm);
                    jymxsq.setLrry(yhid);
                    jymxsq.setXgry(yhid);
                    jymxsq.setYxbz("1");
                    jymxsq.setFphxz("0");
                    jymxsq.setLrsj(allTime);
                    jymxsq.setXgsj(allTime);
                    jymxsq.setSpdm(spvo.getSpbm());
                    jymxsq.setSpmc(spvo.getSpmc());
                    jymxsq.setSpggxh(spvo.getSpggxh());
                    jymxsq.setSpdw(spvo.getSpdw());
                    jymxsq.setSpje(zsfspje);
                    jymxsq.setSpsl(spvo.getSl());
                    jymxsq.setJshj(zsfspje);
                    jymxsq.setKkjje(zsfspje);
                    jymxsq.setYkjje(0d);
                    jymxsq.setSpse(0d);
                    if (jymxsq.getSpje() != null && jymxsq.getSpse() == 0) {
                        Double temp = div(jymxsq.getJshj(), (1 + jymxsq.getSpsl()), 100);
                        String je = new DecimalFormat("0.00").format(temp);
                        jymxsq.setSpse(Double.valueOf(new DecimalFormat("0.00").format(jymxsq.getJshj() - Double.valueOf(je))));
                    }
                    mxList.add(jymxsq);
                }
            }
           num=1;//商品明细序号重新开始
        }
        // 提取码
        List<String> tqmList = new ArrayList<>();
        // 开始数据校验
        for (int i = 0; i < jyxxsqList.size(); i++) {
            Jyxxsq jyxxsq = jyxxsqList.get(i);
            if (StringUtils.isNotBlank(jyxxsq.getTqm())) {
                tqmList.add(jyxxsq.getTqm());
            }
            String jylsh = jyxxsq.getJylsh();
            if (jylsh == null || "".equals(jylsh)) { // 交易流水号的判断
                msgg = "第" + (i + 2) + "行交易流水号没有填写，请重新填写！\r\n";
                msg += msgg;
            }
            if (jylsh != null && jylsh.length() > 20) { // 交易流水号长度的判断
                msgg = "第" + (i + 2) + "行交易流水号超出20个字符，请重新填写！\r\n";
                msg += msgg;
            }
            String ddh = jyxxsq.getDdh();// 订单号的校验
            if (ddh == null || "".equals(ddh)) {
                msgg = "第" + (i + 2) + "行订单号不能为空，请重新填写！\r\n";
                msg += msgg;
            } else if (ddh.length() > 20) {
                msgg = "第" + (i + 2) + "行订单号超出20个字符，请重新填写！\r\n";
                msg += msgg;
            }
            String xfsh = jyxxsq.getXfsh();
            String xfmc = jyxxsq.getXfmc();// 销方名称，销方税号的校验
            if (xfList != null) {
                for (int x = 0; x < xfList.size(); x++) {
                    if (!xfsh.equals(xfList.get(x).getXfsh()) && xfmc.equals(xfList.get(x).getXfmc())) {
                        msgg = "第" + (i + 2) + "行销方税号，销方名称不存在或者不对应，请重新填写！\r\n";
                        msg += msgg;
                    }
                }
            } else {
                msgg = "数据库中不存在有效的销方,请先去维护销方信息！\r\n";
                msg += msgg;
            }
            String xfdz = jyxxsq.getXfdz();// 销方地址的校验
            if (xfdz == null || "".equals(xfdz)) {
                msgg = "第" + (i + 2) + "行销方地址没有填写，请重新填写！\r\n";
                msg += msgg;
            } else if (xfdz.length() > 100) {
                msgg = "第" + (i + 2) + "行销方地址超出100个字符，请重新填写！\r\n";
                msg += msgg;
            }
            String fpzldm = jyxxsq.getFpzldm();// 发票种类代码
            if (fpzldm == null || "".equals(fpzldm)) {
                msgg = "第" + (i + 2) + "行发票种类没有填写，请重新填写！\r\n";
                msg += msgg;
            }

            String xfdh = jyxxsq.getXfdh();// 销方电话校验
            if (xfdh == null || "".equals(xfdh)) {
                msgg = "第" + (i + 2) + "行销方电话没有填写，请重新填写！\r\n";
                msg += msgg;
            } else if (xfdh.length() > 25) {
                msgg = "第" + (i + 2) + "行销方电话超出25个字符，请重新填写！\r\n";
                msg += msgg;
            }
            String xfyh = jyxxsq.getXfyh();// 销方银行校验
            if (xfyh == null || "".equals(xfyh)) {
                msgg = "第" + (i + 2) + "行销方银行没有填写，请重新填写！\r\n";
                msg += msgg;
            } else if (xfyh.length() > 25) {
                msgg = "第" + (i + 2) + "行销方银行超出25个字符，请重新填写！\r\n";
                msg += msgg;
            }
            String xfyhzh = jyxxsq.getXfyhzh();// 销方银行账号的校验
            if (xfyhzh == null || "".equals(xfyhzh)) {
                msgg = "第" + (i + 2) + "行销方银行账号没有填写，请重新填写！\r\n";
                msg += msgg;
            } else if (xfyhzh.length() > 30) {
                msgg = "第" + (i + 2) + "行销方银行超出30个字符，请重新填写！\r\n";
                msg += msgg;
            }
            String skr = jyxxsq.getSkr();// 收款人校验
            if (skr != null && skr.length() > 10) {
                msgg = "第" + (i + 2) + "行收款人超出10个字符，请重新填写！\r\n";
                msg += msgg;
            }
            String fhr = jyxxsq.getFhr();// 复核人校验
            if (fhr != null && fhr.length() > 10) {
                msgg = "第" + (i + 2) + "行复核人超出10个字符，请重新填写！\r\n";
                msg += msgg;
            }
            String kpr = jyxxsq.getKpr();// 开票人校验
            if (kpr != null && kpr.length() > 10) {
                msgg = "第" + (i + 2) + "行开票人超出10个字符，请重新填写！\r\n";
                msg += msgg;
            }
            String gfsh = jyxxsq.getGfsh();// 购方税号校验
            String gfyh1 = jyxxsq.getGfyh();// 购方税号校验
            String gfyhzh1 = jyxxsq.getGfyhzh();// 购方税号校验
            if (fpzldm.equals("01")) {
                if (gfsh == null || gfsh.equals("")) {
                    msgg = "第" + (i + 2) + "行专票购方税号为空，请重新填写！\r\n";
                    msg += msgg;
                }
                if (StringUtils.isBlank(gfyh1) && StringUtils.isBlank(gfyhzh1)) {
                    msgg = "第" + (i + 2) + "行专票购方银行及账号都为空，请重新填写！\r\n";
                    msg += msgg;
                }
                if (StringUtils.isBlank(jyxxsq.getGfdz()) && StringUtils.isBlank(jyxxsq.getGfdh())) {
                    msgg = "第" + (i + 2) + "行专票购方地址及电话都为空，请重新填写！\r\n";
                    msg += msgg;
                }
                if (gfsh != null && (gfsh.length() < 15 || gfsh.length() > 20)) { // 购方税号长度的判断
                    msgg = "第" + (i + 2) + "行购方税号不是15位到20位，请重新填写！\r\n";
                    msg += msgg;
                }
            } else {
                if (gfsh != null && (gfsh.length() < 15 || gfsh.length() > 20)) { // 购方税号长度的判断
                    msgg = "第" + (i + 2) + "行购方税号不是15位到20位，请重新填写！\r\n";
                    msg += msgg;
                }
            }
            String gfmc = jyxxsq.getGfmc();// 购方名称校验
            if (gfmc == null || "".equals(gfmc)) {
                msgg = "第" + (i + 2) + "行购方名称没有填写，请重新填写！\r\n";
                msg += msgg;
            }
            if (gfmc != null && gfmc.length() > 100) { // 购方名称长度的判断
                msgg = "第" + (i + 2) + "行购方名称超出100个字符，请重新填写！\r\n";
                msg += msgg;
            }
            String gfyh = jyxxsq.getGfyh();// 购方银行校验
            if (gfyh != null && gfyh.length() > 50) { // 购方银行长度的判断
                msgg = "第" + (i + 2) + "行购方银行超出50个字符，请重新填写！\r\n";
                msg += msgg;
            }
            String gfyhzh = jyxxsq.getGfyhzh();// 购方银行账号校验
            if (gfyhzh != null && gfyhzh.length() > 50) { // 购方银行账号长度的判断
                msgg = "第" + (i + 2) + "行购方银行账号超出50个字符，请重新填写！\r\n";
                msg += msgg;
            }
            String gfdz = jyxxsq.getGfdz();// 购方地址校验
            if (gfdz != null && gfdz.length() > 100) { // 购方地址长度的判断
                msgg = "第" + (i + 2) + "行购方地址超出200个字符，请重新填写！\r\n";
                msg += msgg;
            }
            String gfdh = jyxxsq.getGfdh();// 购方电话校验
            if (gfdh != null && gfdh.length() > 25) { // 购方电话长度的判断
                msgg = "第" + (i + 2) + "行购方电话超出25位，请重新填写！\r\n";
                msg += msgg;
            }
            String gfEmailstr = jyxxsq.getGfemail();// 购方email校验
            if(gfEmailstr!=null&&!"".equals(gfEmailstr.trim())){
                String []gfEmailArray=gfEmailstr.split(",");
                for(String gfEmail:gfEmailArray){
                    if (gfEmail != null && !"".equals(gfEmail.trim()) && !gfEmail.matches("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$")) {
                        msgg = "第" + (i + 2) + "行购方email格式不正确，请重新填写！\r\n";
                        msg += msgg;
                    }
                }
            }
            String hsbz = jyxxsq.getHsbz();// 含税标志校验
            if (hsbz == null || "".equals(hsbz)) {
                msgg = "第" + (i + 2) + "行含税标志不能为空，请重新填写！\r\n";
                msg += msgg;
            } else if (!("0".equals(hsbz) || "1".equals(hsbz))) {
                msgg = "第" + (i + 2) + "行含税标志只能填写1或0！\r\n";
                msg += msgg;
            }
            Jymxsq mxsq = mxList.get(i);
            String spdm = mxsq.getSpdm();
            if(spdm==null || "".equals(spdm)){
                msgg = "第" + (i + 2) + "行商品代码不能为空，请重新填写！\r\n";
                msg += msgg;
            }
            if (spdm != null && spdm.length() > 20) {
                msgg = "第" + (i + 2) + "行商品代码超过20个字符，请重新填写！\r\n";
                msg += msgg;
            }
            if(spdm != null && spdm.length() == 19){
                Map params2 = new HashMap();
                params2.put("spbm", spdm);
                List<Spbm> spbmList = spbmService.findAllByParam(params2);
                if(spbmList.isEmpty()){
                    msgg += "第"+ (i+1) + "行的商品税收分类编码(ProductCode)"+spdm+"不是最明细列!\r\n";
                }
            }
            String spmc = mxsq.getSpmc();
            if (spmc == null || "".equals(spmc)) {
                msgg = "第" + (i + 2) + "行商品名称不能为空，请重新填写！\r\n";
                msg += msgg;
            } else if (spmc.length() > 50) {
                msgg = "第" + (i + 2) + "行商品名称超过50个字符，请重新填写！\r\n";
                msg += msgg;
            }
            String spggxh = mxsq.getSpggxh();
            if (spggxh != null && spggxh.length() > 36) {
                msgg = "第" + (i + 2) + "行商品规格型号超过36个字符，请重新填写！\r\n";
                msg += msgg;
            }
            String spdw = mxsq.getSpdw();
            if (spdw != null && spdw.length() > 5) {
                msgg = "第" + (i + 2) + "行商品单位超过5个字符，请重新填写！\r\n";
                msg += msgg;
            }
            Double sps = mxsq.getSps();
            if (sps != null && !String.valueOf(sps).matches("^[0-9]{0,16}+(.[0-9]{0,})?$")) {
                msgg = "第" + (i + 2) + "行商品数格式不正确，请重新填写！\r\n";
                msg += msgg;
            }
            Double spdj = mxsq.getSpdj();
            if (spdj != null && !String.valueOf(spdj).matches("^[0-9]{0,16}+(.[0-9]{0,})?$")) {
                msgg = "第" + (i + 2) + "行商品单价格式不正确，请重新填写！\r\n";
                msg += msgg;
            }
            Double spje = mxsq.getSpje();
            if (spje == null || spje <= 0) {
                msgg = "第" + (i + 2) + "行商品金额不能为空或小于等于0，请重新填写！\r\n";
                msg += msgg;
            }
            Double spsl = mxsq.getSpsl();
            if (spsl == null || "".equals(spsl)) {
                msgg = "第" + (i + 2) + "行商品税率不能为空，请重新填写！\r\n";
                msg += msgg;
            }
            Double spse = mxsq.getSpse();
            if ("0".equals(hsbz)) {
                if (spse == null || spse <= 0) {
                    msgg = "第" + (i + 2) + "行不含税商品税额不能为空或小于等于0，请重新填写！\r\n";
                    msg += msgg;
                }
            }
            if (spje != null && spsl != null && spse != null && "1".equals(hsbz)) {
                boolean code = checkWC(spje, spsl, spse);
                if (!code) {
                    msgg = "第" + (i + 2) + "行商品金额，商品税率，商品税额之间的计算校验不通过，请检查！\r\n";
                    msg += msgg;
                }
            }
            if (mxsq.getSpdj() != null && mxsq.getSps() != null && mxsq.getSpje() != null) {
                double res = mxsq.getSpdj() * mxsq.getSps();
                BigDecimal big1 = new BigDecimal(res);
                big1 = big1.setScale(2, BigDecimal.ROUND_HALF_UP);
                BigDecimal big2 = new BigDecimal(mxsq.getSpje());
                big2 = big2.setScale(2, BigDecimal.ROUND_HALF_UP);
                if (big1.compareTo(big2) != 0) {
                    msgg = "第" + (i + 2) + "行商品单价，商品数量，商品金额之间的计算校验不通过，请检查！\r\n";
                    msg += msgg;
                }
            }
        }
        List<String> jylshList = new ArrayList<>();
        // 判断1条交易流水号不能对应对个购方名称
        if (jyxxsqList != null) {
            for (int i = 0; i < jyxxsqList.size(); i++) {
                Jyxxsq jyxxsq = jyxxsqList.get(i);
                String jylsh = jyxxsq.getJylsh();
                String gfmc = jyxxsq.getGfmc();
                if (jylsh != null) {
                    jylshList.add(jylsh);
                }
                for (int j = i + 1; j < jyxxsqList.size(); j++) {
                    Jyxxsq jyxxsq1 = jyxxsqList.get(i);
                    String jylsh1 = jyxxsq1.getJylsh();
                    String gfmc1 = jyxxsq1.getGfmc();
                    if (jylsh1 != null && gfmc != null && jylsh.equals(jylsh1) && !gfmc.equals(gfmc1)) {
                        msgg = "交易流水号" + jylsh + "不能对应多个不同的购方，请重新填写！\r\n";
                        msg += msgg;
                    }
                }
            }
        }
        // 判断交易流水号不能重复
        if (!jylshList.isEmpty()) {
            Map mapParams = new HashMap();
            mapParams.put("gsdm", this.getGsdm());
            mapParams.put("jylshList", jylshList);
            List<Jyxxsq> jyxxsqList1 = jyxxsqservice.findByMapParams(mapParams);
            if (jyxxsqList1.size() != 0) {
                for (Jyxxsq jyxxsq : jyxxsqList1) {
                    msgg = "交易流水号" + jyxxsq.getJylsh() + "已存在，请重新填写！\r\n";
                    msg += msgg;
                }
            }
        }
        // 判断提取码不能重复
        if (!tqmList.isEmpty()) {
            Map mapParams = new HashMap();
            mapParams.put("gsdm", this.getGsdm());
            mapParams.put("tqmList", tqmList);
            List<Jyxxsq> jyxxsqList1 = jyxxsqservice.findByMapParams(mapParams);
            if (!jyxxsqList1.isEmpty()) {
                for (Jyxxsq jyxxq : jyxxsqList1) {
                    msgg = "提取码" + jyxxq.getTqm() + "已存在，请重新填写！\r\n";
                    msg += msgg;
                }
            }
        }
        // 没有异常，保存
        if ("".equals(msg)) {
            System.out.println("校验处理结束时间："+new Date());
            System.out.println("保存数据开始时间："+new Date());
            //处理折扣行数据
            List<JymxsqCl> jymxsqClList = new ArrayList<JymxsqCl>();
            //复制一个新的list用于生成处理表
            List<Jymxsq> jymxsqTempList = new ArrayList<Jymxsq>();

            jymxsqTempList = BeanConvertUtils.convertList(mxList, Jymxsq.class);

            jymxsqClList = discountDealUtil.dealDiscount(jyxxsqList,jymxsqTempList,new ArrayList<Jyzfmx>(),gsdm);
            jyxxsqservice.saveAll(jyxxsqList, mxList,jymxsqClList,new ArrayList<Jyzfmx>());
            System.out.println("保存数据结束时间："+new Date());
        }
        result.put("msg", msg);
        result.put("jshj", zjshj);
        return result;
    }


    /**
     * 获取每一行中的
     *
     * @param enColumnName
     * @param pzMap
     * @param columnIndexMap
     * @param row
     * @return
     * @throws Exception
     */
    private String getValue(String enColumnName, Map<String, String> pzMap, Map<String, Integer> columnIndexMap, List row)
            throws Exception {
        //DrPz drPz = pzMap.get(enColumnName);
        // Integer temp = columnIndexMap.size() - row.size();
        String pzz =pzMap.get(enColumnName);
        if (pzz == null || pzz.equals("")) {
            String cnColumnName = importColumnMapping.get(enColumnName);
            Integer index = columnIndexMap.get(cnColumnName);
            if (index == null) {
                return null;
            }
            if(enColumnName.equals("备注字段")){
                return (row.get(index) == null ? null : row.get(index).toString())+" "+(row.get(index+1) == null ? null : row.get(index+1).toString());
            }else{
                return row.get(index) == null ? null : row.get(index).toString();
            }
        }
        if ("auto".equals(pzz)) {
            if ("jylsh".equals(enColumnName)) {
                String value = "JY" + System.currentTimeMillis();
                Thread.sleep(1);
                return value;
            } else if ("ddh".equals(enColumnName)) {
                String value = "DD" + System.currentTimeMillis();
                Thread.sleep(1);
                return value;
            } else if ("spse".equals(enColumnName)) {
                return "0";
            }
        }
        return null;
    }

    /**
     * 保存交易信息申请表和交易明细申请表
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/save1", method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "开票单保存", key = "ddh_edit")
    public Map save1() {
        Map<String, Object> result = new HashMap<String, Object>();
        String gsdm = getGsdm();
        String xfid = request.getParameter("lrxfid_edit");
        int yhid = getYhid();
        Xf xf = xfService.findOne(Integer.parseInt(xfid));
        Jyxxsq jyxxsq = new Jyxxsq();
        jyxxsq.setClztdm("00");
        jyxxsq.setDdh(request.getParameter("lrddh_edit"));
        jyxxsq.setGfsh(request.getParameter("lrgfsh_edit"));

        jyxxsq.setGfmc(request.getParameter("lrgfmc_edit"));
        jyxxsq.setGfyh(request.getParameter("lrgfyh_edit"));
        jyxxsq.setGfyhzh(request.getParameter("lrgfzh_edit"));
        jyxxsq.setGfsjh(request.getParameter("lrgfsjh_edit"));

        jyxxsq.setGflxr(request.getParameter("lrgflxr_edit"));
        jyxxsq.setBz(request.getParameter("lrgfbz_edit"));
        jyxxsq.setGfemail(request.getParameter("lrgfemail_edit"));
        jyxxsq.setGfdz(request.getParameter("lrgfdz_edit"));
        jyxxsq.setGfdh(request.getParameter("lrgfdh_edit"));
        jyxxsq.setZtbz("6");//0未提交，1已提交
        jyxxsq.setSjly("0");//0平台接入，1接口接入
        String tqm = request.getParameter("lrtqm_edit");
        if (StringUtils.isNotBlank(tqm)) {
            Map params = new HashMap();
            params.put("gsdm", gsdm);
            params.put("tqm", tqm);
            Jyxxsq tmp = jyxxsqservice.findOneByParams(params);
            if (tmp != null) {
                result.put("failure", true);
                result.put("msg", "提取码已经存在");
                return result;
            }
        }
        jyxxsq.setTqm(tqm);
        jyxxsq.setJylsh("JY" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
        jyxxsq.setJshj(0.00);
        jyxxsq.setYkpjshj(0.00);
        jyxxsq.setHsbz("1");
        jyxxsq.setXfid(xf.getId());
        jyxxsq.setXfsh(xf.getXfsh());
        jyxxsq.setXfmc(xf.getXfmc());
        jyxxsq.setLrsj(TimeUtil.getNowDate());
        jyxxsq.setXgsj(TimeUtil.getNowDate());
        jyxxsq.setDdrq(TimeUtil.getNowDate());
        jyxxsq.setFpzldm(request.getParameter("lrfpzl_edit"));
        jyxxsq.setFpczlxdm("11");
        jyxxsq.setXfyh(xf.getXfyh());
        jyxxsq.setXfyhzh(xf.getXfyhzh());
        jyxxsq.setXfdz(xf.getXfdz());
        jyxxsq.setXfdh(xf.getXfdh());
        if (StringUtils.isNotBlank(jyxxsq.getGfemail())) {
            jyxxsq.setSffsyj("1");
        }
        jyxxsq.setKpr(xf.getKpr());
        jyxxsq.setFhr(xf.getFhr());
        jyxxsq.setSkr(xf.getSkr());
        jyxxsq.setSsyf(new SimpleDateFormat("yyyyMM").format(new Date()));
        jyxxsq.setLrry(yhid);
        jyxxsq.setXgry(yhid);
        jyxxsq.setYxbz("1");
        jyxxsq.setGsdm(gsdm);
        String skpid = request.getParameter("lrskpid_edit");
        if (skpid != null && !"".equals(skpid)) {
            jyxxsq.setSkpid(Integer.parseInt(skpid));
        }
        //Map paramskp = new HashMap();
        //paramskp.put("id", skpid);
        Skp skp = skpservice.findOne(Integer.valueOf(skpid));
        jyxxsq.setKpddm(skp.getKpddm());
        try {
            // Map<String, Object> params = null;
            Map params = Tools.getParameterMap(request);
            int mxcount = Integer.valueOf(params.get("mxcount").toString());
            String[] spdms = ((String) params.get("spdm")).split(",");
            String[] spmcs = ((String) params.get("spmc")).split(",");
            String[] spjes = ((String) params.get("je")).split(",");
            String[] spsls = ((String) params.get("rate")).split(",");
            String[] jshjs = ((String) params.get("jshj")).split(",");
            String[] spges = ((String) params.get("ggxh")).split(",");
            String[] spss = ((String) params.get("sl")).split(",");
            String[] spdws = ((String) params.get("dw")).split(",");
            String[] spdjs = ((String) params.get("dj")).split(",");
            String[] spses = ((String) params.get("se")).split(",");

            double jshj = 0.00;
            List<Jymxsq> jymxsqList = new ArrayList<>();
            for (int c = 0; c < mxcount; c++) {
                Jymxsq jymxsq = new Jymxsq();
                int xxh = c + 1;
                jymxsq.setSpmxxh(xxh);
                jymxsq.setFphxz("0");
                jymxsq.setSpdm(spdms[c]);
                jymxsq.setSpmc(spmcs[c]);
                jymxsq.setSpje(Double.valueOf(spjes[c]));
                if (spsls.length != 0) {
                    jymxsq.setSpsl(Double.valueOf(spsls[c]));
                }
                jymxsq.setJshj(Double.valueOf(jshjs[c]));
                jymxsq.setKkjje(Double.valueOf(jshjs[c]));
                jymxsq.setYkjje(0d);
                if (spges.length != 0) {
                    try {
                        jymxsq.setSpggxh(spges[c]);
                    } catch (Exception e) {
                        jymxsq.setSpggxh(null);

                    }
                }
                try {
                    jymxsq.setSps(Double.valueOf(spss[c]));
                } catch (Exception e) {
                    jymxsq.setSps(null);
                }
                if (spdws.length != 0) {
                    try {
                        jymxsq.setSpdw(spdws[c]);
                    } catch (Exception e) {
                        jymxsq.setSpdw(null);

                    }
                }
                if (spdjs.length != 0) {
                    try {
                        jymxsq.setSpdj(Double.valueOf(spdjs[c]));
                    } catch (Exception e) {
                        jymxsq.setSpdj(null);

                    }
                }
                if (spses.length != 0) {
                    jymxsq.setSpse(Double.valueOf(spses[c]));
                }
                //jymxsq.setYkphj(0.00);
                jymxsq.setLrry(yhid);
                jymxsq.setYxbz("1");
                jymxsq.setLrsj(TimeUtil.getNowDate());
                jymxsq.setXgsj(TimeUtil.getNowDate());
                jymxsq.setXgry(yhid);
                jymxsq.setGsdm(gsdm);

                jshj += jymxsq.getJshj();
                jymxsqList.add(jymxsq);
            }
            jyxxsq.setJshj(jshj);
            //jyxxsqservice.saveJyxxsq(jyxxsq, jymxsqList);
            //处理折扣行数据
            List<JymxsqCl> jymxsqClList = new ArrayList<JymxsqCl>();
            //复制一个新的list用于生成处理表
            List<Jymxsq> jymxsqTempList = new ArrayList<Jymxsq>();
            jymxsqTempList = BeanConvertUtils.convertList(jymxsqList, Jymxsq.class);

            jymxsqClList = discountDealUtil.dealDiscount(jymxsqTempList, 0d, jshj, jyxxsq.getHsbz());
            jyxxsqservice.saveJyxxsq(jyxxsq, jymxsqList, jymxsqClList, new ArrayList<Jyzfmx>());
            result.put("success", true);
            result.put("djh", jyxxsq.getSqlsh());
        } catch (Exception ex) {
            ex.printStackTrace();
            result.put("failure", true);
            result.put("msg", "保存出现错误: " + ex.getMessage());
        }
        return result;
    }

}