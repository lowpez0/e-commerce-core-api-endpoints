package com.lopez.ecommerce_api.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lopez.ecommerce_api.dto.*;
import com.lopez.ecommerce_api.model.User;
import com.lopez.ecommerce_api.service.cart_service.CartService;
import com.lopez.ecommerce_api.service.user_service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtService jwtService;
    private final CartService cartService;

    public AuthService(PasswordEncoder passwordEncoder, UserService userService, JwtService jwtService, CartService cartService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.jwtService = jwtService;
        this.cartService = cartService;
        System.out.println("HEY\n\n\n\n\n\n\n\n\"");
    }

    public ResponseRegister registerUser(RequestRegister register,
                                         HttpServletRequest request) {
        if(userService.existsByUsername(register.username()) ) {
            return ResponseRegister.builder().error("Username already exist").registered(false).build();} else if (userService.existsByEmail(register.email())) {
            return ResponseRegister.builder().error("Email already exist").registered(false).build();
        }
        User user = User.builder()
                .fullName(register.fullName())
                .username(register.username())
                .email(register.email())
                .password(passwordEncoder.encode(register.password()))
                .roles(new ArrayList<>())
                .build();
        user.getRoles().add(userService.findRoleByName("ROLE_CUSTOMER"));
        userService.saveUser(user);
        cartService.addCartToUser(register.username()); // add a cart immediately right after a user signed up
        String accessToken = jwtService.generateAccessToken(request, user);
        String refreshToken = jwtService.generateRefreshToken(request, user);
        return ResponseRegister.builder()
                .registered(true)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

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
