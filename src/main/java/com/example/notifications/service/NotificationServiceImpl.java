package com.example.notifications.service;

import com.example.notifications.exception.MyErrorCode;
import com.example.notifications.exception.MyException;
import com.example.notifications.repository.CustomerRepository;
import com.example.notifications.repository.NotificationRepository;
import com.example.notifications.repository.model.CustomerEntity;
import com.example.notifications.repository.model.NotificationEntity;
import com.example.notifications.repository.model.NotificationStatusEntity;
import com.example.notifications.service.models.Notification;
import com.example.notifications.service.models.NotificationStatus;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    @Transactional
    public void sendNotification(Notification notification) {
        Optional<CustomerEntity> customerEntityOptional = customerRepository.findById(notification.getReceiverId());
        if (customerEntityOptional.isEmpty()) {
            throw new MyException("Customer not found", MyErrorCode.NOT_FOUND);
        }
        CustomerEntity customerEntity = customerEntityOptional.get();

        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setMessage(notification.getMessage());
        notificationEntity.setReceiverId(notification.getReceiverId());
        notificationEntity.setStatus(NotificationStatusEntity.PENDING);
        notificationEntity.setCustomer(customerEntity);

        customerEntity.getNotifications().add(notificationEntity);

        customerRepository.save(customerEntity);
    }

    @Override
    public void updateStatus(Long id, NotificationStatus newStatus) {
        Optional<NotificationEntity> notificationEntityOptional = notificationRepository.findById(id);
        if (notificationEntityOptional.isEmpty()) {
            throw new MyException("Notification not found", MyErrorCode.NOT_FOUND);
        }

        NotificationEntity notificationEntity = notificationEntityOptional.get();
        notificationEntity.setStatus(NotificationStatusEntity.valueOf(newStatus.name()));
        notificationRepository.save(notificationEntity);
    }
}
