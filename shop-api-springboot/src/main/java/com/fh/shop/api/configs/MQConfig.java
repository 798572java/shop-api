package com.fh.shop.api.configs;

import com.fh.shop.common.SystemConstant;
import com.fh.shop.mapper.IMsgLogMapper;
import com.fh.shop.mq.MQConstants;
import com.fh.shop.po.MsgLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
@Slf4j
public class MQConfig {

    @Autowired
    private CachingConnectionFactory cachingConnectionFactory;

    @Autowired
    private IMsgLogMapper msgLogMapper;

    private int status = 0;

    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        //设置回调方法
        rabbitTemplate.setConfirmCallback((correlationData,ack,cause) -> {
            if(ack && status == 0){
                //发送成功
                MsgLog msgLog = new MsgLog();
                msgLog.setStatus(SystemConstant.MESSAGE_LOG_STATUS.SEND_SUCCESS);
                msgLog.setUpdateTime(new Date());
                String msgId = correlationData.getId();
                msgLog.setMsgId(msgId);
                msgLogMapper.updateById(msgLog);
            }else {
                //发送失败
                log.info("生产者链接交换机失败,消息:{},原因:{}",correlationData,cause);
            }
        });
        //设置交换机和队列的回调
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback((message,replyCode,replyText,exchange,routingKey) -> {
            //交换机连接队列失败
            log.info("交换机连接队列失败,消息：{},replyCode,{},replyText:{},exchange:{},routingKey:{}",message,replyCode,replyText,exchange,routingKey);
        });
        return rabbitTemplate;
    }


    //------------------------------------------------订单

    @Bean
    public DirectExchange orderEx(){
        return new DirectExchange(MQConstants.ORDER_EX,true,false);
    }

    @Bean
    public Queue orderQueue(){
        Queue queue = new Queue(MQConstants.ORDER_QUEUE, true);
        queue.addArgument("x-dead-letter-exchange",MQConstants.ORDER_EX);
        queue.addArgument("x-dead-letter-routing-key",MQConstants.ORDER_DEAD_ROUTE_KEY);
        return queue;
    }

    @Bean
    public Binding orderBinding(){
        return BindingBuilder.bind(orderQueue()).to(orderEx()).with(MQConstants.ORDER_ROUTE_KEY);
    }

    @Bean
    public DirectExchange orderDeadEx(){
        return new DirectExchange(MQConstants.ORDER_DEAD_EX,true,false);
    }

    @Bean
    public Queue orderDeaQueue(){
        return QueueBuilder.durable(MQConstants.ORDER_DEAD_QUEUE).build();
    }

    @Bean
    public Binding orderDeadBinding(){
        return BindingBuilder.bind(orderDeaQueue()).to(orderDeadEx()).with(MQConstants.ORDER_DEAD_ROUTE_KEY);
    }




    //------------------------------------------------支付

    @Bean
    public FanoutExchange payExchange(){
        return new FanoutExchange(MQConstants.PAY_EXCHANGE,true,false);
    }

    @Bean
    public Queue scoreQueue(){
        return QueueBuilder.durable(MQConstants.PAY_QUEUE_SCORE).build();
    }

    @Bean
    public Queue saleQueue(){
        return new Queue(MQConstants.PAY_QUEUE_SALE,true);
    }

    @Bean
    public Binding scoreBinding(){
        return BindingBuilder.bind(scoreQueue()).to(payExchange());
    }

    @Bean
    public Binding saleBinding(){
        return BindingBuilder.bind(saleQueue()).to(payExchange());
    }


    //-------------------------------------------------邮件

    //交换机
    @Bean
    public DirectExchange mailExchange(){
        return new DirectExchange(MQConstants.MAIL_EXCHANGE,true,false);
    }

    //队列
    @Bean
    public Queue mailQueue(){
        return QueueBuilder.durable(MQConstants.MAIL_QUEUE).build();
    }

    //链接
    @Bean
    public Binding mailBinding(){
        return BindingBuilder.bind(mailQueue()).to(mailExchange()).with(MQConstants.MAIL_ROUT_KEY);
    }

}
