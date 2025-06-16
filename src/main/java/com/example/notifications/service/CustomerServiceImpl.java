package com.example.notifications.service;

import com.example.notifications.repository.CustomerRepository;
import com.example.notifications.repository.model.AddressEntity;
import com.example.notifications.repository.model.AddressTypeEntity;
import com.example.notifications.repository.model.CustomerEntity;
import com.example.notifications.repository.model.NotificationPreferenceTypeEntity;
import com.example.notifications.repository.model.NotificationPreferencesEntity;
import com.example.notifications.service.models.Address;
import com.example.notifications.service.models.Customer;
import com.example.notifications.service.models.NotificationPreferenceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public String createCustomer(Customer customer) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setName(customer.getName());

        Address address = customer.getAddresses().get(0);
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddressType(AddressTypeEntity.valueOf(address.getAddressType().name()));
        addressEntity.setAddressValue(address.getAddressValue());
        addressEntity.setCustomer(customerEntity);

        customerEntity.getAddresses().add(addressEntity);

        NotificationPreferenceType notificationPreferenceType = customer.getNotificationPreferences().get(0);
        NotificationPreferencesEntity notificationPreferencesEntity = new NotificationPreferencesEntity();
        notificationPreferencesEntity.setNotificationType(NotificationPreferenceTypeEntity.valueOf(notificationPreferenceType.name()));
        notificationPreferencesEntity.setCustomer(customerEntity);

        customerEntity.getPreferences().add(notificationPreferencesEntity);

        customerRepository.save(customerEntity);

        return customerEntity.getId().toString();
    }

}
