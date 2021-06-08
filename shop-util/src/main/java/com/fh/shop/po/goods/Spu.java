package com.fh.shop.po.goods;



import java.io.Serializable;
import java.math.BigDecimal;


public class Spu implements Serializable {
    private Long id;

    private String spuName;

    private BigDecimal price;

    private Integer stock;

    private Long cate1;

    private Long cate2;

    private Long cate3;

    private Long brandId;
   // 属性 “16:cpu型号, 18:晓龙879; 20:屏幕大小,40:20尺寸”
    private String attrInfo;

    private String specInfo;

    private String brandName;

    private String cateName;

    private String status;

    private String  newOld;

    private  String  hot;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpuName() {
        return spuName;
    }

    public void setSpuName(String spuName) {
        this.spuName = spuName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Long getCate1() {
        return cate1;
    }

    public void setCate1(Long cate1) {
        this.cate1 = cate1;
    }

    public Long getCate2() {
        return cate2;
    }

    public void setCate2(Long cate2) {
        this.cate2 = cate2;
    }

    public Long getCate3() {
        return cate3;
    }

    public void setCate3(Long cate3) {
        this.cate3 = cate3;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getAttrInfo() {
        return attrInfo;
    }

    public void setAttrInfo(String attrInfo) {
        this.attrInfo = attrInfo;
    }

    public String getSpecInfo() {
        return specInfo;
    }

    public void setSpecInfo(String specInfo) {
        this.specInfo = specInfo;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNewOld() {
        return newOld;
    }

    public void setNewOld(String newOld) {
        this.newOld = newOld;
    }

    public String getHot() {
        return hot;
    }

    public void setHot(String hot) {
        this.hot = hot;
    }
}
