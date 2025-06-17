package com.example.notifications.service;

import com.example.notifications.service.models.NotificationPreference;

import java.util.List;

public interface NotificationPreferencesService {

    void removeNotificationPreference(Long id);

    void addNotificationPreference(NotificationPreference  notificationPreference, Long customerId);

    List<NotificationPreference> getPreferenceByCustomerId(Long id);

}
