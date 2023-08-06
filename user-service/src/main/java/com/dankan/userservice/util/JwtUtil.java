package com.dankan.userservice.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Slf4j
@Component
public class JwtUtil {
    public static String JWT_SECRET_KEY;
    private static final long EXPIRATION_TIME =  1000 * 60 * 60 * 24 * 365; // 365일
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 365; // 365일
    public static final long ACCESS_TOKEN_EXPIRE_TIME = 365;

    @Value("${jwt.secret}")
    public void setKey(String key) {
        JWT_SECRET_KEY = key;
    }

    public static String createJwt(Long memberId) {
        Date now = new Date();

        Date expiredDate = new Date(now.getTime() + EXPIRATION_TIME);

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", memberId);
        claims.put("roles", Arrays.asList("ROLE_ADMIN", "ROLE_USER"));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
                .compact();
    }

    public static String logout() {
        Date now = new Date();

        Date expiredDate = new Date(now.getTime() - EXPIRATION_TIME);

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", JwtUtil.getMemberId());
        claims.put("roles", Arrays.asList(/*"ROLE_ADMIN", */"ROLE_USER"));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
                .compact();
    }

    // jwt refresh 토큰 생성
    public static String createRefreshToken(Long id) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);

        Date now = new Date();
        return Jwts.builder()
                .setIssuedAt(now)
                .setClaims(claims)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
                .compact();
    }

    public static boolean isExpired(String token) {
        boolean isExpired = true;

        try {
            Jwts.parserBuilder()
                    .setSigningKey(JWT_SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();

            isExpired = false;
        }
        catch (ExpiredJwtException ex) {
            isExpired = true;
        }

        return isExpired;
    }

    public static Long getMemberId() {
        String token = JwtUtil.getAccessToken();

        Claims body = Jwts.parserBuilder()
                .setSigningKey(JWT_SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return body.get("id", Long.class);
    }

    public static List<String> getRoles() {
        String token = JwtUtil.getAccessToken();

        Claims body = Jwts.parserBuilder()
                .setSigningKey(JWT_SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return body.get("roles", List.class);
    }

    public static String getAccessToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
        }
        else
        {
            throw new RuntimeException();
        }

        return token;
    }

    public static String getRefreshToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String authorizationHeader = request.getHeader("RefreshToken");
        String token = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
        }
        else
        {
            throw new RuntimeException();
        }

        return token;
    }
}