package com.fh.shop.api.order.biz;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fh.shop.api.cart.vo.CartVo;
import com.fh.shop.api.common.KeyUtil;
import com.fh.shop.api.exception.ShopException;
import com.fh.shop.api.mq.OrderProducer;
import com.fh.shop.mapper.IOrderMapper;
import com.fh.shop.api.order.param.OrderParam;
import com.fh.shop.po.Order;
import com.fh.shop.api.recipients.mapper.IRecipientsMapper;
import com.fh.shop.api.recipients.po.Recipients;
import com.fh.shop.common.ResponseEnum;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.common.SystemConstant;
import com.fh.shop.mapper.IOrderItemMapper;
import com.fh.shop.mapper.ISkuMapper;
import com.fh.shop.po.OrderItem;
import com.fh.shop.po.goods.Sku;
import com.fh.shop.util.RedisUtil;
import com.fh.shop.vo.CartSkuVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("orderService")
@Transactional(rollbackFor = Exception.class)
public class IOrderServiceImpl implements IOrderService {

    @Autowired
    private IOrderMapper orderMapper;

    @Autowired
    private IOrderItemMapper orderItemMapper;

    @Autowired
    private IRecipientsMapper recipientsMapper;

    @Autowired
    private ISkuMapper skuMapper;

    @Autowired
    private OrderProducer orderProducer;

    //插入订单
    @Override
    public ServerResponse addOrder(OrderParam orderParam) {
        //------------------------------------------------------------插入订单start

        Long recipientId = orderParam.getRecipientId();
        //先判断有没有收件人
        if(recipientId == null){
            return ServerResponse.error(ResponseEnum.ORDER_RECIPIENT_IS_NULL);
        }
        Long memberId = orderParam.getMemberId();
        //先减库存sku数据，一旦有商品不足，就不能下单。防止超卖

        //根据会员id获取redis中取出来的购物车数据
        String cartJson = RedisUtil.hget(KeyUtil.buildCartKey(memberId), SystemConstant.FIELD);
        //判断是否有购物车
        if(StringUtils.isEmpty(cartJson)) {
                return ServerResponse.error(ResponseEnum.ORDER_CART_IS_NULL);
        }
        //购物车数据
        CartVo cartVo = JSON.parseObject(cartJson, CartVo.class);
        List<CartSkuVo> cartSkuVoList = cartVo.getCartVoList();
        for (CartSkuVo cartSkuVo : cartSkuVoList) {
            //先查询数据库的库存量
            Sku sku = skuMapper.selectById(cartSkuVo.getSkuId());
            //判断sku表中的库存数量是否大于购买数量,有一个不足就抛出异常进行回滚
            if(sku.getStock() < cartSkuVo.getCount()){
                throw new ShopException(ResponseEnum.ORDER_STOCK_IS_DEFICIENCY);
            }
            //更新数据库
            //update t_sku set stock=stock-count where id = skuId;
            //获取执行的SQL语句的次数，判断执行次数是否==0，如果等于0则说明是那个没有执行SQL语句，直接抛异常
            int count = skuMapper.updateStock(cartSkuVo);
            if(count == 0){
                throw new ShopException(ResponseEnum.ORDER_STOCK_IS_DEFICIENCY);
            }
        }
        //插入订单表
        Order order = new Order();
        //使用雪花算法生成唯一id
        String orderId = IdWorker.getIdStr();
        order.setId(orderId);
        order.setCreateTime(new Date());
        order.setMemberId(memberId);
        order.setPayType(1);
        order.setStatus(SystemConstant.ORDER_STATUS.WAIT_PAY);
        order.setTotalCount(cartVo.getCartCount());
        order.setTotalPrice(new BigDecimal(cartVo.getSumPrice()));
        Recipients recipients = recipientsMapper.selectById(recipientId);
        order.setRecipientName(recipients.getName());
        order.setRecipientPhone(recipients.getPhone());
        order.setRecipientSite(recipients.getSite());
        orderMapper.insert(order);
        //插入订单明细表
        List<OrderItem> orderItemList = new ArrayList<>();
        cartSkuVoList.stream().forEach(x -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(orderId);
            orderItem.setMemberId(memberId);
            orderItem.setSkuCount(x.getCount());
            orderItem.setSkuId(x.getSkuId());
            orderItem.setSkuImage(x.getImage());
            orderItem.setSkuName(x.getSkuName());
            orderItem.setSkuPrice(new BigDecimal(x.getPrice()));
            orderItem.setSubPrice(new BigDecimal(x.getSubPrice()));
            orderItemList.add(orderItem);
        });
        //判断订单详情表是否为空
        if(orderItemList.size() > 0){
            //批量插入
            orderItemMapper.batchInsert(orderItemList);
        }
        //加入完购物车把redis中的缓存清除
        RedisUtil.del(KeyUtil.buildCartKey(memberId));

        //发消息到消息中间件
        orderProducer.sendOrderMsg(orderId,30*1000+"");
        //------------------------------------------------------------插入订单end
        return ServerResponse.success();
    }

    //查询订单
    @Override
    public ServerResponse findOrder(Long memberId) {

        //------------------------------------------------------------查询订单start
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("memberId",memberId);
        List<Order> orders = orderMapper.selectList(orderQueryWrapper);
        return ServerResponse.success(orders);

        //------------------------------------------------------------查询订单end

    }





}
