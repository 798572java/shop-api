package com.fh.shop.api.pay.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.fh.shop.api.configs.AlipayConfig;
import com.fh.shop.api.pay.biz.IPayService;
import com.fh.shop.common.ServerResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping("/api/pay")
public class PayController {

    @Resource(name = "aliPayService")
    private IPayService payService;

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Value("${pay.return.page}")
    private String returnPage;
    @Value("${pay.return.error.page}")
    private String returnErrorPage;

    //支付
    @PostMapping("aliPay")
    public ServerResponse aliPay(String orderId) {
        return payService.aliPay(orderId);
    }

    //支付第三方调用的notify方法
    @PostMapping("/aliNotify")
    public String aliNotify() {
        //获取支付宝POST过来反馈信息
        Map<String, String[]> requestParams = request.getParameterMap();
        return payService.aliNotify(requestParams);
    }

    //支付第三方调用的return方法
    @GetMapping("/aliReturn")
    public void aliReturn() throws IOException {
        //获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("utf-8"), "utf-8");
            params.put(name, valueStr);
        }

        try {
            boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
            //——请在这里编写您的程序（以下代码仅作参考）——
            if (signVerified) {
                String total_amount = params.get("total_amount");
                response.sendRedirect(returnPage+"?price="+total_amount);
            }else{
                response.sendRedirect(returnErrorPage);
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
