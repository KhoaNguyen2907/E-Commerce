package com.ckt.ecommercecybersoft.product.service;

import com.ckt.ecommercecybersoft.brand.model.BrandEntity;
import com.ckt.ecommercecybersoft.brand.service.BrandService;
import com.ckt.ecommercecybersoft.category.model.CategoryEntity;
import com.ckt.ecommercecybersoft.category.service.CategoryService;
import com.ckt.ecommercecybersoft.common.exception.NotFoundException;
import com.ckt.ecommercecybersoft.common.service.GenericService;
import com.ckt.ecommercecybersoft.common.utils.ProjectMapper;
import com.ckt.ecommercecybersoft.product.dto.ProductDTO;
import com.ckt.ecommercecybersoft.product.model.ProductEntity;
import com.ckt.ecommercecybersoft.product.repository.ProductRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public interface ProductService extends GenericService<ProductEntity, ProductDTO, UUID> {
    ProductDTO findProductById(UUID id);
    ProductDTO createProduct(ProductDTO productDTO);

    ProductDTO updateProduct(ProductDTO productDTO, UUID id);
}

@Service
@Transactional
class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final BrandService brandService;
    private final CategoryService categoryService;
    private final ProjectMapper mapper;

    private final MessageSource messageSource;

    public ProductServiceImpl(ProductRepository productRepository,
                              BrandService brandService, CategoryService categoryService, ProjectMapper mapper, MessageSource messageSource) {
        this.productRepository = productRepository;
        this.brandService = brandService;
        this.categoryService = categoryService;
        this.mapper = mapper;
        this.messageSource = messageSource;
    }

    @Override
    public JpaRepository<ProductEntity, UUID> getRepository() {
        return productRepository;
    }

    @Override
    public ProjectMapper getMapper() {
        return mapper;
    }

    @Override
    @Cacheable(value = "product", key = "#id")
    public ProductDTO findProductById(UUID id) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(""));
        return mapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        ProductEntity product = mapper.map(productDTO, ProductEntity.class);
        List<CategoryEntity> categories =
                categoryService.findAllByIds(productDTO.getCategoryIds());
        categories.forEach(product::addCategory);
        return mapper.map(
                productRepository.save(product),
                ProductDTO.class
        );
    }

    @Override
    @CachePut(value = "product", key = "#id")
    public ProductDTO updateProduct(ProductDTO productDTO, UUID id) {
//        ProductEntity curProductEntity = productRepository.findById(id).orElse(null);
        BrandEntity brandEntity;
        if (productDTO.getBrandId() != null){
             brandEntity =
                    brandService.findById(productDTO.getBrandId())
                            .orElseThrow(() -> {
                                return new ValidationException(
                                        "Brand is not existed");
                            });
        } else {
            brandEntity = null;
        }

        ProductEntity curentProduct =
                productRepository.findById(id).map(productEntity -> {
                            // Get Brand
                            List<CategoryEntity> categories =
                                    categoryService.findAllByIds(productDTO.getCategoryIds());
                            productEntity.setTitle(productDTO.getTitle());
                            productEntity.setPrice(productDTO.getPrice());
                            productEntity.setDescription(productDTO.getDescription());
                            productEntity.setThumbnailUrl(productDTO.getThumbnailUrl());
                            productEntity.setStock(productDTO.getStock());
                            productEntity.setBrand(brandEntity);
                            categories.forEach(productEntity::addCategory);
                            return productEntity;
                        })
                        .orElseGet(() -> {
                            ProductEntity product = mapper.map(productDTO, ProductEntity.class);
                            List<CategoryEntity> categories =
                                    categoryService.findAllByIds(productDTO.getCategoryIds());
                            categories.forEach(product::addCategory);
                            return product;
                        });
        return mapper.map(productRepository.save(curentProduct), ProductDTO.class);
    }

    @Override
    @CacheEvict(value = "product", key = "#id")
    public void deleteById(UUID id) {
        ProductEntity productEntity = productRepository.findById(id).orElse(null);
        productRepository.findById(id).stream().forEach(product -> {
            product.removeCategory();
        });
        ProductService.super.deleteById(id);
    }

    @Override
    public List<ProductDTO> findAllDto(Class<ProductDTO> clazz) {
        List<ProductEntity> productEntities = productRepository.findAll();
        List<ProductDTO> productDTOs =
                productEntities.stream().map(productEntity -> {
                    ProductDTO productDTO = mapper.map(productEntity, ProductDTO.class);
                   return productDTO;
                }).collect(Collectors.toList());
        return productDTOs;
    }
}
