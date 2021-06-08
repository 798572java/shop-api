package com.fh.shop.admin.controller.type;

import com.fh.shop.admin.biz.type.ITypeService;
import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.admin.param.TypeInFoParam;
import com.fh.shop.admin.param.TypeParam;
import com.fh.shop.admin.param.TypeQueryParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/type")
public class TypeController {

    @Resource(name = "typeService")
    private ITypeService typeService;


    @RequestMapping(value = "/findInFo",method =RequestMethod.GET)
    @ResponseBody
    public ServerResponse  findInFo(){
        return typeService.findInFo();
    }

    @RequestMapping(value = "/toAdd",method =RequestMethod.GET)
    public String  toAdd(){
        return  "/type/add";
    }

    @RequestMapping(value = "/toList",method =RequestMethod.GET)
    public String toList(){
        return  "/type/list";
    }

    @RequestMapping(value = "/addType",method =RequestMethod.POST)
    @ResponseBody
    public ServerResponse addType(TypeParam typeParam){
       return typeService.addType(typeParam);
    }


    @RequestMapping(value = "/add",method =RequestMethod.POST)
    @ResponseBody
    public ServerResponse add(@RequestBody TypeInFoParam typeInFoParam){
        return  typeService.add(typeInFoParam);
    }




    @RequestMapping(value = "/findType",method =RequestMethod.POST)
    @ResponseBody
    public DataTableResult findType(TypeQueryParam typeQueryParam){
        return typeService.findType(typeQueryParam);
    }
    @RequestMapping(value = "/toEdit",method =RequestMethod.GET)
    public String toEdit(){
        return "/type/update";
    }

    @RequestMapping(value = "/selectByTypeId",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse selectByTypeId(Long id){
        return typeService.selectByTypeId(id);
    }

    @RequestMapping(value = "/editType",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateType(TypeParam typeParam){
        return typeService.updateType(typeParam); 
    };


    @RequestMapping(value = "/deleteType",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deleteType(Long id){
        return  typeService.deleteType(id);
    }

    @RequestMapping(value = "/deleteBatchIds",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deleteBatchIds(String ids){
        return  typeService.deleteBatchIds(ids);
    }

    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse findAll(){
        return  typeService.findAll();
    }


}
