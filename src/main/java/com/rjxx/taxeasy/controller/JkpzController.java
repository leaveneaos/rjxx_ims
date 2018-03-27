package com.rjxx.taxeasy.controller;

import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.dao.JkmbbJpaDao;
import com.rjxx.taxeasy.dao.JkmbzbJpaDao;
import com.rjxx.taxeasy.domains.Jkmbb;
import com.rjxx.taxeasy.domains.Jkmbzb;
import com.rjxx.taxeasy.domains.Jkpzdmb;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.service.JkmbbService;
import com.rjxx.taxeasy.service.JkmbzbService;
import com.rjxx.taxeasy.service.JkpzdmbService;
import com.rjxx.taxeasy.service.XfService;
import com.rjxx.taxeasy.vo.JkpzVo;
import com.rjxx.taxeasy.web.BaseController;
import com.rjxx.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/jkpz")
public class JkpzController extends BaseController {

    @Autowired
    private XfService xfService;
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
        request.setAttribute("jkpzdmb", getjkpzdmb(null));
        request.setAttribute("gsdm", getGsdm());
        Xf xf = new Xf();
        xf.setGsdm(getGsdm());
        List<Xf> xfList = xfService.findAllByParams(xf);
        request.setAttribute("xfList", xfList);
        return "jkpz/index";
    }

    /**
     * 查询列表
     * @param length
     * @param start
     * @param draw
     * @param mbmc
     * @param txt
     * @return
     */
    @RequestMapping(value = "/getjkmbList")
    @ResponseBody
    public Map getJkmb(int length, int start, int draw, String mbmc,  String txt) {
        Pagination pagination = new Pagination();
        List<Jkmbb> list = null;
        try {
            pagination.setPageNo(start / length + 1);
            pagination.setPageSize(length);
            pagination.addParam("gsdm", getGsdm());
            pagination.addParam("orderBy", "lrsj");
            pagination.addParam("xfs", getXfList());
            list = jkmbbService.findByPage(pagination);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
     * @return
     */
    @RequestMapping(value = "/getjkmbzb")
    @ResponseBody
    public List getjkmbzb(Integer mbid){
        List result=new ArrayList();
        try {
            List<JkpzVo> list = jkmbzbService.findByMbId(mbid);
            result=list;
        } catch (Exception e) {
            e.printStackTrace();
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
            String[] idls = ids.split(",");
            for (int i = 0; i < idls.length; i++) {
                String id = idls[i].split(":")[0];
                try {
                    Jkmbb jkmbb = jkmbbService.findOne(Integer.valueOf(id));
                    List<Jkmbzb> list = jkmbzbJpaDao.findListByMbid(jkmbb.getId());
                    for (Jkmbzb jkmbzb : list) {
                        jkmbbJpaDao.delete(jkmbzb.getId());
                    }
                    jkmbbJpaDao.delete(Integer.valueOf(id));
                    result.put("success", true);
                    result.put("msg", "删除成功");
                } catch (Exception e) {
                    e.printStackTrace();
                    result.put("failure", true);
                    result.put("msg", "删除出现错误: " + e);
                }
            }
        }
        return result;
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public Map xgcsb(String mbmc, String mbms,String ids) {
        Map<String, Object> result = new HashMap<String, Object>();
        
        result.put("msg", "保存成功!");
        result.put("success", true);
        return result;
    }
}
