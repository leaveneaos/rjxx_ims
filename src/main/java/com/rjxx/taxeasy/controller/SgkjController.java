package com.rjxx.taxeasy.controller;

import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.bizcomm.utils.*;
import com.rjxx.taxeasy.dao.XfJpaDao;
import com.rjxx.taxeasy.domains.*;
import com.rjxx.taxeasy.invoice.KpService;
import com.rjxx.taxeasy.service.*;
import com.rjxx.taxeasy.vo.Fpkcvo;
import com.rjxx.taxeasy.vo.JyspmxDecimal2;
import com.rjxx.taxeasy.vo.Jyzfmxvo;
import com.rjxx.taxeasy.vo.Spvo;
import com.rjxx.taxeasy.web.BaseController;
import com.rjxx.time.TimeUtil;
import com.rjxx.utils.BeanConvertUtils;
import com.rjxx.utils.CheckOrderUtil;
import com.rjxx.utils.StringUtils;
import com.rjxx.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.rjxx.taxeasy.config.NsrConstans.*;

/**
 * Created by xlm on 2017/5/31.
 */
@Controller
@RequestMapping("/sgkj")
public class SgkjController extends BaseController{

    @Autowired
    private CszbService cszbService;
    @Autowired
    private SpvoService spvoService;
    @Autowired
    private SpzService spzService;
    @Autowired
    private XfService xfService;
    @Autowired
    private JyxxsqService jyxxsqService;
    @Autowired
    private JymxsqService jymxsqService;
    @Autowired
    private JylsService jylsService;
    @Autowired
    private JyspmxService jyspmxService;
    @Autowired
    private KplsService kplsService;
    @Autowired
    private KpspmxService kpspmxService;
    @Autowired
    private SkpService skpService;
    @Autowired
    private JyzfmxService jyzfmxService;
    @Autowired
    private FpkcService fpkcService;
    @Autowired
    private DiscountDealUtil discountDealUtil;
    @Autowired
    private SkService skService;
    @Autowired
    private InvoiceSplitParamsUtil invoiceSplitParamsUtil;
    @Autowired
    private  CheckOrderUtil checkOrderUtil;
    @Autowired
    private XfJpaDao xfJpaDao;
    @Autowired
    private KpService kpService;

