package com.fh.shop.admin.controller.goods;

import com.fh.shop.admin.biz.goods.ISpuService;
import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.admin.controller.BaseController;
import com.fh.shop.admin.param.SpuParam;
import com.fh.shop.admin.param.SpuQueryParam;
import com.fh.shop.admin.param.SpuStatusParam;
import com.fh.shop.util.RedisUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/spu")
public class SpuController extends BaseController {

    @Resource(name="spuService")
    private ISpuService spuService;

    @RequestMapping(value = "/goodsRedis",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse goodsRedis(){
        RedisUtil.del("skuList");
        return ServerResponse.success();
    }


    @RequestMapping(value = "/toAdd",method = RequestMethod.GET)
    public String toAdd(){
        return "goods/add";
    }

    @RequestMapping(value = "/toList",method = RequestMethod.GET)
    public String toList(){
        return "goods/list";
    }

    @RequestMapping(value = "/findspuInfo",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse findspuInfo(Long typeId){
        return spuService.findspuInfo(typeId);
    }

    @RequestMapping(value = "/addSpu",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addSpu(SpuParam spuParam){
        return  spuService.addSpu(spuParam);
    }


    @RequestMapping(value = "/findSpu",method = RequestMethod.POST)
    @ResponseBody
    public DataTableResult findSpu(SpuQueryParam spuQueryParam){
    return spuService.findSpu(spuQueryParam);
    }

    @RequestMapping(value = "/updateStatus",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateStatus(SpuStatusParam spuStatusParam){
        return spuService.updateStatus(spuStatusParam);
    }


    @RequestMapping(value = "/toEdit",method = RequestMethod.GET)
    public String toEdit(){
        return  "/goods/edit";
    }




    @RequestMapping(value = "/selectSpu",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse selectSpu(Long spuId){
        return spuService.selectSpu(spuId);
    }



    @RequestMapping(value = "/editSpu",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse editSpu(SpuParam spuParam){
        return spuService.editSpu(spuParam);
    }

    @RequestMapping(value = "/deleteSpuImage",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deleteSpuImage(Long key, HttpServletRequest request){
        String realPath = getRealPath("/", request);
        return  spuService.deleteSpuImage(key,realPath);
    }

    @RequestMapping(value = "/deleteSpu",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deleteSpu(Long id,HttpServletRequest request){
        String realPath = getRealPath("/", request);
        return spuService.deleteSpu(id,realPath);
    }

    @RequestMapping(value = "/deleteBatch",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deleteBatch(String ids,HttpServletRequest request){
        String realPath = getRealPath("/", request);
        return spuService.deleteBatch(ids,realPath);
    }



}
