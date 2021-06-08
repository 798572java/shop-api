package com.fh.shop.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.DeleteObjectsRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OssUtil {


    // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
    private static String endpoint = "oss-cn-beijing.aliyuncs.com";
    // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
    private static String accessKeyId = "LTAI5tQmeHwXXeGWMfRfQ5bd";
    private static String accessKeySecret = "KFdfboTfMX9fBU6DcCPTQZfEnyNWoc";
    private static String bucketName = "bucket-499";
    private static String url = "https://bucket-499.oss-cn-beijing.aliyuncs.com/";

    public static String fileOss(String fileName,InputStream inputStream) {
        OSS ossClient =null;
        try {
            String s = UUID.randomUUID().toString();
            String suffix = FileUtil.getSuffix(fileName);
            Date date = new Date();
            String dateStr = DateForMat.date2str(date, DateForMat.Date_Str_Y_M_D);
            String ossName=dateStr+"/"+s+suffix;
            // 创建OSSClient实例。
             ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            ossClient.putObject(bucketName, ossName, inputStream);
            String res=url+ossName;
            //System.out.println(bucketUrl+ossName);
            return res;
        } finally {
            if(ossClient !=null){
                ossClient.shutdown();
            }
            if(inputStream !=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public  static void delete(String imageName){

            String image = imageName.replace(url, "");
            String objectName = image;
        OSS ossClient =null;
        try {
             ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            ossClient.deleteObject(bucketName, objectName);
        } finally {
            if(ossClient !=null){
                ossClient.shutdown();
            }
        }

    }

    public static void deleteBatch(List<String> imageNames){
        List<String> filePatchs = imageNames.stream().map(a -> a.replace(url, "")).collect(Collectors.toList());
        OSS ossClient=null;
        try {
            // 创建OSSClient实例。
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            ossClient.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(filePatchs));
            // 关闭OSSClient。
        } finally {
            if(ossClient !=null){
                ossClient.shutdown();
            }
        }
    }


}