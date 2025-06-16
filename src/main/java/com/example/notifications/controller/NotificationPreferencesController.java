package com.example.notifications.controller;

import com.example.notifications.controller.models.AddNotificationPreferenceInput;
import com.example.notifications.controller.models.GetNotificationPreferencesByCustomerIdResponse;
import com.example.notifications.controller.models.dtos.NotificationPreferenceDTO;
import com.example.notifications.service.NotificationPreferencesServiceImpl;
import com.example.notifications.service.models.NotificationPreference;
import com.example.notifications.service.models.NotificationPreferenceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customers/notification_preferencies")
public class NotificationPreferencesController {

    @Autowired
    private NotificationPreferencesServiceImpl notificationPreferencesService;

    @PostMapping("/add")
    public ResponseEntity<String> addNotificationPreference(@Validated @RequestBody AddNotificationPreferenceInput input) {
        NotificationPreference notificationPreference = new NotificationPreference();
        notificationPreference.setNotificationPreferenceType(NotificationPreferenceType
                .valueOf(input.getNotificationPreferenceDTO().getNotificationPreferenceType()));
        notificationPreferencesService.addNotificationPreference(notificationPreference, input.getCustomerId());
        return ResponseEntity.ok("Successfully added notification preference to customer");
    }

    @DeleteMapping("/delete_preference")
    public ResponseEntity<String> deleteNotificationPreference(@RequestBody String preferenceId) {
        Long id = Long.parseLong(preferenceId);
        notificationPreferencesService.removeNotificationPreference(id);
        return ResponseEntity.ok("Successfully deleted notification preference");
    }

    @GetMapping("/get_preferences")
    public ResponseEntity<GetNotificationPreferencesByCustomerIdResponse> getNotificationPreferencesByCustomerId(
            @RequestParam String customerId) {

        Long id = Long.parseLong(customerId);

        List<NotificationPreference> notificationPreferences = notificationPreferencesService.getPreferenceByCustomerId(id);

        List<NotificationPreferenceDTO> preferenceDTOs = notificationPreferences.stream()
                .map(pref -> {
                    NotificationPreferenceDTO dto = new NotificationPreferenceDTO();
                    dto.setId(pref.getId());
                    dto.setNotificationPreferenceType(pref.getNotificationPreferenceType().name()
                    );
                    return dto;
                })
                .toList();

        GetNotificationPreferencesByCustomerIdResponse response = new GetNotificationPreferencesByCustomerIdResponse();
        response.setNotificationPreferencesDTO(preferenceDTOs);

        return ResponseEntity.ok(response);

    }
}
