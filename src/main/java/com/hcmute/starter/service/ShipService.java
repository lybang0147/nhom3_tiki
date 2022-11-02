package com.hcmute.starter.service;

import com.hcmute.starter.model.entity.PaymentEntity;
import com.hcmute.starter.model.entity.ShipEntity;

import java.util.List;

public interface ShipService {
    List<ShipEntity> getAll();
    ShipEntity findShipById(int id);

    ShipEntity create(ShipEntity ship);
    ShipEntity update(ShipEntity ship);
    void delete(int id);
}
