package com.rjxx.taxeasy.job;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rjxx.taxeasy.bizcomm.utils.GetXmlUtil;
import com.rjxx.taxeasy.bizcomm.utils.HttpUtils;
import com.rjxx.taxeasy.bizcomm.utils.XmlMapUtils;
import com.rjxx.taxeasy.domains.*;
import com.rjxx.taxeasy.service.*;
import com.rjxx.taxeasy.vo.Spvo;
import com.rjxx.utils.XmlJaxbUtils;
import com.rjxx.utils.XmltoJson;
import org.apache.axiom.om.OMElement;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by xlm on 2017/8/2.
 */
@Service
public class FwkGetDataJob implements Job {

    private static Logger logger = LoggerFactory.getLogger(FwkGetDataJob.class);

    @Autowired
    private SkpService skpService;
    @Autowired
    private XfService xfService;
    @Autowired
    private SpvoService spvoService;
    @Autowired
    private KplsService kplsService;
    @Autowired
    private GsxxService gsxxService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("获取福维克开票数据任务执行开始,nextFireTime:{},"+context.getNextFireTime());
        String invoiceBack="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:glob=\"http://sap.com/xi/SAPGlobal20/Global\" xmlns:yni=\"http://0001092235-one-off.sap.com/YNIIVJHSY_\">\n" +
                "<soapenv:Header/>\n" +
                    "<soapenv:Body>\n" +
                        "<glob:CustomerInvoiceByElementsQuery_sync>\n" +
                            "<CustomerInvoiceSelectionByElements>\n" +
                                "<SelectionByDate>\n" +
                                    "<InclusionExclusionCode>I</InclusionExclusionCode>\n" +
                                    "<IntervalBoundaryTypeCode>1</IntervalBoundaryTypeCode>\n" +
                                    "<LowerBoundaryCustomerInvoiceDate>2017-11-01</LowerBoundaryCustomerInvoiceDate>\n" +
                                "</SelectionByDate>\n" +
                            "</CustomerInvoiceSelectionByElements>\n" +
                            "<ProcessingConditions>\n" +
                                    "<QueryHitsMaximumNumberValue>10</QueryHitsMaximumNumberValue>\n" +
                                    "<QueryHitsUnlimitedIndicator>false</QueryHitsUnlimitedIndicator>\n" +
                            "</ProcessingConditions>\n" +
                        "</glob:CustomerInvoiceByElementsQuery_sync>\n" +
                    "</soapenv:Body>\n" +
                "</soapenv:Envelope>";
        String Data= HttpUtils.doPostSoap1_2("https://my337109.sapbydesign.com/sap/bc/srt/scs/sap/querycustomerinvoicein?sap-vhost=my337109.sapbydesign.com", invoiceBack, null,"_GoldenTax","Welcome9");
        Map jyxxMap=interping(Data);


    }

    /**
     * 解析报文
     * @param data
     * @return
     */
    public Map interping(String data) {


        String jsonString= XmltoJson.xml2json(data);
        logger.info("---json---"+jsonString);
        Map dataMap=XmltoJson.strJson2Map(jsonString);
        Map Envelope=(Map)dataMap.get("soap-env:Envelope");
        Map Body=(Map)Envelope.get("soap-env:Body");
        Map CustomerInvoiceByElementsResponse_sync=(Map)Body.get("n0:CustomerInvoiceByElementsResponse_sync");
        List<Map> CustomerInvoice=new ArrayList<>();
        if(CustomerInvoiceByElementsResponse_sync.get("CustomerInvoice") instanceof List){
            CustomerInvoice=(List)CustomerInvoiceByElementsResponse_sync.get("CustomerInvoice");
        }else if(CustomerInvoiceByElementsResponse_sync.get("CustomerInvoice") instanceof Map){
            Map CustomerInvoiceMap = (Map)CustomerInvoiceByElementsResponse_sync.get("CustomerInvoice");
            CustomerInvoice.add(CustomerInvoiceMap);
        }
        for(int i = 0; i < CustomerInvoice.size(); i++) {
            Map CustomerInvoiceMap = CustomerInvoice.get(i);

            String InvoiceTypeCI = null;/**订单发票种类(销售单/服务订单扩展字段)**/
            if(CustomerInvoiceMap.get("n1:InvoiceTypeCI")!=null) {
                Map InvoiceTypeCIMap = (Map) CustomerInvoiceMap.get("n1:InvoiceTypeCI");
                if (null != InvoiceTypeCIMap.get("$") && !InvoiceTypeCIMap.get("$").equals("")) {
                    InvoiceTypeCI = (String) InvoiceTypeCIMap.get("$");
                }
            }
            Map ProcessingTypeNameMap = (Map) CustomerInvoiceMap.get("ProcessingTypeName");
            String processingTypeName = null;/**发票类型**/
            if (null != ProcessingTypeNameMap.get("$") && !ProcessingTypeNameMap.get("$").equals("")) {
                processingTypeName = (String)ProcessingTypeNameMap.get("$");//发票类型
            }
            String OriginalSerialNumber = null;/**OriginalSerialNumber交易流水号(贷记凭证：原发票InvoiceID+SapOrderID)**/
            if(CustomerInvoiceMap.get("n1:OriginalSerialNumber")!=null) {
                Map OriginalSerialNumberMap = (Map) CustomerInvoiceMap.get("n1:OriginalSerialNumber");
                if (null != OriginalSerialNumberMap.get("$") && !OriginalSerialNumberMap.get("$").equals("")) {
                    OriginalSerialNumber = OriginalSerialNumberMap.get("$").toString();//SerialNumber交易流水号(发票：InvoiceID+SapOrderID，贷记凭证：原发票InvoiceID+SapOrderID)
                }
            }
            String InvoiceID = null;/**sap发票ID**/
            if (null != CustomerInvoiceMap.get("ID") && !CustomerInvoiceMap.get("ID").equals("")) {
                InvoiceID = CustomerInvoiceMap.get("ID").toString();//sap发票ID
            }
           /* String SerialNo = null;*//**SerialNumber交易流水号(发票：InvoiceID+SapOrderID**//*
            if(CustomerInvoiceMap.get("n1:SerialNo")!=null) {
                Map SerialNoMap = (Map) CustomerInvoiceMap.get("n1:SerialNo");
                if (null != SerialNoMap.get("$") && !SerialNoMap.get("$").equals("")) {
                    SerialNo = SerialNoMap.get("$").toString();
                }
            }*/
            if(processingTypeName.equals("贷记凭证")){
                    Map paramsMap=new HashMap();
                    paramsMap.put("jylsh",OriginalSerialNumber);
                    paramsMap.put("gsdm","fwk");
                    Kpls kpls=kplsService.findOneByParams(paramsMap);
                    HcData hcData=new HcData();
                    hcData.setClientNO(kpls.getKpddm());
                    hcData.setSerialNumber(InvoiceID);
                    hcData.setInvType("12");
                    hcData.setServiceType("1");
                    hcData.setChargeTaxWay("0");
                    hcData.setTotalAmount("-"+(kpls.getJshj()).toString());
                    hcData.setCNNoticeNo("");
                    hcData.setCNDNCode(kpls.getFpdm());
                    hcData.setCNDNNo(kpls.getFphm());
                    String xml= XmlJaxbUtils.toXml(hcData);
                    Map parms=new HashMap();
                    parms.put("gsdm","fwk");
                    Gsxx gsxx=gsxxService.findOneByParams(parms);
                    String ss=HttpUtils.HttpUrlWebService(xml,gsxx.getAppKey(),gsxx.getSecretKey(),"04");
            }else if (!InvoiceTypeCI.equals("专票")) {

                String CISalesPlatform = null;/**销售平台**/
                if(CustomerInvoiceMap.get("n1:CISalesPlatform")!=null) {
                    Map CISalesPlatformMap = (Map) CustomerInvoiceMap.get("n1:CISalesPlatform");
                    if (null != CISalesPlatformMap.get("$") && !CISalesPlatformMap.get("$").equals("")) {
                        CISalesPlatform = (String) CISalesPlatformMap.get("$");
                    }
                }
                String CISalesOrderType = null;/**销售订单类型**/
                if(CustomerInvoiceMap.get("n1:CISalesOrderType")!=null) {
                    Map CISalesOrderTypeMap = (Map) CustomerInvoiceMap.get("n1:CISalesOrderType");
                    if (null != CISalesOrderTypeMap.get("$") && !CISalesOrderTypeMap.get("$").equals("")) {
                        CISalesOrderType = (String) CISalesOrderTypeMap.get("$");
                    }
                }
                String InvoiceCustomerNameCI = null;/**sap发票客户名称对应平台购方名称**/
                if(CustomerInvoiceMap.get("n1:InvoiceCustomerNameCI")!=null) {
                    Map InvoiceCustomerNameCIMap = (Map) CustomerInvoiceMap.get("n1:InvoiceCustomerNameCI");
                    if (null != InvoiceCustomerNameCIMap.get("$") && !InvoiceCustomerNameCIMap.get("$").equals("")) {
                        InvoiceCustomerNameCI = (String) InvoiceCustomerNameCIMap.get("$");
                    }
                }
                if(null==InvoiceCustomerNameCI||"".equals(InvoiceCustomerNameCI)){
                    InvoiceCustomerNameCI="个人";
                }
                String InvoiceCustomerTaxNumberCI = null;/**sap发票客户税号对应平台购方税号**/
                if(CustomerInvoiceMap.get("n1:InvoiceCustomerTaxNumberCI")!=null) {
                    Map InvoiceCustomerTaxNumberCIMap = (Map) CustomerInvoiceMap.get("n1:InvoiceCustomerTaxNumberCI");
                    if (null != InvoiceCustomerTaxNumberCIMap.get("$") && !InvoiceCustomerTaxNumberCIMap.get("$").equals("")) {
                        InvoiceCustomerTaxNumberCI = InvoiceCustomerTaxNumberCIMap.get("$").toString();
                    }
                }
                String InvoiceCustomerAddressTelCI = null;/**sap 发票客户地址电话 对应平台购方地址电话**/
                if(CustomerInvoiceMap.get("n1:InvoiceCustomerAddressTelCI")!=null) {
                    Map InvoiceCustomerAddressTelCIMap = (Map) CustomerInvoiceMap.get("n1:InvoiceCustomerAddressTelCI");
                    if (null != InvoiceCustomerAddressTelCIMap.get("$") && !InvoiceCustomerAddressTelCIMap.get("$").equals("")) {
                        InvoiceCustomerAddressTelCI = InvoiceCustomerAddressTelCIMap.get("$").toString();
                    }
                }
                String CustomerBankAccountCI = null;/**sap 客户银行及账号对应平台购方银行账号**/
                if(CustomerInvoiceMap.get("n1:CustomerBankAccountCI")!=null) {
                    Map CustomerBankAccountCIMap = (Map) CustomerInvoiceMap.get("n1:CustomerBankAccountCI");
                    if (null != CustomerBankAccountCIMap.get("$") && !CustomerBankAccountCIMap.get("$").equals("")) {
                        CustomerBankAccountCI = CustomerBankAccountCIMap.get("$").toString();
                    }
                }
                String ProductRecipientEMail = null;/**邮箱**/
                if(CustomerInvoiceMap.get("n1:ProductRecipientEMail")!=null) {
                    Map ProductRecipientEMailMap = (Map) CustomerInvoiceMap.get("n1:ProductRecipientEMail");
                    if (null != ProductRecipientEMailMap.get("$") && !ProductRecipientEMailMap.get("$").equals("")) {
                        ProductRecipientEMail = ProductRecipientEMailMap.get("$").toString();
                    }
                }
                String ProductRecipientMobileNumber = null;/**手机号**/
                if(CustomerInvoiceMap.get("n1:ProductRecipientMobileNumber")!=null) {
                    Map ProductRecipientMobileNumberMap = (Map) CustomerInvoiceMap.get("n1:ProductRecipientMobileNumber");
                    if (null != ProductRecipientMobileNumberMap.get("$") && !ProductRecipientMobileNumberMap.get("$").equals("")) {
                        ProductRecipientMobileNumber = ProductRecipientMobileNumberMap.get("$").toString();
                    }
                }
              /*  String DrawerCI = null;*//**sap 开票人**//*
                if (null != CustomerInvoiceMap.get("DrawerCI") && !CustomerInvoiceMap.get("DrawerCI").equals("")) {
                    DrawerCI = CustomerInvoiceMap.get("DrawerCI").toString();
                }
                String BillingContentCI = null;*//**sap 开票内容**//*
                if (null != CustomerInvoiceMap.get("BillingContentCI") && !CustomerInvoiceMap.get("BillingContentCI").equals("")) {
                    BillingContentCI = CustomerInvoiceMap.get("BillingContentCI").toString();
                }
                String BillingAddressCI = null;*//**sap 送票地址**//*
                if (null != CustomerInvoiceMap.get("BillingAddressCI") && !CustomerInvoiceMap.get("BillingAddressCI").equals("")) {
                    BillingAddressCI = CustomerInvoiceMap.get("BillingAddressCI").toString();
                }*/

                Map OrderDateMap = (Map) CustomerInvoiceMap.get("n1:OrderDate");
                String OrderDate = null;/**sap 订单时间**/
                if (null != OrderDateMap.get("$") && !OrderDateMap.get("$").equals("")) {
                    OrderDate = (String)OrderDateMap.get("$");
                }
                String PartyID = null;/**sap销方编号，对应平台开票点代码**/
                Map SellerPartyMap = (Map) CustomerInvoiceMap.get("SellerParty");
                if (null != SellerPartyMap.get("PartyID") && !SellerPartyMap.get("PartyID").equals("")) {
                    PartyID = (String)SellerPartyMap.get("PartyID");
                }
                Map PriceAndTaxMap = (Map) CustomerInvoiceMap.get("PriceAndTax");

                String NetAmount = null;/**sap整张发票净价 即不含税金额**/
                Map NetAmountMap = (Map) PriceAndTaxMap.get("NetAmount");
                if (null != NetAmountMap.get("$") && !NetAmountMap.get("$").equals("")) {
                    NetAmount = NetAmountMap.get("$").toString();
                }
                String TaxAmount = null;/**sap 整张发票税额  即税额**/
                Map TaxAmountMap = (Map) PriceAndTaxMap.get("TaxAmount");
                if (null != TaxAmountMap.get("$") && !TaxAmountMap.get("$").equals("")) {
                    TaxAmount = TaxAmountMap.get("$").toString();
                }
                String GrossAmount = null;/**sap 整张发票价税合计 即价税合计**/
                Map GrossAmountMap = (Map) PriceAndTaxMap.get("GrossAmount");
                if (null != GrossAmountMap.get("$") && !GrossAmountMap.get("$").equals("")) {
                    GrossAmount = GrossAmountMap.get("$").toString();
                }
                Jyxxsq jyxxsq = new Jyxxsq();
                jyxxsq.setKpddm(PartyID);//开票点代码
                jyxxsq.setJylsh(InvoiceID);//交易流水号
                jyxxsq.setFpzldm("12");//发票种类
                Map params = new HashMap();
                params.put("gsdm", "fwk");
                params.put("kpddm", PartyID);
                Skp skp = skpService.findOneByParams(params);
                Xf xf = xfService.findOne(skp.getXfid());
                jyxxsq.setKpr(xf.getKpr());//开票人
                jyxxsq.setSkr(xf.getSkr());//收款人
                jyxxsq.setFhr(xf.getFhr());//复核人
                jyxxsq.setSjly("1");//数据来源
                jyxxsq.setXfsh(xf.getXfsh());//销方税号
                jyxxsq.setXfmc(xf.getXfmc());//销方名称
                jyxxsq.setXfdz(xf.getXfdz());//销方地址
                jyxxsq.setXfdh(xf.getXfdh());//销方电话
                jyxxsq.setXfyh(xf.getXfyh());//销方银行
                jyxxsq.setXfyhzh(xf.getXfyhzh());//销方银行账号
                jyxxsq.setGfmc(InvoiceCustomerNameCI);//购方名称
                //jyxxsq.setGfsh(InvoiceCustomerTaxNumberCI);
                jyxxsq.setGfdz(InvoiceCustomerAddressTelCI);
                jyxxsq.setGfyh(CustomerBankAccountCI);
                SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    jyxxsq.setDdrq(OrderDate == null ? new Date() : sim.parse(OrderDate));//订单时间
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                jyxxsq.setSfdyqd("0");//是否打印清单
                jyxxsq.setSfcp("1");//是否拆票
                jyxxsq.setSfdy("0");//是否打印
                jyxxsq.setZsfs("0");//征收方式
                jyxxsq.setJshj(Double.valueOf(GrossAmount));//价税合计
                jyxxsq.setHsbz("1");
                jyxxsq.setTqm(InvoiceID);
                // jyxxsq.setBz();
                String ddh = "";//订单号
                String bz = "";//备注
                String gfsjh=ProductRecipientMobileNumber;//购方手机号
                String DistributionChannelCode = null;/**分销渠道代码**/
                List<Jymxsq> jymxsqList = new ArrayList<>();
                if (CustomerInvoiceMap.get("Item") instanceof Map) {

                    Map ItemMap = (Map) CustomerInvoiceMap.get("Item");

                    String ID = null;/**sap 产品编号*/
                    if (null != ItemMap.get("ID") && !ItemMap.get("ID").equals("")) {
                        ID = ItemMap.get("ID").toString();
                    }
                    String Description = null;/**产品名称 即商品名称**/
                    Map DescriptionMap = (Map) ItemMap.get("Description");
                    if (null != DescriptionMap.get("$") && !DescriptionMap.get("$").equals("")) {
                        Description = DescriptionMap.get("$").toString();
                    }
                    String Pitemtype = null;/**sap 产品自行编码*/
                    Map PitemtypeMap = (Map) ItemMap.get("n1:PItemType");
                    if (null != PitemtypeMap.get("$") && !PitemtypeMap.get("$").equals("")) {
                        Pitemtype = PitemtypeMap.get("$").toString();
                    }
                    String UnitPrice = null;/**sap 单价*/
                    Map UnitPriceMap = (Map) ItemMap.get("n1:UnitPrice");
                    if (null != UnitPriceMap.get("$") && !UnitPriceMap.get("$").equals("")) {
                        UnitPrice = UnitPriceMap.get("$").toString();
                    }
                    String Quantity = null;/** sap 产品数量  即商品数量 **/
                    Map QuantityMap = (Map) ItemMap.get("Quantity");
                    Map QuantityslMap = (Map) QuantityMap.get("Quantity");
                    if (null != QuantityslMap.get("$") && !QuantityslMap.get("$").equals("")) {
                        Quantity = QuantityslMap.get("$").toString();
                    }
                    String MeasureUnitName = null;/**sap 商品单位  **/
                    Map MeasureUnitNameMap = (Map) QuantityMap.get("MeasureUnitName");
                    if (null != MeasureUnitNameMap.get("$") && !MeasureUnitNameMap.get("$").equals("")) {
                        MeasureUnitName = MeasureUnitNameMap.get("$").toString();
                    }

                    String SalesOrderReferenceID = null;/**sap销售订单号**/
                    Map SalesOrderReferenceMap = (Map) ItemMap.get("SalesOrderReference");
                    if (null != SalesOrderReferenceMap.get("ID") && !SalesOrderReferenceMap.get("ID").equals("")) {
                        SalesOrderReferenceID = SalesOrderReferenceMap.get("ID").toString();
                        ddh = SalesOrderReferenceID;
                    }
                    Map DistributionChannelCodeMap = (Map) ItemMap.get("SalesAndServiceBusinessArea");
                    if (null != DistributionChannelCodeMap.get("DistributionChannelCode") && !DistributionChannelCodeMap.get("DistributionChannelCode").equals("")) {
                        DistributionChannelCode = DistributionChannelCodeMap.get("DistributionChannelCode").toString();
                    }
                    /*String MobilePhoneFormattedNumberDescription = null;*//**sap收货人手机  对应平台购方手机号**//*
                    Map ProductRecipientPartyMap = (Map) ItemMap.get("ProductRecipientParty");
                    Map AddressMap = (Map) ProductRecipientPartyMap.get("Address");
                    if (null != AddressMap.get("MobilePhoneFormattedNumberDescription") && !AddressMap.get("MobilePhoneFormattedNumberDescription").equals("")) {
                        MobilePhoneFormattedNumberDescription = AddressMap.get("MobilePhoneFormattedNumberDescription").toString();
                        gfsjh = MobilePhoneFormattedNumberDescription;
                    }*/
                    String PurchaseOrderReferenceID = null;/** sap 前台订单号/外部参考号**/
                    Map PurchaseOrderReferenceMap = (Map) ItemMap.get("PurchaseOrderReference");
                    if (null != PurchaseOrderReferenceMap.get("ID") && !PurchaseOrderReferenceMap.get("ID").equals("")) {
                        PurchaseOrderReferenceID = PurchaseOrderReferenceMap.get("ID").toString();
                        bz = PurchaseOrderReferenceID;
                    }
                    Map ItemPriceAndTaxMap = (Map) ItemMap.get("PriceAndTax");

                    String ItemNetAmount = null;/**sap行发票净价 即不含税金额**/
                    Map ItemNetAmountMap = (Map) ItemPriceAndTaxMap.get("NetAmount");
                    if (null != ItemNetAmountMap.get("$") && !ItemNetAmountMap.get("$").equals("")) {
                        ItemNetAmount = ItemNetAmountMap.get("$").toString();
                    }
                    String ItemTaxAmount = null;/**sap 行整张发票税额  即税额**/
                    Map ItemTaxAmountMap = (Map) ItemPriceAndTaxMap.get("TaxAmount");
                    if (null != ItemTaxAmountMap.get("$") && !ItemTaxAmountMap.get("$").equals("")) {
                        ItemTaxAmount = ItemTaxAmountMap.get("$").toString();
                    }
                    String ItemGrossAmount = null;/**sap 行整张发票价税合计 即价税合计**/
                    Map ItemGrossAmountMap = (Map) ItemPriceAndTaxMap.get("GrossAmount");
                    if (null != ItemGrossAmountMap.get("$") && !ItemGrossAmountMap.get("$").equals("")) {
                        ItemGrossAmount = ItemGrossAmountMap.get("$").toString();
                    }
                    Jymxsq jymxsq = new Jymxsq();
                    Map spMap=new HashMap();
                    spMap.put("gsdm","fwk");
                    spMap.put("spdm",Pitemtype);
                    Spvo spvo=spvoService.findOneSpvo(spMap);
                    jymxsq.setSpdm(spvo.getSpbm());
                    jymxsq.setSpmc(spvo.getSpmc());
                    jymxsq.setSpggxh(Description);
                    jymxsq.setFphxz("0");
                    jymxsq.setSps(Double.valueOf(Quantity));
                    jymxsq.setSpdj(Double.valueOf(UnitPrice));
                    jymxsq.setSpdw(MeasureUnitName);
                    jymxsq.setSpje(Double.valueOf(ItemGrossAmount));
                    jymxsq.setSpse(Double.valueOf(ItemTaxAmount));
                    jymxsq.setJshj(Double.valueOf(ItemGrossAmount));
                    jymxsq.setSpsl(spvo.getSl());
                    jymxsq.setYhzcmc(spvo.getYhzcmc());
                    jymxsq.setLslbz(spvo.getLslbz());
                    jymxsq.setYhzcbs(spvo.getYhzcbs());
                    jymxsqList.add(jymxsq);
                } else if (CustomerInvoiceMap.get("Item") instanceof List) {
                    List<Map> ItemList = (List) CustomerInvoiceMap.get("Item");
                    for (int j = 0; j < ItemList.size(); j++) {
                        Map ItemMap = (Map) ItemList.get(j);
                        String ID = null;/**sap 产品编号*/
                        if (null != ItemMap.get("ID") && !ItemMap.get("ID").equals("")) {
                            ID = ItemMap.get("ID").toString();
                        }
                        String Description = null;/**产品名称 即商品名称**/
                        Map DescriptionMap = (Map) ItemMap.get("Description");
                        if (null != DescriptionMap.get("$") && !DescriptionMap.get("$").equals("")) {
                            Description = DescriptionMap.get("$").toString();
                        }
                        String Pitemtype = null;/**sap 产品自行编码*/
                        Map PitemtypeMap = (Map) ItemMap.get("n1:PItemType");
                        if (null != PitemtypeMap.get("$") && !PitemtypeMap.get("$").equals("")) {
                            Pitemtype = PitemtypeMap.get("$").toString();
                        }
                        String Quantity = null;/** sap 产品数量  即商品数量 **/
                        Map QuantityMap = (Map) ItemMap.get("Quantity");
                        Map QuantityslMap = (Map) QuantityMap.get("Quantity");
                        if (null != QuantityslMap.get("$") && !QuantityslMap.get("$").equals("")) {
                            Quantity = QuantityslMap.get("$").toString();
                        }
                        String MeasureUnitName = null;/**sap 商品单位  **/
                        Map MeasureUnitNameMap = (Map) QuantityMap.get("MeasureUnitName");
                        if (null != MeasureUnitNameMap.get("$") && !MeasureUnitNameMap.get("$").equals("")) {
                            MeasureUnitName =MeasureUnitNameMap.get("$").toString();
                        }
                        String UnitPrice = null;/**sap 单价*/
                        Map UnitPriceMap = (Map) ItemMap.get("n1:UnitPrice");
                        if (null != UnitPriceMap.get("$") && !UnitPriceMap.get("$").equals("")) {
                            UnitPrice = UnitPriceMap.get("$").toString();
                        }
                        String SalesOrderReferenceID = null;/**sap销售订单号**/
                        Map SalesOrderReferenceMap = (Map) ItemMap.get("SalesOrderReference");
                        if (null != SalesOrderReferenceMap.get("ID") && !SalesOrderReferenceMap.get("ID").equals("")) {
                            SalesOrderReferenceID = SalesOrderReferenceMap.get("ID").toString();
                        }
                        Map DistributionChannelCodeMap = (Map) ItemMap.get("SalesAndServiceBusinessArea");
                        if (null != DistributionChannelCodeMap.get("DistributionChannelCode") && !DistributionChannelCodeMap.get("DistributionChannelCode").equals("")) {
                            DistributionChannelCode =DistributionChannelCodeMap.get("DistributionChannelCode").toString();
                        }
                       /* String MobilePhoneFormattedNumberDescription = null;*//**sap收货人手机  对应平台购方手机号**//*
                        Map ProductRecipientPartyMap = (Map) ItemMap.get("ProductRecipientParty");
                        Map AddressMap = (Map) ProductRecipientPartyMap.get("Address");
                        if (null != AddressMap.get("MobilePhoneFormattedNumberDescription") && !AddressMap.get("MobilePhoneFormattedNumberDescription").equals("")) {
                            MobilePhoneFormattedNumberDescription = AddressMap.get("MobilePhoneFormattedNumberDescription").toString();
                            gfsjh = MobilePhoneFormattedNumberDescription;
                        }*/
                        String PurchaseOrderReferenceID = null;/** sap 前台订单号/外部参考号**/
                        Map PurchaseOrderReferenceMap = (Map) ItemMap.get("PurchaseOrderReference");
                        if (null != PurchaseOrderReferenceMap.get("ID") && !PurchaseOrderReferenceMap.get("ID").equals("")) {
                            PurchaseOrderReferenceID = PurchaseOrderReferenceMap.get("ID").toString();
                            bz = PurchaseOrderReferenceID;
                        }
                        Map ItemPriceAndTaxMap = (Map) ItemMap.get("PriceAndTax");

                        String ItemNetAmount = null;/**sap行发票净价 即不含税金额**/
                        Map ItemNetAmountMap = (Map) ItemPriceAndTaxMap.get("NetAmount");
                        if (null != ItemNetAmountMap.get("$") && !ItemNetAmountMap.get("$").equals("")) {
                            ItemNetAmount = ItemNetAmountMap.get("$").toString();
                        }
                        String ItemTaxAmount = null;/**sap 行发票税额  即税额**/
                        Map ItemTaxAmountMap = (Map) ItemPriceAndTaxMap.get("TaxAmount");
                        if (null != ItemTaxAmountMap.get("$") && !ItemTaxAmountMap.get("$").equals("")) {
                            ItemTaxAmount = ItemTaxAmountMap.get("$").toString();
                        }
                        String ItemGrossAmount = null;/**sap 行发票价税合计 即价税合计**/
                        Map ItemGrossAmountMap = (Map) ItemPriceAndTaxMap.get("GrossAmount");
                        if (null != ItemGrossAmountMap.get("$") && !ItemGrossAmountMap.get("$").equals("")) {
                            ItemGrossAmount = ItemGrossAmountMap.get("$").toString();
                        }
                        Jymxsq jymxsq = new Jymxsq();
                        Map spMap=new HashMap();
                        spMap.put("gsdm","fwk");
                        spMap.put("spdm",Pitemtype);
                        Spvo spvo=spvoService.findOneSpvo(spMap);
                        jymxsq.setSpdm(spvo.getSpbm());
                        jymxsq.setSpmc(spvo.getSpmc());
                        jymxsq.setSpggxh(Description);
                        jymxsq.setFphxz("0");
                        jymxsq.setSps(Double.valueOf(Quantity));
                        jymxsq.setSpdj(Double.valueOf(UnitPrice));
                        jymxsq.setSpdw(MeasureUnitName);
                        jymxsq.setSpje(Double.valueOf(ItemGrossAmount));
                        jymxsq.setSpse(Double.valueOf(ItemTaxAmount));
                        jymxsq.setJshj(Double.valueOf(ItemGrossAmount));
                        jymxsq.setSpsl(spvo.getSl());
                        jymxsq.setYhzcmc(spvo.getYhzcmc());
                        jymxsq.setLslbz(spvo.getLslbz());
                        jymxsq.setYhzcbs(spvo.getYhzcbs());
                        jymxsqList.add(jymxsq);
                    }
                }
                jyxxsq.setBz(bz);
                jyxxsq.setDdh(ddh);
                if ((CISalesPlatform.equals("天猫") && DistributionChannelCode.equals("电商")) || (CISalesPlatform.equals("京东") && DistributionChannelCode.equals("电商"))) {
                    jyxxsq.setGfsjh(gfsjh);
                } else if (DistributionChannelCode.equals("SA")) {
                    if (CISalesPlatform.equals("KA客户") || CISalesPlatform.equals("Staff Sales") || CISalesPlatform.equals("Demo Sales") || CISalesPlatform.equals("SA个人")) {
                        jyxxsq.setGfsjh(gfsjh);
                    }
                } else if (DistributionChannelCode.equals("服务维修")) {
                    jyxxsq.setGfsjh(gfsjh);
                }
                List<Jyzfmx> jyzfmxList=new ArrayList<>();
                String xml= GetXmlUtil.getFpkjXml(jyxxsq,jymxsqList,jyzfmxList);
                Map parms=new HashMap();
                parms.put("gsdm","fwk");
                Gsxx gsxx=gsxxService.findOneByParams(parms);
                String resultxml=HttpUtils.HttpUrlPost(xml,gsxx.getAppKey(),gsxx.getSecretKey());
            }
        }
        return null;
    }

    public static void main(String[] args) {
        String invoiceBack="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:glob=\"http://sap.com/xi/SAPGlobal20/Global\" xmlns:yni=\"http://0001092235-one-off.sap.com/YNIIVJHSY_\">\n" +
                    "<soapenv:Header/>\n" +
                    "<soapenv:Body>\n" +
                        "<glob:CustomerInvoiceByElementsQuery_sync>\n" +
                            "<CustomerInvoiceSelectionByElements>\n" +
                            "<SelectionByDate>\n" +
                                "<InclusionExclusionCode>I</InclusionExclusionCode>\n" +
                                "<IntervalBoundaryTypeCode>1</IntervalBoundaryTypeCode>\n" +
                                "<LowerBoundaryCustomerInvoiceDate>2017-11-13</LowerBoundaryCustomerInvoiceDate>\n" +
                            "</SelectionByDate>\n" +
                            "</CustomerInvoiceSelectionByElements>\n" +
                            "<ProcessingConditions>\n" +
                                "<QueryHitsMaximumNumberValue>10</QueryHitsMaximumNumberValue>\n" +
                                "<QueryHitsUnlimitedIndicator>false</QueryHitsUnlimitedIndicator>\n" +
                            "</ProcessingConditions>\n" +
                        "</glob:CustomerInvoiceByElementsQuery_sync>\n" +
                    "</soapenv:Body>\n" +
                "</soapenv:Envelope>\n";
        String Data= HttpUtils.doPostSoap1_1("https://my337109.sapbydesign.com/sap/bc/srt/scs/sap/querycustomerinvoicein?sap-vhost=my337109.sapbydesign.com", invoiceBack, null,"_GoldenTax","Welcome9");
        FwkGetDataJob fwkGetDataJob=new FwkGetDataJob();
        Map resultMap=fwkGetDataJob.interping(Data);
    }
}
