package com.fh.shop.mq;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.mapper.IMemberMapper;
import com.fh.shop.mapper.IOrderItemMapper;
import com.fh.shop.mapper.ISkuMapper;
import com.fh.shop.po.OrderItem;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PayConsumer {

    @Autowired
    private IMemberMapper memberMapper;

    @Autowired
    private IOrderItemMapper orderItemMapper;

    @Autowired
    private ISkuMapper skuMapper;

    @RabbitListener(queues = MQConstants.PAY_QUEUE_SCORE)
    public void handleScoreMessage(String msg){
        PayMessage payMessage = JSON.parseObject(msg, PayMessage.class);
        Long memberId = payMessage.getMemberId();
        //向上取整
        double score = Math.floor(Double.parseDouble(payMessage.getTotalAmount()));
    memberMapper.updateScore(score,memberId);
    }

    @RabbitListener(queues = MQConstants.PAY_QUEUE_SALE)
    public void handleSaleMessage(String msg){
        PayMessage payMessage = JSON.parseObject(msg, PayMessage.class);
        String orderId = payMessage.getOrderId();
        //加销量
        QueryWrapper<OrderItem> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("orderId",orderId);
            List<OrderItem> orderItems = orderItemMapper.selectList(queryWrapper);
            orderItems.stream().forEach(x -> {
                Long skuCount = x.getSkuCount();
                Long skuId = x.getSkuId();
                skuMapper.updateSales(skuCount,skuId);
        });
    }

}
