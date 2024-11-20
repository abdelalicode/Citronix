package com.youfarm.citronix.common.utils;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class TokenUtil {

    private final Map<String, Long> tokenStore = new HashMap<>();

    public String generateToken(Long id) {
        String token = UUID.randomUUID().toString();
        tokenStore.put(token, id);
        return token;
    }

    public boolean isAuthenticated(String token) {
        return tokenStore.containsKey(token);
    }

    public Long getUserFromToken(String token) {
        return tokenStore.get(token);
    }

    public void removeToken(String token) {
        tokenStore.remove(token);
    }
}

