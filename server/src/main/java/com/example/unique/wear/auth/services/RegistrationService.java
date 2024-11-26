package com.example.unique.wear.auth.services;

import com.example.unique.wear.auth.model.dto.RegistrationRequest;
import com.example.unique.wear.auth.model.dto.RegistrationResponse;
import com.example.unique.wear.auth.model.entity.User;
import com.example.unique.wear.auth.repositories.UserDetailRepository;
import com.example.unique.wear.auth.util.VerificationCodeGenerator;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerErrorException;

import java.util.Date;

@Transactional
@Service
public class RegistrationService {

    private final UserDetailRepository userDetailRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityService authorityService;
    private final EmailService emailService;

    public RegistrationService(UserDetailRepository userDetailRepository, PasswordEncoder passwordEncoder, AuthorityService authorityService, EmailService emailService) {
        this.userDetailRepository = userDetailRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityService = authorityService;
        this.emailService = emailService;
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
            User user = mapUser(registrationRequest);
            userDetailRepository.save(user);
            emailService.sendEmail(user);
            return RegistrationResponse.builder()
                    .code(200)
                    .message("Registration successful")
                    .build();
        } catch (Exception e) {
            throw new ServerErrorException(e.getMessage(), e.getCause());
        }
    }

    private User mapUser(RegistrationRequest registrationRequest) {
        User user = new User();
        user.setFirstName(registrationRequest.getFirstName());
        user.setLastName(registrationRequest.getLastName());
        user.setEmail(registrationRequest.getEmail());
        user.setEnabled(false);
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setProvider("manual");
        user.setPhoneNumber(registrationRequest.getPhoneNumber());
        user.setCreatedOn(new Date());
        String code = VerificationCodeGenerator.generateCode();
        user.setVerificationCode(code);
        user.setAuthorities(authorityService.getUserAuthority());
        return user;
    }
}
