package com.fh.shop.admin.po.type;

import java.io.Serializable;

public class SpecType implements Serializable {

    private Long id;
    private  Long  typeId;
    private  Long  specId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getSpecId() {
        return specId;
    }

    public void setSpecId(Long specId) {
        this.specId = specId;
    }
}
