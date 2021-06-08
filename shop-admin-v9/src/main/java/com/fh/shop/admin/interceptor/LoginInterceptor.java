package com.fh.shop.admin.interceptor;

import com.fh.shop.common.SystemConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LoginInterceptor extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object user = request.getSession().getAttribute(SystemConstant.CURR_USER);
        if (user == null) {

            String header = request.getHeader("X-Requested-With");
            if(StringUtils.isNotEmpty(header) && header.equalsIgnoreCase("XMLHttpRequest")){
                response.addHeader("Xjs","Xjs");
            }else {
                // 如果用户信息为空则证明用户未登录，跳转到登录页面
                response.sendRedirect(SystemConstant.LOGIN);
            }
            return false;
        } else {
            // 否则证明用户成功登录，则放行
            return true;
        }
    }




}
