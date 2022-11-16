package com.ckt.ecommercecybersoft.product.boundary;

import com.ckt.ecommercecybersoft.common.utils.ResponseUtils;
import com.ckt.ecommercecybersoft.product.dto.ProductDTO;
import com.ckt.ecommercecybersoft.product.service.ProductService;
import com.ckt.ecommercecybersoft.product.util.UrlUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(UrlUtil.URL_PRODUCT)
public class ProductResource {
    private ProductService productService;

    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public @ResponseBody List<ProductDTO> getAllProductDto() {
        List<ProductDTO> productDTOs = productService.findAllDto(ProductDTO.class);
//        productDTOs.forEach(product -> {
//            String thumbUrl = product.getThumbnailUrl();
//            if(thumbUrl != null && !thumbUrl.isEmpty()) {
//                thumbUrl = Base64.getUrlEncoder().encodeToString(thumbUrl.getBytes());
//                product.setThumbnailUrl(thumbUrl);
//            }
//        });
        return productDTOs;
//        return Arrays.asList("Hello, world") ;
    }

    @PostMapping
    public ResponseEntity<?> saveProduct(@RequestBody @Valid ProductDTO productDTO){
//        String thumbUrl = productDTO.getThumbnailUrl();
//        if(thumbUrl != null && !thumbUrl.isEmpty()) {
//            thumbUrl = Base64.getUrlEncoder().encodeToString(thumbUrl.getBytes());
//        }
        ProductDTO savedProductDTO = productService.createProduct(productDTO);
//        savedProductDTO.setThumbnailUrl(thumbUrl);
        return ResponseUtils.get(
                savedProductDTO,
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@RequestBody ProductDTO productDTO, @PathVariable UUID id) {
        return ResponseUtils.get(
                productService.updateProduct(productDTO, id),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable UUID id){
        productService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
