package com.example.unique.wear.auth.controller;

import com.example.unique.wear.auth.JWT.JWTTokenHelper;
import com.example.unique.wear.auth.model.dto.LoginRequest;
import com.example.unique.wear.auth.model.dto.RegistrationRequest;
import com.example.unique.wear.auth.model.dto.RegistrationResponse;
import com.example.unique.wear.auth.model.dto.UserToken;
import com.example.unique.wear.auth.model.entity.User;
import com.example.unique.wear.auth.services.RegistrationService;
import com.example.unique.wear.auth.services.VerificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final RegistrationService registrationService;
    private final UserDetailsService userDetailsService;
    private final VerificationService verificationService;
    private final JWTTokenHelper jwtTokenHelper;

    public AuthController(AuthenticationManager authenticationManager,
                          RegistrationService registrationService,
                          UserDetailsService userDetailsService,
                          VerificationService verificationService,
                          JWTTokenHelper jwtTokenHelper) {
        this.authenticationManager = authenticationManager;
        this.registrationService = registrationService;
        this.userDetailsService = userDetailsService;
        this.verificationService = verificationService;
        this.jwtTokenHelper = jwtTokenHelper;
    }

    @PostMapping("/login")
    public ResponseEntity<UserToken> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = UsernamePasswordAuthenticationToken
                    .unauthenticated(loginRequest.getUsername(), loginRequest.getPassword());
            Authentication authenticationResponse = this.authenticationManager.authenticate(authentication);

            if (authenticationResponse.isAuthenticated()) {
                User user = (User) authenticationResponse.getPrincipal();
                if (!user.isEnabled()) {
                    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                }

                String token = jwtTokenHelper.generateToken(user.getEmail());
                UserToken userToken = UserToken.builder().token(token).build();
                return new ResponseEntity<>(userToken, HttpStatus.OK);
            }

        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest registrationRequest) {
        RegistrationResponse registrationResponse = registrationService.createUser(registrationRequest);
        if (registrationResponse.getCode() == 400) {
            return new ResponseEntity<>(registrationResponse, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(registrationResponse, HttpStatus.OK);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody Map<String, String> map) {
        String username = map.get("username");
        String code = map.get("code");
        User user = (User) userDetailsService.loadUserByUsername(username);
        if (user != null && user.getVerificationCode().equals(code)) {
            verificationService.verifyUser(username);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
