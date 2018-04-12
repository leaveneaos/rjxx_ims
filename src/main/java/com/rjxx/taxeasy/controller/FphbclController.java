package com.rjxx.taxeasy.controller;

import com.alibaba.fastjson.JSON;
import com.rjxx.taxeasy.bizcomm.utils.DiscountDealUtil;
import com.rjxx.taxeasy.dao.JyxxsqHbJpaDao;
import com.rjxx.taxeasy.dao.JyxxsqJpaDao;
import com.rjxx.taxeasy.domains.*;
import com.rjxx.taxeasy.filter.SystemControllerLog;
import com.rjxx.taxeasy.service.JymxsqService;
import com.rjxx.taxeasy.service.JyxxsqService;
import com.rjxx.taxeasy.service.SkpService;
import com.rjxx.taxeasy.service.XfService;
import com.rjxx.taxeasy.vo.JymxsqVo;
import com.rjxx.taxeasy.vo.JyxxsqVO;
import com.rjxx.taxeasy.web.BaseController;
import com.rjxx.utils.BeanConvertUtils;
import com.rjxx.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: zsq
 * @date: 2018/4/10 13:49
 * @describe: 发票合并处理
 */
@Controller
@RequestMapping("/fphbcl")
public class FphbclController extends BaseController {

    @Autowired
    private JyxxsqService jyxxsqService;
    @Autowired
    private XfService xfService;
    @Autowired
    private SkpService skpService;
    @Autowired
    private JyxxsqJpaDao jyxxsqJpaDao;
    @Autowired
    private JyxxsqHbJpaDao jyxxsqHbJpaDao;
    @Autowired
    private JymxsqService jymxsqService;
    @Autowired
    private DiscountDealUtil discountDealUtil;
    @Autowired
    private JyxxsqService jyxxsqservice;


    @RequestMapping
    @SystemControllerLog(description = "发票合并处理", key = "")
    public String index() {
        request.setAttribute("xfList", getXfList());
        request.setAttribute("skpList", getSkpList());
        return "fphbcl/index";
    }

