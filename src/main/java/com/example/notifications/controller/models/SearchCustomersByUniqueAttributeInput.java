package com.example.notifications.controller.models;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchCustomersByUniqueAttributeInput {

    String id;

    @Size(min = 3, message = "Name must be at least 3 characters")
    String name;

    @Pattern(regexp = "EMAIL|SMS|POSTAL|INVALID", message = "Type must be either EMAIL, SMS, POSTAL or INVALID")
    String addressType;

    @Size(min = 3, message = "Address must be at least 3 characters")
    String addressValue;

    @Pattern(regexp = "EMAIL|SMS|POSTAL", message = "Type must be either EMAIL, SMS or POSTAL")
    String notificationPreferenceType;

    @Pattern(regexp = "ADDRESS_TYPE|ADDRESS_VALUE|PREFERENCE_TYPE|NAME|ID",
            message = "Type must be either ADDRESS_TYPE, ADDRESS_VALUE, PREFERENCE_TYPE, NAME or ID")
    String filterType;

}
