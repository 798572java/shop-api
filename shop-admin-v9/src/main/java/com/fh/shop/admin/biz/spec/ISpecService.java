package com.fh.shop.admin.biz.spec;

import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.admin.param.SpecParam;
import com.fh.shop.admin.param.SpecQueryParam;

public interface ISpecService {


    ServerResponse addSpec(SpecParam specParam);

    DataTableResult findList(SpecQueryParam specQueryParam);

    ServerResponse selectById(Long id);

    ServerResponse updateSpec(SpecParam specParam);

    ServerResponse deleteSpec(Long id);

    ServerResponse deleteBatch(String ids);

}
