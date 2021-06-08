package com.fh.shop.api.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.common.SystemConstant;
import com.fh.shop.mapper.IMsgLogMapper;
import com.fh.shop.po.MsgLog;
import com.fh.shop.util.DateForMat;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class MQMessageJob {

    @Autowired
    private IMsgLogMapper msgLogMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Scheduled(cron = "0/30 * * * * ?")
    public void doJob(){
        //查询状态再"发送中",并且到了重试时间(重试时间 <= 系统时间)
        QueryWrapper<MsgLog> msgLogQueryWrapper = new QueryWrapper<>();
        msgLogQueryWrapper.eq("status",SystemConstant.MESSAGE_LOG_STATUS.SENDING);
        msgLogQueryWrapper.le("retryTime",new Date());
        List<MsgLog> msgLogs = msgLogMapper.selectList(msgLogQueryWrapper);
        msgLogs.forEach(x -> {
            String msgId = x.getMsgId();
            //获取次数
            int retryCount = x.getRetryCount();
            //判断重试次数是否大于等于三
            if(retryCount >= SystemConstant.RETRY_COUNT){
                //更新状态为发送失败
                MsgLog msgLog = new MsgLog();
                msgLog.setMsgId(msgId);
                msgLog.setStatus(SystemConstant.MESSAGE_LOG_STATUS.SEND_FAIL);
                msgLog.setUpdateTime(new Date());
                msgLogMapper.updateById(msgLog);
            }else {
                //那么更新重试时间（当前时间+一分钟）和更新时间
                MsgLog msgLog = new MsgLog();
                msgLog.setMsgId(msgId);
                Date date = new Date();
                msgLog.setRetryTime(DateForMat.addMinute(date,1));
                msgLog.setRetryCount(x.getRetryCount()+1);
                msgLog.setUpdateTime(date);
                msgLogMapper.updateById(msgLog);
                //重投
                CorrelationData correlationData = new CorrelationData(msgId);
                //发送消息
                rabbitTemplate.convertAndSend(x.getExchange(),x.getRouteKey(),x.getMessage(),correlationData);
            }
        });

    }

}
