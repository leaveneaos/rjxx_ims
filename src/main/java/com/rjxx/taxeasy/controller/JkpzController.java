package com.rjxx.taxeasy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.dao.JkmbbJpaDao;
import com.rjxx.taxeasy.dao.JkmbzbJpaDao;
import com.rjxx.taxeasy.domains.*;
import com.rjxx.taxeasy.service.*;
import com.rjxx.taxeasy.vo.JkpzVo;
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
    private GsxxService gsxxService;
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
        Xf xf = new Xf();
        Skp skp = new Skp();
        Map map = new HashMap();
        List<Gsxx> gsxxList = gsxxService.findAllByParams(map);
        List<Skp> skpList = skpService.findAllByParams(skp);
        List<Xf> xfList = xfService.findAllByParams(xf);
        request.setAttribute("gsxx",gsxxList);
        return "jkpz/index";
    }

    /**
     * 查询列表
     * @param length
     * @param start
     * @param draw
     * @param mbmc
     * @return
     */
    @RequestMapping(value = "/getjkmbList")
    @ResponseBody
    public Map getJkmb(int length, int start, int draw, String mbmc,  String mbms,String gsdm) {
        Pagination pagination = new Pagination();
        List<Jkmbb> list = null;
        pagination.setPageNo(start / length + 1);
        pagination.setPageSize(length);
        pagination.addParam("gsdm", gsdm);
        pagination.addParam("orderBy", "lrsj");
        pagination.addParam("mbmc",mbmc);
        pagination.addParam("mbms",mbms);
        list = jkmbbService.findByPage(pagination);
        int total = pagination.getTotalRecord();
        Map<String, Object> result = new HashMap();
        result.put("recordsTotal", total);
        result.put("recordsFiltered", total);
        result.put("draw", draw);
        result.put("data", list);
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
}
