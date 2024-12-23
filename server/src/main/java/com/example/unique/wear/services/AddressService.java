package com.example.unique.wear.services;

import com.example.unique.wear.model.dto.address.AddressDto;
import com.example.unique.wear.model.entity.Address;

import java.security.Principal;
import java.util.UUID;

public interface AddressService {
    Address createAddress(AddressDto addressDto, Principal principal);

    void deleteAddress(UUID id);
}
