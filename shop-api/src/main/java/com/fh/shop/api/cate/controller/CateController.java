package com.fh.shop.api.cate.controller;

import com.fh.shop.api.cate.biz.ICateService;
import com.fh.shop.common.ServerResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@CrossOrigin
@RestController
public class CateController {

    @Resource(name = "cateService")
    private ICateService cateService;



    @RequestMapping(value = "/cates",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse findCate(){
        return  cateService.findCate();
    }


}
