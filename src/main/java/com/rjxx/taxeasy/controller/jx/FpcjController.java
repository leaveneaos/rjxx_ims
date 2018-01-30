package com.rjxx.taxeasy.controller.jx;

import com.alibaba.fastjson.JSON;
import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.dao.leshui.JxfpxxJpaDao;
import com.rjxx.taxeasy.dao.leshui.JxfpxxMapper;
import com.rjxx.taxeasy.domains.leshui.Jxfpxx;
import com.rjxx.taxeasy.filter.SystemControllerLog;
import com.rjxx.taxeasy.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018-01-29.
 * 进项管理-发票采集
 */
@Controller
@RequestMapping("/fpcj")
public class FpcjController extends BaseController {

    @Autowired
    private JxfpxxJpaDao jxfpxxJpaDao;
    @Autowired
    private JxfpxxMapper jxfpxxMapper;


    @RequestMapping
    public String index() throws Exception {
        return "jx/fpcj/index";
    }

    /**
     * 发票采集-查询列表
     * @param length
     * @param start
     * @param draw
     * @param fpdm
     * @param fphm
     * @param kprqq
     * @param xfsh
     * @param fpzldm
     * @param loaddata
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/getJxfpxxList")
    public Map<String, Object> getFpcyList(int length, int start, int draw, String fpdm, String fphm, String kprqq,
                                           String xfsh, String fpzldm,boolean loaddata) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        Pagination pagination = new Pagination();
//        Map map = new HashMap();
        if(loaddata){
            pagination.setPageNo(start / length + 1);
            pagination.setPageSize(length);
           // map.put("start",start);
            // map.put("length",length);
            String gsdm = getGsdm();
            if ("".equals(fpzldm) || fpzldm == null) {
                pagination.addParam("fpzldm",null);
            } else {
                pagination.addParam("fpzldm",fpzldm);
            }
            pagination.addParam("fpdm",fpdm);
            pagination.addParam("fphm",fphm);
            pagination.addParam("xfs",getXfList());
            pagination.addParam("kprqq",kprqq);
            pagination.addParam("gsdm",gsdm);
            if (null != xfsh && !"".equals(xfsh) && !"-1".equals(xfsh)) {
                pagination.addParam("xfsh", xfsh);
            }
            //List dataList = new ArrayList();
            List<Jxfpxx> jxfpxxList = jxfpxxMapper.findByPage(pagination);
            logger.info("查询结果"+ JSON.toJSONString(jxfpxxList));
            int total = 0;
            if(0 == start){
                //total = fpcyMapper.findtotal(map);
               total = pagination.getTotalRecord();
                request.getSession().setAttribute("total",total);
            }else{
              //  total =  (Integer)request.getSession().getAttribute("total");
                request.getSession().getAttribute("total");
            }
            result.put("recordsTotal", total);
            result.put("recordsFiltered", total);
            result.put("draw", draw);
            result.put("data", jxfpxxList);
        }else{
            int total = 0;
            result.put("recordsTotal", total);
            result.put("recordsFiltered", total);
            result.put("draw", draw);
            result.put("data", new ArrayList<>());
        }
        return result;
    }

    /**
     * 发票采集--删除
     * @param fplshs
     * @return
     */
    @ResponseBody
    @RequestMapping("/sc")
    public Map<String, Object> sc(String fplshs) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            String[] ids = fplshs.split(",");
            for (String id : ids) {
                Jxfpxx jxfpxx = jxfpxxJpaDao.findOne(Integer.valueOf(id));
                jxfpxx.setYxbz("0");
                jxfpxxJpaDao.save(jxfpxx);
            }
            result.put("msg", "删除成功");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            result.put("msg","删除失败，请联系开发人员");
        }
        return result;
    }
    @ResponseBody
    @RequestMapping("/xgjxfpxx")
    @SystemControllerLog(description = "修改进行发票详情", key = "fplsh")
    public Map<String, Object> xgjxfpxx(String id,String sfsp,String fpbq) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Jxfpxx jxfpxx = jxfpxxJpaDao.findOne(Integer.valueOf(id));
            jxfpxx.setSfsp(sfsp);
            jxfpxxJpaDao.save(jxfpxx);
            result.put("msg", true);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            result.put("msg",false);
        }
        return result;
    }
}
