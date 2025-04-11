package com.ssmtest.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class JwtConfig {
    private SecretKey secretKey;

    public JwtConfig() {
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }

    public String createToken(String username) {
        // 当前时间
        long nowMillis = System.currentTimeMillis();
        // 过期时间
        long expMillis = nowMillis + TimeUnit.MINUTES.toMillis(2);

//        long expMillis = nowMillis + TimeUnit.DAYS.toMillis(7); // 设置为7天
        Date expiration = new Date(expMillis);

        return Jwts.builder()
                .setSubject(username)
                .setExpiration(expiration)  // 设置过期时间
                .signWith(secretKey)  // 使用安全密钥
                .compact();
    }
    public String createRefreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .signWith(secretKey)  // 使用安全密钥
                .compact();
    }

    public boolean validateToken(String token, String username) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return (claims.getSubject().equals(username) && !claims.getExpiration().before(new Date()));
        } catch (ExpiredJwtException e) {
            System.out.println("Token expired: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Invalid token: " + e.getMessage());
        }
        return false;
    }
}
