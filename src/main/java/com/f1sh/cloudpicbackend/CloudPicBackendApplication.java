package com.f1sh.cloudpicbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("com.f1sh.cloudpicbackend.mapper")
@EnableAspectJAutoProxy(exposeProxy = true)
public class CloudPicBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudPicBackendApplication.class, args);
    }

}
