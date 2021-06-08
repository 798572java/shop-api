package com.fh.shop.mq;

import lombok.Data;

@Data
public class PayMessage {

    private Long memberId;

    private String totalAmount;

    private String orderId;

}
