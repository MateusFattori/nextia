package br.com.fiap.nextia.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.fiap.nextia.model.Token;
import br.com.fiap.nextia.model.User;
import br.com.fiap.nextia.repository.UserRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService {

    private final Algorithm algorithm;
    private final UserRepository userRepository;

    public TokenService(UserRepository userRepository, @Value("${jwt.secret}") String secret) {
        this.userRepository = userRepository;
        this.algorithm = Algorithm.HMAC256(secret);
    }

    public Token createToken(String username) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        var expirationAt = LocalDateTime.now().plus(1, ChronoUnit.HOURS).toInstant(ZoneOffset.ofHours(-3));

        String token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(expirationAt)
                .withIssuer("nextia")
                .withClaim("role", user.getRole().name())
                .sign(algorithm);
        return new Token(token, user.getUsername());
    }

    public User getUserFromToken(String token) {
        var username = JWT.require(algorithm)
                .withIssuer("nextia")
                .build()
                .verify(token)
                .getSubject();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }
}
