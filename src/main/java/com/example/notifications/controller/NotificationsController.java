package com.example.notifications.controller;

import com.example.notifications.controller.models.UpdateStatusInput;
import com.example.notifications.controller.models.dtos.NotificationDTO;
import com.example.notifications.service.NotificationServiceImpl;
import com.example.notifications.service.models.Notification;
import com.example.notifications.service.models.NotificationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers/notification")
public class NotificationsController {

    @Autowired
    private NotificationServiceImpl notificationService;

    @PostMapping("/send")
    public ResponseEntity<?> sendNotification(@RequestBody NotificationDTO input) {
        Notification notification = new Notification();
        notification.setMessage(input.getMessage());
        Long id = Long.parseLong(input.getReceiverId());
        notification.setReceiverId(id);
        notificationService.sendNotification(notification);
        return ResponseEntity.ok("Successfully sent notification");
    }

    @PutMapping("/update-status")
    public ResponseEntity<?> updateStatus(@RequestBody UpdateStatusInput input) {
        Long id = Long.parseLong(input.getNotificationId());
        NotificationStatus newStatus = NotificationStatus.valueOf(input.getStatus());
        notificationService.updateStatus(id, newStatus);
        return ResponseEntity.ok("Successfully updated notification");
    }
}
