package com.hcmute.starter.repository;

import com.hcmute.starter.model.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity,Integer> {
    Optional<OrderEntity> findByOrderId(int id);
}
