package com.hcmute.starter.service;


import com.hcmute.starter.model.entity.OrderEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
public interface OrderService {
    OrderEntity findById(int id);
    List<OrderEntity> getAll();
    OrderEntity save(OrderEntity order);
    void delete(int id);

    long countOrder();

    double countPayMoney(OrderEntity order);
}
