package com.hcmute.starter.model.entity.ProductRating;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hcmute.starter.model.entity.ProductEntity;
import com.hcmute.starter.model.entity.UserEntity;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@RestResource(exported = false)
@Table(name = "\"product_ratings\"")
public class ProductRatingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"id\"")
    private int id;
    @ManyToOne
    @JoinColumn(name = "\"product_id\"")
    private ProductEntity product;
    @ManyToOne
    @JoinColumn(name ="\"user_id\"")
    private UserEntity user;
    @Column(name = "\"rating_point\"")
    private int ratingPoint;
    @Column(name = "\"message\"")
    private String message;
    @Column(name = "\"date\"")
    private LocalDateTime date;
    @OneToMany(mappedBy = "productRating",targetEntity = ProductRatingLikeEntity.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<ProductRatingLikeEntity> likeList;

    @OneToMany(mappedBy = "productRating",targetEntity = ProductRatingImageEntity.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<ProductRatingImageEntity> imageList;

    @OneToMany(mappedBy = "productRating",targetEntity = ProductRatingCommentEntity.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<ProductRatingCommentEntity> commentList;

    public List<ProductRatingCommentEntity> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<ProductRatingCommentEntity> commentList) {
        this.commentList = commentList;
    }

    public List<ProductRatingImageEntity> getImageList() {
        return imageList;
    }

    public void setImageList(List<ProductRatingImageEntity> imageList) {
        this.imageList = imageList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public int getRatingPoint() {
        return ratingPoint;
    }

    public void setRatingPoint(int ratingPoint) {
        this.ratingPoint = ratingPoint;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<ProductRatingLikeEntity> getLikeList() {
        return likeList;
    }

    public void setLikeList(List<ProductRatingLikeEntity> likeList) {
        this.likeList = likeList;
    }

    public ProductRatingEntity() {
    }

    public ProductRatingEntity(ProductEntity product, UserEntity user, int ratingPoint, String message, LocalDateTime date) {
        this.product = product;
        this.user = user;
        this.ratingPoint = ratingPoint;
        this.message = message;
        this.date = date;
    }
}
