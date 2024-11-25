package com.example.unique.wear.auth.services;

import com.example.unique.wear.auth.model.dto.RegistrationRequest;
import com.example.unique.wear.auth.model.dto.RegistrationResponse;
import com.example.unique.wear.auth.model.entity.User;
import com.example.unique.wear.auth.repositories.UserDetailRepository;
import com.example.unique.wear.auth.util.VerificationCodeGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerErrorException;

@Service
public class RegistrationService {

    private final UserDetailRepository userDetailRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityService authorityService;

    public RegistrationService(UserDetailRepository userDetailRepository, PasswordEncoder passwordEncoder, AuthorityService authorityService) {
        this.userDetailRepository = userDetailRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityService = authorityService;
    }

    public RegistrationResponse createUser(RegistrationRequest registrationRequest) {
        User existingUser = userDetailRepository.findByEmail(registrationRequest.getEmail());
        if (existingUser != null) {
            return RegistrationResponse.builder()
                    .code(400)
                    .message("Email already exist!")
                    .build();
        }
        try {
            User user = new User();
            user.setFirstName(registrationRequest.getFirstName());
            user.setLastName(registrationRequest.getLastName());
            user.setEmail(registrationRequest.getEmail());
            user.setEnabled(false);
            user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            user.setProvider("manual");

            String code = VerificationCodeGenerator.generateCode();
            user.setVerificationCode(code);
            user.setAuthorities(authorityService.getUserAuthority());
            userDetailRepository.save(user);

            //TODO: Call method to send Email
            return RegistrationResponse.builder()
                    .code(200)
                    .message("Registration successful")
                    .build();


        } catch (Exception e) {
            throw new ServerErrorException(e.getMessage(), e.getCause());
        }
    }
}
