package com.rjxx.taxeasy.controller;

import com.rjxx.taxeasy.bizcomm.utils.FphcService;
import com.rjxx.taxeasy.bizcomm.utils.InvoiceResponse;
import com.rjxx.taxeasy.bizcomm.utils.SkService;
import com.rjxx.taxeasy.domains.*;
import com.rjxx.taxeasy.filter.SystemControllerLog;
import com.rjxx.taxeasy.invoice.DealOrder01;
import com.rjxx.taxeasy.invoice.DealOrder04;
import com.rjxx.taxeasy.invoice.Kphc;
import com.rjxx.taxeasy.service.*;
import com.rjxx.taxeasy.utils.UrlUtils;
import com.rjxx.taxeasy.vo.Fpcxvo;
import com.rjxx.taxeasy.web.BaseController;
import com.rjxx.utils.HtmlUtils;
import com.rjxx.utils.StringUtils;
import com.rjxx.utils.XmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017-12-05.
 */
@Controller
@RequestMapping("/fpck")
public class FpckController extends BaseController {

    @Autowired
    private FpztService ztService;

    @Autowired
    private KplsService kplsService;

    @Autowired
    private SkpService skpService;

    @Autowired
    private CszbService cszbService;

    @Autowired
    private SkService skService;

    @Autowired
    private FphcService fphcService;

    @Autowired
    private KpspmxService kpspmxService;

    @Autowired
    private DealOrder04 dealOrder04;
    @Autowired
    private JylsService jylsService;
    @Autowired
    private DealOrder01 dealOrder01;
    @Autowired
    private JyxxsqService jyxxsqService;
    @Autowired
    private JymxsqService jymxsqService;
    @Autowired
    private JyzfmxService jyzfmxService;



    @RequestMapping
    public String index() throws Exception {
        Map params = new HashMap<>();
        List<Fpzt> ztList = ztService.findAllByParams(params);
        if (ztList != null) {
            request.setAttribute("ztList", ztList);
        }
        request.setAttribute("xfList", getXfList());
        request.setAttribute("skpList", getSkpList());
        return "fpck/index";
    }
    @RequestMapping(value = "/getKplsList")
    @ResponseBody
    public Map<String, Object> getItems(int length, int start, int draw, String ddh, String fphm, String kprqq,
                                        String kprqz, String spmc, String printflag, String gfmc, String fpdm, String fpzt, String fpczlx, String fpzldm,
                                        String xfsh, String sk, String xfmc,boolean loaddata2,String errorReason) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        //Pagination pagination = new Pagination();
        //pagination.setPageNo(start / length + 1);
        //pagination.setPageSize(length);
        Map maps = new HashMap();
        maps.put("start",start);
        maps.put("length",length);
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
        maps.put("gsdm", gsdm);
        maps.put("xfid", xfid);
        maps.put("skpid", skpid);
        maps.put("ddh", ddh);
        maps.put("fpzldm", fpzldm);
        maps.put("fphm", fphm);
        maps.put("kprqq", kprqq);
        maps.put("kprqz", kprqz);
        //maps.put("spmc", spmc);
        maps.put("printflag", printflag);
        maps.put("gfmc", gfmc);
        maps.put("fpdm", fpdm);
        //发票重开只查询发票状态为00
        List fpztlist = new ArrayList();
        fpztlist.add("00");
        //fpztlist.add("14");
        maps.put("fpztlist", fpztlist);
        maps.put("fpzt", fpzt);
        maps.put("xfmc", xfmc);
        maps.put("fpczlx", "11");
        maps.put("errorReason",errorReason);
        if (null != xfsh && !"".equals(xfsh) && !"-1".equals(xfsh)) {
            maps.put("xfsh", xfsh);
        }
        if (null != sk && !"".equals(sk) && !"-1".equals(sk)) {
            maps.put("sk", sk);
        }


