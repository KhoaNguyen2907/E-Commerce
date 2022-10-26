package com.ckt.ecommercecybersoft.product.boundary;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ckt.ecommercecybersoft.common.utils.ResponseUtils;
import com.ckt.ecommercecybersoft.product.dto.ProductDTO;
import com.ckt.ecommercecybersoft.product.service.ProductService;
import com.ckt.ecommercecybersoft.product.util.UrlUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

//@RunWith(SpringRunner.class)
//@WebMvcTest(ProductResource.class)
//@RunWith(SpringRunner.class)
@WebMvcTest(ProductResource.class)
//@WebMvcTest(HomeController.class)
public class ProductResourceTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;
    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get(UrlUtil.URL_PRODUCT)).andDo(print()).andExpect(status().isOk());
    }
    @Test
    public void getAllProductDto() throws Exception {
        ProductDTO productDTO = ProductDTO.builder()
                .id(UUID.fromString("34dfc0a4-128a-462a-a611-092209a82df8"))
                .title("test product")
                .description("junit test")
                .stock(2)
                .price(3)
                .thumbnailUrl("http://google.com")
                .brandId(UUID.fromString("1e07ac5b-b240-4d4d-97d2-c340265f554c"))
                .categoryIds(Arrays.asList(UUID.fromString("255077fe-7641-4924-b6fc-061b524f1ead")))
                .build();
        List<ProductDTO> productDTOList = Arrays.asList(productDTO);
        when(productService.findAllDto(ProductDTO.class)).thenReturn(productDTOList);
        mockMvc.perform(MockMvcRequestBuilders.get(UrlUtil.URL_PRODUCT))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void saveProduct() throws Exception {
        ProductDTO productDTO = ProductDTO.builder()
                .id(UUID.fromString("34dfc0a4-128a-462a-a611-092209a82df8"))
                .title("test product")
                .description("junit test")
                .stock(2)
                .price(3)
                .thumbnailUrl("http://google.com")
                .brandId(UUID.fromString("1e07ac5b-b240-4d4d-97d2-c340265f554c"))
                .categoryIds(Arrays.asList(UUID.fromString("255077fe-7641-4924-b6fc-061b524f1ead")))
                .build();
        ResponseEntity response = ResponseUtils.get(productDTO, HttpStatus.CREATED);
        when(productService.createProduct(productDTO)).thenReturn(productDTO);
//        ResponseEntity responseEntity = ResponseUtils.get(productDTO, HttpStatus.CREATED);
//        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(UrlUtil.URL_PRODUCT)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(productDTO));
//        mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk());
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(UrlUtil.URL_PRODUCT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(productDTO));
        mockMvc.perform(requestBuilder).andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void updateProduct() {
    }

    @Test
    public void deleteProduct() {
    }
}
