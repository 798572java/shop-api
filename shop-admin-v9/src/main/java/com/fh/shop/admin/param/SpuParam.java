package com.fh.shop.admin.param;

import com.fh.shop.po.goods.Spu;
import lombok.Data;

import java.io.Serializable;

@Data
public class SpuParam implements Serializable {
        private Spu spu=new Spu();

        private String stocks;

        private String prices;
      //"20:颜色，3:红色,4:蓝色 ；19:内存，17：28G，19：68G";
        private String  specInfos;

        private String  skuImages;

}
