package com.fh.shop;

import com.fh.shop.common.CheckSumBuilder;
import com.fh.shop.common.SMSutil;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class TestSMS {
    @Test
    public void test1(){
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost("https://api.netease.im/sms/sendcode.action");
        httpPost.setHeader("AppKey","ccdf5ba43a6de9793857e1ed85c16dc4");
        String noce = UUID.randomUUID().toString();
        httpPost.setHeader("Nonce",noce);
        String curTime = System.currentTimeMillis() + "";
        httpPost.setHeader("CurTime",curTime);
        String checkSum = CheckSumBuilder.getCheckSum("798c1cf890e2", noce, curTime);
        httpPost.setHeader("CheckSum",checkSum);

        CloseableHttpResponse response=null;
        try {
            List<NameValuePair> nameValuePairs=new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("mobile","13253879073"));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs,"utf-8");
            httpPost.setEntity(entity);
            //发送请求
             response = client.execute(httpPost);
            String res = EntityUtils.toString(response.getEntity(),"utf-8");
            System.out.println(res);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if(response!=null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(httpPost !=null){
                httpPost.releaseConnection();
            }
            if(client !=null){
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void test2(){
        SMSutil.getSmS("13253879073");
    }

}
