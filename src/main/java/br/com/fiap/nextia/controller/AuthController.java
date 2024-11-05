package br.com.fiap.nextia.controller;

import br.com.fiap.nextia.service.AuthRequest; // Atualize o import
import br.com.fiap.nextia.service.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest) {
        // Aqui você pode adicionar lógica para verificar o usuário e a senha no banco de dados
        String token = jwtUtil.gerarToken(authRequest.getUsuario(), authRequest.getSenha());
        return ResponseEntity.ok(token);
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestParam String token) {
        boolean isValid = jwtUtil.validarToken(token);
        return ResponseEntity.ok(isValid);
    }
}
