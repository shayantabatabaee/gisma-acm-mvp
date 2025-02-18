package com.gisma.competition.acm.util;

import com.gisma.competition.acm.api.exception.JwtTokenExpiredException;
import com.gisma.competition.acm.persistence.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    public static final String BEARER = "Bearer ";

    @Value("${security.jwt.secret-key}")
    private String secretKey;
    @Value("${security.jwt.expiration-time}")
    private Long expirationTime;

    public String generateToken(User user) {
        return Jwts.builder().
                subject(user.getUsername()).
                claim("email", user.getEmail()).
                issuedAt(new Date()).
                expiration(new Date(System.currentTimeMillis() + expirationTime)).
                signWith(getSecretKey()).
                compact();

    }

    public String extractUsername(String token) throws JwtTokenExpiredException {
        return getClaimsFromToken(token).getSubject();
    }

    public boolean validateToken(String token, UserDetails user) throws JwtTokenExpiredException {
        String username = extractUsername(token);
        return user.getUsername().equals(username);
    }

    private Claims getClaimsFromToken(String token) throws JwtTokenExpiredException {
        try {
            return Jwts.parser().
                    verifyWith(getSecretKey()).
                    build().
                    parseSignedClaims(token).
                    getPayload();
        } catch (ExpiredJwtException e) {
            throw new JwtTokenExpiredException();
        }
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}
