package com.fh.shop.admin.param;

import com.fh.shop.po.cate.Cate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CateParam implements Serializable {

    private Cate cate =new Cate();

    private List<Long>  idsList=new ArrayList<>();

   private String ids;


    public Cate getCate() {
        return cate;
    }

    public void setCate(Cate cate) {
        this.cate = cate;
    }

    public List<Long> getIdsList() {
        return idsList;
    }

    public void setIdsList(List<Long> idsList) {
        this.idsList = idsList;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}
