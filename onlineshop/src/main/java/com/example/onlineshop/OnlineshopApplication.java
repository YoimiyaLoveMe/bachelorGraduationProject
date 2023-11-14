package com.example.onlineshop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wu
 */
@SpringBootApplication
@MapperScan("com.example.onlineshop.mapper")
/**
 * 未加则扫描不到mapper
 */
public class OnlineshopApplication {
    public static void main(String[] args) {
        SpringApplication.run(OnlineshopApplication.class, args);
    }

}
