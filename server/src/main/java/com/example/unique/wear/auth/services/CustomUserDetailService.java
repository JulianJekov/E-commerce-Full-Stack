package com.example.unique.wear.auth.services;

import com.example.unique.wear.auth.model.entity.User;
import com.example.unique.wear.auth.repositories.UserDetailRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailService implements UserDetailsService {

    private final UserDetailRepository userDetailRepository;

    public CustomUserDetailService(UserDetailRepository userDetailRepository) {
        this.userDetailRepository = userDetailRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDetailRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with email " + username  + " not found!");
        }
        Hibernate.initialize(user.getAddresses());
        return user;
    }
}
