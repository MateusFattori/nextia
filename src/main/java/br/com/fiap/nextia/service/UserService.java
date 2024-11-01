package br.com.fiap.nextia.service;

import br.com.fiap.nextia.dto.UserProfileResponse;
import br.com.fiap.nextia.model.User;
import br.com.fiap.nextia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return repository.findAll();
    }

    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public UserProfileResponse getProfile(String username) {
        return repository.findByUsername(username)
                .map(UserProfileResponse::new)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<User> search(String name) {
        return repository.findAll().stream()
                .filter(user -> user.getUsername().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }
}
