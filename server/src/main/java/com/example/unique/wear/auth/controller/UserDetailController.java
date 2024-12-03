package com.example.unique.wear.auth.controller;

import com.example.unique.wear.auth.model.dto.UserDetailsDto;
import com.example.unique.wear.auth.model.entity.User;
import com.example.unique.wear.model.dto.address.AddressDto;
import com.example.unique.wear.model.entity.Address;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserDetailController {

    private final UserDetailsService userDetailsService;

    public UserDetailController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDetailsDto> getUserProfile(Principal principal) {
        if (principal == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        User user = (User) userDetailsService.loadUserByUsername(principal.getName());

        if (user == null) {
            return ResponseEntity.notFound().build();
        }


        UserDetailsDto userDetailsDto = getUserDetailsDto(user);

        return new ResponseEntity<>(userDetailsDto, HttpStatus.OK);
    }


    private static UserDetailsDto getUserDetailsDto(User user) {
        return UserDetailsDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .id(user.getId())
                .phoneNumber(user.getPhoneNumber())
                .authorityList(user.getAuthorities().toArray())
                .addressList(
                        user
                                .getAddresses()
                                .stream()
                                .map(UserDetailController::mapToDto)
                                .toList())
                .build();
    }

    private static AddressDto mapToDto(Address address) {
        return AddressDto.builder()
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .zipCode(address.getZipCode())
                .phoneNumber(address.getPhoneNumber())
                .id(address.getId())
                .build();
    }
}
