package com.example.notifications.controller;

import com.example.notifications.controller.models.GetAllCustomersResponse;
import com.example.notifications.controller.models.UpdateCustomerNameInput;
import com.example.notifications.controller.models.dtos.AddressDTO;
import com.example.notifications.controller.models.dtos.CustomerDTO;
import com.example.notifications.controller.models.dtos.NotificationDTO;
import com.example.notifications.controller.models.dtos.NotificationPreferenceDTO;
import com.example.notifications.service.CustomerServiceImpl;
import com.example.notifications.service.models.Address;
import com.example.notifications.service.models.AddressType;
import com.example.notifications.service.models.Customer;
import com.example.notifications.service.models.NotificationPreference;
import com.example.notifications.service.models.NotificationPreferenceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerServiceImpl customerService;

    @PostMapping("/create_customer")
    public ResponseEntity<String> createCustomer(@Validated @RequestBody CustomerDTO input) {

        List<Address> addresses = input.getAddresses()
                .stream()
                .map(dto -> {
                    Address address = new Address();
                    address.setAddressType(AddressType.valueOf(dto.getAddressType()));
                    address.setAddressValue(dto.getAddressValue());
                    return address;
                })
                .collect(Collectors.toList());

        List<NotificationPreference> notificationPreferences = input.getNotificationPreferences()
                .stream()
                .map(dto -> {
                    NotificationPreference notificationPreference = new NotificationPreference();
                    notificationPreference.setId(dto.getId());
                    notificationPreference.setNotificationPreferenceType(NotificationPreferenceType
                            .valueOf(dto.getNotificationPreferenceType()));
                    return notificationPreference;
                })
                .collect(Collectors.toList());

        Customer customer = new Customer();
        customer.setName(input.getName());
        customer.setAddresses(addresses);
        customer.setNotificationPreferences(notificationPreferences);


        return ResponseEntity.ok(customerService.createCustomer(customer));
    }

    @PutMapping("/update_name")
    public ResponseEntity<?> updateCustomerName(@Validated @RequestBody UpdateCustomerNameInput input) {

        customerService.updateCustomerName(input.getId(), input.getNewName());

        return ResponseEntity.ok("Customer name updated successfully");

    }

    @DeleteMapping("/remove_customer")
    public ResponseEntity<?> removeCustomer(@RequestBody String customerId) {
        Long id = Long.parseLong(customerId);
        customerService.deleteCustomer(id);
        return ResponseEntity.ok("Customer with id " + id + " deleted successfully.");
    }

    @GetMapping
    public ResponseEntity<GetAllCustomersResponse> getAllCustomers() {
        List<Customer> customers = customerService.getAll();

        List<CustomerDTO> customerDTOs = customers.stream()
                .map(customer -> {
                    CustomerDTO dto = new CustomerDTO();
                    dto.setId(customer.getId());
                    dto.setName(customer.getName());

                    dto.setAddresses(
                            customer.getAddresses().stream()
                                    .map(address -> {
                                        AddressDTO addressDTO = new AddressDTO();
                                        addressDTO.setId(address.getId());
                                        addressDTO.setAddressType(address.getAddressType().name());
                                        addressDTO.setAddressValue(address.getAddressValue());
                                        return addressDTO;
                                    })
                                    .toList()
                    );

                    dto.setNotificationPreferences(
                            customer.getNotificationPreferences().stream()
                                    .map(pref -> {
                                        NotificationPreferenceDTO prefDTO = new NotificationPreferenceDTO();
                                        prefDTO.setId(pref.getId());
                                        prefDTO.setNotificationPreferenceType(pref.getNotificationPreferenceType().name());
                                        return prefDTO;
                                    })
                                    .toList()
                    );

                    dto.setNotifications(
                            customer.getNotifications().stream()
                                    .map(notification -> {
                                        NotificationDTO notificationDTO = new NotificationDTO();
                                        notificationDTO.setId(String.valueOf(notification.getId()));
                                        notificationDTO.setMessage(notification.getMessage());
                                        notificationDTO.setReceiverId(String.valueOf(notification.getReceiverId()));
                                        notificationDTO.setStatus(
                                                notification.getStatus() != null ? notification.getStatus().name() : null
                                        );
                                        return notificationDTO;
                                    })
                                    .toList()
                    );

                    return dto;
                })
                .toList();

        GetAllCustomersResponse response = new GetAllCustomersResponse();
        response.setCustomers(customerDTOs);

        return ResponseEntity.ok(response);
    }


}
