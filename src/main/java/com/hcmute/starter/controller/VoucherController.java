package com.hcmute.starter.controller;


import com.hcmute.starter.model.entity.UserEntity;
import com.hcmute.starter.model.entity.VoucherEntity;
import com.hcmute.starter.model.payload.SuccessResponse;

import com.hcmute.starter.security.JWT.JwtUtils;
import com.hcmute.starter.service.UserService;
import com.hcmute.starter.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;

@Component
@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class VoucherController {
    private final VoucherService voucherService;
    private final UserService userService;
    @Autowired
    JwtUtils jwtUtils;
    @GetMapping("/voucher/all")
    public ResponseEntity<SuccessResponse> showAllVoucherPublic() {
        List<VoucherEntity> listVoucher = voucherService.findAllVoucherPublic();
        if(listVoucher.size() == 0)
            return new ResponseEntity<>(new SuccessResponse(false,HttpStatus.FOUND.value(),"Voucher is empty",null), HttpStatus.FOUND);
        SuccessResponse response = new SuccessResponse();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Query Successfully");
        response.setSuccess(true);
        response.getData().put("Voucher List", listVoucher);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/user/get/voucher/{id}")
    public ResponseEntity<SuccessResponse> getVoucher(HttpServletRequest request,@PathVariable UUID id){
        String authorizationHeader = request.getHeader(AUTHORIZATION);
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
                VoucherEntity voucher = voucherService.findById(id);
                SuccessResponse response = new SuccessResponse();
                if(voucher == null || !voucher.isStatus()) {
                    response.setStatus(HttpStatus.NOT_FOUND.value());
                    response.setMessage("Voucher Not Found");
                    response.setSuccess(false);
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                }
                Set<UserEntity> userEntitySet = new HashSet<>();
                userEntitySet.add(user);
                voucher.setAmount(voucher.getAmount()-1);
                voucher.setUserEntities(userEntitySet);
                voucherService.saveVoucher(voucher);
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Get Voucher Successfully");
                response.setSuccess(true);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @GetMapping("/admin/voucher/all")
    public ResponseEntity<SuccessResponse> showAllVoucher(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
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
                List<VoucherEntity> listVoucher = voucherService.findAllVoucher();
                if(listVoucher.size() == 0)
                    return new ResponseEntity<>(new SuccessResponse(false,HttpStatus.FOUND.value(),"Voucher is empty",null), HttpStatus.FOUND);
                SuccessResponse response = new SuccessResponse();
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Query Successfully");
                response.setSuccess(true);
                response.getData().put("Voucher List", listVoucher);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @PostMapping("/admin/add/voucher/insert")
    private ResponseEntity<SuccessResponse> insertVoucher(HttpServletRequest request,@RequestBody VoucherEntity newVoucher){
        String authorizationHeader = request.getHeader(AUTHORIZATION);
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
                List<VoucherEntity> foundVoucher = voucherService.foundVoucher(newVoucher.getType());
                if(foundVoucher.size() > 0)  {
                    SuccessResponse response = new SuccessResponse();
                    response.setStatus(HttpStatus.FOUND.value());
                    response.setMessage("Voucher found");
                    response.setSuccess(false);
                    return new ResponseEntity<>(response, HttpStatus.FOUND);
                }
                voucherService.saveVoucher(newVoucher);
                SuccessResponse response = new SuccessResponse();
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Add voucher successfully");
                response.setSuccess(true);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/admin/voucher/update/{id}")
    private ResponseEntity<SuccessResponse> updateVoucher(HttpServletRequest request,@PathVariable UUID id, @RequestBody VoucherEntity newVoucher, BindingResult errors) throws Exception {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
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
                VoucherEntity voucher = voucherService.findById(id);
                if(voucher != null){
                    List<VoucherEntity> foundVoucher = voucherService.foundVoucher(newVoucher.getType());
                    if(foundVoucher.size() > 0) {
                        SuccessResponse response = new SuccessResponse();
                        response.setStatus(HttpStatus.FOUND.value());
                        response.setMessage("Voucher found");
                        response.setSuccess(false);
                        return new ResponseEntity<>(response, HttpStatus.FOUND);
                    }
                    voucher.setId(id);
                    voucher.setAmount(newVoucher.getAmount());
                    voucher.setCreatedAt(newVoucher.getCreatedAt());
                    voucher.setExpiredDate(newVoucher.getExpiredDate());
                    voucher.setToDate(newVoucher.getToDate());
                    voucherService.saveVoucher(voucher);
                    SuccessResponse response = new SuccessResponse();
                    response.setStatus(HttpStatus.OK.value());
                    response.setMessage("Update voucher successfully");
                    response.setSuccess(true);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }else {
                    SuccessResponse response = new SuccessResponse();
                    response.setStatus(HttpStatus.NOT_FOUND.value());
                    response.setMessage("Voucher not found");
                    response.setSuccess(false);
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @DeleteMapping("/admin/voucher/{id}")
    @ResponseBody
    public ResponseEntity<SuccessResponse> deleteVoucher(HttpServletRequest request,@PathVariable UUID id) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
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
                VoucherEntity voucher = voucherService.findById(id);
                SuccessResponse response = new SuccessResponse();
                if(voucher != null) {
                    voucherService.deleteVoucher(id);
                    response.setStatus(HttpStatus.OK.value());
                    response.setMessage("Delete Voucher Successfully");
                    response.setSuccess(true);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }else {
                    response.setStatus(HttpStatus.NOT_FOUND.value());
                    response.setMessage("Delete voucher fail");
                    response.setSuccess(true);
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @PostMapping("/admin/send/voucher/{id}")
    public ResponseEntity<SuccessResponse> sendVoucher(HttpServletRequest request,@PathVariable UUID id,@RequestParam UUID idUser){
        String authorizationHeader = request.getHeader(AUTHORIZATION);
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
                VoucherEntity voucher = voucherService.findById(id);
                SuccessResponse response = new SuccessResponse();
                if(voucher == null) {
                    response.setStatus(HttpStatus.NOT_FOUND.value());
                    response.setMessage("Voucher Not Found");
                    response.setSuccess(false);
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                }
                Set<UserEntity> userEntitySet = new HashSet<>();
                UserEntity userAcc = userService.findById(idUser);
                if(userAcc == null)
                    return new ResponseEntity<>(new SuccessResponse(false,HttpStatus.NOT_FOUND.value(), "User not found",null),HttpStatus.NOT_FOUND);
                userEntitySet.add(userAcc);
                voucher.setAmount(voucher.getAmount()-1);
                voucher.setUserEntities(userEntitySet);
                voucherService.saveVoucher(voucher);
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Send Voucher Successfully");
                response.setSuccess(true);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}




