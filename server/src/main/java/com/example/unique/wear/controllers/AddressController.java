package com.example.unique.wear.controllers;

import com.example.unique.wear.model.dto.address.AddressDto;
import com.example.unique.wear.model.entity.Address;
import com.example.unique.wear.services.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    //TODO: return dto instead of entity
    @PostMapping("/create")
    public ResponseEntity<Address> createAddress(@RequestBody AddressDto addressDto, Principal principal) {
        Address address = addressService.createAddress(addressDto, principal);
        return ResponseEntity.ok(address);
    }

}
