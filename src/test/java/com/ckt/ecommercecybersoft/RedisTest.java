package com.ckt.ecommercecybersoft;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate template;

    @Test
    final void redisTest() {
        template.opsForValue().set("test", "test value");
        System.out.println(template.opsForValue().get("test"));
        Assertions.assertEquals(template.opsForValue().get("test"), "test value");
    }

}
