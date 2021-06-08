package com.fh.shop.admin.vo.spec;

import com.fh.shop.admin.po.spec.Spec;
import com.fh.shop.admin.po.spec.SpecValue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SpecVo implements Serializable {

    private  Spec spec=new Spec();

    private List<SpecValue> specValueList=new ArrayList<>();


    public Spec getSpec() {
        return spec;
    }

    public void setSpec(Spec spec) {
        this.spec = spec;
    }

    public List<SpecValue> getSpecValueList() {
        return specValueList;
    }

    public void setSpecValueList(List<SpecValue> specValueList) {
        this.specValueList = specValueList;
    }
}
