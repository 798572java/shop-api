package com.fh.shop.admin.vo.type;

import com.fh.shop.admin.po.brand.Brand;
import com.fh.shop.admin.po.spec.Spec;
import com.fh.shop.admin.po.type.Attr;
import com.fh.shop.admin.po.type.AttrValue;
import com.fh.shop.admin.po.type.Type;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EditTypeVo implements Serializable {

    private List<Brand> brandList=new ArrayList<>();
    private List<Spec> specList=new ArrayList<>();

    private Type type=new Type();

    private List<Long> brandTypeidsList=new ArrayList<>();
    private  List<Long> specTypeidsList=new ArrayList<>();

    private List<Attr> attrList=new ArrayList<>();

    private  List<AttrValue> attrValueList=new ArrayList<>();

    public List<Attr> getAttrList() {
        return attrList;
    }

    public void setAttrList(List<Attr> attrList) {
        this.attrList = attrList;
    }

    public List<AttrValue> getAttrValueList() {
        return attrValueList;
    }

    public void setAttrValueList(List<AttrValue> attrValueList) {
        this.attrValueList = attrValueList;
    }

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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Long> getBrandTypeidsList() {
        return brandTypeidsList;
    }

    public void setBrandTypeidsList(List<Long> brandTypeidsList) {
        this.brandTypeidsList = brandTypeidsList;
    }

    public List<Long> getSpecTypeidsList() {
        return specTypeidsList;
    }

    public void setSpecTypeidsList(List<Long> specTypeidsList) {
        this.specTypeidsList = specTypeidsList;
    }
}
