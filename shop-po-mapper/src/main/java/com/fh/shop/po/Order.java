package com.fh.shop.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Order implements Serializable {

    @TableId(type = IdType.INPUT)
    private String  id;

    private Long memberId;

    private BigDecimal totalPrice;

    private Long totalCount;

    private int status;

    private Date createTime;

    private Date sendTime;

    private Date payTime;

    private Date receiveTime;

    private String recipientName;

    private String recipientSite;

    private String recipientPhone;

    private int payType;


}
