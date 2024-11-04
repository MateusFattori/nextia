package br.com.fiap.nextia.service;

import br.com.fiap.nextia.dto.ClienteDTO;
import br.com.fiap.nextia.model.Cliente;
import br.com.fiap.nextia.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Period;
import java.time.LocalDate;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClassificacaoService classificacaoService;

    public Cliente cadastrarCliente(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setNome(clienteDTO.getNome());
        cliente.setCpf(clienteDTO.getCpf());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setSenha(clienteDTO.getSenha());
        cliente.setGenero(clienteDTO.getGenero());
        cliente.setDt_nascimento(clienteDTO.getDt_nascimento());
        cliente.setTelefone(clienteDTO.getTelefone());
        cliente.setPontos(clienteDTO.getPontos());
        cliente.setDt_filiacao(clienteDTO.getDt_filiacao());

        int idade = Period.between(cliente.getDt_nascimento(), LocalDate.now()).getYears();
        int tempoFiliacao = Period.between(cliente.getDt_filiacao(), LocalDate.now()).getYears();

        String classificacao = classificacaoService.classificarCliente(cliente.getPontos(), idade, tempoFiliacao);
        cliente.setClassificacao(classificacao);
        cliente.setPerfilCompra(classificacao);

        return clienteRepository.save(cliente);
    }
}
