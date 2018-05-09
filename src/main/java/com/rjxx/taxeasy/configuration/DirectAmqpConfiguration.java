package com.rjxx.taxeasy.configuration;

import com.rjxx.taxeasy.bizcomm.utils.SkService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName DirectAmqpConfiguration
 * @Description TODO
 * @Author 许黎明
 * @Date 2018-05-09 10:18
 * @Version 1.0
 **/
@Component
@RabbitListener(queues = "queue_ErrorException_Sk_12")
public class DirectAmqpConfiguration {

    @Autowired
    private SkService skService;

    @RabbitHandler
    public void receive(String message) {
        try{
            System.out.println("收到信息: " + message);
            skService.SkServerKP(Integer.valueOf(message));
            //订单数据库处理
        }catch(Exception e){
            System.out.println("发送失败信息给用户邮箱，或发送短信，推送消息");
        }
    }

}
