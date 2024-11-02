package br.com.fiap.nextia.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.fiap.nextia.model.User;
import br.com.fiap.nextia.repository.UserRepository;
import br.com.fiap.nextia.model.Role; // Importe sua enum de Role

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Inserir múltiplos usuários
        createUserIfNotExists("admin", "admin123", Role.ADMIN);
        createUserIfNotExists("gerente_estoque", "estoque123", Role.GERENTE_ESTOQUE);
        createUserIfNotExists("gerente_clientes", "clientes123", Role.GERENTE_CLIENTES);
        createUserIfNotExists("usuario_comum", "usuario123", Role.USER);
    }

    private void createUserIfNotExists(String username, String password, Role role) {
        // Verifica se o usuário já existe para evitar duplicação
        if (!userRepository.existsByUsername(username)) {
            String encodedPassword = passwordEncoder.encode(password);
            User user = new User(null, username, encodedPassword, role);
            userRepository.save(user);
        }
    }
}