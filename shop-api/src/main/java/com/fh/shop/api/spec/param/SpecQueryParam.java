package com.fh.shop.api.spec.param;

import com.fh.shop.common.Page;

import java.io.Serializable;

public class SpecQueryParam extends Page implements Serializable {

    private  String specName;

    public String getSpecName() {

        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }
}
