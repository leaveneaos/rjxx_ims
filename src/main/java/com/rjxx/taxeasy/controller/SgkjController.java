package com.rjxx.taxeasy.controller;

import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.bizcomm.utils.SeperateInvoiceUtils;
import com.rjxx.taxeasy.domains.*;
import com.rjxx.taxeasy.filter.SystemControllerLog;
import com.rjxx.taxeasy.service.*;
import com.rjxx.taxeasy.vo.JyspmxDecimal2;
import com.rjxx.taxeasy.vo.KplsVO4;
import com.rjxx.taxeasy.vo.Spvo;
import com.rjxx.taxeasy.web.BaseController;
import com.rjxx.time.TimeUtil;
import com.rjxx.utils.StringUtils;
import com.rjxx.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

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
    private FpgzService fpgzService;


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
        return "sgkj/index";
    }
    // 查询方法
    @RequestMapping(value = "/getItems")
    @ResponseBody
    public Map<String, Object> getItems(int length, int start, int draw) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        Pagination pagination = new Pagination();
        pagination.setPageNo(start / length + 1);
        pagination.setPageSize(length);
        String gsdm = getGsdm();
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
        result.put("recordsTotal", list2.size());
        result.put("recordsFiltered", list2.size());
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
        //jyxxsq.setGfsjh(request.getParameter("gfsjh"));
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
        String skpid = request.getParameter("kpd");
        if (skpid != null && !"".equals(skpid)) {
            jyxxsq.setSkpid(Integer.parseInt(skpid));
        }
        Skp skp = skpService.findOne(Integer.valueOf(skpid));
        jyxxsq.setKpddm(skp.getKpddm());
        try {
            Map params = Tools.getParameterMap(request);
            int mxcount = Integer.valueOf(params.get("mxcount").toString());//明细条数
            String[] spbms = ((String) params.get("spbm")).split(",");//商品编码
            String[] spmcs = ((String) params.get("spmc")).split(",");//商品名称
            String[] spjes = ((String) params.get("spje")).split(",");//商品金额
            String[] taxrates = ((String) params.get("taxrate")).split(",");//税率
            //String[] jshjs = ((String) params.get("jshj")).split(",");//价税合计
            String[] ggxhs = ((String) params.get("ggxh")).split(",");//规格型号
            String[] spsls = ((String) params.get("spsl")).split(",");//商品数量
            String[] spdws = ((String) params.get("spdw")).split(",");//商品单位
            String[] spdjs = ((String) params.get("spdj")).split(",");//商品单价
            String[] spses = ((String) params.get("spse")).split(",");//商品税额

            double jshj = 0.00;
            List<Jymxsq> jymxsqList = new ArrayList<>();
            for (int c = 0; c < mxcount; c++) {
                Jymxsq jymxsq = new Jymxsq();
                int xxh = c + 1;
                jymxsq.setSpmxxh(xxh);
                jymxsq.setFphxz("0");
                jymxsq.setSpdm(spbms[c]);
                jymxsq.setSpmc(spmcs[c]);
                jymxsq.setSpje(Double.valueOf(spjes[c])-Double.valueOf(spses[c]));
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
                    jymxsq.setSps(Double.valueOf(spsls[c]));
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

                jshj += jymxsq.getJshj();
                jymxsqList.add(jymxsq);
            }
            jyxxsq.setJshj(jshj);
            Integer sqlsh=jyxxsqService.saveJyxxsq(jyxxsq, jymxsqList);
            zjkp(sqlsh);
            result.put("success", true);
            result.put("djh", jyxxsq.getSqlsh());
            result.put("msg", "开票申请成功！");
        } catch (Exception ex) {
            ex.printStackTrace();
            result.put("failure", true);
            result.put("msg", "保存出现错误: " + ex.getMessage());
        }
        return result;
    }

    /**
     * 直接开票
     */
    public void zjkp(Integer sqlsh) throws Exception {
            List<Object> result = new ArrayList<>();
            boolean sfqzfp = true;//是否强制分票  默认强制分票
            // 转换明细
            Jyxxsq jyxxsq=jyxxsqService.findOne(sqlsh);
            Map<String, Object> params1 = new HashMap<>();
            params1.put("sqlsh", jyxxsq.getSqlsh());
            List<JyspmxDecimal2> jyspmxs = jyspmxService.getNeedToKP3(params1);
            // 价税分离
            if ("1".equals(jyxxsq.getHsbz())) {
                jyspmxs = SeperateInvoiceUtils.separatePrice2(jyspmxs);
            }
            //取最大限额
            double zdje = 0d;
            double fpje = 0d;
            int fphs1 = 8;//专票、普票行数
            int fphs2 = 100;//电子票行数
            String hsbz = "0";
            boolean flag = false;
            Skp skp = skpService.findOne(jyxxsq.getSkpid());//取税控盘的最大金额和分票金额
            if ("01".equals(jyxxsq.getFpzldm())) {
                zdje = skp.getZpmax();
                fpje = skp.getZpfz();
            } else if ("02".equals(jyxxsq.getFpzldm())) {
                zdje = skp.getPpmax();
                fpje = skp.getPpfz();
            } else if ("12".equals(jyxxsq.getFpzldm())) {
                zdje = skp.getDpmax();
                fpje = skp.getFpfz();
            }
            List<Fpgz> listt = fpgzService.findAllByParams(new HashMap<>());
            Xf x = new Xf();
            x.setGsdm(jyxxsq.getGsdm());
            x.setXfsh(jyxxsq.getXfsh());
            Xf xf = xfService.findOneByParams(x);
            for (Fpgz fpgz : listt) {
                if (fpgz.getXfids().contains(String.valueOf(xf.getId()))) {
                    if ("01".equals(jyxxsq.getFpzldm())) {
                        fphs1 = fpgz.getZphs();
                        fpje = fpgz.getZpxe();
                    } else if ("02".equals(jyxxsq.getFpzldm())) {
                        fphs1 = fpgz.getPphs();
                        fpje = fpgz.getPpxe();
                    } else if ("12".equals(jyxxsq.getFpzldm())) {
                        fphs2 = fpgz.getDzphs();
                        fpje = fpgz.getDzpxe();
                    }
                    flag = true;
                    hsbz = fpgz.getHsbz();
                    if (fpgz.getSfqzfp().equals("0")) {
                        sfqzfp = false;
                    }
                }
            }
            if (!flag) {
                Map<String, Object> paramse = new HashMap<>();
                paramse.put("mrbz", "1");
                paramse.put("gsdm", jyxxsq.getGsdm());
                Fpgz fpgz2 = fpgzService.findOneByParams(paramse);
                if (null != fpgz2) {
                    if ("01".equals(jyxxsq.getFpzldm())) {
                        fphs1 = fpgz2.getZphs();
                        fpje = fpgz2.getZpxe();
                    } else if ("02".equals(jyxxsq.getFpzldm())) {
                        fphs1 = fpgz2.getPphs();
                        fpje = fpgz2.getPpxe();
                    } else if ("12".equals(jyxxsq.getFpzldm())) {
                        fphs2 = fpgz2.getDzphs();
                        fpje = fpgz2.getDzpxe();
                    }
                    hsbz = fpgz2.getHsbz();
                    if (fpgz2.getSfqzfp().equals("0")) {
                        sfqzfp = false;
                    }
                }
            }
            if (jyxxsq.getSfdyqd() != null && jyxxsq.getSfdyqd().equals("1")) {
                fphs1 = 99999;
                fphs2 = 99999;
            }
            if (0 == fpje) {
                fpje = zdje;
            }
            if (hsbz.equals("1")) {
                // 分票
                if (jyxxsq.getFpzldm().equals("12")) {
                    jyspmxs = SeperateInvoiceUtils.splitInvoicesbhs(jyspmxs, new BigDecimal(Double.valueOf(zdje)), new BigDecimal(fpje), fphs2, sfqzfp, false);
                } else {
                    jyspmxs = SeperateInvoiceUtils.splitInvoicesbhs(jyspmxs, new BigDecimal(Double.valueOf(zdje)), new BigDecimal(fpje), fphs1, sfqzfp, false);
                }
            } else {
                if (jyxxsq.getFpzldm().equals("12")) {
                    jyspmxs = SeperateInvoiceUtils.splitInvoices2(jyspmxs, new BigDecimal(Double.valueOf(zdje)), new BigDecimal(fpje), fphs2, sfqzfp, false);
                } else {
                    jyspmxs = SeperateInvoiceUtils.splitInvoices2(jyspmxs, new BigDecimal(Double.valueOf(zdje)), new BigDecimal(fpje), fphs1, sfqzfp, false);
                }
            }

            // 保存进交易流水
            Map<Integer, List<JyspmxDecimal2>> fpMap = new HashMap<>();
            for (JyspmxDecimal2 jyspmx : jyspmxs) {
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
               /* KplsVO4 kplsVO4 = new KplsVO4(kpls, jyxxsq);
                result.add(kplsVO4);*/
                //i++;
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
        kpls.setFpztdm("04");
        kpls.setSkpid(jyls.getSkpid());
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
        jyls1.setClztdm("00");
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
    @RequestMapping("/findjyxxsq")
    @ResponseBody
    public Map findjyxxsq(String ddh){

       Map resultMap=new HashMap();

       Map parms=new HashMap();

       parms.put("ddh",ddh);

       Jyxxsq jyxxsq=jyxxsqService.findOneByParams(parms);

       parms.put("sqlsh",jyxxsq.getSqlsh());

       List<Jymxsq>jymxsqlist=jymxsqService.findAllByParams(parms);
       resultMap.put("jyxxsq",jyxxsq);
       resultMap.put("jymxsq",jymxsqlist);

        return resultMap;
    }
}
