package com.example.notifications.controller;

import com.example.notifications.controller.models.GetByStatusResponse;
import com.example.notifications.controller.models.UpdateStatusInput;
import com.example.notifications.controller.models.dtos.NotificationDTO;
import com.example.notifications.exception.MyErrorCode;
import com.example.notifications.exception.MyException;
import com.example.notifications.service.NotificationServiceImpl;
import com.example.notifications.service.models.Notification;
import com.example.notifications.service.models.NotificationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customers/notification")
public class NotificationsController {

    @Autowired
    private NotificationServiceImpl notificationService;

    @PostMapping("/send")
    public ResponseEntity<?> sendNotification(@Validated @RequestBody NotificationDTO input) {
        Notification notification = new Notification();
        notification.setMessage(input.getMessage());
        Long id = Long.parseLong(input.getReceiverId());
        notification.setReceiverId(id);
        notificationService.sendNotification(notification);
        return ResponseEntity.ok("Successfully sent notification");
    }

    @PutMapping("/update-status")
    public ResponseEntity<?> updateStatus(@Validated @RequestBody UpdateStatusInput input) {
        Long id = Long.parseLong(input.getNotificationId());
        NotificationStatus newStatus = NotificationStatus.valueOf(input.getStatus());
        notificationService.updateStatus(id, newStatus);
        return ResponseEntity.ok("Successfully updated notification");
    }

    @GetMapping("/get-by-status")
    public ResponseEntity<GetByStatusResponse> getByStatus(@RequestParam String status) {
        if (!status.equals("DELIVERED") &&
            !status.equals("PENDING") &&
            !status.equals("FAILED")) {
            throw new MyException("Type must be either PENDING, FAILED or DELIVERED", MyErrorCode.INVALID_INPUT);
        }

        List<Notification> notifications = notificationService.getNotificationByStatus(
                NotificationStatus.valueOf(status));

        List<NotificationDTO> notificationDTOs = notifications.stream()
                .map(n -> {
                    NotificationDTO dto = new NotificationDTO();
                    dto.setId(String.valueOf(n.getId()));
                    dto.setMessage(n.getMessage());
                    dto.setStatus(n.getStatus().name());
                    dto.setReceiverId(String.valueOf(n.getReceiverId()));
                    return dto;
                })
                .toList();

        GetByStatusResponse response = new GetByStatusResponse();
        response.setNotifications(notificationDTOs);

        return ResponseEntity.ok(response);
    }
}
