package com.example.notifications.service.models;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer {

    Long id;

    String name;

    List<Address> addresses;

    List<NotificationPreferenceType> notificationPreferences;

}
