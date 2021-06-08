package com.fh.shop.admin.po.type;

import java.io.Serializable;

public class Type implements Serializable {

    private  Long id;
    private  String typeName;

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
}
