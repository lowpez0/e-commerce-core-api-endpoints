package com.lopez.ecommerce_api.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lopez.ecommerce_api.dto.RequestAuthentication;
import com.lopez.ecommerce_api.dto.RequestRegister;
import com.lopez.ecommerce_api.dto.ResponseAuthentication;
import com.lopez.ecommerce_api.dto.ResponseRefreshToken;
import com.lopez.ecommerce_api.model.User;
import com.lopez.ecommerce_api.service.AuthService;
import com.lopez.ecommerce_api.service.JwtService;
import com.lopez.ecommerce_api.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/auth/login")
    public ResponseEntity<ResponseAuthentication> login(@RequestBody RequestAuthentication requestLogin,
                                                        HttpServletRequest httpServletRequest) {
        ResponseAuthentication responseAuthentication = authService.authenticateUser(requestLogin, httpServletRequest);
        if(!responseAuthentication.isSuccess()) {
            return new ResponseEntity<>(responseAuthentication, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(responseAuthentication, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/auth/register")
    public ResponseEntity<RequestRegister> saveUser(@RequestBody RequestRegister register) {

    }

    @GetMapping("/auth/refresh-token")
    public ResponseEntity<ResponseRefreshToken> refreshToken(HttpServletRequest request) {
        ResponseRefreshToken responseRefreshToken = authService.refreshToken(request);
        if(!responseRefreshToken.isValid()) {
            return new ResponseEntity<>(responseRefreshToken, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(responseRefreshToken, HttpStatus.OK);
    }

     /*
    @PostMapping("/role/addToUser")
    public ResponseEntity<?> saveRole(@RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    } */

    @Data
    class RoleToUserForm {
        private String username;
        private String roleName;
    }
}
