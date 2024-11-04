package br.com.fiap.nextia.service;

import br.com.fiap.nextia.model.Cliente;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassificacaoService {

    public String classificarCliente(Cliente cliente) {
        int pontos = cliente.getPontos();
        // Adicione aqui a lógica de classificação
        if (pontos > 100) {
            return "VIP";
        } else if (pontos > 50) {
            return "Cliente Regular";
        } else if (pontos > 0) {
            return "Potencial Cliente";
        } else {
            return "Cliente Inativo";
        }
    }

    public void classificarClientes(List<Cliente> clientes) {
        for (Cliente cliente : clientes) {
            String classificacao = classificarCliente(cliente);
            // Aqui você pode armazenar a classificação ou fazer outra coisa com ela
            System.out.println("Cliente: " + cliente.getNome() + ", Classificação: " + classificacao);
        }
    }
}
