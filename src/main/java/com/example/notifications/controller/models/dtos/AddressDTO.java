package com.example.notifications.controller.models.dtos;

import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressDTO {

    Long id;

    @Pattern(regexp = "EMAIL|SMS|POSTAL|INVALID", message = "Type must be either EMAIL, SMS, POSTAL or INVALID")
    String addressType;

    String addressValue;

}
