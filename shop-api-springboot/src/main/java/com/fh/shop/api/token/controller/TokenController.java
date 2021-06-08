package com.fh.shop.api.token.controller;

import com.fh.shop.api.annotations.Check;
import com.fh.shop.api.token.biz.ITokenService;
import com.fh.shop.common.ServerResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/token")
public class TokenController {

    @Resource(name = "tokenService")
    private ITokenService tokenService;

    @Check
    @PostMapping("/createToken")
    public ServerResponse createToken(){
        return tokenService.createToken();
    }

}
