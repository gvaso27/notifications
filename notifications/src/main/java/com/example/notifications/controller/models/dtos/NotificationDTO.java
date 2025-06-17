package com.example.notifications.controller.models.dtos;

import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationDTO {

    String id;

    String message;

    @Pattern(regexp = "PENDING|FAILED|DELIVERED", message = "Type must be either PENDING, FAILED or DELIVERED")
    String status;

    String receiverId;
}
