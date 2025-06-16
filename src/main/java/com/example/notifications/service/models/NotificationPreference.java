package com.example.notifications.service.models;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationPreference {

    Long id;

    NotificationPreferenceType notificationPreferenceType;
}
