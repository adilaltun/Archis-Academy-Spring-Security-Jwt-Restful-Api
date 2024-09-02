package com.works.archisproject.service.impl;

import com.works.archisproject.exception.TokenExpiredException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.key}")
    private String SECRET;

    /**
     *
     * @param email
     * @return String
     *
     * @apiNote  JWT Token üretmek için yazılan method
     *
     * @implNote HS256 ve RS256 için hangisi daha mantıklı açıklama:  https://auth0.com/blog/rs256-vs-hs256-whats-the-difference/
     *
     */
    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, email);
    }


    /**
     *
     * @param claims
     * @param email
     * @return String
     * @code .setClaims(claims) -> jwt sitesindeki payload'a karşılık geliyor.
     * @code .setIssuedAt(new Date(System.currentTimeMillis())) -> ne zaman bu token üretildi.
     * @code .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15)) -> ne zaman bu token geçersiz olacak. 30 dakika için -> 1000 * 60 * 30, 15 saniye için -> 1000 * 15
     * @code .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15)) -> ne zaman bu token geçersiz olacak.
     * @code .signWith(getSignKey(),SignatureAlgorithm.RS256).compact(); -> oluşturulan keyi HS256 ile şifreliyor.
     *
     */
    private String createToken(Map<String, Object> claims, String email) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 100))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     *
     * Verilen SECRET keyini decode edip bize bir key oluşturmamızı sağlayacak.
     *
     */
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     *
     * @param token
     * @return Boolean
     *
     * @apiNote Gelen JWT Token'ı validate etmek için yazılmış olan method.
     * Bu methotta email ve expiration süresine bakar eğer varsa bunlar true döndürür. Diğer türlü false döndürür.
     * Gelen Token'ı validate etmemiz için extract edip içerisindeki claimlerin kontrolünü sağlamak gerekli.
     * @implNote  validateToken methodu için extractExpiration ve extractUser methodlarını oluşturduk.
     *
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        try {
            String username = extractUser(token);
            Date expirationDate = extractExpiration(token);
            return userDetails.getUsername().equals(username) && !expirationDate.before(new Date());
        }catch (TokenExpiredException exception) {
            throw new TokenExpiredException(exception.getMessage());
        }
    }

    /**
     *
     * @param token
     * @return Date
     *
     * @apiNote Gelen token'ın ne zaman expiration olduğunu söyleyecek olan method.
     *
     * @code Jwts.parserBuilder() -> gelen token'ı parse etmek için
     *
     */
    private Date extractExpiration(String token) {
        try {
            Claims claims = Jwts
                    .parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getExpiration();
        }catch (TokenExpiredException exception) {
            throw new TokenExpiredException(exception.getMessage());
        }

    }

    /**
     *
     * @param token
     * @return String
     *
     * @apiNote Gelen email'in doğruluğunu kontrol edecek olan method.
     */
    public String extractUser(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }




}
