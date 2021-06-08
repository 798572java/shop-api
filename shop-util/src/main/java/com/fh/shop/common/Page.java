package com.fh.shop.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel
@Data
public class Page implements Serializable {

    @ApiModelProperty(value = "标识",example = "1")
    private  Long  draw;
    @ApiModelProperty(value = "起始下标",example = "1",required = true)
    private  Long   start;
    @ApiModelProperty(value = "每页总条数",example = "10",required = true)
    private  Long    length;

}
