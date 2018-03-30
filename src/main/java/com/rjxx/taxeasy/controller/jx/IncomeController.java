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
import com.rjxx.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
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
        if(loaddata){
            Pagination pagination = new Pagination();
            pagination.setPageNo(start / length + 1);
            pagination.setPageSize(length);
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (Fpcy fpcy : fpcyList) {
                Fpcyjl fpcyjl = fpcyjlJpaDao.findOneByFpcyIdAndGsdm(fpcy.getId(), getGsdm());
                Fpcyjl fpcyjlsj = fpcyjlJpaDao.findLastSuccessLrsj(fpcy.getId(), getGsdm());
                Integer cycsTotal = fpcyjlJpaDao.findCountByFpcyId(fpcyjl.getFpcyid());
                FpcyVo fpcyVo = new FpcyVo();
                fpcyVo.setId(fpcy.getId());
                fpcyVo.setFpdm(fpcy.getFpdm());
                fpcyVo.setFphm(fpcy.getFphm());
                fpcyVo.setXfmc(fpcy.getXfmc());
                fpcyVo.setBxry(fpcy.getBxry());
                fpcyVo.setKprq(sdf.format(fpcy.getKprq()));
                fpcyVo.setFpzldm(fpcy.getFpzldm());
                fpcyVo.setCycs(fpcy.getCycs());
                fpcyVo.setCycsTotal(cycsTotal);
                fpcyVo.setSjly(fpcy.getSjly());
                fpcyVo.setFpzt(fpcy.getFpzt());
                fpcyVo.setLrsj(sdf1.format(fpcyjlsj.getLrsj()));
                dataList.add(fpcyVo);
            }
            //logger.info("查询结果"+JSON.toJSONString(dataList));
            int total = pagination.getTotalRecord();
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
                                    String sglr_fphm,String sglr_kprq,String sglr_je ,String sglr_bxr) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            //校验是否报销
            String gsdm = getGsdm();
            String msg="";
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
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String fpzt ="";
                    if(fpcyjl.getReturncode().equals("00")){
                        if(fpcyjl.getResultcode().equals("1000")){
                            if(fpcy.getFpzt()!=null && fpcy.getFpzt().equals("0")){
                                fpzt = "正常";
                            }else if(fpcy.getFpzt()!=null && fpcy.getFpzt().equals("1")){
                                fpzt = "作废";
                            }
                        }else {
                            fpzt = fpcyjl.getResultmsg();
                        }
                    }else{
                        fpzt = fpcyjl.getResultmsg();
                    }

                    result.put("status", true);
                    if(StringUtils.isBlank(fpcyjl.getBxry())){
                        if(fpcy.getCyrq()==null){
                            result.put("msg","该发票已存在"+"\r\n查验状态为："+fpzt);
                        }else {
                            result.put("msg","该发票已存在"+"，上次查验日期："+sdf.format(fpcy.getCyrq())+"\r\n查验状态为："+fpzt);
                        }
                    }else {
                        if(fpcy.getCyrq()==null){
                            result.put("msg","该发票已存在，报销人："+fpcyjl.getBxry()+"\r\n查验状态为："+fpzt);
                        }else {
                            result.put("msg","该发票已存在，报销人："+fpcyjl.getBxry()+"\r\n上次查验日期："+sdf.format(fpcy.getCyrq())+"\r\n查验状态为："+fpzt);

                        }
                    }
                    result.put("requery","1");
                    return result;
                }
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            String res = leshuiService.fpcy(sglr_fpdm, sglr_fphm,sdf1.parse(sglr_kprq) , sglr_jym, sglr_je,"01",getGsdm(),sglr_bxr);
            logger.info(JSON.toJSONString(res));
            JSONObject resultJson = JSON.parseObject(res);
            String resultMsg_r = resultJson.getString("resultMsg");//查验结果
            result.put("msg",resultMsg_r);
            result.put("status", true);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", false);
            result.put("msg", "发票查验出现错误");
        }
        return result;
    }



    @RequestMapping(value = "/requery" , method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> requery(String sglr_fpzl,String sglr_jym ,String sglr_fpdm,
                                            String sglr_fphm,String sglr_kprq,String sglr_je,String sglr_bxr) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            String gsdm = getGsdm();
            String res = leshuiService.fpcy(sglr_fpdm, sglr_fphm,sdf1.parse(sglr_kprq), sglr_jym, sglr_je,"01",getGsdm(),sglr_bxr);
//            logger.info(JSON.toJSONString(res));
            JSONObject resultJson = JSON.parseObject(res);
            String resultMsg_r = resultJson.getString("resultMsg");//查验结果
            result.put("msg",resultMsg_r);
            result.put("status", true);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", false);
            result.put("msg", "发票查验出现错误");
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
            fpcy.setXgsj(new Date());
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
        try {
            Fpcy fpcy = fpcyJpaDao.findOne(Integer.valueOf(id));
            List<Fpcymx> fpcymxList = fpcymxJpaDao.findOneByFpcyId(fpcy.getId());
            List<Fpcyjl> fpcyjlList = fpcyjlJpaDao.findByFpcyId(fpcy.getId());
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
        } catch (NumberFormatException e) {
            e.printStackTrace();
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
                if(StringUtils.isNotBlank(bxr)){
                    //Fpcyjl fpcyjl = fpcyjlJpaDao.findOneBy1FpcyId(Integer.valueOf(fpcy.getId()));
                    //fpcyjl.setBxry(bxr);
                    //fpcyjlJpaDao.save(fpcyjl);
                    fpcy.setBxry(bxr);
                    fpcy.setXgsj(new Date());
                    fpcyJpaDao.save(fpcy);
                }
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
    public Map<String,Object> cycs(int length, int start, int draw,String id,boolean loaddata){
        Map<String, Object> result = new HashMap<String, Object>();
        List<FpcyVo> resultList = new ArrayList<>();
        try {
            if(loaddata){
                Fpcy fpcy = fpcyJpaDao.findOne(Integer.valueOf(id));
                List<Fpcyjl> fpcyjlList = fpcyjlJpaDao.findByFpcyId(fpcy.getId());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                int i=1;
                    for (Fpcyjl fpcyjl : fpcyjlList) {
                        FpcyVo fpcyVo = new FpcyVo();
                        if(fpcyjl.getFpzt()!=null && fpcyjl.getFpzt().equals("0")){
                            fpcyVo.setFpzt("正常");
                        }else if(fpcyjl.getFpzt()!=null && fpcyjl.getFpzt().equals("1")){
                            fpcyVo.setFpzt("作废");
                        }else if(fpcyjl.getFpzt()!=null && fpcyjl.getFpzt().equals("9")){
                            fpcyVo.setFpzt("--");
                        }
                        if(fpcyjl.getLrsj()!=null&&!fpcyjl.getLrsj().equals("")){
                            String format = sdf.format(fpcyjl.getLrsj());
                            fpcyVo.setCyrq(format);
                        }else {
                            fpcyVo.setCyrq("");
                        }
                        fpcyVo.setResultMsg(fpcyjl.getResultmsg());
                        fpcyVo.setCycs(i);
                        resultList.add(fpcyVo);
                        i++;
                    }
                int total;
                if(0 == start){
                    total = fpcyjlJpaDao.findCountByFpcyId(Integer.valueOf(id));
                    //total = pagination.getTotalRecord();
                    request.getSession().setAttribute("total",total);
                }else{
                    total =  (Integer)request.getSession().getAttribute("total");
                    //request.getSession().getAttribute("total");
                }
                result.put("recordsTotal", total);
                result.put("recordsFiltered", total);
                result.put("draw", draw);
                result.put("data", resultList);
            }else{
                int total = 0;
                result.put("recordsTotal", total);
                result.put("recordsFiltered", total);
                result.put("draw", draw);
                result.put("data", new ArrayList<>());
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            result.put("status",false);
        }
        return result;
    }
}
