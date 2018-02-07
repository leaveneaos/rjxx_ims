package com.rjxx.taxeasy.controller.jx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.dao.leshui.JxfpmxJpaDao;
import com.rjxx.taxeasy.dao.leshui.JxfpxxJpaDao;
import com.rjxx.taxeasy.dao.leshui.JxfpxxMapper;
import com.rjxx.taxeasy.dao.leshui.JxywjlJpaDao;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.domains.leshui.Jxfpmx;
import com.rjxx.taxeasy.domains.leshui.Jxfpxx;
import com.rjxx.taxeasy.domains.leshui.Jxywjl;
import com.rjxx.taxeasy.filter.SystemControllerLog;
import com.rjxx.taxeasy.service.XfService;
import com.rjxx.taxeasy.service.leshui.LeshuiService;
import com.rjxx.taxeasy.web.BaseController;
import com.rjxx.time.TimeUtil;
import com.rjxx.utils.ChinaNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

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
    @Autowired
    private JxfpmxJpaDao jxfpmxJpaDao;
    @Autowired
    private LeshuiService leshuiService;
    @Autowired
    private JxywjlJpaDao jxywjlJpaDao;
    @Autowired
    private XfService xfService;

    @RequestMapping
    public String index() throws Exception {
        request.setAttribute("xfList", getXfList());
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
     * @param gfsh
     * @param fpzldm
     * @param loaddata
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/getJxfpxxList")
    public Map<String, Object> getFpcyList(int length, int start, int draw, String fpdm, String fphm, String kprqq,
                                           String gfsh, String fpzldm,boolean loaddata) throws Exception {
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
            pagination.addParam("gfs",getXfList());
            pagination.addParam("kprqq",kprqq);
            pagination.addParam("gsdm",gsdm);
            if (null != gfsh && !"".equals(gfsh) && !"-1".equals(gfsh)) {
                pagination.addParam("gfsh", gfsh);
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

    @RequestMapping(value = "/zpxz" , method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> invoiceCheck() {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            //校验是否报销
            String gsdm = getGsdm();
            String startDateStr ="";
            String endDateStr ="";
            String msg="";
            List<Xf> xfList = getXfList();
            if(xfList.size()>0){
                for (Xf xf : xfList) {
                    Jxywjl jxywjl = jxywjlJpaDao.findOneByGfId(xf.getId(), gsdm);
                    if(jxywjl !=null){
                        //比较现在时间跟上次下载时间
                        Date d = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String nowDate = sdf.format(d);
                        startDateStr=nowDate;//现在时间
                        endDateStr = jxywjl.getJssj().toString();//上次下载时间
                        int i = compare_date(startDateStr, endDateStr);
                        if(i == -1 || i==0){
                            logger.info("现在时间小于等于上次下载时间");
                            msg = "专票下载部分成功，税号为"+xf.getXfsh()+"的专票下载出错";
                        }else if(i == 1){
                            String res = leshuiService.fpcxBatch(jxywjl.getJssj(),new Date(),xf.getXfsh(),gsdm,xf.getId());
                            logger.info(JSON.toJSONString(res));
                            if(res!=null && res.equals("0000")){
                                msg = "专票下载成功！";
                            }else if (res.equals("5555")){
                                msg = "专票下载部分成功！";
                            }else if(res.equals("9999")){
                                msg = "专票下载失败！";
                            }
                        }else {
                            result.put("msg","专票下载出错，请联系开发人员");
                            result.put("status", false);
                            return result;
                        }
                    }else {
                        logger.info("根据税号没有查到业务记录");
                        Date now = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat y = new SimpleDateFormat("yyyy");
                        String jssj = TimeUtil.getBeforeDays(sdf.format(now),1);
                        String year = y.format(now)+"-01-01";
                        String kssj = TimeUtil.getBeforeDays(jssj, 365);
                        String ress = leshuiService.fpcxBatch(sdf.parse(year),sdf.parse(jssj), xf.getXfsh(), gsdm, xf.getId());
                        logger.info(JSON.toJSONString(ress));
                        if(ress!=null && ress.equals("0000")){
                            msg = "专票下载成功！";
                        }else if (ress.equals("5555")){
                            msg = "专票下载部分成功！";
                        }else if(ress.equals("9999")){
                            msg = "专票下载失败！";
                        }

                    }
                }
                result.put("status", true);
                result.put("msg",msg);
                return result;
            }else {
                result.put("msg","进项购方信息没有维护，请先维护购方信息！");
                result.put("status", false);
                return result;
            }

        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", false);
            result.put("msg", "发票查验出现错误: " + e.getMessage());
        }
        return result;
    }
    public static int compare_date(String DATE1, String DATE2) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("dt1 在dt2前");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("dt1在dt2后");
                return -1;
            } else {
                System.out.println("dt1等于dt2");
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 2;
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
                jxfpxx.setXgsj(new Date());
                jxfpxxJpaDao.save(jxfpxx);
            }
            result.put("msg", "删除成功");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            result.put("msg","删除失败，请联系开发人员");
        }
        return result;
    }

    /**
     * 修改进项发票信息是否收票
     * @param id
     * @param sfsp
     * @param fpbq
     * @return
     */
    @ResponseBody
    @RequestMapping("/xgjxfpxx")
    @SystemControllerLog(description = "修改进行发票详情", key = "fplsh")
    public Map<String, Object> xgjxfpxx(String id,String sfsp,String fpbq) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Jxfpxx jxfpxx = jxfpxxJpaDao.findOne(Integer.valueOf(id));
            jxfpxx.setSfsp(sfsp);
            jxfpxx.setXgsj(new Date());
            jxfpxxJpaDao.save(jxfpxx);
            result.put("msg", true);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            result.put("msg",false);
        }
        return result;
    }

    /**
     * 修改进项发票信息勾选标志
     * @param fplshs
     * @return
     */
    @ResponseBody
    @RequestMapping("/jxfpxxGx")
    @SystemControllerLog(description = "勾选", key = "fplshs")
    public Map<String, Object> jxfpxxGx(String fplshs) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            String[] ids = fplshs.split(",");
            for (String id : ids) {
                Jxfpxx jxfpxx = jxfpxxJpaDao.findOne(Integer.valueOf(id));
                jxfpxx.setGxbz("1");
                jxfpxx.setXgsj(new Date());
                jxfpxx.setGxsj(new Date());
                jxfpxxJpaDao.save(jxfpxx);
            }
            result.put("status", true);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            result.put("status",false);
        }
        return result;
    }

    /**
     * 发票采集-发票预览
     * @param fplsh
     * @return
     * @throws Exception
     */
    @RequestMapping( value= "/fpcjyl")
    public String fpcjyl(String fplsh)   {
        try {

            Jxfpxx jxfpxx = jxfpxxJpaDao.findOne(Integer.valueOf(fplsh));
            List<Jxfpmx> jxfpmxList = jxfpmxJpaDao.findOneByFplsh(Integer.valueOf(fplsh));
            List dxlist = new ArrayList();
            ChinaNumber cn = new ChinaNumber();
            if(jxfpxx.getJshj() !=null && !jxfpxx.getJshj().equals("")){
                String jshjstr = jxfpxx.getJshj().toString();
                dxlist.add(cn.getCHSNumber(jshjstr));
            }
            session.setAttribute("zwlist", dxlist);
            session.setAttribute("jxfpxx",jxfpxx);
            session.setAttribute("jxfpmxList",jxfpmxList);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "jx/fpcj/fapiao";
    }
}
