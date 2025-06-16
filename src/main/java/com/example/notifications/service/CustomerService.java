package com.example.notifications.service;

import com.example.notifications.service.models.Customer;

public interface CustomerService {

    String createCustomer(Customer customer);

    void updateCustomerName(Long id, String newName);

    void deleteCustomer(Long id);

}
