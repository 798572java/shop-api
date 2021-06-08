package com.fh.shop.api.mq;

import com.alibaba.fastjson.JSON;
import com.fh.shop.common.SystemConstant;
import com.fh.shop.mapper.IMsgLogMapper;
import com.fh.shop.mq.MQConstants;
import com.fh.shop.mq.MailMessage;
import com.fh.shop.po.MsgLog;
import com.fh.shop.util.DateForMat;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class MailProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private IMsgLogMapper msgLogMapper;

    //使用AmqpTemplate
    public void sendMail(MailMessage mailMessage){
        String mailMsgJson = JSON.toJSONString(mailMessage);
        amqpTemplate.convertAndSend(MQConstants.MAIL_EXCHANGE, MQConstants.MAIL_ROUT_KEY,mailMsgJson);
    }

    //使用RabbitTemplate
    public void sendMailMessage(MailMessage mailMessage){
        String msgId = UUID.randomUUID().toString();
        mailMessage.setMsgId(msgId);
        String mailMsgJson = JSON.toJSONString(mailMessage);
        //插入数据库
        MsgLog msgLog = new MsgLog();
        msgLog.setMsgId(msgId);
        msgLog.setExchange(MQConstants.MAIL_EXCHANGE);
        msgLog.setRouteKey(MQConstants.MAIL_ROUT_KEY);
        msgLog.setMessage(mailMsgJson);
        Date date = new Date();
        msgLog.setInsertTime(date);
        msgLog.setUpdateTime(date);
        //下次时间=当前时间加一分钟
        Date retryTime = DateForMat.addMinute(date, 1);
        msgLog.setRetryTime(retryTime);
        msgLog.setStatus(SystemConstant.MESSAGE_LOG_STATUS.SENDING);
        msgLog.setRetryCount(0);
        msgLogMapper.insert(msgLog);
        CorrelationData correlationData = new CorrelationData(msgId);
        //发送消息
        rabbitTemplate.convertAndSend(MQConstants.MAIL_EXCHANGE,MQConstants.MAIL_ROUT_KEY,mailMsgJson,correlationData);
    }



}
