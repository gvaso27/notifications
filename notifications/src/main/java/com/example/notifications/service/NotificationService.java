package com.example.notifications.service;

import com.example.notifications.service.models.Notification;
import com.example.notifications.service.models.NotificationStatus;

import java.util.List;

public interface NotificationService {

    void sendNotification(Notification notification);

    void updateStatus(Long id, NotificationStatus newStatus);

    List<Notification> getNotificationByStatus(NotificationStatus status);

}
