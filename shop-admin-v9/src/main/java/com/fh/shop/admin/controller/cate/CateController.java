package com.fh.shop.admin.controller.cate;

import com.fh.shop.admin.biz.cate.ICateService;
import com.fh.shop.admin.param.CateParam;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.po.cate.Cate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


@Controller
@RequestMapping("/cate")
public class CateController {

    @Resource(name = "cateService")
    private ICateService cateService;

    @RequestMapping(value = "/toIndex",method = RequestMethod.GET)
    public String toIndex(){
        return "/cate/index";
    }

    @RequestMapping(value = "/findCate",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse findCate(){
        return  cateService.findCate();
    }

    @RequestMapping(value = "/addCate",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addCate(Cate cate){
        return  cateService.addCate(cate);
    }


    @RequestMapping(value = "/findCateById",method = RequestMethod.POST)
    @ResponseBody
    public  ServerResponse findCateById(Long id){
        return  cateService.findCateById(id);
    }


    @RequestMapping(value = "/updateCate",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateCate(CateParam cateParam){
        return  cateService.updateCate(cateParam);
    }


    @RequestMapping(value = "/deleteCate",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deleteCate(String ids){
      return cateService.deleteCate(ids);
    }

    @RequestMapping(value = "/findById",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse findById(Long id){
        return cateService.findById(id);
    }

}
