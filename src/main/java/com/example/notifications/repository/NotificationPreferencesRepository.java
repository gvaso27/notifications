package com.example.notifications.repository;

import com.example.notifications.repository.model.NotificationPreferenceTypeEntity;
import com.example.notifications.repository.model.NotificationPreferencesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationPreferencesRepository  extends JpaRepository<NotificationPreferencesEntity, Long> {

    List<NotificationPreferencesEntity> findAllByNotificationType(NotificationPreferenceTypeEntity notificationType);
    
}
