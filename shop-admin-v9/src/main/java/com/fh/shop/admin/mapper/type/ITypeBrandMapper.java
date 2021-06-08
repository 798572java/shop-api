package com.fh.shop.admin.mapper.type;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.admin.po.type.BrandType;

import java.util.List;

public interface ITypeBrandMapper extends BaseMapper<BrandType> {

    void addBrandType(List<BrandType> brandTypeList);

    List<Long> selectBranTypeById(Long id);

    void deleteBatch(Long id);

    void addBatch(List<BrandType> brandTypeList);

    void deleteBatchIds(List<Long> typeIdList);
}
