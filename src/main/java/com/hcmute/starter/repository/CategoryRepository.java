package com.hcmute.starter.repository;

import com.hcmute.starter.model.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.UUID;
@EnableJpaRepositories
public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID>  {
    List<CategoryEntity> findByName(String categoryName);
    @Query(value = "SELECT * FROM category WHERE parent_id is null",
            countQuery = "SELECT count(*) FROM category WHERE parent_id is null",
            nativeQuery = true)
    List<CategoryEntity> findCategoryRoot();
    @Query(value = "SELECT * FROM category WHERE parent_id = ?1",
            countQuery = "SELECT count(*) FROM category WHERE parent_id = ?1",
            nativeQuery = true)
    List<CategoryEntity> findCategoryChild(UUID uuid);

}
