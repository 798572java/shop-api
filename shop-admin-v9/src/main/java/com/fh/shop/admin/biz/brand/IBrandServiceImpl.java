package com.fh.shop.admin.biz.brand;

import com.fh.shop.admin.mapper.brand.IBrandMapper;
import com.fh.shop.admin.mapper.cate.ICateMapper;
import com.fh.shop.admin.param.BrandQueryParam;
import com.fh.shop.admin.po.brand.Brand;
import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.po.cate.Cate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("brandService")
public class IBrandServiceImpl implements IBrandService {

    @Autowired
    private IBrandMapper brandMapper;
    @Autowired
    private ICateMapper cateMapper;

    public void addBrand(Brand brand) {
        brandMapper.addbrand(brand);
    }

    @Override
    public DataTableResult findList(BrandQueryParam brandQueryParam) {
            //业务逻辑
        //获取总条数
       Long count= brandMapper.findListCount(brandQueryParam);
       //获取分页后数据
        List<Brand> brandList=brandMapper.findListPage(brandQueryParam);

        //将数据放到构造函数中返回
        return new DataTableResult(brandQueryParam.getDraw(),count,count,brandList);
    }

    @Override
    public void deleteBrand(Long id,String realPath) {
        Brand brand = brandMapper.selectBrandById(id);
        String logo = brand.getLogo();
        String s = realPath + logo;
        File file = new File(s);
            if(file.exists()){
                file.delete();
            }
        brandMapper.deleteBrand(id);
    }



    @Override
    public ServerResponse selectBrandById(Long id) {

       Brand brand= brandMapper.selectBrandById(id);

        return ServerResponse.success(brand);
    }

    @Override
    public ServerResponse updateBrand(Brand brand,String realPath) {
        String logo = brand.getLogo();
        if (StringUtils.isNotEmpty(logo)){

            String oldLogo = brand.getOldLogo();
            String s = realPath + oldLogo;
            File file = new File(s);
            if (file.exists()){
                file.delete();
            }
        }else {
            brand.setLogo(brand.getOldLogo());
        }
        brandMapper.updateBrand(brand);
        return  ServerResponse.success();
    }

    @Override
    public ServerResponse deleteBatch(String ids, String realPath) {
        if(StringUtils.isNotEmpty(ids)){
            //将字符串分割为数组
            String[] idsArr = ids.split(",");
            List<Long> idsList=new ArrayList<>();
            for (String s : idsArr) {
                idsList.add(Long.parseLong(s));
            }
         List<String> longList=   brandMapper.selectListByidList(idsList);
            for (String logo : longList) {
              String logos  =realPath+logo;
                File file = new File(logos);
                if(file.exists()){
                    file.delete();
                }
            }
            brandMapper.deleteBatch(idsList);
            return ServerResponse.success();
        }
        return ServerResponse.error();
    }



    @Override
    public ServerResponse findBrandAll(Long cateId) {
        if(cateId==null){
            List<Brand> brandLsit = brandMapper.findInfo();
            return ServerResponse.success(brandLsit);
        }else {
            //查询所有的分类
            List<Cate> cateList = cateMapper.selectList(null);
            Set<Long> typeList = new HashSet<>();
            getLeaf(cateId, cateList, typeList);
            //获取出当前分类下叶子的类型
            if(typeList.size()>0){
                //通过类型id，查询出对应的brandList  两表联查
                List<Brand> brandList = brandMapper.findListByTypeIdList(typeList);
                return ServerResponse.success(brandList);
            }else {
                Cate cateById = cateMapper.findCateById(cateId);
                Long typeId = cateById.getTypeId();
                List<Brand> brandList = brandMapper.selectByTypeId(typeId);
                return ServerResponse.success(brandList);
            }
        }
    }

    @Override
    public ServerResponse allHttp() {
        List<Brand> brandList = brandMapper.selectList(null);
        return ServerResponse.success(brandList);
    }


    private void  getLeaf(Long cateId, List<Cate> cateList, Set<Long> typeList){
            for (Cate cate : cateList) {
                if(cateId==cate.getFatherId()){
                    if(cate.getTypeId()!=null && isLeft(cate.getId(), cateList)){
                        typeList.add(cate.getTypeId());
                    }
                    getLeaf(cate.getId(),cateList,typeList);
                }
            }
        }

       private Boolean isLeft(Long cateId,List<Cate> cateList){
           for (Cate cate : cateList) {
                if(cateId==cate.getFatherId()){
                 return  false;
                }
           }
           return  true;
       }






}
