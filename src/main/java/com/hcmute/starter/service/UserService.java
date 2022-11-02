package com.hcmute.starter.service;

import com.hcmute.starter.model.entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Service
public interface UserService {
    UserEntity findByFullName(String fullname);
    UserEntity findById(UUID id);
    List<UserEntity> getAllUser(int page, int size);
    UserEntity saveUser(UserEntity user,String roleName);
    Boolean existsByFullName(String fullname);
    UserEntity findByPhone(String phone);
    Boolean existsByPhone(String phone);
    UserEntity saveInfo(UserEntity user);
    UserEntity findByEmail(String email);
    UserEntity updateActive(UserEntity user);
    UserEntity setStatus(UserEntity user,Boolean status);




}
