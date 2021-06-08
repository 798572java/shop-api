package com.fh.shop.admin.param;

import com.fh.shop.common.Page;

import java.io.Serializable;

public class BrandQueryParam extends Page implements Serializable {
    private   String  brandName;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
