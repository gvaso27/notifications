package com.example.notifications.controller;

import com.example.notifications.controller.models.DTOs.CustomerDTO;
import com.example.notifications.service.CustomerServiceImpl;
import com.example.notifications.service.models.Address;
import com.example.notifications.service.models.AddressType;
import com.example.notifications.service.models.Customer;
import com.example.notifications.service.models.NotificationPreferenceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerServiceImpl customerService;

    @PostMapping("/create_customer")
    public ResponseEntity<String> createCustomer(@RequestBody CustomerDTO input) {

        Address address = new Address();
        address.setAddressType(AddressType.valueOf(input.getAddressType()));
        address.setAddressValue(input.getAddressValue());
        List<Address> addresses = new ArrayList<>();
        addresses.add(address);

        NotificationPreferenceType notificationPreferenceType = NotificationPreferenceType.valueOf(input.getNotificationPreferenceType());
        List<NotificationPreferenceType> notificationPreferenceTypes = new ArrayList<>();
        notificationPreferenceTypes.add(notificationPreferenceType);

        Customer customer = new Customer();
        customer.setName(input.getName());
        customer.setAddresses(addresses);
        customer.setNotificationPreferences(notificationPreferenceTypes);


        return ResponseEntity.ok(customerService.createCustomer(customer));
    }

}
