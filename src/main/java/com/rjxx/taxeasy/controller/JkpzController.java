package com.rjxx.taxeasy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.domain.OrderDetail;
import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.bizcomm.utils.ExportTextUtil;
import com.rjxx.taxeasy.bizcomm.utils.GetCszService;
import com.rjxx.taxeasy.dao.CszbJpaDao;
import com.rjxx.taxeasy.dao.JkmbbJpaDao;
import com.rjxx.taxeasy.dao.JkmbzbJpaDao;
import com.rjxx.taxeasy.domains.*;
import com.rjxx.taxeasy.dto.*;
import com.rjxx.taxeasy.service.*;
import com.rjxx.taxeasy.vo.*;
import com.rjxx.taxeasy.web.BaseController;
import com.rjxx.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Method;
import java.util.*;

@Controller
@RequestMapping("/jkpz")
public class JkpzController extends BaseController {

    @Autowired
    private XfService xfService;
    @Autowired
    private CszbService cszbService;
    @Autowired
    private SkpService skpService;
    @Autowired
    private JkmbbService jkmbbService;
    @Autowired
    private JkpzdmbService jkpzdmbService;
    @Autowired
    private JkmbbJpaDao jkmbbJpaDao;
    @Autowired
    private JkmbzbService jkmbzbService;
    @Autowired
    private JkmbzbJpaDao jkmbzbJpaDao;
    @Autowired
    private CszbJpaDao cszbJpaDao;
    @Autowired
    private GsxxService gsxxService;
    @Autowired
    private CsbService csbService;
    @Autowired
    private GetCszService getCszService;

    @RequestMapping
    public String index() {
        request.setAttribute("jylsh", getjkpzdmb(1));
        request.setAttribute("ddh", getjkpzdmb(2));
        request.setAttribute("ddrq", getjkpzdmb(3));
        request.setAttribute("kpddm", getjkpzdmb(4));
        request.setAttribute("fpzl", getjkpzdmb(5));
        request.setAttribute("dyqd", getjkpzdmb(6));
        request.setAttribute("zdcf", getjkpzdmb(7));
        request.setAttribute("ljdy", getjkpzdmb(8));
        request.setAttribute("zffs", getjkpzdmb(9));
        request.setAttribute("hsbz", getjkpzdmb(10));
        request.setAttribute("bz", getjkpzdmb(11));
        request.setAttribute("spbbh", getjkpzdmb(12));
        request.setAttribute("lkr", getjkpzdmb(13));
        request.setAttribute("xf", getjkpzdmb(14));
        request.setAttribute("sp", getjkpzdmb(15));
        request.setAttribute("spyh", getjkpzdmb(16));
        request.setAttribute("zf", getjkpzdmb(17));
        request.setAttribute("gf", getjkpzdmb(18));
        request.setAttribute("gsdm", getGsdm());
        Map map = new HashMap();
        List<Gsxx> gsxxList = gsxxService.findAllByParams(map);
        for (Gsxx gsxx : gsxxList) {
            gsxx.setGsdm(gsxx.getGsdm().toLowerCase());
        }
        request.setAttribute("gsxx",gsxxList);
        return "jkpz/index";
    }

    /**
     * 查询列表
     * @param length
     * @param start
     * @param draw
     * @return
     */
    @RequestMapping(value = "/getjkmbList")
    @ResponseBody
    public Map getJkmb(int length, int start, int draw,String gsdm,boolean loaddata) {
        Map<String, Object> result = new HashMap();
        Pagination pagination = new Pagination();
        pagination.setPageNo(start / length + 1);
        pagination.setPageSize(length);
        pagination.addParam("gsdm", gsdm);
        pagination.addParam("orderBy", "lrsj");
        List<JkmbbVo> list = jkmbbService.findByPage(pagination);
        for (JkmbbVo jkmbbVo : list) {
            jkmbbVo.setGsdm(jkmbbVo.getGsdm().toLowerCase());
        }
        int total = pagination.getTotalRecord();
        if(loaddata){
            result.put("recordsTotal", total);
            result.put("recordsFiltered", total);
            result.put("draw", draw);
            result.put("data", list);
        }else {
            result.put("recordsTotal", 0);
            result.put("recordsFiltered", 0);
            result.put("draw", draw);
            result.put("data", new ArrayList<>());
        }
        return result;
    }

    /**
     * 查看详情
     * @param mbid
     * @return 成功返回list
     */
    @RequestMapping(value = "/getjkmbzb")
    @ResponseBody
    public Map getjkmbzb(Integer mbid,int length, int start, int draw,boolean loaddata){
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Pagination pagination = new Pagination();
            pagination.setPageNo(start / length + 1);
            pagination.setPageSize(length);
            pagination.addParam("mbid",mbid);
            if(loaddata) {
                List<JkpzVo> list = jkmbzbService.findByPage(pagination);
                int total = pagination.getTotalRecord();
                result.put("recordsTotal", total);
                result.put("recordsFiltered", total);
                result.put("draw", draw);
                result.put("data", list);
            }else {
                result.put("recordsTotal", 0);
                result.put("recordsFiltered", 0);
                result.put("draw", draw);
                result.put("data", new ArrayList<>());
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("data",new ArrayList<>());
        }
        return  result;
    }



