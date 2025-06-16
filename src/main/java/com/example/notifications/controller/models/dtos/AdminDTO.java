package com.example.notifications.controller.models.dtos;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminDTO {

    @Size(min = 4, message = "Name must be at least 3 characters")
    String username;

    String password;

}
