import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.aliyun.oss.model.PutObjectResult;
import com.fh.shop.util.DateCalUtil;
import com.fh.shop.util.DateForMat;
import com.fh.shop.util.FileUtil;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TestOss {
    @Test
    public void oss(){
        String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
        String accessKeyId = "LTAI5t8YJaJeKj4qsRrAnDyR";
        String accessKeySecret = "9lzLbYvROdvUq6VvepTsJkTLE5UGIp";
        String bucket="xjsczmm";
        InputStream inputStream1 =null;
        try {

            String fileName="E:\\图片\\IMG_0626.JPG";
            inputStream1 = new FileInputStream(fileName);
            String s = UUID.randomUUID().toString();
            String suffix = FileUtil.getSuffix(fileName);
            Date date = new Date();
            String dateStr = DateForMat.date2str(date, DateForMat.Date_Str_Y_M_D);
            String ossName=dateStr+"/"+s+suffix;
            String bucketUrl="https://xjsczmm.oss-cn-hangzhou.aliyuncs.com/";
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            ossClient.putObject(bucket, ossName, inputStream1);
            System.out.println(bucketUrl+ossName);
            ossClient.shutdown();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(inputStream1 !=null){
                try {
                    inputStream1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    @Test
    public void delete(){
        String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
        String accessKeyId = "LTAI5t8YJaJeKj4qsRrAnDyR";
        String accessKeySecret = "9lzLbYvROdvUq6VvepTsJkTLE5UGIp";

        String bucketName = "xjsczmm";
        Date date = new Date();
        String dateStr = DateForMat.date2str(date, DateForMat.Date_Str_Y_M_D);
        String objectName = dateStr+"/26c693df-a089-4f6d-aa35-6d56625ae188.jpg";

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        ossClient.deleteObject(bucketName, objectName);
    }



    public void deleteBatch(List<String> imageNames){
        String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
        String accessKeyId = "LTAI5t8YJaJeKj4qsRrAnDyR";
        String accessKeySecret = "9lzLbYvROdvUq6VvepTsJkTLE5UGIp";

        String bucketName = "xjsczmm";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);



        DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(imageNames));
        // 关闭OSSClient。
        ossClient.shutdown();
    }























}
