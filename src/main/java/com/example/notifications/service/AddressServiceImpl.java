package com.example.notifications.service;

import com.example.notifications.exception.MyErrorCode;
import com.example.notifications.exception.MyException;
import com.example.notifications.repository.AddressRepository;
import com.example.notifications.repository.CustomerRepository;
import com.example.notifications.repository.model.AddressEntity;
import com.example.notifications.repository.model.AddressTypeEntity;
import com.example.notifications.repository.model.CustomerEntity;
import com.example.notifications.service.models.Address;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Override
    @Transactional
    public void addAddress(Address address, Long customerId) {
        Optional<CustomerEntity> customerEntityOptional = customerRepository.findById(customerId);
        if (customerEntityOptional.isEmpty()) {
            throw new MyException("Customer not found", MyErrorCode.NOT_FOUND);
        }

        CustomerEntity customerEntity = customerEntityOptional.get();

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddressType(AddressTypeEntity.valueOf(address.getAddressType().name()));
        addressEntity.setAddressValue(address.getAddressValue());
        addressEntity.setCustomer(customerEntity);

        customerEntity.getAddresses().add(addressEntity);

        customerRepository.save(customerEntity);
    }


    @Override
    public void updateAddress(Address address) {
        Optional<AddressEntity> addressEntityOptional = addressRepository.findById(address.getId());
        if (addressEntityOptional.isEmpty()) {
            throw new MyException("Address not found", MyErrorCode.NOT_FOUND);
        }
        AddressEntity addressEntity = addressEntityOptional.get();
        addressEntity.setAddressType(AddressTypeEntity.valueOf(address.getAddressType().name()));
        addressEntity.setAddressValue(address.getAddressValue());
        addressRepository.save(addressEntity);
    }

    @Override
    public void deleteAddressById(Long id) {
        Optional<AddressEntity> addressEntityOptional = addressRepository.findById(id);
        if (addressEntityOptional.isEmpty()) {
            throw new MyException("Address not found", MyErrorCode.NOT_FOUND);
        }
        addressRepository.deleteById(id);
    }
}
