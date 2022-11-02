package com.hcmute.starter.repository;

import com.hcmute.starter.model.entity.UserEntity;
import com.hcmute.starter.model.entity.UserNotificationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@EnableJpaRepositories
public interface UserNotificationRepository extends JpaRepository<UserNotificationEntity, Integer> {

    Optional<UserNotificationEntity> findByNotificationId(int notification_id);
    List<UserNotificationEntity> findAllByUser(UserEntity user);
    @Query(value = "SELECT * FROM user_notifications WHERE uuid = ?1 and type = ?2",
            countQuery = "SELECT count(*) FROM user_notifications WHERE uuid = ?1 and type = ?2",
            nativeQuery = true)
    Page<UserNotificationEntity> findAllByUser(UUID uuid, String type, Pageable paging);
}
