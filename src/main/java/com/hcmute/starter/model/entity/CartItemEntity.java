package com.hcmute.starter.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
@RestResource(exported = false)
@Entity
@Table(name = "\"cart_items\"")
public class CartItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"id\"")
    private int id;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name ="\"cart_id\"")
    private CartEntity cart;
    @ManyToOne
    @JoinColumn(name="\"product_id\"")
    private ProductEntity product;

    @Column(name = "\"quantity\"")
    private int quantity;


    public CartEntity getCart() {
        return cart;
    }

    public CartItemEntity(CartEntity cart, ProductEntity product, int quantity) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }

    public void setCart(CartEntity cart) {
        this.cart = cart;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }



    public CartItemEntity() {
    }
}
