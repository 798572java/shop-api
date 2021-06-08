package com.fh.shop.common;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HTTPClientUtil {

    public static  String  getClient(String url, Map<String,String> headers,Map<String,String> mobile){

        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        if(headers !=null && headers.size()>0){
            headers.forEach((x,y)-> httpPost.addHeader(x,y));
        }

        CloseableHttpResponse response=null;
        try {
            if(mobile!=null && mobile.size()>0){
                List<BasicNameValuePair> mobileList=new ArrayList<>();
                mobile.forEach((x,y)-> mobileList.add(new BasicNameValuePair(x,y)));
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(mobileList);
                httpPost.setEntity(urlEncodedFormEntity);
            }
             response = client.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String res = EntityUtils.toString(entity);
            return res;
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



}
