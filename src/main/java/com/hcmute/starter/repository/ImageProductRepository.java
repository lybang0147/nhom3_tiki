package com.hcmute.starter.repository;

import com.hcmute.starter.model.entity.ImageProductEntity;
import com.hcmute.starter.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@EnableJpaRepositories
public interface ImageProductRepository extends JpaRepository<ImageProductEntity, UUID> {
    List<ImageProductEntity> findByProduct(ProductEntity product);
}
