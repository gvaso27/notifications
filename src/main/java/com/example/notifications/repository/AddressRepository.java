package com.example.notifications.repository;

import com.example.notifications.repository.model.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    Optional<AddressEntity> findById(Long id);
}
