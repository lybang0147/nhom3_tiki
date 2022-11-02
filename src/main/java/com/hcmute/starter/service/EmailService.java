package com.hcmute.starter.service;

import com.hcmute.starter.model.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public interface EmailService {
    public void sendForgetPasswordMessage(UserEntity user);

}

