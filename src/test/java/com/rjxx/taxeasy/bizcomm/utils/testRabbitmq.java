package com.rjxx.taxeasy.bizcomm.utils;

import com.alibaba.fastjson.JSON;
import com.rjxx.Application;
import com.rjxx.taxeasy.config.RabbitmqUtils;
import com.rjxx.taxeasy.domains.Gsxx;
import com.rjxx.taxeasy.domains.Jyls;
import com.rjxx.taxeasy.domains.Kpls;
import com.rjxx.taxeasy.service.*;
import com.rjxx.taxeasy.vo.Kpspmxvo;
import com.rjxx.utils.DesUtils;
import com.rjxx.utils.StringUtils;
import com.rjxx.utils.XmlJaxbUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.*;

/**
 * Created by xlm on 2018/2/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class testRabbitmq {

    @Autowired
    private RabbitmqUtils rabbitmqUtils;
    @Autowired
    private KplsService kplsService;
    @Autowired
    private SkpService skpService;
    @Autowired
    private CszbService cszbService;
    @Autowired
    private SkService skService;
    @Autowired
    private FpclService fpclService;
    @Autowired
    private JylsService jylsService;

    @Autowired
    private KpspmxService kpspmxService;

    @Autowired
    private GeneratePdfService generatePdfService;

    @Autowired
    private GsxxService gsxxService;

    @Autowired
    private FphxUtil fphxUtil;

    @Test
    public void getmqdata(){
        try {
            String key="A!9fF&vH";
            byte[] content = (byte[]) rabbitmqUtils.receiveMsg("result", "invoice");
            String invoicedata=new String(content);
            if (StringUtils.isNotBlank(invoicedata)) {
                invoicedata = DesUtils.DESDecrypt(invoicedata, key);
                System.out.println("--------数据------------"+invoicedata);
                if(invoicedata.contains("RESPONSE_COMMON_FPKJ")){
                    Map<String, String> resultMap = new HashMap<>();
                    boolean suc = parseDzfpResultXml(resultMap, invoicedata);
                    String kplshstr=resultMap.get("FPQQLSH").toString();
                    resultMap.put("KPLSH",kplshstr);
                    int kplshpp = Integer.valueOf(kplshstr);
                    Map params = new HashMap();
                    params.put("kplsh", kplshpp);
                    Kpls kpls = kplsService.findOneByParams(params);
                    if(kpls!=null){
                        if(kpls.getFpzldm().equals("12")){
                            fpclService.updateKpls(resultMap);
                        }
                    }
                }else if(invoicedata.contains("Response")){
                    InvoiceResponse response = XmlJaxbUtils.convertXmlStrToObject(InvoiceResponse.class, invoicedata);
                    this.updateInvoiceResult(response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新开票结果
     *
     * @param response
     * @return
     */
    public void updateInvoiceResult(InvoiceResponse response) throws Exception {
        String returnCode = response.getReturnCode();
        if ("0000".equals(returnCode)) {
            String lsh = response.getLsh();
            int pos = lsh.indexOf("$");
            int kplsh;
            if (pos != -1) {
                kplsh = Integer.valueOf(lsh.substring(0, pos));
            } else {
                kplsh = Integer.valueOf(lsh);
            }
            Kpls kpls = kplsService.findOne(kplsh);
            kpls.setFpdm(response.getFpdm());
            kpls.setFphm(response.getFphm());
            kpls.setFpztdm("00");
            kpls.setErrorReason(null);
            kpls.setPrintflag("" + response.getPrintFlag());
            kpls.setKprq(DateUtils.parseDate(response.getKprq(), "yyyy-MM-dd HH:mm:ss"));
            kpls.setXgsj(new Date());
            kpls.setXgry(1);
            if (StringUtils.isNotBlank(response.getReturnMessage())) {
                kpls.setErrorReason(response.getReturnMessage());
            } else {
                kpls.setErrorReason(null);
            }
            kplsService.save(kpls);
            Jyls jyls = jylsService.findOne(kpls.getDjh());
            jyls.setClztdm("91");
            jylsService.save(jyls);
            String czlxdm = kpls.getFpczlxdm();
            if ("12".equals(czlxdm) || "13".equals(czlxdm)) {
                if (kpls.getHkFphm() != null && kpls.getHkFpdm() != null) {
                    kpls.setJylsh("");
                    Kpls ykpls = kplsService.findByhzfphm(kpls);
                    Map param2 = new HashMap<>();
                    param2.put("kplsh", ykpls.getKplsh());
                    // 全部红冲后修改
                    Kpspmxvo mxvo = kpspmxService.findKhcje(param2);
                    if (mxvo.getKhcje() == 0) {
                        param2.put("fpztdm", "02");
                        kplsService.updateFpczlx(param2);
                    } else {
                        param2.put("fpztdm", "01");
                        kplsService.updateFpczlx(param2);
                    }
                }
            }
            Map parms=new HashMap();
            parms.put("gsdm",kpls.getGsdm());
            Gsxx gsxx=gsxxService.findOneByParams(parms);
            //String url="https://vrapi.fvt.tujia.com/Invoice/CallBack";
            String url=gsxx.getCallbackurl();
            if(!("").equals(url)&&url!=null){
                String returnmessage=null;
                if(!kpls.getGsdm().equals("Family")&&!kpls.getGsdm().equals("fwk")) {
                    returnmessage = fphxUtil.CreateReturnMessage(kpls.getKplsh());
                    //输出调用结果
                    System.out.println("回写报文" + returnmessage);
                    if (returnmessage != null && !"".equals(returnmessage)) {
                        Map returnMap = fphxUtil.httpPost(returnmessage, kpls);
                        System.out.println("返回报文" + JSON.toJSONString(returnMap));
                    }
                }else if(kpls.getGsdm().equals("fwk")){
                    returnmessage = fphxUtil.CreateReturnMessage3(kpls.getKplsh());
                    System.out.println("回写报文" + returnmessage);
                    if (returnmessage != null && !"".equals(returnmessage)) {
                        String ss= HttpUtils.netWebService(url,"CallBack",returnmessage,gsxx.getAppKey(),gsxx.getSecretKey());
                    }
                }
            }
        } else {
            String lsh = response.getLsh();
            int pos = lsh.indexOf("$");
            int kplsh;
            if (pos != -1) {
                kplsh = Integer.valueOf(lsh.substring(0, pos));
            } else {
                kplsh = Integer.valueOf(lsh);
            }
            Kpls kpls = kplsService.findOne(kplsh);
            kpls.setFpztdm("05");
            kpls.setErrorReason(response.getReturnMessage());
            kpls.setXgsj(new Date());
            kpls.setXgry(1);
            kplsService.save(kpls);
            Jyls jyls = jylsService.findOne(kpls.getDjh());
            jyls.setClztdm("92");
            jylsService.save(jyls);
            Map parms=new HashMap();
            parms.put("gsdm",kpls.getGsdm());
            Gsxx gsxx=gsxxService.findOneByParams(parms);
            //String url="https://vrapi.fvt.tujia.com/Invoice/CallBack";
            String url=gsxx.getCallbackurl();
            if(!("").equals(url)&&url!=null){
                String returnmessage=null;
                if(!kpls.getGsdm().equals("Family")&&!kpls.getGsdm().equals("fwk")) {
                    returnmessage = fphxUtil.CreateReturnMessage(kpls.getKplsh());
                    //输出调用结果
                    System.out.println("回写报文" + returnmessage);
                    if (returnmessage != null && !"".equals(returnmessage)) {
                        Map returnMap = fphxUtil.httpPost(returnmessage, kpls);
                        System.out.println("返回报文" + JSON.toJSONString(returnMap));
                    }
                }else if(kpls.getGsdm().equals("fwk")){
                    returnmessage = fphxUtil.CreateReturnMessage3(kpls.getKplsh());
                    System.out.println("回写报文" + returnmessage);
                    if (returnmessage != null && !"".equals(returnmessage)) {
                        String ss= HttpUtils.netWebService(url,"CallBack",returnmessage,gsxx.getAppKey(),gsxx.getSecretKey());
                    }
                }
            }
        }

    }
    /**
     * 解析电子发票返回的xml
     *
     * @param map
     * @param xml
     * @return
     */
    private boolean parseDzfpResultXml(Map<String, String> map, String xml) {
        // 创建SAXReader的对象sr
        SAXReader sr = new SAXReader();
        StringReader s = null;
        s = new StringReader(xml);
        InputSource is = new InputSource(s);
        Document doc = null;
        boolean flag = false;
        try {
            // 通过sr对象的read方法加载xml，获取document对象
            doc = sr.read(is);
            if (doc == null)
                return flag;
            // doc = DocumentHelper.parseText(xxml);
            // 通过doc对象获取根节点business
            Element business = doc.getRootElement();
            // System.out.println(business.getName());//根节点的name
            // 获取根节点的属性
            List<Attribute> bus = business.attributes();
            for (Attribute bu : bus) {
                map.put(bu.getName(), bu.getValue());
                // System.out.println(bu.getName()+bu.getValue());
            }
            // 通过business对象的elementIterator()方法获取迭代器
            Iterator it = business.elementIterator();
            // 遍历迭代器，获取根节点的信息
            while (it.hasNext()) {
                // 得到每一个body
                Element body = (Element) it.next();
                // 获取body的属性名与属性值
                List<Attribute> bodys = body.attributes();
                for (Attribute b : bodys) {
                    // System.out.println(body.getName());//根节点的子节点的name
                    map.put(b.getName(), b.getValue());
                    // System.out.println(b.getName()+b.getValue());
                }
                // 通过body对象的elementIterator()方法获取迭代器,获取body的节点名与节点值
                Iterator itt = body.elementIterator();
                while (itt.hasNext()) {
                    // 得到每一个body的节点
                    Element bos = (Element) itt.next();
                    map.put(bos.getName(), bos.getStringValue());
                    // System.out.println(bos.getName()+ bos.getStringValue());
                }
                flag = true;
            }
            s.close();
        } catch (Exception e) {
            System.out.println("解析税控盘返回报文失败");
            flag = false;
        }
        return flag;
    }
}
