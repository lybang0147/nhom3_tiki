package com.hcmute.starter.service.Impl;

import com.hcmute.starter.model.entity.ShipEntity;
import com.hcmute.starter.repository.ShipRepository;
import com.hcmute.starter.service.ShipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor


public class ShipServiceImpl implements ShipService {

    final ShipRepository shipRepository;

    @Override
    public List<ShipEntity> getAll() {
        return shipRepository.findAll();
    }

    @Override
    public ShipEntity findShipById(int id) {
        Optional<ShipEntity> ship = shipRepository.findByShipId(id);
        if(ship.isEmpty()){
            return null;
        }
        return ship.get();
    }

    @Override
    public ShipEntity create(ShipEntity ship) {
        return shipRepository.save(ship);
    }

    @Override
    public ShipEntity update(ShipEntity ship) {
        return shipRepository.save(ship);
    }

    @Override
    public void delete(int id) {
        shipRepository.deleteById(id);
    }
}
