package com.woc.emailscheduler.service;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Base64;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}") // Load secret from application.properties
    private String secretKey;

    // Make sure the secret key is 256 bits (32 bytes) long by directly using the bytes.
    private byte[] getSecretKey() {
        byte[] secretKeyBytes = secretKey.getBytes();
        System.out.println("Secret key length: " + secretKeyBytes.length);
        if (secretKeyBytes.length < 32) {
            throw new IllegalArgumentException("Secret key must be at least 256 bits (32 bytes) long.");
        }
        return secretKeyBytes;
    }


    // ✅ Generate Token
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(SignatureAlgorithm.HS256, getSecretKey()) // Use raw bytes for the secret key
                .compact();
    }

    // ✅ Validate Token and refresh if expired
    public String validateTokenAndRefresh(String token, UserDetails userDetails) {
        if (isTokenExpired(token)) {
            // If the token is expired, generate a new one
            return generateToken(userDetails);
        } else if (validateToken(token, userDetails)) {
            // If valid, return the same token
            return token;
        } else {
            // Invalid token case, returning null or throw exception as per your use case
            return null;
        }
    }

    // ✅ Validate Token
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // ✅ Extract Username
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // ✅ Extract Expiration Date
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // ✅ Generic Claim Extraction
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser()
                .setSigningKey(getSecretKey()) // Use raw bytes for secret key
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }

    // ✅ Check if Token is Expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // ✅ Refresh Token if expired (helper method for regenerating token)
    public String refreshToken(UserDetails userDetails) {
        return generateToken(userDetails);
    }
}
