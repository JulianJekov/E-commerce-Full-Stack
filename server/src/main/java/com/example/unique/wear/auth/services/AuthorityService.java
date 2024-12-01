package com.example.unique.wear.auth.services;

import com.example.unique.wear.auth.model.entity.Authority;
import com.example.unique.wear.auth.repositories.AuthorityRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AuthorityService {

    private final AuthorityRepository authorityRepository;

    public AuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    public List<Authority> getUserAuthority() {
        Authority authority = authorityRepository.findByRoleCode("USER");
        if (authority == null) {
            throw new RuntimeException("Authority with role 'USER' not found.");
        }

        return Collections.singletonList(authority);

    }

    public Authority createAuthority(String roleCode, String description) {
        Authority authority = Authority
                .builder()
                .roleCode(roleCode)
                .roleDescription(description)
                .build();
        return authorityRepository.save(authority);

    }
}
