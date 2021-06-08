package com.fh.shop.admin.po.brand;

import java.io.Serializable;

public class Brand implements Serializable {

    private Long id;

    private String brandName;

    private String logo;

    private String  oldLogo;

    private Integer createYear;

    public String getOldLogo() {
        return oldLogo;
    }

    public void setOldLogo(String oldLogo) {
        this.oldLogo = oldLogo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getCreateYear() {
        return createYear;
    }

    public void setCreateYear(Integer createYear) {
        this.createYear = createYear;
    }
}