    //查询未开票
    @ResponseBody
    @RequestMapping("/getItems")
    public Map<String, Object> getItems(int length, int start, int draw, String ddh, String kprqq, String kprqz,
                                        String spmc, String gfmc, String xfsh, String fpzldm, boolean loaddata) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        Map map = new HashMap();
        if(loaddata){
            List list = new ArrayList();
            map.put("start",start);
            map.put("length",length);
            String gsdm = getGsdm();
            map.put("ddh",ddh);
            map.put("ztbz","6");//状态标志6表示未处理，5表示已部分处理
            map.put("bfzt","5");
            map.put("kprqq",kprqq);
            map.put("kprqz",kprqz);
            map.put("gsdm",gsdm);
            if (null != xfsh && !"".equals(xfsh) && !"-1".equals(xfsh)) {
                map.put("xfsh", xfsh);
            }
            List<JyxxsqVO> fpList = jyxxsqService.findByPage2(map);
            Integer jshjSum = jyxxsqService.findJshjSum(map);
            Map map1 = new HashMap();
            map1.put("jshjSum",jshjSum);
            String sqlshs="";
            for (JyxxsqVO jyxxsqVO : fpList) {
               sqlshs += jyxxsqVO.getSqlsh()+",";
            }
            map1.put("sqlshs",sqlshs);
            int total;
            if(0 == start){
                int countTotal = jyxxsqService.findtotal(map);
                map1.put("countTotal",countTotal);
                list.add(map1);
                total = list.size();
                request.getSession().setAttribute("total",total);
            }else{
                total =  (Integer)request.getSession().getAttribute("total");
            }
            request.getSession().setAttribute("fpList",fpList);
            result.put("recordsTotal", total);
            result.put("recordsFiltered", total);
            result.put("draw", draw);
            result.put("data", list);
        }else{
            int total = 0;
            result.put("recordsTotal", total);
            result.put("recordsFiltered", total);
            result.put("draw", draw);
            result.put("data", new ArrayList<>());
        }
        return result;
    }

    //生成订单号
    public char getRandomLetter() {
        String chars = "abcdefghijklmnopqrstuvwxyz";
        return chars.charAt(new Random().nextInt(26));
    }

    //发票合并
    public Map fphb(String sqlshs,String gfmc,String gfsh,String gfdz,
                    String gfdh,String gfyh,String yhzh){
        Map<String, Object> result = new HashMap();
        if(StringUtils.isBlank(sqlshs)){
            result.put("success",false);
            result.put("msg","合并失败");
            return result;
        }
        try {
            String[] sqs = sqlshs.split(",");
            List sqlshList = new ArrayList();
            for (String sqlsh : sqs) {
                sqlshList.add(sqlsh);
            }
            Date date = new Date();
            Map map = new HashMap();
            map.put("ztbz","6");//状态标志6表示未处理，5表示已部分处理
            map.put("bfzt","5");
            map.put("gsdm","zsq");
            map.put("sqlshList",sqlshList);
            List<JyxxsqVO> listKpddm = jyxxsqService.findBySqlsh(map);
            if(listKpddm.isEmpty()){
                result.put("success",false);
                result.put("msg","合并失败,没有要合并的数据");
                return result;
            }
            List resultList = new ArrayList();
            for (JyxxsqVO jyxxsq : listKpddm) {
                //合并电票
//                if(jyxxsq.getFpzldm().equals("12")){}
                Jyxxsq newJyxxsq = saveNewJyxxsq(jyxxsq,date,gfmc,gfsh,gfyh,"",gfdz,gfdh,"");
                String sqlshCount = jyxxsq.getSqlshCount();
                String[] sq = sqlshCount.split(",");
                List hbsqlshlist = new ArrayList();
                for (String s : sq) {
                    Map map1 = new HashMap();
                    map1.put("sqlsh",s);
                    map1.put("gsdm" , newJyxxsq.getGsdm());
                    Jyxxsq jyxxsq1 = jyxxsqService.findOneByJylsh(map1);
                    jyxxsq1.setZtbz("3");
                    jyxxsqJpaDao.save(jyxxsq1);
                    hbsqlshlist.add(s);
                }
                Map jymxsqMap = new HashMap();
                jymxsqMap.put("gsdm" ,"zsq");
                jymxsqMap.put("sqlshList",hbsqlshlist);
                List<JymxsqVo> jymxsqList = jymxsqService.findBySqlsh(jymxsqMap);

                List<Jymxsq> newjymxsqList = new ArrayList<>();
                int spmxxh = 1;
                for (JymxsqVo jymxsq : jymxsqList) {
                    Jymxsq newjymxsq = new Jymxsq();
                    newjymxsq.setHsbz(jymxsq.getHsbz());
                    newjymxsq.setSpmxxh(spmxxh);
                    newjymxsq.setFphxz(jymxsq.getFphxz());
                    newjymxsq.setSpdm(jymxsq.getSpdm());
                    newjymxsq.setSpmc(jymxsq.getSpmc());
                    newjymxsq.setSpggxh(jymxsq.getSpggxh());
                    newjymxsq.setSpzxbm(jymxsq.getSpzxbm());
                    newjymxsq.setSpdw(jymxsq.getSpdw());
                    newjymxsq.setSps(jymxsq.getSumsps());
                    newjymxsq.setSpdj(jymxsq.getSpdj());
                    newjymxsq.setSpje(jymxsq.getSumspje());
                    newjymxsq.setSpsl(jymxsq.getSpsl());
                    newjymxsq.setSpse(jymxsq.getSumspse());
                    newjymxsq.setJshj(jymxsq.getSumjshj());
                    newjymxsq.setGsdm(jymxsq.getGsdm());
                    newjymxsq.setYhzcbs(jymxsq.getYhzcbs());
                    newjymxsq.setYhzcmc(jymxsq.getYhzcmc());
                    newjymxsq.setLslbz(jymxsq.getLslbz());
                    newjymxsq.setKkjje(jymxsq.getSumkkjje());
                    newjymxsq.setYkjje(0d);
                    newjymxsq.setSkpid(jymxsq.getSkpid());
                    newjymxsq.setXfid(jymxsq.getXfid());
                    newjymxsq.setYxbz("1");
                    newjymxsq.setSpbz(jymxsq.getSpbz());
                    newjymxsq.setLrry(1);
                    newjymxsq.setLrsj(date);
                    newjymxsq.setXgsj(date);
                    newjymxsq.setXgry(1);
                    spmxxh++;
                    newjymxsqList.add(newjymxsq);
                }
                List<JymxsqCl> jymxsqClList = new ArrayList<JymxsqCl>();
                List<Jymxsq> jymxsqTempList = new ArrayList<Jymxsq>();
                jymxsqTempList = BeanConvertUtils.convertList(newjymxsqList, Jymxsq.class);
                jymxsqClList = discountDealUtil.dealDiscount(jymxsqTempList, 0d, newJyxxsq.getJshj(),jyxxsq.getHsbz());
                Integer saveJyxxsq = jyxxsqservice.saveJyxxsq(newJyxxsq, newjymxsqList, jymxsqClList, new ArrayList<Jyzfmx>());
                //保存交易明细合并
                for (String s : sq) {
                    JyxxsqHb jyxxsqHb = new JyxxsqHb();
                    //保存交易信息合并表
                    jyxxsqHb.setOldsqlsh(Integer.valueOf(s));
                    jyxxsqHb.setNewsqlsh(saveJyxxsq);
                    jyxxsqHb.setYxbz("1");
                    jyxxsqHb.setLrry(1);
                    jyxxsqHb.setLrsj(date);
                    jyxxsqHb.setXgry(1);
                    jyxxsqHb.setXgsj(date);
                    jyxxsqHbJpaDao.save(jyxxsqHb);
                }
                resultList.add(newJyxxsq);
            }
            result.put("data",resultList);
            result.put("success",true);
            result.put("msg","合并成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success",false);
            result.put("msg","合并失败,请联系管理员");
            return result;
        }
        return  result;
    }


    public Jyxxsq saveNewJyxxsq(JyxxsqVO jyxxsq,Date date,String gfmc,String gfsh,String gfyh,
                                String gfyhzh,String gfdz,String gfdh,String gfemail){
        Jyxxsq jyxxsqNew = new Jyxxsq();
        jyxxsqNew.setKpddm(jyxxsq.getKpddm());
        jyxxsqNew.setJylsh("JY" + new SimpleDateFormat("yyyyMMddHHmmssSS").format(date));
        jyxxsqNew.setDdrq(date);
        jyxxsqNew.setDdh("DD" + System.currentTimeMillis() + getRandomLetter());
        jyxxsqNew.setFpzldm(jyxxsq.getFpzldm());
        jyxxsqNew.setFpczlxdm("11");
        jyxxsqNew.setXfid(jyxxsq.getXfid());
        jyxxsqNew.setXfsh(jyxxsq.getXfsh());
        jyxxsqNew.setXfmc(jyxxsq.getXfmc());
        jyxxsqNew.setXfyh(jyxxsq.getXfyh());
        jyxxsqNew.setXfyhzh(jyxxsq.getXfyhzh());
        jyxxsqNew.setXflxr(jyxxsq.getXflxr());
        jyxxsqNew.setXfdz(jyxxsq.getXfdz());
        jyxxsqNew.setXfyb(jyxxsq.getXfyb());
        jyxxsqNew.setXfdh(jyxxsq.getXfdh());
        jyxxsqNew.setGfmc(gfmc);
        jyxxsqNew.setGfsh(gfsh);
        if(StringUtils.isNotBlank(gfsh)){
            jyxxsqNew.setGflx("1");
        }else {
            jyxxsqNew.setGflx("0");
        }
        jyxxsqNew.setGfyh(gfyh);
        jyxxsqNew.setGfyhzh(gfyhzh);
        jyxxsqNew.setGfdz(gfdz);
        jyxxsqNew.setGfdh(gfdh);
        jyxxsqNew.setGfemail(gfemail);
        if(StringUtils.isNotBlank(gfemail)){
            jyxxsqNew.setSffsyj("1");
        }else {
            jyxxsqNew.setSffsyj("0");
        }
        jyxxsqNew.setClztdm("00");
        jyxxsqNew.setKpr(jyxxsq.getKpr());
        jyxxsqNew.setSkr(jyxxsq.getSkr());
        jyxxsqNew.setFhr(jyxxsq.getFhr());
        jyxxsqNew.setZsfs(jyxxsq.getZsfs());
        jyxxsqNew.setSsyf(jyxxsq.getSsyf());
        jyxxsqNew.setHsbz(jyxxsq.getHsbz());
        jyxxsqNew.setJshj(jyxxsq.getJshjSum());
        jyxxsqNew.setYkpjshj(jyxxsq.getYkpjshj());
        jyxxsqNew.setYxbz("1");
        jyxxsqNew.setLrsj(date);
        jyxxsqNew.setLrry(1);
        jyxxsqNew.setXgry(1);
        jyxxsqNew.setXgsj(date);
        jyxxsqNew.setGsdm("zsq");
        jyxxsqNew.setTqm(jyxxsq.getTqm());
        jyxxsqNew.setSkpid(jyxxsq.getSkpid());
        jyxxsqNew.setSjly("0");
        jyxxsqNew.setZtbz("6");
        jyxxsqNew.setSfdyqd(jyxxsq.getSfdyqd());
//        Jyxxsq jyxxsqSave = jyxxsqJpaDao.save(jyxxsqNew);
        return jyxxsqNew;
    }

}
