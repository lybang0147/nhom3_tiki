package com.hcmute.starter.controller;


import com.hcmute.starter.mapping.UserNotificationMapping;
import com.hcmute.starter.model.entity.UserEntity;
import com.hcmute.starter.model.entity.UserNotificationEntity;
import com.hcmute.starter.model.payload.SuccessResponse;
import com.hcmute.starter.model.payload.request.Notification.AddNotificationRequest;
import com.hcmute.starter.security.JWT.JwtUtils;
import com.hcmute.starter.service.UserNotificationService;
import com.hcmute.starter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("api/notification")
@RequiredArgsConstructor
public class UserNotificationController {

    private final UserNotificationService userNotificationService;
    private final UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserNotificationMapping userNotificationMapping;


    @GetMapping("/list")
    public ResponseEntity<SuccessResponse> getAllNotification(){
        List<UserNotificationEntity> list = userNotificationService.getAll();

        SuccessResponse response = new SuccessResponse();
//        Map<Integer,UserNotificationEntity> map = (Map<Integer, UserNotificationEntity>) userNotificationService.getAll();

        response.setStatus(HttpStatus.OK.value());
        response.setMessage("successful");
        response.setSuccess(true);

//        for(Map.Entry<Integer, UserNotificationEntity> item : map.entrySet()){
//            response.getData().put(String.valueOf(item.getValue().getNotificationId()),item.getValue().getMessage());
//        }
        for(UserNotificationEntity notification : list){
            response.getData().put("Notification "+ notification.getNotificationId(),notification.toString());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<SuccessResponse> getNotificationById(@PathVariable("id")int id){
        UserNotificationEntity notification = userNotificationService.findNotificationById(id);
        SuccessResponse response=new SuccessResponse();
        if(notification==null){
            response.setMessage("Notification not found");
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setSuccess(false);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        }
        else{
            response.setMessage("Successful");
            response.setSuccess(true);
            response.setStatus(HttpStatus.OK.value());
            response.getData().put("notification " + notification.getNotificationId(),notification.getMessage());
            return new ResponseEntity<>(response, HttpStatus.OK);

        }
    }


    @PostMapping("/create")
    public ResponseEntity<SuccessResponse> createNotification(@RequestBody AddNotificationRequest request){
        UserNotificationEntity notification = userNotificationMapping.modelToEntity(request);
        SuccessResponse response = new SuccessResponse();
        try {
            userNotificationService.create(notification,request.getIdUser());
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("add notification successful");
            response.setSuccess(true);
            response.getData().put("notification",notification.getMessage());
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<SuccessResponse> getUserNotification(HttpServletRequest httpServletRequest) throws Exception {
        SuccessResponse response = new SuccessResponse();
        String authorizationHeader = httpServletRequest.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String accessToken = authorizationHeader.substring("Bearer ".length());
            if (jwtUtils.validateExpiredToken(accessToken) == true) {
                throw new BadCredentialsException("access token is  expired");
            }
            try {
                UserEntity user = userService.findByPhone(jwtUtils.getUserNameFromJwtToken(accessToken));
                List<UserNotificationEntity> userListNotification = userNotificationService.findNotificationByUser(user);
                if (userListNotification.isEmpty()) {
                    response.setMessage("User have no notification");
                    response.setStatus(HttpStatus.NOT_FOUND.value());
                    response.setSuccess(false);
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                }
                response.setMessage("User Notification");
                response.setSuccess(true);
                response.setStatus(HttpStatus.OK.value());
                for (UserNotificationEntity notification : userListNotification) {
                    response.getData().put("Notification " + notification.getNotificationId(), notification.toString());
                }
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (Exception e) {
                throw new Exception(e.getMessage() + "\n Get Notification Fail");
            }
        } else
        {
            throw new BadCredentialsException("access token is missing!!!");
        }
    }

    @DeleteMapping("id")
    public ResponseEntity<SuccessResponse> deleteNotification(@PathVariable("id")int id) throws Exception{
        SuccessResponse response = new SuccessResponse();
        try {
            userNotificationService.delete(id);
            response.setMessage("Delete notification success");
            response.setStatus(HttpStatus.OK.value());
            response.setSuccess(true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            throw new Exception(e.getMessage() + "\nDelete notification fail");
        }
    }
}
