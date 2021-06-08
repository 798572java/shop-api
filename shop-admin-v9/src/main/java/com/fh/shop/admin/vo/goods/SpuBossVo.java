package com.fh.shop.admin.vo.goods;


import com.fh.shop.po.goods.Sku;
import com.fh.shop.po.goods.Spu;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
public class SpuBossVo implements Serializable {

 private   List<Sku> skuList= new ArrayList<>();

 private Spu spu =new Spu();



}
