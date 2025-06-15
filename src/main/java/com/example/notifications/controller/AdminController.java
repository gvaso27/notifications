package com.example.notifications.controller;

import com.example.notifications.repository.model.AdminEntity;
import com.example.notifications.service.AdminService;
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
    private AdminService adminService;

    @PostMapping
    public ResponseEntity<AdminEntity> createAdmin(@RequestBody AdminEntity admin) {
        return ResponseEntity.ok(adminService.createAdmin(admin));
    }

}