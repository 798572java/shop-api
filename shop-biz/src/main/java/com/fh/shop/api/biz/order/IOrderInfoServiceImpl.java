package com.fh.shop.api.biz.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.common.ResponseEnum;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.common.SystemConstant;
import com.fh.shop.mapper.IOrderItemMapper;
import com.fh.shop.mapper.IOrderMapper;
import com.fh.shop.mapper.ISkuMapper;
import com.fh.shop.po.Order;
import com.fh.shop.po.OrderItem;
import com.fh.shop.po.goods.Sku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("orderInfoService")
public class IOrderInfoServiceImpl implements IOrderInfoService {

    @Autowired
    private IOrderItemMapper orderItemMapper;

    @Autowired
    private IOrderMapper orderMapper;

    @Autowired
    private ISkuMapper skuMapper;

    @Override
    public ServerResponse cancelOrder(String id) {
        //取消订单
            //------------------------------------------------------------取消订单start

            Order orderInfo = orderMapper.selectById(id);
            //判断订单状态
            if(orderInfo.getStatus() != SystemConstant.ORDER_STATUS.WAIT_PAY){
                return ServerResponse.error(ResponseEnum.ORDER_STATUS_IS_ERROR);
            }
            //更新订单状态
            Order order = new Order();
            order.setId(id);
            order.setStatus(SystemConstant.ORDER_STATUS.TRADE_CLOSE);
            orderMapper.updateById(order);
            //更新库存（归还）
            QueryWrapper<OrderItem> orderItemQueryWrapper = new QueryWrapper<>();
            orderItemQueryWrapper.eq("orderId",id);
            List<OrderItem> orderItems = orderItemMapper.selectList(orderItemQueryWrapper);
            orderItems.stream().forEach(x -> {
                Long skuId = x.getSkuId();
                Long skuCount = x.getSkuCount();
                Sku sku = new Sku();
                sku.setId(skuId);
                skuMapper.updateSkuStock(skuId,skuCount);
            });
            return ServerResponse.success();

            //------------------------------------------------------------取消订单end
        }
    }

