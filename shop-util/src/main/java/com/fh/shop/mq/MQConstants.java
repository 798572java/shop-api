package com.fh.shop.mq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConstants {

   //--------------------------------邮件

   public static final String MAIL_EXCHANGE="mail_exchange";
   public static final String MAIL_QUEUE="mail_queue";
   public static final String MAIL_ROUT_KEY="mail_rout_key";

   //--------------------------------支付家积分销量

   public static final String PAY_EXCHANGE="pay_exchange";
   public static final String PAY_QUEUE_SCORE="pay_queue_score";
   public static final String PAY_QUEUE_SALE="pay_queue_sale";

    //-------------------------------订单

    public static final String ORDER_EX = "order_ex";
    public static final String ORDER_QUEUE = "order_queue";
    public static final String ORDER_ROUTE_KEY = "order_route_key";

    //-------------------------------死信队列

    public static final String ORDER_DEAD_EX = "order_dead_ex";
    public static final String ORDER_DEAD_QUEUE = "order_dead_queue";
    public static final String ORDER_DEAD_ROUTE_KEY = "order_dead_route_key";


}
