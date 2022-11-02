package com.hcmute.starter.controller;


import com.hcmute.starter.model.entity.DiscountProgramEntity;
import com.hcmute.starter.model.entity.UserEntity;
import com.hcmute.starter.model.entity.VoucherEntity;
import com.hcmute.starter.model.payload.SuccessResponse;
import com.hcmute.starter.model.payload.request.DiscountProgram.DiscountProgramRequest;
import com.hcmute.starter.security.JWT.JwtUtils;
import com.hcmute.starter.service.DiscountProgramService;
import com.hcmute.starter.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class DiscountProgramController {
    private final UserService userService;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    private final DiscountProgramService discountProgramService;
    @PostMapping("/admin/discountProgram/insert")
    public ResponseEntity<SuccessResponse> addDiscountProgram(HttpServletRequest req, @RequestBody @Valid DiscountProgramRequest request) {
        String authorizationHeader = req.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String accessToken = authorizationHeader.substring("Bearer ".length());
            if(jwtUtils.validateExpiredToken(accessToken)){
                throw new BadCredentialsException("access token is  expired");
            }
            UserEntity user=userService.findById(UUID.fromString(jwtUtils.getUserNameFromJwtToken(accessToken)));
            if(user.getId().toString().length()<1){
                throw new BadCredentialsException("User not found");
            }
            else{
                if (discountProgramService.existsByName(request.getName())) {
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                }
                DiscountProgramEntity discountProgram = new DiscountProgramEntity(request.getName(), request.getPercent(), request.getFromDate(), request.getToDate(), request.getDescription());
                SuccessResponse response = new SuccessResponse();
                try {
                    discountProgramService.saveDiscountProgram(discountProgram);
                    response.setStatus(HttpStatus.OK.value());
                    response.setMessage("successfully add seller");
                    response.setSuccess(true);
                    response.getData().put("name", discountProgram.getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @GetMapping("/admin/discountProgram/all")
    public ResponseEntity<SuccessResponse> getAllDiscountProgram(HttpServletRequest req) {
        String authorizationHeader = req.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String accessToken = authorizationHeader.substring("Bearer ".length());
            if(jwtUtils.validateExpiredToken(accessToken)){
                throw new BadCredentialsException("access token is  expired");
            }
            UserEntity user=userService.findById(UUID.fromString(jwtUtils.getUserNameFromJwtToken(accessToken)));
            if(user.getId().toString().length()<1){
                throw new BadCredentialsException("User not found");
            }
            else{
                SuccessResponse response = new SuccessResponse();
                try {
                    List<DiscountProgramEntity> discountProgramList = discountProgramService.getAllDiscountProgram();
                    response.setStatus(HttpStatus.OK.value());
                    response.setMessage("successfully get all programs");
                    response.setSuccess(true);
                    response.getData().put("discount_programs", discountProgramList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    //
    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse> getDiscountProgramByName(@PathVariable Long id) {
        DiscountProgramEntity discountProgram = discountProgramService.findByDiscountId(id);
        SuccessResponse response = new SuccessResponse();
        if (discountProgram == null) {
            response.setMessage("Program not found");
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setSuccess(false);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            response.setMessage("Get user success");
            response.setSuccess(true);
            response.setStatus(HttpStatus.OK.value());
            response.getData().put("discount_program", discountProgram);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

    }
    //update
    @PutMapping("admin/discountProgram/{id}")
    ResponseEntity<SuccessResponse> updateDiscountProgram(HttpServletRequest req,@RequestBody DiscountProgramEntity newDiscountProgram, @PathVariable Long id) {
        String authorizationHeader = req.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String accessToken = authorizationHeader.substring("Bearer ".length());
            if(jwtUtils.validateExpiredToken(accessToken)){
                throw new BadCredentialsException("access token is  expired");
            }
            UserEntity user=userService.findById(UUID.fromString(jwtUtils.getUserNameFromJwtToken(accessToken)));
            if(user.getId().toString().length()<1){
                throw new BadCredentialsException("User not found");
            }
            else{
                DiscountProgramEntity updateDiscountProgram = discountProgramService.findByDiscountId(id);
                SuccessResponse response = new SuccessResponse();
                if (updateDiscountProgram == null) {
                    response.setMessage("Program not found");
                    response.setStatus(HttpStatus.NOT_FOUND.value());
                    response.setSuccess(false);
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

                } else {
                    try {
                        updateDiscountProgram.setName(newDiscountProgram.getName());
                        updateDiscountProgram.setPercent(newDiscountProgram.getPercent());
                        updateDiscountProgram.setFromDate(newDiscountProgram.getFromDate());
                        updateDiscountProgram.setToDate(newDiscountProgram.getToDate());
                        updateDiscountProgram.setDescription(newDiscountProgram.getDescription());
                        discountProgramService.saveDiscountProgram(updateDiscountProgram);
                        response.setStatus(HttpStatus.OK.value());
                        response.setMessage("successfully update program");
                        response.setSuccess(true);
                        response.getData().put("discount_programs", discountProgramService.findByName(newDiscountProgram.getName()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    //DELETE
    @DeleteMapping("admin/discountProgram/{id}")
    ResponseEntity<SuccessResponse> deleteProduct(HttpServletRequest req,@PathVariable Long id) {
        String authorizationHeader = req.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String accessToken = authorizationHeader.substring("Bearer ".length());
            if(jwtUtils.validateExpiredToken(accessToken)){
                throw new BadCredentialsException("access token is  expired");
            }
            UserEntity user=userService.findById(UUID.fromString(jwtUtils.getUserNameFromJwtToken(accessToken)));
            if(user.getId().toString().length()<1){
                throw new BadCredentialsException("User not found");
            }
            else{
                DiscountProgramEntity deleteDiscountProgram = discountProgramService.findByDiscountId(id);
                SuccessResponse response = new SuccessResponse();
                if (deleteDiscountProgram == null) {
                    response.setMessage("Program not found");
                    response.setStatus(HttpStatus.NOT_FOUND.value());
                    response.setSuccess(false);
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                } else {
                    try {
                        discountProgramService.delete(deleteDiscountProgram.getId());
                        response.setStatus(HttpStatus.OK.value());
                        response.setMessage("successfully delete program");
                        response.setSuccess(true);
                        response.getData().put("discount_program", deleteDiscountProgram);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}