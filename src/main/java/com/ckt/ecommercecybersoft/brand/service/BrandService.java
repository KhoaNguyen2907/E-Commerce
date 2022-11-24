package com.ckt.ecommercecybersoft.brand.service;

import com.ckt.ecommercecybersoft.brand.dto.BrandDTO;
import com.ckt.ecommercecybersoft.brand.model.BrandEntity;
import com.ckt.ecommercecybersoft.brand.repository.BrandRepository;
import com.ckt.ecommercecybersoft.common.exception.NotFoundException;
import com.ckt.ecommercecybersoft.common.service.GenericService;
import com.ckt.ecommercecybersoft.common.utils.ProjectMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

public interface BrandService extends GenericService<BrandEntity, BrandDTO, UUID> {
    BrandDTO findBrandById(UUID id);

    BrandDTO createBrand(BrandDTO brandDTO);

    BrandDTO updateBrand(BrandDTO brandDTO, UUID id);
}

@Service
class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final ProjectMapper mapper;

    public BrandServiceImpl(BrandRepository brandRepository, ProjectMapper mapper) {
        this.brandRepository = brandRepository;
        this.mapper = mapper;
    }

    @Override
    public JpaRepository<BrandEntity, UUID> getRepository() {
        return brandRepository;
    }

    @Override
    public ProjectMapper getMapper() {
        return mapper;
    }

    @Override
    @Cacheable(value = "brand", key = "#id")
    public BrandDTO findBrandById(UUID id) {
        BrandEntity brandEntity = brandRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(""));
        return mapper.map(brandEntity, BrandDTO.class);
    }

    @Override
    public BrandDTO createBrand(BrandDTO brandDTO) {
        BrandEntity brandEntity = mapper.map(brandDTO, BrandEntity.class);
        return mapper.map(
                brandRepository.save(brandEntity),
                BrandDTO.class);
    }

    @Override
    @CachePut(value = "brand", key = "#id")
    public BrandDTO updateBrand(BrandDTO brandDTO, UUID id) {
        BrandEntity brandEntity = brandRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(""));
        brandEntity.setTitle(brandDTO.getTitle());
        brandEntity.setThumbnailUrl(brandDTO.getThumbnail());
        return mapper.map(brandEntity, BrandDTO.class);
    }

    @Override
    @CacheEvict(value = "brand", key = "#id")
    public void deleteById(UUID id) {
        BrandService.super.deleteById(id);
    }
}