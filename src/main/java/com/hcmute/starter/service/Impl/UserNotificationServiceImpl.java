package com.hcmute.starter.service.Impl;

import com.hcmute.starter.model.entity.UserEntity;
import com.hcmute.starter.model.entity.UserNotificationEntity;
import com.hcmute.starter.repository.UserNotificationRepository;
import com.hcmute.starter.repository.UserRepository;
import com.hcmute.starter.service.UserNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@Transactional
@RequiredArgsConstructor
public class UserNotificationServiceImpl implements UserNotificationService {

    final UserNotificationRepository userNotificationRepository;
    final UserRepository userRepository;


    public List<UserNotificationEntity> getAll() {
        return userNotificationRepository.findAll();
    }
    @Override
    public void saveNotification(UserNotificationEntity notification){
        userNotificationRepository.save(notification);
    }

    @Override
    public UserNotificationEntity findNotificationById(int notification_id) {
        Optional<UserNotificationEntity> notification = userNotificationRepository.findByNotificationId(notification_id);
        if(notification.isEmpty()){
            return null;
        }
        return notification.get();
    }


    @Override
    public List<UserNotificationEntity> findNotificationByUser(UserEntity user) {
        if(userNotificationRepository.findAllByUser(user).isEmpty()){
            return null;
        }else {
            return userNotificationRepository.findAllByUser(user);
        }
    }
    @Override
    public List<UserNotificationEntity> findNotificationByUser(UserEntity user, int page, int size, String type){
        Pageable paging = PageRequest.of(page, size);
        Page<UserNotificationEntity> pagedResult = userNotificationRepository.findAllByUser(user.getId(),type,paging);
        return pagedResult.toList();
    }

    @Override
    public UserNotificationEntity create(UserNotificationEntity notification, UUID uuid) {
//        Optional<UserEntity> user = userRepository.findById(uuid);
//        List<UserEntity> UserSet=new ArrayList<>();
//        UserSet.add(user.get());
//        notification.setUser(UserSet.get(0));
//        return userNotificationRepository.save(notification);

        Optional<UserEntity> user = userRepository.findById(uuid);
        notification.setUser(user.get());
        return userNotificationRepository.save(notification);
    }

    @Override
    public void delete(int id) {
        userNotificationRepository.deleteById(id);
    }
}
