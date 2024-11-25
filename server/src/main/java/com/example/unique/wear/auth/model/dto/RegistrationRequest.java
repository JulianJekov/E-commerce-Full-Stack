package com.example.unique.wear.auth.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationRequest {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private CharSequence password;
}
