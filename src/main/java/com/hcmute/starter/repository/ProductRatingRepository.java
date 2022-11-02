package com.hcmute.starter.repository;

import com.hcmute.starter.model.entity.ProductEntity;
import com.hcmute.starter.model.entity.ProductRating.ProductRatingEntity;
import com.hcmute.starter.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRatingRepository extends JpaRepository<ProductRatingEntity,Integer> {
    List<ProductRatingEntity> getAllByProduct(ProductEntity product);
    List<ProductRatingEntity> getAllByUser(UserEntity user);
    Optional<ProductRatingEntity> getByUserAndProduct(UserEntity user,ProductEntity product);
}
