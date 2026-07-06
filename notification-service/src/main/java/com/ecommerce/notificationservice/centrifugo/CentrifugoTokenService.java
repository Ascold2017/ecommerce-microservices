package com.ecommerce.notificationservice.centrifugo;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class CentrifugoTokenService {

    private final SecretKey secretKey;
    private final long ttlMs;

    public CentrifugoTokenService(
            @Value("${centrifugo.token-secret}") String secret,
            @Value("${centrifugo.token-ttl-ms}") long ttlMs) {
        // ВАЖНО: секрет Centrifugo берём как «сырые» байты строки —
        // НЕ base64-декодируем (в отличие от нашего app-jwt секрета в auth)
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.ttlMs = ttlMs;
    }

    public String generateConnectionToken(String userId) {
        Date now = new Date();
        return Jwts.builder()
                .subject(userId)                               // Centrifugo прочитает sub как user ID
                .issuedAt(now)
                .expiration(new Date(now.getTime() + ttlMs))
                .signWith(secretKey)
                .compact();
    }
}