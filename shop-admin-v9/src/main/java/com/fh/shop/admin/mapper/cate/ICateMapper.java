package com.fh.shop.admin.mapper.cate;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.admin.param.CateParam;
import com.fh.shop.po.cate.Cate;

import java.util.List;

public interface ICateMapper extends BaseMapper<Cate> {
    List<Cate> findCate();

    void addCate(Cate cate);


    Cate findCateById(Long id);

    void updateCate(Cate cate);

    void updateType(CateParam cateParam);

    void deleteCate(List<Long> cateIdList);
}
