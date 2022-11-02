package com.hcmute.starter.repository;

import com.hcmute.starter.model.entity.ShipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

@EnableJpaRepositories
public interface ShipRepository extends JpaRepository<ShipEntity,Integer> {

    Optional<ShipEntity> findByShipId(int ship_id);
}
