package com.example.unique.wear.auth.services;

import com.example.unique.wear.auth.model.entity.User;
import com.example.unique.wear.auth.repositories.UserDetailRepository;
import org.springframework.stereotype.Service;

@Service
public class VerificationService {

    private final UserDetailRepository userDetailRepository;

    public VerificationService(UserDetailRepository userDetailRepository) {
        this.userDetailRepository = userDetailRepository;
    }

    public void verifyUser(String username) {
        User user = userDetailRepository.findByEmail(username);
        user.setEnabled(true);
        userDetailRepository.save(user);
    }
}