    @RequestMapping
    public  String index()throws Exception{

        String gsdm = this.getGsdm();
        Cszb cszb = cszbService.getSpbmbbh(gsdm, getXfid(), null, "sfqyspz");
        List<Spvo> list2 = new ArrayList<>();
        if (null!=cszb&&cszb.getCsz().equals("是")) {
            Map<String, Object> pMap = new HashMap<>();
            pMap.put("xfs", getXfList());
            list2 = spzService.findAllByParams(pMap);
        }
        if (list2.size()==0) {
            list2 = spvoService.findAllByGsdm(gsdm);
        }
        if (!list2.isEmpty()) {
            request.setAttribute("sp", list2.get(0));
        }
        request.setAttribute("spList", list2);
        request.setAttribute("xfList", getXfList());
        request.setAttribute("xfnum", getXfList().size());
        if(getXfList().size()==1) {
            Map params = new HashMap<>();
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
            params.put("xfid", getXfList().get(0).getId());
            params.put("gsdm", gsdm);
            params.put("skpid", skpid);
            List<Fpkcvo> list = fpkcService.findKpd(params);
            request.setAttribute("skpList", list);
        }
        return "sgkj/index";
    }
    // 查询方法
    @RequestMapping(value = "/getItems")
    @ResponseBody
    public Map<String, Object> getItems(int length, int start, int draw,Integer xfid,String spmc) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        Pagination pagination = new Pagination();
        pagination.setPageNo(start / length + 1);
        pagination.setPageSize(length);
        String gsdm = getGsdm();
        Cszb cszb = cszbService.getSpbmbbh(gsdm, xfid, null, "sfqyspz");
        List<Spvo> list2 = new ArrayList<>();
        if (null!=cszb&&cszb.getCsz().equals("是")) {
            Map<String, Object> pMap = new HashMap<>();
            pMap.put("xfs", getXfList());
            pMap.put("gsdm",gsdm);
            list2 = spzService.findAllByParams(pMap);
        }
        if(StringUtils.isNotBlank(spmc)){
            pagination.addParam("spmc",spmc);
        }
        pagination.addParam("gsdm",gsdm);
        if (list2.size()==0) {
            if(xfid !=null){
                //根据纳税人类型判断适用配型
                List sylx = null;
                Xf xf = xfJpaDao.findOneById(xfid);
                String ybnsrqssj = xf.getYbnsrqssj();//一般纳税人起始时间
                String ybnsrjyzs = xf.getYbnsrjyzs() ==null ?"2":xf.getYbnsrjyzs();//一般纳税人简易征收
                Date ybnsrqssjDate = null;
                if(org.apache.commons.lang.StringUtils.isNotBlank(ybnsrqssj)){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
                    ybnsrqssjDate = sdf.parse(ybnsrqssj);
                }
                //如果是小规模纳税人
                if(ybnsrqssjDate==null || !ybnsrqssjDate.before(new Date())){
                    sylx = Arrays.asList(XGM_NSR);
                }else{
                    switch (ybnsrjyzs){
                        case "2":
                            sylx = Arrays.asList(YB_NSR);
                            break;
                        case "3":
                            sylx = Arrays.asList(JYZS_NSR);
                            break;
                        case "4":
                            sylx = Arrays.asList(BFJYZS_NSR);
                            break;
                        default:
                            break;
                    }
                }
                pagination.addParam("sylxList",sylx);
            }
            list2 = spvoService.findAllOnPage(pagination);
        }
        int total = pagination.getTotalRecord();
        result.put("recordsTotal", total);
        result.put("recordsFiltered", total);
        result.put("draw", draw);
        result.put("data", list2);
        return result;
    }

    /**
     * 保存手工开具数据
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Map save() {
        Map<String, Object> result = new HashMap<String, Object>();
        String gsdm = getGsdm();
        String xfid = request.getParameter("xf");
        int yhid = getYhid();
        Xf xf = xfService.findOne(Integer.parseInt(xfid));
        Jyxxsq jyxxsq = new Jyxxsq();
        jyxxsq.setClztdm("00");
        jyxxsq.setDdh(request.getParameter("ddh"));
        jyxxsq.setGfsh(request.getParameter("gfsh"));
        jyxxsq.setGfmc(request.getParameter("gfmc"));
        jyxxsq.setGfyh(request.getParameter("gfyh"));
        jyxxsq.setGfyhzh(request.getParameter("yhzh"));
        jyxxsq.setGfsjh(request.getParameter("lxdh"));
        //jyxxsq.setGflxr(request.getParameter("gflxr"));
        jyxxsq.setBz(request.getParameter("bz"));
        jyxxsq.setGfemail(request.getParameter("yjdz"));
        jyxxsq.setGfdz(request.getParameter("gfdz"));
        jyxxsq.setGfdh(request.getParameter("gfdh"));
        jyxxsq.setZtbz("3");//'状态标识 0 待提交,1已申请,2退回,3已处理,4删除,5部分处理,6待处理'
        jyxxsq.setSjly("0");//0平台录入，1接口接入
        String tqm = request.getParameter("tqm");
        if (StringUtils.isNotBlank(tqm)) {
            Map params = new HashMap();
            params.put("gsdm", gsdm);
            params.put("tqm", tqm);
            Jyxxsq tmp = jyxxsqService.findOneByParams(params);
            if (tmp != null) {
                result.put("failure", true);
                result.put("msg", "提取码已经存在");
                return result;
            }
        }
        jyxxsq.setTqm(tqm);
        jyxxsq.setJylsh("JY" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
        jyxxsq.setJshj(Double.parseDouble(request.getParameter("jshj")));
        jyxxsq.setYkpjshj(0.00);
        jyxxsq.setHsbz("1");
        jyxxsq.setXfid(xf.getId());
        jyxxsq.setXfsh(xf.getXfsh());
        jyxxsq.setXfmc(xf.getXfmc());
        jyxxsq.setLrsj(TimeUtil.getNowDate());
        jyxxsq.setXgsj(TimeUtil.getNowDate());
        jyxxsq.setDdrq(TimeUtil.getNowDate());
        jyxxsq.setFpzldm(request.getParameter("fpzldm"));
        jyxxsq.setFpczlxdm("11");
        String skpid = request.getParameter("kpd");
        if (skpid != null && !"".equals(skpid)) {
            jyxxsq.setSkpid(Integer.parseInt(skpid));
        }
        Skp skp = skpService.findOne(Integer.valueOf(skpid));
        jyxxsq.setKpddm(skp.getKpddm());
        Map xfxxmap=GetXfxx.getXfxx(xf,skp);
        jyxxsq.setXfdz(xfxxmap.get("xfdz").toString());
        jyxxsq.setXfdh(xfxxmap.get("xfdh").toString());
        jyxxsq.setXfyh(xfxxmap.get("xfyh").toString());
        jyxxsq.setXfyhzh(xfxxmap.get("xfyhzh").toString());
        jyxxsq.setSkr(xfxxmap.get("skr").toString());
        jyxxsq.setKpr(xfxxmap.get("kpr").toString());
        jyxxsq.setFhr(xfxxmap.get("fhr").toString());
        if (StringUtils.isNotBlank(jyxxsq.getGfemail())) {
            jyxxsq.setSffsyj("1");
        }
        jyxxsq.setSsyf(new SimpleDateFormat("yyyyMM").format(new Date()));
        jyxxsq.setLrry(yhid);
        jyxxsq.setXgry(yhid);
        jyxxsq.setYxbz("1");
        jyxxsq.setGsdm(gsdm);
        jyxxsq.setZsfs("0");
        try {
            Map params = Tools.getParameterMap(request);
            int mxcount = Integer.valueOf(params.get("mxcount").toString());//明细条数
            String[] spbms = ((String) params.get("spbm")).split(",",-1);//商品编码
            String[] spmcs = ((String) params.get("spmc")).split(",",-1);//商品名称
            String[] spjes = ((String) params.get("spje")).split(",",-1);//商品金额
            String[] taxrates = ((String) params.get("taxrate")).split(",",-1);//税率
            //String[] jshjs = ((String) params.get("jshj")).split(",",-1);//价税合计
            String[] ggxhs = ((String) params.get("ggxh")).split(",",-1);//规格型号
            String[] spsls = ((String) params.get("spsl")).split(",",-1);//商品数量
            String[] spdws = ((String) params.get("spdw")).split(",",-1);//商品单位
            String[] spdjs = ((String) params.get("spdj")).split(",",-1);//商品单价
            String[] spses = ((String) params.get("spse")).split(",",-1);//商品税额
            String[] yhzcbs = ((String) params.get("yhzcbs")).split(",",-1);//优惠政策标识
            String[] yhzcmc = ((String) params.get("yhzcmc")).split(",",-1);//优惠政策名称
            String[] lslbz = ((String) params.get("lslbz")).split(",",-1);//零税率
            double jshj = 0.00;
            List<Jymxsq> jymxsqList = new ArrayList<>();
            for (int c = 0; c < mxcount; c++) {
                Jymxsq jymxsq = new Jymxsq();
                int xxh = c + 1;
                jymxsq.setDdh(request.getParameter("ddh"));
                jymxsq.setSpmxxh(xxh);
                jymxsq.setFphxz("0");
                jymxsq.setSpdm(spbms[c]);
                jymxsq.setSpmc(spmcs[c]);
                //jymxsq.setSpje(Double.valueOf(spjes[c])-Double.valueOf(spses[c]));
                //修改商品明细商品金额为含税金额
                jymxsq.setSpje(Double.valueOf(spjes[c]));
                if (taxrates.length != 0) {
                    jymxsq.setSpsl(Double.valueOf(taxrates[c]));//商品税率
                }
                jymxsq.setJshj(Double.valueOf(spjes[c]));//价税合计
                jymxsq.setKkjje(Double.valueOf(spjes[c]));
                jymxsq.setYkjje(0d);
                if (ggxhs.length != 0) {
                    try {
                        jymxsq.setSpggxh(ggxhs[c]);
                    } catch (Exception e) {
                        jymxsq.setSpggxh(null);

                    }
                }
                try {
                    jymxsq.setSps(Double.parseDouble(spsls[c]));
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
                jymxsq.setLrry(yhid);
                jymxsq.setYxbz("1");
                jymxsq.setLrsj(TimeUtil.getNowDate());
                jymxsq.setXgsj(TimeUtil.getNowDate());
                jymxsq.setXgry(yhid);
                jymxsq.setGsdm(gsdm);
                if(yhzcbs.length==0||yhzcmc==null){
                    jymxsq.setYhzcbs("");
                }else{
                    jymxsq.setYhzcbs(yhzcbs[c]);
                }
                if(yhzcmc.length==0||yhzcmc==null){
                    jymxsq.setYhzcmc("");
                }else{
                    jymxsq.setYhzcmc(URLDecoder.decode(yhzcmc[c],"utf8"));
                }
                if(lslbz.length==0){
                    jymxsq.setLslbz("");
                }else{
                    jymxsq.setLslbz(lslbz[c]);
                }
                jshj += jymxsq.getJshj();
                jymxsqList.add(jymxsq);
            }
            jyxxsq.setJshj(jshj);
            String sfbx=request.getParameter("sfbx");
            //String errormessage=this.checkall(jyxxsq,jymxsqList,sfbx);
            List<Jyxxsq> jyxxsqList = new ArrayList<>();
            jyxxsqList.add(jyxxsq);
            String errormessage = checkOrderUtil.checkOrders(jyxxsqList,jymxsqList,null,getGsdm(),null);
            if(("").equals(errormessage)||errormessage==null){

                //jyxxsqservice.saveJyxxsq(jyxxsq, jymxsqList);
                //处理折扣行数据
//                List<JymxsqCl> jymxsqClList = new ArrayList<JymxsqCl>();
                //复制一个新的list用于生成处理表
//                List<Jymxsq> jymxsqTempList = new ArrayList<Jymxsq>();
//                jymxsqTempList = BeanConvertUtils.convertList(jymxsqList, Jymxsq.class);
//
//                jymxsqClList = discountDealUtil.dealDiscount(jymxsqTempList, 0d, jshj,jyxxsq.getHsbz());
//                 Integer sqlsh=jyxxsqService.saveJyxxsq(jyxxsq, jymxsqList,jymxsqClList,new ArrayList<Jyzfmx>());
//                List<JymxsqCl> JymxsqCllist= discountDealUtil.dealDiscount(jymxsqList,0d,0d) ;
//                zjkp(sqlsh);
                Map kpMap = new HashMap();
                kpMap.put("jyxxsqList",jyxxsqList);
                kpMap.put("jymxsqList",jymxsqList);
                kpMap.put("jyzfmxList",new ArrayList<>() );
                //01 开票，02 上传数据 不开票
                String kpresult = kpService.uploadOrderData(gsdm, kpMap,"01");
                result.put("success", true);
                result.put("djh", jyxxsq.getSqlsh());
                result.put("msg", kpresult);
            }else{
                result.put("failure", true);
                result.put("msg", errormessage);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            result.put("failure", true);
            result.put("msg", "保存出现错误: " + ex.getMessage());
        }
        return result;
    }

    private String checkall(Jyxxsq jyxxsq,List<Jymxsq>jymxsqList ,String sfbx) {
        String msgg="";
        String msg="";
        if (jyxxsq.getFpzldm().equals("01")) {
            if (jyxxsq.getGfsh() == null || jyxxsq.getGfsh().equals("")) {
                msgg = "专票购方税号为空，请重新填写！";
                msg += msgg;
            }
            if (StringUtils.isBlank(jyxxsq.getGfyh()) && StringUtils.isBlank(jyxxsq.getGfyhzh())) {
                msgg = "专票购方银行及账号都为空，请重新填写！";
                msg += msgg;
            }
            if (StringUtils.isBlank(jyxxsq.getGfdz()) && StringUtils.isBlank(jyxxsq.getGfdh())) {
                msgg = "专票购方地址及电话都为空，请重新填写！";
                msg += msgg;
            }
            if (jyxxsq.getGfsh() != null && (jyxxsq.getGfsh().length() != 15 &&jyxxsq.getGfsh().length() !=18&&jyxxsq.getGfsh().length() !=20)) { // 购方税号长度的判断
                msgg = "购方税号不是15，18，20位，请重新填写！";
                msg += msgg;
            }
        } else {
            if(sfbx.equals("1")){
                if (jyxxsq.getGfsh() != null && (jyxxsq.getGfsh().length() != 15 &&jyxxsq.getGfsh().length() !=18&& jyxxsq.getGfsh().length() !=20)) { // 购方税号长度的判断
                    msgg = "购方税号不是15，18，20位，请重新填写！";
                    msg += msgg;
                }
            }
        }
        if (jyxxsq.getXfyhzh().length() > 30) {
            msgg = "销方银行超出30个字符，请重新在平台维护！";
            msg += msgg;
        }
        String skr = jyxxsq.getSkr();// 收款人校验
        if (skr != null && skr.length() > 10) {
            msgg = "收款人超出10个字符，请重新在平台维护！";
            msg += msgg;
        }
        String fhr = jyxxsq.getFhr();// 复核人校验
        if (fhr != null && fhr.length() > 10) {
            msgg = "复核人超出10个字符，请重新在平台维护！";
            msg += msgg;
        }
        String kpr = jyxxsq.getKpr();// 开票人校验
        if (kpr != null && kpr.length() > 10) {
            msgg = "开票人超出10个字符，请重新在平台维护！";
            msg += msgg;
        }
        String xfdh = jyxxsq.getXfdh();// 销方电话校验
        if (xfdh == null || "".equals(xfdh)) {
            msgg = "销方电话没有填写，请重新填写！";
            msg += msgg;
        } else if (xfdh.length() > 25) {
            msgg = "销方电话超出25个字符，请重新填写！";
            msg += msgg;
        }
        String xfdz = jyxxsq.getXfdz();// 销方地址的校验
        if (xfdz == null || "".equals(xfdz)) {
            msgg = "销方地址没有填写，请重新填写！";
            msg += msgg;
        } else if (xfdz.length() > 100) {
            msgg = "销方地址超出100个字符，请重新填写！";
            msg += msgg;
        }
        String ddh = jyxxsq.getDdh();// 订单号的校验
        if (ddh.length() > 20) {
            msgg = "订单号超出20个字符，请重新填写！";
            msg += msgg;
        }
        String gfmc = jyxxsq.getGfmc();// 购方名称校验
        if (gfmc != null && gfmc.length() > 100) { // 购方名称长度的判断
            msgg = "购方名称超出100个字符，请重新填写！";
            msg += msgg;
        }
        String gfyh = jyxxsq.getGfyh();// 购方银行校验
        if (gfyh != null && gfyh.length() > 50) { // 购方银行长度的判断
            msgg = "购方银行超出50个字符，请重新填写！";
            msg += msgg;
        }
        String gfyhzh = jyxxsq.getGfyhzh();// 购方银行账号校验
        if (gfyhzh != null && gfyhzh.length() > 50) { // 购方银行账号长度的判断
            msgg = "购方银行账号超出50个字符，请重新填写！";
            msg += msgg;
        }
        String gfdz = jyxxsq.getGfdz();// 购方地址校验
        if (gfdz != null && gfdz.length() > 100) { // 购方地址长度的判断
            msgg = "购方地址超出200个字符，请重新填写！";
            msg += msgg;
        }
        String gfdh = jyxxsq.getGfdh();// 购方电话校验
        if (gfdh != null && gfdh.length() > 25) { // 购方电话长度的判断
            msgg = "购方电话超出25位，请重新填写！";
            msg += msgg;
        }
        String gfEmail = jyxxsq.getGfemail();// 购方email校验
        if (gfEmail != null && !"".equals(gfEmail.trim()) && !gfEmail.matches("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$")) {
            msgg = "购方email格式不正确，请重新填写！";
            msg += msgg;
        }
        for(int i=0;i<jymxsqList.size();i++){
            Jymxsq mxsq = jymxsqList.get(i);
            String spdm = mxsq.getSpdm();
            if(spdm==null || "".equals(spdm)){
                msgg = "第" + (i + 1) + "行商品代码不能为空，请重新填写！";
                msg += msgg;
                break;
            }
            if (spdm != null && spdm.length() > 20) {
                msgg = "第" + (i + 1) + "行商品代码超过20个字符，请重新填写！";
                msg += msgg;
                break;
            }
            String spmc = mxsq.getSpmc();
            if (spmc == null || "".equals(spmc)) {
                msgg = "第" + (i + 1) + "行商品名称不能为空，请重新填写！";
                msg += msgg;
                break;
            } else if (spmc.length() > 50) {
                msgg = "第" + (i + 1) + "行商品名称超过50个字符，请重新填写！";
                msg += msgg;
                break;
            }
            String spggxh = mxsq.getSpggxh();
            if (spggxh != null && spggxh.length() > 36) {
                msgg = "第" + (i + 1) + "行商品规格型号超过36个字符，请重新填写！";
                msg += msgg;
                break;
            }
            String spdw = mxsq.getSpdw();
            if (spdw != null && spdw.length() > 5) {
                msgg = "第" + (i + 1) + "行商品单位超过5个字符，请重新填写！";
                msg += msgg;
                break;
            }

            Double spje = mxsq.getSpje();
            if (spje == null || spje <= 0) {
                msgg = "第" + (i + 1) + "行商品金额不能为空或小于等于0，请重新填写！";
                msg += msgg;
                break;
            }
            Double spse = mxsq.getSpse();
            if (spse == null || spse < 0) {
                    msgg = "第" + (i + 1) + "行不含税商品税额不能为空或小于等于0，请重新填写！";
                    msg += msgg;
                    break;
            }
        }
        return msg;
    }

    /**
     * 直接开票
     */
    public void zjkp(Integer sqlsh) throws Exception {
            List<Object> result = new ArrayList<>();
            boolean sfqzfp = false;//是否强制分票  默认强制分票
            // 转换明细
            Jyxxsq jyxxsq=jyxxsqService.findOne(sqlsh);
            Map<String, Object> params1 = new HashMap<>();
            params1.put("sqlsh", jyxxsq.getSqlsh());
            List<JyspmxDecimal2> jyspmxs = jyspmxService.getNeedToKP4(params1);
            // 价税分离
            if ("1".equals(jyxxsq.getHsbz())) {
                jyspmxs = SeperateInvoiceUtils.separatePrice2(jyspmxs);
            }

        Map params = invoiceSplitParamsUtil.getInvoiceSplitParams(jyxxsq);
        String hsbz = String.valueOf(params.get("hsbz"));//确定是否含税分票，目前只支持不含税。
        double zdje = Double.valueOf(params.get("zdje").toString());//开票限额
        double fpje = Double.valueOf(params.get("fpje").toString());//分票金额
        int fphs1 = Integer.valueOf(params.get("fphs1").toString());//纸票分票行数
        int fphs2 = Integer.valueOf(params.get("fphs2").toString());//电子票分票行数
        sfqzfp = Boolean.valueOf(params.get("sfqzfp").toString());//是否强制分票
        boolean spzsfp = Boolean.valueOf(params.get("spzsfp").toString());//是否整数分票

            List<JyspmxDecimal2> splitKpspmxs  = new ArrayList<JyspmxDecimal2>();
            Map mapResult = new HashMap();
            mapResult = InvoiceSplitUtils.dealDiscountLine(jyspmxs);
            if (hsbz.equals("1")) {
                // 分票
                if (jyxxsq.getFpzldm().equals("12")) {
                    InvoiceSplitUtils.splitInvoiceshs((List)mapResult.get("jymxsqs"), (Map)mapResult.get("zkAndbzk"), new BigDecimal(Double.valueOf(zdje)), new BigDecimal(fpje), fphs2, sfqzfp, spzsfp, 0, splitKpspmxs);
                } else {
                    InvoiceSplitUtils.splitInvoiceshs((List)mapResult.get("jymxsqs"), (Map)mapResult.get("zkAndbzk"), new BigDecimal(Double.valueOf(zdje)), new BigDecimal(fpje), fphs1, sfqzfp, spzsfp, 0, splitKpspmxs);
                }
            } else {
                if (jyxxsq.getFpzldm().equals("12")) {
                    InvoiceSplitUtils.splitInvoices((List)mapResult.get("jymxsqs"), (Map)mapResult.get("zkAndbzk"), new BigDecimal(Double.valueOf(zdje)), jyxxsq.getZsfs(), new BigDecimal(fpje), fphs2, sfqzfp, spzsfp, 0, splitKpspmxs);
                } else {
                    InvoiceSplitUtils.splitInvoices((List)mapResult.get("jymxsqs"), (Map)mapResult.get("zkAndbzk"), new BigDecimal(Double.valueOf(zdje)), jyxxsq.getZsfs(), new BigDecimal(fpje), fphs1, sfqzfp, spzsfp, 0, splitKpspmxs);
                }
            }

            // 保存进交易流水
            Map<Integer, List<JyspmxDecimal2>> fpMap = new HashMap<>();
            for (JyspmxDecimal2 jyspmx : splitKpspmxs) {
                int fpnum = jyspmx.getFpnum();
                List<JyspmxDecimal2> list2 = fpMap.get(fpnum);
                if (list2 == null) {
                    list2 = new ArrayList<>();
                    fpMap.put(fpnum, list2);
                }
                list2.add(jyspmx);
            }
            //fpnum和kplsh对应关系
            Map<Integer, Integer> fpNumKplshMap = new HashMap<>();
            //保存开票信息
            //int i = 1;
            for (Map.Entry<Integer, List<JyspmxDecimal2>> entry : fpMap.entrySet()) {
                int fpNum = entry.getKey();
                List<JyspmxDecimal2> fpJyspmxList = entry.getValue();
                Jyls jyls = saveJyls(jyxxsq, fpJyspmxList);
                jyxxsq.setZtbz("3");
                jyxxsq.setXgsj(new Date());
                jyxxsqService.save(jyxxsq);
                List<Jyspmx> list2 = saveJyspmx(jyls, fpJyspmxList);
                //保存开票流水
                Kpls kpls = saveKpls(jyls, list2, jyxxsq.getSfdy());
                saveKpspmx(kpls, list2);
                //区分开票方式
                Cszb cszb = cszbService.getSpbmbbh(getGsdm(), jyxxsq.getXfid(), jyxxsq.getSkpid(), "kpfs");
                if(cszb != null && cszb.getCsz().equals("01")){
                    skService.callService(kpls.getKplsh());
                }
                if(cszb != null && cszb.getCsz().equals("03")){
                    if(kpls.getGsdm().equals("afb")){
                        skService.SkServerKPhttps(kpls.getKplsh());
                    }else{
                        skService.SkServerKP(kpls.getKplsh());
                    }
                }
               if("04".equals(cszb.getCsz())&&cszb != null ){
                   skService.SkBoxKP(kpls.getKplsh());
               }
                if("05".equals(cszb.getCsz())&&cszb != null ){
                    skService.skEkyunKP(kpls.getKplsh());
                }
            }

        //return result;
    }

    /**
     * 保存开票商品明细
     *
     * @param kpls
     * @param
     * @return
     */
    public void saveKpspmx(Kpls kpls, List<Jyspmx> jyspmx1) throws Exception {
        int kplsh = kpls.getKplsh();
        for (Jyspmx jyspmx : jyspmx1) {
            Kpspmx kpspmx = new Kpspmx();
            kpspmx.setKplsh(kplsh);
            kpspmx.setDjh(jyspmx.getDjh());
            kpspmx.setSpmxxh(jyspmx.getSpmxxh());
            kpspmx.setSpdm(jyspmx.getSpdm());
            kpspmx.setSpmc(jyspmx.getSpmc());
            kpspmx.setFphxz(jyspmx.getFphxz());
            kpspmx.setSpggxh(jyspmx.getSpggxh());
            if (jyspmx.getSpdj() != null) {
                kpspmx.setSpdj(jyspmx.getSpdj().doubleValue());
            }
            kpspmx.setSpdw(jyspmx.getSpdw());
            if (jyspmx.getSps() != null) {
                kpspmx.setSps(jyspmx.getSps().doubleValue());
            }
            kpspmx.setSpse(jyspmx.getSpse().doubleValue());
            kpspmx.setSpje(jyspmx.getSpje().doubleValue());
            kpspmx.setSpsl(jyspmx.getSpsl().doubleValue());
            kpspmx.setGsdm(kpls.getGsdm());
            kpspmx.setLrry(kpls.getLrry());
            kpspmx.setXgry(kpls.getXgry());
            kpspmx.setLrsj(TimeUtil.getNowDate());
            kpspmx.setXgsj(TimeUtil.getNowDate());
            kpspmx.setKhcje(jyspmx.getJshj().doubleValue());
            if (null == jyspmx.getKce()) {
                kpspmx.setKce(0d);
            } else {
                kpspmx.setKce(jyspmx.getKce().doubleValue());
            }
            kpspmx.setYhzcbs(jyspmx.getYhzcbs());
            kpspmx.setYhzcmc(jyspmx.getYhzcmc());
            kpspmx.setLslbz(jyspmx.getLslbz());
            kpspmx.setYhcje(0d);
            kpspmxService.save(kpspmx);
        }
    }


    /**
     * 保存开票流水
     *
     * @param jyls
     * @return
     */
    public Kpls saveKpls(Jyls jyls, List<Jyspmx> jyspmx1, String dybz) throws Exception {
        Kpls kpls = new Kpls();
        kpls.setDjh(jyls.getDjh());
        kpls.setJylsh(jyls.getJylsh());
        kpls.setJylssj(jyls.getJylssj());
        kpls.setGsdm(jyls.getGsdm());
        kpls.setLrry(jyls.getLrry());
        kpls.setLrsj(TimeUtil.getNowDate());
        kpls.setXgry(jyls.getXgry());
        kpls.setXgsj(TimeUtil.getNowDate());
        kpls.setBz(jyls.getBz());
        kpls.setFpczlxdm(jyls.getFpczlxdm());
        kpls.setFpzldm(jyls.getFpzldm());
        kpls.setGfdh(jyls.getGfdh());
        kpls.setGfdz(jyls.getGfdz());
        kpls.setZsfs(jyls.getZsfs());
        if (dybz != null && dybz.equals("1")) {
            kpls.setPrintflag("2");
        } else {
            kpls.setPrintflag("0");
        }
        kpls.setGfmc(jyls.getGfmc());
        kpls.setGfsh(jyls.getGfsh());
        kpls.setGfyh(jyls.getGfyh());
        kpls.setGfyhzh(jyls.getGfyhzh());
        kpls.setGfemail(jyls.getGfemail());
        kpls.setGflxr(jyls.getGflxr());
        kpls.setFhr(jyls.getFhr());
        kpls.setKpr(jyls.getKpr());
        kpls.setSkr(jyls.getSkr());
        kpls.setXfid(jyls.getXfid());
        kpls.setXfsh(jyls.getXfsh());
        kpls.setXfmc(jyls.getXfmc());
        kpls.setXfdz(jyls.getXfdz());
        kpls.setXfdh(jyls.getXfdh());
        kpls.setXfyh(jyls.getXfyh());
        kpls.setXfyhzh(jyls.getXfyhzh());
        String fpczlxdm = jyls.getFpczlxdm();
        if ("12".equals(fpczlxdm) || "13".equals(fpczlxdm) || "23".equals(fpczlxdm)) {
            //红冲或换开操作
            kpls.setHzyfpdm(jyls.getYfpdm());
            kpls.setHzyfphm(jyls.getYfphm());
            kpls.setHcrq(jyls.getLrsj());
            kpls.setHcry(jyls.getLrry());
            if ("12".equals(fpczlxdm)) {
                kpls.setHkbz("0");
            } else if ("13".equals(fpczlxdm)) {
                kpls.setHkbz("1");
            }
        }
        double hjje = 0;
        double hjse = 0;
        for (Jyspmx jyspmx : jyspmx1) {
            hjje += jyspmx.getSpje().doubleValue();
            hjse += jyspmx.getSpse().doubleValue();
        }
        double jshj = hjje + hjse;
        kpls.setHjje(hjje);
        kpls.setHjse(hjse);
        kpls.setJshj(jshj);
        kpls.setSfdyqd(jyls.getSfdyqd());
        kpls.setYxbz("1");
        kpls.setFpztdm("14");
        kpls.setSkpid(jyls.getSkpid());
        kpls.setSerialorder(jyls.getJylsh()+jyls.getDdh()+jyls.getGsdm());
        kplsService.save(kpls);
        return kpls;
    }
    /**
     * 保存交易流水`
     *
     * @param
     * @return
     */
    public Jyls saveJyls(Jyxxsq jyxxsq, List<JyspmxDecimal2> jyspmxList) throws Exception {
        Jyls jyls1 = new Jyls();
        jyls1.setDdh(jyxxsq.getDdh());
        jyls1.setJylsh(jyxxsq.getJylsh());
        jyls1.setJylssj(TimeUtil.getNowDate());
        jyls1.setFpzldm(jyxxsq.getFpzldm());
        jyls1.setFpczlxdm("11");
        jyls1.setXfid(jyxxsq.getXfid());
        jyls1.setXfsh(jyxxsq.getXfsh());
        jyls1.setXfmc(jyxxsq.getXfmc());
        jyls1.setXfyh(jyxxsq.getXfyh());
        jyls1.setTqm(jyxxsq.getTqm());
        jyls1.setXfyhzh(jyxxsq.getXfyhzh());
        jyls1.setXflxr(jyxxsq.getXflxr());
        jyls1.setXfdh(jyxxsq.getXfdh());
        jyls1.setXfdz(jyxxsq.getXfdz());
        jyls1.setGfid(jyxxsq.getGfid());
        jyls1.setGfsh(jyxxsq.getGfsh());
        jyls1.setGfmc(jyxxsq.getGfmc());
        jyls1.setGfyh(jyxxsq.getGfyh());
        jyls1.setGfyhzh(jyxxsq.getGfyhzh());
        jyls1.setGflxr(jyxxsq.getGflxr());
        jyls1.setGfdh(jyxxsq.getGfdh());
        jyls1.setGfdz(jyxxsq.getGfdz());
        jyls1.setGfyb(jyxxsq.getGfyb());
        jyls1.setGfemail(jyxxsq.getGfemail());
        jyls1.setGfsjh(jyxxsq.getGfsjh());
        jyls1.setClztdm("40");
        jyls1.setBz(jyxxsq.getBz());
        jyls1.setSkr(jyxxsq.getSkr());
        jyls1.setKpr(jyxxsq.getKpr());
        jyls1.setFhr(jyxxsq.getFhr());
        jyls1.setSsyf(jyxxsq.getSsyf());
        jyls1.setSffsyj(jyxxsq.getSffsyj());
        jyls1.setYfpdm(null);
        jyls1.setYfphm(null);
        jyls1.setHsbz(jyxxsq.getHsbz());
        double hjje = 0;
        double hjse = 0;
        for (JyspmxDecimal2 jyspmx : jyspmxList) {
            hjje += jyspmx.getSpje().doubleValue();
            hjse += jyspmx.getSpse().doubleValue();
        }
        jyls1.setJshj(hjje + hjse);
        jyls1.setYkpjshj(0d);
        jyls1.setYxbz("1");
        jyls1.setGsdm(jyxxsq.getGsdm());
        jyls1.setLrry(jyxxsq.getLrry());
        jyls1.setLrsj(TimeUtil.getNowDate());
        jyls1.setXgry(jyxxsq.getXgry());
        jyls1.setXgsj(TimeUtil.getNowDate());
        jyls1.setSkpid(jyxxsq.getSkpid());
        jyls1.setSqlsh(jyxxsq.getSqlsh());
        jyls1.setZsfs(jyxxsq.getZsfs());
        jylsService.save(jyls1);
        return jyls1;
    }

    public List<Jyspmx> saveJyspmx(Jyls jyls, List<JyspmxDecimal2> fpJyspmxList) throws Exception {
        int djh = jyls.getDjh();
        List<Jyspmx> list = new ArrayList<>();
        for (JyspmxDecimal2 mxItem : fpJyspmxList) {
            Jyspmx jymx = new Jyspmx();
            jymx.setDjh(djh);
            jymx.setSpmxxh(mxItem.getSpmxxh());
            jymx.setSpdm(mxItem.getSpdm());
            jymx.setSpmc(mxItem.getSpmc());
            jymx.setSpggxh(mxItem.getSpggxh());
            jymx.setSpdw(mxItem.getSpdw());
            jymx.setSps(mxItem.getSps() == null ? null : mxItem.getSps().doubleValue());
            jymx.setSpdj(mxItem.getSpdj() == null ? null : mxItem.getSpdj().doubleValue());
            jymx.setSpje(mxItem.getSpje() == null ? null : mxItem.getSpje().doubleValue());
            jymx.setSpsl(mxItem.getSpsl().doubleValue());
            jymx.setSpse(mxItem.getSpse() == null ? null : mxItem.getSpse().doubleValue());
            jymx.setJshj(mxItem.getJshj() == null ? null : mxItem.getJshj().doubleValue());
            jymx.setYkphj(0d);
            jymx.setGsdm(jyls.getGsdm());
            jymx.setLrsj(TimeUtil.getNowDate());
            jymx.setLrry(jyls.getLrry());
            jymx.setXgsj(TimeUtil.getNowDate());
            jymx.setXgry(jyls.getXgry());
            jymx.setFphxz("0");
            if (null == mxItem.getKce()) {
                jymx.setKce(0d);
            } else {
                jymx.setKce(mxItem.getKce().doubleValue());
            }
            jymx.setYhzcbs(mxItem.getYhzcbs());
            jymx.setYhzcmc(mxItem.getYhzcmc());
            jymx.setLslbz(mxItem.getLslbz());
            jyspmxService.save(jymx);
            list.add(jymx);
        }
        return list;
    }
    @RequestMapping(value = "/findjyxxsq")
    @ResponseBody
    public Map findjyxxsq(String ddh){
       Map resultMap=new HashMap();
       Map parms=new HashMap();
       parms.put("ddh",ddh);
       parms.put("gsdm",getGsdm());
       Jyxxsq jyxxsq=jyxxsqService.findOneByParams(parms);
       List<Jymxsq>jymxsqlist=null;
        List<Jyzfmxvo>jyzfmxList=null;
       if(jyxxsq!=null){
           parms.put("sqlsh",jyxxsq.getSqlsh());
           jymxsqlist=jymxsqService.findAllByParams(parms);
           jyzfmxList=jyzfmxService.findAllByParamsVo(parms);
       }
       resultMap.put("jyxxsq",jyxxsq);
       resultMap.put("jymxsq",jymxsqlist);
       resultMap.put("jyzfmx",jyzfmxList);
        return resultMap;
    }
}
