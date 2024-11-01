package br.com.fiap.nextia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.nextia.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
}
