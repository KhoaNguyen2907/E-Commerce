package com.ckt.ecommercecybersoft.common.service;

import com.ckt.ecommercecybersoft.common.entity.BaseEntity;
import com.ckt.ecommercecybersoft.common.exception.NotFoundException;
import com.ckt.ecommercecybersoft.common.utils.ProjectMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface GenericService<T extends BaseEntity, D, I> {
    JpaRepository<T, I> getRepository();

    ProjectMapper getMapper();
    default List<T> findAll() {
        return getRepository().findAll();
    }

    default List<T> findAll(Pageable page) {
        return getRepository().findAll(page).stream().collect(Collectors.toList());
    }

    default List<D> findAllDto(Class<D> clazz) {
        return getRepository().findAll().stream()
                .map(model -> getMapper().map(model, clazz))
                .collect(Collectors.toList());
    }

    default List<D> findAllDto(Pageable page, Class<D> clazz) {
        return getRepository().findAll(page).stream()
                .map(model -> getMapper().map(model, clazz))
                .collect(Collectors.toList());
    }


    default Optional<T> findById(I id) {
        return getRepository().findById(id);
    }

    default D save(D dto, Class<T> clazz) {
        T entity = getMapper().map(dto, clazz);
        T savedEntity = getRepository().save(entity);
        return getMapper().map(savedEntity, (Type) dto.getClass());
    }

    default void deleteById(I id) {
        getRepository().findById(id).orElseThrow(() -> new NotFoundException("Data Not Found"));
        getRepository().deleteById(id);
    }

    default List<T> findAllByIds(List<I> ids) {
        return getRepository().findAllById((ids));
    }
}

