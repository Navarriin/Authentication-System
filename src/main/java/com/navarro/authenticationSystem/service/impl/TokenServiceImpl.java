package com.navarro.authenticationSystem.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.navarro.authenticationSystem.models.User;
import com.navarro.authenticationSystem.service.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenServiceImpl implements TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    @Override
    public String generateToken(User user) {
        try {
            Algorithm algorithm = setAlgorithm(this.secret);
            return JWT.create()
                    .withIssuer("api-users")
                    .withSubject(user.getUserName())
                    .withExpiresAt(this.genExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while authenticating");
        }
    }

    @Override
    public String validateToken(String token) {
        try {
            Algorithm algorithm = setAlgorithm(this.secret);
            return JWT.require(algorithm)
                    .withIssuer("api-users")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    private Algorithm setAlgorithm(String secret) {
        return Algorithm.HMAC256(secret);
    }
}
