package com.hcmute.starter.service;

import com.hcmute.starter.model.entity.UserEntity;
import com.hcmute.starter.model.entity.UserNotificationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Component
@Service
public interface UserNotificationService {
    List<UserNotificationEntity> getAll();

    void saveNotification(UserNotificationEntity notification);

    UserNotificationEntity findNotificationById(int notification_id);

    List<UserNotificationEntity> findNotificationByUser(UserEntity user);

    List<UserNotificationEntity> findNotificationByUser(UserEntity user, int page, int size, String type);

    UserNotificationEntity create(UserNotificationEntity notification, UUID uuid);
    void delete(int notification_id);
}
