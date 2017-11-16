package com.rjxx.taxeasy.bizcomm.utils;

import com.rjxx.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by Administrator on 2017-02-16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class SkServiceTest {

    @Autowired
    private SkService skService;

    @Test
    public void testCallService() throws Exception {
        for(int i=0;i<=3000;i++) {
            InvoiceResponse response = skService.callService(143);
            System.out.println("第"+i+"条数据");
            System.out.println(response.getReturnCode());
            System.out.println(response.getReturnMessage());
        }
    }


}
