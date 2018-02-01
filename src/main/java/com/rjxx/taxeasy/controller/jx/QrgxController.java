package com.rjxx.taxeasy.controller.jx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.dao.leshui.JxfpmxJpaDao;
import com.rjxx.taxeasy.dao.leshui.JxfpxxJpaDao;
import com.rjxx.taxeasy.dao.leshui.JxfpxxMapper;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.domains.leshui.InvoiceAuth;
import com.rjxx.taxeasy.domains.leshui.Jxfpxx;
import com.rjxx.taxeasy.filter.SystemControllerLog;
import com.rjxx.taxeasy.service.leshui.LeshuiService;
import com.rjxx.taxeasy.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2018-01-29.
 * 进项管理-确认勾选
 */
@Controller
@RequestMapping("/qrgx")
public class QrgxController extends BaseController{

    @Autowired
    private JxfpxxJpaDao jxfpxxJpaDao;
    @Autowired
    private JxfpxxMapper jxfpxxMapper;
    @Autowired
    private JxfpmxJpaDao jxfpmxJpaDao;
    @Autowired
    private LeshuiService leshuiService;

    @RequestMapping
    public String index() throws Exception {
        request.setAttribute("xfList", getXfList());
        return "jx/qrgx/index";
    }

    /**
     * 发票采集-查询列表
     * @param length
     * @param start
     * @param draw
     * @param fpdm
     * @param fphm
     * @param kprqq
     * @param gfsh
     * @param fpzldm
     * @param loaddata
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/getJxfpxxList")
    public Map<String, Object> getFpcyList(int length, int start, int draw, String fpdm, String fphm, String kprqq,
                                            String gfsh, String fpzldm, boolean loaddata) throws Exception {
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
            if (null != gfsh && !"".equals(gfsh) && !"-1".equals(gfsh)) {
                pagination.addParam("gfsh", gfsh);
            }
            //List dataList = new ArrayList();
            List<Jxfpxx> jxfpxxList = jxfpxxMapper.findQrgxByPage(pagination);
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
     *  取消勾选
     * @param fplshs
     * @return
     */
    @ResponseBody
    @RequestMapping("/qx")
    @SystemControllerLog(description = "取消勾选", key = "fplsh")
    public Map<String, Object> qx(String fplshs) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            String[] ids = fplshs.split(",");
            for (String id : ids) {
                Jxfpxx jxfpxx = jxfpxxJpaDao.findOne(Integer.valueOf(id));
                jxfpxx.setGxbz("0");
                Date d = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                jxfpxx.setXgsj(sdf.format(d));
                jxfpxxJpaDao.save(jxfpxx);
            }
            result.put("status", true);
            result.put("msg","取消勾选成功");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            result.put("status",false);
            result.put("msg","取消勾选失败，请联系开发人员");
        }
        return result;
    }

    /**
     * 勾选认证
     * @param fplshs
     * @return
     */
    @RequestMapping(value = "/zpxz" , method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object>  gxrz(String fplshs){
        Map<String, Object>  result = new HashMap();
        try {
            String[] ids = fplshs.split(",");
            String gsdm = getGsdm();
            List<Xf> xfList = getXfList();
            for (String id : ids) {
                Jxfpxx jxfpxx = jxfpxxJpaDao.findOne(Integer.valueOf(id));
                List<InvoiceAuth> list = new ArrayList<>();
                InvoiceAuth invoiceAuth = new InvoiceAuth();
                invoiceAuth.setFpdm(jxfpxx.getFpdm());
                invoiceAuth.setFphm(jxfpxx.getFphm());
                list.add(invoiceAuth);
                String fprz = leshuiService.fprz(jxfpxx.getGfsh(), list);
                JSONObject resultJson = JSON.parseObject(fprz);
                JSONObject head = resultJson.getJSONObject("head");
                JSONObject body_r = resultJson.getJSONObject("body");
                String rtnMsg = head.getString("rtnMsg");
                result.put("msg",rtnMsg);
                result.put("status",true);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status",false);
            result.put("msg","勾选认证出现异常，请联系开发人员");
        }
        return result;
    }
}
