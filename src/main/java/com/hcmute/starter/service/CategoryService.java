package com.hcmute.starter.service;

import com.hcmute.starter.model.entity.CategoryEntity;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Component
@Service
public interface CategoryService {
    CategoryEntity saveCategory(CategoryEntity category);

    List<CategoryEntity> foundCategory(CategoryEntity category);

    List<CategoryEntity> findAllCategory();

    List<CategoryEntity> findByCategoryParent(UUID uuid);

    List<CategoryEntity> findCategoryRoot();

    CategoryEntity findById(UUID id);

    List<CategoryEntity> findCategoryChild(CategoryEntity category);

    void deleteCategory(UUID id);
}
