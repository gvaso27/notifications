package com.example.notifications.controller;

import com.example.notifications.controller.models.GetCustomersResponse;
import com.example.notifications.controller.models.SearchCustomersByUniqueAttributeInput;
import com.example.notifications.controller.models.UpdateCustomerNameInput;
import com.example.notifications.controller.models.dtos.AddressDTO;
import com.example.notifications.controller.models.dtos.CustomerDTO;
import com.example.notifications.controller.models.dtos.NotificationDTO;
import com.example.notifications.controller.models.dtos.NotificationPreferenceDTO;
import com.example.notifications.exception.MyErrorCode;
import com.example.notifications.exception.MyException;
import com.example.notifications.service.CustomerServiceImpl;
import com.example.notifications.service.models.Address;
import com.example.notifications.service.models.AddressType;
import com.example.notifications.service.models.Customer;
import com.example.notifications.service.models.FilterType;
import com.example.notifications.service.models.NotificationPreference;
import com.example.notifications.service.models.NotificationPreferenceType;
import com.example.notifications.service.models.UniqueAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

    @DeleteMapping("/remove_customer/{customerId}")
    public ResponseEntity<?> removeCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.ok("Customer with id " + customerId + " deleted successfully.");
    }

    @GetMapping
    public ResponseEntity<GetCustomersResponse> getAllCustomers() {
        List<Customer> customers = customerService.getAll();

        List<CustomerDTO> customerDTOs = customers.stream()
                .map(this::mapToCustomerDTO)
                .toList();

        GetCustomersResponse response = new GetCustomersResponse();
        response.setCustomers(customerDTOs);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search-byUniqueAttribute")
    public ResponseEntity<GetCustomersResponse> searchCustomersByUniqueAttribute(
            @Validated @ModelAttribute SearchCustomersByUniqueAttributeInput input) {

        if (input == null || input.getFilterType() == null) {
            throw new MyException("Missing filter type", MyErrorCode.INVALID_INPUT);
        }

        FilterType filterType;
        try {
            filterType = FilterType.valueOf(input.getFilterType());
        } catch (IllegalArgumentException e) {
            throw new MyException("Invalid filter type: " + input.getFilterType(), MyErrorCode.INVALID_INPUT);
        }

        UniqueAttribute uniqueAttribute = new UniqueAttribute();
        uniqueAttribute.setId(input.getId());
        uniqueAttribute.setName(input.getName());
        uniqueAttribute.setAddressType(input.getAddressType());
        uniqueAttribute.setAddressValue(input.getAddressValue());
        uniqueAttribute.setNotificationPreferenceType(input.getNotificationPreferenceType());

        List<Customer> customers = customerService.filterCustomers(uniqueAttribute, filterType);

        if (customers == null || customers.isEmpty()) {
            throw new MyException("No customers found for given filter", MyErrorCode.NOT_FOUND);
        }

        List<CustomerDTO> customerDTOs = customers.stream()
                .map(this::mapToCustomerDTO)
                .collect(Collectors.toList());

        GetCustomersResponse response = new GetCustomersResponse();
        response.setCustomers(customerDTOs);

        return ResponseEntity.ok(response);
    }

    private CustomerDTO mapToCustomerDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());

        List<AddressDTO> addressDTOs = customer.getAddresses().stream().map(address -> {
            AddressDTO a = new AddressDTO();
            a.setId(address.getId());
            a.setAddressType(address.getAddressType().name());
            a.setAddressValue(address.getAddressValue());
            return a;
        }).collect(Collectors.toList());

        dto.setAddresses(addressDTOs);

        List<NotificationPreferenceDTO> preferenceDTOs = customer.getNotificationPreferences().stream().map(pref -> {
            NotificationPreferenceDTO p = new NotificationPreferenceDTO();
            p.setId(pref.getId());
            p.setNotificationPreferenceType(pref.getNotificationPreferenceType().name());
            return p;
        }).collect(Collectors.toList());

        dto.setNotificationPreferences(preferenceDTOs);

        List<NotificationDTO> notificationDTOs = customer.getNotifications().stream().map(notif -> {
            NotificationDTO n = new NotificationDTO();
            n.setId(notif.getId() != null ? notif.getId().toString() : null);
            n.setMessage(notif.getMessage());
            n.setStatus(notif.getStatus() != null ? notif.getStatus().name() : null);
            n.setReceiverId(notif.getReceiverId() != null ? notif.getReceiverId().toString() : null);
            return n;
        }).collect(Collectors.toList());

        dto.setNotifications(notificationDTOs);

        return dto;
    }
}
