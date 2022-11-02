package com.hcmute.starter.controller;


import com.hcmute.starter.mapping.ShipMapping;
import com.hcmute.starter.model.entity.PaymentEntity;
import com.hcmute.starter.model.entity.ShipEntity;
import com.hcmute.starter.model.payload.SuccessResponse;
import com.hcmute.starter.model.payload.request.Payment.AddPaymentRequest;
import com.hcmute.starter.model.payload.request.Ship.AddShipRequest;
import com.hcmute.starter.service.ShipService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/ship")
@RequiredArgsConstructor
public class ShipController {

    private final ShipService shipService;

    @Autowired
    final ShipMapping shipMapping;

    @GetMapping("/list")
    public ResponseEntity<SuccessResponse> getAllShipType(){
        List<ShipEntity> list = shipService.getAll();
        SuccessResponse response = new SuccessResponse();

        response.setStatus(HttpStatus.OK.value());
        response.setMessage("successful");
        response.setSuccess(true);
        for(ShipEntity ship : list){
            response.getData().put("Ship Type "+ ship.getShipId(),ship.toString());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<SuccessResponse> getShipTypeById(@PathVariable("id")int id){
        ShipEntity ship = shipService.findShipById(id);
        SuccessResponse response=new SuccessResponse();
        if(ship==null){
            response.setMessage("Ship Type not found");
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setSuccess(false);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        }
        else{
            response.setMessage("Successful");
            response.setSuccess(true);
            response.setStatus(HttpStatus.OK.value());
            response.getData().put("Ship Type " + ship.getShipId(),ship.toString());
            return new ResponseEntity<>(response, HttpStatus.OK);

        }
    }

    @PostMapping("/create")
    public ResponseEntity<SuccessResponse> createShipType(@RequestBody AddShipRequest request){


        ShipEntity ship = shipMapping.modelToEntity(request);
        SuccessResponse response = new SuccessResponse();
        try {
            shipService.create(ship);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("add ship type successful");
            response.setSuccess(true);
            response.getData().put("Ship Type",ship.toString());
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse> updateShipType(@RequestBody AddShipRequest request,@PathVariable("id") int id){


        ShipEntity ship = shipMapping.updateToEntity(request,id);
        SuccessResponse response = new SuccessResponse();
        try {
            shipService.update(ship);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("update ship type successful");
            response.setSuccess(true);
            response.getData().put("Ship Type",ship.toString());
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> deleteShipTypeById(@PathVariable("id")int id) throws Exception{
        SuccessResponse response = new SuccessResponse();
        try {
            shipService.delete(id);
            response.setMessage("Delete ship type success");
            response.setStatus(HttpStatus.OK.value());
            response.setSuccess(true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            throw new Exception(e.getMessage() + "\nDelete Ship type fail");
        }
    }
}
