package com.fh.shop.admin.mapper.goods;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.admin.param.SpuQueryParam;
import com.fh.shop.po.goods.Spu;

import java.util.List;

public interface ISpuMapper extends BaseMapper<Spu> {
    Long findSpuCount(SpuQueryParam spuQueryParam);

    List<Spu> findpageSpuAll(SpuQueryParam spuQueryParam);
}
