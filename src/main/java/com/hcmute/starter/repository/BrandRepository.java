package com.hcmute.starter.repository;

import com.hcmute.starter.model.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.UUID;
@EnableJpaRepositories
public interface BrandRepository extends JpaRepository<BrandEntity, UUID> {

}
