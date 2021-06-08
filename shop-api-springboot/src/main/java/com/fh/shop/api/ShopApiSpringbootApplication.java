package com.fh.shop.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication()
@MapperScan({"com.fh.shop.api.*.mappers","com.fh.shop.mappers"})
@EnableScheduling
@EnableAutoConfiguration
public class ShopApiSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopApiSpringbootApplication.class, args);
    }

}
