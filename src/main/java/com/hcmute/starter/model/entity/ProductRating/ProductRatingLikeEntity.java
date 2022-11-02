package com.hcmute.starter.model.entity.ProductRating;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hcmute.starter.model.entity.UserEntity;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;

@Entity
@Table(name = "\"rating_product_like\"")
@RestResource(exported = false)
public class ProductRatingLikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"id\"")
    private int id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "\"rating_id\"")
    private ProductRatingEntity productRating;
    @ManyToOne
    @JoinColumn(name = "\"user_id\"")
    private UserEntity user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProductRatingEntity getProductRating() {
        return productRating;
    }

    public void setProductRating(ProductRatingEntity productRating) {
        this.productRating = productRating;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public ProductRatingLikeEntity() {
    }
}
