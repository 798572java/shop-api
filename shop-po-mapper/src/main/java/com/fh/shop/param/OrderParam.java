package com.fh.shop.param;

import com.fh.shop.common.Page;
import lombok.Data;

import java.io.Serializable;

@Data
//@ApiModel
public class OrderParam extends Page implements Serializable {

//    @ApiModelProperty(hidden = true)
    private Long memberId;

    private Integer payType;

    private Long recipientId;

}
