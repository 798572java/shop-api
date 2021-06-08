package com.fh.shop.admin.vo.type;

import com.fh.shop.admin.po.brand.Brand;
import com.fh.shop.admin.po.spec.Spec;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TypeVo implements Serializable {

    private List<Brand> brandList=new ArrayList<>();
    private List<Spec> specList=new ArrayList<>();

    public List<Brand> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<Brand> brandList) {
        this.brandList = brandList;
    }

    public List<Spec> getSpecList() {
        return specList;
    }

    public void setSpecList(List<Spec> specList) {
        this.specList = specList;
    }
}
