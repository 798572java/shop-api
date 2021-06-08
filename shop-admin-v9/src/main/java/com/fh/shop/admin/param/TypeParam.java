package com.fh.shop.admin.param;

import java.io.Serializable;

public class TypeParam implements Serializable {
    private Long id;

    //类型名字
    private  String typeName;
    //12,13,13 规格id集合
    private  String  specIds;
    // 品牌id集合
    private  String  brandIds;
    //
    private String attrNameStr;

    private  String attrValueStr;

    private String  attrStrArr;

    private String attrValueArr;


    public String getAttrStrArr() {
        return attrStrArr;
    }

    public void setAttrStrArr(String attrStrArr) {
        this.attrStrArr = attrStrArr;
    }

    public String getAttrValueArr() {
        return attrValueArr;
    }

    public void setAttrValueArr(String attrValueArr) {
        this.attrValueArr = attrValueArr;
    }

    public String getAttrNameStr() {
        return attrNameStr;
    }

    public void setAttrNameStr(String attrNameStr) {
        this.attrNameStr = attrNameStr;
    }

    public String getAttrValueStr() {
        return attrValueStr;
    }

    public void setAttrValueStr(String attrValueStr) {
        this.attrValueStr = attrValueStr;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getSpecIds() {
        return specIds;
    }

    public void setSpecIds(String specIds) {
        this.specIds = specIds;
    }

    public String getBrandIds() {
        return brandIds;
    }

    public void setBrandIds(String brandIds) {
        this.brandIds = brandIds;
    }
}
