package com.fh.shop.admin.vo.goods;

import com.fh.shop.admin.po.type.AttrValue;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class SpuAttrVo implements Serializable {

    private Long id;

    private String attrName;

    private List<AttrValue> attrValueList=new ArrayList<>();
}
