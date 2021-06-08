package com.fh.shop.admin.biz.goods;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.admin.mapper.brand.IBrandMapper;
import com.fh.shop.admin.mapper.cate.ICateMapper;
import com.fh.shop.admin.mapper.goods.ISkuImageMapper;
import com.fh.shop.admin.mapper.goods.ISkuMapper;
import com.fh.shop.admin.mapper.goods.ISpuMapper;
import com.fh.shop.admin.mapper.spec.ISpecMapper;
import com.fh.shop.admin.mapper.spec.ISpecValueMapper;
import com.fh.shop.admin.mapper.type.IAttrMapper;
import com.fh.shop.admin.mapper.type.IAttrValueMapper;
import com.fh.shop.admin.param.SpuParam;
import com.fh.shop.admin.param.SpuQueryParam;
import com.fh.shop.admin.param.SpuStatusParam;
import com.fh.shop.admin.po.brand.Brand;
import com.fh.shop.admin.po.spec.Spec;
import com.fh.shop.admin.po.spec.SpecValue;
import com.fh.shop.admin.po.type.Attr;
import com.fh.shop.admin.po.type.AttrValue;
import com.fh.shop.admin.vo.goods.*;
import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.po.cate.Cate;
import com.fh.shop.po.goods.Sku;
import com.fh.shop.po.goods.SkuImage;
import com.fh.shop.po.goods.Spu;
import com.fh.shop.util.OssUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service("spuService")
public class ISpuServiceImpl implements ISpuService {

    public static final Spu SPU = new Spu();
    @Autowired
    private IBrandMapper brandMapper;

    @Autowired
    private IAttrMapper attrMapper;
    @Autowired
    private IAttrValueMapper attrValueMapper;
    @Autowired
    private ISpecMapper specMapper;
    @Autowired
    private ISpecValueMapper specValueMapper;
    @Autowired
    private ISpuMapper spuMapper;
    @Autowired
    private ISkuMapper skuMapper;
    @Autowired
    private ISkuImageMapper skuImageMapper;
    @Autowired
    private ICateMapper cateMapper;
    @Override
    public ServerResponse findspuInfo(Long typeId) {
      List<Brand> brandList=  brandMapper.selectByTypeId(typeId);
        //====然后根据我们的类型id去获取spuAttrVoList
        //根据类型id获取attr集合
        QueryWrapper<Attr> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("typeId",typeId);
        List<Attr> attrList = attrMapper.selectList(queryWrapper);
        //将attrList的id提取出来成集合
        List<Long> attrIds = attrList.stream().map(a -> a.getId()).collect(Collectors.toList());
        //通过attrIDS集合获取attrValiue集合
        QueryWrapper<AttrValue> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.in("attrId",attrIds);
        List<AttrValue> attrValueList = attrValueMapper.selectList(queryWrapper1);
        //遍历attr集合转换为spuAttrVoList
        List<SpuAttrVo> spuAttrVoList = attrList.stream().map(a -> {
            SpuAttrVo spuAttrVo = new SpuAttrVo();
            spuAttrVo.setAttrName(a.getAttrName());
            spuAttrVo.setId(a.getId());
            List<AttrValue> collect = attrValueList.stream().filter(y -> y.getAttrId().longValue() == a.getId().longValue()).collect(Collectors.toList());
            spuAttrVo.setAttrValueList(collect);
            return spuAttrVo;
        }).collect(Collectors.toList());

        //====然后根据我们的类型id去获取spuSpecVos
        List<Spec> specList=  specMapper.findSpecByTypeId(typeId);
        List<Long> specids = specList.stream().map(a -> a.getId()).collect(Collectors.toList());
        QueryWrapper<SpecValue> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.in("specId",specids);
        List<SpecValue> specValueList = specValueMapper.selectList(queryWrapper2);
        List<SpuSpecVo> SpuSpecVoList = specList.stream().map(a -> {
            SpuSpecVo spuSpecVo = new SpuSpecVo();
            spuSpecVo.setSpecName(a.getSpecName());
            spuSpecVo.setId(a.getId());
            spuSpecVo.setSpecValueList(specValueList.stream().filter(y-> y.getSpecId().longValue()== a.getId().longValue()).collect(Collectors.toList()));
            return spuSpecVo;
        }).collect(Collectors.toList());

        SpuVo spuVo = new SpuVo();
        spuVo.setBrandList(brandList);
        spuVo.setSpuAttrVoList(spuAttrVoList);
        spuVo.setSpuSpecVos(SpuSpecVoList);
        return ServerResponse.success(spuVo);
    }

