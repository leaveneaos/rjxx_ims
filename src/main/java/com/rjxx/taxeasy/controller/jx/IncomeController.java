package com.rjxx.taxeasy.controller.jx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.dao.leshui.FpcyJpaDao;
import com.rjxx.taxeasy.dao.leshui.FpcyMapper;
import com.rjxx.taxeasy.dao.leshui.FpcyjlJpaDao;
import com.rjxx.taxeasy.dao.leshui.FpcymxJpaDao;
import com.rjxx.taxeasy.domains.leshui.Fpcy;
import com.rjxx.taxeasy.domains.leshui.Fpcyjl;
import com.rjxx.taxeasy.domains.leshui.Fpcymx;
import com.rjxx.taxeasy.service.leshui.LeshuiService;
import com.rjxx.taxeasy.vo.FpcyVo;
import com.rjxx.taxeasy.web.BaseController;
import com.rjxx.utils.ChinaNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2018-01-22.
 * 进项管理页面
 */
@Controller
@RequestMapping("/income")
public class IncomeController extends BaseController {

    @Autowired
    private LeshuiService leshuiService;
    @Autowired
    private FpcyJpaDao fpcyJpaDao;
    @Autowired
    private FpcyjlJpaDao fpcyjlJpaDao;
    @Autowired
    private FpcyMapper fpcyMapper;
    @Autowired
    private FpcymxJpaDao fpcymxJpaDao;


    @RequestMapping
    public String index() throws Exception {
        return "jx/fpcy/index";
    }


