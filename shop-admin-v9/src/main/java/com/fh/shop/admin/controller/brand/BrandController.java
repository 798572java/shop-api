package com.fh.shop.admin.controller.brand;

import com.fh.shop.admin.annotation.LogInfo;
import com.fh.shop.admin.biz.brand.IBrandService;
import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.admin.controller.BaseController;
import com.fh.shop.admin.param.BrandQueryParam;
import com.fh.shop.admin.po.brand.Brand;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class BrandController extends BaseController {

    @Resource(name="brandService")
    private IBrandService brandService;

    @RequestMapping(value = "brand/toadd",method = RequestMethod.GET)
    public String  toAdd(){

        return "brand/add";
    }

    @RequestMapping(value = "/brand/list",method = RequestMethod.GET)
    public String  list(){

        return "/brand/list";
    }

    @ResponseBody
    @RequestMapping(value = "/brand/findList",method = RequestMethod.POST)
    public DataTableResult findList(BrandQueryParam brandQueryParam){
        return  brandService.findList(brandQueryParam);
    }


    @RequestMapping(value = "/brand/addBrand",method = RequestMethod.POST)
    @LogInfo(info = "品牌添加")
    public String addBrand(Brand brand){
        brandService.addBrand(brand);
        return "/brand/list";
    }

    @RequestMapping(value = "/brand/allHttp",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse allHttp(){

        return  brandService.allHttp();
    }


    @RequestMapping(value = "brand/deleteBrand",method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(info = "品牌删除")
    public ServerResponse deleteBrand(Long id,HttpServletRequest request){
        String realPath = getRealPath("/",request);
        brandService.deleteBrand(id,realPath);
        return ServerResponse.success();
    }

    @RequestMapping(value = "brand/deleteBatch",method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(info = "批量删除")
    public ServerResponse deleteBatch(String  ids,HttpServletRequest request){
        String realPath = getRealPath("/",request);

        return brandService.deleteBatch(ids,realPath);
    }


    @RequestMapping(value = "/brand/toAddBrand",method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(info = "品牌添加")
    public ServerResponse toAddBrand(Brand brand){
        brandService.addBrand(brand);
        return ServerResponse.success();
    }

    @RequestMapping(value = "/brand/selectBrandById",method = RequestMethod.POST)
    @ResponseBody
    public  ServerResponse selectBrandById(Long  id){

        return  brandService.selectBrandById(id);
    }


    @RequestMapping(value = "/brand/updateBrand",method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(info = "更新品牌")
    public ServerResponse updateBrand(Brand brand,HttpServletRequest request){

            String realPath = getRealPath("/",request);

        return  brandService.updateBrand(brand,realPath);
    }

    @RequestMapping(value = "/brand/findBrandAll",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse findBrandAll(Long cateId){
        return brandService.findBrandAll(cateId);
    }



}
