package com.fh.shop.admin.mapper.spec;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.admin.param.SpecQueryParam;
import com.fh.shop.admin.po.spec.Spec;

import java.util.List;


public interface ISpecMapper extends BaseMapper<Spec> {

    void  addSpec(Spec spec);


    Long findSpecCount(SpecQueryParam specQueryParam);

    List<Spec> findSpecPageList(SpecQueryParam specQueryParam);

    Spec selectSpecById(Long id);

    void updateSpec(Spec spec);

    void deleteSpec(Long id);

    void deleteBatch(List<Long> idsList);

    List<Spec> findInFo();

    List<Spec> findSpecByTypeId(Long typeId);

}
