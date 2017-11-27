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
                "<SelectionByID>\n" +
                "<InclusionExclusionCode>I</InclusionExclusionCode>\n" +
                "<IntervalBoundaryTypeCode>1</IntervalBoundaryTypeCode>\n" +
                "<LowerBoundaryIdentifier>140022</LowerBoundaryIdentifier>\n" +
                "</SelectionByID>\n" +
                "</CustomerInvoiceSelectionByElements>\n" +
                "<ProcessingConditions>\n" +
                "<QueryHitsMaximumNumberValue>1</QueryHitsMaximumNumberValue>\n" +
                "<QueryHitsUnlimitedIndicator>false</QueryHitsUnlimitedIndicator>\n" +
                "</ProcessingConditions>\n" +
                "</glob:CustomerInvoiceByElementsQuery_sync>\n" +
                "</soapenv:Body>\n" +
                "</soapenv:Envelope>\n";
        String Data= HttpUtils.doPostSoap1_1("https://my337109.sapbydesign.com/sap/bc/srt/scs/sap/querycustomerinvoicein?sap-vhost=my337109.sapbydesign.com", invoiceBack, null,"_GoldenTax","Welcome9");
        //FwkGetDataJob fwkGetDataJob=new FwkGetDataJob();
        Map resultMap=fwkGetDataJob.interping(Data);

    }

}
