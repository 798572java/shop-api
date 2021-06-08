package com.fh.shop.admin.controller.spec;

import com.fh.shop.admin.biz.spec.ISpecService;
import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.admin.param.SpecParam;
import com.fh.shop.admin.param.SpecQueryParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/spec")
public class SpecController {

        @Resource(name = "specService")
        private ISpecService specService;

        @RequestMapping(value = "/toAdd",method = RequestMethod.GET)
        public String  toAddSpec(){
            return  "/spec/add";
        }


        @RequestMapping(value = "/toUpdate",method = RequestMethod.GET)
        public String toUpdate(){
                return  "/spec/update";
        }

        @RequestMapping(value = "/toList",method = RequestMethod.GET)
        public String toList(){
                return  "/spec/list";
        }

        @RequestMapping(value = "/findList",method = RequestMethod.POST)
        @ResponseBody
        public DataTableResult findList(SpecQueryParam specQueryParam){
                return  specService.findList(specQueryParam);
        }


        @RequestMapping(value = "/addSpec",method = RequestMethod.POST)
        @ResponseBody
        public ServerResponse addSpec(SpecParam specParam){
            return  specService.addSpec(specParam);
        }


        @RequestMapping(value = "/selectById",method = RequestMethod.POST)
        @ResponseBody
        public ServerResponse selectById(Long id){

                return  specService.selectById(id);
        }

        @RequestMapping(value = "/updateSpec",method = RequestMethod.POST)
        @ResponseBody
        public ServerResponse updateSpec(SpecParam specParam){
                return specService.updateSpec(specParam);
        }

        @RequestMapping(value = "/deleteSpec",method = RequestMethod.POST)
        @ResponseBody
        public ServerResponse deleteSpec(Long id){

                return specService.deleteSpec(id);
        }


        @RequestMapping(value = "/deleteBatch",method = RequestMethod.POST)
        @ResponseBody
        public ServerResponse deleteBatch(String  ids){
                return  specService.deleteBatch(ids);
        }

}
