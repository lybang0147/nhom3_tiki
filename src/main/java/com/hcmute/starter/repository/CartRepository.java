package com.hcmute.starter.repository;

import com.hcmute.starter.model.entity.CartEntity;
import com.hcmute.starter.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;
@EnableJpaRepositories
public interface CartRepository extends JpaRepository<CartEntity,Integer> {
    Optional<CartEntity> findByUserAndStatus(UserEntity entity, Boolean status);
}
