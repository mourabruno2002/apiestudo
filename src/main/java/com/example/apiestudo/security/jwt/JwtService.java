package com.example.apiestudo.security.jwt;

import com.example.apiestudo.config.JwtProperties;
import com.example.apiestudo.exception.jwt.InvalidTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtService {

    private final JwtProperties jwtProperties;
    private final SecretKey signingKey;

    public JwtService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.signingKey = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    public String generateToken(UserDetails userDetails) {

        Map<String, Object> claims = new HashMap<>();
        String subject = userDetails.getUsername();

        return buildToken(claims, subject);
    }

    public Claims extractAllClaims(String token) {
        if (token == null || token.isBlank()) {
            throw new InvalidTokenException("Invalid token.");
        }

        JwtParser parser = Jwts.parserBuilder().setSigningKey(getSigningKey()).build();

        return parser.parseClaimsJws(token).getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);

        return resolver.apply(claims);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean isTokenValid(String token) {
        try {
            extractAllClaims(token);
            return true;

        } catch (JwtException e) {
            return false;
        }
    }

    public Date getExpirationDate() {

        return new Date(System.currentTimeMillis() + jwtProperties.getExpiration());
    }


    //INTERNAL METHODS
    private SecretKey getSigningKey() {
        return signingKey;
    }

    private String buildToken(Map<String, Object> claims, String subject) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(getExpirationDate())
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
