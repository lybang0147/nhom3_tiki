package com.hcmute.starter.mapping;

import com.hcmute.starter.model.entity.ProductEntity;
import com.hcmute.starter.model.entity.ProductRating.ProductRatingEntity;
import com.hcmute.starter.model.entity.UserEntity;
import com.hcmute.starter.model.payload.request.ProductRatingRequest.AddNewRatingRequest;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class ProductRatingMapping {
    public static ProductRatingEntity addReqToEntity(AddNewRatingRequest addNewRatingRequest, ProductEntity product, UserEntity user)
    {
        ProductRatingEntity productRating = new ProductRatingEntity();
        productRating.setProduct(product);
        productRating.setRatingPoint(addNewRatingRequest.getRatingPoint());
        productRating.setMessage(addNewRatingRequest.getMessage());
        productRating.setDate(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));
        productRating.setUser(user);
        return productRating;
    }
}

