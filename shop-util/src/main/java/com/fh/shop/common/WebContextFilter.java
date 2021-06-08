package com.fh.shop.common;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class WebContextFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //强转
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        try {
            //将request set进入WebContext
            WebContext.setRequest(request);
            //继续执行后续代码
            filterChain.doFilter(servletRequest,servletResponse);
        } finally {
            WebContext.remove();
        }
    }

    @Override
    public void destroy() {

    }
}
