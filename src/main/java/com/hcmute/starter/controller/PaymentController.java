package com.hcmute.starter.controller;


import com.hcmute.starter.mapping.PaymentMapping;
import com.hcmute.starter.model.entity.PaymentEntity;
import com.hcmute.starter.model.entity.UserNotificationEntity;
import com.hcmute.starter.model.payload.SuccessResponse;
import com.hcmute.starter.model.payload.request.Notification.AddNotificationRequest;
import com.hcmute.starter.model.payload.request.Payment.AddPaymentRequest;
import com.hcmute.starter.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    PaymentMapping paymentMapping;

    @GetMapping("/list")
    public ResponseEntity<SuccessResponse> getAllPayment(){
        List<PaymentEntity> list = paymentService.getAll();
        SuccessResponse response = new SuccessResponse();

        response.setStatus(HttpStatus.OK.value());
        response.setMessage("successful");
        response.setSuccess(true);
        for(PaymentEntity payment : list){
            response.getData().put("Payment "+ payment.getPaymentId(),payment.getPaymentName());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<SuccessResponse> getPaymentById(@PathVariable("id")int id){
        PaymentEntity payment = paymentService.getPaymentById(id);
        SuccessResponse response=new SuccessResponse();
        if(payment==null){
            response.setMessage("Payment not found");
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setSuccess(false);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        }
        else{
            response.setMessage("Successful");
            response.setSuccess(true);
            response.setStatus(HttpStatus.OK.value());
            response.getData().put("Payment " + payment.getPaymentId(),payment.getPaymentName());
            return new ResponseEntity<>(response, HttpStatus.OK);

        }
    }

    @PostMapping("/create")
    public ResponseEntity<SuccessResponse> createNotification(@RequestBody AddPaymentRequest request){


        PaymentEntity payment = paymentMapping.modelToEntity(request);
        SuccessResponse response = new SuccessResponse();
        try {
            paymentService.create(payment);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("add payment successful");
            response.setSuccess(true);
            response.getData().put("Payment",payment.toString());
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse> updatePayment(@RequestBody AddPaymentRequest request,@PathVariable("id") int id){


        PaymentEntity payment = paymentMapping.updateToEntity(request,id);
        SuccessResponse response = new SuccessResponse();
        try {
            paymentService.update(payment);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("update payment successful");
            response.setSuccess(true);
            response.getData().put("Payment",payment.toString());
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> deletePayment(@PathVariable("id")int id) throws Exception{
        SuccessResponse response = new SuccessResponse();
        try {
            paymentService.delete(id);
            response.setMessage("Delete payment success");
            response.setStatus(HttpStatus.OK.value());
            response.setSuccess(true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            throw new Exception(e.getMessage() + "\nDelete Payment fail");
        }
    }
}
