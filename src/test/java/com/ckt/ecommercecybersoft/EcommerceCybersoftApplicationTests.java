package com.ckt.ecommercecybersoft;

import com.ckt.ecommercecybersoft.brand.repository.BrandRepository;
import com.ckt.ecommercecybersoft.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

@SpringBootTest
class EcommerceCybersoftApplicationTests {
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private ProductRepository productRepository;



    @Test
    void testCrudFromProduct() {
        Assertions.assertEquals(null,
                productRepository.findById(UUID.fromString(
                        "6f8947ec-b739-4fcb-a7e2-bae8dbb9d53e"
                )).orElse(null));
    }

    @Test
    public void getAllProductDTO() {
        Assertions.assertEquals(13, productRepository.findAll().size());
    }
    @Test
    void contextLoads() {
    }


}
