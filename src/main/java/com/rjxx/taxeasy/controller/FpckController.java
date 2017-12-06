package com.rjxx.taxeasy.controller;

import com.rjxx.taxeasy.bizcomm.utils.FphcService;
import com.rjxx.taxeasy.bizcomm.utils.InvoiceResponse;
import com.rjxx.taxeasy.bizcomm.utils.SkService;
import com.rjxx.taxeasy.domains.*;
import com.rjxx.taxeasy.filter.SystemControllerLog;
import com.rjxx.taxeasy.service.*;
import com.rjxx.taxeasy.utils.UrlUtils;
import com.rjxx.taxeasy.vo.Fpcxvo;
import com.rjxx.taxeasy.web.BaseController;
import com.rjxx.utils.HtmlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Transactional
    @SystemControllerLog(description = "发票重开",key = "kplshck")
    public Map fpck(String kplshck,String gfmcck,String gfshck,String gfemailck,
                    String gfdzck,String gfdhck,String gfyhck,String gfyhzhck,String sfbx) throws Exception {
        Map<String, Object> result = new HashMap<>();
        if(null==kplshck){
            result.put("success", false);
            result.put("msg", "重新开具失败！");
            return result;
        }
//        String []kpsqh= kplshs.split(",");
//        for(int i=0;i<kpsqh.length;i++){
            Kpls kpls=kplsService.findOne(Integer.parseInt(kplshck));
            if(kpls.getFpztdm().equals("00")){
                try{
                    /*Map paramsMap=new HashMap();
                    paramsMap.put("kplsh",kplshck);
                    List<Kpspmx> kpspmxList=  kpspmxService.findMxList(paramsMap);
                    String ss="";
                    String hcje="";
                    for(int j=0;j<kpspmxList.size();j++){
                        Kpspmx kpspmx=kpspmxList.get(j);
                        ss+=kpspmx.getId()+",";
                        hcje+=kpspmx.getKhcje()+",";
                    }
                    //先红冲
                    InvoiceResponse flag = fphcService.hccl(kpls.getKplsh(), getYhid(),
                            getGsdm(), hcje, ss,kpls.getHztzdh(),kpls.getJylsh());
                    if (flag.getReturnCode().equals("0000")) {
                        result.put("success", true);
                        result.put("msg", "重新开具成功！");
                    }else{
                        result.put("success", false);
                        result.put("msg", "重新开具,红冲失败！"+flag.getReturnMessage());
                        return result;
                    }*/
                    result.put("success", true);
                    result.put("msg", "重新开具成功！");
                }catch (Exception e){
                    result.put("success", false);
                    result.put("msg", "重新开具失败！");
                    return result;
                }
            }else{
                result.put("success", false);
                result.put("msg", "该信息不能重新开具!");
                return result;
            }
        return result;
    }

}
