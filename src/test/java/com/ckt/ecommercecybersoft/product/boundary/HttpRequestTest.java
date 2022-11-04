package com.ckt.ecommercecybersoft.product.boundary;

import com.ckt.ecommercecybersoft.product.util.UrlUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {
        this.restTemplate.getForObject("http://localhost:" + port + "/" + UrlUtil.URL_PRODUCT,
                List.class).size();
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/" + UrlUtil.URL_PRODUCT,
                List.class).size()).isEqualTo(15);
    }
}
