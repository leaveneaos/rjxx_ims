package com.rjxx.taxeasy.job;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rjxx.taxeasy.bizcomm.utils.HttpUtils;
import com.rjxx.taxeasy.bizcomm.utils.XmlMapUtils;
import com.rjxx.taxeasy.domains.Jymxsq;
import com.rjxx.taxeasy.domains.Jyxxsq;
import com.rjxx.taxeasy.domains.Skp;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.service.SkpService;
import com.rjxx.taxeasy.service.SpvoService;
import com.rjxx.taxeasy.service.XfService;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by xlm on 2017/8/2.
 */
public class FwkGetDataJob implements Job {

    private static Logger logger = LoggerFactory.getLogger(FwkGetDataJob.class);

    @Autowired
    private SkpService skpService;
    @Autowired
    private XfService xfService;
    @Autowired
    private SpvoService spvoService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("获取福维克开票数据任务执行开始,nextFireTime:{},"+context.getNextFireTime());
        String invoiceBack="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:glob=\"http://sap.com/xi/SAPGlobal20/Global\" xmlns:y1j=\"http://0001092235-one-off.sap.com/Y1JU2A0ZY_\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <glob:CustomerInvoiceByElementsQuery_sync>\n" +
                "         <CustomerInvoiceSelectionByElements>\n" +
                "            <SelectionByLastChangeDateTime>\n" +
                "               <InclusionExclusionCode>I</InclusionExclusionCode>\n" +
                "               <IntervalBoundaryTypeCode>3</IntervalBoundaryTypeCode>\n" +
                "               <LowerBoundaryCustomerInvoiceLastChangeDateTime>2017-07-21T00:00:00.00000Z</LowerBoundaryCustomerInvoiceLastChangeDateTime>\n" +
                "               <UpperBoundaryCustomerInvoiceLastChangeDateTime>2017-07-31T00:00:00.00000Z</UpperBoundaryCustomerInvoiceLastChangeDateTime>\n" +
                "            </SelectionByLastChangeDateTime>\n" +
                "         </CustomerInvoiceSelectionByElements>\n" +
                "         <ProcessingConditions>\n" +
                "            <QueryHitsMaximumNumberValue>4</QueryHitsMaximumNumberValue>\n" +
                "            <QueryHitsUnlimitedIndicator>false</QueryHitsUnlimitedIndicator>\n" +
                "         </ProcessingConditions>\n" +
                "      </glob:CustomerInvoiceByElementsQuery_sync>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        String Data= HttpUtils.doPostSoap1_1("https://my337109.sapbydesign.com/sap/bc/srt/scs/sap/querycustomerinvoicein?sap-vhost=my337109.sapbydesign.com", invoiceBack, null,"_GoldenTax","Welcome9");
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
        List<Map> CustomerInvoice=(List)CustomerInvoiceByElementsResponse_sync.get("CustomerInvoice");
        for(int i = 0; i < CustomerInvoice.size(); i++){
            Map CustomerInvoiceMap=CustomerInvoice.get(i);
            String InvoiceID=null;/**sap发票ID**/
            if (null != CustomerInvoiceMap.get("ID") && !CustomerInvoiceMap.get("ID").equals("")) {
                InvoiceID = CustomerInvoiceMap.get("ID").toString();//sap发票ID
            }
            String OriginalSerialNumber=null;/**SerialNumber交易流水号(发票：InvoiceID+SapOrderID，贷记凭证：原发票InvoiceID+SapOrderID)**/
            if (null != CustomerInvoiceMap.get("OriginalSerialNumber") && !CustomerInvoiceMap.get("OriginalSerialNumber").equals("")) {
                OriginalSerialNumber=CustomerInvoiceMap.get("ID").toString();//SerialNumber交易流水号(发票：InvoiceID+SapOrderID，贷记凭证：原发票InvoiceID+SapOrderID)
            }
            String InvoiceCustomerNameCI=null;/**sap发票客户名称对应平台购方名称**/
            if (null != CustomerInvoiceMap.get("InvoiceCustomerNameCI") && !CustomerInvoiceMap.get("InvoiceCustomerNameCI").equals("")) {
                InvoiceCustomerNameCI=CustomerInvoiceMap.get("InvoiceCustomerNameCI").toString();
            }
            String InvoiceCustomerTaxNumberCI=null;/**sap发票客户税号对应平台购方税号**/
            if (null != CustomerInvoiceMap.get("InvoiceCustomerTaxNumberCI") && !CustomerInvoiceMap.get("InvoiceCustomerTaxNumberCI").equals("")) {
                InvoiceCustomerTaxNumberCI=CustomerInvoiceMap.get("InvoiceCustomerTaxNumberCI").toString();
            }
            String InvoiceCustomerAddressTelCI=null;/**sap 发票客户地址电话 对应平台购方地址电话**/
            if (null != CustomerInvoiceMap.get("InvoiceCustomerAddressTelCI") && !CustomerInvoiceMap.get("InvoiceCustomerAddressTelCI").equals("")) {
                InvoiceCustomerAddressTelCI=CustomerInvoiceMap.get("InvoiceCustomerAddressTelCI").toString();
            }
            String CustomerBankAccountCI=null;/**sap 客户银行及账号对应平台购方银行账号**/
            if (null != CustomerInvoiceMap.get("CustomerBankAccountCI") && !CustomerInvoiceMap.get("CustomerBankAccountCI").equals("")) {
                CustomerBankAccountCI=CustomerInvoiceMap.get("CustomerBankAccountCI").toString();
            }
            String DrawerCI=null;/**sap 开票人**/
            if (null != CustomerInvoiceMap.get("DrawerCI") && !CustomerInvoiceMap.get("DrawerCI").equals("")) {
                DrawerCI=CustomerInvoiceMap.get("DrawerCI").toString();
            }
            String BillingContentCI=null;/**sap 开票内容**/
            if (null != CustomerInvoiceMap.get("BillingContentCI") && !CustomerInvoiceMap.get("BillingContentCI").equals("")) {
                BillingContentCI=CustomerInvoiceMap.get("BillingContentCI").toString();
            }
            String BillingAddressCI=null;/**sap 送票地址**/
            if (null != CustomerInvoiceMap.get("BillingAddressCI") && !CustomerInvoiceMap.get("BillingAddressCI").equals("")) {
                BillingAddressCI=CustomerInvoiceMap.get("BillingAddressCI").toString();
            }
            String OrderDateTime=null;/**sap 订单时间**/
            if (null != CustomerInvoiceMap.get("OrderDateTime") && !CustomerInvoiceMap.get("OrderDateTime").equals("")) {
                OrderDateTime=CustomerInvoiceMap.get("OrderDateTime").toString();
            }
            Map ProcessingTypeNameMap=(Map)CustomerInvoiceMap.get("ProcessingTypeName");
            String processingTypeName=null;/**发票类型**/
            if (null != ProcessingTypeNameMap.get("$") && !ProcessingTypeNameMap.get("$").equals("")) {
                processingTypeName=ProcessingTypeNameMap.get("$").toString();//发票类型
            }
            String PartyID=null;/**sap销方编号，对应平台开票点代码**/
            Map SellerPartyMap=(Map)CustomerInvoiceMap.get("SellerParty");
            if (null != SellerPartyMap.get("PartyID") && !SellerPartyMap.get("PartyID").equals("")) {
                PartyID=SellerPartyMap.get("PartyID").toString();
            }
            Map PriceAndTaxMap=(Map)CustomerInvoiceMap.get("PriceAndTax");

            String NetAmount=null;/**sap整张发票净价 即不含税金额**/
            Map NetAmountMap=(Map)PriceAndTaxMap.get("NetAmount");
            if (null != NetAmountMap.get("$") && !NetAmountMap.get("$").equals("")) {
                NetAmount=NetAmountMap.get("$").toString();
            }
            String TaxAmount=null;/**sap 整张发票税额  即税额**/
            Map TaxAmountMap=(Map)PriceAndTaxMap.get("TaxAmount");
            if (null != TaxAmountMap.get("$") && !TaxAmountMap.get("$").equals("")) {
                TaxAmount=TaxAmountMap.get("$").toString();
            }
            String GrossAmount=null;/**sap 整张发票价税合计 即价税合计**/
            Map GrossAmountMap=(Map)PriceAndTaxMap.get("GrossAmount");
            if (null != GrossAmountMap.get("$") && !GrossAmountMap.get("$").equals("")) {
                GrossAmount=GrossAmountMap.get("$").toString();
            }
            Jyxxsq jyxxsq=new Jyxxsq();
            jyxxsq.setKpddm(PartyID);//开票点代码
            jyxxsq.setJylsh(OriginalSerialNumber);//交易流水号
            jyxxsq.setFpzldm("12");//发票种类
            Map params=new HashMap();
            params.put("gsdm","fwk");
            params.put("kpddm",PartyID);
            Skp skp=skpService.findOneByParams(params);
            Xf xf=xfService.findOne(skp.getXfid());
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
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            try {
                jyxxsq.setDdrq(OrderDateTime == null ? new Date() : sim.parse(OrderDateTime));//订单时间
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
            jyxxsq.setTqm(OriginalSerialNumber);
           // jyxxsq.setBz();
            String ddh="";//订单号
            String bz="";//备注
            String gfsjh="";//购方手机号
            List<Jymxsq> jymxsqList=new ArrayList<>();
            if(CustomerInvoiceMap.get("Item") instanceof Map){

                Map ItemMap=(Map)CustomerInvoiceMap.get("Item");
                String ID=null;/**sap 产品编号*/
                if (null != ItemMap.get("ID") && !ItemMap.get("ID").equals("")) {
                    ID=ItemMap.get("ID").toString();
                }
                String Description=null;/**产品名称 即商品名称**/
                Map DescriptionMap=(Map)ItemMap.get("Description");
                if (null != DescriptionMap.get("$") && !DescriptionMap.get("$").equals("")) {
                    Description=DescriptionMap.get("$").toString();
                }
                String Quantity=null;/** sap 产品数量  即商品数量 **/
                Map QuantityMap=(Map)ItemMap.get("Quantity");
                Map QuantityslMap=(Map)QuantityMap.get("Quantity");
                if (null != QuantityslMap.get("$") && !QuantityslMap.get("$").equals("")) {
                    Quantity=QuantityslMap.get("$").toString();
                }
                String MeasureUnitName=null;/**sap 商品单位  **/
                Map MeasureUnitNameMap=(Map)QuantityMap.get("MeasureUnitName");
                if (null != MeasureUnitNameMap.get("$") && !MeasureUnitNameMap.get("$").equals("")) {
                    MeasureUnitName=MeasureUnitNameMap.get("$").toString();
                }

                String SalesOrderReferenceID=null;/**sap销售订单号**/
                Map SalesOrderReferenceMap=(Map)ItemMap.get("SalesOrderReference");
                if (null != SalesOrderReferenceMap.get("ID") && !SalesOrderReferenceMap.get("ID").equals("")) {
                    SalesOrderReferenceID=SalesOrderReferenceMap.get("ID").toString();
                    ddh=SalesOrderReferenceID;
                }
                String DistributionChannelCode=null;/**分货渠道代码**/
                Map DistributionChannelCodeMap=(Map)ItemMap.get("SalesAndServiceBusinessArea");
                if (null != DistributionChannelCodeMap.get("DistributionChannelCode") && !DistributionChannelCodeMap.get("DistributionChannelCode").equals("")) {
                    DistributionChannelCode=DistributionChannelCodeMap.get("DistributionChannelCode").toString();
                }
                String MobilePhoneFormattedNumberDescription=null;/**sap收货人手机  对应平台购方手机号**/
                Map ProductRecipientPartyMap=(Map)ItemMap.get("ProductRecipientParty");
                Map AddressMap=(Map)ProductRecipientPartyMap.get("Address");
                if (null != AddressMap.get("MobilePhoneFormattedNumberDescription") && !AddressMap.get("MobilePhoneFormattedNumberDescription").equals("")) {
                    MobilePhoneFormattedNumberDescription=AddressMap.get("MobilePhoneFormattedNumberDescription").toString();
                    gfsjh=MobilePhoneFormattedNumberDescription;
                }
                String PurchaseOrderReferenceID=null;/** sap 前台订单号/外部参考号**/
                Map PurchaseOrderReferenceMap=(Map)ItemMap.get("PurchaseOrderReference");
                if (null != PurchaseOrderReferenceMap.get("ID") && !PurchaseOrderReferenceMap.get("ID").equals("")) {
                    PurchaseOrderReferenceID=PurchaseOrderReferenceMap.get("ID").toString();
                    bz=PurchaseOrderReferenceID;
                }
                Map ItemPriceAndTaxMap=(Map)ItemMap.get("PriceAndTax");

                String ItemNetAmount=null;/**sap行发票净价 即不含税金额**/
                Map ItemNetAmountMap=(Map)ItemPriceAndTaxMap.get("NetAmount");
                if (null != ItemNetAmountMap.get("$") && !ItemNetAmountMap.get("$").equals("")) {
                    ItemNetAmount=ItemNetAmountMap.get("$").toString();
                }
                String ItemTaxAmount=null;/**sap 行整张发票税额  即税额**/
                Map ItemTaxAmountMap=(Map)ItemPriceAndTaxMap.get("TaxAmount");
                if (null != ItemTaxAmountMap.get("$") && !ItemTaxAmountMap.get("$").equals("")) {
                    ItemTaxAmount=ItemTaxAmountMap.get("$").toString();
                }
                String ItemGrossAmount=null;/**sap 行整张发票价税合计 即价税合计**/
                Map ItemGrossAmountMap=(Map)ItemPriceAndTaxMap.get("GrossAmount");
                if (null != ItemGrossAmountMap.get("$") && !ItemGrossAmountMap.get("$").equals("")) {
                    ItemGrossAmount=ItemGrossAmountMap.get("$").toString();
                }
                Jymxsq jymxsq=new Jymxsq();
                //jymxsq.setSpdm();
                jymxsq.setSpmc(Description);
               // jymxsq.setSpggxh();
                jymxsq.setFphxz("0");
                jymxsq.setSps(Double.valueOf(Quantity));
               // jymxsq.setSpdj();
                jymxsq.setSpdw(MeasureUnitName);
                jymxsq.setSpje(Double.valueOf(ItemGrossAmount));
                jymxsq.setSpse(Double.valueOf(ItemTaxAmount));
                jymxsq.setJshj(Double.valueOf(ItemGrossAmount));
                //jymxsq.setSpsl();
                jymxsqList.add(jymxsq);
            }else if(CustomerInvoiceMap.get("Item") instanceof List){
                List<Map> ItemList=(List)CustomerInvoiceMap.get("Item");
                for(int j = 0; j < ItemList.size(); j++){
                    Map ItemMap=(Map)ItemList.get(j);
                    String ID=null;/**sap 产品编号*/
                    if (null != ItemMap.get("ID") && !ItemMap.get("ID").equals("")) {
                        ID=ItemMap.get("ID").toString();
                    }
                    String Description=null;/**产品名称 即商品名称**/
                    Map DescriptionMap=(Map)ItemMap.get("Description");
                    if (null != DescriptionMap.get("$") && !DescriptionMap.get("$").equals("")) {
                        Description=DescriptionMap.get("$").toString();
                    }
                    String Quantity=null;/** sap 产品数量  即商品数量 **/
                    Map QuantityMap=(Map)ItemMap.get("Quantity");
                    Map QuantityslMap=(Map)QuantityMap.get("Quantity");
                    if (null != QuantityslMap.get("$") && !QuantityslMap.get("$").equals("")) {
                        Quantity=QuantityslMap.get("$").toString();
                    }
                    String MeasureUnitName=null;/**sap 商品单位  **/
                    Map MeasureUnitNameMap=(Map)QuantityMap.get("MeasureUnitName");
                    if (null != MeasureUnitNameMap.get("$") && !MeasureUnitNameMap.get("$").equals("")) {
                        MeasureUnitName=MeasureUnitNameMap.get("$").toString();
                    }

                    String SalesOrderReferenceID=null;/**sap销售订单号**/
                    Map SalesOrderReferenceMap=(Map)ItemMap.get("SalesOrderReference");
                    if (null != SalesOrderReferenceMap.get("ID") && !SalesOrderReferenceMap.get("ID").equals("")) {
                        SalesOrderReferenceID=SalesOrderReferenceMap.get("ID").toString();
                    }
                    String DistributionChannelCode=null;/**分货渠道代码**/
                    Map DistributionChannelCodeMap=(Map)ItemMap.get("SalesAndServiceBusinessArea");
                    if (null != DistributionChannelCodeMap.get("DistributionChannelCode") && !DistributionChannelCodeMap.get("DistributionChannelCode").equals("")) {
                        DistributionChannelCode=DistributionChannelCodeMap.get("DistributionChannelCode").toString();
                    }
                    String MobilePhoneFormattedNumberDescription=null;/**sap收货人手机  对应平台购方手机号**/
                    Map ProductRecipientPartyMap=(Map)ItemMap.get("ProductRecipientParty");
                    Map AddressMap=(Map)ProductRecipientPartyMap.get("Address");
                    if (null != AddressMap.get("MobilePhoneFormattedNumberDescription") && !AddressMap.get("MobilePhoneFormattedNumberDescription").equals("")) {
                        MobilePhoneFormattedNumberDescription=AddressMap.get("MobilePhoneFormattedNumberDescription").toString();
                        gfsjh=MobilePhoneFormattedNumberDescription;
                    }
                    String PurchaseOrderReferenceID=null;/** sap 前台订单号/外部参考号**/
                    Map PurchaseOrderReferenceMap=(Map)ItemMap.get("PurchaseOrderReference");
                    if (null != PurchaseOrderReferenceMap.get("ID") && !PurchaseOrderReferenceMap.get("ID").equals("")) {
                        PurchaseOrderReferenceID=PurchaseOrderReferenceMap.get("ID").toString();
                    }
                    Map ItemPriceAndTaxMap=(Map)ItemMap.get("PriceAndTax");

                    String ItemNetAmount=null;/**sap行发票净价 即不含税金额**/
                    Map ItemNetAmountMap=(Map)ItemPriceAndTaxMap.get("NetAmount");
                    if (null != ItemNetAmountMap.get("$") && !ItemNetAmountMap.get("$").equals("")) {
                        ItemNetAmount=ItemNetAmountMap.get("$").toString();
                    }
                    String ItemTaxAmount=null;/**sap 行发票税额  即税额**/
                    Map ItemTaxAmountMap=(Map)ItemPriceAndTaxMap.get("TaxAmount");
                    if (null != ItemTaxAmountMap.get("$") && !ItemTaxAmountMap.get("$").equals("")) {
                        ItemTaxAmount=ItemTaxAmountMap.get("$").toString();
                    }
                    String ItemGrossAmount=null;/**sap 行发票价税合计 即价税合计**/
                    Map ItemGrossAmountMap=(Map)ItemPriceAndTaxMap.get("GrossAmount");
                    if (null != ItemGrossAmountMap.get("$") && !ItemGrossAmountMap.get("$").equals("")) {
                        ItemGrossAmount=ItemGrossAmountMap.get("$").toString();
                    }
                    Jymxsq jymxsq=new Jymxsq();
                    //jymxsq.setSpdm();
                    jymxsq.setSpmc(Description);
                    // jymxsq.setSpggxh();
                    jymxsq.setFphxz("0");
                    jymxsq.setSps(Double.valueOf(Quantity));
                    // jymxsq.setSpdj();
                    jymxsq.setSpdw(MeasureUnitName);
                    jymxsq.setSpje(Double.valueOf(ItemGrossAmount));
                    jymxsq.setSpse(Double.valueOf(ItemTaxAmount));
                    jymxsq.setJshj(Double.valueOf(ItemGrossAmount));
                    //jymxsq.setSpsl();
                    jymxsqList.add(jymxsq);
                }
            }
            jyxxsq.setBz(bz);
            jyxxsq.setDdh(ddh);
            jyxxsq.setGfsjh(gfsjh);
        }
        return null;
    }

