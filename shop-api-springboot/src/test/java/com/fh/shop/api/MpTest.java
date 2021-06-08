package com.fh.shop.api;

import com.fh.shop.mapper.IMsgLogMapper;
import com.fh.shop.po.MsgLog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MpTest {

    @Autowired
    private IMsgLogMapper msgLogMapper;

    @Test
    public  void  test(){
        MsgLog msgLog = new MsgLog();
        msgLog.setMsgId("1111");
        msgLog.setInsertTime(new Date());
        msgLogMapper.insert(msgLog);
    }

}
