package com.security.xo.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ClaimsBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.sql.Time;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {
    @Value("${my.custom.jwtkey}")
    String ky;
    Long valdity= TimeUnit.MINUTES.toMillis(60);
    public String generateToken(UserDetails userDetails){
        Map<String,String> claims=new HashMap<>();
        claims.put("iss","http://localhost:8080");
        claims.put("msg","pls work");
        return Jwts.builder()
                .claims(claims)
                .issuedAt(Date.from(Instant.now()))
                .expiration(
                        Date.from(
                                Instant.now()
                                        .plusMillis(valdity)
                        )
                )
                .subject(userDetails.getUsername())
                .signWith(generateKey())
                .compact();
    }
    SecretKey generateKey(){
        byte[] decodedKy=ky.getBytes();
        return Keys.hmacShaKeyFor(decodedKy);
    }
    String getUserName(String jwt){
        Claims claims=Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
        return claims.getSubject();
    }
}
