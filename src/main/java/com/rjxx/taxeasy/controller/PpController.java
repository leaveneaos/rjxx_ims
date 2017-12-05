package com.rjxx.taxeasy.controller;

import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.domains.Group;
import com.rjxx.taxeasy.domains.Gsxx;
import com.rjxx.taxeasy.domains.Pp;
import com.rjxx.taxeasy.domains.Skp;
import com.rjxx.taxeasy.filter.SystemControllerLog;
import com.rjxx.taxeasy.service.PpService;
import com.rjxx.taxeasy.service.SkpService;
import com.rjxx.taxeasy.vo.SkpVo;
import com.rjxx.taxeasy.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;


/**
 * Created by Administrator on 2017-11-16.
 */
@Controller
@RequestMapping("/pp")
public class PpController  extends BaseController {

    @Autowired
    private PpService ppService;

    @Autowired
    private SkpService skpService;


    @RequestMapping
    @SystemControllerLog(description = "品牌管理", key = "")
    public String index() throws Exception {
        session.setAttribute("xfs", getXfList());
        session.setAttribute("xf", getXfList().get(0));
        return "pp/index";
    }

    /**
     * 分页查询品牌信息
     *
     * @param length
     * @param start
     * @param draw
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getpplist")
    @ResponseBody
    public Map getsksblist(int length, int start, int draw, String ppmc,  String txt) throws Exception {
        Pagination pagination = new Pagination();
        pagination.setPageNo(start / length + 1);
        pagination.setPageSize(length);

        pagination.addParam("gsdm", getGsdm());
        pagination.addParam("ppmc", ppmc);
        pagination.addParam("orderBy", "lrsj");
        pagination.addParam("xfs", getXfList());
        List<Pp> list = ppService.findByPage(pagination);
        int total = pagination.getTotalRecord();
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("recordsTotal", total);
        result.put("recordsFiltered", total);
        result.put("draw", draw);
        result.put("data", list);
        return result;
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    @Transactional
    @SystemControllerLog(description = "新增品牌信息",key = "kpddm")
    public Map save( String ppmc, String ppdm, String ppurl, String aliMShortName,String aliSubMShortName, String wechatLogoUrl) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> prms = new HashMap<>();
            Pp pp = new Pp();
            prms.put("gsdm", getGsdm());
            prms.put("ppdm",ppdm);
            Pp pps = ppService.findOneByParams(prms);
            if(pps != null){
                result.put("failure", true);
                result.put("msg", "该品牌代码已存在，请重试！");
                return result;
            }
            pp.setPpdm(ppdm);
            pp.setPpmc(ppmc);
            pp.setPpurl(ppurl);
            pp.setYxbz("1");
            pp.setLrsj(new Date());
            pp.setLrry(getYhid());
            pp.setXgry(getYhid());
            pp.setXgsj(new Date());
            pp.setGsdm(getGsdm());
            pp.setAliSubMShortName(aliSubMShortName);
            pp.setAliMShortName(aliMShortName);
            if(wechatLogoUrl!=null&&!wechatLogoUrl.equals("")){
                pp.setWechatLogoUrl(wechatLogoUrl);
            }
            ppService.save(pp);
            result.put("success", true);
            result.put("msg", "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("failure", true);
            result.put("msg", "添加出现错误: 服务异常" + e);
        }
        return result;
    }
    /**
     * 修改品牌信息
     * @param id
     * @param ppmc
     * @param ppdm
     * @param ppurl
     * @param aliMShortName
     * @param wechatLogoUrl
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    @SystemControllerLog(description = "修改品牌信息",key = "id")
    public Map  update(int id,  String ppmc, String ppdm, String ppurl, String aliMShortName,String aliSubMShortName, String wechatLogoUrl) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Pp pp = ppService.findOne(id);
            pp.setPpdm(ppdm);
            pp.setPpmc(ppmc);
            pp.setPpurl(ppurl);
            pp.setAliMShortName(aliSubMShortName);
            pp.setAliSubMShortName(aliMShortName);
            if(null!=wechatLogoUrl&&!wechatLogoUrl.equals("")){
                pp.setWechatLogoUrl(wechatLogoUrl);
            }
            pp.setGsdm(getGsdm());
            pp.setLrry(pp.getLrry());
            pp.setLrsj(pp.getLrsj());
            pp.setXgry(getYhid());
            pp.setXgsj(new Date());
            pp.setYxbz("1");
            ppService.save(pp);
            result.put("success", true);
            result.put("msg", "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("failure", true);
            result.put("msg", "修改出现错误: 服务异常" + e);
        }
        return result;
    }


    @RequestMapping(value = "/delele")
    @ResponseBody
    @Transactional
    @SystemControllerLog(description = "删除品牌",key = "ids")
    public Map delete(String ids) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (!"".equals(ids)) {
            String[] idls = ids.split(",");
            for (int i = 0; i < idls.length; i++) {
                String id = idls[i].split(":")[0];
                try {
                    Pp pp = ppService.findOne(Integer.valueOf(id));
                    pp.setYxbz("0");
                    ppService.save(pp);
                    Skp s = new Skp();
                    s.setGsdm(getGsdm());
                    s.setPid(Integer.valueOf(id));
                    List<Skp> skpList = skpService.findAllByParams(s);
                    if (!skpList.isEmpty()) {
                        for (Skp skp : skpList) {
                            skp.setPid(null);
                        }
                        skpService.save(skpList);
                    }
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

}