    @Override
    public ServerResponse addSpu(SpuParam spuParam) {

        Spu spu = spuParam.getSpu();

        spuMapper.insert(spu);
        Long id = spu.getId();
        String spuName = spu.getSpuName();
        String[] stockArr = spuParam.getStocks().split(",");
        String[] priceArr = spuParam.getPrices().split(",");
        String[] specInfoArr = spuParam.getSpecInfos().split(";");
        //3:/upload/4e8f.JPG/upload/467.JPG;4:/upload/4e8f.JPG/upload/467.JPG
        String[] imageArr = spuParam.getSkuImages().split(";");
        List<Sku> skuList=new ArrayList<>();
        for (int i = 0; i < stockArr.length; i++) {
            Sku sku = new Sku();
            sku.setSpuId(id);
            sku.setStock(Integer.parseInt(stockArr[i]));
            sku.setPrice(new BigDecimal(priceArr[i]));
            sku.setSpecInfo(specInfoArr[i]);
            String[] spec = specInfoArr[i].split(",");
            List<String> specName = Arrays.stream(spec).map(a -> a.split(":")[1]).collect(Collectors.toList());
            //skuName= spuName+sku的规格名
            String skuName=spuName+"  "+StringUtils.join(specName,"  ");
            sku.setSkuName(skuName);

            long colorId = Long.parseLong(specInfoArr[i].split(",")[0].split(":")[0]);
            sku.setColorId(colorId);
            //插入skuimage
            //3:/upload/4e8f.JPG/upload/467.JPG
            String image = Arrays.stream(imageArr).filter(a -> Long.parseLong(a.split("=")[0]) == colorId)
                    .map(s -> s.split("=")[1].split(",")[0]).findFirst().get();
            sku.setImage(image);

            skuList.add(sku);
        }
        if(skuList.size()>0){
            skuMapper.addSku(skuList);
        }
        //插入skuimage的字段
        //3:   /upload/4e8f.JPG,  /upload/467.JPG  ;  4:/upload/4e8f.JPG/upload/467.JPG
        //           0                     1
        List<SkuImage> skuImageList=new ArrayList<>();
        Arrays.stream(imageArr).forEach(a-> {
            String colorId = a.split("=")[0];
            Arrays.stream(a.split("=")[1].split(",")).forEach(x-> {
                SkuImage skuImage = new SkuImage();
                skuImage.setColorId(Long.parseLong(colorId));
                skuImage.setSpuId(id);
                skuImage.setImage(x);
                skuImageList.add(skuImage);
            });
        });
        //批量插入skuimage
        skuImageMapper.addSkuImageBatch(skuImageList);
        return ServerResponse.success();
    }


