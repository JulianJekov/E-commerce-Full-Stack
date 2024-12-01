package com.example.unique.wear.auth.services;

import com.example.unique.wear.auth.model.entity.Authority;
import com.example.unique.wear.auth.model.entity.User;
import com.example.unique.wear.auth.repositories.UserDetailRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OAuth2Service {

    private final UserDetailRepository userDetailRepository;
    private final AuthorityService authorityService;

    public OAuth2Service(UserDetailRepository userDetailRepository, AuthorityService authorityService) {
        this.userDetailRepository = userDetailRepository;
        this.authorityService = authorityService;
    }

    public User getUser(String userName) {
        return userDetailRepository.findByEmail(userName);
    }

    @Transactional
    public User createUser(OAuth2User oAuth2User, String provider) {
        String firstName = oAuth2User.getAttribute("given_name");
        String lastName = oAuth2User.getAttribute("family_name");
        String email = oAuth2User.getAttribute("email");
        List<Authority> authorities = authorityService.getUserAuthority();
        User user= User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .provider(provider)
                .enabled(true)
                .authorities(authorities)
                .build();
        return userDetailRepository.save(user);
    }
}
