package com.mthree.academy.c458.team1.food_diary_manager.services.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-ms}")
    private String jwtExpirationMs;

    /**
     * @return The secret key derived from the JWT Secret
     */
    public SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }


    /**
     * Generate a JWT, containing claims for subject (username), role, issued at, and expiration.
     * @param authentication The authentication object for the signed-in user.
     * @return A newly-generated signed JWT.
     */
    public String generateJwt(Authentication authentication) {

        //Calculate JWT expiration
        Instant currentDateTime = Instant.now();
        Instant expirationDateTime = currentDateTime.plusMillis(Long.parseLong(jwtExpirationMs));

        //Find user role
        String role = authentication.getAuthorities().stream()
            .findFirst()
            .map(GrantedAuthority::getAuthority)
            .orElseThrow();

        //Generate JWT token - Jwts requires Date class.
        return Jwts.builder()
            .subject(authentication.getName()) //The username
            .claim("role", role) //Add claim for user role
            .issuedAt(Date.from(currentDateTime))
            .expiration(Date.from(expirationDateTime))
            .signWith(getSigningKey(), Jwts.SIG.HS256) //Sign token with SHA-256 (HS256) algorithm
            .compact(); //Convert token into string.
    }

}
