package com.fh.shop.admin.biz.cate;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.admin.mapper.cate.ICateMapper;
import com.fh.shop.admin.mapper.type.ITypeMapper;
import com.fh.shop.admin.param.CateParam;
import com.fh.shop.admin.po.type.Type;
import com.fh.shop.admin.vo.cate.CateVo;
import com.fh.shop.common.ResponseEnum;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.po.cate.Cate;
import com.fh.shop.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("cateService")
public class ICateServiceImpl implements ICateService {

    @Autowired
    private ICateMapper cateMapper;
    @Autowired
    private ITypeMapper typeMapper;


    @Override
    public ServerResponse findCate() {
       List<Cate> cateList= cateMapper.findCate();
        return ServerResponse.success(cateList);
    }

    @Override
    public ServerResponse addCate(Cate cate) {
        cateMapper.addCate(cate);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse findCateById(Long id) {
        Cate cate=cateMapper.findCateById(id);
        List<Type> typeList=typeMapper.findAll();
        CateVo cateVo = new CateVo();
        cateVo.setCate(cate);
        cateVo.setTypeList(typeList);
        return ServerResponse.success(cateVo);
    }

    @Override
    public ServerResponse updateCate(CateParam cateParam) {
            //首先获取值
        Cate cate = cateParam.getCate();
        String ids = cateParam.getIds();
        if(StringUtils.isEmpty(ids)){
            //修改分类表
            cateMapper.updateCate(cate);
            return ServerResponse.success();
        }else {
            //修改分类表的子孙
            String[] idsArr = ids.split(",");
            List<Long> idsList=new ArrayList<>();

            for (String s : idsArr) {
                idsList.add(Long.parseLong(s));
            }
            cateParam.setIdsList(idsList);
        }

        cateMapper.updateType(cateParam);

        return ServerResponse.success();
    }

    @Override
    public ServerResponse deleteCate(String ids) {
        if(StringUtils.isEmpty(ids)){
            return  ServerResponse.error(ResponseEnum.CATE_ID_NULL);
        }
        String[] idsArr = ids.split(",");
        List<Long> cateIdList=new ArrayList<>();
        for (String s : idsArr) {
            cateIdList.add(Long.parseLong(s));
        }
        cateMapper.deleteCate(cateIdList);
        RedisUtil.del("cateList");
        return ServerResponse.success();

    }

    @Override
    public ServerResponse findById(Long id) {
        QueryWrapper<Cate> QueryWrapper = new QueryWrapper<>();
        QueryWrapper.eq("fatherId",id);
        List<Cate> cates = cateMapper.selectList(QueryWrapper);
        return ServerResponse.success(cates);
    }
}
