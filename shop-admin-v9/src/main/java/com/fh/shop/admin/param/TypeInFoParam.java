package com.fh.shop.admin.param;

import com.fh.shop.admin.po.type.BrandType;
import com.fh.shop.admin.po.type.SpecType;
import com.fh.shop.admin.po.type.Type;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Data
public class TypeInFoParam implements Serializable {

    private Type type=new Type();
    // [1,2,3,4]
    private List<BrandType>  brandTypeList=new ArrayList<>();
    //[2,5,7,8]
    private List<SpecType> specTypeList =new ArrayList<>();
    // {attr:"颜色",  attrInfoParamList:[{attrValue:"红色"},{attrValue:"蓝色"},{attrValue:"绿色"}]},
    private List<AttrInfoParam> attrInfoParamList=new ArrayList<>();


}
