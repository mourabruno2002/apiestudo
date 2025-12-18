package com.example.apiestudo.security.jwt;

import com.example.apiestudo.exception.jwt.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;


@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long expiration;

    //GETTER
    public Long getExpiration() {
        return expiration;
    }

    public Claims extractAllClaims(String token) {
        if (token == null || token.isBlank()) {
            throw new InvalidTokenException("Invalid token.");
        }

        String jwtToken = token;

        if (token.toLowerCase().startsWith("bearer ")) {
            jwtToken = token.substring("bearer ".length()).trim();
        }

        JwtParser parser = Jwts.parserBuilder().setSigningKey(getSigningKey()).build();

        return parser.parseClaimsJws(jwtToken).getBody();
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

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String subject = extractUsername(token);

        return Objects.equals(subject, userDetails.getUsername());
    }

    public String generateToken(UserDetails userDetails) {

        Map<String, Object> claims = new HashMap<>();
        String subject = userDetails.getUsername();

        return buildToken(claims, subject);
    }

    public Date getExpirationDate() {

        return new Date(System.currentTimeMillis() + expiration);
    }

    // INTERNAL METHODS
    private SecretKey getSigningKey() {

        byte[] keyBites = secretKey.getBytes();

        return Keys.hmacShaKeyFor(keyBites);
    }

    private String buildToken(Map<String, Object> claims, String subject) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
