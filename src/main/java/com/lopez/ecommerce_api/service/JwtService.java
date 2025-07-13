package com.lopez.ecommerce_api.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lopez.ecommerce_api.model.Role;
import com.lopez.ecommerce_api.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Value("${secret.key}")
    private String SECRET_KEY;

    public String generateAccessToken(HttpServletRequest request, User user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 30000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                .sign(getSignInKey());
    }

    public String generateRefreshToken(HttpServletRequest request, User user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 3000000))
                .withIssuer(request.getRequestURL().toString())
                .sign(getSignInKey());
    }

    public DecodedJWT isTokenValid(String token) {
        JWTVerifier verifier = JWT.require(getSignInKey()).build();
        return verifier.verify(token);
    }

    public Algorithm getSignInKey() {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
        return Algorithm.HMAC256(keyBytes);
    }

}
