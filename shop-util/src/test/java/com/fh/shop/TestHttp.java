package com.fh.shop;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;

public class TestHttp {

    @Test
    public void test1(){
        //先获取浏览器
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        //在输入url
        HttpGet httpGet = new HttpGet("http://v.qq.com");
        CloseableHttpResponse execute=null;
        FileWriter fileWriter =null;
        try {
            //再进行提交发送请求
             execute = httpClient.execute(httpGet);
            //再获取信息
            HttpEntity entity = execute.getEntity();
            String s = EntityUtils.toString(entity, "utf-8");
            System.out.println(s);
             fileWriter = new FileWriter("d:http.html");
            fileWriter.write(s);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(httpClient!=null){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(execute!=null){
                try {
                    execute.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fileWriter!=null){
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }


}
