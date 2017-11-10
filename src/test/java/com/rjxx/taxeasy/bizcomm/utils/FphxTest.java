package com.rjxx.taxeasy.bizcomm.utils;

import com.alibaba.fastjson.JSON;
import com.rjxx.Application;
import com.rjxx.taxeasy.domains.Fphxwsjl;
import com.rjxx.taxeasy.domains.Gsxx;
import com.rjxx.taxeasy.service.FphxwsjlService;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xlm on 2017/11/8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class FphxTest {

    @Autowired
    private GeneratePdfService generatePdfService;

    @Autowired
    private FphxwsjlService fphxwsjlService;



    @Test
    public void fphx(){
        Map parms=new HashMap();
        parms.put("gsdm","Family");
        parms.put("returnCode","9004");
        parms.put("rqq","2017-10-17");
        parms.put("rqz","2017-11-09");
        List<Fphxwsjl> fphxwsjlList=fphxwsjlService.findAllByParams(parms);
        for(Fphxwsjl fphxwsjl:fphxwsjlList){
            try {
                Map resultMap=this.httpPost(fphxwsjl.getReturncontent(),fphxwsjl.getWsurl(),fphxwsjl.getSign());
                String returnCode=resultMap.get("ReturnCode").toString();
                String ReturnMessage=resultMap.get("ReturnMessage").toString();
                fphxwsjl.setReturnmessage(ReturnMessage);
                fphxwsjl.setReturncode(returnCode);
                fphxwsjlService.save(fphxwsjl);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public  Map httpPost(String sendMes,String url,String sign) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        RequestConfig requestConfig = RequestConfig.custom().
                setSocketTimeout(120*1000).setConnectionRequestTimeout(120*1000).setConnectTimeout(120*1000).build();
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .build();
        httpPost.addHeader("Content-Type", "application/json");
        String strMessage = "";
        BufferedReader reader = null;
        StringBuffer buffer = new StringBuffer();
        Map resultMap = null;
        try {
            Map nvps = new HashMap();
            nvps.put("invoiceData", sendMes);
            nvps.put("sign", sign);
            StringEntity requestEntity = new StringEntity(JSON.toJSONString(nvps), "utf-8");
            httpPost.setEntity(requestEntity);
            response = httpClient.execute(httpPost, new BasicHttpContext());
            if (response.getStatusLine().getStatusCode() != 200) {
                System.out.println("request url failed, http code=" + response.getStatusLine().getStatusCode()
                        + ", url=" + url);
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                reader = new BufferedReader(new InputStreamReader(entity.getContent(), "utf-8"));
                while ((strMessage = reader.readLine()) != null) {
                    buffer.append(strMessage);
                }
            }
            System.out.println("接收返回值:" + buffer.toString());
            resultMap = handerReturnMes(buffer.toString());
        } catch (IOException e) {
            System.out.println("request url=" + url + ", exception, msg=" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (response != null) try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultMap;
    }

    /**
     * 接收返回报文并做后续处理
     *
     * @param returnMes
     *
     * @throws Exception
     */
    public  Map handerReturnMes(String returnMes) throws Exception {

        Document document = DocumentHelper.parseText(returnMes);
        Element root = document.getRootElement();
        List<Element> childElements = root.elements();
        Map resultMap = new HashMap();
        for (Element child : childElements) {
            resultMap.put(child.getName(), child.getText());// 返回结果
        }
        return resultMap;
    }
}
