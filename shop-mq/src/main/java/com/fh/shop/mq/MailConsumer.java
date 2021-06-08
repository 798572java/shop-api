package com.fh.shop.mq;

import com.alibaba.fastjson.JSON;
import com.fh.shop.common.SystemConstant;
import com.fh.shop.mapper.IMsgLogMapper;
import com.fh.shop.po.MsgLog;
import com.fh.shop.util.MailUtil;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class MailConsumer {

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private IMsgLogMapper msgLogMapper;

    @RabbitListener(queues = MQConstants.MAIL_QUEUE)
    public void  handleMailMessage(String mailJson, Message message, Channel channel) throws IOException {
        MessageProperties messageProperties = message.getMessageProperties();
        long deliveryTag = messageProperties.getDeliveryTag();
        MailMessage mailMessage = JSON.parseObject(mailJson, MailMessage.class);
        String msgId = mailMessage.getMsgId();
        try {
            //进行消息幂等性的判断
            MsgLog msgLogInfo = msgLogMapper.selectById(msgId);
            //随着表中的数据越来越多，所以需要把以前消费成功的给进行删除，要么人工删除，要么定时删除.
            if(msgLogInfo == null || msgLogInfo.getStatus() == SystemConstant.MESSAGE_LOG_STATUS.CONSUME_SUCCESS){
                //响应，删除消息队列中的消息
                channel.basicAck(deliveryTag,false);
                return;
            }
            //发送邮件
            mailUtil.sendMailHtml(mailMessage.getTo(),mailMessage.getTitle(),mailMessage.getContent());
            //更新数据库表中消息的状态为消费成功
            MsgLog msgLog = new MsgLog();
            msgLog.setStatus(SystemConstant.MESSAGE_LOG_STATUS.CONSUME_SUCCESS);
            msgLog.setUpdateTime(new Date());
            msgLog.setMsgId(msgId);
            msgLogMapper.updateById(msgLog);
            //响应，删除消息队列中的消息
            channel.basicAck(deliveryTag,false);
        } catch (Exception e) {
            e.printStackTrace();
            //要从队列中删除,第三个参数代表是否重进队列，true代表是，false代表否
            //因为这条消息已经再表中了，我们可以在后台做一个功能
            //查询msg_log表中状态为投递成功，投递失败的数据
            channel.basicNack(deliveryTag,false,true);
        }
    }
    

}
