package com.hcmute.starter.repository;

import com.hcmute.starter.model.entity.userAddress.CommuneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
@EnableJpaRepositories
public interface CommuneRepository extends JpaRepository<CommuneEntity,String> {
    List<CommuneEntity> findAllByDistrict(String districtId);
}
