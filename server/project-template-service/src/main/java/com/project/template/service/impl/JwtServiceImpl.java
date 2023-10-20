package com.project.template.service.impl;

import com.project.template.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Ouweshs28
 */
@Service
public class JwtServiceImpl implements JwtService {

    private static final String SECRET_KEY = "spULDih0YgQTz+4h6+JI+TzhL+NkIugOO5JIM3p5X4V4ngLbmdCK1vd0tBJK7JXz";
    public static final int TIME_TO_EXPIRE = 1000 * 60 * 60 * 10;

    @Override
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    @Override
    public Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(getClaimsFromToken(token));
    }

    @Override
    public String generateToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + TIME_TO_EXPIRE))
                .signWith(getSigningKey())
                .compact();
    }

    @Override
    public String generateToken(String username) {
        return generateToken(new HashMap<>(), username);
    }

    @Override
    public boolean isTokenValid(String token, String username) {
        return (username.equals(getUsernameFromToken(token)) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return getExpirationFromToken(token).before(new Date());
    }

    private Date getExpirationFromToken(String token) {
        return getClaimsFromToken(token).getExpiration();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
