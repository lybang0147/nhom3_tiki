package com.hcmute.starter.repository;

import com.hcmute.starter.mapping.AddressMapping;
import com.hcmute.starter.model.entity.UserEntity;
import com.hcmute.starter.model.entity.userAddress.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Optional;
@EnableJpaRepositories
public interface AddressRepository extends JpaRepository<AddressEntity,String> {
    Optional<AddressEntity> findById(String id);

    @Override
    List<AddressEntity> findAll();



}
