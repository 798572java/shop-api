package com.fh.shop.admin.param;

import com.fh.shop.admin.po.type.Attr;
import com.fh.shop.admin.po.type.AttrValue;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class AttrInfoParam implements Serializable {

    private Attr attr=new Attr();

    private List<AttrValue> attrValueList=new ArrayList<>();

}
