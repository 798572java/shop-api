package com.fh.shop.admin.po.type;

import java.io.Serializable;

public class BrandType implements Serializable {
    private  Long id;
    private  Long typeId;
    private  Long  brandId;

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

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }
}
