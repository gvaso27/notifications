package com.example.notifications.service;

import com.example.notifications.exception.MyErrorCode;
import com.example.notifications.exception.MyException;
import com.example.notifications.repository.CustomerRepository;
import com.example.notifications.repository.NotificationPreferencesRepository;
import com.example.notifications.repository.model.CustomerEntity;
import com.example.notifications.repository.model.NotificationPreferenceTypeEntity;
import com.example.notifications.repository.model.NotificationPreferencesEntity;
import com.example.notifications.service.models.NotificationPreference;
import com.example.notifications.service.models.NotificationPreferenceType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationPreferencesServiceImpl implements NotificationPreferencesService {

    @Autowired
    private NotificationPreferencesRepository notificationPreferencesRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void removeNotificationPreference(Long id) {
        Optional<NotificationPreferencesEntity>  notificationPreferencesEntity
                = notificationPreferencesRepository.findById(id);
        if (notificationPreferencesEntity.isEmpty()) {
            throw new MyException("Notification preference not found", MyErrorCode.NOT_FOUND);
        }
        notificationPreferencesRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void addNotificationPreference(NotificationPreference notificationPreference, Long customerId) {
        Optional<CustomerEntity> customerEntityOptional = customerRepository.findById(customerId);
        if (customerEntityOptional.isEmpty()) {
            throw new MyException("Customer not found", MyErrorCode.NOT_FOUND);
        }

        CustomerEntity customerEntity = customerEntityOptional.get();

        NotificationPreferencesEntity notificationPreferencesEntity = new NotificationPreferencesEntity();
        notificationPreferencesEntity.setCustomer(customerEntity);
        notificationPreferencesEntity.setNotificationType(NotificationPreferenceTypeEntity
                .valueOf(notificationPreference.getNotificationPreferenceType().name()));

        customerEntity.getPreferences().add(notificationPreferencesEntity);

        customerRepository.save(customerEntity);
    }

    @Override
    public List<NotificationPreference> getPreferenceByCustomerId(Long id) {
        Optional<CustomerEntity> customerEntityOptional = customerRepository.findById(id);
        if (customerEntityOptional.isEmpty()) {
            throw new MyException("Customer not found", MyErrorCode.NOT_FOUND);
        }

        CustomerEntity customerEntity = customerEntityOptional.get();

        return customerEntity.getPreferences()
                .stream()
                .map(entity -> {
                    NotificationPreference preference = new NotificationPreference();
                    preference.setId(entity.getId());
                    preference.setNotificationPreferenceType(
                            NotificationPreferenceType.valueOf(
                                    entity.getNotificationType().name()
                            )
                    );
                    return preference;
                })
                .toList();
    }


}
