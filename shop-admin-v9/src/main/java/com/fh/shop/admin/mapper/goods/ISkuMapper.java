package com.fh.shop.admin.mapper.goods;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.po.goods.Sku;

import java.util.List;

public interface ISkuMapper extends BaseMapper<Sku> {

    void addSku(List<Sku> skuList);

    void updateSku(List<Sku> editSkuList);

}
