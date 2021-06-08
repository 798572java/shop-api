package com.fh.shop.api.spec.param;

import com.fh.shop.api.spec.po.Spec;
import com.fh.shop.api.spec.po.SpecValue;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class SpecInFoParam implements Serializable {


    private Spec spec=new Spec();

    private List<SpecValue> specValueList=new ArrayList<>();

}
