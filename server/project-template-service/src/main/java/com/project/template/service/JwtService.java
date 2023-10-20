package com.project.template.service;

import io.jsonwebtoken.Claims;

import java.util.Map;
import java.util.function.Function;

public interface JwtService {
    String getUsernameFromToken(String token);

    Claims getClaimsFromToken(String token);

    <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver);

    String generateToken(Map<String, Object> claims, String username);

    String generateToken(String username);

    public boolean isTokenValid(String token, String username);
}
