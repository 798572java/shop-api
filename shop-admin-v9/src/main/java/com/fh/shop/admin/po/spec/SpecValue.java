package com.fh.shop.admin.po.spec;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("t_specvalue")
public class SpecValue {

    private Long id;

    private String specValue;

    private Long specId;

    private int valueSort;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpecValue() {
        return specValue;
    }

    public void setSpecValue(String specValue) {
        this.specValue = specValue;
    }

    public Long getSpecId() {
        return specId;
    }

    public void setSpecId(Long specId) {
        this.specId = specId;
    }

    public int getValueSort() {
        return valueSort;
    }

    public void setValueSort(int valueSort) {
        this.valueSort = valueSort;
    }
}
