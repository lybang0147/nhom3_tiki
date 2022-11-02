package com.hcmute.starter.model.entity;


import lombok.NoArgsConstructor;
import org.springframework.data.rest.core.annotation.RestResource;
import javax.persistence.*;
import java.time.LocalDateTime;


@RestResource(exported = false)
@Entity
@Table(name = "\"user_notifications\"")
@NoArgsConstructor
public class UserNotificationEntity {

    public UserNotificationEntity(int notificationId, String message, int status, LocalDateTime date_create) {
        this.notificationId = notificationId;
        this.message = message;
        this.status = status;
        this.dateCreate = date_create;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"notification_id\"")
    private int notificationId;

    @Column(name = "\"message\"")
    private String message;
    @Column(name = "\"type\"")
    private String type;

    @Column(name = "\"status\"")
    private int status;

    @Column(name = "\"date_create\"")
    private LocalDateTime dateCreate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "\"uuid\"")
    private UserEntity user;


    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(LocalDateTime dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "{ message='" + message + '\'' +
                ", status=" + status +
                ", dateCreate=" + dateCreate +
                ", user=" + user.getId() +
                '}';
    }
}
