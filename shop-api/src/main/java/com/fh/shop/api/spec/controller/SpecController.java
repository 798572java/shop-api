package com.fh.shop.api.spec.controller;


import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.api.spec.biz.ISpecService;
import com.fh.shop.api.spec.param.SpecInFoParam;
import com.fh.shop.api.spec.param.SpecParam;
import com.fh.shop.api.spec.param.SpecQueryParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/specs")
public class SpecController {

        @Resource(name = "specService")
        private ISpecService specService;

        @RequestMapping(value = "/findList",method = RequestMethod.POST)
        public DataTableResult findList(SpecQueryParam specQueryParam){
                return  specService.findList(specQueryParam);
        }


        @RequestMapping(value = "/addSpec",method = RequestMethod.POST)
        public ServerResponse addSpec(SpecParam specParam){
            return  specService.addSpec(specParam);
        }

        @RequestMapping(value = "/add",method = RequestMethod.POST)
        public ServerResponse add(@RequestBody  List<SpecInFoParam> specInFoParamList){
                return  specService.add(specInFoParamList);
        }

        @RequestMapping(value = "/selectById",method = RequestMethod.POST)
        public ServerResponse selectById(Long id){
                return  specService.selectById(id);
        }

        @RequestMapping(value = "/updateSpec",method = RequestMethod.POST)
        public ServerResponse updateSpec(SpecParam specParam){
                return specService.updateSpec(specParam);
        }

        @RequestMapping(value = "/deleteSpec",method = RequestMethod.POST)
        public ServerResponse deleteSpec(Long id){
                return specService.deleteSpec(id);
        }


        @RequestMapping(value = "/deleteBatch",method = RequestMethod.POST)
        public ServerResponse deleteBatch(String  ids){
                return  specService.deleteBatch(ids);
        }





}
