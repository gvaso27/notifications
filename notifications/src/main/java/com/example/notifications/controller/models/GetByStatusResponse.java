package com.example.notifications.controller.models;

import com.example.notifications.controller.models.dtos.NotificationDTO;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetByStatusResponse {
    List<NotificationDTO> notifications;
}
