package com.fh.shop.api.recipients.controller;

import com.fh.shop.api.annotations.Check;
import com.fh.shop.api.common.SecretLogin;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.recipients.biz.IRecipientsService;
import com.fh.shop.api.recipients.po.Recipients;
import com.fh.shop.common.ServerResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/recipients")
@Api(tags = "收件人接口")
public class RecipientsController {

    @Autowired
    private HttpServletRequest request;

    @Resource(name = "recipientService")
    private IRecipientsService recipientsService;

    @Check
    @PostMapping("/addRecipient")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-auth",value = "请求头",paramType = "header",required = true,dataType = "java.lang.String"),
    })
    @ApiOperation("添加收件人")
    public ServerResponse addRecipient(Recipients recipients){
        MemberVo memberVo = (MemberVo) request.getAttribute(SecretLogin.MEMVO);
        Long memberId = memberVo.getId();
        recipients.setMemberId(memberId);
        return recipientsService.addRecipient(recipients,memberId);
    }

    @Check
    @GetMapping("/findList")
    public ServerResponse findList(){
        MemberVo memberVo = (MemberVo) request.getAttribute(SecretLogin.MEMVO);
        Long memberId = memberVo.getId();
        return recipientsService.findList(memberId);
    }

    @Check
    @PostMapping("/updateStatus")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-auth",value = "请求头",paramType = "header",required = true,dataType = "java.lang.String"),
    })
    @ApiOperation("修改状态")
    public ServerResponse updateStatus(Long id){
        MemberVo memberVo = (MemberVo) request.getAttribute(SecretLogin.MEMVO);
        Long memberId = memberVo.getId();
        return recipientsService.updateStatus(id,memberId);
    }

}
