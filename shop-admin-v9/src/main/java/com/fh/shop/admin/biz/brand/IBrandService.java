package com.fh.shop.admin.biz.brand;


import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.admin.param.BrandQueryParam;
import com.fh.shop.admin.po.brand.Brand;


public interface IBrandService {
    void addBrand(Brand brand);

    DataTableResult findList(BrandQueryParam brandQueryParam);

    void deleteBrand(Long id, String realPath);


    ServerResponse selectBrandById(Long id);

    ServerResponse updateBrand(Brand brand, String realPath);

    ServerResponse deleteBatch(String ids, String realPath);

    ServerResponse findBrandAll(Long cateId);


    ServerResponse allHttp();

}