    @Override
    public DataTableResult findSpu(SpuQueryParam spuQueryParam) {

       Long count= spuMapper.findSpuCount(spuQueryParam);
        List<Spu> spuList=spuMapper.findpageSpuAll(spuQueryParam);
        //组装数据
        //获取skuList
        if(spuList.size()>0){
            List<Long> spuIds = spuList.stream().map(Spu::getId).collect(Collectors.toList());
            QueryWrapper<Sku> skuQueryWrapper = new QueryWrapper<>();
            skuQueryWrapper.in("spuId",spuIds);
            List<Sku> skuList = skuMapper.selectList(skuQueryWrapper);

            List<SpuBossVo> spuBossVoList = spuList.stream().map(a -> {
                SpuBossVo spuBossVo = new SpuBossVo();
                spuBossVo.setSpu(a);
                spuBossVo.setSkuList(skuList.stream().filter(c-> c.getSpuId().longValue()==a.getId()).collect(Collectors.toList()));
                return spuBossVo;
            }).collect(Collectors.toList());
            return  new DataTableResult(spuQueryParam.getDraw(),count,count,spuBossVoList);
        }
        return  new DataTableResult(spuQueryParam.getDraw(),count,count,new ArrayList());

    }

    @Override
    public ServerResponse deleteSpu(Long spuId,String realPath) {
        //删除spu
        spuMapper.deleteById(spuId);
        //删除sku
        QueryWrapper<Sku> skuQueryWrapper = new QueryWrapper<>();
        skuQueryWrapper.eq("spuId",spuId);
        skuMapper.delete(skuQueryWrapper);
        //查询spu的image
        QueryWrapper<SkuImage> skuImageQueryWrapper = new QueryWrapper<>();
        skuImageQueryWrapper.eq("spuId",spuId);
        List<SkuImage> skuImageList = skuImageMapper.selectList(skuImageQueryWrapper);
        //删除硬盘上的图片

        deleteBatchs(skuImageList);

        List<Long> skuimageIds = skuImageList.stream().map(a -> a.getId()).collect(Collectors.toList());
        if(skuimageIds.size()>0){
            skuImageMapper.deleteBatchIds(skuimageIds);
        }
        return ServerResponse.success();
    }

    @Override
    public ServerResponse selectSpu(Long id) {
        //查询 spu数据
        Spu spu = spuMapper.selectById(id);
        //通过cate3 id查询typeId
        Long cate3 = spu.getCate3();
        Cate cate = cateMapper.selectById(cate3);
        Long typeId = cate.getTypeId();

        SpuEditVo spuEditVo = new SpuEditVo();
        spuEditVo.setTypeId(typeId);
        spuEditVo.setSpu(spu);

        QueryWrapper<Sku> skuQueryWrapper = new QueryWrapper<>();
        skuQueryWrapper.eq("spuId",id);
        List<Sku> skuList = skuMapper.selectList(skuQueryWrapper);
        spuEditVo.setSkuList(skuList);

        QueryWrapper<SkuImage> skuImageQueryWrapper = new QueryWrapper<>();
        skuImageQueryWrapper.eq("spuId",id);
        List<SkuImage> skuImageList = skuImageMapper.selectList(skuImageQueryWrapper);

        Set<Long> colorIds = skuImageList.stream().map(x -> x.getColorId()).collect(Collectors.toSet());

        List<SpecValue> specValueList = specValueMapper.selectBatchIds(colorIds);
        List<SkuImageVo> skuImageVoList = specValueList.stream().map(x -> {
            SkuImageVo skuImageVo = new SkuImageVo();
            skuImageVo.setColorId(x.getId());
            skuImageVo.setColorName(x.getSpecValue());
            skuImageVo.setSkuImageList(skuImageList.stream().filter(a -> a.getColorId().longValue() == x.getId().longValue()).collect(Collectors.toList()));
            return skuImageVo;
        }).collect(Collectors.toList());

            if(specValueList.size()>0){
                spuEditVo.setSkuImageList(skuImageVoList);
            }
        return ServerResponse.success(spuEditVo);
    }

