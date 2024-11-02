package br.com.fiap.nextia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.fiap.nextia.model.Credentials;
import br.com.fiap.nextia.model.User;
import br.com.fiap.nextia.service.AuthService;

@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping
    public ResponseEntity<User> login(@RequestBody Credentials credentials) {
        User user = authService.authenticate(credentials);

        if (user != null) {
            return ResponseEntity.ok(user);
        }

        return ResponseEntity.status(401).build();
    }
}
