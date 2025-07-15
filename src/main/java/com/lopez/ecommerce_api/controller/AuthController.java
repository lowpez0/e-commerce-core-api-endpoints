package com.lopez.ecommerce_api.controller;

import com.lopez.ecommerce_api.dto.*;
import com.lopez.ecommerce_api.model.User;
import com.lopez.ecommerce_api.service.AuthService;
import com.lopez.ecommerce_api.service.user_service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody RequestAuthentication requestLogin,
                                                        HttpServletRequest httpServletRequest) {
        ResponseAuthentication responseAuthentication = authService.authenticateUser(requestLogin, httpServletRequest);
        if(!responseAuthentication.isSuccess()) {
            return new ResponseEntity<>(new ErrorResponse(responseAuthentication.getError()), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(responseAuthentication, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/auth/register")
    public ResponseEntity<?> registerUser(@RequestBody RequestRegister register,
                                                     HttpServletRequest request) {
        ResponseRegister responseRegister = authService.registerUser(register, request);
        if(!responseRegister.isRegistered()) {
            return new ResponseEntity<>(new ErrorResponse(responseRegister.getError()), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(responseRegister, HttpStatus.OK);

    }

    @GetMapping("/auth/refresh-token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        ResponseRefreshToken responseRefreshToken = authService.refreshToken(request);
        if(!responseRefreshToken.isValid()) {
            return new ResponseEntity<>(new ErrorResponse(responseRefreshToken.getError()), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(responseRefreshToken, HttpStatus.OK);
    }

}
