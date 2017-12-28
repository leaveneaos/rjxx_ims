package com.rjxx.taxeasy.bizcomm.utils;

import com.rjxx.Application;
import com.rjxx.taxeasy.job.FwkGetDataJob;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Map;

/**
 * Created by xlm on 2017/11/2.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class FwkGetDataTest {

    @Autowired
    private FwkGetDataJob fwkGetDataJob;

    @Test
    public void getdata(){
        String invoiceBack="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:glob=\"http://sap.com/xi/SAPGlobal20/Global\" xmlns:yni=\"http://0001092235-one-off.sap.com/YNIIVJHSY_\">\n" +
                "<soapenv:Header/>\n" +
                "<soapenv:Body>\n" +
                "<glob:CustomerInvoiceByElementsQuery_sync>\n" +
                "<CustomerInvoiceSelectionByElements>\n" +
                "<SelectionByDate>\n" +
                "<InclusionExclusionCode>I</InclusionExclusionCode>\n" +
                "<IntervalBoundaryTypeCode>1</IntervalBoundaryTypeCode>\n" +
                "<LowerBoundaryCustomerInvoiceDate>2017-12-16</LowerBoundaryCustomerInvoiceDate>\n" +
                 /*"<LowerBoundaryCustomerInvoiceDate>2017-12-04</LowerBoundaryCustomerInvoiceDate>\n"+
                 "<UpperBoundaryCustomerInvoiceDate>2017-12-25</UpperBoundaryCustomerInvoiceDate>\n"+*/
                 "</SelectionByDate>\n" +
                /*"<SelectionByID>\n" +
                "<InclusionExclusionCode>I</InclusionExclusionCode>\n" +
                "<IntervalBoundaryTypeCode>1</IntervalBoundaryTypeCode>\n" +
                "<LowerBoundaryIdentifier>168618</LowerBoundaryIdentifier>\n" +
                "</SelectionByID>\n" +*/
                "</CustomerInvoiceSelectionByElements>\n" +
                "<ProcessingConditions>\n" +
                "<QueryHitsMaximumNumberValue>300</QueryHitsMaximumNumberValue>\n" +
                "<QueryHitsUnlimitedIndicator>false</QueryHitsUnlimitedIndicator>\n" +
                "</ProcessingConditions>\n" +
                "</glob:CustomerInvoiceByElementsQuery_sync>\n" +
                "</soapenv:Body>\n" +
                "</soapenv:Envelope>\n";
        String Data= HttpUtils.doPostSoap1_1("https://my337076.sapbydesign.com/sap/bc/srt/scs/sap/querycustomerinvoicein?sap-vhost=my337076.sapbydesign.com", invoiceBack, null,"_BW","Welcome9");

        Map resultMap=fwkGetDataJob.interping(Data);
       /*String ss=" <soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:glob=\"http://sap.com/xi/SAPGlobal20/Global\">\n" +
               "    <soap:Header/>\n" +
               "    <soap:Body>\n" +
               "       <glob:GoldenTaxGoldenTaxCreateRequest_sync>\n" +
               "          <BasicMessageHeader></BasicMessageHeader>\n" +
               "          <GoldenTax>\n" +
               "             <CutInvID>166890</CutInvID>\n" +
               "             <GoldenTaxID>88297004</GoldenTaxID>\n" +
               "             <GoldenTaxDate>2017-12-07</GoldenTaxDate>\n" +
               "             <GoldenTaxResult>Succeed</GoldenTaxResult>\n" +
               "             <GoldenTaxCode>031001700111</GoldenTaxCode>\n" +
               "          </GoldenTax>\n" +
               "       </glob:GoldenTaxGoldenTaxCreateRequest_sync>\n" +
               "    </soap:Body>\n" +
               " </soap:Envelope>\n";
        String Data= HttpUtils.doPostSoap1_2("https://my337076.sapbydesign.com/sap/bc/srt/scs/sap/yyb40eysay_managegoldentaxinvo?sap-vhost=my337076.sapbydesign.com", ss, null,"Wendy","Welcome9");*/

    }

}
