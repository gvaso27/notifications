package com.example.notifications.controller.models;

import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateStatusInput {

    String notificationId;

    @Pattern(regexp = "FAILED|DELIVERED", message = "Type must be either FAILED or DELIVERED")
    String status;
}
