package com.example.unique.wear.services.impl;

import com.example.unique.wear.auth.model.entity.User;
import com.example.unique.wear.model.dto.address.AddressDto;
import com.example.unique.wear.model.entity.Address;
import com.example.unique.wear.repositories.AddressRepository;
import com.example.unique.wear.services.AddressService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class AddressServiceImpl implements AddressService {

    private final UserDetailsService userDetailsService;
    private final AddressRepository addressRepository;

    public AddressServiceImpl(UserDetailsService userDetailsService, AddressRepository addressRepository) {
        this.userDetailsService = userDetailsService;
        this.addressRepository = addressRepository;
    }


    //TODO: return dto instead of entity
    @Override
    public Address createAddress(AddressDto addressDto, Principal principal) {
        User user = (User) userDetailsService.loadUserByUsername(principal.getName());
        Address address = Address.builder()
                .street(addressDto.getStreet())
                .city(addressDto.getCity())
                .state(addressDto.getState())
                .zipCode(addressDto.getZipCode())
                .phoneNumber(addressDto.getPhoneNumber())
                .user(user)
                .build();
        return addressRepository.save(address);
    }
}
