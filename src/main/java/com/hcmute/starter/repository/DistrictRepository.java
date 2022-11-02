package com.hcmute.starter.repository;

import com.hcmute.starter.model.entity.userAddress.DistrictEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
@EnableJpaRepositories
public interface DistrictRepository extends JpaRepository<DistrictEntity,String> {
    List<DistrictEntity> findAllByProvince(String provinceId);
}
