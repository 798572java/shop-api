package com.fh.shop.po;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderItem implements Serializable {

    private Long id;

    private Long memberId;

    private Long skuId;

    private String orderId;

    private String skuName;

    private BigDecimal skuPrice;

    private String skuImage;

    private Long skuCount;

    private BigDecimal subPrice;


}
