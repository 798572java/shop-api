package com.fh.shop.admin.vo.goods;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class SpuOVo {

    private Long id;

    private String spuName;

    private BigDecimal price;

    private Integer stock;

    private String cates;

    private String brandName;



}
