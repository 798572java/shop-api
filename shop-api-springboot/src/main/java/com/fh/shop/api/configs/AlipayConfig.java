package com.fh.shop.api.configs;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2021000117658341";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCmOe1/xnbWyIi5e6OF1EmUYUz/IDFxaqyWbpUreax8biV8GVaYtxV/TIxiLBtavrWnthpv/COkWPN8SmKzdPJp+FpHTQCRhxO+8GhVBYcGuJ4MUvCqHjYFjsOD7R6Q46pZLT5fxilmPnpSBkFA7p1V8BuXvmpeA7rLxen4hvmeKoGuX5oTG52OkqgPlO+/RXjt2IJsV0Mn0lPKiwGR2CXSVvOdEjeTeZalimpCi/DbTTcFt6JAhyoM2adaF9OcOFicp627e/om3ChFcjHuZS87+bGoHBx/TwbCTElu6uA+eBDE6kGzMA3VjpHkyA/cr6kr53gS7QaXmtffL7NLGbOnAgMBAAECggEAMpNFc68K0WbKUsUPh9SDXqYMK0O8R2nRgSO8NzIaDPHmbc+3zVdHLyg3Pld1KzMDSjfZ62Cn54U48AQh68AVUbDCpxcCqUST+XBkDhpUbvMVA2Lo3pm6jg5V5nnq5uKeHevqInHEfttblEFa8UqojFE11AGXk2tHFmDtQzSuFjUTC9evp4vfYAmlbBvRx3NmMBstbgfIgr5zNTgM8yv1+90zO9KrvV6Jozub9mNvNXE4V0LbvtPP2OO1DGC0p7UsVfPu62u6KqC7HG6ao4j5ioGyCcBxgShdPuHWW9JOVo3owW0nkgOz9tpZI8MNliUqwMRGTN54UBSyLvJy4yfugQKBgQDVyw0vYuA51ZSctN+dPcYlIpe82ukR/cyFHD+9+Oz1AtAWwFqh2TnFDp9y7DvHZf4Uzc+EBOFpPIi3vm1sWstjK5Pme1KjV0ZB9Obi/a7gY+yCukwVC2eKwx2pLmu2l78YRHcP2sOSGrh9RabPNcLyIkBgyDKuxHgjhnnv38zoFwKBgQDHCuF+4WJHRgyB6IhCsrsVGNTbbe3ggX3zZzrAZc9KYyD85NukGy0wn4eA8L4XKjlWLrmAWVX3SpiMzUBK5x8oI67oYd37eR6U9HG+96Zf2Us2R5lYucXrV5MB2s6hjUySNobm4Q+BzuLRAzdrkZPPQILl+jHhUXQfecYfKxI68QKBgQDCUj05xPVpm+foR764fV2Mg1TII3GVGMq8cmC9m3RFfrkE9PFsCrWnlxL2siHjq6C16BumM4luzca2vC9ZPW2ARiu4Yn9KWBlT+fWifFJnQmvGpu5gcm06gv6Ct97PqD3ZIOHXV1BLjF4D/zsPPB5lVxyM22q1z34jTCepEBJ/IQKBgA8/0SjkYaAuSLUHOLFLejPSlDQ/htMFeL3WMdUdt7TuRU+6xO0cu+iWwE3ro9Sogn4M1F91+LUafPeEniFe9a8glLzvCo/CpsootDI6TP/Y6P8+mwwGJ5ixO+0Pc3dDGRO2o706jvKWBCCZS3ytSZK+UYkHEx09HVVwAfLPXPUxAoGActfqcn4V9ul2KQLQrm9sJIH/bS522gsVH0+44m6aYtNOa3JwxhGSzsOBVtvs3RJ4eFBLIRRYp2KeA438nC1JJeaXGpIAKIzghpChhPj8cQARlGRJKaoxufPenNdhSmmXgMooaMwCbIalHKfdCh56HEDtZNnhonCrx6trcR2tvdk=";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2jav63D2qGY0xFbFTKx+yaVQkcXy9OvRMuAgx4zI6ppl5rKbF3+Sgi2yfMtH2YmHJSdv26oCEdC1t+Vg6VJeShY4ukebNKJJd8zzsAZI8094xihxK0M4c78Yd2hUc+HtR7bf4q0/GoLQw8KQM2J76vBuc/tK7kDTSc9hMF/7lH2m0bzLTKFgj1l7Nowxzb3GM5yx4hzaNO67gY1sGpwYkVKTObPqL5IAWfT8lsf7Dm7Jy1M32SnNSlwztQhW4A2z2EMHk/UQiWKSIm8UY+BAfO5cnTdwdDSks9efZIbo+MqIUg4DPkrowlqXkTJKACK3VWbYUnMZcwFV8b+b+wcXuQIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://2zus7k.natappfree.cc/api/pay/aliNotify";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://2zus7k.natappfree.cc/api/pay/aliReturn";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do\n";
	
	// 支付宝网关
	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

