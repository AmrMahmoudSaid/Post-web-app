package com.example.postwebapp.security;

import com.example.postwebapp.exception.BlogAPIExeption;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;
    @Value("${app.jwt-expiration-milliseconds}")
    private Long jwtExpiratioData;
    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime()+jwtExpiratioData);
        String token = Jwts.builder().setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(key())
                .compact();
        return token;

    }
    private Key key(){
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecret)
        );
    }

    public String getUserName(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
        String username = claims.getSubject();
        return username;
    }
    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(token);
            return true;
        }catch (MalformedJwtException exception){
            throw  new BlogAPIExeption("Invalid JWT token.",HttpStatus.BAD_REQUEST);
        }catch (ExpiredJwtException exception){
            throw new BlogAPIExeption("Expired JWT token." , HttpStatus.BAD_REQUEST);
        }catch (UnsupportedJwtException exception){
            throw new BlogAPIExeption("Unsupported JWT token." , HttpStatus.BAD_REQUEST);
        }catch (IllegalArgumentException exception){
            throw new BlogAPIExeption("JWT claims string is empty." , HttpStatus.BAD_REQUEST);
        }

    }

}
