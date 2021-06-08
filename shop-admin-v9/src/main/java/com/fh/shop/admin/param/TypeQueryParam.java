package com.fh.shop.admin.param;

import com.fh.shop.common.Page;

import java.io.Serializable;

public class TypeQueryParam extends Page implements Serializable {
    private String typeName;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
