package com.fh.shop.admin.biz.type;

import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.admin.mapper.brand.IBrandMapper;
import com.fh.shop.admin.mapper.spec.ISpecMapper;
import com.fh.shop.admin.mapper.type.*;
import com.fh.shop.admin.param.TypeInFoParam;
import com.fh.shop.admin.param.TypeParam;
import com.fh.shop.admin.param.TypeQueryParam;
import com.fh.shop.admin.po.brand.Brand;
import com.fh.shop.admin.po.spec.Spec;
import com.fh.shop.admin.po.type.*;
import com.fh.shop.admin.vo.type.EditTypeVo;
import com.fh.shop.admin.vo.type.TypeVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("typeService")
public class ITypeServiceImpl implements ITypeService {

    @Autowired
    private IBrandMapper brandMapper;

    @Autowired
    private ISpecMapper specMapper;
    @Autowired
    private ITypeSpecMapper typeSpecMapper;
    @Autowired
    private ITypeBrandMapper typeBrandMapper;
    @Autowired
    private ITypeMapper typeMapper;
    @Autowired
    private IAttrMapper attrMapper;
    @Autowired
    private IAttrValueMapper attrValueMapper;

    @Override
    public ServerResponse findInFo() {
       List<Brand> brandList= brandMapper.findInfo();
      List<Spec> specList=   specMapper.findInFo();
        TypeVo typeVo = new TypeVo();
        typeVo.setBrandList(brandList);
        typeVo.setSpecList(specList);
        return ServerResponse.success(typeVo);
    }

    @Override
    public ServerResponse addType(TypeParam typeParam) {








//        //先获取
//        String typeName = typeParam.getTypeName();
//        String brandIds = typeParam.getBrandIds();
//        String specIds = typeParam.getSpecIds();
//        String attrNameStr = typeParam.getAttrNameStr();
//        String attrValueStr = typeParam.getAttrValueStr();
//        if(StringUtils.isEmpty(typeName) || StringUtils.isEmpty(brandIds)
//                                         || StringUtils.isEmpty(specIds)
//                                         || StringUtils.isEmpty(attrNameStr)
//                                         || StringUtils.isEmpty(attrValueStr)){
//                return ServerResponse.error();
//        }
//        //先插入类型
//        Type type = new Type();
//        type.setTypeName(typeName);
//        typeMapper.addType(type);
//        Long id = type.getId();
//        //再新增品牌类型
//        String[] brandIdsArr = brandIds.split(",");
//        List<BrandType> brandTypeList=new ArrayList<>();
//        for (String brandId : brandIdsArr) {
//            BrandType brandType = new BrandType();
//            brandType.setTypeId(id);
//            brandType.setBrandId(Long.parseLong(brandId));
//            brandTypeList.add(brandType);
//        }
//        typeBrandMapper.addBrandType(brandTypeList);
//        //新增规格类型
//        String[] specIdsArr = specIds.split(",");
//        List<SpecType> specTypeList=new ArrayList<>();
//        for (String specId : specIdsArr) {
//            SpecType specType = new SpecType();
//            specType.setTypeId(id);
//            specType.setSpecId(Long.parseLong(specId));
//            specTypeList.add(specType);
//        }
//        typeSpecMapper.addSpecType(specTypeList);
//        //新增属性
//        String[] attrNameArr = attrNameStr.split(",");
//        String[] attrValueArr = attrValueStr.split(";");
//        int count=0;
//        for (String attrName : attrNameArr) {
//            Attr attr = new Attr();
//            attr.setAttrName(attrName);
//            attr.setTypeId(id);
//            attrMapper.addAttr(attr);
//            Long attrId = attr.getId();
//            String[] attrValueArrStr = attrValueArr[count++].split(",");
//            List<AttrValue> attrValuesList=new ArrayList<>();
//            for (String attrValue : attrValueArrStr) {
//                AttrValue attrValue1 = new AttrValue();
//                attrValue1.setAttrValue(attrValue);
//                attrValue1.setAttrId(attrId);
//                attrValuesList.add(attrValue1);
//            }
//            attrValueMapper.addBatch(attrValuesList);
//
//        }
        return ServerResponse.success();
    }

