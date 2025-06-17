package com.example.notifications.service.models;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UniqueAttribute {

    String id;

    String name;

    String addressType;

    String addressValue;

    String notificationPreferenceType;
}
