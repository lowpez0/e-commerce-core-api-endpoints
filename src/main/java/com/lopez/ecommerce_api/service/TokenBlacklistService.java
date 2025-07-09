package com.lopez.ecommerce_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

//Redis should be installed to use this
@Service
@RequiredArgsConstructor
public class TokenBlacklistService {

    private final RedisTemplate<String, String> redisTemplate;

    public void addToBlackList(String token, Duration expiryTime) {
        redisTemplate.opsForValue().set(
                "blacklist:" + token,
                "revoked",
                expiryTime
        );
    }

    public boolean isBlackListed(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey("blacklist:" + token));
    }
}
