package com.example.notifications.repository;

import com.example.notifications.repository.model.AddressEntity;
import com.example.notifications.repository.model.AddressTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

    Optional<AddressEntity> findById(Long id);

    List<AddressEntity> findAllByAddressType(AddressTypeEntity addressType);

    List<AddressEntity> findAllByAddressValueContainingIgnoreCase(String addressValue);

}
