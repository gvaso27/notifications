package com.example.notifications.controller.models.DTOs;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import jakarta.validation.constraints.Pattern;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerDTO {

    String name;

    @Pattern(regexp = "EMAIL|SMS|POSTAL|INVALID", message = "Type must be either EMAIL, SMS, POSTAL or INVALID")
    String addressType;

    String addressValue;

    @Pattern(regexp = "EMAIL|SMS|POSTAL", message = "Type must be either EMAIL, SMS or POSTAL")
    String notificationPreferenceType;

}
