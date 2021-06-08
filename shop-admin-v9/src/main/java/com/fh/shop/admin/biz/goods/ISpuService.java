package com.fh.shop.admin.biz.goods;

import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.admin.param.SpuParam;
import com.fh.shop.admin.param.SpuQueryParam;
import com.fh.shop.admin.param.SpuStatusParam;

public interface ISpuService {
    ServerResponse findspuInfo(Long typeId);

    ServerResponse addSpu(SpuParam spuParam);

    DataTableResult findSpu(SpuQueryParam spuQueryParam);


    ServerResponse deleteSpu(Long id, String realPath);

    ServerResponse selectSpu(Long spuId);

    ServerResponse editSpu(SpuParam spuParam);

    ServerResponse deleteSpuImage(Long key, String realPath);

    ServerResponse deleteBatch(String ids, String realPath);


    ServerResponse updateStatus(SpuStatusParam spuStatusParam);

}
