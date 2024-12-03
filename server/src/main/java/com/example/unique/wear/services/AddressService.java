package com.example.unique.wear.services;

import com.example.unique.wear.model.dto.address.AddressDto;
import com.example.unique.wear.model.entity.Address;

import java.security.Principal;

public interface AddressService {
    Address createAddress(AddressDto addressDto, Principal principal);
}
