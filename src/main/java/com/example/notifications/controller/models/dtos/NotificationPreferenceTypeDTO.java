package com.example.notifications.controller.models.dtos;

import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationPreferenceTypeDTO {

    @Pattern(regexp = "EMAIL|SMS|POSTAL", message = "Type must be either EMAIL, SMS or POSTAL")
    String notificationPreferenceType;

}
