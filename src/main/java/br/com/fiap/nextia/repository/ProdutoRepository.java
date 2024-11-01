package br.com.fiap.nextia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.nextia.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    
}
