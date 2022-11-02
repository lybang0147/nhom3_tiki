package com.hcmute.starter.repository;

import com.hcmute.starter.model.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

@EnableJpaRepositories
public interface PaymentRepository extends JpaRepository<PaymentEntity,Integer> {
    Optional<PaymentEntity> findByPaymentId(int payment_id);
}
