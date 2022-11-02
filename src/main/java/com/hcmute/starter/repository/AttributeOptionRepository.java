package com.hcmute.starter.repository;

import com.hcmute.starter.model.entity.AttributeOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface AttributeOptionRepository extends JpaRepository<AttributeOptionEntity, String> {
}
