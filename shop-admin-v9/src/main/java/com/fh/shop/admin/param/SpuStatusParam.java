package com.fh.shop.admin.param;

import lombok.Data;

import java.io.Serializable;

@Data
public class SpuStatusParam implements Serializable {

    private Long spuId;

    private String status;

    private String flag;
}
