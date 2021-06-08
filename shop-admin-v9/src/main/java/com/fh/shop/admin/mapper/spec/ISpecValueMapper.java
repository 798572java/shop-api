package com.fh.shop.admin.mapper.spec;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.admin.po.spec.SpecValue;

import java.util.List;

public interface ISpecValueMapper extends BaseMapper<SpecValue> {


    void addSpecValue(SpecValue specValue);

    List<SpecValue> selectSpecValueBySortId(Long id);

    void deleteSpecValueBySpecId(Long id);

    void deleteBatch(List<Long> idsList);

    void addSpecAllValue(List<SpecValue> specValueList);

}
