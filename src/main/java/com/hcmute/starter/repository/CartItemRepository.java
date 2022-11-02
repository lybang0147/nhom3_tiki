package com.hcmute.starter.repository;

import com.hcmute.starter.model.entity.CartEntity;
import com.hcmute.starter.model.entity.CartItemEntity;
import com.hcmute.starter.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Optional;
@EnableJpaRepositories
public interface CartItemRepository extends JpaRepository<CartItemEntity,Integer> {
    Optional<CartItemEntity> findByProductAndCart(ProductEntity product, CartEntity cart);
    Optional<CartItemEntity> findByIdAndCart(int id,CartEntity cart);
}