    public static void main(String[] args) {
        String invoiceBack="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:glob=\"http://sap.com/xi/SAPGlobal20/Global\" xmlns:y1j=\"http://0001092235-one-off.sap.com/Y1JU2A0ZY_\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <glob:CustomerInvoiceByElementsQuery_sync>\n" +
                "         <CustomerInvoiceSelectionByElements>\n" +
                "            <SelectionByLastChangeDateTime>\n" +
                "               <InclusionExclusionCode>I</InclusionExclusionCode>\n" +
                "               <IntervalBoundaryTypeCode>3</IntervalBoundaryTypeCode>\n" +
                "               <LowerBoundaryCustomerInvoiceLastChangeDateTime>2017-07-30T00:00:00.00000Z</LowerBoundaryCustomerInvoiceLastChangeDateTime>\n" +
                "               <UpperBoundaryCustomerInvoiceLastChangeDateTime>2017-07-31T00:00:00.00000Z</UpperBoundaryCustomerInvoiceLastChangeDateTime>\n" +
                "            </SelectionByLastChangeDateTime>\n" +
                "         </CustomerInvoiceSelectionByElements>\n" +
                "         <ProcessingConditions>\n" +
                "            <QueryHitsMaximumNumberValue>3</QueryHitsMaximumNumberValue>\n" +
                "            <QueryHitsUnlimitedIndicator>false</QueryHitsUnlimitedIndicator>\n" +
                "         </ProcessingConditions>\n" +
                "      </glob:CustomerInvoiceByElementsQuery_sync>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        String Data= HttpUtils.doPostSoap1_1("https://my337109.sapbydesign.com/sap/bc/srt/scs/sap/querycustomerinvoicein?sap-vhost=my337109.sapbydesign.com", invoiceBack, null,"_GoldenTax","Welcome9");
        FwkGetDataJob fwkGetDataJob=new FwkGetDataJob();
        Map resultMap=fwkGetDataJob.interping(Data);
    }
}
