package com.ckt.ecommercecybersoft;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class EcommerceCybersoftApplication {
    @Autowired
    public static void main(String[] args) {
        SpringApplication.run(EcommerceCybersoftApplication.class, args);
    }

}