    @Override
    public ServerResponse editSpu(SpuParam spuParam) {
        //修改sup的
        Spu spu = spuParam.getSpu();
        String spuName = spu.getSpuName();
        Long spuId = spu.getId();
        spuMapper.updateById(spu);
        String[] imageArr = spuParam.getSkuImages().split(";");
        //查询所有的sku
        QueryWrapper<Sku> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("spuId",spuId);
        List<Sku> skuList = skuMapper.selectList(queryWrapper1);
        //更新sku
        //-1_70:蓝色;67_71:绿色;68_72:黄色
        //69_70:蓝色,13:32G,51:骁龙654;  70_70:蓝色,13:32G,52:晓龙856;  71_71:绿色,13:32G,51:骁龙654; 72_71:绿色,13:32G,52:晓龙856;-1_72:黄色,13:32G,51:骁龙654;-1_72:黄色,13:32G,52:晓龙856
        String[] skuSpecArr = spuParam.getSpecInfos().split(";");
        String[] priceArr = spuParam.getPrices().split(",");
        String[] stockArr = spuParam.getStocks().split(",");
        List<Sku> addSkuList=new ArrayList<>();
        List<Long> editSkuList=new ArrayList<>();
        for (int i = 0; i < skuSpecArr.length; i++) {
            Long skuId= Long.valueOf(skuSpecArr[i].split("_")[0]);
            //准备Sku数据
            int stock = Integer.parseInt(stockArr[i]);
            String price = priceArr[i];
            String specinfo = skuSpecArr[i].split("_")[1];
            long colorId = Long.parseLong(skuSpecArr[i].split(",")[0].split(":")[0].split("_")[1]);
            String image = Arrays.stream(imageArr).filter(a -> Long.parseLong(a.split("=")[0]) == colorId)
                                   .map(d -> d.split("=")[1].split(",")[0]).findFirst().get();

            String[] spec = skuSpecArr[i].split("_")[1].split(",");
            List<String> specName = Arrays.stream(spec).map(a -> a.split(":")[1]).collect(Collectors.toList());
            //skuName= spuName+sku的规格名
            String skuName=spuName+"  "+StringUtils.join(specName,"  ");



            if(skuId==-1){
                Sku sku = getSku(spuId, stock, price, specinfo, colorId, image, skuName);

                addSkuList.add(sku);

            }else {
                Sku sku = getSku(spuId, stock, price, specinfo, colorId, image, skuName);

                sku.setId(skuId);
                editSkuList.add(skuId);
                skuMapper.updateById(sku);
            }
        }
        if(addSkuList.size()>0){
            skuMapper.addSku(addSkuList);
        }

        ////
        //图片信息70:/upload/35f1b580-6bcd-4ca8-9d45-3dbeb562aa1b.JPG,/upload/bd64741d-2a88-4dc4-a3e0-7c6945ce5308.JPG;
        // 71:/upload/faddf9ac-64a9-4ca2-bfca-40ce1300b30a.JPG,/upload/8c2f3f08-2d31-4c0e-ac26-6721e7949475.JPG;
        //先删除相关图片
        QueryWrapper<SkuImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("spuId",spuId);
        skuImageMapper.delete(queryWrapper);
        

        //与后台传过来的sku相对比如果没有则从集合中移除掉
        List<Long> deleteIdList = skuList.stream().filter(x -> !editSkuList.contains(x.getId())).map(y -> y.getId()).collect(Collectors.toList());
        if(deleteIdList.size()>0){
            skuMapper.deleteBatchIds(deleteIdList);
        }

        List<SkuImage> skuImageList=new ArrayList<>();
        Arrays.stream(imageArr).forEach(a-> {
            String colorId = a.split("=")[0];
            Arrays.stream(a.split("=")[1].split(",")).forEach(x-> {
                SkuImage skuImage = new SkuImage();
                skuImage.setColorId(Long.parseLong(colorId));
                skuImage.setSpuId(spuId);
                skuImage.setImage(x);
                skuImageList.add(skuImage);
            });
        });
        //批量插入skuimage
        skuImageMapper.addSkuImageBatch(skuImageList);

        return ServerResponse.success();
    }



