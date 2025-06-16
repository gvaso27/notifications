package com.example.notifications.controller.models;

import com.example.notifications.controller.models.dtos.AddressDTO;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddAddressInput {

    AddressDTO addressDTO;

    Long customerId;

}
