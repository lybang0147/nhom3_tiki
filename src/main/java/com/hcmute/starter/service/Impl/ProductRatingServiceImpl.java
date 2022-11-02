package com.hcmute.starter.service.Impl;

import com.hcmute.starter.model.entity.ImageProductEntity;
import com.hcmute.starter.model.entity.ProductEntity;
import com.hcmute.starter.model.entity.ProductRating.ProductRatingCommentEntity;
import com.hcmute.starter.model.entity.ProductRating.ProductRatingEntity;
import com.hcmute.starter.model.entity.ProductRating.ProductRatingImageEntity;
import com.hcmute.starter.model.entity.ProductRating.ProductRatingLikeEntity;
import com.hcmute.starter.model.entity.UserEntity;
import com.hcmute.starter.repository.ProductRatingCommentRepository;
import com.hcmute.starter.repository.ProductRatingImageRepository;
import com.hcmute.starter.repository.ProductRatingLikeRepository;
import com.hcmute.starter.repository.ProductRatingRepository;
import com.hcmute.starter.service.ProductRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductRatingServiceImpl implements ProductRatingService {
    final ProductRatingRepository productRatingRepository;
    final ProductRatingLikeRepository productRatingLikeRepository;
    final ProductRatingImageRepository productRatingImageRepository;
    final ProductRatingCommentRepository productRatingCommentRepository;
    @Override
    public ProductRatingEntity saveRating(ProductRatingEntity entity) {
        return productRatingRepository.save(entity);
    }

    @Override
    public List<ProductRatingEntity> getAllRatingByProduct(ProductEntity product) {
        List<ProductRatingEntity> list = productRatingRepository.getAllByProduct(product);
        return list;
    }

    @Override
    public List<ProductRatingEntity> getAllRatingByUser(UserEntity user) {
        List<ProductRatingEntity> list = productRatingRepository.getAllByUser(user);
        return list;
    }

    @Override
    public int countRatingLike(ProductRatingEntity entity) {
        List<ProductRatingLikeEntity> list = productRatingLikeRepository.findAllByProductRating(entity);
        return list.size();


    }

    @Override
    public ProductRatingLikeEntity saveLike(ProductRatingLikeEntity productRatingLike) {
        return productRatingLikeRepository.save(productRatingLike);
    }

    @Override
    public ProductRatingLikeEntity getLikeByRatingAndUser(ProductRatingEntity productRating,UserEntity user) {
        Optional<ProductRatingLikeEntity> likeEntity = productRatingLikeRepository.findByProductRatingAndUser(productRating,user);
        if (likeEntity.isEmpty())
            return null;
        return likeEntity.get();

    }

    @Override
    public void deleteLike(int id) {
        productRatingLikeRepository.deleteById(id);
    }

    @Override
    public void saveListRatingImage(List<String> urls, ProductRatingEntity ratingEntity) {

        for (String url : urls){
            ProductRatingImageEntity entity = new ProductRatingImageEntity();
            entity.setProductRating(ratingEntity);
            entity.setImageLink(url);
            productRatingImageRepository.save(entity);
        }


    }

    @Override
    public ProductRatingEntity getByUserAndProduct(UserEntity user, ProductEntity product) {
        Optional<ProductRatingEntity> productRating = productRatingRepository.getByUserAndProduct(user,product);
        if(productRating.isEmpty())
            return null;
        return productRating.get();
    }

    @Override
    public ProductRatingEntity getRatingById(int id) {
        Optional<ProductRatingEntity> productRating = productRatingRepository.findById(id);
        if(productRating.isEmpty())
            return null;
        return productRating.get();
    }

    @Override
    public ProductRatingCommentEntity saveComment(ProductRatingCommentEntity commentEntity) {
        return productRatingCommentRepository.save(commentEntity);
    }
}
