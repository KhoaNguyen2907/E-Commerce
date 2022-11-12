package com.ckt.ecommercecybersoft;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class EcommerceCybersoftApplication implements CommandLineRunner {
    @Autowired
    private RedisTemplate template;

    public static void main(String[] args) {
        SpringApplication.run(EcommerceCybersoftApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        template.opsForValue().set("loda", "hello world");

        // In ra màn hình Giá trị của key "loda" trong Redis
        System.out.println("Value of key loda: " + template.opsForValue().get("loda"));
    }
}
