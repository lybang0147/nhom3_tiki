package com.hcmute.starter.repository;

import com.hcmute.starter.model.entity.ProductRating.ProductRatingCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRatingCommentRepository extends JpaRepository<ProductRatingCommentEntity,Integer> {
}
