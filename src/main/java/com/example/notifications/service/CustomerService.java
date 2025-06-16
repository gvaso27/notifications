package com.example.notifications.service;

import com.example.notifications.service.models.Customer;

import java.util.List;

public interface CustomerService {

    String createCustomer(Customer customer);

    void updateCustomerName(Long id, String newName);

    void deleteCustomer(Long id);

    List<Customer> getAll();

}
