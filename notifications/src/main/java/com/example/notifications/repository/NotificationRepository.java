package com.example.notifications.repository;

import com.example.notifications.repository.model.NotificationEntity;
import com.example.notifications.repository.model.NotificationStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
    List<NotificationEntity> getNotificationEntityByStatus(NotificationStatusEntity status);
}