    @Override
    public ServerResponse deleteSpuImage(Long key, String realPath) {
        SkuImage skuImage = skuImageMapper.selectById(key);
        String image = skuImage.getImage();
        OssUtil.delete(image);

        skuImageMapper.deleteById(key);
        return ServerResponse.success(image);
    }

    @Override
    public ServerResponse deleteBatch(String ids, String realPath) {
        if(StringUtils.isEmpty(ids)){
            return ServerResponse.error();
        }
        //转换为集合
        String[] spuIds = ids.split(",");
        List<Long> spuidsList = Arrays.stream(spuIds).map(a -> Long.parseLong(a)).collect(Collectors.toList());
        //批量删除spu
            spuMapper.deleteBatchIds(spuidsList);
        //删除spu对应的sku
        QueryWrapper<Sku> skuQueryWrapper = new QueryWrapper<>();
        skuQueryWrapper.in("spuId",spuidsList);
        skuMapper.delete(skuQueryWrapper);
        //查询skuimage

        QueryWrapper<SkuImage> skuImageQueryWrapper = new QueryWrapper<>();
        skuImageQueryWrapper.in("spuId",spuidsList);
        List<SkuImage> skuImageList = skuImageMapper.selectList(skuImageQueryWrapper);
//        //批量删除skuimage硬盘上的图片
//        skuImageList.stream().forEach(a->{
//            File file = new File(realPath+a.getImage());
//            if(file.exists()){
//                file.delete();
//            }
//        });

        deleteBatchs(skuImageList);
        List<Long> skuImageIds = skuImageList.stream().map(a -> a.getId()).collect(Collectors.toList());
        //批量删除skuimage数据库的
        if(skuImageIds.size()>0){
            skuImageMapper.deleteBatchIds(skuImageIds);
        }
        return ServerResponse.success();
    }


    public void deleteBatchs( List<SkuImage> skuImageList){
        List<String> skuimages = skuImageList.stream().map(a -> a.getImage()).collect(Collectors.toList());
        OssUtil.deleteBatch(skuimages);
    }

    @Override
    public ServerResponse updateStatus(SpuStatusParam spuStatusParam) {
        Long spuId = spuStatusParam.getSpuId();
        String flag = spuStatusParam.getFlag();
        String status = spuStatusParam.getStatus();
        Spu spu = new Spu();
        spu.setId(spuId);
        if(flag.equals("status")){
            spu.setStatus(status);
        }else if(flag.equals("newOld")){
            spu.setNewOld(status);
        }else if(flag.equals("hot")){
            spu.setHot(status);
        }
        spuMapper.updateById(spu);
        //修改spu状态的同时要修改sku状态

        Sku sku = new Sku();
        if(flag.equals("status")){
            sku.setStatus(status);
        }else if(flag.equals("newOld")){
            sku.setNewOld(status);
        }else if(flag.equals("hot")){
            sku.setHot(status);
        }
        //查询spu关联的多个sku
//        QueryWrapper<Sku> queryWrapper1 = new QueryWrapper<>();
//        queryWrapper1.eq("spuId",spuId);
//        List<Sku> skuList = skuMapper.selectList(queryWrapper1);
//        List<Long> skuId = skuList.stream().map(x -> x.getSpuId()).collect(Collectors.toList());

        //批量修改spu下的多个sku
        QueryWrapper<Sku> updateWrapper = new QueryWrapper<>();
        updateWrapper.eq("spuId",spuId);
        skuMapper.update(sku, updateWrapper);

        return ServerResponse.success();
    }


    private Sku getSku(Long spuId, int stock, String price, String specinfo, long colorId, String image, String skuName) {
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        sku.setPrice(new BigDecimal(price));
        sku.setSpecInfo(specinfo);
        sku.setStock(stock);
        sku.setColorId(colorId);
        sku.setImage(image);
        sku.setSkuName(skuName);
        return sku;
    }
}
