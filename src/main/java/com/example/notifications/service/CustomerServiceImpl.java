package com.example.notifications.service;

import com.example.notifications.exception.MyErrorCode;
import com.example.notifications.exception.MyException;
import com.example.notifications.repository.CustomerRepository;
import com.example.notifications.repository.model.AddressEntity;
import com.example.notifications.repository.model.AddressTypeEntity;
import com.example.notifications.repository.model.CustomerEntity;
import com.example.notifications.repository.model.NotificationPreferenceTypeEntity;
import com.example.notifications.repository.model.NotificationPreferencesEntity;
import com.example.notifications.service.models.Address;
import com.example.notifications.service.models.AddressType;
import com.example.notifications.service.models.Customer;
import com.example.notifications.service.models.NotificationPreferenceType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public String createCustomer(Customer customer) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setName(customer.getName());

        List<AddressEntity> addressEntities = customer.getAddresses()
                .stream()
                .map(address -> {
                    AddressEntity addressEntity = new AddressEntity();
                    addressEntity.setAddressType(AddressTypeEntity.valueOf(address.getAddressType().name()));
                    addressEntity.setAddressValue(address.getAddressValue());
                    addressEntity.setCustomer(customerEntity);
                    return addressEntity;
                })
                .collect(Collectors.toList());

        customerEntity.getAddresses().addAll(addressEntities);

        List<NotificationPreferencesEntity> preferenceEntities = customer.getNotificationPreferences()
                .stream()
                .map(pref -> {
                    NotificationPreferencesEntity preferenceEntity = new NotificationPreferencesEntity();
                    preferenceEntity.setNotificationType(NotificationPreferenceTypeEntity.valueOf(pref.name()));
                    preferenceEntity.setCustomer(customerEntity);
                    return preferenceEntity;
                })
                .collect(Collectors.toList());

        customerEntity.getPreferences().addAll(preferenceEntities);

        customerRepository.save(customerEntity);

        return customerEntity.getId().toString();
    }


    @Override
    public void updateCustomerName(Long id, String newName) {
        Optional<CustomerEntity> customerEntityOptional = customerRepository.findById(id);
        if (customerEntityOptional.isEmpty()) {
            throw new MyException("customer with id " + id + " does not exist", MyErrorCode.NOT_FOUND);
        }
        CustomerEntity customerEntity = customerEntityOptional.get();

        customerEntity.setName(newName);
        customerRepository.save(customerEntity);
    }

    @Override
    @Transactional
    public void deleteCustomer(Long id) {
        Optional<CustomerEntity> customerEntityOptional = customerRepository.findById(id);
        if (customerEntityOptional.isEmpty()) {
            throw new MyException("Customer with id " + id + " does not exist", MyErrorCode.NOT_FOUND);
        }

        customerRepository.delete(customerEntityOptional.get());
    }

    @Override
    public List<Customer> getAll() {
        List<CustomerEntity> customerEntities = customerRepository.findAll();

        return customerEntities.stream()
                .map(entity -> {
                    Customer customer = new Customer();
                    customer.setId(entity.getId());
                    customer.setName(entity.getName());

                    List<Address> addresses = entity.getAddresses()
                            .stream()
                            .map(addressEntity -> {
                                Address address = new Address();
                                address.setId(addressEntity.getId());
                                address.setAddressType(AddressType.valueOf(addressEntity.getAddressType().name()));
                                address.setAddressValue(addressEntity.getAddressValue());
                                return address;
                            })
                            .collect(Collectors.toList());

                    customer.setAddresses(addresses);

                    List<NotificationPreferenceType> preferences = entity.getPreferences()
                            .stream()
                            .map(prefEntity -> NotificationPreferenceType.valueOf(prefEntity.getNotificationType().name()))
                            .collect(Collectors.toList());

                    customer.setNotificationPreferences(preferences);

                    return customer;
                })
                .collect(Collectors.toList());
    }


}
