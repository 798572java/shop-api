package com.fh.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.param.OrderParam;
import com.fh.shop.po.Order;

import java.util.List;

public interface IOrderMapper extends BaseMapper<Order> {
    Long fineOrderCount(OrderParam orderParam);

    List<Order> findOrderList(OrderParam orderParam);

}