    /**
     * 查询列表
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
    @RequestMapping("/getFpcyList")
    public Map<String, Object> getFpcyList(int length, int start, int draw, String fpdm, String fphm, String kprqq,
                                         String gfsh, String fpzldm,boolean loaddata) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
		Pagination pagination = new Pagination();
//        Map map = new HashMap();
        if(loaddata){
            pagination.setPageNo(start / length + 1);
            pagination.setPageSize(length);
            //map.put("start",start);
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
                pagination.addParam("xfsh", gfsh);
            }
            List<FpcyVo> dataList = new ArrayList();
            List<Fpcy> fpcyList = fpcyMapper.findByPage(pagination);
            for (Fpcy fpcy : fpcyList) {
                Fpcyjl fpcyjl = fpcyjlJpaDao.findOneByFpcyIdAndGsdm(fpcy.getId(), getGsdm());
                Integer cycsTotal = fpcyjlJpaDao.findCountByFpcyId(fpcyjl.getFpcyid());
                FpcyVo fpcyVo = new FpcyVo();
                fpcyVo.setId(fpcy.getId());
                fpcyVo.setFpdm(fpcy.getFpdm());
                fpcyVo.setFphm(fpcy.getFphm());
                fpcyVo.setXfmc(fpcy.getXfmc());
                fpcyVo.setBxry(fpcyjl.getBxry());
                fpcyVo.setKprq(fpcy.getKprq());
                fpcyVo.setFpzldm(fpcy.getFpzldm());
                fpcyVo.setCycs(fpcy.getCycs());
                fpcyVo.setCycsTotal(cycsTotal);
                fpcyVo.setSjly(fpcy.getSjly());
                fpcyVo.setFpzt(fpcy.getFpzt());
                dataList.add(fpcyVo);
            }
            //logger.info("查询结果"+JSON.toJSONString(dataList));
            int total;
            if(0 == start){
                //total = fpcyMapper.findtotal(map);
                 total = pagination.getTotalRecord();
                request.getSession().setAttribute("total",total);
            }else{
                total =  (Integer)request.getSession().getAttribute("total");
                //request.getSession().getAttribute("total");
            }
            result.put("recordsTotal", total);
            result.put("recordsFiltered", total);
            result.put("draw", draw);
            result.put("data", dataList);
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
     * 发票查验
     * @param sglr_fpzl
     * @param sglr_jym
     * @param sglr_fpdm
     * @param sglr_fphm
     * @param sglr_kprq
     * @param sglr_je
     * @return
     */
    @RequestMapping(value = "/invoiceCheck" , method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> invoiceCheck(String sglr_fpzl,String sglr_jym ,String sglr_fpdm,
                                    String sglr_fphm,String sglr_kprq,String sglr_je) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            //校验是否报销
            String gsdm = getGsdm();
            //Cszb sfyybx = cszbService.getSpbmbbh(gsdm, null, null, "sfyybx");
            //if(sfyybx.getCsz().equals("是")){
            Fpcy fpcy = fpcyJpaDao.findOneByFpdmAndFphm(sglr_fpdm, sglr_fphm);
                if(fpcy !=null){
                    Fpcyjl fpcyjl = fpcyjlJpaDao.findOneBy1FpcyId(fpcy.getId());
                    List<Fpcymx> fpcymxList = fpcymxJpaDao.findOneByFpcyId(fpcy.getId());
                    session.setAttribute("fpcy",fpcy);
                    if(fpcyjl != null){
                        session.setAttribute("fpcyjlList",fpcyjl);
                    }
                    if(fpcymxList.size()>0){
                        session.setAttribute("fpcymxList",fpcymxList);
                    }
                    result.put("status", true);
//                    result.put("msg","查验成功");
                    if(fpcyjl.getBxry() == null){
                        result.put("msg","该发票已存在"+"，上次查验日期："+fpcy.getCyrq()+"\r\n查验状态为："+fpcy.getFpzt());
                    }else {
                        result.put("msg","该发票已存在，报销人："+fpcyjl.getBxry()+"\r\n上次查验日期："+fpcy.getCyrq()+"\r\n查验状态为："+fpcy.getFpzt());
                    }
                    result.put("requery","1");
                    return result;
                }
           // }
            String res = leshuiService.fpcy(sglr_fpdm, sglr_fphm, sglr_kprq, sglr_jym, sglr_je,"01",getGsdm());
            logger.info(JSON.toJSONString(res));
            JSONObject resultJson = JSON.parseObject(res);
            String resultMsg_r = resultJson.getString("resultMsg");//查验结果
            result.put("msg",resultMsg_r);
            result.put("status", true);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", false);
            result.put("msg", "发票查验出现错误: " + e.getMessage());
        }
        return result;
    }



    @RequestMapping(value = "/requery" , method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> requery(String sglr_fpzl,String sglr_jym ,String sglr_fpdm,
                                            String sglr_fphm,String sglr_kprq,String sglr_je) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            //校验是否报销
            String gsdm = getGsdm();
            String res = leshuiService.fpcy(sglr_fpdm, sglr_fphm, sglr_kprq, sglr_jym, sglr_je,"01",getGsdm());
            logger.info(JSON.toJSONString(res));
            JSONObject resultJson = JSON.parseObject(res);
            String resultMsg_r = resultJson.getString("resultMsg");//查验结果
            result.put("msg",resultMsg_r);
            result.put("status", true);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", false);
            result.put("msg", "发票查验出现错误: " + e.getMessage());
        }
        return result;
    }


    /**
     * 发票查验删除
     * @param fpcyIds
     * @return
     */
    @ResponseBody
    @RequestMapping("/sc")
    public Map<String, Object> sc(String fpcyIds) {
        Map<String, Object> result = new HashMap<String, Object>();
        String[] ids = fpcyIds.split(",");
        for (String id : ids) {
            Fpcy fpcy = fpcyJpaDao.findOne(Integer.valueOf(id));
            fpcy.setYxbz("0");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d = new Date();
            fpcy.setXgsj(sdf.format(d));
            fpcyJpaDao.save(fpcy);
        }
        result.put("msg", "删除成功");
        return result;
    }

    /**
     * 发票查验预览
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping( value= "/fpcyyl")
    public String fpcyyl(String id)   {
        //Map<String, Object> result = null;
        try {
            //result = new HashMap<>();
            Fpcy fpcy = new Fpcy();
            List<Fpcymx> fpcymxList = new ArrayList<>();
            List<Fpcyjl> fpcyjlList = new ArrayList<>();
            fpcy = fpcyJpaDao.findOne(Integer.valueOf(id));
            fpcymxList = fpcymxJpaDao.findOneByFpcyId(fpcy.getId());
            fpcyjlList = fpcyjlJpaDao.findOneByFpcyId(fpcy.getId());
            List dxlist = new ArrayList();
            ChinaNumber cn = new ChinaNumber();
            if(fpcy.getJshj() !=null && !fpcy.getJshj().equals("")){
                String jshjstr = fpcy.getJshj().toString();
                dxlist.add(cn.getCHSNumber(jshjstr));
            }
            session.setAttribute("zwlist", dxlist);
            session.setAttribute("fpcy",fpcy);
            session.setAttribute("fpcymxList",fpcymxList);
            session.setAttribute("fpcyjlList",fpcyjlList);
            //result.put("status",true);
            //result.put("id",id);
            //result.put("fpcy",fpcy);
            //result.put("fpcymxList",fpcymxList);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            //result.put("status",false);
            //result.put("msg","程序出错，请联系开发人员;");
        }
        return "jx/fpcy/fapiao";
    }

    /**
     * 发票查验-保存报销人
     * @param fpbq
     * @param bxr
     * @param id
     * @return
     */
    @RequestMapping( value= "/saveBc")
    @ResponseBody
    public Map saveBc(String fpbq,String bxr,String id){
        Map<String, Object> result = null;
        try {
            result = new HashMap<>();
            Fpcy fpcy = fpcyJpaDao.findOne(Integer.valueOf(id));
            result.put("status",true);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status",false);
            result.put("msg","程序出错，请联系开发人员;");
        }
        return result;
    }

    @RequestMapping(value = "cycs")
    @ResponseBody
    public Map cycs( String id){
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Fpcy fpcy = new Fpcy();
            List<Fpcyjl> fpcyjlList = new ArrayList<>();
            if(session.getAttribute("fpcy") == null){
                fpcy = fpcyJpaDao.findOne(Integer.valueOf(id));
                fpcyjlList = fpcyjlJpaDao.findOneByFpcyId(fpcy.getId());
                if(fpcyjlList.size()>0){
                    result.put("fpcyjlList",fpcyjlList);
                    result.put("status",true);
                    return result;
                }
            }else {
                fpcy = (Fpcy)session.getAttribute("fpcy");
                fpcyjlList = (List<Fpcyjl>) session.getAttribute("fpcyjlList");
                result.put("fpcyjlList",fpcyjlList);
                result.put("status",true);
                return result;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            result.put("status",false);
        }
        return result;
    }
}
