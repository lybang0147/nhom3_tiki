package com.hcmute.starter.repository;

import com.hcmute.starter.model.entity.userAddress.AddressTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface AddressTypeRepository extends JpaRepository<AddressTypeEntity,Integer> {
}
