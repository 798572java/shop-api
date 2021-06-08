package com.fh.shop.admin.param;

import com.fh.shop.common.Page;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SpuQueryParam extends Page implements Serializable {

    private  String brandIds;

    private String spuName;

    private BigDecimal priceMin;

    private BigDecimal priceMax;

    private Integer StockMin;

    private Integer StockMax;

    private Long cate1;

    private Long cate2;

    private Long cate3;
}
