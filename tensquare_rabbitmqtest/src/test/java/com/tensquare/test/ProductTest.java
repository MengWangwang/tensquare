package com.tensquare.test;

import com.tensquare.rabbit.RabbitApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitApplication.class)
public class ProductTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /*
        普通模式
     */
    @Test
    public void SendMag(){
        rabbitTemplate.convertAndSend("itcast","直接模式测试");
    }
    /*
       分裂模式
    */
    @Test
    public void SendMag1(){
        rabbitTemplate.convertAndSend("mww","","分裂模式测试");
    }

    /*
       主题模式
    */
    @Test
    public void SendMag2(){
        rabbitTemplate.convertAndSend("topictest","good.log","主题模式测试");
    }
}
