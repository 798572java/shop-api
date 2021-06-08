package com.fh.shop.api.pay.biz;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.fh.shop.api.configs.AlipayConfig;
import com.fh.shop.api.mq.PayProducer;
import com.fh.shop.mapper.IOrderMapper;
import com.fh.shop.po.Order;
import com.fh.shop.common.ResponseEnum;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.common.SystemConstant;
import com.fh.shop.mapper.IMemberMapper;
import com.fh.shop.mapper.IOrderItemMapper;
import com.fh.shop.mapper.ISkuMapper;
import com.fh.shop.mq.PayMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service("aliPayService")
public class IPayServiceImpl implements IPayService {

    @Autowired
    private IOrderMapper orderMapper;
    @Autowired
    private IMemberMapper memberMapper;
    @Autowired
    private ISkuMapper skuMapper;
    @Autowired
    private IOrderItemMapper orderItemMapper;
    @Autowired
    private PayProducer payProducer;


    @Override
    public ServerResponse aliPay(String orderId) {
        //获取订单信息
        Order order = orderMapper.selectById(orderId);
        if(order == null){
            return ServerResponse.error(ResponseEnum.PAY_ORDER_ID_IS_ERROR);
        }
        if(order.getStatus() != SystemConstant.ORDER_STATUS.WAIT_PAY){
            return ServerResponse.error(ResponseEnum.PAY_ORDER_STATUS_IS_ERROR);
        }
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = order.getId();
        //付款金额，必填
        String total_amount = order.getTotalPrice().toString();
        //订单名称，必填
        String subject = "test";

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //请求
        String result = null;
        try {
            result = alipayClient.pageExecute(alipayRequest).getBody();
            //输出
            System.out.println(result);
            return ServerResponse.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }


    }

    @Override
    public String aliNotify(Map<String, String[]> requestParams) {
        Map<String,String> params = new HashMap<String,String>();
        try {
            for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }
            //调用SDK验证签名
            boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
            //验证签名
            if(signVerified){
                String trade_status = params.get("trade_status");
                //验证状态是否是success
                if(trade_status.equals("TRADE_SUCCESS")){
                    /*进行业务处理*/
                    //获取订单id
                    String out_trade_no = params.get("out_trade_no");
                    Order order = orderMapper.selectById(out_trade_no);

                    // 1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
                    if(order == null){
                        return "fail";
                    }

                    //2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
                    String orderPrice = order.getTotalPrice().toString();
                    String price = params.get("total_amount");
                    if(!orderPrice.equals(price)){
                        return "fail";
                    }

                    //3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
                    String seller_id = params.get("seller_id");
                    if(!seller_id.equals(SystemConstant.PAY_SELL_ID)){
                        return "fail";
                    }

                    //4、验证app_id是否为该商户本身。
                    String app_id = params.get("app_id");
                    if(!app_id.equals(SystemConstant.PAY_APP_ID)){
                        return "fail";
                    }


                    //判断状态是不是已支付状态
                    if(order.getStatus() != SystemConstant.ORDER_STATUS.WAIT_PAY){
                        //取反，如果不是已支付状态那么就让他进行支付，返回success
                        return "success";
                    }

                    //更改订单状态
                    Order order1 = new Order();
                    order1.setId(out_trade_no);
                    order1.setStatus(SystemConstant.ORDER_STATUS.PAY_SUCCESS);
                    orderMapper.updateById(order1);


                    //往member表中插入积分
                    String total_amount = params.get("total_amount");
                    Long memberId = order.getMemberId();
                    //向上取整
//                    double score = Math.floor(Double.parseDouble(total_amount));
//                    memberMapper.updateScore(score,memberId);
                    //往sku表中插入销量
//                    QueryWrapper<OrderItem> queryWrapper = new QueryWrapper<>();
//                    queryWrapper.eq("orderId",out_trade_no);
//                    List<OrderItem> orderItems = orderItemMapper.selectList(queryWrapper);
//                    orderItems.stream().forEach(x -> {
//                        Long skuCount = x.getSkuCount();
//                        Long skuId = x.getSkuId();
//                        skuMapper.updateSales(skuCount,skuId);
//                    });
                    //异步支付
                    PayMessage payMessage = new PayMessage();
                    payMessage.setTotalAmount(total_amount);
                    payMessage.setMemberId(memberId);
                    payMessage.setOrderId(out_trade_no);
                    payProducer.sendMessage(payMessage);
                    return "success";
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return "fail";
    }



}
