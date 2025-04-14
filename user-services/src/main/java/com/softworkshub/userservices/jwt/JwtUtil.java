package com.softworkshub.userservices.jwt;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private static final Long JWT_EXPIRATION_TIME = 5 * 60 * 60L;

    private static final String secret = "6d044ae3d024f19e1f64c3bd572ed411116523caf7ddfb7dda16e73a5172804676f1d9350098f151dd680fbdd267c8ef1e5b72150c0d97c1e43c90fa3ac1d906";

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;
        claims.put("email", customUserDetails.getEmail());
        claims.put("roles", customUserDetails.getRoles());
        claims.put("name",customUserDetails.getName());
        return doGenerateToken(claims, customUserDetails.getUsername());
    }

    public String doGenerateToken(Map<String, Object> claims, String subject) {
        return
                Jwts.builder()
                        .setClaims(claims)
                        .setSubject(subject)
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_TIME * 1000))
                        .signWith(SignatureAlgorithm.HS512 , secret)
                        .compact();
    }

}
