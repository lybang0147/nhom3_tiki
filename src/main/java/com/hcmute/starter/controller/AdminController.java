package com.hcmute.starter.controller;

import com.hcmute.starter.model.entity.CategoryEntity;
import com.hcmute.starter.model.entity.UserEntity;
import com.hcmute.starter.model.payload.SuccessResponse;
import com.hcmute.starter.model.payload.response.Category.CategoryResponse;
import com.hcmute.starter.security.JWT.JwtUtils;
import com.hcmute.starter.service.CategoryService;
import com.hcmute.starter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;
@RestController
@RequestMapping("api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    @Autowired
    JwtUtils jwtUtils;
    @GetMapping("/user/all")
    public ResponseEntity<SuccessResponse> getAllUser(HttpServletRequest req, @RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "20")int size){
        String authorizationHeader = req.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String accessToken = authorizationHeader.substring("Bearer ".length());
            if(jwtUtils.validateExpiredToken(accessToken)){
                throw new BadCredentialsException("access token is expired");
            }
            UserEntity user=userService.findById(UUID.fromString(jwtUtils.getUserNameFromJwtToken(accessToken)));
            if(user==null)
                throw new BadCredentialsException("User not found");
            else{
                List<UserEntity> list = new ArrayList<>();
                list = userService.getAllUser(page,size);
                SuccessResponse response = new SuccessResponse();
                response.setStatus(HttpStatus.OK.value());
                response.setSuccess(true);
                response.setMessage("List User");
                response.getData().put("listUser",list);
                return new ResponseEntity<>(response,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
