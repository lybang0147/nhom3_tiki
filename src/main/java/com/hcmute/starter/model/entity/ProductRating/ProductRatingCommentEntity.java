package com.hcmute.starter.model.entity.ProductRating;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hcmute.starter.model.entity.UserEntity;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;

@Entity
@RestResource(exported = false)
@Table(name = "\"rating_comments\"")
public class ProductRatingCommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"id\"")
    private int id;
    @ManyToOne
    @JoinColumn(name = "\"user_id\"")
    private UserEntity user;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "\"rating_id\"")
    private ProductRatingEntity productRating;
    @Column(name = "\"comment\"")
    private String comment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public ProductRatingEntity getProductRating() {
        return productRating;
    }

    public void setProductRating(ProductRatingEntity productRating) {
        this.productRating = productRating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ProductRatingCommentEntity(UserEntity user, ProductRatingEntity productRating, String comment) {
        this.user = user;
        this.productRating = productRating;
        this.comment = comment;
    }

    public ProductRatingCommentEntity() {
    }
}
