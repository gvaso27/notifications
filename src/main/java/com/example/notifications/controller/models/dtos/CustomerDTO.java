package com.example.notifications.controller.models.dtos;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerDTO {

    Long id;

    @Size(min = 4, message = "Name must be at least 3 characters")
    String name;

    List<AddressDTO> addresses;

    List<NotificationPreferenceDTO> notificationPreferences;

    List<NotificationDTO> notifications;

}
