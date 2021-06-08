package com.fh.shop.admin.mapper.type;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.admin.po.type.Attr;

import java.util.List;

public interface
IAttrMapper extends BaseMapper<Attr> {
    void addAttr(Attr attr);

    List<Attr> selectAttrByTypeId(Long id);

    void deleteAttrById(Long id);

    List<Long> selectAttrByTypeIdLong(Long id);

    List<Long> selectAttrIdByTypeId(List<Long> typeIdList);

    void deleteBatch(List<Long> typeIdList);
}
