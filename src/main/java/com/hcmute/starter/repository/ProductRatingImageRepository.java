package com.hcmute.starter.repository;

import com.hcmute.starter.model.entity.ProductRating.ProductRatingImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRatingImageRepository extends JpaRepository<ProductRatingImageEntity,Integer> {
}
