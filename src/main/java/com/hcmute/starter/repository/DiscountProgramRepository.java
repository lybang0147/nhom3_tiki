package com.hcmute.starter.repository;

import com.hcmute.starter.model.entity.BrandEntity;
import com.hcmute.starter.model.entity.CartItemEntity;
import com.hcmute.starter.model.entity.DiscountProgramEntity;
import com.hcmute.starter.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@EnableJpaRepositories
public interface DiscountProgramRepository extends JpaRepository<DiscountProgramEntity, Long> {
    Optional<DiscountProgramEntity> findByName(String name);
    void deleteById(Long id);
    @Override
    List<DiscountProgramEntity> findAll();
    Boolean existsByName(String name);

    Optional<DiscountProgramEntity> findById(Long id);
    Optional<DiscountProgramEntity> findByIdAndBrandEntities(Long id,BrandEntity brand);
}
