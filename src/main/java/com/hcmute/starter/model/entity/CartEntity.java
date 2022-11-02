package com.hcmute.starter.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.rest.core.annotation.RestResource;


import javax.persistence.*;
import java.util.Collection;
import java.util.List;
@RestResource(exported = false)
@Entity
@Table(name = "\"carts\"")
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"id\"")
    private int id;
    @Column(name = "\"total\"")
    private double total;
    @Column(name = "\"status\"")
    private Boolean status;
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "\"uuid\"")
    private UserEntity user;

    @OneToMany(mappedBy = "cart",targetEntity = CartItemEntity.class,fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<CartItemEntity> cartItem;

    @JsonIgnore
    @OneToOne(mappedBy = "cartOrder",targetEntity = OrderEntity.class)
    private OrderEntity order;

    public CartEntity(Double total, Boolean status, UserEntity user) {
        this.total = total;
        this.status = status;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
    public CartEntity() {
    }

    public List<CartItemEntity> getCartItem() {
        return cartItem;
    }

    public void setCartItem(List<CartItemEntity> cartItem) {
        this.cartItem = cartItem;
    }

}
