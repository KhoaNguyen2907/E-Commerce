package com.ckt.ecommercecybersoft.brand.service;

import com.ckt.ecommercecybersoft.brand.dto.BrandDTO;
import com.ckt.ecommercecybersoft.brand.model.BrandEntity;
import com.ckt.ecommercecybersoft.brand.repository.BrandRepository;
import com.ckt.ecommercecybersoft.common.service.GenericService;
import com.ckt.ecommercecybersoft.common.utils.ProjectMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

public interface BrandService extends GenericService<BrandEntity, BrandDTO, UUID> {

}
@Service
class BrandServiceImpl implements BrandService{

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
}