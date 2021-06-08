package com.fh.shop.api.Interceptor;

import com.fh.shop.api.annotations.Token;
import com.fh.shop.api.common.KeyUtil;
import com.fh.shop.api.exception.ShopException;
import com.fh.shop.common.ResponseEnum;
import com.fh.shop.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class ITokenInterceptor extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //判断是否有@Token这个注解，如果没有就直接放行
        if(!method.isAnnotationPresent(Token.class)){
            return true;
        }
        //获取有信息
        String token = request.getHeader("fh-token");
        if(StringUtils.isEmpty(token)){
            throw new ShopException(ResponseEnum.TOKEN_HEADER_IS_NULL);
        }
        //删除redis中对应的token
        Long del = RedisUtil.del(KeyUtil.buildTokenKey(token));
        if(del == 0){
            throw new ShopException(ResponseEnum.TOKEN_HEADER_IS_NULL);
        }

        return true;
    }

}
