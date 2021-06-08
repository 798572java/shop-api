package com.fh.shop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan("com.fh.shop.common")
@MapperScan({"com.fh.shop.api.*.mappers","com.fh.shop.mappers"})
public class ShopMqApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopMqApplication.class, args);
    }

}
