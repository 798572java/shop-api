package com.fh.shop.api.spec.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.api.spec.po.SpecValue;

import java.util.List;

public interface ISpecValueMapper extends BaseMapper<SpecValue> {


    void addSpecValue(SpecValue specValue);

    List<SpecValue> selectSpecValueBySortId(Long id);

    void deleteSpecValueBySpecId(Long id);

    void deleteBatch(List<Long> idsList);

    void addSpecAllValue(List<SpecValue> specValueList);

}
