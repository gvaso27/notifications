package com.example.notifications.controller.models;

import com.example.notifications.controller.models.dtos.NotificationPreferenceDTO;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddNotificationPreferenceInput {

    NotificationPreferenceDTO notificationPreferenceDTO;

    Long customerId;

}
