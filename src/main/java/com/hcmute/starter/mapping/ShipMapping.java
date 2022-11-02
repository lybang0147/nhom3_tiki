package com.hcmute.starter.mapping;

import com.hcmute.starter.model.entity.PaymentEntity;
import com.hcmute.starter.model.entity.ShipEntity;
import com.hcmute.starter.model.payload.request.Payment.AddPaymentRequest;
import com.hcmute.starter.model.payload.request.Ship.AddShipRequest;
import com.hcmute.starter.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShipMapping {

    @Autowired
    ShipService shipService;

    public ShipEntity modelToEntity(AddShipRequest addShipRequest){
        ShipEntity newShip = new ShipEntity();
        newShip.setShipType(addShipRequest.getShipType());
        newShip.setShipPrice(addShipRequest.getShipPrice());
        return newShip;
    }

    public ShipEntity updateToEntity(AddShipRequest addShipRequest,int id){
        ShipEntity ship = shipService.findShipById(id);
        ship.setShipType(addShipRequest.getShipType());
        ship.setShipPrice(addShipRequest.getShipPrice());
        return ship;
    }
}
