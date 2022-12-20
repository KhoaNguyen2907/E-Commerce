package com.ckt.ecommercecybersoft.product.boundary;

import com.ckt.ecommercecybersoft.common.model.ResponseDTO;
import com.ckt.ecommercecybersoft.common.utils.ProjectMapper;
import com.ckt.ecommercecybersoft.common.utils.ResponseUtils;
import com.ckt.ecommercecybersoft.product.dto.ProductDTO;
import com.ckt.ecommercecybersoft.product.service.ProductService;
import com.ckt.ecommercecybersoft.product.util.UrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@CrossOrigin(value = "https://black-adam-web.herokuapp.com/", allowCredentials = "true")
@RestController
@RequestMapping(UrlUtil.URL_PRODUCT)
public class ProductResource {
    @Autowired
    private ProductService productService;
    @Autowired
    ProjectMapper mapper;

    @GetMapping
    public ResponseEntity<ResponseDTO> getAllProductDto() {
        List<ProductDTO> productDTOs = productService.findAllDto(ProductDTO.class);
        return ResponseUtils.get(productDTOs, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseDTO> getProductById(@PathVariable UUID id) {
        ProductDTO productDTO = productService.findProductById(id);
        return ResponseUtils.get(productDTO, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<ResponseDTO> saveProduct(@RequestBody @Valid ProductDTO productDTO){
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
    public ResponseEntity<ResponseDTO> updateProduct(@RequestBody ProductDTO productDTO, @PathVariable UUID id) {
        return ResponseUtils.get(
                productService.updateProduct(productDTO, id),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteProduct(@PathVariable UUID id){
        ProductDTO ret = productService.deleteProductById(id);
        return ResponseUtils.get(
                ret,
                HttpStatus.OK
        );
    }
}
