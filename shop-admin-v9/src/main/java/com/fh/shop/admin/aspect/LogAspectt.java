package com.fh.shop.admin.aspect;

import com.fh.shop.admin.annotation.LogInfo;
import com.fh.shop.admin.biz.log.ILogService;
import com.fh.shop.common.SystemConstant;
import com.fh.shop.common.WebContext;
import com.fh.shop.admin.po.log.Log;
import com.fh.shop.admin.po.user.User;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class LogAspectt {

    @Resource(name = "logService")
    private ILogService logService;

    public static final Logger LOGGER= LoggerFactory.getLogger(LogAspectt.class);


    public Object logAspest(ProceedingJoinPoint pjp) throws Throwable {
        //获取类
        String canonicalName = pjp.getTarget().getClass().getCanonicalName();
        //获取方法
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        String name = signature.getName();
        //获取方法
        Method method = signature.getMethod();
        //将info改为全局变量
        String info="";
        //判断方法上有没有注解
        if (method.isAnnotationPresent(LogInfo.class)){
            //获取注解
            LogInfo annotation = method.getAnnotation(LogInfo.class);
             info = annotation.info();
        }
        StringBuilder ss=new StringBuilder();
        HttpServletRequest request = WebContext.getRequest();
        Map<String, String[]> parameterMap =   request.getParameterMap();
        Iterator<Map.Entry<String, String[]>> iterator =  parameterMap.entrySet().iterator();
    
        while (iterator.hasNext()){
            Map.Entry<String, String[]> entry = iterator.next();
            String key = entry.getKey();
            String[] value = entry.getValue();
            ss.append(";").append(key).append("=").append(StringUtils.join(value,","));
        }
        String result="";
        if(ss.length()>0){
            result = ss.substring(1);
        }


        LOGGER.info("开始这个"+canonicalName+"的"+name+"方法");
        Object proceed = pjp.proceed();
        //新增日志数据库
       
        User user = (User) request.getSession().getAttribute(SystemConstant.CURR_USER);
        if(user==null){
            return proceed;
        }
        Log log=new Log();
        log.setRealName(user.getRealName());
        log.setInsertTime(new Date());
        log.setInfo(info);
        log.setUserName(user.getUserName());
        log.setParamInfo(result);
        logService.addLog(log);

        LOGGER.info("结束这个"+canonicalName+"的"+name+"方法");
        return  proceed;
    }




}
