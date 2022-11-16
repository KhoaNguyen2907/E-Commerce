package com.ckt.ecommercecybersoft.category;

import com.ckt.ecommercecybersoft.category.dto.CategoryDTO;
import com.ckt.ecommercecybersoft.category.model.CategoryEntity;
import com.ckt.ecommercecybersoft.category.service.CategoryService;
import com.ckt.ecommercecybersoft.category.utils.CategoryUrlUtils;
import com.ckt.ecommercecybersoft.common.model.ResponseDTO;
import com.ckt.ecommercecybersoft.common.utils.ProjectMapper;
import com.ckt.ecommercecybersoft.common.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    ProjectMapper mapper;

    @GetMapping
    public ResponseEntity<ResponseDTO> getAllCategory(){
        List<CategoryDTO> categoryDTOs = categoryService.findAllDto(CategoryDTO.class);
        return ResponseUtils.get(categoryDTOs, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        categoryDTO = categoryService.save(categoryDTO, CategoryEntity.class);
        return ResponseUtils.get(categoryDTO, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ResponseDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        CategoryDTO savedCategory = categoryService.update(categoryDTO);
        return ResponseUtils.get(savedCategory, HttpStatus.OK);
    }

    @DeleteMapping(CategoryUrlUtils.BY_ID)
    public ResponseEntity<ResponseDTO> deleteCategory(@PathVariable("id") UUID id){
        categoryService.deleteById(id);
        return ResponseUtils.get(null, HttpStatus.OK);
    }

}

