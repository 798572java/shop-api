package com.fh.shop.admin.mapper.type;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.admin.param.TypeQueryParam;
import com.fh.shop.admin.po.type.Type;

import java.util.List;

public interface ITypeMapper extends BaseMapper<Type> {
    void addType(Type type);

    Long selectTypeCount(TypeQueryParam typeQueryParam);

    List<Type> selectTypeList(TypeQueryParam typeQueryParam);

    Type selectTypeById(Long id);

    void updateTypeName(Type type);

    void deleteType(Long id);

    void deleteBatchIds(List<Long> typeIdList);

    List<Type> findAll();
}