        // String tmp = (String)request.getSession().getAttribute("total");
        int total;
        if(0 == start && loaddata2){
            total = kplsService.findTotal(maps);
            request.getSession().setAttribute("total",total);
        }else{
            if(!loaddata2){
                total = 0;
            }else {
                total = (Integer) request.getSession().getAttribute("total");
            }
            //request.getSession().getAttribute("total");
        }
        if(loaddata2){
            List<Fpcxvo> ykfpList = kplsService.findByPage2(maps);
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
     * 重新开具--先红冲，再开具
     * @param kplshck
     * @param gfmcck
     * @param gfshck
     * @param gfemailck
     * @param gfdzck
     * @param gfdhck
     * @param gfyhck
     * @param gfyhzhck
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/fpckSave")
    @ResponseBody
    @SystemControllerLog(description = "发票重开",key = "kplshck")
    public Map fpck(String kplshck,String gfmcck,String gfshck,String gfemailck,
                    String gfdzck,String gfdhck,String gfyhck,String gfyhzhck,String sfbx,String fpcklx
                    ,String bz,String fpzldm) throws Exception {
        Map<String, Object> result = new HashMap<>();
        if (null == kplshck) {
            result.put("success", false);
            result.put("msg", "重新开具失败！");
            return result;
        }
        String[] kpsqh = kplshck.split(",");
        for (int j = 0; j < kpsqh.length; j++) {
            Kpls kpls = kplsService.findOne(Integer.parseInt(kpsqh[j].toString()));
            if (kpls.getFpztdm().equals("00")) {
                try {
                    Map paramsMap = new HashMap();
                    paramsMap.put("kplsh", kplshck);
                    List<Kpspmx> kpspmxList = kpspmxService.findMxList(paramsMap);

                    Map jylsMap = new HashMap();
                    jylsMap.put("jylsh", kpls.getJylsh());
                    List<Jyls> list = jylsService.findAllByJylsh(jylsMap);
                    List<Jyxxsq> jyxxsqList = new ArrayList();
                    List<Jymxsq> jymxsqList = new ArrayList();
                    List<Jyzfmx> jyzfmxList = new ArrayList<Jyzfmx>();
                    Map jyxxsqMap = new HashMap();
                    jyxxsqMap.put("jylsh", list.get(0).getJylsh());
                    jyxxsqMap.put("gsdm", getGsdm());
                    Jyxxsq jyxxsq = jyxxsqService.findOneByJylsh(jyxxsqMap);
                    Map jymxsqMap = new HashMap();
                    jymxsqMap.put("sqlsh", list.get(0).getSqlsh());
//                    jymxsqMap.put("gsdm", getGsdm());
                    jymxsqList = jymxsqService.findAllBySqlsh(jymxsqMap);
                    if (jymxsqList.size() < 0) {
                        result.put("success", false);
                        result.put("msg", "重新开具失败！");
                        return result;
                    }
                    Map jyzfsqMap = new HashMap();
                    jyzfsqMap.put("gsdm", getGsdm());
                    jyzfsqMap.put("sqlsh", list.get(0).getSqlsh());
                    jyzfmxList = jyzfmxService.findAllByParams(jyzfsqMap);
//                    String ss="";
//                    String hcje="";
//                    for(int j=0;j<kpspmxList.size();j++){
//                        Kpspmx kpspmx=kpspmxList.get(j);
//                        ss+=kpspmx.getId()+",";
//                        hcje+=kpspmx.getKhcje()+",";
//                    }
                    //先红冲
                    //InvoiceResponse flag = fphcService.hccl(kpls.getKplsh(), getYhid(),
//                            getGsdm(), hcje, ss,kpls.getHztzdh(),kpls.getJylsh());
//                    if(kpls.getFpzldm().equals("12")){
                        Kphc kphc = new Kphc();
                        kphc.setCNDNCode(kpls.getFpdm());
                        kphc.setCNDNNo(kpls.getFphm());
                        kphc.setInvType(kpls.getFpzldm());
                        kphc.setExtractCode(list.get(0).getTqm());
                        kphc.setClientNO(jyxxsq.getKpddm());
                        kphc.setServiceType("1");
                        kphc.setSerialNumber("JY" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
                        kphc.setOrderNumber(list.get(0).getDdh());
                        kphc.setCNNoticeNo(kpls.getHztzdh());
                        BigDecimal big = new BigDecimal(kpls.getJshj().toString());
                        BigDecimal totalAmount = big.multiply(new BigDecimal("-1"));
                        kphc.setTotalAmount(Double.valueOf(totalAmount.toString()));
                        Map hcMap = new HashMap();
                        hcMap.put("Kphc", kphc);
                        String resultxml = dealOrder04.execute(getGsdm(), hcMap, "04");
                        Map<String, Object> resultHCMap = XmlUtil.xml2Map(resultxml);
                        String returnHCMsg = resultHCMap.get("ReturnMessage").toString();
                        String returnHCCode = resultHCMap.get("ReturnCode").toString();
                        if (returnHCCode.equals("0000")) {
                            //更新原始数据提取码
                            if(StringUtils.isNotBlank(jyxxsq.getTqm())){
                                Map param= new HashMap();
                                param.put("tqm",jyxxsq.getTqm()+"_01");
                                param.put("gsdm",jyxxsq.getGsdm());
                                param.put("sqlsh",jyxxsq.getSqlsh());
                                jyxxsqService.updateJyxxsqTqm(param);
                            }

                            //修改购方信息--重开
                            if(fpcklx!=null && fpcklx.equals("01")){
                                if (null != gfshck && !gfshck.equals("")) {
                                    jyxxsq.setGflx("1");
                                } else {
                                    jyxxsq.setGflx("0");
                                }
                                jyxxsq.setGfmc(gfmcck);
                                jyxxsq.setGfsh(gfshck);
                                jyxxsq.setGfemail(gfemailck);
                                jyxxsq.setGfdz(gfdzck);
                                jyxxsq.setGfdh(gfdhck);
                                jyxxsq.setGfyh(gfyhck);
                                jyxxsq.setGfyhzh(gfyhzhck);
                                if (gfemailck != null && !gfemailck.equals("")) {
                                    jyxxsq.setSffsyj("1");
                                }
                                jyxxsq.setFpzldm(fpzldm);
                                jyxxsq.setBz(bz);
                            }
                            /*if (jymxsqList.get(0).getDdh() == null) {
                                jymxsqList.get(0).setDdh(jyxxsq.getDdh());
                            }*/
                            jyxxsq.setJylsh("JY" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
                            if (jyxxsq.getHsbz().equals("1")) {
                                for (int i = 0; i < jymxsqList.size(); i++) {
                                    jymxsqList.get(i).setSpse(null);
                                    jymxsqList.get(i).setSpje(jymxsqList.get(i).getJshj());
                                    jymxsqList.get(i).setDdh(jyxxsq.getDdh());
                                }
                            }
                            //修改订单日期
                            jyxxsq.setDdrq(new Date());
                            jyxxsqList.add(jyxxsq);
                            Map kpMap = new HashMap();
                            kpMap.put("jyxxsqList", jyxxsqList);
                            kpMap.put("jymxsqList", jymxsqList);
                            kpMap.put("jyzfmxList", jyzfmxList);
                            String returnXml = dealOrder01.execute(getGsdm(), kpMap, "01");
                            Map<String, Object> resultCKMap = XmlUtil.xml2Map(returnXml);
                            String returnCKMsg = resultCKMap.get("ReturnMessage").toString();
                            String returnCKCode = resultCKMap.get("ReturnCode").toString();
                            if (returnCKCode.equals("0000")) {
                                result.put("success", true);
                                result.put("msg", "重新开具成功！");
                            } else {
                                result.put("success", false);
                                result.put("msg", "重新开具失败！" + returnCKMsg);
                                return result;
                            }
                        } else {
                            result.put("success", false);
                            result.put("msg", "重新开具,红冲失败！" + returnHCMsg);
                            return result;
                        }
//                    }else if(kpls.getFpzldm().equals("01")){
//                        result.put("success", false);
//                        result.put("msg", "暂不支持,增值税专用发票的发票重开！");
//                        return result;
//                    }else if(kpls.getFpzldm().equals("02")){
//                        result.put("success", false);
//                        result.put("msg", "暂不支持,增值税普通发票的发票重开！");
//                        return result;
//                    }
                } catch (Exception e) {
                    result.put("success", false);
                    result.put("msg", "重新开具失败！");
                    return result;
                }
            } else {
                result.put("success", false);
                result.put("msg", "该信息不能重新开具!");
                return result;
            }
        }
        return result;
    }
}
