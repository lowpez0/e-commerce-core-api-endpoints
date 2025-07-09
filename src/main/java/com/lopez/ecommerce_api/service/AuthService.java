package com.lopez.ecommerce_api.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lopez.ecommerce_api.dto.RequestAuthentication;
import com.lopez.ecommerce_api.dto.ResponseAuthentication;
import com.lopez.ecommerce_api.dto.ResponseRefreshToken;
import com.lopez.ecommerce_api.model.User;
import com.lopez.ecommerce_api.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtService jwtService;

    public ResponseAuthentication authenticateUser(RequestAuthentication requestLogin,
                                                   HttpServletRequest request) {
        User user = userService.getUser(requestLogin.getUsername());
        if(user == null || !passwordEncoder.matches(requestLogin.getPassword(), user.getPassword())) {
            return ResponseAuthentication.builder().error("Invalid credentials").success(false).build();
        }
        String accessToken = jwtService.generateAccessToken(request, user);
        String refreshToken = jwtService.generateRefreshToken(request, user);

        return ResponseAuthentication.builder().accessToken(accessToken).refreshToken(refreshToken).success(true).build();
    }

    public ResponseRefreshToken refreshToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authorizationHeader.substring(7);
                DecodedJWT decodedJWT = jwtService.isTokenValid(refreshToken);
                User user = userService.getUser(/*username*/decodedJWT.getSubject());
                String accessToken = jwtService.generateAccessToken(request, user);
                return ResponseRefreshToken.builder().accessToken(accessToken).valid(true).build();
            } catch(JWTVerificationException e) { return ResponseRefreshToken.builder().valid(false).error(e.getMessage()).build(); }

            }
        return ResponseRefreshToken.builder().valid(false).build();
    }

}
