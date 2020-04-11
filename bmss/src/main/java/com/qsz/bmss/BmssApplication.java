package com.qsz.bmss;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.qsz.bmss.dao")
@SpringBootApplication
public class BmssApplication {

    public static void main(String[] args) {
        SpringApplication.run(BmssApplication.class, args);
    }

}
