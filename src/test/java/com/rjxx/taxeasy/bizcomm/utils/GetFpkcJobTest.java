package com.rjxx.taxeasy.bizcomm.utils;

import com.rjxx.Application;
import com.rjxx.taxeasy.job.GetFpkcJob;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by xlm on 2017/11/2.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class GetFpkcJobTest {

    @Autowired
    private GetFpkcJob getFpkcJob;

    private String LastReturnedObjectID="";

    @Test
    public void getFpkc(){
        getFpkcJob.getFpkc("");
    }

}
