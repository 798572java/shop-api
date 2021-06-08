package com.fh.shop.admin.mapper.type;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.admin.po.type.AttrValue;

import java.util.List;

public interface IAttrValueMapper extends BaseMapper<AttrValue> {
    void addBatch(List<AttrValue> attrValuesList);

    List<AttrValue> selectAttrValue(List<Long> idList);

    void deleteBatch(List<Long> attrIds);

    void deleteBatchByAttrList(List<Long> attrIds);

}
