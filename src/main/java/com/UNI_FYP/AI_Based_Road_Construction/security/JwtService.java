package com.UNI_FYP.AI_Based_Road_Construction.security;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    // Read from application.properties
    // app.jwt.secret=404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970
    @Value("${app.jwt.secret}")
    private String secretKey;

    // app.jwt.expiration=86400000  (24 hours in milliseconds)
    @Value("${app.jwt.expiration}")
    private long jwtExpiration;

    // ─────────────────────────────────────────────────────────────
    // PUBLIC METHODS — these are what you call from AuthService
    // ─────────────────────────────────────────────────────────────

    /**
     * Generate a token for a user with no extra claims.
     * Called on successful login.
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Generate a token with extra claims (e.g. role, departmentId).
     * These claims are embedded inside the token and readable on the frontend.
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())   // stores email as subject
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Check if a token is valid for a given user.
     * Called in JwtAuthFilter on every request.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        // token is valid if username matches AND token is not expired
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Extract the email (username) from a token.
     * Called in JwtAuthFilter to find which user made the request.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // ─────────────────────────────────────────────────────────────
    // PRIVATE HELPERS — internal plumbing, you don't call these
    // ─────────────────────────────────────────────────────────────

    /**
     * Generic method to extract any single claim from a token.
     * e.g. extractClaim(token, Claims::getSubject) → email
     *      extractClaim(token, Claims::getExpiration) → expiry date
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Parse the full token and return all claims.
     * Throws JwtException if the token is tampered with or expired.
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Convert the base64-encoded secret key string into a Key object.
     * HMAC-SHA256 requires a key of at least 256 bits (32 bytes).
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}