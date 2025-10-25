package tj.alimov.gorcerystoreinventory.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class JwtService {


    private final Key SECRET_KEY;


    public JwtService(@Value("${jwt.secret}") String value) {
        SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(value));
    }
    // Extracts username from token
    public String extractUsername(String token){
        // Check if token is expired
        if (isTokenExpired(token)) return "";
        // Get username from claims
        return extractClaims(token).get("username", String.class);
    }
    // Extract userId from token
    public Long extractUserId(String token){
        // Check if token is expired
        if (isTokenExpired(token)) return null;
        return extractClaims(token).get("userId", Long.class);
    }
    // Extract roles from token
    public Set<String> extractRoles(String token){
        // Check if token is expired
        if (isTokenExpired(token)) return new HashSet<>();

        // Get all roles
        @SuppressWarnings("unchecked")
        Set<String> roles = extractClaims(token).get("roles", Set.class);
        return roles;
    }

    // Checks if token is expired
    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
    // Extracts all claims for given token
    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
