package com.lzl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lzl.mapper")
public class StorageManageSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(StorageManageSystemApplication.class, args);
    }

}
