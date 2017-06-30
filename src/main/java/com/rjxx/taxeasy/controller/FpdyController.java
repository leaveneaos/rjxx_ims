package com.rjxx.taxeasy.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjxx.taxeasy.utils.UrlUtils;
import com.rjxx.utils.HtmlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.bizcomm.utils.FpcdService;
import com.rjxx.taxeasy.bizcomm.utils.InvoiceResponse;
import com.rjxx.taxeasy.domains.Skp;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.service.KplsService;
import com.rjxx.taxeasy.service.KpspmxService;
import com.rjxx.taxeasy.vo.Fpcxvo;
import com.rjxx.taxeasy.vo.Kpspmxvo;
import com.rjxx.taxeasy.web.BaseController;

@Controller
@RequestMapping("/fpdy")
public class FpdyController extends BaseController {

    @Autowired
    private KplsService kplsService;
    @Autowired
    private KpspmxService mxService;

    @Autowired
    private FpcdService fpcdService;

    @RequestMapping
    public String index() throws Exception {
        request.setAttribute("xfList", getXfList());
        request.setAttribute("skpList", getSkpList());
        return "fpdy/index";
    }

    @RequestMapping(value = "/getKplsList")
    @ResponseBody
    public Map<String, Object> getItems(int length, int start, int draw,boolean loaddata) throws Exception {


        String kprqq = request.getParameter("kprqq");//开票日期起
        String kprqz = request.getParameter("kprqz");//开票日期止

        String gfmc = request.getParameter("gfmc");//购方名称

        String xfi = request.getParameter("xfi");//销方税号

        String fplx = request.getParameter("fplx");//发票类型

        String ddh = request.getParameter("ddh");//订单号

        String fpdm = request.getParameter("fpdm");//发票代码

        String fphm = request.getParameter("fphm");//发票号码


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
        pagination.addParam("fpzldm", fplx);
        pagination.addParam("gsdm", gsdm);
        pagination.addParam("xfid", xfid);
        pagination.addParam("xfi", xfi);
        pagination.addParam("skpid", skpid);
        pagination.addParam("kprqq", kprqq);
        pagination.addParam("kprqz", kprqz);
        pagination.addParam("gfmc", gfmc);
        pagination.addParam("ddh", ddh);
        pagination.addParam("fpdm", fpdm);
        pagination.addParam("fphm", fphm);


        List<Fpcxvo> kcdfpList = kplsService.findKcdfpByPage(pagination);
        int total = pagination.getTotalRecord();
        if(loaddata){
            result.put("recordsTotal", total);
            result.put("recordsFiltered", total);
            result.put("draw", draw);
            result.put("data", kcdfpList);
        }else{
            result.put("recordsTotal", 0);
            result.put("recordsFiltered", 0);
            result.put("draw", draw);
            result.put("data", new ArrayList<>());
        }
        return result;
    }

    @RequestMapping(value = "/getKplsList1")
    @ResponseBody
    public Map<String, Object> getItems1(int length, int start, int draw,boolean loaddata2) throws Exception {

        String kprqq = request.getParameter("kprqq");//开票日期起
        String kprqz = request.getParameter("kprqz");//开票日期止

        String gfmc = request.getParameter("gfmc");//购方名称

        String xfi = request.getParameter("xfi");//销方税号

        String fplx = request.getParameter("fplx");//发票类型

        String ddh = request.getParameter("ddh");//订单号

        String fpdm = request.getParameter("fpdm");//发票代码

        String fphm = request.getParameter("fphm");//发票号码

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
        pagination.addParam("fpzldm", fplx);
        pagination.addParam("gsdm", gsdm);
        pagination.addParam("xfid", xfid);
        pagination.addParam("xfi", xfi);
        pagination.addParam("skpid", skpid);
        pagination.addParam("kprqq", kprqq);
        pagination.addParam("kprqz", kprqz);
        pagination.addParam("gfmc", gfmc);
        pagination.addParam("ddh", ddh);
        pagination.addParam("fpdm", fpdm);
        pagination.addParam("fphm", fphm);


        List<Fpcxvo> kcdfpList = kplsService.findKcdfpByPage1(pagination);
        int total = pagination.getTotalRecord();
        if(loaddata2){
            result.put("recordsTotal", total);
            result.put("recordsFiltered", total);
            result.put("draw", draw);
            result.put("data", kcdfpList);
        }else{
            result.put("recordsTotal", 0);
            result.put("recordsFiltered", 0);
            result.put("draw", draw);
            result.put("data", new ArrayList<>());
        }
        return result;
    }

    @RequestMapping(value = "/getMx")
    @ResponseBody
    public Map<String, Object> getMx(int kplsh) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        Map params = new HashMap<>();
        params.put("id", kplsh);
        List<Kpspmxvo> kpspmxList = mxService.findAllByParams(params);
        // List<Kpspmxvo> mxList = new ArrayList<Kpspmxvo>();
        if (kpspmxList != null) {
            for (Kpspmxvo mx : kpspmxList) {
                if (mx.getKhcje() == null) {
                    mx.setKhcje(mx.getJshj());
                }
                if (mx.getYhcje() == null) {
                    mx.setYhcje(0.00);
                }
            }
        }
        result.put("data", kpspmxList);
        return result;
    }

    @Transactional
    @RequestMapping(value = "/cd")
    @ResponseBody
    public Map<String, Object> update(String kplsh, String fphm, String fpdm) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        //try {

        String[] kplsh2 = kplsh.split(",");
        String[] fphm2 = fphm.split(",");
        String[] fpdm2 = fpdm.split(",");


        if (checkFpdm(fpdm2)) {
            if (!checkNum(fphm2)) {
                result.put("success", false);
                result.put("msg", "批量重打时，请选择连续的发票号码！");
                return result;

            }
        } else {
            result.put("success", false);
            result.put("msg", "批量重打时，请选择相同的发票代码！");
            return result;
        }


        for (int i = 0; i < kplsh2.length; i++) {
            InvoiceResponse flag = fpcdService.cdcl(Integer.valueOf(kplsh2[i]), getYhid(), getGsdm());
            if (!flag.getReturnCode().equals("0000")) {
                result.put("success", false);
                result.put("msg", "第" + (i + 1) + "条流水申请重打失败" + flag.getReturnMessage());
                return result;

            }
        }


        result.put("success", true);
        result.put("msg", "重打成功!");

		
/*		} catch (Exception e) {
            e.printStackTrace();
			result.put("success", false);
			result.put("msg", "后台出现错误: " + e.getMessage());
		}*/
        return result;
    }

    /**
     * 批量打印电子票
     *
     * @param ids
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/printmany")
    public String printSingle(String ids) throws Exception {
        Map params = new HashMap<>();
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
        return "fpdy/printandview";
    }


    /*
     * 	判断是否是连续的开票流水号
     * @param arr
     */
    public static boolean checkNum(String[] arr) {

        boolean f = true;
        //对数组进行冒泡排序
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {

                if (Long.parseLong(arr[j]) > Long.parseLong(arr[j + 1])) {
                    String temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        for (int j = 0; j < arr.length - 1; j++) {

            if ((Long.parseLong(arr[j + 1]) - Long.parseLong(arr[j])) > 1) {
                f = false;
            }
        }
        return f;

    }

    /*
     * 	判断是否是连续的开票流水号
     * @param arr
     */
    public static boolean checkFpdm(String[] arr) {

        boolean f = true;

        for (int j = 0; j < arr.length - 1; j++) {

            if ((Long.parseLong(arr[j + 1]) - Long.parseLong(arr[j])) != 0) {
                f = false;

            }

        }

        return f;

    }
}
