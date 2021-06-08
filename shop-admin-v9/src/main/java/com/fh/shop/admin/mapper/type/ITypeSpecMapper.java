package com.fh.shop.admin.mapper.type;

import com.fh.shop.admin.po.type.SpecType;

import java.util.List;

public interface ITypeSpecMapper {

    void addSpecType(List<SpecType> specTypeList);

    List<Long> selectSpecTypeById(Long id);

    void deleteBatch(Long id);

    void addBatch(List<SpecType> specTypeList);

    void deleteBatchIds(List<Long> typeIdList);
}