    @Override
    public DataTableResult findType(TypeQueryParam typeQueryParam) {
       Long count= typeMapper.selectTypeCount(typeQueryParam);
        List<Type> typeList=typeMapper.selectTypeList(typeQueryParam);

        return new DataTableResult(typeQueryParam.getDraw(),count,count,typeList);
    }

    @Override
    public ServerResponse selectByTypeId(Long id) {
        //通过id先查询类型
        Type type=typeMapper.selectTypeById(id);
        //通过类型id查询类型品牌表
       List<Long> brandTypeidsList=typeBrandMapper.selectBranTypeById(id);
        //通过类型id查询类型规格表
        List<Long> specTypeidsList=typeSpecMapper.selectSpecTypeById(id);
        //查询所有品牌，规格
        List<Brand> brandList= brandMapper.findInfo();
        List<Spec> specList=   specMapper.findInFo();
        //通过类型id查询出属性数据
        List<Attr>attrList=attrMapper.selectAttrByTypeId(id);

        List<Long> idList=new ArrayList<>();
        for (Attr attr : attrList) {
            idList.add(attr.getId());
        }
       List<AttrValue>attrValueList= attrValueMapper.selectAttrValue(idList);

        //组装数据
        EditTypeVo editTypeVo = new EditTypeVo();
        editTypeVo.setType(type);
        editTypeVo.setSpecList(specList);
        editTypeVo.setSpecTypeidsList(specTypeidsList);
        editTypeVo.setBrandList(brandList);
        editTypeVo.setBrandTypeidsList(brandTypeidsList);
        editTypeVo.setAttrList(attrList);
        editTypeVo.setAttrValueList(attrValueList);
        return ServerResponse.success(editTypeVo);
    }

    @Override
    public ServerResponse updateType(TypeParam typeParam) {
        //获取数据
        Long id = typeParam.getId();
        String typeName = typeParam.getTypeName();
        String specIds = typeParam.getSpecIds();
        String brandIds = typeParam.getBrandIds();
        String attrStrArrUpdate = typeParam.getAttrStrArr();
        String attrValueArrUpdate = typeParam.getAttrValueArr();
        //非空
        if(StringUtils.isEmpty(typeName) || StringUtils.isEmpty(specIds)
                                         || StringUtils.isEmpty(brandIds)
                                         || StringUtils.isEmpty(attrStrArrUpdate)
                                         ||  StringUtils.isEmpty(attrValueArrUpdate)){
            return  ServerResponse.error();
        }
        //修改类型
        Type type = new Type();
        type.setId(id);
        type.setTypeName(typeName);
        typeMapper.updateTypeName(type);
        //删除品牌类型
        typeBrandMapper.deleteBatch(id);
        //插入新数据品牌类型
        String[] brandIdsArr = brandIds.split(",");
        List<BrandType> brandTypeList=new ArrayList<>();
        for (String brandId : brandIdsArr) {
            BrandType brandType = new BrandType();
            brandType.setTypeId(id);
            brandType.setBrandId(Long.parseLong(brandId));
            brandTypeList.add(brandType);
        }
        typeBrandMapper.addBatch(brandTypeList);
        //删除规格
        typeSpecMapper.deleteBatch(id);
        //插入规格
        String[] specIdsArr = specIds.split(",");
        List<SpecType> specTypeList=new ArrayList<>();
        for (String  specId: specIdsArr) {
            SpecType specType = new SpecType();
            specType.setTypeId(id);
            specType.setSpecId(Long.parseLong(specId));
            specTypeList.add(specType);
        }

        typeSpecMapper.addBatch(specTypeList);
        //根据id查询属性id
        List<Long> attrIds=attrMapper.selectAttrByTypeIdLong(id);
        //先根据类型id删除属性
        attrMapper.deleteAttrById(id);
        //根据属性id删除属性值
        attrValueMapper.deleteBatch(attrIds);
        //再插入属性的数据
        String[] attrArr = attrStrArrUpdate.split(",");
        String[] attrValueUpdate = attrValueArrUpdate.split(";");
        int count=0;
        for (String attrName : attrArr) {
            Attr attr = new Attr();
            attr.setAttrName(attrName);
            attr.setTypeId(id);

            attrMapper.addAttr(attr);
            Long attrId = attr.getId();
            String[] attrValueArrStr = attrValueUpdate[count++].split(",");
            List<AttrValue> attrValuesList=new ArrayList<>();
            for (String attrValue : attrValueArrStr) {
                AttrValue attrValue1 = new AttrValue();
                attrValue1.setAttrValue(attrValue);
                attrValue1.setAttrId(attrId);
                attrValuesList.add(attrValue1);
            }
            attrValueMapper.addBatch(attrValuesList);


        }


        return ServerResponse.success();
    }

