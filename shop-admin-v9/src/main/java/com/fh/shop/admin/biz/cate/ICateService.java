package com.fh.shop.admin.biz.cate;

import com.fh.shop.admin.param.CateParam;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.po.cate.Cate;

public interface ICateService {
    ServerResponse findCate();

    ServerResponse addCate(Cate cate);

    ServerResponse findCateById(Long id);

    ServerResponse updateCate(CateParam cateParam);

    ServerResponse deleteCate(String ids);

    ServerResponse findById(Long id);

}
