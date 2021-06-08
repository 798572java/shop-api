package com.fh.shop.admin.biz.type;

import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.admin.param.TypeInFoParam;
import com.fh.shop.admin.param.TypeParam;
import com.fh.shop.admin.param.TypeQueryParam;

public interface ITypeService {
    ServerResponse findInFo();

    ServerResponse addType(TypeParam typeParam);

    DataTableResult findType(TypeQueryParam typeQueryParam);

    ServerResponse selectByTypeId(Long id);

    ServerResponse updateType(TypeParam typeParam);

    ServerResponse deleteType(Long id);

    ServerResponse deleteBatchIds(String ids);

    ServerResponse findAll();

    ServerResponse add(TypeInFoParam typeInFoParam);

}
