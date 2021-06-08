package com.fh.shop.api.biz.order;

import com.fh.shop.common.ServerResponse;

public interface IOrderInfoService {

    ServerResponse cancelOrder(String id);

}
