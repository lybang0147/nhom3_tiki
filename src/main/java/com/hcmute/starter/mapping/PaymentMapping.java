package com.hcmute.starter.mapping;

import com.hcmute.starter.model.entity.PaymentEntity;
import com.hcmute.starter.model.payload.request.Payment.AddPaymentRequest;
import com.hcmute.starter.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapping {

    @Autowired
    PaymentService paymentService;

    public PaymentEntity modelToEntity(AddPaymentRequest addPaymentRequest){
        PaymentEntity newPayment = new PaymentEntity();
        newPayment.setPaymentName(addPaymentRequest.getPayment());
        return newPayment;
    }

    public PaymentEntity updateToEntity(AddPaymentRequest addPaymentRequest,int id){
        PaymentEntity payment = paymentService.getPaymentById(id);
        payment.setPaymentName(addPaymentRequest.getPayment());
        return payment;
    }
}
