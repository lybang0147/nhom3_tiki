package com.hcmute.starter.repository;

import com.hcmute.starter.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@EnableJpaRepositories
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByFullName(String fullName);
    Boolean existsByFullName(String fullName);
    Page<UserEntity> findAll(Pageable pageable);
    @Override
    List<UserEntity> findAll();
    Optional<UserEntity> findByPhone(String phone);
    Boolean existsByPhone(String phone);
    Optional<UserEntity> findByEmail(String email);


}
