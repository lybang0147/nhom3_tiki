package com.hcmute.starter.mapping;


import com.hcmute.starter.model.entity.*;
import com.hcmute.starter.model.entity.userAddress.AddressEntity;
import com.hcmute.starter.model.payload.request.Order.AddOrderRequest;
import com.hcmute.starter.security.JWT.JwtUtils;
import com.hcmute.starter.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class OrderMapping {

    @Autowired
    AddressService addressService;
    @Autowired
    PaymentService paymentService;
    @Autowired
    ShipService shipService;


    public OrderEntity ModelToEntity(AddOrderRequest addOrderRequest){
        AddressEntity address = addressService.findById(addOrderRequest.getIdAddress());
        PaymentEntity payment = paymentService.getPaymentById(addOrderRequest.getIdPayment());
        ShipEntity ship = shipService.findShipById(addOrderRequest.getIdShip());

        OrderEntity order = new OrderEntity();
        order.setAddressOrder(address);
        order.setPaymentOrder(payment);
        order.setShipOrder(ship);
        order.setCreatedDate(LocalDateTime.now());
        order.setDelStatus(0);
        order.setExpectedDate(LocalDate.from(order.getCreatedDate().plusDays(2)));
        return order;
    }
}
