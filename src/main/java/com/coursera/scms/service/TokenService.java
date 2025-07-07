package com.coursera.scms.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class TokenService {

    // Секретный ключ (в реальном приложении хранить в конфиге)
    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Метод генерации токена на основе email
    public String generateToken(String email) {
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + 3600000; // 1 час жизни токена

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(nowMillis))
                .setExpiration(new Date(expMillis))
                .signWith(secretKey)
                .compact();
    }

    // Метод для получения ключа подписи (если нужен)
    public SecretKey getSigningKey() {
        return secretKey;
    }
}
