package com.fh.shop.api.spec.param;

import java.io.Serializable;

public class SpecParam implements Serializable {

    private  Long  id;
    private  String   specNames;
    private  String  specSorts;
    private  String  specValueAlls;

    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpecNames() {
        return specNames;
    }

    public void setSpecNames(String specNames) {
        this.specNames = specNames;
    }

    public String getSpecSorts() {
        return specSorts;
    }

    public void setSpecSorts(String specSorts) {
        this.specSorts = specSorts;
    }

    public String getSpecValueAlls() {
        return specValueAlls;
    }

    public void setSpecValueAlls(String specValueAlls) {
        this.specValueAlls = specValueAlls;
    }
}
