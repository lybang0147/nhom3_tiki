package com.hcmute.starter.model.entity.ProductRating;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;

@Entity
@Table(name="\"rating_images\"")
@RestResource(exported = false)
public class ProductRatingImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"id\"")
    private int id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "\"rating_id\"")
    private ProductRatingEntity productRating;
    @Column(name = "\"image_url\"")
    private String imageLink;

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

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public ProductRatingImageEntity() {
    }
}
