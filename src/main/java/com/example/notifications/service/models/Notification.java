package com.example.notifications.service.models;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Notification {

    Long id;

    String message;

    NotificationStatus status;

    Long receiverId;
}
