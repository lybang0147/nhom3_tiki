package com.hcmute.starter.repository;

import com.hcmute.starter.model.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

@EnableJpaRepositories
public interface CountryRepository extends JpaRepository<CountryEntity, String> {
    Optional<CountryEntity> findByName(String name);
}
