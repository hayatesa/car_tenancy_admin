package com.dev.main;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ServletComponentScan // 扫描filter和servlet
@EnableTransactionManagement//开启事务管理
@MapperScan({"com.dev.main.dao", "com.dev.main.shiro.dao", "com.dev.main.tenancy.dao"})//与dao层的@Mapper二选一写上即可(0主要作用是扫包)
@EnableCaching
@EnableScheduling // 开启定时器
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
