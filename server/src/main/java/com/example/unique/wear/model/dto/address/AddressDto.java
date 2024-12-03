package com.example.unique.wear.model.dto.address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto {
    private UUID id;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String phoneNumber;
}
