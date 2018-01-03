package com.rjxx.taxeasy.bizcomm.utils;

import com.caucho.hessian.client.HessianProxyFactory;
import com.pk.hessian.SKIService;
import com.pk.hessian.bean.FwqxxBean;
import com.pk.hessian.bean.JkglxxBean;
import com.pk.hessian.bean.ResultBean;
import com.rjxx.Application;
import com.rjxx.taxeasy.db.DBFactory;
import com.rjxx.taxeasy.service.SkInvoiceService;
import com.sk.common.utils.SResult;
import org.apache.commons.collections.map.HashedMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xlm on 2017/12/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class testHessian {

    @Autowired
    private DBFactory skDBFactory;
    @Autowired
    private SkInvoiceService skInvoiceService;

    @Test
    public void getdata() {
        String url="http://180.153.192.171:18080/SKServer/remoting/SKIService";
        HessianProxyFactory factory = new HessianProxyFactory();
        SKIService skiService = null;
        Connection connection = null;
        try {
            connection = skDBFactory.getConnection();
            String jqbh="499099832080";
            List<Map<String, Object>> dataList = skInvoiceService.getFwqxxData(jqbh,connection);
            if (dataList.isEmpty()) {
                return;
            }
            FwqxxBean fwqxx=new FwqxxBean();
            Map<String, Object> fwqxxMap=dataList.get(0);
            fwqxx.setId(fwqxxMap.get("id").toString().trim());
            fwqxx.setJqbh(fwqxxMap.get("jqbh").toString().trim());
            fwqxx.setIpdz(fwqxxMap.get("ipdz").toString().trim());
            fwqxx.setDkh(fwqxxMap.get("dkh").toString().trim());
            fwqxx.setFwqlx(fwqxxMap.get("fwqlx").toString().trim());
            fwqxx.setFwqxh(fwqxxMap.get("fwqxh").toString().trim());
            fwqxx.setFwqrl(Integer.parseInt(fwqxxMap.get("fwqrl").toString()));
            fwqxx.setFplxdm(fwqxxMap.get("fplxdm").toString().trim());
            fwqxx.setQybz(fwqxxMap.get("qybz").toString().trim());
            //fwqxx.setFwqbm(fwqxxMap.get("fwqbm").toString().trim());
            fwqxx.setFwqbbh(fwqxxMap.get("fwqbbh").toString().trim());
            fwqxx.setKpdwdm(fwqxxMap.get("kpdwdm").toString().trim());
            fwqxx.setKpdwmc(fwqxxMap.get("kpdwmc").toString().trim());
            fwqxx.setSwjgdm(fwqxxMap.get("swjgdm").toString().trim());
            fwqxx.setSwjgmc(fwqxxMap.get("swjgmc").toString().trim());
            fwqxx.setSldz(fwqxxMap.get("sldz").toString().trim());
            fwqxx.setSldkh(fwqxxMap.get("sldkh").toString().trim());
            fwqxx.setKpjh(fwqxxMap.get("kpjh").toString().trim());
            skiService = (SKIService) factory.create(SKIService.class, url);
            JkglxxBean jkglxxBean=new JkglxxBean();
            jkglxxBean.setIpdz(fwqxx.getIpdz());
            jkglxxBean.setDkh(fwqxx.getDkh());
            jkglxxBean.setFplxdm(fwqxx.getFplxdm());
            jkglxxBean.setJqbh(fwqxx.getJqbh());
            SResult sResult=skiService.queryJkxx(jkglxxBean);
            Map<String, Object>  jkxxMap=sResult.getReturndata();
            fwqxx.setOne(jkxxMap);
            ResultBean resultBean=skiService.cb(fwqxx);
            System.out.println(resultBean.getId());
            System.out.println(resultBean.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
