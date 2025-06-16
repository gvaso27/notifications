package com.example.notifications.repository;

import com.example.notifications.repository.model.NotificationPreferencesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationPreferencesRepository  extends JpaRepository<NotificationPreferencesEntity, Long> {
}
