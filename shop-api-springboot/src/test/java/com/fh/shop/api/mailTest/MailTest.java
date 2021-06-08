package com.fh.shop.api.mailTest;

import com.fh.shop.api.mq.MailProducer;
import com.fh.shop.mq.MailMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MailTest {

    @Autowired
    private MailProducer producer;

    @Test
    public void test1(){
        MailMessage mailMessage = new MailMessage();
        mailMessage.setTo("2953423981@qq.com");
        mailMessage.setContent("aaaa");
        mailMessage.setTitle("aaaaa");
        producer.sendMailMessage(mailMessage);
    }


}
