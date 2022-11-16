package com.ckt.ecommercecybersoft.category.service;

import com.ckt.ecommercecybersoft.category.dto.CategoryDTO;
import com.ckt.ecommercecybersoft.category.model.CategoryEntity;
import com.ckt.ecommercecybersoft.category.repository.CategoryRepository;
import com.ckt.ecommercecybersoft.common.service.GenericService;
import com.ckt.ecommercecybersoft.common.utils.ProjectMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

public interface CategoryService extends GenericService<CategoryEntity, CategoryDTO, UUID> {
    CategoryDTO update(CategoryDTO categoryDTO);
}

@Service
class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProjectMapper mapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ProjectMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public JpaRepository<CategoryEntity, UUID> getRepository() {
        return categoryRepository;
    }

    @Override
    public ProjectMapper getMapper() {
        return mapper;
    }

    @Override
    public CategoryDTO update(CategoryDTO categoryDTO) {
        CategoryEntity category = mapper.map(categoryDTO, CategoryEntity.class);
        category = categoryRepository.save(category);
        return mapper.map(category, CategoryDTO.class);
    }
}
