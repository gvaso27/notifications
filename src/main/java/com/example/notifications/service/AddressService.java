package com.example.notifications.service;

import com.example.notifications.service.models.Address;

public interface AddressService {

    void addAddress(Address address, Long customerId);

    void updateAddress(Address address);

    void deleteAddressById(Long id);
}
