package com.hcmute.starter.mapping;


import com.hcmute.starter.common.StatusMessageService;
import com.hcmute.starter.model.entity.UserEntity;
import com.hcmute.starter.model.entity.UserNotificationEntity;
import com.hcmute.starter.model.payload.request.Notification.AddNotificationRequest;
import com.hcmute.starter.model.payload.response.Notification.NotificationResponse;
import com.hcmute.starter.repository.UserRepository;
import com.hcmute.starter.service.UserNotificationService;
import com.hcmute.starter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class UserNotificationMapping {

    @Autowired
    UserNotificationService userNotificationService;

    @Autowired
    UserService userService;
    public UserNotificationEntity modelToEntity(AddNotificationRequest addNotificationRequest){
        UserNotificationEntity newNotification = new UserNotificationEntity();
        UserEntity user = userService.findById(addNotificationRequest.getIdUser());
        newNotification.setType(addNotificationRequest.getType());
        newNotification.setUser(user);
        newNotification.setStatus(1);
        newNotification.setDateCreate(LocalDateTime.now());
        newNotification.setMessage(addNotificationRequest.getMessage());
        return newNotification;
    }
    public NotificationResponse entityToResponse(UserNotificationEntity notification){
        NotificationResponse notificationResponse = new NotificationResponse();
        notificationResponse.setId(notification.getNotificationId());
        notificationResponse.setUserId(notification.getUser().getId());
        notificationResponse.setLink("");
        if(notification.getStatus()==1)
            notificationResponse.setSeen(false);
        else notificationResponse.setSeen(true);
        notificationResponse.setText(notification.getMessage());
        Date date = new Date(notification.getDateCreate().getYear()-1900,notification.getDateCreate().getMonth().getValue()-1,notification.getDateCreate().getDayOfMonth());
        notificationResponse.setDate(date);
        notificationResponse.setCreatedAt(date);
        notificationResponse.setType(notification.getType());
        notificationResponse.setUpdatedAt(date);
        return notificationResponse;
    }
}
