package com.fh.shop.api.goods.controller;

import com.fh.shop.common.ServerResponse;
import com.fh.shop.api.goods.biz.ISkuService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@CrossOrigin
public class SkuController {

    @Resource(name = "skuService")
    private ISkuService skuService;

        @GetMapping("/skus/status")
        public ServerResponse findStatusList(){
            return skuService.findStatusList();
        }



}
