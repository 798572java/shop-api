package com.fh.shop.api.spec.biz;

import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.api.spec.param.SpecInFoParam;
import com.fh.shop.api.spec.param.SpecParam;
import com.fh.shop.api.spec.param.SpecQueryParam;

import java.util.List;

public interface ISpecService {


    ServerResponse addSpec(SpecParam specParam);

    DataTableResult findList(SpecQueryParam specQueryParam);

    ServerResponse selectById(Long id);

    ServerResponse updateSpec(SpecParam specParam);

    ServerResponse deleteSpec(Long id);

    ServerResponse deleteBatch(String ids);

    ServerResponse add(List<SpecInFoParam> specInFoParamList);



}
