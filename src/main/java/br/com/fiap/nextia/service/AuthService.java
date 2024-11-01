package br.com.fiap.nextia.service;

import br.com.fiap.nextia.model.Credentials;
import br.com.fiap.nextia.model.Token;
import br.com.fiap.nextia.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, TokenService tokenService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    public Token login(Credentials credentials) {
        var user = userRepository.findByUsername(credentials.username())
                .orElseThrow(() -> new RuntimeException("Access denied"));
    
        if (!passwordEncoder.matches(credentials.password(), user.getPassword())) {
            throw new RuntimeException("Access denied");
        }
    
        Token token = tokenService.createToken(user.getUsername());
        return new Token(token.token(), user.getUsername());
    }
    
}
