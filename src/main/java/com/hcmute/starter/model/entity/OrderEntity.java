package com.hcmute.starter.model.entity;

import com.hcmute.starter.model.entity.userAddress.AddressEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@RestResource(exported = false)
@Entity
@Table(name = "\"orders\"")
@NoArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"order_id\"")
    private int orderId;

    @ManyToOne()
    @JoinColumn(name = "\"uuid\"")
    private UserEntity userOrder;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "\"cart_id\"")
    private CartEntity cartOrder;

    @Column(name = "\"name\"")
    private String name;

    @ManyToOne()
    @JoinColumn(name="\"user_address\"")
    private AddressEntity addressOrder;

    @Column(name = "\"created_date\"")
    private LocalDateTime createdDate;

    @ManyToOne()
    @JoinColumn(name = "\"payment_type\"")
    private PaymentEntity paymentOrder;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "\"ship_type\"")
    private ShipEntity shipOrder;

    @Column(name = "\"total\"")
    private double total;

    @Column(name = "\"del_status\"")
    private int delStatus;

    @Column(name = "\"expected_date\"")
    private LocalDate expectedDate;


    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public UserEntity getUserOrder() {
        return userOrder;
    }

    public void setUserOrder(UserEntity userOrder) {
        this.userOrder = userOrder;
    }

    public CartEntity getCartOrder() {
        return cartOrder;
    }

    public void setCartOrder(CartEntity cartOrder) {
        this.cartOrder = cartOrder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AddressEntity getAddressOrder() {
        return addressOrder;
    }

    public void setAddressOrder(AddressEntity addressOrder) {
        this.addressOrder = addressOrder;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public PaymentEntity getPaymentOrder() {
        return paymentOrder;
    }

    public void setPaymentOrder(PaymentEntity paymentOrder) {
        this.paymentOrder = paymentOrder;
    }

    public ShipEntity getShipOrder() {
        return shipOrder;
    }

    public void setShipOrder(ShipEntity shipOrder) {
        this.shipOrder = shipOrder;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(int delStatus) {
        this.delStatus = delStatus;
    }

    public LocalDate getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(LocalDate expectedDate) {
        this.expectedDate = expectedDate;
    }
}
