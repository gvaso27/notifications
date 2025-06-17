package com.example.notifications.service;

import com.example.notifications.exception.MyErrorCode;
import com.example.notifications.exception.MyException;
import com.example.notifications.repository.AddressRepository;
import com.example.notifications.repository.CustomerRepository;
import com.example.notifications.repository.NotificationPreferencesRepository;
import com.example.notifications.repository.model.AddressEntity;
import com.example.notifications.repository.model.AddressTypeEntity;
import com.example.notifications.repository.model.CustomerEntity;
import com.example.notifications.repository.model.NotificationPreferenceTypeEntity;
import com.example.notifications.repository.model.NotificationPreferencesEntity;
import com.example.notifications.service.models.Address;
import com.example.notifications.service.models.AddressType;
import com.example.notifications.service.models.Customer;
import com.example.notifications.service.models.FilterType;
import com.example.notifications.service.models.Notification;
import com.example.notifications.service.models.NotificationPreference;
import com.example.notifications.service.models.NotificationPreferenceType;
import com.example.notifications.service.models.NotificationStatus;
import com.example.notifications.service.models.UniqueAttribute;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private NotificationPreferencesRepository notificationPreferencesRepository;

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
                    preferenceEntity.setNotificationType(NotificationPreferenceTypeEntity
                            .valueOf(pref.getNotificationPreferenceType().name()));
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
        return customerRepository.findAll()
                .stream()
                .map(this::mapToCustomer)
                .collect(Collectors.toList());
    }


    @Override
    public List<Customer> filterCustomers(UniqueAttribute attribute, FilterType filterType) {
        if (filterType.equals(FilterType.ID)) {
            return List.of(getCustomerById(Long.parseLong(attribute.getId())));
        } else if (filterType.equals(FilterType.NAME)) {
            return filterByName(attribute.getName());
        } else if (filterType.equals(FilterType.PREFERENCE_TYPE)) {
            return filterByPreferenceType(NotificationPreferenceType
                    .valueOf(attribute.getNotificationPreferenceType()));
        } else if (filterType.equals(FilterType.ADDRESS_TYPE)) {
            return filterByAddressType(AddressType.valueOf(attribute.getAddressType()));
        } else if (filterType.equals(FilterType.ADDRESS_VALUE)) {
            return filterByAddressValue(attribute.getAddressValue());
        }
        return null;
    }

    private Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(this::mapToCustomer)
                .orElseThrow(() -> new MyException("Customer with id " + id + " does not exist", MyErrorCode.NOT_FOUND));
    }


    private List<Customer> filterByName(String name) {
        List<CustomerEntity> customerEntityOptional = customerRepository.findByNameContaining(name);
        if (customerEntityOptional.isEmpty()) {
            throw new MyException("Customer with name " + name + " does not exist", MyErrorCode.NOT_FOUND);
        }

        return customerEntityOptional.stream().map(this::mapToCustomer).collect(Collectors.toList());

    }

    private List<Customer> filterByPreferenceType(NotificationPreferenceType type) {
        List<NotificationPreferencesEntity> notificationPreferences = notificationPreferencesRepository
                .findAllByNotificationType(NotificationPreferenceTypeEntity.valueOf(type.name()));

        if (notificationPreferences.isEmpty()) {
            throw new MyException("No customers found with preference type " + type, MyErrorCode.NOT_FOUND);
        }

        Set<CustomerEntity> uniqueCustomers = notificationPreferences.stream()
                .map(NotificationPreferencesEntity::getCustomer)
                .collect(Collectors.toSet());

        return uniqueCustomers.stream()
                .map(this::mapToCustomer)
                .collect(Collectors.toList());
    }

    private List<Customer> filterByAddressType(AddressType type) {
        List<AddressEntity> addresses = addressRepository
                .findAllByAddressType(AddressTypeEntity.valueOf(type.name()));

        if (addresses.isEmpty()) {
            throw new MyException("No customers found with address type " + type, MyErrorCode.NOT_FOUND);
        }

        Set<CustomerEntity> uniqueCustomers = addresses.stream()
                .map(AddressEntity::getCustomer)
                .collect(Collectors.toSet());

        return uniqueCustomers.stream()
                .map(this::mapToCustomer)
                .collect(Collectors.toList());
    }


    private List<Customer> filterByAddressValue(String address) {
        List<AddressEntity> addresses = addressRepository
                .findAllByAddressValueContainingIgnoreCase(address);

        if (addresses.isEmpty()) {
            throw new MyException("No customers found with address value like: " + address, MyErrorCode.NOT_FOUND);
        }

        Set<CustomerEntity> uniqueCustomers = addresses.stream()
                .map(AddressEntity::getCustomer)
                .collect(Collectors.toSet());

        return uniqueCustomers.stream()
                .map(this::mapToCustomer)
                .collect(Collectors.toList());
    }


    private Customer mapToCustomer(CustomerEntity entity) {
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

        List<NotificationPreference> preferences = entity.getPreferences()
                .stream()
                .map(prefEntity -> {
                    NotificationPreference pref = new NotificationPreference();
                    pref.setId(prefEntity.getId());
                    pref.setNotificationPreferenceType(
                            NotificationPreferenceType.valueOf(prefEntity.getNotificationType().name()));
                    return pref;
                })
                .collect(Collectors.toList());
        customer.setNotificationPreferences(preferences);

        List<Notification> notifications = entity.getNotifications()
                .stream()
                .map(notificationEntity -> {
                    Notification notification = new Notification();
                    notification.setId(notificationEntity.getId());
                    notification.setReceiverId(notificationEntity.getReceiverId());
                    notification.setMessage(notificationEntity.getMessage());
                    notification.setStatus(NotificationStatus.valueOf(notificationEntity.getStatus().name()));
                    return notification;
                })
                .collect(Collectors.toList());
        customer.setNotifications(notifications);

        return customer;
    }


}
