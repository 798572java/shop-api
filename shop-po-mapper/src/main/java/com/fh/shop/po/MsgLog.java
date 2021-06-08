package com.fh.shop.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MsgLog implements Serializable {

    @TableId(type = IdType.INPUT)
    private String msgId;

    private String exchange;

    private String routeKey;

    private String message;

    private int status;

    private Date retryTime;

    private int retryCount;

    private Date insertTime;

    private Date updateTime;

}