    @RequestMapping(value = "/getmbzb")
    @ResponseBody
    public Map getjkmbzb(String mbid){
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map map = new HashMap();
            map.put("mbid",Integer.valueOf(mbid));
                List<JkpzVo> list = jkmbzbService.findByMbId(map);
            System.out.println(JSON.toJSONString(list));
            result.put("data",list);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("data",new ArrayList<>());
        }
        return  result;
    }



    public List<Jkpzdmb> getjkpzdmb(Integer pzbid) {
        Map params = new HashMap<>();
        params.put("pzbid",pzbid);
        List<Jkpzdmb> list = jkpzdmbService.findAllByParams(params);
        return list;
    }

    /**
     * 删除模板
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    @Transactional
    public Map delete(String ids) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(ids)) {
            try {
                String[] idl = ids.split(",");
                Map map = new HashMap();
                map.put("csm","jkpzmbid");
                Csb csb = csbService.findOneByParams(map);
                for (String id : idl) {
                    List<Cszb> list1 = cszbJpaDao.findByCsidAndCsz(csb.getId(), id);
                    if(!list1.isEmpty()){
                        result.put("success", false);
                        result.put("msg", "该模板正在使用，请重新选择！");
                        return result;
                    }
                    Jkmbb jkmbb = jkmbbService.findOne(Integer.valueOf(id));
                        List<Jkmbzb> list = jkmbzbJpaDao.findListByMbid(jkmbb.getId());
                        for (Jkmbzb jkmbzb : list) {
                            jkmbzbJpaDao.delete(jkmbzb);
                        }
                        List<Cszb> cszbList = cszbJpaDao.findByGsdmAndCszAndCsid(csb.getId(), jkmbb.getGsdm(),id);
                        for (Cszb cszb1 : cszbList) {
                            cszbJpaDao.delete(cszb1);
                        }
                    jkmbbJpaDao.delete(jkmbb);
                }
                result.put("success", true);
                result.put("msg", "删除成功");
            } catch (Exception e) {
                e.printStackTrace();
                result.put("success", false);
                result.put("msg", "删除失败");
            }
        }
        return result;
    }

    //新增模板
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map bcmb(String jkpz_mbmc, String jkpz_mbms,String jkpz_gsdm,String lsh_pzbid,String jkpz_jylsh,
                    String ddh_pzbid, String jkpz_ddh,String ddrq_pzbid ,String jkpz_ddrq,
                    String kpddm_pzbid ,String jkpz_kpddm, String fpzl_pzbid , String jkpz_fpzl,
                    String dyqd_pzbid , String jkpz_dyqd, String zdcf_pzbid , String jkpz_zdcf,
                    String ljdy_pzbid , String jkpz_ljdy , String zsfs_pzbid , String jkpz_zsfs ,
                    String hsbz_pzbid , String jkpz_hsbz , String bzjkpz , String jkpz_bz ,
                    String spbmbb_pzbid , String jkpz_spbmbb , String lkr_pzbid , String jkpz_lkr,
                    String xfqxx_pzbid , String jkpz_xfqxx , String spqxx_pzbid , String jkpz_spqxx,
                    String spyhxx_pzbid ,String jkpz_spyhxx ,String zfxx_pzbid ,String jkpz_zfxx,
                    String gfxx_pzbid ,String jkpz_gfxx) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Jkmbb jkmbb = new Jkmbb();
            Date date = new Date();
            jkmbb.setGsdm(jkpz_gsdm);
            jkmbb.setMbmc(jkpz_mbmc);
            jkmbb.setMbms(jkpz_mbms);
            jkmbb.setYxbz("1");
            jkmbb.setLrsj(date);
            jkmbb.setXgry(1);
            jkmbb.setXgsj(date);
            jkmbb.setLrry(1);
            Jkmbb jkmbb1 = jkmbbJpaDao.save(jkmbb);
            bc(jkmbb1.getId(),Integer.valueOf(lsh_pzbid),Integer.valueOf(jkpz_jylsh));
            bc(jkmbb1.getId(), Integer.valueOf(ddh_pzbid), Integer.valueOf(jkpz_ddh));
            bc(jkmbb1.getId(), Integer.valueOf(ddrq_pzbid), Integer.valueOf(jkpz_ddrq));
            bc(jkmbb1.getId(), Integer.valueOf(kpddm_pzbid), Integer.valueOf(jkpz_kpddm));
            bc(jkmbb1.getId(), Integer.valueOf(fpzl_pzbid), Integer.valueOf(jkpz_fpzl));
            bc(jkmbb1.getId(), Integer.valueOf(dyqd_pzbid), Integer.valueOf(jkpz_dyqd));
            bc(jkmbb1.getId(), Integer.valueOf(zdcf_pzbid), Integer.valueOf(jkpz_zdcf));
            bc(jkmbb1.getId(), Integer.valueOf(ljdy_pzbid), Integer.valueOf(jkpz_ljdy));
            bc(jkmbb1.getId(), Integer.valueOf(zsfs_pzbid), Integer.valueOf(jkpz_zsfs));
            bc(jkmbb1.getId(), Integer.valueOf(hsbz_pzbid), Integer.valueOf(jkpz_hsbz));
            bc(jkmbb1.getId(), Integer.valueOf(bzjkpz), Integer.valueOf(jkpz_bz));
            bc(jkmbb1.getId(), Integer.valueOf(spbmbb_pzbid), Integer.valueOf(jkpz_spbmbb));
            bc(jkmbb1.getId(), Integer.valueOf(lkr_pzbid), Integer.valueOf(jkpz_lkr));
            bc(jkmbb1.getId(), Integer.valueOf(xfqxx_pzbid), Integer.valueOf(jkpz_xfqxx));
            bc(jkmbb1.getId(), Integer.valueOf(spqxx_pzbid), Integer.valueOf(jkpz_spqxx));
            bc(jkmbb1.getId(), Integer.valueOf(spyhxx_pzbid), Integer.valueOf(jkpz_spyhxx));
            bc(jkmbb1.getId(), Integer.valueOf(zfxx_pzbid), Integer.valueOf(jkpz_zfxx));
            bc(jkmbb1.getId(), Integer.valueOf(gfxx_pzbid), Integer.valueOf(jkpz_gfxx));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("msg", "保存失败!");
            result.put("success", false);
            return result;
        }
        result.put("msg", "保存成功!");
        result.put("success", true);
        return result;
    }

    public  boolean bc(Integer mbid,Integer pzbid,Integer cszffid){
        try {
            Date date = new Date();
            Jkmbzb jkmbzb = new Jkmbzb();
            jkmbzb.setMbid(mbid);
            jkmbzb.setPzbid(pzbid);
            jkmbzb.setLrsj(date);
            jkmbzb.setLrry(1);
            jkmbzb.setXgry(1);
            jkmbzb.setXgsj(date);
            jkmbzb.setCszffid(cszffid);
            jkmbzbJpaDao.save(jkmbzb);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


    //修改模板
    @RequestMapping(value = "/update")
    @ResponseBody
    public Map xgmb(String mbxxid,String jkpz_mbmc, String jkpz_mbms,String jkpz_gsdm,String lsh_pzbid,String jkpz_jylsh,
                    String ddh_pzbid, String jkpz_ddh,String ddrq_pzbid ,String jkpz_ddrq,
                    String kpddm_pzbid ,String jkpz_kpddm, String fpzl_pzbid , String jkpz_fpzl,
                    String dyqd_pzbid , String jkpz_dyqd, String zdcf_pzbid , String jkpz_zdcf,
                    String ljdy_pzbid , String jkpz_ljdy , String zsfs_pzbid , String jkpz_zsfs ,
                    String hsbz_pzbid , String jkpz_hsbz , String bzjkpz , String jkpz_bz ,
                    String spbmbb_pzbid , String jkpz_spbmbb , String lkr_pzbid , String jkpz_lkr,
                    String xfqxx_pzbid , String jkpz_xfqxx , String spqxx_pzbid , String jkpz_spqxx,
                    String spyhxx_pzbid ,String jkpz_spyhxx ,String zfxx_pzbid ,String jkpz_zfxx,
                    String gfxx_pzbid ,String jkpz_gfxx) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Date date = new Date();
            Jkmbb jkmbb = jkmbbJpaDao.findByid(Integer.valueOf(mbxxid));
            jkmbb.setMbmc(jkpz_mbmc);
            jkmbb.setMbms(jkpz_mbms);
            jkmbb.setGsdm(jkpz_gsdm);
            jkmbb.setXgsj(date);
            jkmbb.setXgry(getYhid());
            jkmbbJpaDao.save(jkmbb);
            xg(Integer.valueOf(mbxxid), Integer.valueOf(lsh_pzbid), Integer.valueOf(jkpz_jylsh));
            xg(Integer.valueOf(mbxxid), Integer.valueOf(ddh_pzbid), Integer.valueOf(jkpz_ddh));
            xg(Integer.valueOf(mbxxid), Integer.valueOf(ddrq_pzbid), Integer.valueOf(jkpz_ddrq));
            xg(Integer.valueOf(mbxxid), Integer.valueOf(kpddm_pzbid), Integer.valueOf(jkpz_kpddm));
            xg(Integer.valueOf(mbxxid), Integer.valueOf(fpzl_pzbid), Integer.valueOf(jkpz_fpzl));
            xg(Integer.valueOf(mbxxid), Integer.valueOf(dyqd_pzbid), Integer.valueOf(jkpz_dyqd));
            xg(Integer.valueOf(mbxxid), Integer.valueOf(zdcf_pzbid), Integer.valueOf(jkpz_zdcf));
            xg(Integer.valueOf(mbxxid), Integer.valueOf(ljdy_pzbid), Integer.valueOf(jkpz_ljdy));
            xg(Integer.valueOf(mbxxid), Integer.valueOf(zsfs_pzbid), Integer.valueOf(jkpz_zsfs));
            xg(Integer.valueOf(mbxxid), Integer.valueOf(hsbz_pzbid), Integer.valueOf(jkpz_hsbz));
            xg(Integer.valueOf(mbxxid), Integer.valueOf(bzjkpz), Integer.valueOf(jkpz_bz));
            xg(Integer.valueOf(mbxxid), Integer.valueOf(spbmbb_pzbid), Integer.valueOf(jkpz_spbmbb));
            xg(Integer.valueOf(mbxxid), Integer.valueOf(lkr_pzbid), Integer.valueOf(jkpz_lkr));
            xg(Integer.valueOf(mbxxid), Integer.valueOf(xfqxx_pzbid), Integer.valueOf(jkpz_xfqxx));
            xg(Integer.valueOf(mbxxid), Integer.valueOf(spqxx_pzbid), Integer.valueOf(jkpz_spqxx));
            xg(Integer.valueOf(mbxxid), Integer.valueOf(spyhxx_pzbid), Integer.valueOf(jkpz_spyhxx));
            xg(Integer.valueOf(mbxxid), Integer.valueOf(zfxx_pzbid), Integer.valueOf(jkpz_zfxx));
            xg(Integer.valueOf(mbxxid), Integer.valueOf(gfxx_pzbid), Integer.valueOf(jkpz_gfxx));
            result.put("msg", "保存成功!");
            result.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("msg", "保存失败!");
            result.put("success", false);
            return result;
        }
        return result;
    }

    public  boolean xg(Integer mbid,Integer pzbid,Integer cszffid){
        try {
            Jkmbzb jkmbzb = jkmbzbJpaDao.findByMbidAndPzbid(mbid,pzbid);
            jkmbzb.setXgsj(new Date());
            jkmbzb.setXgry(getYhid());
            jkmbzb.setCszffid(cszffid);
            jkmbzbJpaDao.save(jkmbzb);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 获取列表
     * @param gsdm
     * @return
     */
    @RequestMapping(value = "/getxxList")
    @ResponseBody
    public Map getxxList(String gsdm,String mbid){
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            if(StringUtils.isBlank(gsdm)){
                result.put("success", false);
                result.put("data",new ArrayList<>());
            }
            List list = new ArrayList();
            List Xflist = new ArrayList();
            List<CsbVo> list1 = getCszService.getCsz(gsdm, "jkpzmbid");
            JkpzTree jkpzGsTree = new JkpzTree();
            for (int i =0;i<list1.size();i++) {
                CsbVo csbVo = list1.get(i);
                if (csbVo.getGsdm() != null && csbVo.getXfid() == null && csbVo.getKpdid() == null) {
                    jkpzGsTree.setId(csbVo.getGsdm());
                    Map map = new HashMap();
                    map.put("gsdm", gsdm);
                    Gsxx gsxx = gsxxService.findOneByParams(map);
                    jkpzGsTree.setText(gsxx.getGsmc());
                    if (csbVo.getCsz() != null) {
                        jkpzGsTree.setTemplateId(csbVo.getCsz());
                        Jkmbb jkmbb = jkmbbJpaDao.findByid(Integer.valueOf(csbVo.getCsz()));
                        jkpzGsTree.setTemplateName(jkmbb.getMbmc());
                    } else {
                        jkpzGsTree.setTemplateId("");
                        jkpzGsTree.setTemplateName("");
                    }
                } else {
                    if (csbVo.getGsdm() != null && csbVo.getXfid() != null && csbVo.getKpdid() == null) {
                        List Skplist = new ArrayList();
                        JkpzTree XfTree = new JkpzTree();
                        XfTree.setId(csbVo.getXfid().toString());
                        XfTree.setText(csbVo.getXfmc());
                        if (csbVo.getCsz() != null) {
                            XfTree.setTemplateId(csbVo.getCsz());
                            Jkmbb jkmbb = jkmbbJpaDao.findByid(Integer.valueOf(csbVo.getCsz()));
                            XfTree.setTemplateName(jkmbb.getMbmc());
                        } else {
                            XfTree.setTemplateId("");
                            XfTree.setTemplateName("");
                        }
                        for (int j = 1; j < list1.size(); j++) {
                            CsbVo csbVo2 = list1.get(j);
                            if (csbVo2.getXfid().compareTo(csbVo.getXfid())==0 && null !=csbVo2.getKpdid()) {
                                JkpzTree XfTree3 = new JkpzTree();
                                XfTree3.setId(csbVo2.getKpdid().toString());
                                XfTree3.setText(csbVo2.getKpdmc());
                                if (csbVo2.getCsz() != null) {
                                    XfTree3.setTemplateId(csbVo2.getCsz());
                                    Jkmbb jkmbb = jkmbbJpaDao.findByid(Integer.valueOf(csbVo2.getCsz()));
                                    XfTree3.setTemplateName(jkmbb.getMbmc());
                                } else {
                                    XfTree3.setTemplateId("");
                                    XfTree3.setTemplateName("");
                                }
                                Skplist.add(XfTree3);
                            }
                        }
                        if(!Skplist.isEmpty())
                            XfTree.setChildren(Skplist);
                        Xflist.add(XfTree);

                    }
                    jkpzGsTree.setChildren(Xflist);
                }
            }
            list.add(jkpzGsTree);
            System.out.println(JSON.toJSONString(list));
            result.put("success", true);
            result.put("data",JSON.toJSONString(list));
//            List list = new ArrayList();
//            //Map map = new HashMap();
//            //map.put("csm","jkpzmbid");
//            //Csb csb = csbService.findOneByParams(map);
//            Map map1 = new HashMap();
//            map1.put("gsdm" ,gsdm);
//            Gsxx gsxx = gsxxService.findOneByParams(map1);
//            List<CsbVo> list1 = getCszService.getCsz(gsdm, "jkpzmbid");
//            JkpzTree gsTree = new JkpzTree();
//            gsTree.setId(gsxx.getGsdm());
//            gsTree.setText(gsxx.getGsmc());
//            //Cszb cszb1 = cszbJpaDao.findOneByCsidAndGsdm(csb.getId(), gsxx.getGsdm());
//            for (CsbVo csbVo : list1) {
//                if(csbVo.getGsdm()!=null && csbVo.getXfid()==null && csbVo.getKpdid()==null&&csbVo.getGsdm().equals(gsdm)){
//                    if(csbVo.getCsz()!=null){
//                        gsTree.setTemplateId(csbVo.getCsz());
//                        Jkmbb jkmbb = jkmbbJpaDao.findByid(Integer.valueOf(csbVo.getCsz()));
//                        gsTree.setTemplateName(jkmbb.getMbmc());
//                    }else {
//                        gsTree.setTemplateId("");
//                        gsTree.setTemplateName("");
//                    }
//                }
//            }
////            if(cszb1!=null){
////                Jkmbb jkmbb = jkmbbJpaDao.findByid(Integer.valueOf(cszb1.getCsz()));
////                jkpzGsTree.setTemplateId(cszb1.getCsz());
////                jkpzGsTree.setTemplateName(jkmbb.getMbmc());
////            }else {
////                jkpzGsTree.setTemplateId("");
////                jkpzGsTree.setTemplateName("");
////            }
//            Xf xf = new Xf();
//            xf.setGsdm(gsdm);
//            List<Xf> xfList = xfService.findAllByParams(xf);
//            List Xflist = new ArrayList();
//            for (Xf xf1 : xfList) {
//                JkpzTree XfTree = new JkpzTree();
//                XfTree.setId(xf1.getId().toString());
//                XfTree.setText(xf1.getXfmc());
//                Skp skp = new Skp();
//                skp.setGsdm(gsdm);
//                skp.setXfid(xf1.getId());
//                for (CsbVo csbVo : list1) {
//                    if(csbVo.getXfid()!=null && csbVo.getKpdid()==null && csbVo.getXfid().toString().equals(xf1.getId().toString())){
//                        if(csbVo.getCsz()!=null){
//                            XfTree.setTemplateId(csbVo.getCsz());
//                            Jkmbb jkmbb = jkmbbJpaDao.findByid(Integer.valueOf(csbVo.getCsz()));
//                            XfTree.setTemplateName(jkmbb.getMbmc());
//                        }else {
//                            XfTree.setTemplateId("");
//                            XfTree.setTemplateName("");
//                        }
//                    }
//                }
//                //Cszb cszb = cszbJpaDao.findOneByCsidAndGsdmAndXf(csb.getId(), gsdm, xf1.getId());
////                if(cszb!=null&&cszb.getKpdid()==null){
////                    Jkmbb jkmbb = jkmbbJpaDao.findByid(Integer.valueOf(cszb.getCsz()));
////                    jkpzXfTree.setTemplateId(jkmbb.getId().toString());
////                    jkpzXfTree.setTemplateName(jkmbb.getMbmc());
////                }else {
////                    jkpzXfTree.setTemplateId("");
////                    jkpzXfTree.setTemplateName("");
////                }
//                List<Skp> skpList = skpService.findAllByParams(skp);
//                List listSkp = new ArrayList();
//                if(!skpList.isEmpty()){
//                    for (Skp skp1 : skpList) {
//                        JkpzTree SkpTree = new JkpzTree();
//                        SkpTree.setId(skp1.getId().toString());
//                        SkpTree.setText(skp1.getKpdmc());
//                        for (CsbVo csbVo : list1) {
//                            if(csbVo.getKpdid()!=null && csbVo.getKpdid().toString().equals(skp1.getId().toString())){
//                                if (csbVo.getCsz() != null) {
//                                    SkpTree.setTemplateId(csbVo.getCsz());
//                                    Jkmbb jkmbb = jkmbbJpaDao.findByid(Integer.valueOf(csbVo.getCsz()));
//                                    SkpTree.setTemplateName(jkmbb.getMbmc());
//                                } else {
//                                    SkpTree.setTemplateId("");
//                                    SkpTree.setTemplateName("");
//                                }
//                            }
//                        }
////                        Cszb cszbs = cszbJpaDao.findOneByCsidAndGsdmAndXfAndSkp(csb.getId(), gsdm, xf1.getId(), skp1.getId());
////                        if(cszbs!=null){
////                            Jkmbb jkmbb = jkmbbJpaDao.findByid(Integer.valueOf(cszbs.getCsz()));
////                            jkpzSkpTree.setTemplateId(jkmbb.getId().toString());
////                            jkpzSkpTree.setTemplateName(jkmbb.getMbmc());
////                        }else {
////                            jkpzSkpTree.setTemplateId("");
////                            jkpzSkpTree.setTemplateName("");
////                        }
//                        listSkp.add(SkpTree);
//                    }
//                }
//                XfTree.setChildren(listSkp);
//                Xflist.add(XfTree);
//            }
//            gsTree.setChildren(Xflist);
//            list.add(gsTree);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("data",new ArrayList<>());
        }
        return result;
    }

    /**
     * 修改或新增
     * @return
     */
    @RequestMapping(value = "/savembsq")
    @ResponseBody
    public Map savembsq(String str){
        Map<String, Object> result = new HashMap<String, Object>();
        if(StringUtils.isBlank(str)){
            result.put("success", false);
            result.put("msg","授权失败");
        }
        try {
            logger.debug(JSON.toJSONString(str));
            Date date = new Date();
            JSONObject object = JSON.parseObject(str);
            String mbid = object.getString("mbid");
            String gsdm = object.getString("gsdm");
            JSONArray data = object.getJSONArray("data");
            for (int i = 0; i < data.size(); i++) {
                JSONObject jo = data.getJSONObject(i);
                String gsid = jo.getString("gsid");
                String xfid = jo.getString("xfid");
                String skpid = jo.getString("skpid");
                String templateId = jo.getString("templateId");
                Map map = new HashMap();
                map.put("csm","jkpzmbid");
                Csb csb = csbService.findOneByParams(map);
                //原模板id(templateId)不为空先删除，后插入
                if(StringUtils.isNotBlank(templateId)){
                    if(StringUtils.isNotBlank(gsid)){
                        //公司
                        Cszb cszbGs = cszbJpaDao.findOneByCsidAndGsdm(csb.getId(), gsdm);
                        if(cszbGs!=null&& cszbGs.getXfid()==null && cszbGs.getKpdid()==null){
                            cszbJpaDao.delete(cszbGs);
                            Cszb cszb = new Cszb();
                            cszb.setGsdm(gsdm);
                            cszb.setXfid(null);
                            cszb.setKpdid(null);
                            cszb.setCsid(csb.getId());
                            cszb.setCsz(mbid);//新增mbid
                            cszb.setYxbz("1");
                            cszb.setLrsj(date);
                            cszb.setLrry(getYhid());
                            cszb.setXgry(getYhid());
                            cszb.setXgsj(date);
                            cszbService.save(cszb);
                        }else {
                            Cszb cszb = new Cszb();
                            cszb.setGsdm(gsdm);
                            cszb.setXfid(null);
                            cszb.setKpdid(null);
                            cszb.setCsid(csb.getId());
                            cszb.setCsz(mbid);//新增mbid
                            cszb.setYxbz("1");
                            cszb.setLrsj(date);
                            cszb.setLrry(getYhid());
                            cszb.setXgry(getYhid());
                            cszb.setXgsj(date);
                            cszbService.save(cszb);
                        }
                    }else if(StringUtils.isNotBlank(xfid)){
                        //销方一级
                        Cszb xfcszb = cszbJpaDao.findOneByCsidAndGsdmAndXf(csb.getId(),gsdm,Integer.valueOf(xfid));
                        if(xfcszb!=null&&xfcszb.getKpdid()==null){
                            //删除原模板id(templateId)
                            cszbJpaDao.delete(xfcszb);
                            Cszb cszb = new Cszb();
                            cszb.setGsdm(gsdm);
                            cszb.setXfid(Integer.valueOf(xfid));
                            cszb.setCsid(csb.getId());
                            cszb.setCsz(mbid);//新增mbid
                            cszb.setYxbz("1");
                            cszb.setLrsj(date);
                            cszb.setLrry(1);
                            cszb.setXgry(1);
                            cszb.setXgsj(date);
                            cszbService.save(cszb);
                        }else {
                            Cszb xfcszb2 = cszbJpaDao.findOneByCsidAndGsdmAndXf(csb.getId(),gsdm,Integer.valueOf(xfid));
                            if(xfcszb2 == null){
                                //新增
                                Cszb cszb = new Cszb();
                                cszb.setGsdm(gsdm);
                                cszb.setXfid(Integer.valueOf(xfid));
                                cszb.setCsid(csb.getId());
                                cszb.setCsz(mbid);//新增mbid
                                cszb.setYxbz("1");
                                cszb.setLrsj(date);
                                cszb.setLrry(1);
                                cszb.setXgry(1);
                                cszb.setXgsj(date);
                                cszbService.save(cszb);
                            }
                        }
                    }else if(StringUtils.isNotBlank(skpid)){
                        //税控盘
                        Cszb skpcszb = cszbJpaDao.findOneByCsidAndGsdmAndXfAndSkp(csb.getId(),gsdm,Integer.valueOf(xfid),Integer.valueOf(skpid));
                        if(skpcszb!=null){
                            //删除原模板id(templateId)
                            cszbJpaDao.delete(skpcszb);
                            Cszb cszb = new Cszb();
                            cszb.setGsdm(gsdm);
                            cszb.setXfid(Integer.valueOf(xfid));
                            cszb.setKpdid(Integer.valueOf(skpid));
                            cszb.setCsid(csb.getId());
                            cszb.setCsz(mbid);//新增mbid
                            cszb.setYxbz("1");
                            cszb.setLrsj(date);
                            cszb.setLrry(1);
                            cszb.setXgry(1);
                            cszb.setXgsj(date);
                            cszbService.save(cszb);
                        }else {
                            Cszb skpcszb2 = cszbJpaDao.findOneByCsidAndGsdmAndXfAndSkp(csb.getId(),gsdm,Integer.valueOf(xfid),Integer.valueOf(skpid));
                            if(skpcszb2 == null){
                                //新增
                                Cszb cszb = new Cszb();
                                cszb.setGsdm(gsdm);
                                cszb.setXfid(Integer.valueOf(xfid));
                                cszb.setKpdid(Integer.valueOf(skpid));
                                cszb.setCsid(csb.getId());
                                cszb.setCsz(mbid);//新增mbid
                                cszb.setYxbz("1");
                                cszb.setLrsj(date);
                                cszb.setLrry(1);
                                cszb.setXgry(1);
                                cszb.setXgsj(date);
                                cszbService.save(cszb);
                            }
                        }
                    }
                }else {
                    if(StringUtils.isNotBlank(gsid)){
                        Cszb cszbGs = cszbJpaDao.findOneByCsidAndGsdm(csb.getId(), gsdm);
                        if(cszbGs ==null){
                            Cszb cszb = new Cszb();
                            cszb.setGsdm(gsdm);
                            cszb.setXfid(null);
                            cszb.setKpdid(null);
                            cszb.setCsid(csb.getId());
                            cszb.setCsz(mbid);//新增mbid
                            cszb.setYxbz("1");
                            cszb.setLrsj(date);
                            cszb.setLrry(getYhid());
                            cszb.setXgry(getYhid());
                            cszb.setXgsj(date);
                            cszbService.save(cszb);
                        }
                    }else if(StringUtils.isNotBlank(xfid)){
                        Cszb xfcszb3 = cszbJpaDao.findOneByCsidAndGsdmAndXf(csb.getId(),gsdm,Integer.valueOf(xfid));
                        if(xfcszb3 ==null){
                            //新增
                            Cszb cszb = new Cszb();
                            cszb.setGsdm(gsdm);
                            cszb.setXfid(Integer.valueOf(xfid));
                            cszb.setCsid(csb.getId());
                            cszb.setCsz(mbid);//新增mbid
                            cszb.setYxbz("1");
                            cszb.setLrsj(date);
                            cszb.setLrry(1);
                            cszb.setXgry(1);
                            cszb.setXgsj(date);
                            cszbService.save(cszb);
                        }
                    }else if(StringUtils.isNotBlank(skpid)){
                        Cszb skpcszb3 = cszbJpaDao.findOneByCsidAndGsdmAndXfAndSkp(csb.getId(),gsdm,Integer.valueOf(xfid),Integer.valueOf(skpid));
                        if(skpcszb3 == null){
                            //新增
                            Cszb cszb = new Cszb();
                            cszb.setGsdm(gsdm);
                            cszb.setXfid(Integer.valueOf(xfid));
                            cszb.setKpdid(Integer.valueOf(skpid));
                            cszb.setCsid(csb.getId());
                            cszb.setCsz(mbid);//新增mbid
                            cszb.setYxbz("1");
                            cszb.setLrsj(date);
                            cszb.setLrry(1);
                            cszb.setXgry(1);
                            cszb.setXgsj(date);
                            cszbService.save(cszb);
                        }
                    }
                }
            }
            result.put("success", true);
            result.put("msg","授权成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("msg","授权失败");
        }
        return result;
    }
    /**
     * 删除授权
     * @param str
     * @return
     */
    @RequestMapping(value = "/scCsb")
    @ResponseBody
    public Map scCsb(String str){
        Map<String, Object> result = new HashMap<String, Object>();
        if(StringUtils.isBlank(str)){
            result.put("success", false);
            return result;
        }
        try {
            JSONObject object = JSON.parseObject(str);
            String mbid = object.getString("mbid");
            String gsdm = object.getString("gsdm");
            JSONArray data = object.getJSONArray("data");
            for (int i = 0; i < data.size(); i++) {
                JSONObject jo = data.getJSONObject(i);
                String xfid = jo.getString("xfid");
                String gsid = jo.getString("gsid");
                String skpid = jo.getString("skpid");
                Map map = new HashMap();
                map.put("csm","jkpzmbid");
                Csb csb = csbService.findOneByParams(map);
                if(StringUtils.isNotBlank(gsid)){
                    Cszb gscszb = cszbJpaDao.findOneByCsidAndGsdm(csb.getId(), gsid);
                    if(gscszb!=null && gscszb.getXfid()==null&&gscszb.getKpdid()==null){
                        cszbJpaDao.delete(gscszb);
                    }
                }else if(StringUtils.isNotBlank(xfid)){
                    Cszb xfcszb = cszbJpaDao.findOneByCsidAndGsdmAndXf(csb.getId(),gsdm,Integer.valueOf(xfid));
                    if(xfcszb!=null&&xfcszb.getKpdid()==null){
                        cszbJpaDao.delete(xfcszb);
                    }
                }else if(StringUtils.isNotBlank(skpid)){
                    Cszb skpcszb = cszbJpaDao.findOneByCsidAndGsdmAndXfAndSkp(csb.getId(),gsdm,Integer.valueOf(xfid),Integer.valueOf(skpid));
                    if(skpcszb !=null){
                        cszbJpaDao.delete(skpcszb);
                    }
                }
            }
            result.put("success", true);
            result.put("msg","授权成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("msg","授权失败");
        }
        return result;
    }


    @RequestMapping(value = "/exportTxt")
    @ResponseBody
    //导出txt
    public Map exportTxt(String mbid){
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            if(StringUtils.isBlank(mbid)){
                result.put("success", false);
                result.put("msg","导出数据为空");
                return result;
            }
            String msg ="";
            Map map1 = new HashMap();
            map1.put("mbid",mbid);
//            map1.put("mbid","3");
            List<JkpzVo> jkmbzbList = jkmbzbService.findByMbId(map1);
            if(jkmbzbList.isEmpty()){
                result.put("success", false);
                result.put("msg","导出数据为空");
                return result;
            }
            AdapterPost adapterPost = new AdapterPost();
            adapterPost.setAppId("RJxxxxxxxx");
            adapterPost.setTaxNo("310101123456789");
            adapterPost.setClientNo("KP001");
            adapterPost.setSign("afafddaf2eweddew");
            AdapterData adapterData = new AdapterData();
            adapterPost.setData(adapterData);
            AdapterDataOrder order =new AdapterDataOrder();
            order.setTotalAmount(5410.00d);
            order.setTotalDiscount(910.00d);
            order.setExtractedCode("1A2B3C4D5F");
            adapterData.setOrder(order);
            for (JkpzVo jkpzVo : jkmbzbList) {
                Map paraMap = new HashMap();
                paraMap.put("jkpzVo",jkpzVo);
                paraMap.put("adapterPost", adapterPost);
                String execute = execute(jkpzVo.getCszff(), paraMap);
                if(StringUtils.isNotBlank(execute)){
                    msg += execute;
                }
            }
            if(StringUtils.isNotBlank(msg)){
                result.put("success", false);
                result.put("msg",msg);
                return result;
            }
            System.out.println(JSON.toJSONString(adapterPost));
            //将集合转换成字符串
            String jsonString = JSON.toJSONString(adapterPost);
            ExportTextUtil.writeToTxt(response,jsonString);
            result.put("success", true);
            result.put("msg","导出成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("msg","系统异常");
            return result;
        }
        return result;
    }

    public String execute(String methodName,Map map){
        String result = "";
        try {
            Method target = this.getClass().getMethod(methodName,Map.class);
            result = (String)target.invoke(this,map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public String param(Map map){
        String result ="";
        try {
            JkpzVo jkpzVo = (JkpzVo) map.get("jkpzVo");
            AdapterPost adapterPost = (AdapterPost) map.get("adapterPost");
            if(jkpzVo==null || adapterPost ==null){
                result="获取数据错误";
                return result;
            }
            String pzcsm = jkpzVo.getPzcsm();
            switch (pzcsm){
                case "serialNumber":
                    adapterPost.getData().setSerialNumber("2016062412444500001");
                    break;
                case "orderNo":
                    adapterPost.getData().getOrder().setOrderNo("ME24156071");
                    break;
                case "orderDate":
                    adapterPost.getData().getOrder().setOrderDate(new Date());
                    break;
                case "clientNo":
                    adapterPost.setClientNo("KP001");
                    break;
                case "invType":
                    adapterPost.getData().setInvType("12");
                    break;
                case "invoiceList":
                    adapterPost.getData().getOrder().setInvoiceList("0");
                    break;
                case "invoiceSplit":
                    adapterPost.getData().getOrder().setInvoiceSplit("1");
                    break;
                case "invoicePrint":
                    adapterPost.getData().getOrder().setInvoiceSfdy("1");
                    break;
                case "chargeTaxWay":
                    adapterPost.getData().getOrder().setChargeTaxWay("0");
                    break;
                case "taxMark":
                    adapterPost.getData().getOrder().setTaxMark("0");
                    break;
                case "remark":
                    adapterPost.getData().getOrder().setRemark("该栏目打印在发票上的备注");
                    break;
                case "version":
                    adapterPost.getData().setVersion("18.0");
                    break;
                case "person":
                    adapterPost.getData().setPayee("收款人");
                    adapterPost.getData().setDrawer("开票人");
                    adapterPost.getData().setReviewer("复核人");
                    break;
                case "seller":
                    AdapterDataSeller seller =new AdapterDataSeller();
                    seller.setIdentifier("310101123456789");
                    seller.setAddress("某某路10号1203室");
                    seller.setName("发票开具方名称");
                    seller.setTelephoneNo("021-55555555");
                    seller.setBank("中国建设银行打浦桥支行");
                    seller.setBankAcc("123456789-0");
                    adapterPost.getData().setSeller(seller);
                    break;
                case "items":
                    List list = new ArrayList();
                    AdapterDataOrderDetails details =new AdapterDataOrderDetails();
                    details.setVenderOwnCode("商品自行编码");
                    details.setProductCode("1000000000000000000");
                    details.setProductName("商品1");
                    details.setRowType("0");
                    details.setSpec("规格型号1");
                    details.setUtil("单位1");
                    details.setQuantity(1d);
                    details.setUnitPrice(1000.00d);
                    details.setAmount(1000.00d);
                    details.setDeductAmount(0d);
                    details.setTaxRate(0.17d);
                    details.setTaxAmount(170.00d);
                    details.setMxTotalAmount(1170.00d);
                    details.setPolicyName("优惠政策名称");
                    details.setPolicyMark("优惠政策标识");
                    details.setTaxRateMark("零税率标志");
                    list.add(details);
                    adapterPost.getData().getOrder().setOrderDetails(list);
                    break;
                case "payments":
                    List list1 = new ArrayList();
                    AdapterDataOrderPayments payments1 =new AdapterDataOrderPayments();
                    AdapterDataOrderPayments payments2 =new AdapterDataOrderPayments();
                    payments1.setPayCode("01");
                    payments1.setPayPrice(500d);
                    payments2.setPayCode("03");
                    payments2.setPayPrice(410.00d);
                    list1.add(payments1);
                    adapterPost.getData().getOrder().setPayments(list1);
                    break;
                case "buyer":
                    AdapterDataOrderBuyer buyer = new AdapterDataOrderBuyer();
                    buyer.setCustomerType("0");
                    buyer.setIdentifier("310105987654321");
                    buyer.setName("购买方名称");
                    buyer.setAddress("某某路20号203室");
                    buyer.setTelephoneNo("13912345678");
                    buyer.setBank("中国建设银行打浦桥支行");
                    buyer.setBankAcc("123456789-0");
                    buyer.setEmail("abc@163.com");
                    buyer.setIsSend("1");
                    buyer.setRecipient("张三");
                    buyer.setReciAddress("收件人地址");
                    buyer.setZip("200000");
                    adapterPost.getData().getOrder().setBuyer(buyer);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result="获取数据错误";
            return result;
        }
        return null;
    }
}
