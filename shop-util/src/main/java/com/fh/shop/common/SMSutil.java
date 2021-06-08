package com.fh.shop.common;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SMSutil {
    private static final String URL = "https://api.netease.im/sms/sendcode.action";
    private static final String APPKEY = "d84d04a0e940a8544a0cef2061350043";
    private static final String APPSECRET = "5b73df96fb45";

    public static String getSmS(String phone){
        Map<String,String> headers=new HashMap<>();
        headers.put("AppKey",APPKEY);
        String noce = UUID.randomUUID().toString();
        headers.put("Nonce",noce);
        String curTime = System.currentTimeMillis() + "";
        headers.put("CurTime",curTime);
        String checkSum = CheckSumBuilder.getCheckSum(APPSECRET, noce, curTime);
        headers.put("CheckSum",checkSum);

        Map<String,String> mobile=new HashMap<>();
        mobile.put("mobile",phone);

        String res = HTTPClientUtil.getClient(URL,headers,mobile);
        System.out.println(res);
        return res;
    }
}
