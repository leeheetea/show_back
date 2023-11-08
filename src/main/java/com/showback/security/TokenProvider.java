package com.showback.security;

import com.showback.model.User;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class TokenProvider {
    private static final String SECRET_KEY = "FlRpX30pMqDbiAkmlfArbrmVkDD4RqISskGZmBFax5oGVxzXXWUzTR5JyskiHMIV9M1Oicegkpi46AdvrcX1E6CmTUBc6IFbTPiD";

    public String create(User userEntity, String ipAddress, String userAgent) {
        Date expireyDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
        String socialCode = null;
        if (userEntity.getSocialLogin() != null) {
            socialCode = userEntity.getSocialLogin().getSocialCode();
        }

        JwtBuilder jwtBuilder = Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setSubject(userEntity.getUserId().toString())
                .claim("ipAddress", ipAddress)
                .claim("userAgent", userAgent)
                .claim("loginType", userEntity.getLoginType())
                .claim("username", userEntity.getUsername())
                .setIssuer("showday")
                .setIssuedAt(new Date())
                .setExpiration(expireyDate);

        if(socialCode != null) {
            jwtBuilder.claim("socialCode", socialCode);
        }

        return jwtBuilder.compact();
    }

    public String validateAndGetUserId (String token){
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean isTokenExpired(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        Date expiration = claims.getExpiration();
        return expiration.before(new Date());
    }
}
