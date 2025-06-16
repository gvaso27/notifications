package com.example.notifications.service;

import com.example.notifications.exception.MyErrorCode;
import com.example.notifications.exception.MyException;
import com.example.notifications.repository.AdminRepository;
import com.example.notifications.repository.model.AdminEntity;
import com.example.notifications.service.models.Admin;
import com.example.notifications.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String createAdmin(Admin admin) {
        if (adminRepository.existsByUsername(admin.getUsername())) {
            throw new MyException("Username already exists", MyErrorCode.CONFLICT);
        }

        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setUsername(admin.getUsername());
        adminEntity.setPassword(passwordEncoder.encode(admin.getPassword()));

        adminRepository.save(adminEntity);
        return adminEntity.getId().toString();
    }

    @Override
    public String authenticateAdmin(Admin admin) {
        String username = admin.getUsername();
        String password = admin.getPassword();

        AdminEntity adminEntity = adminRepository.findByUsername(username)
                .orElseThrow(() -> new MyException("Invalid credentials", MyErrorCode.UNAUTHORIZED));

        if (!passwordEncoder.matches(password, adminEntity.getPassword())) {
            throw new MyException("Invalid credentials", MyErrorCode.UNAUTHORIZED);
        }

        return jwtUtil.generateToken(username);
    }

}
