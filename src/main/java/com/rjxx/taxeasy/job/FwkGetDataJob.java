package com.rjxx.taxeasy.job;

import com.rjxx.taxeasy.bizcomm.utils.HttpUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xlm on 2017/8/2.
 */
public class FwkGetDataJob implements Job {

    private static Logger logger = LoggerFactory.getLogger(FwkGetDataJob.class);

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
                "            <QueryHitsMaximumNumberValue>500</QueryHitsMaximumNumberValue>\n" +
                "            <QueryHitsUnlimitedIndicator>false</QueryHitsUnlimitedIndicator>\n" +
                "         </ProcessingConditions>\n" +
                "      </glob:CustomerInvoiceByElementsQuery_sync>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        String Data= HttpUtils.doPostSoap1_1("https://my338217.sapbydesign.com/sap/bc/srt/scs/sap/querycustomerinvoicein?sap-vhost=my338217.sapbydesign.com", invoiceBack, null,"_GoldenTax","Welcome9");
        Map jyxxMap=interping(Data);


    }

    /**
     * 解析报文
     * @param data
     * @return
     */
    private Map interping(String data) {


        return null;
    }
}
