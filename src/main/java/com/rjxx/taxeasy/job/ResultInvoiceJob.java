package com.rjxx.taxeasy.job;

import com.alibaba.fastjson.JSON;
import com.rjxx.taxeasy.bizcomm.utils.*;
import com.rjxx.taxeasy.config.RabbitmqUtils;
import com.rjxx.taxeasy.domains.Gsxx;
import com.rjxx.taxeasy.domains.Jyls;
import com.rjxx.taxeasy.domains.Kpls;
import com.rjxx.taxeasy.service.*;
import com.rjxx.taxeasy.vo.Kpspmxvo;
import com.rjxx.time.TimeUtil;
import com.rjxx.utils.DesUtils;
import com.rjxx.utils.StringUtils;
import com.rjxx.utils.XmlJaxbUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by xlm on 2018/1/31.
 */
@DisallowConcurrentExecution
public class ResultInvoiceJob  implements Job{

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
    private JylsService jylsService;

    @Autowired
    private KpspmxService kpspmxService;

    @Autowired
    private GeneratePdfService generatePdfService;

    @Autowired
    private GsxxService gsxxService;

    @Autowired
    private FpclService fpclService;

    private static Logger logger = LoggerFactory.getLogger(ResultInvoiceJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("获取发票数据任务执行开始,nextFireTime:{},"+context.getNextFireTime());
        try {
            do{
            String key="A!9fF&vH";
            byte[] content = (byte[]) rabbitmqUtils.receiveMsg("result", "invoice");
            String invoicedata=new String(content);
                if (StringUtils.isNotBlank(invoicedata)) {
                    invoicedata = DesUtils.DESDecrypt(invoicedata, key);
                    logger.info("--------数据------------"+invoicedata);
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
                               // fpclService.updateKpls(resultMap);
                                this.updateDpresult(kpls,resultMap,suc,invoicedata);
                            }
                        }
                    }else if(invoicedata.contains("Response")){
                        InvoiceResponse response = XmlJaxbUtils.convertXmlStrToObject(InvoiceResponse.class, invoicedata);
                        this.updateInvoiceResult(response);
                    }
                }
            }while (true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * 更新交易流水状态
     *
     * @param djh
     * @param clztdm
     */
    private void updateJyls(int djh, String clztdm) {
        Jyls jyls = jylsService.findOne(djh);
        if (jyls != null) {
            jyls.setClztdm(clztdm);
            jyls.setXgsj(new Date());
            jylsService.save(jyls);
        }
    }
    public void updateDpresult(Kpls kpls,Map resultMap,boolean suc,String content) throws Exception {
        if (!suc) {
            //解析xml异常
            kpls.setFpztdm("05");
            kpls.setErrorReason("返回的xml异常，无法解析");
            kpls.setXgsj(new Date());
            kplsService.save(kpls);
            updateJyls(kpls.getDjh(), "92");
            logger.error("dzfp return xml error!!!kplsh:" + kpls.getKplsh() + ",xml:" + content);
            Map parms=new HashMap();
            parms.put("gsdm",kpls.getGsdm());
            Gsxx gsxx=gsxxService.findOneByParams(parms);
            //String url="https://vrapi.fvt.tujia.com/Invoice/CallBack";
            String url=gsxx.getCallbackurl();
            if(!("").equals(url)&&url!=null){
                String returnmessage=null;
                if(!kpls.getGsdm().equals("Family")&&!kpls.getGsdm().equals("fwk")) {
                    returnmessage = generatePdfService.CreateReturnMessage(kpls.getKplsh());
                    //输出调用结果
                    logger.info("回写报文" + returnmessage);
                    if (returnmessage != null && !"".equals(returnmessage)) {
                        Map returnMap = generatePdfService.httpPost(returnmessage, kpls);
                        logger.info("返回报文" + JSON.toJSONString(returnMap));
                    }
                }else if(kpls.getGsdm().equals("fwk")){
                    returnmessage = generatePdfService.CreateReturnMessage3(kpls.getKplsh());
                    logger.info("回写报文" + returnmessage);
                    if (returnmessage != null && !"".equals(returnmessage)) {
                        String ss= HttpUtils.netWebService(url,"CallBack",returnmessage,gsxx.getAppKey(),gsxx.getSecretKey());
                        logger.info("返回报文" + ss);
                    }
                }
            }
            return;
        }
        String dzfpReturnCode = (String) resultMap.get("RETURNCODE");
        if (!"0000".equals(dzfpReturnCode)) {
            //返回结果不正确
            String dzfpReturnMsg = (String) resultMap.get("RETURNMSG");
            if ("-99 流水号重复".equals(dzfpReturnMsg)) {
                //返回重复结果
                if (StringUtils.isNotBlank(kpls.getFphm())) {
                    //状态正常，已经更新过了，不做处理了
                    logger.warn("kplsh:" + kpls.getKplsh() + " -99 流水号重复");
                    return;
                }
            }
            kpls.setFpztdm("05");
            if (StringUtils.isBlank(dzfpReturnMsg)) {
                kpls.setErrorReason("未知异常，开票软件没有返回结果，请去开票软件确认");
            } else {
                kpls.setErrorReason(dzfpReturnMsg);
            }
            kplsService.save(kpls);
            updateJyls(kpls.getDjh(), "92");
            logger.error("dzfp return xml error!!!kplsh:" + kpls.getKplsh() + ",xml:" + content);
            Map parms=new HashMap();
            parms.put("gsdm",kpls.getGsdm());
            Gsxx gsxx=gsxxService.findOneByParams(parms);
            //String url="https://vrapi.fvt.tujia.com/Invoice/CallBack";
            String url=gsxx.getCallbackurl();
            if(!("").equals(url)&&url!=null){
                String returnmessage=null;
                if(!kpls.getGsdm().equals("Family")&&!kpls.getGsdm().equals("fwk")) {
                    returnmessage = generatePdfService.CreateReturnMessage(kpls.getKplsh());
                    //输出调用结果
                    logger.info("回写报文" + returnmessage);
                    if (returnmessage != null && !"".equals(returnmessage)) {
                        Map returnMap = generatePdfService.httpPost(returnmessage, kpls);
                        logger.info("返回报文" + JSON.toJSONString(returnMap));
                    }
                }else if(kpls.getGsdm().equals("fwk")){
                    returnmessage = generatePdfService.CreateReturnMessage3(kpls.getKplsh());
                    logger.info("回写报文" + returnmessage);
                    if (returnmessage != null && !"".equals(returnmessage)) {
                        String ss= HttpUtils.netWebService(url,"CallBack",returnmessage,gsxx.getAppKey(),gsxx.getSecretKey());
                    }
                }
            }
            return;
        }
        //保存正常结果
        updateKpls(resultMap, kpls);
        String czlxdm = kpls.getFpczlxdm();
        if ("12".equals(czlxdm) || "13".equals(czlxdm)) {
            updateJyls(kpls.getDjh(), "91");
            if (kpls.getHzyfphm() != null && kpls.getHzyfpdm() != null) {
                kpls.setJylsh("");
                Kpls parms=new Kpls();
                parms.setFpdm(kpls.getHzyfpdm());
                parms.setFphm(kpls.getHzyfphm());
                Kpls ykpls = kplsService.findByfphm(parms);
                Map param2 = new HashMap<>();
                param2.put("kplsh", ykpls.getKplsh());
                // 全部红冲后修改
                //Kpspmxvo mxvo = kpspmxService.findKhcje(param2);
                //if (mxvo.getKhcje() == 0) {
                param2.put("fpztdm", "02");
                kplsService.updateFpczlx(param2);
                       /* } else {
                            param2.put("fpztdm", "01");
                            kplsService.updateFpczlx(param2);
                        }*/
            }
        } else {
            updateJyls(kpls.getDjh(), "21");
        }
        //此处开始生成pdf

        skService.ReCreatePdf(kpls.getKplsh());
        Map parms=new HashMap();
        parms.put("gsdm",kpls.getGsdm());
        Gsxx gsxx=gsxxService.findOneByParams(parms);
        //String url="https://vrapi.fvt.tujia.com/Invoice/CallBack";
        String url=gsxx.getCallbackurl();
        if(!("").equals(url)&&url!=null){
            String returnmessage=null;
            if(!kpls.getGsdm().equals("Family")&&!kpls.getGsdm().equals("fwk")) {
                returnmessage = generatePdfService.CreateReturnMessage(kpls.getKplsh());
                //输出调用结果
                logger.info("回写报文" + returnmessage);
                if (returnmessage != null && !"".equals(returnmessage)) {
                    Map returnMap = generatePdfService.httpPost(returnmessage, kpls);
                    logger.info("返回报文" + JSON.toJSONString(returnMap));
                }
            }else if(kpls.getGsdm().equals("fwk")){
                returnmessage = generatePdfService.CreateReturnMessage3(kpls.getKplsh());
                logger.info("回写报文" + returnmessage);
                if (returnmessage != null && !"".equals(returnmessage)) {
                    String ss= HttpUtils.netWebService(url,"CallBack",returnmessage,gsxx.getAppKey(),gsxx.getSecretKey());
                    String fwkReturnMessageStr=fwkReturnMessage(kpls);
                    logger.info("----------sap回写报文----------" + fwkReturnMessageStr);
                    String Data= HttpUtils.doPostSoap1_2(gsxx.getSapcallbackurl(), fwkReturnMessageStr, null,"Wendy","Welcome9");
                    logger.info("----------fwk平台回写返回报文--------" + ss);
                    logger.info("----------sap回写返回报文----------" + Data);

                }
            }
        }
    }
    public String   fwkReturnMessage(Kpls kpls) {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String result="Succeed";
        if(kpls.getFpczlxdm().equals("12")){
            result="CancelSucceed";
        }
        String ss="\n" +
                "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:glob=\"http://sap.com/xi/SAPGlobal20/Global\">\n" +
                "   <soap:Header/>\n" +
                "   <soap:Body>\n" +
                "      <glob:GoldenTaxGoldenTaxCreateRequest_sync>\n" +
                "         <BasicMessageHeader></BasicMessageHeader>\n" +
                "         <GoldenTax>\n" +
                "            <CutInvID>"+kpls.getJylsh()+"</CutInvID>\n" +
                "            <GoldenTaxID>"+kpls.getFphm()+"</GoldenTaxID>\n" +
                "            <GoldenTaxDate>\n" +
                "               <StartDateTime>"+sim.format(kpls.getKprq())+"</StartDateTime>\n" +
                "               <EndDateTime>"+sim.format(kpls.getKprq())+"</EndDateTime>\n" +
                "            </GoldenTaxDate>\n" +
                "            <GoldenTaxResult>"+result+"</GoldenTaxResult>\n" +
                "            <GoldenTaxCode>"+kpls.getFpdm()+"</GoldenTaxCode>\n" +
                "         </GoldenTax>\n" +
                "      </glob:GoldenTaxGoldenTaxCreateRequest_sync>\n" +
                "   </soap:Body>\n" +
                "</soap:Envelope>";
        return ss;
    }
    /**
     * 在t_kpls及t_kpspmx 表中增加记录
     *
     * @param map
     * @param kpls
     */
    private void updateKpls(Map<String, String> map, Kpls kpls) {
        // 保存已开发票结果主表
        try {
            String fpdm = map.get("FP_DM");
            String fphm = map.get("FP_HM");
            String czlx = kpls.getFpczlxdm();
            kpls.setFpdm(fpdm);
            kpls.setFphm(fphm);
            if ("13".equals(czlx)) {
                kpls.setHkbz("1");
            } else {
                kpls.setHkbz("0");
            }
            kpls.setFpEwm(map.get("EWM"));
            kpls.setSksbm(map.get("JQBH"));
            kpls.setMwq(map.get("FP_MW"));
            kpls.setJym(map.get("JYM"));
            String kprq = map.get("KPRQ");
            kpls.setKprq(TimeUtil.getSysDateInDate(kprq, null));
            String bz = map.get("BZ");
            if (StringUtils.isNotBlank(bz)) {
                kpls.setBz(bz);
            }
            kpls.setFpztdm("00");
            kpls.setErrorReason(null);
            kpls.setXgsj(new Date());
            kplsService.save(kpls);
        } catch (Exception e) {
            logger.error("", e);
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
                    returnmessage = generatePdfService.CreateReturnMessage(kpls.getKplsh());
                    //输出调用结果
                    logger.info("回写报文" + returnmessage);
                    if (returnmessage != null && !"".equals(returnmessage)) {
                        Map returnMap = generatePdfService.httpPost(returnmessage, kpls);
                        logger.info("返回报文" + JSON.toJSONString(returnMap));
                    }
                }else if(kpls.getGsdm().equals("fwk")){
                    returnmessage = generatePdfService.CreateReturnMessage3(kpls.getKplsh());
                    logger.info("回写报文" + returnmessage);
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
                    returnmessage = generatePdfService.CreateReturnMessage(kpls.getKplsh());
                    //输出调用结果
                    logger.info("回写报文" + returnmessage);
                    if (returnmessage != null && !"".equals(returnmessage)) {
                        Map returnMap = generatePdfService.httpPost(returnmessage, kpls);
                        logger.info("返回报文" + JSON.toJSONString(returnMap));
                    }
                }else if(kpls.getGsdm().equals("fwk")){
                    returnmessage = generatePdfService.CreateReturnMessage3(kpls.getKplsh());
                    logger.info("回写报文" + returnmessage);
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
            logger.error("解析税控盘返回报文失败", e);
            flag = false;
        }
        return flag;
    }
    public static void main(String[] args) {
        String ss="<?xml version=\"1.0\" encoding=\"gbk\"?><business id=\"FPKJ\" comment=\"发票开具\"><RESPONSE_COMMON_FPKJ class=\"RESPONSE_COMMON_FPKJ\"><FPQQLSH>745</FPQQLSH><FP_DM>031001600411</FP_DM><FP_HM>19358792</FP_HM><KPRQ>20171123093654</KPRQ><JQBH>661502064555</JQBH><FP_MW>4+-&gt;6-*&lt;+51-+572+71&gt;5&gt;&gt;59/7/-*&lt;/4+*-72+/&lt;18086-76++6&lt;6/++72+045&gt;034-06+/31870&gt;5795953*93&lt;/4+*-72+/&lt;18086/&lt;8&lt;</FP_MW><JYM>65402984660530328499</JYM><EWM/><BZ></BZ><RETURNCODE>0000</RETURNCODE><RETURNMSG>4011-开票成功 [0000,]</RETURNMSG></RESPONSE_COMMON_FPKJ></business>";
        Map<String, String> resultMap = new HashMap<>();
        ResultInvoiceJob resultInvoiceJob=new ResultInvoiceJob();
        boolean suc = resultInvoiceJob.parseDzfpResultXml(resultMap, ss);
        System.out.println(JSON.toJSONString(resultMap));
    }
}
