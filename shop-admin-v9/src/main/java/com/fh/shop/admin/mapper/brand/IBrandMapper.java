package com.fh.shop.admin.mapper.brand;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.admin.param.BrandQueryParam;
import com.fh.shop.admin.po.brand.Brand;

import java.util.List;
import java.util.Set;

public interface IBrandMapper extends BaseMapper<Brand> {


    void addbrand(Brand brand);

    Long findListCount(BrandQueryParam brandQueryParam);

    List<Brand> findListPage(BrandQueryParam brandQueryParam);

    void deleteBrand(Long id);


    Brand selectBrandById(Long id);

    void updateBrand(Brand brand);

    List<String> selectListByidList(List<Long> idsList);

    void deleteBatch(List<Long> idsList);

    List<Brand> findInfo();

    List<Brand> selectByTypeId(Long typeId);

    List<Brand> findListByTypeIdList(Set<Long> typeList);

}
