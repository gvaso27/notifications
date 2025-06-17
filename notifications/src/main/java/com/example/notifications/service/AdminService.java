package com.example.notifications.service;

import com.example.notifications.service.models.Admin;

public interface AdminService {

    String createAdmin(Admin admin);

    String authenticateAdmin(Admin admin);

}
