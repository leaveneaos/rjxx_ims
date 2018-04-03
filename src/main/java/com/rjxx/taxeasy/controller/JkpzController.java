package com.rjxx.taxeasy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.dao.CszbJpaDao;
import com.rjxx.taxeasy.dao.JkmbbJpaDao;
import com.rjxx.taxeasy.dao.JkmbzbJpaDao;
import com.rjxx.taxeasy.domains.*;
import com.rjxx.taxeasy.service.*;
import com.rjxx.taxeasy.vo.*;
import com.rjxx.taxeasy.web.BaseController;
import com.rjxx.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
                for (String id : idl) {
                        Jkmbb jkmbb = jkmbbService.findOne(Integer.valueOf(id));
                        List<Jkmbzb> list = jkmbzbJpaDao.findListByMbid(jkmbb.getId());
                        for (Jkmbzb jkmbzb : list) {
                            jkmbzbJpaDao.delete(jkmbzb.getId());
                        }
                        jkmbbJpaDao.delete(Integer.valueOf(id));
                        Map map = new HashMap();
                        map.put("csm","jkpzmbid");
                        Csb csb = csbService.findOneByParams(map);
                        Cszb cszb = cszbService.getSpbmbbh(jkmbb.getGsdm(), null, null, "jkpzmbid");
                        if(csb!=null&&cszb!=null){
                            List<Cszb> cszbList = cszbJpaDao.findByGsdmAndCszAndCsid(csb.getId(), jkmbb.getGsdm(), cszb.getCsz());
                            for (Cszb cszb1 : cszbList) {
                                cszbJpaDao.delete(cszb1.getId());
                            }
                        }
                    result.put("success", true);
                    result.put("msg", "删除成功");
                }
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
            Map map = new HashMap();
            map.put("csm","jkpzmbid");
            Csb csb = csbService.findOneByParams(map);
            List list = new ArrayList();
            Xf xf = new Xf();
            xf.setGsdm(gsdm);
            List<Xf> xfList = xfService.findAllByParams(xf);
            for (Xf xf1 : xfList) {
                JkpzTreeXFVo jkpzTreeXFVo = new JkpzTreeXFVo();
                jkpzTreeXFVo.setId(xf1.getId().toString());
                jkpzTreeXFVo.setText(xf1.getXfmc());
                jkpzTreeXFVo.setParent("#");
                Skp skp = new Skp();
                skp.setGsdm(gsdm);
                skp.setXfid(xf1.getId());
                List<Skp> skpList = skpService.findAllByParams(skp);
                Cszb cszb = cszbJpaDao.findOneByCsidAndGsdmAndXfAndCsz(csb.getId(), gsdm, xf1.getId(), mbid);
                if(cszb!=null&&cszb.getKpdid()==null){
                    Jkmbb jkmbb = jkmbbJpaDao.findByid(Integer.valueOf(cszb.getCsz()));
                    Map ma = new HashMap();
                    ma.put("selected",true);
                    jkpzTreeXFVo.setText(xf1.getXfmc()+"("+jkmbb.getMbmc()+")");
                    jkpzTreeXFVo.setState(ma);
                }
                for (Skp skp1 : skpList) {
                    JkpzTreeXFVo jkpzTreeXFVo1 = new JkpzTreeXFVo();
                    jkpzTreeXFVo1.setId(skp1.getId().toString());
                    jkpzTreeXFVo1.setText(skp1.getKpdmc());
                    jkpzTreeXFVo1.setParent(xf1.getId().toString());
                    Cszb cszbs = cszbJpaDao.findOneByCsidAndGsdmAndXfAndSkpAndCsz(csb.getId(), gsdm, xf1.getId(), skp1.getId(), mbid);
                    if(cszbs!=null){
                        Jkmbb jkmbb = jkmbbJpaDao.findByid(Integer.valueOf(cszb.getCsz()));
                        Map ma1 = new HashMap();
                        ma1.put("selected",true);
                        jkpzTreeXFVo1.setState(ma1);
                        jkpzTreeXFVo1.setText(xf1.getXfmc()+"("+jkmbb.getMbmc()+")");
                    }
                    list.add(jkpzTreeXFVo1);
                }
                list.add(jkpzTreeXFVo);
            }
            System.out.println(JSON.toJSONString(list));
            result.put("success", true);
            result.put("data",JSON.toJSONString(list));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("data",new ArrayList<>());
        }
        return result;
    }

    /**
     * @param mbid
     * @param gsdm
     * @param xfid
     * @param kpdid
     * @return
     */
    @RequestMapping(value = "/savembsq")
    @ResponseBody
    public Map savembsq(String mbid ,String gsdm,String xfid , String kpdid){
        Map<String, Object> result = new HashMap<String, Object>();
        if(StringUtils.isBlank(mbid)){
            result.put("success", false);
            result.put("msg","授权失败");
        }
        try {
            Map map = new HashMap();
            map.put("csm","jkpzmbid");
            Csb csb1 = csbService.findOneByParams(map);
            if(StringUtils.isBlank(kpdid)){
                //销方
                Cszb xfcszb = cszbJpaDao.findOneByCsidAndGsdmAndXfAndCsz(csb1.getId(), gsdm,Integer.valueOf(xfid),mbid);
                if(xfcszb!=null&&xfcszb.getKpdid()==null){
                    // 销方授权      删除
                    cszbJpaDao.delete(xfcszb);
                }else {
                    //新增 销方授权
                    Cszb cszb = new Cszb();
                    cszb.setGsdm(gsdm);
                    cszb.setXfid(Integer.valueOf(xfid));
                    cszb.setCsid(csb1.getId());
                    cszb.setCsz(mbid);
                    cszb.setYxbz("1");
                    cszb.setLrsj(new Date());
                    cszb.setLrry(getYhid());
                    cszb.setXgry(getYhid());
                    cszb.setXgsj(new Date());
                    cszbService.save(cszb);
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
     * @param id
     * @return
     */
    @RequestMapping(value = "/scCsb")
    @ResponseBody
    public Map<String, Object> scCsb(String id){
        Map<String, Object> result = new HashMap<String, Object>();
        if(StringUtils.isBlank(id)){
            result.put("success", false);
            return result;
        }
        Cszb cszb = cszbJpaDao.finfOneById(Integer.valueOf(id));
        cszb.setYxbz("0");
        cszbService.save(cszb);
        result.put("success", true);
        return result;
    }

    /**
     * 修改授权
     * @param id
     * @param mbid
     * @param gsdm
     * @param xfid
     * @param kpdid
     * @return
     */
    @RequestMapping(value = "/updatembsq")
    @ResponseBody
    public Map updatembsq(String id,String mbid ,String gsdm,String xfid , String kpdid){
        Map<String, Object> result = new HashMap<String, Object>();
        if(StringUtils.isBlank(id)){
            result.put("success", false);
            result.put("msg","授权失败");
        }
        try {
            Cszb cszb = cszbJpaDao.finfOneById(Integer.valueOf(id));
            cszb.setGsdm(gsdm);
            cszb.setXfid(Integer.valueOf(xfid));
            cszb.setKpdid(Integer.valueOf(kpdid));
            cszb.setCsid(46);
            cszb.setCsz(mbid);
            cszb.setXgry(getYhid());
            cszb.setXgsj(new Date());
            cszbService.save(cszb);
            result.put("success", true);
            result.put("msg","授权成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("msg","授权失败");
        }
        return result;
    }

}
