package com.inkly.inkly_backend.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JwtService {

    private final PrivateKey privateKey;
    private final PublicKey publicKey;
    private final String issuer;

    public JwtService(PrivateKey privateKey, PublicKey publicKey, @Value("${jwts.issuer}") String issuer) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.issuer = issuer;
    }

    public String generateToken(UserDetails userDetails) {
        Instant now = Instant.now();
        return Jwts.builder()
                .issuer(issuer)
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(2, ChronoUnit.MINUTES)))
                .claim("authorities",userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .signWith(privateKey, Jwts.SIG.RS256)
                .compact();
    }

    public Claims validateToken(String token) {
        return Jwts.parser()
                .verifyWith(publicKey)
                .requireIssuer(issuer)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
