package com.example.notifications.controller;

import com.example.notifications.controller.models.AddAddressInput;
import com.example.notifications.controller.models.dtos.AddressDTO;
import com.example.notifications.service.AddressService;
import com.example.notifications.service.models.Address;
import com.example.notifications.service.models.AddressType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/add")
    public ResponseEntity<String> addAddress(@Validated @RequestBody AddAddressInput input) {
        Address address = new Address();
        address.setAddressType(AddressType.valueOf(input.getAddressDTO().getAddressType()));
        address.setAddressValue(input.getAddressDTO().getAddressValue());
        addressService.addAddress(address, input.getCustomerId());
        return ResponseEntity.ok("Successfully added address");

    }

    @PutMapping("/update")
    public ResponseEntity<String> updateAddress(@Validated @RequestBody AddressDTO input) {
        Address address = new Address();
        address.setId(input.getId());
        address.setAddressValue(input.getAddressValue());
        address.setAddressType(AddressType.valueOf(input.getAddressType()));
        addressService.updateAddress(address);
        return ResponseEntity.ok("Successfully updated address");
    }

    @DeleteMapping("/delete_address")
    public ResponseEntity<String> deleteAddress(@RequestBody String addressId) {
        Long id = Long.parseLong(addressId);
        addressService.deleteAddressById(id);
        return ResponseEntity.ok("Successfully deleted address");
    }
}
