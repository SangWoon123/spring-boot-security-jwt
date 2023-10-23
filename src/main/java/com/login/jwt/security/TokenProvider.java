package com.login.jwt.security;

import com.login.jwt.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
/*
    토큰 생성 및 검증
 */
@Service
public class TokenProvider {

    private final String SECRET_KEY;
    public TokenProvider( @Value("${secret.key}") String secretKey) {
        SECRET_KEY = secretKey;
    }

    /*
        JWT 토큰생성
        header(타입,알고리즘) / payload(내용) / signature(서명)
     */
    public String create(User user){
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
                .setSubject(user.getId())
                .setIssuer("Jwt login")
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now()
                        .plus(1, ChronoUnit.DAYS)))
                .compact();
    }

    /*
        Jwt 검증 Payload에 해당하는 userId를 반환
     */
    public String validateAndGetUserId(String token){
        Claims claims=Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
