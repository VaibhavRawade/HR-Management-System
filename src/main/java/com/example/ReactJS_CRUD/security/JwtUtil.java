package com.example.ReactJS_CRUD.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    // Generate Token
    public String generateToken(String username) {

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    // Extract Username
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Extract Expiration
    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    // Extract Claims
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    // Check Expiry
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Validate Token
    public Boolean validateToken(String token, String username) {

        final String extractedUsername = extractUsername(token);

        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
}