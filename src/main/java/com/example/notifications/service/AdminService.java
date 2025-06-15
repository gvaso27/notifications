package com.example.notifications.service;

import com.example.notifications.repository.AdminRepository;
import com.example.notifications.repository.model.AdminEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public AdminEntity createAdmin(AdminEntity admin) {
        try {
            return adminRepository.save(admin);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
