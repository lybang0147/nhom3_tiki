package com.hcmute.starter.service;

import com.hcmute.starter.model.entity.ProductEntity;
import com.hcmute.starter.model.entity.ProductRating.ProductRatingCommentEntity;
import com.hcmute.starter.model.entity.ProductRating.ProductRatingEntity;
import com.hcmute.starter.model.entity.ProductRating.ProductRatingImageEntity;
import com.hcmute.starter.model.entity.ProductRating.ProductRatingLikeEntity;
import com.hcmute.starter.model.entity.UserEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
public interface ProductRatingService {
    ProductRatingEntity saveRating(ProductRatingEntity entity);
    List<ProductRatingEntity> getAllRatingByProduct(ProductEntity product);
    List<ProductRatingEntity> getAllRatingByUser(UserEntity user);
    int countRatingLike(ProductRatingEntity entity);
    ProductRatingLikeEntity saveLike(ProductRatingLikeEntity productRatingLike);
    ProductRatingLikeEntity getLikeByRatingAndUser(ProductRatingEntity productRating,UserEntity user);
    void deleteLike(int id);
    void saveListRatingImage(List<String> urls,ProductRatingEntity ratingEntity);

    ProductRatingEntity getByUserAndProduct(UserEntity user, ProductEntity product);
    ProductRatingEntity getRatingById(int id);
    ProductRatingCommentEntity saveComment(ProductRatingCommentEntity commentEntity);

}
