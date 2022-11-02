package com.hcmute.starter.repository;

import com.hcmute.starter.model.entity.UserEntity;
import com.hcmute.starter.model.entity.VoucherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@EnableJpaRepositories
public interface VoucherRepository extends JpaRepository<VoucherEntity, UUID> {
    List<VoucherEntity> findAllByType(String type);
    List<VoucherEntity> findAllByStatus(boolean status);
    Optional<VoucherEntity> findByIdAndUserEntities(UUID id, UserEntity user);

}
