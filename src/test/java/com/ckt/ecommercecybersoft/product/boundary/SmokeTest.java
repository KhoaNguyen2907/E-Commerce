package com.ckt.ecommercecybersoft.product.boundary;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SmokeTest {

    @Autowired
    private ProductResource productResource;

    @Test
    public void contextLoads() throws Exception {
//        Assert.assertNotNull("Not null", controller);
        Assert.assertNotNull("Not null", productResource);
    }
}
