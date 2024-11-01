package br.com.fiap.nextia.controller;

import br.com.fiap.nextia.model.Credentials;
import br.com.fiap.nextia.model.Token;
import br.com.fiap.nextia.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody Credentials credentials) {
        Token token = authService.login(credentials);
        return ResponseEntity.ok(token);
    }
}
