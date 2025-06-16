package com.example.notifications.controller;

import com.example.notifications.controller.models.DTOs.AdminDTO;
import com.example.notifications.controller.models.LogInAdminResponse;
import com.example.notifications.service.AdminServiceImpl;
import com.example.notifications.service.models.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    @Autowired
    private AdminServiceImpl adminService;

    @PostMapping
    public ResponseEntity<String> createAdmin(@RequestBody AdminDTO input) {

        Admin admin =  new Admin();
        admin.setUsername(input.getUsername());
        admin.setPassword(input.getPassword());

        return ResponseEntity.ok(adminService.createAdmin(admin));
    }

    @PostMapping("/login")
    public ResponseEntity<LogInAdminResponse> logInAdmin(@RequestBody AdminDTO input) {
        Admin admin =  new Admin();
        admin.setUsername(input.getUsername());
        admin.setPassword(input.getPassword());

        String token = adminService.authenticateAdmin(admin);

        LogInAdminResponse logInAdminResponse = new LogInAdminResponse();
        logInAdminResponse.setToken(token);
        logInAdminResponse.setUsername(admin.getUsername());
        return ResponseEntity.ok(logInAdminResponse);
    }

}