package com.fh.shop.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.po.goods.Sku;
import com.fh.shop.vo.CartSkuVo;
import com.fh.shop.vo.SkuEmailVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ISkuMapper extends BaseMapper<Sku> {

    public List<Sku> findStatusList();

    List<SkuEmailVo> selectSkuStock();

    int updateStock(CartSkuVo cartSkuVo);

    int updateSkuStock(@Param("skuId") Long skuId, @Param("count") Long count);

    void updateSales(@Param("skuCount") Long skuCount, @Param("skuId") Long skuId);
}
