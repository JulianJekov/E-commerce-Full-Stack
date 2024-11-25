package com.example.unique.wear.auth.services;

import com.example.unique.wear.auth.model.entity.Authority;
import com.example.unique.wear.auth.repositories.AuthorityRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorityService {

    private final AuthorityRepository authorityRepository;

    public AuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    public List<Authority> getUserAuthority() {
        List<Authority> authorities = new ArrayList<>();
        Authority authority = authorityRepository.findByRoleCode("USER");
        authorities.add(authority);
        return authorities;
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
