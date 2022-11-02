package com.hcmute.starter.repository;

import com.hcmute.starter.model.entity.ProductEntity;
import com.hcmute.starter.model.entity.ProductRating.ProductRatingEntity;
import com.hcmute.starter.model.entity.ProductRating.ProductRatingLikeEntity;
import com.hcmute.starter.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRatingLikeRepository extends JpaRepository<ProductRatingLikeEntity,Integer> {
    List<ProductRatingLikeEntity> findAllByProductRating(ProductRatingEntity productRating);
    Optional<ProductRatingLikeEntity> findByProductRatingAndUser(ProductRatingEntity product, UserEntity user);
}
