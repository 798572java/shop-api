package com.fh.shop.admin.biz.spec;

import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ResponseEnum;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.admin.mapper.spec.ISpecMapper;
import com.fh.shop.admin.mapper.spec.ISpecValueMapper;
import com.fh.shop.admin.param.SpecParam;
import com.fh.shop.admin.param.SpecQueryParam;
import com.fh.shop.admin.po.spec.Spec;
import com.fh.shop.admin.po.spec.SpecValue;
import com.fh.shop.admin.vo.spec.SpecVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("specService")
public class ISpecServiceImpl implements ISpecService {

        @Autowired
        private ISpecMapper specMapper;

        @Autowired
        private ISpecValueMapper specValueMapper;


    @Override
    public ServerResponse addSpec(SpecParam specParam) {
        //首先获取传过来的值
        // 黑色，内存
        String specNames = specParam.getSpecNames();
        //1，2
        String specSorts = specParam.getSpecSorts();
        // value="黑色=1,红色=2;8G=1,16G=2,32G=3"
        String specValueAlls = specParam.getSpecValueAlls();
        //判断不能为空
        if(StringUtils.isEmpty(specNames) || StringUtils.isEmpty(specSorts) || StringUtils.isEmpty(specValueAlls)){
            return ServerResponse.error(ResponseEnum.SPEC_ERROR_NULL);
        }
        String[] specNameArr = specNames.split(",");
        String[] specSortArr = specSorts.split(",");
       // ["黑色=1,红色=2","8G=1,16G=2,32G=3"]
        String[] specValueAllArr = specValueAlls.split(";");

        for (int i = 0; i < specNameArr.length; i++) {
            Spec spec = new Spec();
            spec.setSpecName(specNameArr[i]);
            spec.setSort(Integer.parseInt(specSortArr[i]));
            specMapper.addSpec(spec);
            //获取规格值表的外键对应的主键
            Long id = spec.getId();
            //"黑色=1，红色=2"
            String specValueOne = specValueAllArr[i];
            // [黑色,红色]
            String[] specinfo = specValueOne.split(",");
            List<SpecValue> specValueList=new ArrayList<>();
            for (String s : specinfo) {
                //[黑色，1]
                String[] specone = s.split("=");
                String value = specone[0];
                String sort = specone[1];
                SpecValue specValue = new SpecValue();
                specValue.setSpecValue(value);
                specValue.setValueSort(Integer.parseInt(sort));
                specValue.setSpecId(id);
                specValueList.add(specValue);

            }
            specValueMapper.addSpecAllValue(specValueList);
        }


        return ServerResponse.success();
    }

    @Override
    public DataTableResult findList(SpecQueryParam specQueryParam) {
        Long count= specMapper.findSpecCount(specQueryParam);
        List<Spec> specList= specMapper.findSpecPageList(specQueryParam);

        return new DataTableResult(specQueryParam.getDraw(),count,count,specList);
    }

    @Override
    public ServerResponse selectById(Long id) {
        Spec spec= specMapper.selectSpecById(id);
        List<SpecValue> specValueList=specValueMapper.selectSpecValueBySortId(id);
        SpecVo specVo = new SpecVo();
        specVo.setSpec(spec);
        specVo.setSpecValueList(specValueList);
        return ServerResponse.success(specVo);
    }

    @Override
    public ServerResponse updateSpec(SpecParam specParam) {

        Long id = specParam.getId();
        //首先获取传过来的值
        // 黑色
        String specName = specParam.getSpecNames();
        //1
        String specSort = specParam.getSpecSorts();
        // value="黑色=1,红色=2"
        String specValueAll = specParam.getSpecValueAlls();
        //判断不能为空
        if(StringUtils.isEmpty(specName) || StringUtils.isEmpty(specSort) || StringUtils.isEmpty(specValueAll)){
            return ServerResponse.error(ResponseEnum.SPEC_ERROR_NULL);
        }
        //修改规格
        Spec spec = new Spec();
        spec.setId(id);
        spec.setSpecName(specName);
        spec.setSort(Integer.parseInt(specSort));
        specMapper.updateSpec(spec);

        //删除规格值表的对应信息
        specValueMapper.deleteSpecValueBySpecId(id);
        //增加新的信息
        String[] specinfo = specValueAll.split(",");
        List<SpecValue> valueList=new ArrayList<>();
        for (String s : specinfo) {
            //[黑色，1]
            String[] specone = s.split("=");
            String value = specone[0];
            String sort = specone[1];
            SpecValue specValue = new SpecValue();
            specValue.setSpecValue(value);
            specValue.setValueSort(Integer.parseInt(sort));
            specValue.setSpecId(id);
            valueList.add(specValue);
        }
        specValueMapper.addSpecAllValue(valueList);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse deleteSpec(Long id) {
        if (id==null){
            return  ServerResponse.error();
        }
        //删除规格
        specMapper.deleteSpec(id);
        //删除规格值
        specValueMapper.deleteSpecValueBySpecId(id);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse deleteBatch(String ids) {
        //判断ids是否为空
        if(StringUtils.isEmpty(ids)){
            return ServerResponse.error();
        }
        //分割
        String[] idsArr = ids.split(",");
        List<Long> idsList=new ArrayList<>();
        //循环将id放入list中
        for (int i = 0; i < idsArr.length; i++) {
           idsList.add(Long.parseLong(idsArr[i]));
        }
        //删除规格
        specMapper.deleteBatch(idsList);
        //删除指定规格值
        specValueMapper.deleteBatch(idsList);
        return ServerResponse.success();
    }
}
