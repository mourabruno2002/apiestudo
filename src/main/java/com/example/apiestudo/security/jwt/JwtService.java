package com.example.apiestudo.security.jwt;

import com.example.apiestudo.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
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
        this.signingKey = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        return buildToken(claims, userDetails);
    }

    public String extractUsername(String token) {

        return extractClaim(token, Claims::getSubject);
    }

    public Date calculateExpirationDate() {

        return new Date(System.currentTimeMillis() + jwtProperties.getExpiration());
    }

    public boolean isTokenValid(String token) {
        try {
            return extractExpiration(token).after(new Date());

        } catch (JwtException e) {
            return false;
        }
    }

    //INTERNAL METHODS
    private Claims extractAllClaims(String token) {

        return Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token).getBody();
    }

    private Date extractExpiration(String token) {

        return  extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);

        return resolver.apply(claims);
    }

    private String buildToken(Map<String, Object> claims, UserDetails userDetails) {

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(calculateExpirationDate())
                .setSubject(userDetails.getUsername())
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }
}
