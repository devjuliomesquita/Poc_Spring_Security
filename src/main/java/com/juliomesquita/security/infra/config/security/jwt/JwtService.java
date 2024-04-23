package com.juliomesquita.security.infra.config.security.jwt;

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

    @Value("${spring.api.security.token.secret-key}")
    private String SECRET_KEY;

    @Value("${spring.api.security.token.expiration}")
    private Long ACCESS_EXPIRATION;

    @Value("${spring.api.security.token.refresh-token.expiration}")
    private Long REFRESH_EXPIRATION;

    public String extractUserCpf(String jwt) {
        return this.extractClaim(jwt, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> cloimsResolver) {
        final Claims claims = this.extractAllClaims(token);
        return cloimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] decode = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(decode);
    }

    public String generateAccessToken(UserDetails userDetails) {
        return this.generateAccessToken(new HashMap<>(), userDetails);
    }

    public String generateAccessToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return this.buildToken(extraClaims, userDetails, ACCESS_EXPIRATION);
    }

    public String generateRefreshToken(
            UserDetails userDetails
    ) {
        return this.buildToken(new HashMap<>(), userDetails, REFRESH_EXPIRATION);
    }


    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = this.extractUserCpf(token);
        return (username.equals(userDetails.getUsername())) && !this.isTokenExpired(token);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private boolean isTokenExpired(String token) {
        return this.extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return this.extractClaim(token, Claims::getExpiration);
    }
}
