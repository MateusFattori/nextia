package br.com.fiap.nextia.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.nextia.model.Cliente;
import br.com.fiap.nextia.repository.ClienteRepository;
import br.com.fiap.nextia.service.ClassificacaoService; // Importando o ClassificacaoService
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/cliente")
@Slf4j
public class ClienteController {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ClassificacaoService classificacaoService; // Injetando o ClassificacaoService

    @GetMapping
    public List<Cliente> index() {
        return clienteRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public ResponseEntity<Cliente> create(@RequestBody Cliente cliente) {
        log.info("Cadastrando cliente: {}", cliente);

        String categoria = classificacaoService.classificarCliente(cliente.getPontos(), 
                calcularIdade(cliente.getDt_nascimento()), 
                calcularTempoFiliacao(cliente.getDt_filiacao()));
        
        cliente.setClassificacao(categoria);
        cliente.setPerfilCompra(categoria);
        
        Cliente savedCliente = clienteRepository.save(cliente);
        return ResponseEntity.status(CREATED).body(savedCliente);
    }

    @GetMapping("{id}")
    public ResponseEntity<Cliente> get(@PathVariable Long id) {
        log.info("Buscando cliente por id: {}", id);
        return clienteRepository.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<Cliente> update(@PathVariable Long id, @RequestBody Cliente cliente) {
        log.info("Atualizando cliente id {} para {}", id, cliente);

        verificarSeExisteCliente(id);
        
        cliente.setId(id);
        
        // Classificando cliente ao ser atualizado
        String categoria = classificacaoService.classificarCliente(cliente.getPontos(), 
                calcularIdade(cliente.getDt_nascimento()), 
                calcularTempoFiliacao(cliente.getDt_filiacao()));
        
        cliente.setClassificacao(categoria);
        cliente.setPerfilCompra(categoria); // Atualizando o campo de perfilCompra se necessário

        Cliente updatedCliente = clienteRepository.save(cliente);
        return ResponseEntity.ok(updatedCliente);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void destroy(@PathVariable Long id) {
        log.info("Apagando cliente {}", id);
        verificarSeExisteCliente(id);
        clienteRepository.deleteById(id);
    }

    private void verificarSeExisteCliente(Long id) {
        clienteRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    // Método para calcular a idade a partir da data de nascimento
    private int calcularIdade(LocalDate dtNascimento) {
        if (dtNascimento == null) {
            return 0; // ou lançar uma exceção, dependendo do seu caso
        }
        return LocalDate.now().getYear() - dtNascimento.getYear();
    }

    // Método para calcular o tempo de filiação a partir da data de filiação
    private int calcularTempoFiliacao(LocalDate dtFiliacao) {
        if (dtFiliacao == null) {
            return 0; // ou lançar uma exceção, dependendo do seu caso
        }
        return LocalDate.now().getYear() - dtFiliacao.getYear();
    }
}
