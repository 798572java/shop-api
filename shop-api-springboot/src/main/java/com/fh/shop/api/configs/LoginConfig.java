package com.fh.shop.api.configs;

import com.fh.shop.api.Interceptor.ITokenInterceptor;
import com.fh.shop.api.Interceptor.LoginIntercepTor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoginConfig implements WebMvcConfigurer {


    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getInterceptor()).addPathPatterns("/api/**");
        registry.addInterceptor(tokenInterceptor()).addPathPatterns("/api/**");
    }



    @Bean
    public  LoginIntercepTor getInterceptor(){
        return  new LoginIntercepTor();
    }

    @Bean
    public ITokenInterceptor tokenInterceptor(){
        return new ITokenInterceptor();
    }


}
