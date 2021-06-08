package com.fh.shop.api.recipients.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class Recipients implements Serializable {

    private Long id;

    private String name;    //收件人

    private String site;    //收件地址

    private String phone;   //电话

    private Long memberId;

    private String status;

}
