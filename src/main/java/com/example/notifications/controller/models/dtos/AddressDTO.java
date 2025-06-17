package com.example.notifications.controller.models.dtos;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressDTO {

    Long id;

    @Pattern(regexp = "EMAIL|SMS|POSTAL|INVALID", message = "Type must be either EMAIL, SMS, POSTAL or INVALID")
    String addressType;

    @Size(min = 3, message = "Address must be at least 3 characters")
    String addressValue;

}
