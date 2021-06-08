package com.fh.shop.api.order.controller;

import com.baomidou.mybatisplus.core.conditions.interfaces.Func;
import com.fh.shop.api.annotations.Check;
import com.fh.shop.api.annotations.Token;
import com.fh.shop.api.biz.order.IOrderInfoService;
import com.fh.shop.api.common.SecretLogin;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.order.biz.IOrderService;
import com.fh.shop.api.order.param.OrderParam;
import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ServerResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@Api(tags = "订单接口")
public class OrderController {

    @Resource(name = "orderService")
    private IOrderService orderService;

    @Autowired
    private HttpServletRequest request;

    @Resource(name = "orderInfoService")
    private IOrderInfoService orderInfoService;

    @Check
    @Token
    @PostMapping("/addOrder")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-auth",value = "请求头",paramType = "header",required = true,dataType = "java.lang.String"),
    })
    @ApiOperation("新增订单数据")
    public ServerResponse addOrder(OrderParam orderParam){
        MemberVo memberVo = (MemberVo) request.getAttribute(SecretLogin.MEMVO);
        Long memberId = memberVo.getId();
        orderParam.setMemberId(memberId);
        return orderService.addOrder(orderParam);
    }

    @Check
    @PostMapping("/findOrder")
    @ApiOperation("查询订单数据")
    public ServerResponse findOrder(){
        MemberVo memberVo = (MemberVo) request.getAttribute(SecretLogin.MEMVO);
        Long memberId = memberVo.getId();
        return orderService.findOrder(memberId);
    }

    @Check
    @PostMapping("/cancelOrder/{id}")
    @ApiOperation("取消订单")
    public ServerResponse cancelOrder(@PathVariable String id){
        return orderInfoService.cancelOrder(id);
    }


}
