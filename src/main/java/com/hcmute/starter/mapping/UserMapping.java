package com.hcmute.starter.mapping;

import com.hcmute.starter.model.entity.UserEntity;
import com.hcmute.starter.model.payload.request.UserRequest.AddNewUserRequest;
import com.hcmute.starter.model.payload.request.UserRequest.UserInfoRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserMapping {
    public static UserEntity registerToEntity(AddNewUserRequest registerRequest) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        return new UserEntity(registerRequest.getPhone(), registerRequest.getPassword());
    }

}
