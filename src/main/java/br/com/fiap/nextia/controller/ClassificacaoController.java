package br.com.fiap.nextia.controller;

import br.com.fiap.nextia.model.Cliente;
import br.com.fiap.nextia.service.ClassificacaoService;
import br.com.fiap.nextia.repository.ClienteRepository; // Importa o repositório para acessar os clientes

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/classificacao")
public class ClassificacaoController {

    @Autowired
    private ClassificacaoService classificacaoService;

    @Autowired
    private ClienteRepository clienteRepository; // Para acessar os dados dos clientes

    // Endpoint existente para classificar com base nos parâmetros
    @GetMapping("/classificar")
    public String classificar(@RequestParam double pontos, @RequestParam int idade, @RequestParam int tempoFiliacao) {
        return classificacaoService.classificarCliente((int) pontos, idade, tempoFiliacao);
    }
    

    // Novo endpoint para classificar um cliente já existente
    @PostMapping("/classificar-cliente/{id}")
    public String classificarCliente(@PathVariable Long id) {
        Cliente cliente = clienteRepository.findById(id).orElse(null);
        if (cliente == null) {
            return "Cliente não encontrado";
        }

        // Calcula idade e tempo de filiação
        int idade = LocalDate.now().getYear() - cliente.getDt_nascimento().getYear();
        int tempoFiliacao = (int) LocalDate.now().until(cliente.getDt_filiacao()).toTotalMonths();

        // Classifica o cliente
        String classificacao = classificacaoService.classificarCliente(cliente.getPontos(), idade, tempoFiliacao);
        cliente.setClassificacao(classificacao); // Atualiza a classificação do cliente
        cliente.setPerfilCompra(classificacao); // Atualiza o perfil de compra do cliente
        clienteRepository.save(cliente); // Salva as atualizações

        return String.format("Cliente %s classificado como: %s", cliente.getNome(), classificacao);
    }
}
