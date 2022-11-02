package com.hcmute.starter.repository;

import com.hcmute.starter.model.entity.userAddress.ProvinceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface ProvinceRepository extends JpaRepository<ProvinceEntity,String> {
}
