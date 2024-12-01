package com.example.unique.wear.auth.controller;

import com.example.unique.wear.auth.JWT.JWTTokenHelper;
import com.example.unique.wear.auth.model.entity.User;
import com.example.unique.wear.auth.services.OAuth2Service;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/oauth2")
public class OAuth2Controller {

    private final OAuth2Service oAuth2Service;
    private final JWTTokenHelper jwtTokenHelper;

    public OAuth2Controller(OAuth2Service oAuth2Service, JWTTokenHelper jwtTokenHelper) {
        this.oAuth2Service = oAuth2Service;
        this.jwtTokenHelper = jwtTokenHelper;
    }

    @GetMapping("/success")
    public void callbackOAuth2(@AuthenticationPrincipal OAuth2User oAuth2User,
                               HttpServletResponse response) throws IOException {

        String username = oAuth2User.getAttribute("email");
        User user = oAuth2Service.getUser(username);
        if (user == null) {
            user = oAuth2Service.createUser(oAuth2User, "google");
        }

        String token = jwtTokenHelper.generateToken(user.getUsername());

        response.sendRedirect("http://localhost:3000/oauth2/callback?token=" + token);
    }
}
