package br.com.fiap.nextia.service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.stereotype.Service;

@Service
public class JwtUtil {
    public String gerarToken(String usuario, String senha) {
        String payload = usuario + ":" + senha;
        return Base64.getEncoder().encodeToString(payload.getBytes(StandardCharsets.UTF_8));
    }

    public String decodificarToken(String token) {
        byte[] decodedBytes = Base64.getDecoder().decode(token);
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }

    public boolean validarToken(String token) {
        String decoded = decodificarToken(token);
        return decoded != null && !decoded.isEmpty();
    }
}
