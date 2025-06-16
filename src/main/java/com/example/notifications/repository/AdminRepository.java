package com.example.notifications.repository;

import com.example.notifications.repository.model.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<AdminEntity, Long> {

    Boolean existsByUsername(String username);

    Optional<AdminEntity> findByUsername(String username);

}
