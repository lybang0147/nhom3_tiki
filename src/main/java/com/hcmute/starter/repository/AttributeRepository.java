package com.hcmute.starter.repository;

import com.hcmute.starter.model.entity.AttributeEntity;
import com.hcmute.starter.model.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories
public interface AttributeRepository extends JpaRepository<AttributeEntity, String> {
}