    @Override
    public ServerResponse deleteType(Long id) {
        //首先删除类型的数据
        typeMapper.deleteType(id);
        //删除类型品牌
        typeBrandMapper.deleteBatch(id);
        //删除类型规格
        typeSpecMapper.deleteBatch(id);
        //查询属性id
        List<Attr> attrList = attrMapper.selectAttrByTypeId(id);
            List<Long> attrIds=new ArrayList<>();
                for (Attr attr : attrList) {
                    attrIds.add(attr.getId());
                }
         //  删除属性
        attrMapper.deleteAttrById(id);
         //删除属性值
        attrValueMapper.deleteBatch(attrIds);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse deleteBatchIds(String ids) {
            if(StringUtils.isEmpty(ids)){
                return ServerResponse.error();
            }
        String[] typeIdsArr = ids.split(",");
            List<Long> typeIdList=new ArrayList<>();
        for (String typeId : typeIdsArr) {
            typeIdList.add(Long.parseLong(typeId));
        }
        typeMapper.deleteBatchIds(typeIdList);
        typeBrandMapper.deleteBatchIds(typeIdList);
        typeSpecMapper.deleteBatchIds(typeIdList);
        //通过类型id查询多个属性id
       List<Long> attrIds= attrMapper.selectAttrIdByTypeId(typeIdList);
        //通过属性id集合删除属性值
        attrValueMapper.deleteBatchByAttrList(attrIds);
        //删除属性
        attrMapper.deleteBatch(typeIdList);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse findAll() {

       List<Type> typeList= typeMapper.findAll();

        return ServerResponse.success(typeList);
    }

    @Override
    public ServerResponse add(TypeInFoParam typeInFoParam) {
        //新增type
        Type type = typeInFoParam.getType();
        typeMapper.insert(type);
        Long id = type.getId();
        //新增 brandType表  [2,3,4,5]

        List<BrandType> brandTypeList = typeInFoParam.getBrandTypeList();
        brandTypeList.forEach(a->a.setTypeId(id));
        typeBrandMapper.addBatch(brandTypeList);
        //新增specType表 [1,2,3,4]
        List<SpecType> specTypeList = typeInFoParam.getSpecTypeList();
        specTypeList.forEach(a->a.setTypeId(id));
        typeSpecMapper.addBatch(specTypeList);
        //新增属性表
        //attrInfoParamList:[
        //	  {attr:"颜色",attrInfoParamList:[{attrValue:"红色"},{attrValue:"蓝色"},{attrValue:"绿色"}]},
        //	  {attr:"颜色",attrInfoParamList:[{attrValue:"红色"},{attrValue:"蓝色"},{attrValue:"绿色"}]},
        //	  {attr:"颜色",attrInfoParamList:[{attrValue:"红色"},{attrValue:"蓝色"},{attrValue:"绿色"}]}
        //	                ]}
        typeInFoParam.getAttrInfoParamList().stream().forEach(a-> {
            Attr attr = a.getAttr();
            attr.setTypeId(id);
            attrMapper.addAttr(attr);
            List<AttrValue> attrValueList = a.getAttrValueList();
            attrValueList.stream().forEach(x->x.setAttrId(attr.getId()));
            attrValueMapper.addBatch(attrValueList);
        });
        return ServerResponse.success();
    }
}
