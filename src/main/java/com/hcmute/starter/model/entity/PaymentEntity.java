package com.hcmute.starter.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "\"payments\"")
@NoArgsConstructor
@RestResource(exported = false)
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"payment_id\"")
    private int paymentId;

    @Column(name = "\"payment_name\"")
    private String paymentName;

    @JsonIgnore
    @OneToMany(mappedBy = "paymentOrder",targetEntity = OrderEntity.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<OrderEntity> listOrder;

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    @Override
    public String toString() {
        return "PaymentEntity{" +
                "paymentId=" + paymentId +
                ", paymentName='" + paymentName + '\'' +
                '}';
    }
}
