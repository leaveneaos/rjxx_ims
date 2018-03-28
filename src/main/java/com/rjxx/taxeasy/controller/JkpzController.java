package com.rjxx.taxeasy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.dao.JkmbbJpaDao;
import com.rjxx.taxeasy.dao.JkmbzbJpaDao;
import com.rjxx.taxeasy.domains.*;
import com.rjxx.taxeasy.service.*;
import com.rjxx.taxeasy.vo.JkmbbVo;
import com.rjxx.taxeasy.vo.JkpzVo;
import com.rjxx.taxeasy.web.BaseController;
import com.rjxx.utils.StringUtils;
import org.springframework.beans.NullValueInNestedPathException;
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

    @RequestMapping
    public String index() {
        request.setAttribute("jkpzdmb", getjkpzdmb());
        request.setAttribute("gsdm", getGsdm());
//        Xf xf = new Xf();
//        Skp skp = new Skp();
//        Map map = new HashMap();
//        List<Gsxx> gsxxList = gsxxService.findAllByParams(map);
//        List<Skp> skpList = skpService.findAllByParams(skp);
//        List<Xf> xfList = xfService.findAllByParams(xf);
//        request.setAttribute("gsxx",gsxxList);
//        request.setAttribute("skp",skpList);
//        request.setAttribute("xf",xfList);
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
//        result.put("recordsTotal", total);
//        result.put("recordsFiltered", total);
//        result.put("draw", draw);
//        result.put("data", list);
        return result;
    }

    /**
     * 查看详情
     * @param mbid
     * @return 成功返回list  失败list为空
     */
    @RequestMapping(value = "/getjkmbzb")
    @ResponseBody
    public Map getjkmbzb(Integer mbid){
        Map result=new HashMap();
        try {
            if(mbid==null){
                result.put("success", false);
                result.put("data",new ArrayList<>());
            }
            List<JkpzVo> list = jkmbzbService.findByMbId(mbid);
            if(list.isEmpty()){
                result.put("success", false);
                result.put("data",new ArrayList<>());
            }
            result.put("success", true);
            result.put("data",list);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("data",new ArrayList<>());
        }
        return  result;
    }

    public List<Jkpzdmb> getjkpzdmb() {
        Map params = new HashMap<>();
        List<Jkpzdmb> list = jkpzdmbService.findAllByParams(params);
        return list;
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delele")
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
                            jkmbbJpaDao.delete(jkmbzb.getId());
                        }
                        jkmbbJpaDao.delete(Integer.valueOf(id));
                        result.put("success", true);
                        result.put("msg", "删除成功");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                result.put("success", false);
                result.put("msg", "删除失败");
            }
        }
        return result;
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public Map xgcsb(String mbmc, String mbms,String str3) {
        Map<String, Object> result = new HashMap<String, Object>();
        if(StringUtils.isBlank(str3)){
            result.put("msg", "保存失败，请检查数据!");
            result.put("success", false);
        }
        try {
            Jkmbb jkmbb = new Jkmbb();
            Date date = new Date();
            jkmbb.setGsdm(getGsdm());
            jkmbb.setMbmc(mbmc);
            jkmbb.setMbms(mbms);
            jkmbb.setYxbz("1");
            jkmbb.setLrsj(date);
            jkmbb.setLrry(getYhid());
            Jkmbb jkmbb1 = jkmbbJpaDao.save(jkmbb);
            JSONArray objects = JSON.parseArray(str3);
            for (Object o : objects) {
                Jkmbzb jkmbzb = new Jkmbzb();
                jkmbzb.setMbid(jkmbb1.getId());
                JSONObject j = (JSONObject) o;
                String pzbid = j.getString("pzbid");
                String cszffid = j.getString("cszffid");
                jkmbzb.setPzbid(Integer.valueOf(pzbid));
                jkmbzb.setCszffid(Integer.valueOf(cszffid));
                jkmbzb.setLrry(getYhid());
                jkmbzb.setLrsj(date);
                jkmbzbJpaDao.save(jkmbzb);
            }
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

    /**
     * 获取销方列表
     * @param gsdm
     * @return
     */
    @RequestMapping(value = "/getxfxxList")
    @ResponseBody
    public Map getXfxxList(String gsdm){
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            if(StringUtils.isBlank(gsdm)){
                result.put("success", false);
                result.put("data",new ArrayList<>());
            }
            Xf xf = new Xf();
            xf.setGsdm(gsdm);
            List<Xf> xfList = xfService.findAllByParams(xf);
            result.put("success", true);
            result.put("data",xfList);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("data",new ArrayList<>());
        }
        return result;
    }

    /**
     * 获取税控盘列表
     * @param xfid
     * @param gsdm
     * @return
     */
    @RequestMapping(value = "/getskpxxList")
    @ResponseBody
    public Map getSkpxxList(String xfid,String gsdm){
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            if(StringUtils.isBlank(xfid)){
                result.put("success", false);
                result.put("data",new ArrayList<>());
            }
            Skp skp = new Skp();
            skp.setGsdm(gsdm);
            skp.setXfid(Integer.valueOf(xfid));
            List<Skp> skpList = skpService.findAllByParams(skp);
            result.put("success", true);
            result.put("data",skpList);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("data",new ArrayList<>());
        }
        return result;
    }

    @RequestMapping(value = "/getsfsq")
    @ResponseBody
    public Boolean getcsb(String gsdm,String xfid ,String kpdid){
        Cszb cszb = cszbService.getSpbmbbh(gsdm, Integer.valueOf(xfid), Integer.valueOf(kpdid), "jkpzmbid");
        if(cszb !=null){
            return true;
        }
        return false;
    }
}
