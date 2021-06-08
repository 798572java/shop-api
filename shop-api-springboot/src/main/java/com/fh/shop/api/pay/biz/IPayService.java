package com.fh.shop.api.pay.biz;

import com.fh.shop.common.ServerResponse;

import java.util.Map;

public interface IPayService {

    public ServerResponse aliPay(String orderId);

    String aliNotify(Map<String, String[]> requestParams);
}
