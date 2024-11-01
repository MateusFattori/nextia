package br.com.fiap.nextia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.nextia.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username); 
}
 