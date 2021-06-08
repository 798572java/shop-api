package com.fh.shop.api.order.biz;

import com.fh.shop.api.order.param.OrderParam;
import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ServerResponse;

public interface IOrderService {

    ServerResponse addOrder(OrderParam orderParam);

    ServerResponse findOrder(Long memberId);

}
