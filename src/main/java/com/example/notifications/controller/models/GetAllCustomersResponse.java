package com.example.notifications.controller.models;

import com.example.notifications.controller.models.dtos.CustomerDTO;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetAllCustomersResponse {
    List<CustomerDTO> customers;
}
