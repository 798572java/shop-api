package com.fh.shop.admin.po.type;

import java.io.Serializable;

public class AttrValue implements Serializable {
    private Long id;
    private String attrValue;
    private  Long attrId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }

    public Long getAttrId() {
        return attrId;
    }

    public void setAttrId(Long attrId) {
        this.attrId = attrId;
    }
}
