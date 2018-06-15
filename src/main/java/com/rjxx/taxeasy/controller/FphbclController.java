package com.rjxx.taxeasy.controller;

import com.alibaba.fastjson.JSON;
import com.rjxx.taxeasy.bizcomm.utils.DiscountDealUtil;
import com.rjxx.taxeasy.bizcomm.utils.FphbService;
import com.rjxx.taxeasy.dao.JyxxsqHbJpaDao;
import com.rjxx.taxeasy.dao.JyxxsqJpaDao;
import com.rjxx.taxeasy.dto.FphbData;
import com.rjxx.taxeasy.filter.SystemControllerLog;
import com.rjxx.taxeasy.service.JymxsqService;
import com.rjxx.taxeasy.service.JyxxsqService;
import com.rjxx.taxeasy.service.SkpService;
import com.rjxx.taxeasy.service.XfService;
import com.rjxx.taxeasy.vo.JyxxsqVO;
import com.rjxx.taxeasy.web.BaseController;
import com.rjxx.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
    @Autowired
    private FphbService fphbService;


    @RequestMapping
    public String index() {
        request.setAttribute("xfList", getXfList());
        request.setAttribute("skpList", getSkpList());
        return "fphbcl/index";
    }

    //查询未开票数据进行合并
    @RequestMapping(value="/getItems")
    @ResponseBody
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
            if(fpList.isEmpty()){
                int total = 0;
                result.put("recordsTotal", total);
                result.put("recordsFiltered", total);
                result.put("draw", draw);
                result.put("data", new ArrayList<>());
                return result;
            }
            Map map1 = new HashMap();
            map1.put("jshjSum",jshjSum);
            String sqlshs="";
            for (JyxxsqVO jyxxsqVO : fpList) {
               sqlshs += jyxxsqVO.getSqlsh()+",";
            }
            int indx = sqlshs.lastIndexOf(",");
            if(indx!=-1){
                sqlshs = sqlshs.substring(0,indx)+sqlshs.substring(indx+1,sqlshs.length());
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


    //发票合并
    @RequestMapping(value = "/fphb")
    @ResponseBody
    public Map fphbcl(String sqlshs,String gfmc,String gfsh,String gfdz,
                    String gfdh,String gfyh,String yhzh){
        Map<String, Object> result = new HashMap();
        int total =0;
        try {
            if(StringUtils.isBlank(sqlshs)){
                result.put("data",new ArrayList<>());
                result.put("recordsTotal", total);
                result.put("recordsFiltered", total);
                result.put("msg","合并失败，合并数据为空");
                return result;
            }
            if(StringUtils.isBlank(gfmc)){
                result.put("data",new ArrayList<>());
                result.put("recordsTotal", total);
                result.put("recordsFiltered", total);
                result.put("msg","购方名称为空");
                return result;
            }
            String[] sqs = sqlshs.split(",");
            List sqlshList = new ArrayList();
            for (String sqlsh : sqs) {
                sqlshList.add(sqlsh);
            }
            if(sqlshList.isEmpty()){
                result.put("data",new ArrayList<>());
                result.put("recordsTotal", total);
                result.put("recordsFiltered", total);
                result.put("msg","合并数据失败");
                return result;
            }
            FphbData data = new FphbData();
            data.setSqlshList(sqlshList);
            data.setGfmc(gfmc);
            data.setGfsh(gfsh);
            data.setGfdh(gfdz);
            data.setGfdh(gfdh);
            data.setGfyh(gfyh);
            data.setGfyhzh(yhzh);
            List list = fphbService.fphbcl(data);
            if(list.isEmpty()){
                result.put("data",new ArrayList<>());
                result.put("recordsTotal", total);
                result.put("recordsFiltered", total);
                result.put("msg","合并数据失败");
                return result;
            }
            result.put("data",list);
            result.put("recordsTotal", list.size());
            result.put("recordsFiltered", list.size());
            result.put("msg", "合并成功!");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("data",new ArrayList<>());
            result.put("recordsTotal", total);
            result.put("recordsFiltered", total);
            result.put("msg","合并失败，系统错误");
            return result;
        }
        return  result;
    }

    //合并撤销
    @RequestMapping(value = "/fphbCancle")
    @ResponseBody
    public Map fphbCancle(String sqlshs){
        Map<String, Object> result = new HashMap();
        try {
            if(StringUtils.isBlank(sqlshs)){
                result.put("success",false);
                result.put("msg","撤销失败，撤销数据为空");
                return result;
            }
            List<Integer> sqlshList = new ArrayList();
            String[] sqs = sqlshs.split(",");
            for (String sqlsh : sqs) {
                sqlshList.add(Integer.valueOf(sqlsh));
            }
            boolean b = fphbService.fphbCancle(sqlshList, getGsdm(), getYhid());
            if(b){
                result.put("success",true);
                result.put("msg","撤销数据成功");
            }else {
                result.put("success",false);
                result.put("msg","撤销数据失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success",false);
            result.put("msg","撤销失败，系统异常");
            return result;
        }
        return result;
    }

    //合并保存
    @RequestMapping(value = "/fphbSave")
    @ResponseBody
    public Map fphbSave(String sqlshs){
        Map<String, Object> result = new HashMap();
        try {
            if(StringUtils.isBlank(sqlshs)){
                result.put("success",false);
                result.put("msg","保存失败,保存数据为空");
                return result;
            }
            List sqlshList = new ArrayList();
            String[] sqs = sqlshs.split(",");
            for (String sqlsh : sqs) {
                sqlshList.add(sqlsh);
            }
            boolean b = fphbService.fphbsave(sqlshList, getYhid(),getGsdm());
            if(b){
                result.put("success",true);
                result.put("msg","保存数据成功");
            }else {
                result.put("success",false);
                result.put("msg","保存数据失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success",false);
            result.put("msg","保存失败,系统错误");
            return result;
        }
        return result;
    }
}
